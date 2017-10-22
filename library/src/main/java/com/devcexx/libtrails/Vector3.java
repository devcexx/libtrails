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

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * Represents a vector member of the R^3 Euclidean vector space and provides
 * methods to transform it. This class is immutable. This means that the
 * components xyz of the vector cannot be modified after the constructor, and
 * all the methods that returns instances of Vector3 will return fresh instances
 * of this class.
 *
 * @author devcexx
 */
public class Vector3 {
    /**
     * A final field that contains the null vector of the Euclidean space
     * (0,0,0)
     */
    public static final Vector3 ORIGIN = new Vector3(0,0,0);

    /**
     * A final field that contains the director vector of the X-axis of the
     * canonical base.
     */
    public static final Vector3 AXIS_X = new Vector3(1,0,0);

    /**
     * A final field that contains the director vector of the Y-axis of the
     * canonical base.
     */
    public static final Vector3 AXIS_Y = new Vector3(0,1,0);

    /**
     * A final field that contains the director vector of the Z-axis of the
     * canonical base.
     */
    public static final Vector3 AXIS_Z = new Vector3(0,0,1);

    /**
     * The X component of the vector.
     */
    public final float x;

    /**
     * The Y component of the vector.
     */
    public final float y;

    /**
     * The Z component of the vector.
     */
    public final float z;

    /**
     * Creates a new instance of a Vector3 with the specified components.
     * @param x The x component of the new vector.
     * @param y The y component of the new vector.
     * @param z The z component of the new vector.
     */
    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new instance of a Vector3 with the specified components.
     * @param x The x component of the new vector.
     * @param y The y component of the new vector.
     * @param z The z component of the new vector.
     */
    public Vector3(double x, double y, double z) {
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
    }

    /**
     * Creates a new instance of a Vector3 with the specified array of
     * components.
     * @param comps an array of three elements that contains the components
     *              of the new vector. The components must in the order of
     *              x, y, z.
     */
    public Vector3(float[] comps) {
        this.x = comps[0];
        this.y = comps[1];
        this.z = comps[2];
    }

    /**
     * Builds a new instance of this class from the components of a vector
     * from the Bukkit API.
     * @param v the source vector.
     * @return A new instance of this class with the same values as the source
     * vector.
     *
     * @see Vector
     */
    public static Vector3 from(Vector v){
        return new Vector3(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Builds a new instance of this class from the components of a Location
     * object from the Bukkit API.
     * @param l the source Location object.
     * @return A new instance of this class with the same values as the source
     * Object.
     *
     * @see Vector
     */
    public static Vector3 from(Location l){
        return new Vector3(l.getX(), l.getY(), l.getZ());
    }

    /**
     * Builds a new instance of this class from the components of a Block
     * object from the Bukkit API.
     * @param l the source Block object.
     * @return A new instance of this class with the same values as the source
     * Object.
     *
     * @see Vector
     */
    public static Vector3 from(Block l){
        return new Vector3(l.getX(), l.getY(), l.getZ());
    }

    /**
     * Determines if the current object is inside the cuboid defined by the
     * input vectors.
     * @param min The lower point of the cuboid, inclusive.
     * @param max The upper point of the cuboid, inclusive.
     * @return {@code true} if the current vector is contained in the cuboid.
     * {@code false} otherwise.
     */
    public boolean isBetween(Vector3 min, Vector3 max){
        return min.x >= x && min.y >= y && min.z >= z &&
                x <= max.x && y <= max.y && z <= max.z;
    }

    /**
     * Create a new vector from the current one, but setting a new value for the
     * x component.
     * @param x The x component of the returned vector.
     * @return A new vector created from the current one, but with the specified
     * x component.
     */
    public Vector3 withX(float x){
        return new Vector3(x, y, z);
    }

    /**
     * Create a new vector from the current one, but setting a new value for the
     * y component.
     * @param y The y component of the returned vector.
     * @return A new vector created from the current one, but with the specified
     * y component.
     */
    public Vector3 withY(float y){
        return new Vector3(x, y, z);
    }

    /**
     * Create a new vector from the current one, but setting a new value for the
     * z component.
     * @param z The z component of the returned vector.
     * @return A new vector created from the current one, but with the specified
     * z component.
     */
    public Vector3 withZ(float z){
        return new Vector3(x, y, z);
    }

    /**
     * Sum up the components of the current vector with the specified ones.
     * @param x the x component.
     * @param y the y component.
     * @param z the z component.
     * @return A new vector that is the sum of the components of the current one
     * with the specified components.
     */
    public Vector3 add(float x, float y, float z){
        return new Vector3(this.x + x, this.y + y, this.z + z);
    }

    /**
     * Sum up the current vector with another.
     * @param other The vector to sum with.
     * @return A new vector that is the sum of the current vector with the
     * input vector.
     */
    public Vector3 add(Vector3 other){
        return add(other.x, other.y, other.z);
    }

    /**
     * Subtract up the components of the current vector with the specified ones.
     * @param x the x component.
     * @param y the y component.
     * @param z the z component.
     * @return A new vector that is the subtraction of the components of the
     * current on with the specified components.
     */
    public Vector3 sub(float x, float y, float z){
        return new Vector3(this.x - x, this.y - y, this.z - z);
    }

    /**
     * Subtract up the current vector with another.
     * @param other The vector to sum with.
     * @return A new vector that is the result of the subtraction of the current
     * vector with the input vector.
     */
    public Vector3 sub(Vector3 other){
        return sub(other.x, other.y, other.z);
    }

    /**
     * Multiplies all the components of the current vector by a factor f.
     * @param f the multiplication factor.
     * @return A new vector that is the result of the multiplication of all
     * the components of the current vector by a factor f.
     */
    public Vector3 mul(float f){
        return new Vector3(this.x * f, this.y * f, this.z * f);
    }

    /**
     * Multiplies each component of the current vector by its corresponding
     * component of the input vector.
     * @param f the multiplication vector.
     * @return A new vector that is the result of the multiplication of each
     * component of the current vector by its corresponding component of the
     * input vector.
     */
    public Vector3 mul(Vector3 f){
        return new Vector3(this.x * f.x, this.y * f.y, this.z * f.z);
    }

    /**
     * Multiplies each component of the current vector by its corresponding
     * specified component.
     * @param fx The x component.
     * @param fy The y component.
     * @param fz The z component.
     * @return A new vector that is the result of the multiplication of each
     * component of the current vector by its corresponding specified component.
     */
    public Vector3 mul(float fx, float fy, float fz){
        return new Vector3(this.x * fx, this.y * fy, this.z * fz);
    }

    /**
     * Divides all the components of the current vector by a divisor f.
     * @param f the divisor.
     * @return A new vector that is the result of the divison of all
     * the components of the current vector by a divisor f.
     */
    public Vector3 div(float f){
        return new Vector3(this.x / f, this.y / f, this.z / f);
    }

    /**
     * Divides each component of the current vector by its corresponding
     * component of the input vector.
     * @param f the divisor vector.
     * @return A new vector that is the result of the divsion of each
     * component of the current vector by its corresponding component of the
     * input vector.
     */
    public Vector3 div(Vector3 f){
        return new Vector3(this.x / f.x, this.y / f.y, this.z / f.z);
    }

    /**
     * Divides each component of the current vector by its corresponding
     * specified component.
     * @param fx The x component.
     * @param fy The y component.
     * @param fz The z component.
     * @return A new vector that is the result of the division of each
     * component of the current vector by its corresponding specified component.
     */
    public Vector3 div(float fx, float fy, float fz){
        return new Vector3(this.x / fx, this.y / fy, this.z / fz);
    }

    /**
     * Returns the dot product between the current vector and other.
     * @param other the other vector.
     * @return The dot product between the current vector and other in a float
     * number.
     */
    public float dot(Vector3 other){
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    /**
     * Returns the vector that represents the cross product between this vector
     * and another, where this vector is the first operand, and the specified
     * vector, the second one.
     * @param o the other vector.
     * @return the cross product between this vector
     * and another.
     */
    public Vector3 cross(Vector3 o){
        return new Vector3(
                this.y * o.z - o.y * this.z,
                this.z * o.x - o.z * this.x,
                this.x * o.y - o.x * this.y
        );
    }

    /**
     * Returns the mixed product, or the triple scalar product between this
     * vector and two more vectors, where the current vector is the first
     * operand of the dot product of the operation.
     * @param cross1 the first operand of the cross product.
     * @param cross2 the second operand of the cross product.
     * @return the mixed product of the current vector and another two ones.
     */
    public float mixed(Vector3 cross1, Vector3 cross2){
        return cross1.cross(cross2).dot(this);
    }

    /**
     * Returns the squared norm, or squared length of the current vector.
     * @return The squared norm of the current product.
     */
    public double normSquared(){
        return x * x + y * y + z * z;
    }

    /**
     * Returns the norm, or length, of the current vector.
     * @return the norm of the current vector.
     */
    public double norm(){
        return (float) TrailUtil.sqrt(normSquared());
    }

    /**
     * Returns the squared distance between the current vector and the
     * specified one.
     * @param o The other vector.
     * @return The squared distance between the current vector and the
     * specified one.
     */
    public float distanceSquared(Vector3 o){
        float x = this.x - o.x;
        float y = this.y - o.y;
        float z = this.z - o.z;
        return x * x + y * y + z * z;
    }

    /**
     * Returns the Euclidean distance between the current vector and the
     * specified one.
     * @param o the other vector.
     * @return the euclidean distance between the current vector and the
     * specified one.
     */
    public double distance(Vector3 o){
        return TrailUtil.sqrt(distanceSquared(o));
    }

    /**
     * Returns the cosine of the angle between the current vector and another.
     * @param o the other vector.
     * @return the cosine of the angle between the current vector and another.
     */
    public float cosine(Vector3 o){
        return this.dot(o) / ((float) this.norm() * (float) o.norm());
    }

    /**
     * Returns the angle between the current vector and another.
     * @param o the other vector.
     * @return the angle in radians between the current vector and another. The
     * angle will be between 0 and π, inclusive.
     */
    public double angle(Vector3 o){
        return Math.acos(cosine(o));
    }


    /**
     * Returns the full angle between the current vector and another.
     * @param o the other vector.
     * @param normal the vector perpendicular to the plane respect the angle
     *               will be metered.
     * @return The angle between 0 and 2π between this vector and the specified
     * vector, respect to the plane whose perpendicular vector is the specified
     * normal vector.
     */
    public double fullAngle(Vector3 o, Vector3 normal){
        float dot = this.dot(o);
        float det = normal.mixed(this, o);
        double angle = Math.atan2(det, dot);
        return angle > 0 ? angle : angle + 2 * Math.PI;
    }

    /**
     * Returns this vector normalized, this means, with norm 1
     * @return the current vector normalized.
     */
    public Vector3 normalize(){
        float mod = (float) this.norm();
        if (mod == 0) return Vector3.ORIGIN;
        return new Vector3(x / mod, y / mod, z / mod);
    }

    /**
     * Scales the current vector to get one with the same direction as the
     * current one, but with the specified norm.
     * @param norm the new norm of the vector.
     * @return a vector with the same direction as the current one, but
     * with the specified norm.
     */
    public Vector3 withNorm(float norm) {
        return normalize().mul(norm);
    }

    /**
     * Scales the current vector to get one with the same direction as the
     * current one, but with the specified size factor.
     * @param factor the size factor of the norm.
     * @return a vector with the same direction as the current one, but
     * with its norm multiplied by the specified factor.
     */
    public Vector3 scale(float factor) {
        return withNorm((float) this.norm() * factor);
    }

    /**
     * Rotates the current vector around de X axis of the canonical base.
     * @param angle the rotation angle, in radians.
     * @return the current vector rotated around the X axis, {@code angle}
     * radians.
     */
    public Vector3 rotateX(float angle){
        return rotate(AXIS_X, angle);
    }

    /**
     * Rotates the current vector around de Y axis of the canonical base.
     * @param angle the rotation angle, in radians.
     * @return the current vector rotated around the Y axis, {@code angle}
     * radians.
     */
    public Vector3 rotateY(float angle){
        return rotate(AXIS_Y, angle);
    }

    /**
     * Rotates the current vector around de Z axis of the canonical base.
     * @param angle the rotation angle, in radians.
     * @return the current vector rotated around the Z axis, {@code angle}
     * radians.
     */
    public Vector3 rotateZ(float angle){
        return rotate(AXIS_Z, angle);
    }

    /**
     * Rotates the current vector around an arbitrary axis, defined by the
     * parameter k.
     * @param k the rotation axis.
     * @param angle the rotation angle, in radians.
     * @return the current vector rotated around the {@code k} axis,
     * {@code angle} radians.
     */
    public Vector3 rotate(Vector3 k, float angle){
        if (k.normSquared() != 1){
            k = k.normalize();
        }

        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);

        Vector3 cross = k.cross(this);
        float kfactor = k.dot(this) * (1 - cos);

        return new Vector3(
                this.x * cos + cross.x * sin + k.x * kfactor,
                this.y * cos + cross.y * sin + k.y * kfactor,
                this.z * cos + cross.z * sin + k.z * kfactor
        );
    }

    /**
     * Projects the current vector onto the specified one.
     * @param onto The vector.
     * @return The current vector, projected onto the specified vector.
     */
    public Vector3 project(Vector3 onto) {
        return onto.mul(this.dot(onto) / (float) onto.normSquared());
    }

    /**
     * Projects the current vector onto the surface whose ortogonal base is
     * defined by the specified vectors.
     * @param d1 The first vector of the surface base, ortogonal to {@code d2}
     * @param d2 The second vector of the surface base, ortogonal to {@code d1}
     * @return The current vector projected onto the surface defined by the
     * specified base.
     */
    public Vector3 projectOntoSurface(Vector3 d1, Vector3 d2) {
        return this.project(d1).add(this.project(d2));
    }

    /**
     * Returns the current vector, with the x component set to 0
     * @return the current vector with the x component set to 0
     */
    public Vector3 stripX(){
        return withX(0);
    }

    /**
     * Returns the current vector, with the y component set to 0
     * @return the current vector with the y component set to 0
     */
    public Vector3 stripY(){
        return withY(0);
    }

    /**
     * Returns the current vector, with the z component set to 0
     * @return the current vector with the z component set to 0
     */
    public Vector3 stripZ(){
        return withZ(0);
    }

    /**
     * Returns the current vector as a vector of the Bukkit API.
     * @return the current vector as a vector of Bukkit API.
     */
    public Vector toVector(){
        return new Vector(x, y, z);
    }

    /**
     * Returns the current vector as an arry of components.
     * @return a float array of 3 items that contains the value of each
     * component x, y, z, respectively.
     */
    public float[] tofloats(){
        return new float[]{x, y, z};
    }

    /**
     * Returns the current vector as a Location object, with the world set to
     * {@code null}, and pitch and yaw set to 0.
     * @return a Location object with the data of the current vector.
     */
    public Location toLocation(){
        return toLocation(null);
    }

    /**
     * Returns the current vector as a Location object, with the world set to
     * the specified one, and pitch and yaw set to 0.
     *
     * @param w The target world.
     * @return a Location object with the data of the current vector.
     */
    public Location toLocation(World w){
        return toLocation(w, 0.0f, 0.0f);
    }

    /**
     * Returns the current vector as a Location object, with the world, pitch
     * and yaw specified.
     *
     * @param w The target world.
     * @param pitch The pitch.
     * @param yaw The yaw.
     * @return a Location object with the data of the current vector.
     */
    public Location toLocation(World w, float pitch, float yaw){
        return new Location(w, x, y, z, yaw, pitch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        return Float.compare(vector3.x, x) == 0 && Float.compare(vector3.y, y) == 0 && Float.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + "; " + y + "; " + z + "]";
    }
}
