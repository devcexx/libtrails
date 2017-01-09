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

import java.util.stream.Stream;

/**
 * Class that provides a circumference trail.
 */
public class SinusoidalSupplier implements ParticleSupplier {
    private final SuppliedParticle[] particles;

    /**
     * Creates a new trail with the specified parameters.
     * @param particle the particle of the sinusoidal wave.
     * @param radius the radius of the sinusoidal wave (amplitude).
     * @param delta the angle between each point of the wave, in radians.
     * @param offset the start angle, in radians.
     * @param rotation the rotation angle of the wave around the Y axis,
     *                 in radians.
     */
    public SinusoidalSupplier(Particle particle, float radius, float delta,
                              float offset, float rotation) {
        int n = (int) (2 * Math.PI / delta);
        particles = new SuppliedParticle[n];
        for (int i = 0; i < n; i++) {
            float angle = offset + delta * i;
            particles[i] = new SuppliedParticle(particle, new Vector3(
                    0,
                    0,
                    Math.sin(angle)
            ).mul(radius).rotateY(rotation));
        }
    }

    @Override
    public Stream<SuppliedParticle> supply(int tick) {
        return Stream.of(particles[tick % particles.length]);
    }
}
