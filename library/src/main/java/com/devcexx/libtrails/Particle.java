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

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class Particle {
    private final Effect effect;
    private final int id;
    private final int data;

    private final Object offx;
    private final Object offy;
    private final Object offz;

    private final Object speed;
    private final Object particleCount;
    private final int radius;

    public static Builder a() {
        return new Builder();
    }

    public Particle(Effect effect, int id, int data, Object offx,
                       Object offy, Object offz, Object particleCount,
                    Object speed, int radius){
        this.effect = effect;
        this.id = id;
        this.data = data;

        this.speed = speed;
        this.particleCount = particleCount;
        this.radius = radius;

        this.offx = offx;
        this.offy = offy;
        this.offz = offz;
    }

    public Particle withEffect(Effect ef) {
        return new Particle(ef, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withId(int id) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withData(int data) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withOffset(float offx, float offy, float offz) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withOffset(Function<Integer, Float> fx,
                               Function<Integer, Float> fy,
                               Function<Integer, Float> fz) {
        return new Particle(effect, id, data, fx, fy, fz,
                particleCount, speed, radius);
    }

    public Particle withOffsetX(float offx) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withOffsetX(Function<Integer, Float> fx) {
        return new Particle(effect, id, data, fx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withOffsetY(float offy) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withOffsetY(Function<Integer, Float> fy) {
        return new Particle(effect, id, data, offx, fy, offz,
                particleCount, speed, radius);
    }

    public Particle withOffsetZ(float offz) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withOffsetZ(Function<Integer, Float> fz) {
        return new Particle(effect, id, data, offx, offy, fz,
                particleCount, speed, radius);
    }

    public Particle withSpeed(float speed) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
    }

    public Particle withSpeed(Function<Integer, Float> speedf) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speedf, radius);
    }

    public Particle withCount(int n) {
        return new Particle(effect, id, data, offx, offy, offz,
                n, speed, radius);
    }

    public Particle withCount(Function<Integer, Integer> nf) {
        return new Particle(effect, id, data, offx, offy, offz,
                nf, speed, radius);
    }

    public Particle withRadius(int radius) {
        return new Particle(effect, id, data, offx, offy, offz,
                particleCount, speed, radius);
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
        float offx = ((Number) (this.offx instanceof Function ?
                ((Function)this.offx).apply(tick) : this.offx)).floatValue();

        float offy = ((Number) (this.offy instanceof Function ?
                ((Function)this.offy).apply(tick) : this.offy)).floatValue();

        float offz = ((Number) (this.offz instanceof Function ?
                ((Function)this.offz).apply(tick) : this.offz)).floatValue();

        float speed = ((Number) (this.speed instanceof Function ?
                ((Function)this.speed).apply(tick) : this.speed)).floatValue();

        int n = ((Number) (this.particleCount instanceof Function ?
                ((Function)this.particleCount).apply(tick) :
                this.particleCount)).intValue();

        if (o instanceof Player) {
            ((Player)o).spigot().playEffect(loc, effect, id, data,
                    offx, offy, offz, speed, n, radius);
        } else {
            ((World)o).spigot().playEffect(loc, effect, id, data,
                    offx, offy, offz, speed, n, radius);
        }
    }

    public static class Builder {

        private Effect effect;
        private int id;
        private int data;

        private Object offx;
        private Object offy;
        private Object offz;

        private Object speed;
        private Object particleCount;
        private int radius;

        public Builder withEffect(Effect ef) {
            this.effect = ef;
            return this;
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withData(int data) {
            this.data = data;
            return this;
        }

        public Builder withOffsetX(float offx) {
            this.offx = offx;
            return this;
        }

        public Builder withOffsetX(Function<Integer, Float> fx) {
            this.offx = fx;
            return this;
        }

        public Builder withOffsetY(float offy) {
            this.offy = offy;
            return this;
        }

        public Builder withOffsetY(Function<Integer, Float> fy) {
            this.offy = fy;
            return this;
        }

        public Builder withOffsetZ(float offz) {
            this.offz = offz;
            return this;
        }

        public Builder withOffsetZ(Function<Integer, Float> fz) {
            this.offz = fz;
            return this;
        }

        public Builder withSpeed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder withSpeed(Function<Integer, Float> speedf) {
            this.speed = speedf;
            return this;
        }

        public Builder withCount(int n) {
            this.particleCount = n;
            return this;
        }

        public Builder withCount(Function<Integer, Integer> nf) {
            this.particleCount = nf;
            return this;
        }

        public Builder withRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Particle z() {
            return new Particle(effect, id, data, offx, offy, offz,
                    particleCount, speed, radius);
        }
    }


}
