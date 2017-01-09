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

import org.bukkit.entity.Player;

import java.util.UUID;

class PlayerState {
    public final Player player;
    public final UUID id;
    public final DefaultSkinModel defSkinModel;
    public CachedSkin currentSkin;
    public long lastMovementTime;


    PlayerState(Player p) {
        this.player = p;
        this.id = p.getUniqueId();
        this.defSkinModel = (id.hashCode() & 1) == 1 ?
                DefaultSkinModel.ALEX :
                DefaultSkinModel.STEVE;
    }
}
