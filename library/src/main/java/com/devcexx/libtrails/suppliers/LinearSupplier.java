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
