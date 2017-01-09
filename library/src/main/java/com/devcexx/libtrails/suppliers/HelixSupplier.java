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
import com.devcexx.libtrails.SuppliedParticle;

import java.util.stream.Stream;

/**
 * Class that provides an helix trail.
 */
public class HelixSupplier extends CircumferenceSupplier {

    /**
     * Creates a new trail with the specified parameters.
     * @param particle the particle of the helix.
     * @param radius the radius of the circumference that defines the helix.
     * @param delta the variation of the angle between each point, in radians.
     * @param offset the start angle.
     */
    public HelixSupplier(Particle particle, float radius, float delta, float offset) {
        super(particle, radius, delta, offset, 0);
    }

    @Override
    public Stream<SuppliedParticle> supply(int tick) {
        return Stream.of(particles[tick % particles.length]);
    }
}
