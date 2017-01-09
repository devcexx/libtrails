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
 * Provides some methods to perform some common operations.
 */
public abstract class TrailUtil {

    /**
     * Returns an approximation of the inverse square root of the specified
     * number.
     * @param n A number greater or equal to zero, whose inverse square root
     *          will be calculated.
     * @return the inverse square root of the specified number.
     */
    public static double isqrt(double n) {
        double xhalf = 0.5 * n;
        long i = Double.doubleToRawLongBits(n);
        i = 0x5FE6EB50C7B537AAL - (i>>1);
        n = Double.longBitsToDouble(i);
        n = n * (1.5 - xhalf*n*n);
        return n;
    }

    /**
     * Returns an approximation of the square root of the specified number.
     * This method is equivalent to {@code 1.0 / isqrt(n)}
     * @param n A number greater or equal to zero, whose square root
     *          will be calculated.
     * @return the square root of the specified number.
     */
    public static double sqrt(double n)
    {
        return 1.0 / isqrt(n);
    }

    /**
     * Returns the Greatest Common Division using a recursive algorithm based
     * on the Euclides theorem.
     * @param a the first number, positive.
     * @param b the second number, positive.
     * @return The Greatest Common Division of the input numbers.
     */
    public static int gcd(int a, int b) {
        int min = Math.min(a,b);
        int max = Math.max(a,b);

        int r = max % min;
        if (r == 0) {
            return min;
        } else {
            return gcd(min, r);
        }
    }

    /**
     * Draws a line into the specified array.
     * @param buffer The array where the line will be w
     * @param p The particles that will be used to draw the line.
     * @param index The first index where the line particles will be placed.
     * @param from The location of the first point of the line.
     * @param director The unit vector that defines the direction of the line.
     * @param step The distance between each point of the line.
     * @param n The number of the points of the line.
     */
    public static void fillWithLine(SuppliedParticle[] buffer, Particle p,
                                    int index, Vector3 from,
                                    Vector3 director, float step, int n) {
        for (int i = 0; i < n; i++) {
            buffer[index + i] = new SuppliedParticle(p,
                    from.add(director.mul(step * i)));
        }
    }
}
