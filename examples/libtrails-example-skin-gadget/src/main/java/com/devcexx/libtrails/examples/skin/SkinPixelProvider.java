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

package com.devcexx.libtrails.examples.skin;

import java.awt.image.BufferedImage;
import java.util.function.IntBinaryOperator;

public class SkinPixelProvider implements IntBinaryOperator {
    private final BufferedImage skin;

    public SkinPixelProvider(BufferedImage skin) {
        this.skin = skin;
    }

    @Override
    public int applyAsInt(int x, int y) {

        //Test for helmet
        int color = skin.getRGB(40 + x, 8 + y);
        if (((color >> 24) & 0xFF) > 0) { //Check for total transparency
            return color;
        } else {
            //If there's not helmet at that position, return the
            //color of the normal head.
            return skin.getRGB(8 + x, 8 + y);
        }
    }
}
