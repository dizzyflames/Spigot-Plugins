/*     */ package me.dizzy.floorislava;
/*     */ 
/*     */ import java.util.Hashtable;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */   implements CommandExecutor, Listener {
/*  19 */   ConsoleCommandSender console = getServer().getConsoleSender();
/*     */ 
/*     */   
/*     */   private Hashtable<String, Block> steppedOn;
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  27 */     getServer().getPluginManager().registerEvents(this, (Plugin)this);
/*  28 */     this.console.sendMessage(ChatColor.GREEN + "Floor is lava plugin enabled");
/*  29 */     this.steppedOn = new Hashtable<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  37 */     this.console.sendMessage("Floor is lava plugin disabled");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent event) {
/*  45 */     Player player = event.getPlayer();
/*  46 */     Block b1 = player.getLocation().subtract(player.getWidth() / 2.0D, 0.8D, player.getWidth() / 2.0D).getBlock();
/*  47 */     Block b2 = player.getLocation().subtract(-player.getWidth() / 2.0D, 0.8D, player.getWidth() / 2.0D).getBlock();
/*  48 */     Block b3 = player.getLocation().subtract(player.getWidth() / 2.0D, 0.8D, -player.getWidth() / 2.0D).getBlock();
/*  49 */     Block b4 = player.getLocation().subtract(-player.getWidth() / 2.0D, 0.8D, -player.getWidth() / 2.0D).getBlock();
/*     */     
/*  51 */     if ((!b1.isEmpty() || !b2.isEmpty() || !b3.isEmpty() || !b4.isEmpty()) && (
/*  52 */       b1.isEmpty() || this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b1")) && (b2.isEmpty() || this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b2")) && (b3.isEmpty() || this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b3")) && (b4.isEmpty() || this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b4")) && (
/*  53 */       b1.isEmpty() || !this.steppedOn.containsValue(b1)) && (b2.isEmpty() || !this.steppedOn.containsValue(b2)) && (b3.isEmpty() || !this.steppedOn.containsValue(b3)) && (b4.isEmpty() || !this.steppedOn.containsValue(b4))) {
/*     */       
/*  55 */       if (this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b1")) {
/*  56 */         if (!b1.isEmpty() && !((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b1")).isEmpty()) {
/*  57 */           ((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b1")).setType(Material.LAVA);
/*  58 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b1");
/*  59 */         } else if (((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b1")).isEmpty()) {
/*  60 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b1");
/*     */         } 
/*     */       }
/*     */       
/*  64 */       if (this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b2")) {
/*  65 */         if (!b2.isEmpty() && !((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b2")).isEmpty()) {
/*  66 */           ((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b2")).setType(Material.LAVA);
/*  67 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b2");
/*  68 */         } else if (((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b2")).isEmpty()) {
/*  69 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b2");
/*     */         } 
/*     */       }
/*     */       
/*  73 */       if (this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b3")) {
/*  74 */         if (!b3.isEmpty() && !((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b3")).isEmpty()) {
/*  75 */           ((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b3")).setType(Material.LAVA);
/*  76 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b3");
/*  77 */         } else if (((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b3")).isEmpty()) {
/*  78 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b3");
/*     */         } 
/*     */       }
/*     */       
/*  82 */       if (this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b4")) {
/*  83 */         if (!b4.isEmpty() && !((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b4")).isEmpty()) {
/*  84 */           ((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b4")).setType(Material.LAVA);
/*  85 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b4");
/*  86 */         } else if (((Block)this.steppedOn.get(String.valueOf(player.getUniqueId().toString()) + "b4")).isEmpty()) {
/*  87 */           this.steppedOn.remove(String.valueOf(player.getUniqueId().toString()) + "b4");
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (b1.getType() != Material.OBSIDIAN && b1.getType() != Material.END_PORTAL_FRAME && b1.getType() != Material.END_PORTAL && b1.getType() != Material.NETHER_PORTAL && b1.getType() != Material.CAULDRON && b1.getType() != Material.BEDROCK && 
/*  95 */       !b1.isEmpty() && !this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b1")) {
/*  96 */       this.steppedOn.put(String.valueOf(player.getUniqueId().toString()) + "b1", b1);
/*     */     }
/*     */     
/*  99 */     if (b2.getType() != Material.OBSIDIAN && b2.getType() != Material.END_PORTAL_FRAME && b2.getType() != Material.END_PORTAL && b2.getType() != Material.NETHER_PORTAL && b2.getType() != Material.CAULDRON && b2.getType() != Material.BEDROCK && 
/* 100 */       !b2.isEmpty() && !this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b2")) {
/* 101 */       this.steppedOn.put(String.valueOf(player.getUniqueId().toString()) + "b2", b2);
/*     */     }
/*     */     
/* 104 */     if (b3.getType() != Material.OBSIDIAN && b3.getType() != Material.END_PORTAL_FRAME && b3.getType() != Material.END_PORTAL && b3.getType() != Material.NETHER_PORTAL && b3.getType() != Material.CAULDRON && b3.getType() != Material.BEDROCK && 
/* 105 */       !b3.isEmpty() && !this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b3")) {
/* 106 */       this.steppedOn.put(String.valueOf(player.getUniqueId().toString()) + "b3", b3);
/*     */     }
/*     */     
/* 109 */     if (b4.getType() != Material.OBSIDIAN && b4.getType() != Material.END_PORTAL_FRAME && b3.getType() != Material.END_PORTAL && b3.getType() != Material.NETHER_PORTAL && b4.getType() != Material.CAULDRON && b4.getType() != Material.BEDROCK && 
/* 110 */       !b4.isEmpty() && !this.steppedOn.containsKey(String.valueOf(player.getUniqueId().toString()) + "b4"))
/* 111 */       this.steppedOn.put(String.valueOf(player.getUniqueId().toString()) + "b4", b4); 
/*     */   }
/*     */ }


/* Location:              C:\Users\jason\Documents\GitHub\Spigot-Plugins\floorislava\floorislava.jar!\me\dizzy\floorislava\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */