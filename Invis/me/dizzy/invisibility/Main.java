/*     */ package me.dizzy.invisibility;
/*     */ 
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.ProtocolManager;
/*     */ import com.comphenix.protocol.events.ConnectionSide;
/*     */ import com.comphenix.protocol.events.PacketAdapter;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.events.PacketEvent;
/*     */ import com.comphenix.protocol.events.PacketListener;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.HashSet;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class Main
/*     */   extends JavaPlugin
/*     */   implements Listener
/*     */ {
/*  42 */   ConsoleCommandSender console = getServer().getConsoleSender();
/*     */   private ProtocolManager protocolManager;
/*  44 */   private PotionEffect inv = new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 1);
/*     */   private Hashtable<UUID, Boolean> invisPlayer;
/*     */   private Hashtable<UUID, Location> prevLocation;
/*  47 */   private Plugin plugin = (Plugin)this;
/*     */   
/*     */   public void onLoad() {
/*  50 */     this.protocolManager = ProtocolLibrary.getProtocolManager();
/*     */   }
/*     */   
/*     */   public void onEnable() {
/*  54 */     this.console.sendMessage(ChatColor.GREEN + "Invisibility plugin has been enabled.");
/*  55 */     PluginManager pm = getServer().getPluginManager();
/*  56 */     pm.registerEvents(this, (Plugin)this);
/*     */     
/*  58 */     PacketAdapter.AdapterParameteters parameters = new PacketAdapter.AdapterParameteters();
/*  59 */     parameters.plugin((Plugin)this);
/*  60 */     parameters.connectionSide(ConnectionSide.CLIENT_SIDE);
/*  61 */     Set<PacketType> packets = new HashSet<>();
/*  62 */     packets.add(PacketType.Play.Client.ARM_ANIMATION);
/*  63 */     packets.add(PacketType.Play.Client.ENTITY_ACTION);
/*  64 */     parameters.types(packets);
/*  65 */     this.protocolManager.getAsynchronousManager().registerAsyncHandler(
/*  66 */         (PacketListener)new PacketAdapter(parameters)
/*     */         {
/*     */           public void onPacketReceiving(PacketEvent event)
/*     */           {
/*  70 */             int ATTACK_REACH = 4;
/*  71 */             Random rnd = new Random();
/*     */             
/*  73 */             Player observer = event.getPlayer();
/*  74 */             Location observerPos = observer.getEyeLocation();
/*  75 */             Vector3D observerDir = new Vector3D(observerPos.getDirection());
/*     */             
/*  77 */             Vector3D observerStart = new Vector3D(observerPos);
/*  78 */             Vector3D observerEnd = observerStart.add(observerDir.multiply(4));
/*     */             
/*  80 */             Player hit = null;
/*     */             
/*  82 */             for (Player target : Main.this.protocolManager.getEntityTrackers((Entity)observer)) {
/*     */               
/*  84 */               if (!observer.canSee(target)) {
/*  85 */                 Vector3D targetPos = new Vector3D(target.getLocation());
/*  86 */                 Vector3D minimum = targetPos.add(-0.5D, 0.0D, -0.5D);
/*  87 */                 Vector3D maximum = targetPos.add(0.5D, 1.67D, 0.5D);
/*     */                 
/*  89 */                 if (Main.this.hasIntersection(observerStart, observerEnd, minimum, maximum) && (
/*  90 */                   hit == null || hit.getLocation().distanceSquared(observerPos) > target.getLocation().distanceSquared(observerPos))) {
/*  91 */                   hit = target;
/*     */                 }
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*  98 */             if (hit != null) {
/*  99 */               PacketContainer useEntity = Main.this.protocolManager.createPacket(PacketType.Play.Client.USE_ENTITY, false);
/* 100 */               useEntity.getIntegers().write(0, Integer.valueOf(hit.getEntityId()));
/* 101 */               useEntity.getEntityUseActions().write(0, EnumWrappers.EntityUseAction.ATTACK);
/*     */               
/* 103 */               for (Player other : Bukkit.getOnlinePlayers()) {
/* 104 */                 if (!observer.canSee(hit)) {
/* 105 */                   other.showPlayer(this.plugin, hit);
/*     */                 }
/*     */               } 
/*     */               
/*     */               try {
/* 110 */                 Main.this.protocolManager.recieveClientPacket(event.getPlayer(), useEntity);
/* 111 */               } catch (Exception e) {
/* 112 */                 e.printStackTrace();
/*     */               }
/*     */             
/*     */             } 
/*     */           }
/* 117 */         }).syncStart();
/*     */     
/* 119 */     this.invisPlayer = new Hashtable<>();
/* 120 */     this.prevLocation = new Hashtable<>();
/* 121 */     for (Player p : getServer().getOnlinePlayers()) {
/* 122 */       this.invisPlayer.put(p.getUniqueId(), Boolean.valueOf(false));
/* 123 */       this.prevLocation.put(p.getUniqueId(), p.getLocation());
/*     */     } 
/*     */     
/* 126 */     BukkitScheduler scheduler = getServer().getScheduler();
/* 127 */     scheduler.scheduleSyncRepeatingTask((Plugin)this, new Runnable()
/*     */         {
/*     */           public void run() {
/* 130 */             for (Player p : Bukkit.getOnlinePlayers()) {
/*     */               
/* 132 */               Location from = (Location)Main.this.prevLocation.get(p.getUniqueId());
/* 133 */               Location to = p.getLocation();
/* 134 */               if (((Boolean)Main.this.invisPlayer.get(p.getUniqueId())).booleanValue())
/*     */               {
/* 136 */                 if (p.isSneaking()) {
/* 137 */                   for (Player other : Bukkit.getOnlinePlayers()) {
/* 138 */                     if (other.canSee(p)) {
/* 139 */                       other.hidePlayer(Main.this.plugin, p);
/*     */                     }
/* 141 */                     if (!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
/* 142 */                       p.addPotionEffect(Main.this.inv);
/*     */                     }
/*     */                   } 
/*     */                 } else {
/* 146 */                   for (Player other : Bukkit.getOnlinePlayers()) {
/* 147 */                     if (!other.canSee(p)) {
/* 148 */                       other.showPlayer(Main.this.plugin, p);
/*     */                     }
/* 150 */                     if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
/* 151 */                       p.removePotionEffect(PotionEffectType.INVISIBILITY);
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               }
/*     */               
/* 157 */               Main.this.prevLocation.replace(p.getUniqueId(), p.getLocation());
/*     */             } 
/*     */           }
/* 160 */         }0L, 13L);
/*     */   }
/*     */   
/*     */   private boolean hasIntersection(Vector3D p1, Vector3D p2, Vector3D min, Vector3D max) {
/* 164 */     double epsilon = 9.999999747378752E-5D;
/*     */     
/* 166 */     Vector3D d = p2.subtract(p1).multiply(0.5D);
/* 167 */     Vector3D e = max.subtract(min).multiply(0.5D);
/* 168 */     Vector3D c = p1.add(d).subtract(min.add(max).multiply(0.5D));
/* 169 */     Vector3D ad = d.abs();
/*     */     
/* 171 */     if (Math.abs(c.x) > e.x + ad.x)
/* 172 */       return false; 
/* 173 */     if (Math.abs(c.y) > e.y + ad.y)
/* 174 */       return false; 
/* 175 */     if (Math.abs(c.z) > e.z + ad.z) {
/* 176 */       return false;
/*     */     }
/* 178 */     if (Math.abs(d.y * c.z - d.z * c.y) > e.y * ad.z + e.z * ad.y + 9.999999747378752E-5D)
/* 179 */       return false; 
/* 180 */     if (Math.abs(d.z * c.x - d.x * c.z) > e.z * ad.x + e.x * ad.z + 9.999999747378752E-5D)
/* 181 */       return false; 
/* 182 */     if (Math.abs(d.x * c.y - d.y * c.x) > e.x * ad.y + e.y * ad.x + 9.999999747378752E-5D) {
/* 183 */       return false;
/*     */     }
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   private void toggleVisibilityNative(Player observer, Player target) {
/* 189 */     if (observer.canSee(target)) {
/* 190 */       observer.hidePlayer((Plugin)this, target);
/*     */     } else {
/*     */       
/* 193 */       observer.showPlayer((Plugin)this, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
/* 199 */     Player target = null;
/*     */     
/* 201 */     if (!(sender instanceof Player)) {
/* 202 */       sender.sendMessage(ChatColor.RED + "This command can only be used by a player.");
/* 203 */       return true;
/*     */     } 
/*     */     
/* 206 */     if (command.getName().equalsIgnoreCase("add") || command.getName().equalsIgnoreCase("remove")) {
/* 207 */       if (args.length == 1) {
/* 208 */         target = getServer().getPlayerExact(args[0]);
/*     */       } else {
/* 210 */         sender.sendMessage(ChatColor.RED + "This command only accepts one argument.");
/* 211 */         return true;
/*     */       } 
/* 213 */       if (command.getName().equalsIgnoreCase("add")) {
/* 214 */         this.invisPlayer.replace(target.getUniqueId(), Boolean.valueOf(true));
/* 215 */         sender.sendMessage(ChatColor.GREEN + "added " + target.getName());
/* 216 */       } else if (command.getName().equalsIgnoreCase("remove")) {
/* 217 */         this.invisPlayer.replace(target.getUniqueId(), Boolean.valueOf(false));
/* 218 */         sender.sendMessage(ChatColor.GREEN + "removed " + target.getName());
/*     */       } 
/*     */     } else {
/* 221 */       sender.sendMessage(ChatColor.RED + command.toString() + " is an invalid command.");
/* 222 */       return true;
/*     */     } 
/* 224 */     return false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJoin(PlayerJoinEvent event) {
/* 229 */     if (event.getPlayer().hasPotionEffect(PotionEffectType.INVISIBILITY)) {
/* 230 */       event.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
/*     */     }
/* 232 */     this.invisPlayer.put(event.getPlayer().getUniqueId(), Boolean.valueOf(false));
/* 233 */     this.prevLocation.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocation());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent event) {
/* 238 */     this.invisPlayer.remove(event.getPlayer().getUniqueId());
/* 239 */     this.prevLocation.remove(event.getPlayer().getUniqueId());
/*     */   }
/*     */   
/*     */   private static double round(double value, int places) {
/* 243 */     if (places < 0) throw new IllegalArgumentException();
/*     */     
/* 245 */     BigDecimal bd = new BigDecimal(Double.toString(value));
/* 246 */     bd = bd.setScale(places, RoundingMode.HALF_UP);
/* 247 */     return bd.doubleValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\jason\Documents\GitHub\Spigot-Plugins\Invis\Invis.jar!\me\dizzy\invisibility\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */