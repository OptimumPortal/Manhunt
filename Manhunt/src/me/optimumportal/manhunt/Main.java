package me.optimumportal.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	Player speedrunner = null;
	
	// COMMAND
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("manhunt")) {
			if (!(sender.hasPermission("manhunt.admin"))) return false;
			if (args[0].equalsIgnoreCase("speedrunner")) {
					Player player = Bukkit.getPlayer(args[1]);
					if (player == null) return false;
					speedrunner = player;
			}
		}
		return true;
	}
	
	// HUNTER WIN
	public void onDeath(PlayerDeathEvent event) {
		if (event.getEntity() != speedrunner) return;
		for (Player players : Bukkit.getOnlinePlayers()) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "Hunters win!");
			players.sendTitle(ChatColor.AQUA + "" + ChatColor.BOLD + "Hunters win! The manhunt has now concluded.", ChatColor.GREEN + speedrunner.getName() + ChatColor.RED + " died", 0, 100, 20);
		}
	}
	
	// SPEEDRUNNER WIN
	public void onAdvancement(PlayerAdvancementDoneEvent event) {
		if (event.getPlayer() != speedrunner) return;
		if (event.getAdvancement().getKey().getKey() != "end/kill_dragon") return;
		Bukkit.broadcastMessage(ChatColor.GOLD + "Speedrunner win! The manhunt has now concluded.");
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.sendTitle(ChatColor.GOLD + "" + ChatColor.BOLD + "Speedrunner wins!", ChatColor.GREEN + speedrunner.getName() + ChatColor.RED + " killed the" + ChatColor.MAGIC + "" + ChatColor.BOLD + " Ender Dragon", 0, 100, 20);
		}
	}
	
	// COMPASS
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.getPlayer() != speedrunner) return;
		Location loc = event.getPlayer().getLocation();
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.setCompassTarget(loc);
		}
	}
}
