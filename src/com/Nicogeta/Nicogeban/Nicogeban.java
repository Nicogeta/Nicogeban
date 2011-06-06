package com.Nicogeta.Nicogeban;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Nicogeban extends JavaPlugin {
	
	private static final Logger log = Logger.getLogger("Minecraft");
	public static PermissionHandler Permissions;
	private final NicogebanPlayerListener playerListener = new NicogebanPlayerListener(this);
	
	 private void setupPermissions() {
	      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

	      if (Nicogeban.Permissions == null) 
	      {
	          if (test != null) {
	              Nicogeban.Permissions = ((Permissions)test).getHandler();
	              log.info("Nicogeban has detected Permissions!");
	          } else {
	             log.info("Nicogeban has not detected Permissions.");
	          }
	      }
	  }
	 
	 public void onDisable() {
		 log.info("Nicogeban DISABLE");
	 }
	 
	 @Override
	 public void onEnable() {
		 log.info("Nicogeban ENABLE (by Nicogeta)");
		 log.info("Nicogeban ver 0.1");
		 PluginManager pm = getServer().getPluginManager();
		 pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.High, this);
		 setupPermissions();
	 }
	 
		public void recordEvent(PlayerLoginEvent event) {
			// TODO Auto-generated method stub
			
		}

}
