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

package com.devcexx.libtrails.suppliers;

import com.devcexx.libtrails.Particle;
import com.devcexx.libtrails.ParticleSupplier;
import com.devcexx.libtrails.SuppliedParticle;
import com.devcexx.libtrails.Vector3;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Class that provides a circumference trail.
 */
public class CircumferenceSupplier implements ParticleSupplier {
    public final SuppliedParticle[] particles;
    public final int appearingInterval;

    /**
     * Creates a new trail with the specified parameters.
     * @param particle the particle of the circumference.
     * @param radius the radius of the circumference.
     * @param delta the variation of the angle between each point, in radians.
     * @param offset the start angle.
     * @param appearingInterval the time, in Minecraft ticks, that must elapse
     *                          between each drawing.
     */
    public CircumferenceSupplier(Particle particle,
                                 float radius, float delta, float offset,
                                 int appearingInterval) {
        this.appearingInterval = appearingInterval;

        int n = (int) Math.round(2 * Math.PI / delta);
        particles = new SuppliedParticle[n];
        for (int i = 0; i < n; i++) {
            float angle = offset + delta * i;
            particles[i] = new SuppliedParticle(particle, new Vector3(
                    Math.cos(angle),
                    0,
                    Math.sin(angle)
            ).mul(radius));
        }
    }

    @Override
    public Stream<SuppliedParticle> supply(int tick) {
        if (tick % appearingInterval == 0) {
            return Arrays.stream(particles);
        } else {
            return Stream.of();
        }
    }
}
