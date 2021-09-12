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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.With;
import lombok.experimental.Tolerate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.function.Function;

@With
@Builder
@AllArgsConstructor
public class Particle {
    private final Effect effect;
    private final int id;
    private final int data;

    private final Function<Integer, Float> offsetX;
    private final Function<Integer, Float> offsetY;
    private final Function<Integer, Float> offsetZ;

    private final Function<Integer, Float> speed;
    private final Function<Integer, Integer> count;
    private final int radius;

    public Particle withOffset(float offX, float offY, float offZ) {
        return new Particle(effect, id, data, (tick) -> offX, (tick) -> offY, (tick) -> offZ,
                speed, count, radius);
    }

    public Particle withOffset(Function<Integer, Float> fx,
                               Function<Integer, Float> fy,
                               Function<Integer, Float> fz) {
        return new Particle(effect, id, data, fx, fy, fz,
                speed, count, radius);
    }

    @Tolerate
    public Particle withOffsetX(float offX) {
        return withOffsetX((tick) -> offX);
    }

    @Tolerate
    public Particle withOffsetY(float offY) {
        return withOffsetY((tick) -> offY);
    }

    @Tolerate
    public Particle withOffsetZ(float offZ) {
        return withOffsetZ((tick) -> offZ);
    }

    @Tolerate
    public Particle withSpeed(float speed) {
        return withSpeed((tick) -> speed);
    }

    @Tolerate
    public Particle withCount(int count) {
        return withCount((tick) -> count);
    }

    public void spawn(Player p, Vector3 loc) {
        spawn(p, loc, 0);
    }

    public void spawn(Player p, float x, float y, float z) {
        spawn(p, x, y, z, 0);
    }

    public void spawn(World w, Vector3 loc) {
        spawn(w, loc, 0);
    }

    public void spawn(World w, float x, float y, float z) {
        spawn(w, x, y, z, 0);
    }

    public void spawn(Player p, Vector3 loc, int tick) {
        spawn0(p, loc.toLocation(p.getWorld()), tick);
    }

    public void spawn(Player p, float x, float y, float z, int tick) {
        spawn0(p, new Location(p.getWorld(), x, y, z), tick);
    }

    public void spawn(World w, Vector3 loc, int tick) {
        spawn0(w, loc.toLocation(w), tick);
    }

    public void spawn(World w, float x, float y, float z, int tick) {
        spawn0(w, new Location(w, x, y, z), tick);
    }

    private void spawn0(Object o, Location loc, int tick) {
        float offx = this.offsetX.apply(tick);
        float offy = this.offsetY.apply(tick);
        float offz = this.offsetZ.apply(tick);
        float speed = this.speed.apply(tick);
        int n = this.count.apply(tick);

        if (o instanceof Player) {
            ((Player)o).spigot().playEffect(loc, effect, id, data,
                    offx, offy, offz, speed, n, radius);
        } else {
            ((World)o).spigot().playEffect(loc, effect, id, data,
                    offx, offy, offz, speed, n, radius);
        }
    }

    public static class ParticleBuilder {
        public ParticleBuilder() {
            this.offsetX = (tick) -> 0.0f;
            this.offsetY = (tick) -> 0.0f;
            this.offsetZ = (tick) -> 0.0f;
            this.speed = (tick) -> 0.0f;
            this.count = (tick) -> 1;
        }

        @Tolerate
        public ParticleBuilder offsetX(float offsetX) {
            return offsetX((tick) -> offsetX);
        }

        @Tolerate
        public ParticleBuilder offsetY(float offsetY) {
            return offsetY((tick) -> offsetY);
        }

        @Tolerate
        public ParticleBuilder offsetZ(float offsetZ) {
            return offsetZ((tick) -> offsetZ);
        }

        @Tolerate
        public ParticleBuilder speed(float speed) {
            return speed((tick) -> speed);
        }

        @Tolerate
        public ParticleBuilder count(int count) {
            return count((tick) -> count);
        }
    }
}
