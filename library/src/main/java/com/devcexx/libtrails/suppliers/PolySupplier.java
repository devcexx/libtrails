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
import com.devcexx.libtrails.TrailUtil;
import com.devcexx.libtrails.Vector3;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Class that allows to render a trail composed by shapes with equidistants
 * vertex placed in a circumference. This points are connected with lines,
 * and a specified jump between vertex. This allow to create regular common
 * 2D polygons (when jump = 0) or complex handy-like star drawings.
 */
public class PolySupplier implements ParticleSupplier {
    public final SuppliedParticle[] particles;
    public final int appearingInterval;

    /**
     * Creates a new instance of the supplier.
     * @param particle Creates a new trail with the specified parameters.
     * @param appearingInterval The time, in Minecraft ticks, that must elapse
     *                          between each drawing.
     * @param vertex The number of vertex of the shape.
     * @param jumps The number of vertex that will be skipped each time when
     *              connecting them with lines.
     * @param radius The radius of the circumference that circumscribes the
     *               shape.
     * @param step The distance, in Minecraft blocks, between each point
     *             of the lines of the shape.
     * @param angleOffset The angle of the first vertex.
     */
    public PolySupplier(Particle particle, int appearingInterval, int vertex,
                        int jumps, float radius, float step,
                        float angleOffset) {
        this.appearingInterval = appearingInterval;

        float fullAngle = (2 * (float) Math.PI / vertex) * (jumps + 1);
        double linesLength = new Vector3(radius, 0, 0)
                .sub(
                        radius * (float) Math.cos(fullAngle),
                        radius * (float) Math.sin(fullAngle), 0
                ).norm();

        int n = (int) Math.round(linesLength / step);

        // k * (j + 1) ≡ 0 (mod n) <=> k ≡ 0 (mod n / mcd(n, j + 1)), where
        // k ≡ line count; n ≡ vertex count; j ≡ jumps between vertex.
        int lineCount = vertex / TrailUtil.gcd(vertex, jumps + 1);

        particles = new SuppliedParticle[lineCount * n];

        for (int i = 0; i < lineCount; i++) {
            float originAngle = angleOffset + i * fullAngle;
            float targetAngle = originAngle + fullAngle;

            Vector3 ptFrom = new Vector3(radius * Math.cos(originAngle), 0,
                    radius * Math.sin(originAngle));
            Vector3 ptTo = new Vector3(radius * Math.cos(targetAngle), 0,
                    radius * Math.sin(targetAngle));

            TrailUtil.fillWithLine(particles, particle, i * n,
                    ptFrom, ptTo.sub(ptFrom).normalize(), step, n);
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
