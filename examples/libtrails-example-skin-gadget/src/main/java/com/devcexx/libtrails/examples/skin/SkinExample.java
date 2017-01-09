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

import com.devcexx.libtrails.LinearTransf;
import com.devcexx.libtrails.Vector3;
import com.devcexx.libtrails.suppliers.BitmapSupplier;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class SkinExample extends JavaPlugin implements Listener {

    private CachedSkin steveSkin;
    private CachedSkin alexSkin;

    private final SkinDownloader downloader = new SkinDownloader(this);
    private final Collection<UUID> onlineIds = Collections2.transform(
            Bukkit.getOnlinePlayers(), f -> f == null ? null : f.getUniqueId());

    private final Map<UUID, CachedSkin> cachedSkins = Maps.newHashMap();
    private final Map<UUID, PlayerState> playerState = Maps.newHashMap();


    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        try {
            try (InputStream in = getResource("alex.png")) {
                alexSkin = new CachedSkin(null, null,
                        buildSupplier(ImageIO.read(in)));
            }

            try (InputStream in = getResource("steve.png")) {
                steveSkin = new CachedSkin(null, null,
                        buildSupplier(ImageIO.read(in)));
            }

            getServer().getScheduler().runTaskTimer(this, new SkinCacheCleanupper(),
                    100L, 100L);
            getServer().getScheduler().runTaskTimer(this, new PlayerTicker(),
                    5L, 5L);

            for (Player p : Bukkit.getOnlinePlayers()){
                performJoin(p);
            }
        } catch (Exception ex) {
            getLogger().severe("Failed to load plugin!");
            ex.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void performJoin(Player player) {
        PlayerState st = new PlayerState(player);

        CachedSkin skin = cachedSkins.get(player.getUniqueId());

        String url;
        try {
            url = downloader.getSkinUrl(player);
        } catch (ParseException e1) {
            url = null;
            getLogger().severe("Error parsing GameProfile data!");
            e1.printStackTrace();
        }

        if (skin == null || (url != null && !skin.skinUrl.equals(url))) {
            st.currentSkin = getDefaultSkin(st);
            String finalUrl = url;

            if (url != null) {
                downloader.beginDownloadSkin(url, img -> {
                    if (img != null) {
                        BitmapSupplier supplier = buildSupplier(img);
                        Bukkit.getScheduler().runTask(this, () -> {
                            CachedSkin sk = new CachedSkin(finalUrl,
                                    player.getUniqueId(), supplier);
                            cachedSkins.put(sk.owner, sk);
                            st.currentSkin = sk;
                        });
                    }
                });
            }
        }

        playerState.put(player.getUniqueId(), st);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        performJoin(e.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().distanceSquared(e.getTo()) > 0.0) {
            playerState.get(e.getPlayer().getUniqueId()).lastMovementTime =
                    System.currentTimeMillis();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerState.remove(e.getPlayer().getUniqueId());
    }

    public BitmapSupplier buildSupplier(BufferedImage img) {
        return new BitmapSupplier(Effect.COLOURED_DUST, 1, 60,
                new SkinPixelProvider(img), 8, 8, 2.0f, 2.0f, 8.0f, 8.0f);
    }

    public CachedSkin getDefaultSkin(PlayerState p) {
        return p.defSkinModel == DefaultSkinModel.STEVE ? steveSkin : alexSkin;
    }

    private class PlayerTicker implements Runnable {

        @Override
        public void run() {
            for (PlayerState st : playerState.values()) {
                if (System.currentTimeMillis() - st.lastMovementTime > 100) {

                    Location ploc = st.player.getLocation().add(0, 3.0, 0);
                    Vector3 direction = Vector3.from(ploc.getDirection())
                            .stripY();
                    Vector3 position = Vector3.from(ploc);
                    st.currentSkin.supplier.transformVectors(
                            LinearTransf.translate(0.1f, 0.0f, 0.0f).andThen(
                            LinearTransf.rotateRenderPlane(direction)
                                    .andThen(LinearTransf
                                            .translate(position))))
                    .supply(1).forEach((p) ->
                            p.particle.spawn(st.player.getWorld(), p.position));
                }
            }
        }
    }

    private class SkinCacheCleanupper implements Runnable {

        @Override
        public void run() {
            Iterator<CachedSkin> iterator = cachedSkins.values().iterator();
            while (iterator.hasNext()) {
                CachedSkin k = iterator.next();
                boolean timedout = System.currentTimeMillis() -
                        k.timestamp > 1800;
                if (onlineIds.contains(k.owner)) {
                    k.timestamp = System.currentTimeMillis();
                } else if (timedout ||
                        (cachedSkins.size() > 200 &&
                                onlineIds.size() < cachedSkins.size())) {
                    iterator.remove();
                }
            }
        }
    }
}
