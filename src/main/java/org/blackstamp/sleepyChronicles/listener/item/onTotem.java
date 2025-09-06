package org.blackstamp.sleepyChronicles.listener.item;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.ChatColor;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.blackstamp.sleepyChronicles.util.data.playerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.chatPrefix;

@Registrable
public class onTotem implements Listener {
    playerData data = new playerData();
    globalClass global = new globalClass();

    @EventHandler
    private void onTotem(EntityResurrectEvent e) {
        Random r = new Random();

        if (e.getEntity() instanceof Player p) {

            ItemStack main = p.getInventory().getItemInMainHand();
            ItemStack off = p.getInventory().getItemInOffHand();

            if(main.getType().equals(Material.TOTEM_OF_UNDYING) || off.getType().equals(Material.TOTEM_OF_UNDYING)) {
                UUID uuid = p.getUniqueId();
                if(p.hasPotionEffect(PotionEffectType.UNLUCK)) {
                    int amplifier = p.getPotionEffect(PotionEffectType.UNLUCK).getAmplifier();
                    int duration = p.getPotionEffect(PotionEffectType.UNLUCK).getDuration();
                    Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> p.addPotionEffect(new PotionEffect(PotionEffectType.UNLUCK,duration,amplifier,true,false)), 1);

                }


                Bukkit.getOnlinePlayers().forEach(all -> all.playSound(all.getLocation(), Sound.ENTITY_GUARDIAN_DEATH, 1F, 0.75F));

                if(main.hasItemMeta()){
                    ItemMeta mainMeta = main.getItemMeta();
                    if(mainMeta.hasCustomModelDataComponent()){
                        CustomModelDataComponent mainData = mainMeta.getCustomModelDataComponent();
                        if(mainData.getStrings().contains("wooden_totem")){
                            showWoodenTotem(p,e);
                            return;
                        }
                    }

                }

                if(off.hasItemMeta()){
                    ItemMeta offMeta = off.getItemMeta();
                    if(offMeta.hasCustomModelDataComponent()){
                        CustomModelDataComponent offData = offMeta.getCustomModelDataComponent();
                        if(offData.getStrings().contains("wooden_totem")){
                            showWoodenTotem(p,e);
                            return;
                        }
                    }
                }

                int currentTotems = global.getTotems(uuid);

                if (currentTotems <= 3) {
                    totemDebuff(p, 0, e);

                } else if (currentTotems <= 7) {
                    int randomTotems = r.nextInt(0, 2);
                    totemDebuff(p, randomTotems, e);

                } else if (currentTotems > 7) {
                    int randomTotems = r.nextInt(1, 4);
                    totemDebuff(p, randomTotems, e);
                    global.removeTotemInitialEffects(p);
                    Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () ->
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600,2)), 1);

                } else if (currentTotems >= 30) {
                    p.sendMessage(chatPrefix + "§cYour totem broke!");
                    e.setCancelled(true);
                }

            }
        }
    }

    private void totemDebuff(Player p, int totemsToRemove, EntityResurrectEvent e) {
        Inventory inv = p.getInventory();
        UUID uuid = p.getUniqueId();
        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);

        if (totemsToRemove != 0) {
            if (inv.contains(totem, totemsToRemove)) {
                for (int i = 1; i <= totemsToRemove; i++) {
                    inv.removeItem(totem);
                }

                p.sendMessage(chatPrefix + "§cYou've consumpt §f" + totemsToRemove + "§c EXTRA totems as a debuff!");
            } else {
                p.sendMessage(chatPrefix + "§cYou didn't had the required totems in your inventory! As consequence you died.");
                e.setCancelled(true);
                return;
            }

        }

        global.updateTotems(uuid, totemsToRemove + 1);

        HashMap<String, String> attackSources = getAttackSources();
        String totemCause = e.getEntity().getLastDamageCause().getCause().toString().toUpperCase();
        Entity directEntity = e.getEntity().getLastDamageCause().getDamageSource().getDirectEntity();
        Entity originEntity;
        String finalCause;

        if (attackSources.containsKey(totemCause)) {
            switch (directEntity) {
                case Projectile ignored -> {
                        originEntity = e.getEntity().getLastDamageCause().getDamageSource().getCausingEntity();
                    finalCause = attackSources.get(totemCause).concat(directEntity.getName() + " from " + originEntity.getName());
                }

                case Entity ignored -> finalCause = attackSources.get(totemCause).concat(directEntity.getName());
                case null -> finalCause = attackSources.get(totemCause);
            }

            Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(
                    chatPrefix + "§f" + p.getName() + " has consumpt a §ctotem of undying§f! \n" +
                            "§8(" + "Nª" + global.getTotems(uuid) + ". Cause: §7" + finalCause + "§8)"));

            global.showTotemUse(p, global.getTotems(p.getUniqueId()), "Totem of Undying", finalCause);
        }


    }

    private void showWoodenTotem(Player p, EntityResurrectEvent e) {
        HashMap<String, String> attackSources = getAttackSources();
        String totemCause = e.getEntity().getLastDamageCause().getCause().toString().toUpperCase();
        Entity directEntity = e.getEntity().getLastDamageCause().getDamageSource().getDirectEntity();
        Entity originEntity;
        String finalCause;

        if (attackSources.containsKey(totemCause)) {
            switch (directEntity) {
                case Projectile ignored -> {
                    originEntity = e.getEntity().getLastDamageCause().getDamageSource().getCausingEntity();
                    finalCause = attackSources.get(totemCause).concat(directEntity.getName() + " from " + originEntity.getName());
                }

                case Entity ignored -> finalCause = attackSources.get(totemCause).concat(directEntity.getName());
                case null -> finalCause = attackSources.get(totemCause);
            }

            Bukkit.getOnlinePlayers().forEach(all -> {
                all.sendMessage(
                        chatPrefix + "§f" + p.getName() + " has consumpt a " + ChatColor.of("#8e5f25") + "Wooden Totem§f! \n" +
                                "§8(Cause: §7" + finalCause + "§8)");
                all.playSound(all.getLocation(), Sound.ENTITY_WITHER_HURT, 0.2F, 0F);
            });

            global.showTotemUse(p, global.getTotems(p.getUniqueId()), "Wooden Totem", finalCause);
        }
    }

    private HashMap<String, String> getAttackSources() {
        HashMap<String, String> deathSources = new HashMap<>();

        deathSources.put("ENTITY_ATTACK", "Attack from ");
        deathSources.put("ENTITY_EXPLOSION", "Blewed up by a ");
        deathSources.put("BLOCK_EXPLOSION", "Block explosion ");
        deathSources.put("CONTACT", "Contact");
        deathSources.put("DROWNING", "Drowning");
        deathSources.put("SUFFOCATION", "Suffocating");
        deathSources.put("FALL", "Falling");
        deathSources.put("THORNS", "Thorns");
        deathSources.put("FIRE", "Fire");
        deathSources.put("FIRE_TICK", "Fire ticks");
        deathSources.put("LAVA", "Lava");
        deathSources.put("LIGHTNING", "Lightning");
        deathSources.put("POISON", "Poisoning");
        deathSources.put("WITHER", "Wither");
        deathSources.put("PROJECTILE", "");
        deathSources.put("MAGIC", "Magic");
        deathSources.put("VOID", "Void");
        deathSources.put("STARVATION", "Hunger");
        deathSources.put("KILL", "Suicide");
        deathSources.put("WORLD_BORDER", "Reached the limits.. quite literally");
        deathSources.put("CUSTOM", "???");
        deathSources.put("NULL", "Unknown");

        return deathSources;
    }
}