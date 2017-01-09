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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * An interface that identifies a class as a particle supplier for a determined
 * particle trails.
 */
public interface ParticleSupplier {

    /**
     * Returns a stream of particles that should be render at the specified
     * moment.
     * @param tick a number that the particle rendering should be dependent of.
     *             The caller of this method should invoke it with consecutive
     *             values of this number, with a fixed delay rate, to ensure
     *             the particle system is rendered correctly.
     * @return an Stream that contains the particles that should be render
     * at this moment.
     */
    Stream<SuppliedParticle> supply(int tick);

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} method
     * returns the current output stream transformed by the specified function.
     * @param f A {@link BiFunction} that requires a Stream, as the Stream
     *          that should be transformed; and an Integer, as the tick
     *          parameter of the {@link #supply(int)} call, and returns the
     *          transformed Stream.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier transformStream(
            BiFunction<Stream<SuppliedParticle>, Integer,
                    Stream<SuppliedParticle>> f) {
        return tick -> f.apply(ParticleSupplier.this.supply(tick), tick);
    }

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} returns
     * the current output stream where all of its entries are transformed by
     * the specified function.
     * @param f A {@link BiFunction} that requires a {@link SuppliedParticle},
     *          as the particle to transform, and an Integer, as the tick
     *          parameter of the {@link #supply(int)} call, and returns the
     *          transformed particle.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier transformParticles(
            BiFunction<SuppliedParticle, Integer, SuppliedParticle> f) {
        return transformStream((s, t) -> s.map(p -> f.apply(p, t)));
    }

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} returns
     * the current output stream where all of its entries are transformed by
     * the specified function.
     * @param f A {@link BiFunction} that requires a {@link SuppliedParticle},
     *          as the particle to transform, and an Integer, as the tick
     *          parameter of the {@link #supply(int)} call, and returns the
     *          transformed particle.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier transformVectors(Function<Vector3, Vector3> f) {
        return transformStream((s, t) -> s.map(p -> p.with(f.apply(p.position))));
    }

    default ParticleSupplier combine(ParticleSupplier... others) {
        return transformStream((s, t) -> {
            Stream<SuppliedParticle> stream = s;
            for (ParticleSupplier p : others) {
                stream = Stream.concat(s, p.supply(t));
            }
            return stream;
        });
    }

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} returns
     * the current output stream where all of its entries are translated as
     * specified.
     * @param f a {@link Function} that takes an Integer, as the tick of the
     *          current call, and returns the translation vector for that tick.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier translate(Function<Integer, Vector3> f) {
        return transformParticles((p, t) -> p.with(p.position.add(f.apply(t))));
    }

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} returns
     * the current output stream where all of its entries are scaled as
     * specified.
     * @param f a {@link Function} that takes an Integer, as the tick of the
     *          current call, and returns the scale factor for that tick.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier scale(Function<Integer, Float> f) {
        return transformParticles((p, t) -> p.with(p.position
                .scale(f.apply(t))));
    }

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} returns
     * the current output stream where all of its entries are rotated as
     * specified.
     * @param f a {@link Function} that takes an Integer, as the tick of the
     *          current call, and returns the rotation angle around the x axis
     *          for that tick.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier rotateX(Function<Integer, Float> f) {
        return transformParticles((p, t) -> p.with(p.position
                .rotateX(f.apply(t))));
    }

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} returns
     * the current output stream where all of its entries are rotated as
     * specified.
     * @param f a {@link Function} that takes an Integer, as the tick of the
     *          current call, and returns the rotation angle around the y axis
     *          for that tick.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier rotateY(Function<Integer, Float> f) {
        return transformParticles((p, t) -> p.with(p.position
                .rotateY(f.apply(t))));
    }

    /**
     * Creates a {@link ParticleSupplier} whose {@link #supply(int)} returns
     * the current output stream where all of its entries are rotated as
     * specified.
     * @param f a {@link Function} that takes an Integer, as the tick of the
     *          current call, and returns the rotation angle around the z axis
     *          for that tick.
     * @return a new ParticleSupplier with its {@link #supply(int)} output value
     * transformed as requested.
     */
    default ParticleSupplier rotateZ(Function<Integer, Float> f) {
        return transformParticles((p, t) -> p.with(p.position
                .rotateZ(f.apply(t))));
    }

}
