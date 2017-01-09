/*
 *  This file is part of libtrails.
 *  libtrails is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  libtrails is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with libtrails.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.devcexx.libtrails;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Function;

/**
 * Represents a trail of particles that can be synchronized with the movement
 * and the direction of an entity.
 */
public class EntityTrail {

    /**
     * The entity that owns this trail.
     */
    public final Entity entity;

    /**
     * The plugin that will register all the tasks.
     */
    public final Plugin plugin;

    /**
     * The interval of the scheduled task.
     */
    public final int taskInterval;

    /**
     * The transformation applied to the direction of the entity that allows
     * to correctly apply a trail over it.
     */
    public Function<Vector3, Vector3> entityDirectionTransformer;

    /**
     * The transformation applied to the position of the entity that allows
     * to correctly apply a trail over it.
     */
    public Function<Vector3, Vector3> entityPositionTransformer;

    /**
     * The supplier of the trail that will be applied over the entity.
     */
    public ParticleSupplier trail;

    /**
     * The number of ticks that of life that the trail has.
     */
    public int ticksAlive;

    private boolean began;
    private BukkitTask task;

    /**
     * Determines whether the particle is being rendered or not.
     * @return true if does. false otherwhise.
     */
    public boolean hasBegun() {
        return began;
    }

    private static Function<Vector3, Vector3> getDefaultDirFunction(Entity e){
        if (e instanceof Arrow)
            return v -> v.mul(-1.0f, -1.0f, 1.0f);
        return v -> v;

    }

    /**
     * Creates a new {@link EntityTrail} with the default transformations for
     * both position and entity location, optimized for each entity.
     * @param plugin The plugin that owns this trail.
     * @param entity The entity that owns this trail.
     * @param trail The trail that will be applied.
     * @param interval The interval, in Minecraft ticks, of the task that will
     *                 render the trail.
     */
    public EntityTrail(Plugin plugin, Entity entity, ParticleSupplier trail,
                       int interval) {
        this(plugin, entity, getDefaultDirFunction(entity), v -> v, trail,
                interval);
    }

    /**
     * Creates a new {@link EntityTrail}.
     * @param plugin The plugin that owns this trail.
     * @param entity The entity that owns this trail.
     * @param directionTransf The transformation applied to the direction of the
     *                        entity that allows to correctly apply a trail
     *                        over it.
     * @param positionTransf The transformation applied to the position of the
     *                       entity that allows to correctly apply a trail
     *                       over it.
     * @param trail The trail that will be applied.
     * @param interval The interval, in Minecraft ticks, of the task that will
     *                 render the trail.
     */
    public EntityTrail(Plugin plugin, Entity entity,
                       Function<Vector3, Vector3> directionTransf,
                       Function<Vector3, Vector3> positionTransf,
                       ParticleSupplier trail, int interval) {
        this.entityDirectionTransformer = directionTransf == null
                ? v -> v : directionTransf;
        this.entityPositionTransformer = positionTransf == null
                ? v -> v : positionTransf;
        this.entity = entity;
        this.trail = trail;
        this.taskInterval = interval;
        this.plugin = plugin;
    }

    /**
     * Begins the task to render the trail. If the task is already started, it
     * does nothing.
     */
    public void begin() {
        if (!began) {
            began = true;
            task = Bukkit.getScheduler().runTaskTimer(plugin, new TrailTicker(),
                    0, taskInterval);
        }
    }

    /**
     * Stops the trail rendering. If the {@link #begin()} method is called again
     * after invoking this method, the trail will be start rendering with the
     * value of ticksAlive that has when it stopped, unless the method
     * {@link #reset()} is called before.
     */
    public void stop() {
        if (began) {
            task.cancel();
            task = null;
            began = false;
        }
    }

    /**
     * Resets the life of the trail to zero.
     */
    public void reset() {
        ticksAlive = 0;
    }

    private class TrailTicker implements Runnable {

        @Override
        public void run() {
            if (entity.isDead() || !entity.isValid()) {
                stop();
            } else {
                Location eLoc = entity.getLocation();
                Vector3 position = entityPositionTransformer.apply(Vector3
                        .from(eLoc));
                Vector3 direction = entityDirectionTransformer.apply(Vector3
                        .from(eLoc.getDirection()));

                ParticleSupplier finalSupplier = trail.transformVectors(
                        LinearTransf.rotateRenderPlane(direction)
                                .andThen(LinearTransf.translate(position)));

                finalSupplier.supply(ticksAlive).forEach((p) -> p.particle
                        .spawn(eLoc.getWorld(), p.position, ticksAlive));
                ticksAlive += taskInterval;
            }
        }
    }
}
