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
 * Represents a basic linear trail.
 */
public class LinearSupplier implements ParticleSupplier {

    private SuppliedParticle particle;

    /**
     * Builds a new linear trail.
     * @param particle The particle of the trail.
     */
    public LinearSupplier(Particle particle) {
        this(particle, Vector3.ORIGIN);
    }

    /**
     * Builds a new linear trail.
     * @param particle The particle of the trail.
     * @param offset The offset of the particle from the origin.
     */
    public LinearSupplier(Particle particle, Vector3 offset) {
        this.particle = new SuppliedParticle(particle, offset);
    }

    @Override
    public Stream<SuppliedParticle> supply(int tick) {
        return Stream.of(this.particle);
    }
}
