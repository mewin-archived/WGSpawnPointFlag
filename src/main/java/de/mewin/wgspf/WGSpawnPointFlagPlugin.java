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

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author mewin<mewin001@hotmail.de>
 */
public class WGSpawnPointFlagPlugin extends JavaPlugin
{
    private WorldGuardPlugin wgPlugin;
    private RespawnListener listener;
    
    @Override
    public void onEnable()
    {
        wgPlugin = getWorldGuard();
        
        if (wgPlugin == null) {
            getLogger().warning("This plugin requires WorldGuard, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        if (getServer().getPluginManager().getPlugin("WGCustomFlags") == null)
        {
            getLogger().warning("This plugin requires WorldGuard Custom Flags, disabling.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        listener = new RespawnListener(wgPlugin);
        getServer().getPluginManager().registerEvents(listener, this);
    }
    
    private WorldGuardPlugin getWorldGuard()
    {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }
        
        return (WorldGuardPlugin) plugin;
    }
}