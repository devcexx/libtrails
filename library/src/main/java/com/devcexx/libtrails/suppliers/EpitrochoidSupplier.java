package com.devcexx.libtrails.suppliers;

import com.devcexx.libtrails.*;

/**
 * Class that provides an Epitrochoid trail.
 */
public class EpitrochoidSupplier extends SpirographSupplier {

    /**
     * Builds a new epitrochoid trail.
     * @param particle The particle that will be spawned.
     * @param delta The variation of the angle per drawn point.
     * @param directorRadius The radius of the fixed circumference.
     * @param rollingRadius The radius of the rolling circumference.
     * @param hdist The distance between the center of the rolling center to
     *              the point that will be used to draw the curve.
     * @param appearingInterval the time, in Minecraft ticks, that must elapse
     *                          between each drawing.
     */
    public EpitrochoidSupplier(Particle particle, float delta,
                               float directorRadius, float rollingRadius,
                               float hdist, int appearingInterval) {
        super(particle, delta, directorRadius, rollingRadius, hdist,
                appearingInterval);
    }

    @Override
    protected Vector3 fetchVector(float R, float r, float h, float theta) {
        return new Vector3(
                (R + r) * Math.cos(theta) - h * Math.cos(theta * (R + r) / r),
                0,
                (R + r) * Math.sin(theta) - h * Math.sin(theta * (R + r) / r)
        );
    }
}
