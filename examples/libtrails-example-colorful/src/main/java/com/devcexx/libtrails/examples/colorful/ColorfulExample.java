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

package com.devcexx.libtrails.examples.colorful;

import com.devcexx.libtrails.EntityTrail;
import com.devcexx.libtrails.Particle;
import com.devcexx.libtrails.suppliers.CircumferenceSupplier;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class ColorfulExample extends JavaPlugin implements Listener {

    private final Map<Entity, EntityTrail> trails = new HashMap<>();
    private final Particle particle = Particle.builder()
            .effect(Effect.COLOURED_DUST)
            .count(0)
            .speed(1.0f)
            .offsetX(this::tickToColor)

            /* Both Green and Blue components follows the same Piecewise as the
             * Red channel, with shifted to the right.
             */
            .offsetY(t -> tickToColor(t + 256))
            .offsetZ(t -> tickToColor(t + 512))
            .radius(120)
            .build();


    private float tickToColor(int tick) {
        tick = (tick * 20) % 1536;
        if (tick < 256) return 1.0f;
        if (tick < 512) return Math.max(0.001f, (255 - (tick % 256)) / 256.0f);
        if (tick < 1024) return 0.0f;
        if (tick < 1280) return Math.max(0.001f, (tick % 256) / 256.0f);
        return 1.0f;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onBowShot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player &&
                e.getProjectile() instanceof Arrow) {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (!e.getProjectile().isDead()) {
                    EntityTrail t = new EntityTrail(this, e.getProjectile(),
                            new CircumferenceSupplier(particle, 1.2f, 0.9f, 0.0f, 1)
                            .rotateY(tick -> tick * (2.0f * (float) Math.PI / 20))
                            , 1);
                    trails.put(e.getProjectile(), t);
                    t.begin();
                }
            }, 5L);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Arrow) {
            EntityTrail t = trails.remove(e.getEntity());
            if (t != null)
                t.stop();
        }
    }

}
