/*    */ package me.dizzy.fireworks;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.FireworkEffect;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Firework;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.meta.FireworkMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Fireworks
/*    */ {
/*    */   public static void spawnFireworks(Player p, Location loc) {
/* 17 */     Firework fw = (Firework)p.getWorld().spawnEntity(loc, EntityType.FIREWORK);
/* 18 */     FireworkMeta fwm = fw.getFireworkMeta();
/*    */     
/* 20 */     Random rnd = new Random();
/*    */     
/* 22 */     int rt = rnd.nextInt(5) + 1;
/* 23 */     FireworkEffect.Type type = FireworkEffect.Type.BALL;
/* 24 */     if (rt == 1) type = FireworkEffect.Type.BALL; 
/* 25 */     if (rt == 2) type = FireworkEffect.Type.BALL_LARGE; 
/* 26 */     if (rt == 3) type = FireworkEffect.Type.BURST; 
/* 27 */     if (rt == 4) type = FireworkEffect.Type.CREEPER; 
/* 28 */     if (rt == 5) type = FireworkEffect.Type.STAR;
/*    */     
/* 30 */     int r1i = rnd.nextInt(17) + 1;
/* 31 */     int r2i = rnd.nextInt(17) + 1;
/* 32 */     Color c1 = getColor(r1i);
/* 33 */     Color c2 = getColor(r2i);
/*    */     
/* 35 */     FireworkEffect effect = FireworkEffect.builder().flicker(rnd.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(rnd.nextBoolean()).build();
/* 36 */     fwm.addEffect(effect);
/* 37 */     int rp = rnd.nextInt(2) + 1;
/* 38 */     fwm.setPower(rp);
/*    */     
/* 40 */     fw.setFireworkMeta(fwm);
/*    */   }
/*    */   
/*    */   public static Color getColor(int i) {
/* 44 */     Color c = null;
/* 45 */     if (i == 1) {
/* 46 */       c = Color.AQUA;
/*    */     }
/* 48 */     if (i == 2) {
/* 49 */       c = Color.BLACK;
/*    */     }
/* 51 */     if (i == 3) {
/* 52 */       c = Color.BLUE;
/*    */     }
/* 54 */     if (i == 4) {
/* 55 */       c = Color.FUCHSIA;
/*    */     }
/* 57 */     if (i == 5) {
/* 58 */       c = Color.GRAY;
/*    */     }
/* 60 */     if (i == 6) {
/* 61 */       c = Color.GREEN;
/*    */     }
/* 63 */     if (i == 7) {
/* 64 */       c = Color.LIME;
/*    */     }
/* 66 */     if (i == 8) {
/* 67 */       c = Color.MAROON;
/*    */     }
/* 69 */     if (i == 9) {
/* 70 */       c = Color.NAVY;
/*    */     }
/* 72 */     if (i == 10) {
/* 73 */       c = Color.OLIVE;
/*    */     }
/* 75 */     if (i == 11) {
/* 76 */       c = Color.ORANGE;
/*    */     }
/* 78 */     if (i == 12) {
/* 79 */       c = Color.PURPLE;
/*    */     }
/* 81 */     if (i == 13) {
/* 82 */       c = Color.RED;
/*    */     }
/* 84 */     if (i == 14) {
/* 85 */       c = Color.SILVER;
/*    */     }
/* 87 */     if (i == 15) {
/* 88 */       c = Color.TEAL;
/*    */     }
/* 90 */     if (i == 16) {
/* 91 */       c = Color.WHITE;
/*    */     }
/* 93 */     if (i == 17) {
/* 94 */       c = Color.YELLOW;
/*    */     }
/* 96 */     return c;
/*    */   }
/*    */ }


/* Location:              C:\Users\jason\Documents\GitHub\Spigot-Plugins\FireworksPlugin\FireworksPlugin.jar!\me\dizzy\fireworks\Fireworks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */