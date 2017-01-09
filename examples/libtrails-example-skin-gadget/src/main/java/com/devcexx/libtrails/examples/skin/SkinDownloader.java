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

import com.google.common.collect.Iterators;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SkinDownloader {

    private final Logger logger;
    private final Executor pool = Executors
            .newCachedThreadPool(r -> new Thread(r, "Skin Downloader"));

    public SkinDownloader(Plugin pl) {
        this.logger = pl.getLogger();
    }

    public void beginDownloadSkin(String url, Callback<BufferedImage>
            callback) {
        pool.execute(() -> {
            try {
                BufferedImage img = downloadSkin(url);
                if (callback != null) {
                    callback.done(img);
                }
            } catch (Exception ex) {
                logger.severe("Failed to download skin from url " + url);
                ex.printStackTrace();
            }
        });
    }

    public BufferedImage downloadSkin(String url)
            throws IOException{
        return ImageIO.read(new URL(url));
    }

    public String getSkinUrl(Player p) throws ParseException {
        return getSkinUrl((GameProfile) Reflector
                .invoke(p.getClass(), "getProfile", p, null, null));
    }

    public String getSkinUrl(GameProfile prof) throws ParseException {
        Collection<Property> ps = prof.getProperties().get("textures");

        if (ps == null || ps.isEmpty()) {
            return null;
        } else {
            Property p = Iterators.getLast(ps.iterator());

            JSONObject obj = (JSONObject) new JSONParser().parse(
                    new String(Base64.getDecoder().decode(p.getValue())));

            obj = ((JSONObject) obj.get("textures"));
            obj = ((JSONObject) obj.get("SKIN"));
            return (String) obj.get("url");
        }
    }

    public interface Callback<T> {
        void done(T o);
    }
}
