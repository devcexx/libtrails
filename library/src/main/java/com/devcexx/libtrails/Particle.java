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

    private final float offx;
    private final float offy;
    private final float offz;

    private final float speed;
    private final int particleCount;
    private final int radius;

    private final Function<Integer, Float> offxf;
    private final Function<Integer, Float> offyf;
    private final Function<Integer, Float> offzf;
    private final Function<Integer, Float> speedf;
    private final Function<Integer, Integer> particleCountf;

    public static Builder a() {
        return new Builder();
    }

    protected Particle(Effect effect, int id, int data, float offx,
                       float offy, float offz, Function<Integer, Float> offxf,
                       Function<Integer, Float> offyf,
                       Function<Integer, Float> offzf,
                       int particleCount,
                       Function<Integer, Integer> particleCountf, float speed,
                       Function<Integer, Float> speedf, int radius){
        this.effect = effect;
        this.id = id;
        this.data = data;

        this.offx = offx;
        this.offy = offy;
        this.offz = offz;

        this.speed = speed;
        this.particleCount = particleCount;
        this.radius = radius;

        this.offxf = offxf;
        this.offyf = offyf;
        this.offzf = offzf;

        this.speedf = speedf;
        this.particleCountf = particleCountf;
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
        float offx = offxf == null ? this.offx : offxf.apply(tick);
        float offy = offyf == null ? this.offy : offyf.apply(tick);
        float offz = offzf == null ? this.offz : offzf.apply(tick);
        float speed = speedf == null ? this.speed : speedf.apply(tick);
        int n = particleCountf == null ? this.particleCount :
                particleCountf.apply(tick);

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

        private float offx;
        private float offy;
        private float offz;

        private float speed;
        private int particleCount;
        private int radius;

        private Function<Integer, Float> offxf = null;
        private Function<Integer, Float> offyf = null;
        private Function<Integer, Float> offzf = null;
        private Function<Integer, Float> speedf = null;
        private Function<Integer, Integer> particleCountf = null;

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
            this.offxf = null;
            return this;
        }

        public Builder withOffsetX(Function<Integer, Float> fx) {
            this.offxf = fx;
            return this;
        }

        public Builder withOffsetY(float offy) {
            this.offy = offy;
            this.offyf = null;
            return this;
        }

        public Builder withOffsetY(Function<Integer, Float> fy) {
            this.offyf = fy;
            return this;
        }

        public Builder withOffsetZ(float offz) {
            this.offz = offz;
            this.offzf = null;
            return this;
        }

        public Builder withOffsetZ(Function<Integer, Float> fz) {
            this.offzf = fz;
            return this;
        }

        public Builder withSpeed(float speed) {
            this.speed = speed;
            this.speedf = null;
            return this;
        }

        public Builder withSpeed(Function<Integer, Float> speedf) {
            this.speedf = speedf;
            return this;
        }

        public Builder withCount(int n) {
            this.particleCount = n;
            this.particleCountf = null;
            return this;
        }

        public Builder withCount(Function<Integer, Integer> nf) {
            this.particleCountf = nf;
            return this;
        }

        public Builder withRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Particle z() {
            return new Particle(effect, id, data, offx, offy, offz, offxf,
                    offyf, offzf, particleCount, particleCountf, speed,
                    speedf, radius);
        }
    }


}
