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

import java.lang.reflect.Method;

public class Reflector {
    public static Object invoke(Class<?> from, String method, Object instance,
                                Class<?>[] types, Object[] params){
        try {
            Method mtd = from.getDeclaredMethod(method, types);
            mtd.setAccessible(true);
            return mtd.invoke(instance, params == null ?
                    new Object[0] : params);
        } catch (Exception ex){
            throw new RuntimeException("Reflection error!", ex);
        }
    }
}
