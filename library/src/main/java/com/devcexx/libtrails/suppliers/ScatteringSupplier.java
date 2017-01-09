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
import java.util.Random;
import java.util.stream.Stream;

/**
 * Class that provides a trail that spreads particles randomly into
 * a defined space.
 */
public class ScatteringSupplier implements ParticleSupplier{

    private final Random random = new Random();
    public final Particle[] particles;
    public final Vector3 spreadSpace;
    public final Vector3 spreadSpaceOffset;
    public final int minParticles;
    public final int maxParticles;
    public final int appearingInteval;

    /**
     * Creates a new trail with the specified parameters.
     * @param particles the possible kind of particles that could be spawned
     *                  in the trail.
     * @param spreadSpace the space where the particles can be spawned.
     * @param spreadSpaceOffset the offset of the spread space from the center.
     * @param minParticles the minimum count of particles that must be spawned
     *                     in each step.
     * @param maxParticles the maximum number of particles that could be spawned
     *                     in each step.
     * @param appearingInterval the time between each spawning, in Minecraft
     *                          ticks.
     */
    public ScatteringSupplier(Particle[] particles, Vector3 spreadSpace,
                              Vector3 spreadSpaceOffset, int minParticles,
                              int maxParticles, int appearingInterval) {
        this.particles = particles;

        //Half the size of the space, to consider the positive and negative
        //coordinates.
        this.spreadSpace = spreadSpace.mul(0.5f);
        this.spreadSpaceOffset = spreadSpaceOffset;
        this.minParticles = minParticles;
        this.maxParticles = maxParticles;
        this.appearingInteval = appearingInterval;
    }


    @Override
    public Stream<SuppliedParticle> supply(int tick) {
        if (tick % appearingInteval == 0) {
            int n = random.nextInt(maxParticles - minParticles) + minParticles + 1;
            SuppliedParticle[] res = new SuppliedParticle[n];

            for (int i = 0; i < n; i++) {
                res[i] = new SuppliedParticle(
                        particles[random.nextInt(particles.length)],
                        spreadSpaceOffset.add(
                                spreadSpace.mul(
                                        (float) random.nextGaussian(),
                                        (float) random.nextGaussian(),
                                        (float) random.nextGaussian()
                                )));
            }
            return Arrays.stream(res);
        } else {
            return Stream.of();
        }
    }
}
