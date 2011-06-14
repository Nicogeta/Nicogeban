package com.Nicogeta.Nicogeban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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

	public int theNickLength;
	static String mainDirectory = "plugins/Nicogeban";
	static File ConfigCreate = new File(mainDirectory + File.separator + "config.properties");
	static Properties prop = new Properties();

	public void loadProcedure() {
		FileInputStream inFile = null;

		try {
			inFile = new FileInputStream(ConfigCreate);
			prop.load(inFile);
			inFile.close();
		} catch(FileNotFoundException e) {
			throw new RuntimeException("Config file not found.", e);
		} catch (IOException e){
			throw new RuntimeException("Failed to load config file.", e);
		}

		String theLength = prop.getProperty("minNickLength");
		try {
			theNickLength = Integer.parseInt(theLength);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Failed to parse the Length.", e);
		}
	}

	public void onEnable() {
		new File(mainDirectory).mkdir();
		if(!ConfigCreate.exists()) {
			try {
				ConfigCreate.createNewFile();
				FileOutputStream out = new FileOutputStream(ConfigCreate);
				prop.put("minNickLength", "6");
				prop.store(out, "Nicogeban config, please replace 6 by the nickname length you want");
				out.flush();
				out.close();
			} catch (IOException e) {
				throw new RuntimeException("Failed to create default config file.", e);
			}
		}
		loadProcedure();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.High, this);
		setupPermissions();
		log.info("Nicogeban ENABLE (by Nicogeta)");
		log.info("Nicogeban ver 0.2");
	}


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

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player;
		if (command.getName().equalsIgnoreCase("Nicogeban")) {
			if(sender instanceof Player) {
				player = (Player) sender;
				if ((Nicogeban.Permissions == null && player.isOp()) 
						|| (Nicogeban.Permissions != null && Nicogeban.Permissions.has(player, "Nicogeban.reload"))) {

					if(args.length == 0) {
						loadProcedure();
						player.sendMessage(ChatColor.GREEN + "INFO: Changes applied");
					} else {
						player.sendMessage(ChatColor.RED + "INFO: You have to type /Nicogeban");
					}
				} else {
					player.sendMessage("You don't have the permission to do that !");
				}
			}
		}
		return true;	
	}

	public void recordEvent(PlayerLoginEvent event) {
		// TODO Auto-generated method stub

	}

}
