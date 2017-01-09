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
 * Class that provides a star trail.
 */
public class StarSupplier implements ParticleSupplier {
    public final SuppliedParticle[] particles;
    public final int appearingInterval;

    /**
     * Creates a new instance of this class.
     * @param particle The particle used to render the star.
     * @param appearingInterval the time, in Minecraft ticks, that must elapse
     *                          between each star.
     * @param vertex The number of vertex of the star.
     * @param step The distance, in Minecraft blocks, between each point
     *             of the lines of the star.
     * @param highRadius The radius of the outer circumference that delimites
     *                   the star.
     * @param lowRadius The radius of the inner circumference that delimites
     *                  the minimum size of the star.
     * @param angleStartOffset The offset of the initial angle.
     */
    public StarSupplier(Particle particle, int appearingInterval,
                        int vertex, float step, float highRadius,
                        float lowRadius, float angleStartOffset) {
        this.appearingInterval = appearingInterval;

        float fullAngle = (float) (2 * Math.PI / vertex);
        float midAngle = fullAngle / 2.0f;
        double linesLength = new Vector3(highRadius, 0, 0)
                .sub(
                        lowRadius * (float) Math.cos(midAngle),
                        lowRadius * (float) Math.sin(midAngle), 0
                ).norm();

        int pointsPerLine = (int) Math.round(linesLength / step);
        particles = new SuppliedParticle[2 * pointsPerLine * vertex];
        int index = 0;

        for (int i = 0; i < vertex; i++) {
            double highAngle = angleStartOffset + fullAngle * i;
            double lowAngle = highAngle  + midAngle;
            double nextHighAngle = highAngle + fullAngle;

            Vector3 ptFrom = new Vector3(highRadius * Math.cos(highAngle), 0,
                    highRadius * Math.sin(highAngle));
            Vector3 ptTo = new Vector3(lowRadius * Math.cos(lowAngle), 0,
                    lowRadius * Math.sin(lowAngle));

            TrailUtil.fillWithLine(particles, particle, index,
                    ptFrom, ptTo.sub(ptFrom).normalize(), step, pointsPerLine);
            index += pointsPerLine;

            ptFrom = ptTo;
            ptTo = new Vector3(highRadius * Math.cos(nextHighAngle), 0,
                    highRadius * Math.sin(nextHighAngle));

            TrailUtil.fillWithLine(particles, particle, index,
                    ptFrom, ptTo.sub(ptFrom).normalize(), step, pointsPerLine);
            index += pointsPerLine;
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
