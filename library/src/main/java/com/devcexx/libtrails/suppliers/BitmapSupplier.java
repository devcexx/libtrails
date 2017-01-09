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
import org.bukkit.Effect;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.function.IntBinaryOperator;
import java.util.stream.Stream;

/**
 * Class that allows to represent compose an image made of coloreables
 * particles in Minecraft, from a bitmap.
 */
public class BitmapSupplier implements ParticleSupplier {
    public final SuppliedParticle[] particles;
    public final int allocatedPixels;
    public final int appearingInterval;

    /**
     * Creates a new bitmap supplier.
     * @param particleType the type of the particle that will be spawned. MUST
     *                     BE a coloreable particle, (COLORED_DUST or SPELL).
     * @param appearingInterval the time, in Minecraft ticks, that must elapse
     *                          between each image.
     * @param visibilityRadius the visible radius of the particles. Anyone
     *                         in the same world as the particles, and nearer
     *                         than this value will be able to see them.
     * @param image an instance of {@link BufferedImage} that contains the
     *              imagen that will be rendered.
     * @param w the width, in Minecraft Blocks of the rendered image.
     * @param h the height, in Minecraft Blocks of the rendered image.
     * @param horizontalResolution the number of horizontal particles per
     *                             block that will compose the rendered image.
     * @param verticalResolution the number of vertical particles per
     *                             block that will compose the rendered image.
     */
    public BitmapSupplier(Effect particleType, int appearingInterval,
                          int visibilityRadius, BufferedImage image, float w,
                          float h, float horizontalResolution,
                          float verticalResolution) {

        this(particleType, appearingInterval, visibilityRadius, image::getRGB,
                image.getWidth(), image.getHeight(), w, h, horizontalResolution,
                verticalResolution);
    }

    /**
     * Creates a new bitmap supplier.
     * @param particleType the type of the particle that will be spawned. MUST
     *                     BE a coloreable particle, (COLORED_DUST or SPELL).
     * @param appearingInterval the time, in Minecraft ticks, that must elapse
     *                          between each image.
     * @param visibilityRadius the visible radius of the particles. Anyone
     *                         in the same world as the particles, and nearer
     *                         than this value will be able to see them.
     * @param provider a {@link IntBinaryOperator} that is the origin of the
     *                 data of the bitmap. It must take two parameters: x and y,
     *                 respectively. The result of this function must the color
     *                 of the pixel located at (x, y), in ARGB format, with
     *                 8 bits per channel. <br>
     *                 [AAAAAAAA|RRRRRRRR|GGGGGGGG|BBBBBBBB]
     * @param originw the width of the source image.
     * @param originh the height of the source image.
     * @param w the width, in Minecraft Blocks of the rendered image.
     * @param h the height, in Minecraft Blocks of the rendered image.
     * @param horizontalResolution the number of horizontal particles per
     *                             block that will compose the rendered image.
     * @param verticalResolution the number of vertical particles per
     *                             block that will compose the rendered image.
     */
    public BitmapSupplier(Effect particleType, int appearingInterval,
                          int visibilityRadius, IntBinaryOperator provider,
                          int originw, int originh, float w, float h,
                          float horizontalResolution,
                          float verticalResolution) {

        if (particleType != Effect.COLOURED_DUST
                && particleType != Effect.SPELL)
            throw new IllegalArgumentException("The specified effect " +
                    "cannot have RGB colors");

        this.appearingInterval = appearingInterval;

        int finalw = (int) (w * horizontalResolution);
        int finalh = (int) (h * verticalResolution);

        particles = new SuppliedParticle[finalh * finalw];

        double xdist = 1.0 / horizontalResolution;
        double zdist = 1.0 / verticalResolution;

        float midw = w / 2;
        float midh = h / 2;

        double xScale = originw/(double)finalw;
        double yScale = originh/(double)finalh;
        int px, py;

        int index = 0;

        //Nearest neighbor image scaling
        for (int i = 0; i < finalh; i++) {
            for (int j = 0; j < finalw; j++) {

                px = (int) Math.floor(j*xScale);
                py = (int) Math.floor(i*yScale);
                int argb = provider.applyAsInt(px, py);

                if (((argb >> 24) & 0xFF) >= 127) {

                    //Math.max(0.001f...): if speed is 0.0f, Minecraft draws it
                    //with a red color. This code avoids it.
                    float r = Math.max(0.001f, ((argb >> 16) & 0xFF) / 256.0f);
                    float g = Math.max(0.001f, ((argb >> 8) & 0xFF) / 256.0f);
                    float b = Math.max(0.001f, (argb & 0xFF) / 256.0f);

                    particles[index++] = new SuppliedParticle(
                            Particle.a()
                                    .withEffect(particleType)
                                    .withRadius(visibilityRadius)
                                    .withCount(0)
                                    .withOffsetX(r)
                                    .withOffsetY(g)
                                    .withOffsetZ(b)
                                    .withSpeed(1.0f).z(),
                            new Vector3(j * xdist - midw, 0, midh - (i * zdist))
                    );
                }
            }
        }
        this.allocatedPixels = index;
    }

    @Override
    public Stream<SuppliedParticle> supply(int tick) {
        if (tick % appearingInterval == 0) {
            return Arrays.stream(particles, 0, allocatedPixels);
        } else {
            return Stream.of();
        }
    }

}
