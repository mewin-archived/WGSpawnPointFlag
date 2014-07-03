/*
 * Copyright (C) 2013 mewin<mewin001@hotmail.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.mewin.wgspf;

import com.mewin.WGCustomFlags.FlagManager;
import com.mewin.WGCustomFlags.flags.CustomLocationFlag;
import com.mewin.util.Util;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 *
 * @author mewin<mewin001@hotmail.de>
 */
public class RespawnListener implements Listener
{
    private WorldGuardPlugin wgp;
    private HashMap<String, Location> respawnLocations;
    private CustomLocationFlag respawnLocationFlag = null;
    
    public RespawnListener(WorldGuardPlugin wgp)
    {
        this.respawnLocations = new HashMap<String, Location>();
        this.wgp = wgp;
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e)
    {
        if (respawnLocations.containsKey(e.getPlayer().getName()))
        {
            e.setRespawnLocation(respawnLocations.remove(e.getPlayer().getName()));
        }
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e)
    {
        if (this.respawnLocationFlag == null)
        {
            this.respawnLocationFlag = (CustomLocationFlag) FlagManager.getCustomFlag("respawn-location");
        }
        if (e.getEntityType() == EntityType.PLAYER)
        {
            com.sk89q.worldedit.Location loc = Util.getFlagValue(wgp, e.getEntity().getLocation(), respawnLocationFlag);
            if (loc != null)
            {
                respawnLocations.put(((Player) e.getEntity()).getName(), BukkitUtil.toLocation(loc));
            }
            else
            {
                System.out.println(e.getEntity().getLocation());
            }
        }
    }
}