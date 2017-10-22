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

import java.util.Vector;
import java.util.function.Function;

public abstract class LinearTransf {
    public static Function<Vector3, Vector3> translate(float xoffset,
                                                       float yoffset,
                                                       float zoffset) {
        return v -> v.add(xoffset, yoffset, zoffset);
    }

    public static Function<Vector3, Vector3> translate(Vector3 vec) {
        return v -> v.add(vec);
    }

    public static Function<Vector3, Vector3> scale(float scaleFactor) {
        return v -> v.scale(scaleFactor);
    }

    public static Function<Vector3, Vector3> rotate(Vector3 axis, float angle) {
        return v -> v.rotate(axis, angle);
    }

    public static Function<Vector3, Vector3> rotateRenderPlane(Vector3 normal) {
        //Gets the rotation axis of the transformation.
        Vector3 axis = Vector3.AXIS_Y.cross(normal).normalize();
        if (axis.equals(Vector3.ORIGIN)) {
            if (Vector3.AXIS_Y.dot(normal) > 0) {
                //Axis Y has the same direction as normal. Nothing to do
                return v -> v;
            } else {
                //Axis Y is opposite to normal.
                axis = Vector3.AXIS_Z;
            }
        }

        //Get normal vector on the XZ plane.
        Vector3 xzvector = normal.stripY();

        //Gets the rotation angle of the normal vector
        //around the y axis.
        float yAngle = (float) Vector3.AXIS_Z.fullAngle(xzvector,
                Vector3.AXIS_Y);

        //Gets the rotation axis of the transformation.
        float angle = (float) normal.angle(Vector3.AXIS_Y);

        //First rotation: rotates the point around the y axis
        //to place the drawing in front of the rotation axis, to avoid
        //unexpected rotations.

        //Second rotation: rotates the point around the axis of the
        //rotation between the Y axis and the new normal axis, to place
        //the drawing inside the plane.
        Vector3 faxis = axis;
        return v -> v.rotateY(yAngle + (float) Math.PI).rotate(faxis, angle);
    }
}
