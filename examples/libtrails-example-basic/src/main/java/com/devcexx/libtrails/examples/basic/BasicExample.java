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

package com.devcexx.libtrails.examples.basic;

import com.devcexx.libtrails.EntityTrail;
import com.devcexx.libtrails.Particle;
import com.devcexx.libtrails.Vector3;
import com.devcexx.libtrails.suppliers.ScatteringSupplier;
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
import java.util.Random;

public class BasicExample extends JavaPlugin implements Listener {
    private final Map<Entity, EntityTrail> trails = new HashMap<>();
    private final Particle[] particles = new Particle[] {
            Particle.builder()
                    .effect(Effect.FIREWORKS_SPARK)
                    .offsetX(0.0f)
                    .offsetZ(0.0f)
                    .count(3)
                    .radius(120)
                    .build(),

            Particle.builder()
                    .effect(Effect.FLAME)
                    .offsetX(0.0f)
                    .offsetZ(0.0f)
                    .count(3)
                    .radius(120)
                    .build(),

            Particle.builder()
                    .effect(Effect.SMOKE)
                    .offsetX(0.0f)
                    .offsetZ(0.0f)
                    .count(3)
                    .radius(120)
                    .build(),
    };
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
                            new ScatteringSupplier(particles,
                                    new Vector3(2.0f, 1.0f, 2.0f),
                                    Vector3.ORIGIN, 5, 20, 1)
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
