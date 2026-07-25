//package org.blackstamp.sleepychronicles.game.mobs.custom.vanilla.creeper;
//
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.monster.Creeper;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.api.color.SleepyPalette;
//import org.blackstamp.sleepychronicles.api.data.days.DayManager;
//import org.blackstamp.sleepychronicles.api.item.SleepyItems;
//import org.blackstamp.sleepychronicles.api.mobs.SleepyMob;
//import org.blackstamp.sleepychronicles.game.LootEntry;
//import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyAttack;
//import org.blackstamp.sleepychronicles.game.spawn.interfaces.SleepyLootable;
//import org.bukkit.entity.Player;
//import org.bukkit.event.entity.EntityDamageByEntityEvent;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
//public class NullCreeper extends SleepyMob {
//    private static final int MAX_HEALTH = 5;
//
//    public NullCreeper(Level level){
//        super(new NullCreeperEntity(level), level, "null", SleepyPalette.NULL.getColor1());
//
//        setFuse(15 - DayManager.getInstance().getDay());
//        setMaxHealth(MAX_HEALTH);
//    }
//
//    private static class NullCreeperEntity extends Creeper implements SleepyAttack, SleepyLootable {
//        private final static PotionEffect pot = new PotionEffect(PotionEffectType.UNLUCK, 20 * 30, 1,true,false);
//
//        public NullCreeperEntity(Level level){ super(EntityType.CREEPER, level); }
//
//        @Override
//        public ItemStack getDrop(){ return new LootEntry(SleepyItems.NULL_POWDER.build(),1,5,0.25D).build(); }
//
//        @Override
//        public void handleAttack(EntityDamageByEntityEvent e){
//            if(!(e.getEntity() instanceof Player p)) return;
//
//            p.addPotionEffect(pot);
//        }
//    }
//}