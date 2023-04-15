package me.dizzy.jumpdeath;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	ConsoleCommandSender console = this.getServer().getConsoleSender();
	boolean started = false;
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String labels, String[] args) {
		if(!(sender instanceof Player)) {
			console.sendMessage("Only a player may use this command");
			return false;
		}
		
		if(cmd.getName().equalsIgnoreCase("start")) {
			started = true;
			console.sendMessage("jump death started");
		}
		else if(cmd.getName().equalsIgnoreCase("stop")) {
			started = false;
			console.sendMessage("jump death stopped");
		}
		return true;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Entity p = (Entity) event.getPlayer();
		if(started && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
			if(p.getVelocity().getY() >= 0 && !p.isOnGround()) {
				if(!event.getPlayer().isSwimming() && !event.getPlayer().isFlying()) {
					if(!event.getFrom().getBlock().getType().equals(Material.WATER)) {
						event.getPlayer().setHealth(0);
					}
				}
			}
		}
	}
}
