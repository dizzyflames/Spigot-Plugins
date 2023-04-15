package me.dizzy.mobrandomizer;

import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public class Main extends JavaPlugin implements Listener {
	ConsoleCommandSender console = this.getServer().getConsoleSender();
	Random rnd = new Random();
	Hashtable<UUID, Integer> playerDelay = new Hashtable<UUID, Integer>();
	Hashtable<UUID, Boolean> playerAdded = new Hashtable<UUID, Boolean>();
	
	int currentTimer = 0;
	int minDelay = 30; // 30 seconds
	int maxDelay = 300; // 5 minutes
	int mobMin = 1;
	int mobMax = 10;
	int delay = rnd.nextInt(maxDelay - minDelay) + minDelay;
    
	Vector<EntityType> mobs = new Vector<EntityType>();
	
	// called when plugin is enabled to set up the runnable
	public void onEnable() {
		console.sendMessage(ChatColor.GREEN + "Mob Randomizer plugin has been enabled.");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		mobs.add(EntityType.SILVERFISH);
		mobs.add(EntityType.ZOMBIE);
		mobs.add(EntityType.SKELETON);
		mobs.add(EntityType.CREEPER);
		mobs.add(EntityType.PIGLIN);
		mobs.add(EntityType.SPIDER);
		mobs.add(EntityType.CAVE_SPIDER);
		mobs.add(EntityType.SLIME);
		mobs.add(EntityType.RAVAGER);
		mobs.add(EntityType.VEX);
		mobs.add(EntityType.GHAST);
		mobs.add(EntityType.HOGLIN);
		mobs.add(EntityType.WITHER_SKELETON);
		mobs.add(EntityType.BLAZE);
		mobs.add(EntityType.ENDERMAN);
		mobs.add(EntityType.COW);
		mobs.add(EntityType.SHEEP);
		mobs.add(EntityType.PIG);
		mobs.add(EntityType.VILLAGER);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			playerAdded.put(p.getUniqueId(), false);
		}
	
		BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	
            	currentTimer += 1;
            	for(Player p : Bukkit.getOnlinePlayers()) {
            		
            		if(playerAdded.get(p.getUniqueId()) != null && playerAdded.get(p.getUniqueId())) {
            			if(currentTimer == playerDelay.get(p.getUniqueId())) {
            				spawnMob(p, rnd.nextInt(mobMax - mobMin) + mobMin, mobs.get(rnd.nextInt((mobs.size() - 1))));
            				
            				playerDelay.put(p.getUniqueId(), currentTimer + delay);
            				
                        	//delay = rnd.nextInt(maxDelay - minDelay) + minDelay;
            			}
            		}
            	}
            	
            	delay = rnd.nextInt(maxDelay - minDelay) + minDelay;
            }
        }, 0L, 20L);
        
	}
	
	// function accepts commands for the plugin
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Commands can only be used by a player");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("addall")) {
			for(Player player : getServer().getOnlinePlayers()) {
				playerAdded.replace(player.getUniqueId(), true);
				playerDelay.put(player.getUniqueId(), currentTimer + delay);
			}
			console.sendMessage("Added all players");
			
		}
		else if(cmd.getName().equalsIgnoreCase("removeall")) {
			for(Player player : getServer().getOnlinePlayers()) {
				playerAdded.replace(player.getUniqueId(), false);
				playerDelay.remove(player.getUniqueId());
			}
			console.sendMessage("removed all players");
			
		}
		else if(cmd.getName().equalsIgnoreCase("add")) {
			playerAdded.replace(getServer().getPlayerExact(args[0]).getUniqueId(), true);
			playerDelay.put(p.getUniqueId(), currentTimer + delay);
			console.sendMessage("Added player " + getServer().getPlayerExact(args[0]).getName());
		} 
		else if(cmd.getName().equalsIgnoreCase("remove")) {
			playerAdded.replace(getServer().getPlayerExact(args[0]).getUniqueId(), false);
			playerDelay.remove(p.getUniqueId());
			console.sendMessage("Removed player " + getServer().getPlayerExact(args[0]).getName());
		}
		else if(cmd.getName().equalsIgnoreCase("min")) {
			if(Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[0]) >= maxDelay) {
				console.sendMessage("Minimum time range cannot be less than or equal to 0 or greater than or equal to maximum time range");
				return true;
			}
			minDelay = Integer.parseInt(args[0]);
			console.sendMessage("The minimum range of mob spawn has been changed to " + minDelay + " seconds");
		}
		else if(cmd.getName().equalsIgnoreCase("max")) {
			if (Integer.parseInt(args[0]) <= minDelay) {
				console.sendMessage("Maximum time range cannot be less than or equal to minimum time range.");
				return true;
			}
			maxDelay = Integer.parseInt(args[0]);
			console.sendMessage("The maximum range of mob spawn has been changed to " + maxDelay + " seconds");
		}
		else if(cmd.getName().equalsIgnoreCase("mobmin")) {
			if(Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[0]) >= mobMax) {
				console.sendMessage("Minimum mob count cannot less than or equal to 0 or be greater than maximum mob count");
				return true;
			}
			mobMin = Integer.parseInt(args[0]);
			console.sendMessage("Minimum mob count has been changed to " + mobMin);
		}
		else if(cmd.getName().equalsIgnoreCase("mobmax")) {
			if (Integer.parseInt(args[0]) <= mobMin) {
				console.sendMessage("Maximum mob count cannot be less than or equal to minimum mob count.");
				return true;
			}
			mobMax = Integer.parseInt(args[0]);
			console.sendMessage("The maximum mob count has been changed to " + mobMax);
		}
		else {
			sender.sendMessage(cmd.getName() + " is not a valid command");
			return true;
		}
		
		return false;
	}
	
	// accepts the player, number of mobs, and mob type
	// spawn mobs at the players location and returns nothing
	public void spawnMob(Player player, int mobNum, EntityType mob) {
		Location mobLocation = player.getLocation();
		
		for(int i = 0; i < mobNum; i++) {
			mobLocation.setX(mobLocation.getX() + rnd.nextInt(3 - 1) + 1);
			mobLocation.setZ(mobLocation.getZ() + rnd.nextInt(3 - 1) + 1);
			player.getWorld().spawnEntity(mobLocation, mob);
			mobLocation = player.getLocation();
		}
	}
	
	// event handler for players joining that defaults false value to table of players
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		playerAdded.put(event.getPlayer().getUniqueId(), false);	
	}
	
	// event handler for players quitting that removes them from the table of players
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		playerAdded.remove(event.getPlayer().getUniqueId());
	}
}
