/*    */ package me.dizzy.invisibility;
/*    */ 
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Vector3D
/*    */ {
/* 10 */   public static final Vector3D ORIGIN = new Vector3D(0.0D, 0.0D, 0.0D);
/*    */   
/*    */   public final double x;
/*    */   
/*    */   public Vector3D(double x, double y, double z) {
/* 15 */     this.x = x;
/* 16 */     this.y = y;
/* 17 */     this.z = z;
/*    */   }
/*    */   public final double y; public final double z;
/*    */   public Vector3D(Location location) {
/* 21 */     this(location.toVector());
/*    */   }
/*    */   
/*    */   public Vector3D(Vector vector) {
/* 25 */     if (vector == null) {
/* 26 */       throw new IllegalArgumentException("Vector cannot be NULL.");
/*    */     }
/* 28 */     this.x = vector.getX();
/* 29 */     this.y = vector.getY();
/* 30 */     this.z = vector.getZ();
/*    */   }
/*    */   
/*    */   public Vector toVector() {
/* 34 */     return new Vector(this.x, this.y, this.z);
/*    */   }
/*    */   
/*    */   public Vector3D add(Vector3D other) {
/* 38 */     if (other == null) {
/* 39 */       throw new IllegalArgumentException("other cannot be NULL.");
/*    */     }
/* 41 */     return new Vector3D(this.x + other.x, this.y + other.y, this.z + other.z);
/*    */   }
/*    */   
/*    */   public Vector3D add(double x, double y, double z) {
/* 45 */     return new Vector3D(this.x + x, this.y + y, this.z + z);
/*    */   }
/*    */   
/*    */   public Vector3D subtract(Vector3D other) {
/* 49 */     if (other == null) {
/* 50 */       throw new IllegalArgumentException("other cannot be NULL.");
/*    */     }
/* 52 */     return new Vector3D(this.x - other.x, this.y - other.y, this.z - other.z);
/*    */   }
/*    */   
/*    */   public Vector3D subtract(double x, double y, double z) {
/* 56 */     return new Vector3D(this.x - x, this.y - y, this.z - x);
/*    */   }
/*    */   
/*    */   public Vector3D multiply(int factor) {
/* 60 */     return new Vector3D(this.x * factor, this.y * factor, this.z * factor);
/*    */   }
/*    */   
/*    */   public Vector3D multiply(double factor) {
/* 64 */     return new Vector3D(this.x * factor, this.y * factor, this.z * factor);
/*    */   }
/*    */   
/*    */   public Vector3D divide(int divisor) {
/* 68 */     if (divisor == 0)
/* 69 */       throw new IllegalArgumentException("Cannot divide by NULL."); 
/* 70 */     return new Vector3D(this.x / divisor, this.y / divisor, this.z / divisor);
/*    */   }
/*    */   
/*    */   public Vector3D divide(double divisor) {
/* 74 */     if (divisor == 0.0D)
/* 75 */       throw new IllegalArgumentException("Cannot divide by NULL."); 
/* 76 */     return new Vector3D(this.x / divisor, this.y / divisor, this.z / divisor);
/*    */   }
/*    */   
/*    */   public Vector3D abs() {
/* 80 */     return new Vector3D(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
/*    */   }
/*    */   
/*    */   public String toString() {
/* 84 */     return String.format("[x: %s, y: %s, z: %s]", new Object[] { Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.z) });
/*    */   }
/*    */ }


/* Location:              C:\Users\jason\Documents\GitHub\Spigot-Plugins\Invis\Invis.jar!\me\dizzy\invisibility\Vector3D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */