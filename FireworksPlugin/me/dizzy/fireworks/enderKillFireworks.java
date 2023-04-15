/*    */ package me.dizzy.fireworks;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ public class enderKillFireworks
/*    */   extends BukkitRunnable {
/*    */   private final JavaPlugin plugin;
/*    */   private int counter;
/*    */   Player killer;
/*    */   Location enderLoc;
/*    */   
/*    */   public enderKillFireworks(JavaPlugin plugin, int counter, Player killer, Location enderLoc) {
/* 17 */     this.plugin = plugin;
/* 18 */     this.counter = counter;
/* 19 */     this.killer = killer;
/* 20 */     this.enderLoc = enderLoc;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 25 */     if (this.counter <= 0) {
/* 26 */       cancel();
/*    */     }
/*    */     
/* 29 */     Location fwLoc = this.enderLoc.clone();
/* 30 */     Random rnd = new Random();
/* 31 */     for (int i = 0; i < 19; i++) {
/* 32 */       fwLoc.setX(this.enderLoc.getX() + (rnd.nextInt(10) - 5));
/* 33 */       fwLoc.setZ(this.enderLoc.getZ() + (rnd.nextInt(10) - 5));
/* 34 */       Fireworks.spawnFireworks(this.killer.getPlayer(), fwLoc);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jason\Documents\GitHub\Spigot-Plugins\FireworksPlugin\FireworksPlugin.jar!\me\dizzy\fireworks\enderKillFireworks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */