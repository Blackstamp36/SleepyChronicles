//package org.blackstamp.sleepychronicles.game.mobs.custom.vanilla;
//
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.monster.creaking.Creaking;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
//import org.blackstamp.sleepychronicles.api.item.SleepyItems;
//import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
//import org.blackstamp.sleepychronicles.game.LootEntry;
//import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyDamageable;
//import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyLootable;
//import org.bukkit.Material;
//import org.bukkit.Tag;
//import org.bukkit.entity.Player;
//import org.bukkit.event.entity.EntityDamageByEntityEvent;
//import org.bukkit.event.entity.EntityDamageEvent;
//import org.bukkit.inventory.ItemStack;
//
//public class BobCreaking extends SleepyMob {
//
//    // todo:
//    // Make the other (and this one) mobs spawn, just SPAWN with their attributes. Worry about events later.
//    // Then, run the server to see what errors are still on the chase.
//
//    private static final double ATTACK_DAMAGE = 100D;
//
//    public BobCreaking(Level level) {
//        super(new BobCreakingEntity(level),level,"Bob", SleepyPalette.BOB.getColor1());
//
//        setDamage(ATTACK_DAMAGE);
//    }
//
//    private static class BobCreakingEntity extends Creaking implements SleepyLootable, SleepyDamageable {
//
//        public BobCreakingEntity(Level level){ super(EntityType.CREAKING,level); }
//
//        @Override
//        public ItemStack getDrop(){ return new LootEntry(SleepyItems.BOB_FLESH.build(),1,3,0.05D).build(); }
//
//        @Override
//        public void handleDamage(EntityDamageByEntityEvent e){
//            if(!(e.getDamager() instanceof Player p)) return;
//
//            EntityDamageEvent.DamageCause cause = e.getCause();
//            Material main = p.getInventory().getItemInMainHand().getType();
//
//            if(!cause.name().endsWith("_ATTACK")) e.setCancelled(true);
//            if(!Tag.ITEMS_AXES.isTagged(main)) e.setCancelled(true);
//        }
//    }
//}