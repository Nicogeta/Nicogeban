package com.Nicogeta.Nicogeban;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class NicogebanPlayerListener extends PlayerListener {
	final Nicogeban plugin;

	public NicogebanPlayerListener(Nicogeban instance) {
		plugin = instance;
	}

	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String playername = event.getPlayer().getName();

		boolean isExempt = false;
		if((Nicogeban.Permissions == null && player.isOp()))
			isExempt = true;
		else if(Nicogeban.Permissions != null && Nicogeban.Permissions.has(player, "Nicogeban.exempt"))
			isExempt = true;

		if(!isExempt) {

			if (!playername.matches("[A-Za-z_0-9]+")){
				player.kickPlayer("bye bye :D");
			} else if (playername.length() < plugin.theNickLength) {
				player.kickPlayer("bye bye :D");
			}

		}

	}

}
