/*     */ package me.dizzy.fireworks;
/*     */ 
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*     */   boolean move = false;
/*     */   boolean attack = false;
/*     */   boolean kill = false;
/*     */   boolean blockBreak = false;
/*  35 */   ConsoleCommandSender console = getServer().getConsoleSender();
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  39 */     getServer().getPluginManager().registerEvents(this, (Plugin)this);
/*  40 */     this.console.sendMessage(ChatColor.GREEN + "Fireworks pugin is enabled");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/*  46 */     if (!(sender instanceof Player)) {
/*  47 */       this.console.sendMessage("Only a player may use this command");
/*  48 */       return true;
/*     */     } 
/*     */     
/*  51 */     if (command.getName().equalsIgnoreCase("move")) {
/*  52 */       this.move = !this.move;
/*     */     }
/*  54 */     else if (command.getName().equalsIgnoreCase("attack")) {
/*  55 */       this.attack = !this.attack;
/*     */     }
/*  57 */     else if (command.getName().equalsIgnoreCase("kill")) {
/*  58 */       this.kill = !this.kill;
/*     */     }
/*  60 */     else if (command.getName().equalsIgnoreCase("blockbreak")) {
/*  61 */       this.blockBreak = !this.blockBreak;
/*     */     }
/*  63 */     else if (command.getName().equalsIgnoreCase("toggleOn")) {
/*  64 */       this.move = true;
/*  65 */       this.attack = true;
/*  66 */       this.kill = true;
/*  67 */       this.blockBreak = true;
/*     */     }
/*  69 */     else if (command.getName().equalsIgnoreCase("toggleOff")) {
/*  70 */       this.move = false;
/*  71 */       this.attack = false;
/*  72 */       this.kill = false;
/*  73 */       this.blockBreak = false;
/*     */     } 
/*     */     
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerAttack(EntityDamageByEntityEvent event) {
/*  81 */     if (this.attack && 
/*  82 */       event.getDamager() instanceof Player) {
/*  83 */       Location entityLoc = event.getEntity().getLocation();
/*  84 */       entityLoc.setY(entityLoc.getY() + 3.0D);
/*  85 */       Fireworks.spawnFireworks((Player)event.getDamager(), entityLoc);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onDeath(EntityDeathEvent event) throws InterruptedException {
/*  92 */     if (this.kill && 
/*  93 */       event.getEntity().getKiller() instanceof Player) {
/*  94 */       Fireworks.spawnFireworks(event.getEntity().getKiller(), event.getEntity().getLocation());
/*  95 */       if (event.getEntity().getType() == EntityType.ENDER_DRAGON) {
/*  96 */         BukkitTask bukkitTask = (new enderKillFireworks(this, 5, event.getEntity().getKiller(), event.getEntity().getLocation())).runTaskTimer((Plugin)this, 20L, 100L);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerMove(PlayerMoveEvent event) {
/* 104 */     if (this.move) {
/* 105 */       if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())
/* 106 */         return;  Fireworks.spawnFireworks(event.getPlayer(), event.getPlayer().getLocation());
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockBreak(BlockBreakEvent event) {
/* 112 */     if (this.blockBreak) {
/* 113 */       Player p = event.getPlayer();
/* 114 */       Block b = event.getBlock();
/*     */       
/* 116 */       Fireworks.spawnFireworks(p, b.getLocation());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\jason\Documents\GitHub\Spigot-Plugins\FireworksPlugin\FireworksPlugin.jar!\me\dizzy\fireworks\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */