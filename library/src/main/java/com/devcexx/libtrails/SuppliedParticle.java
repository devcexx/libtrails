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

/**
 * Represents a particle provided by a {@link ParticleSupplier}, ready to be
 * spawned.
 */
public class SuppliedParticle {
    /**
     * The particle that should be rendered.
     */
    public final Particle particle;

    /**
     * The position of the particle respect the origin.
     */
    public final Vector3 position;

    /**
     * Creates a new supplied particle, from the specified particle and offset.
     * @param particle The particle that should be rendered.
     * @param position The position of the particle respect the origin.
     */
    public SuppliedParticle(Particle particle, Vector3 position) {
        this.particle = particle;
        this.position = position;
    }

    /**
     * Returns a new {@link SuppliedParticle} with the specified particle.
     * @param p the new particle.
     * @return A new instance of the {@link SuppliedParticle} with the specified
     * particle, and the same offset.
     */
    public SuppliedParticle with(Particle p) {
        return new SuppliedParticle(p, position);
    }

    /**
     * Returns a new {@link SuppliedParticle} wt
     * @param o the new offset.
     * @return A new instance of the {@link SuppliedParticle} with the same
     * particle and the specified new offset.
     */
    public SuppliedParticle with(Vector3 o) {
        return new SuppliedParticle(particle, o);
    }
}
