package org.blackstamp.sleepyChronicles.listener.player;

import org.blackstamp.sleepyChronicles.globalClass;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
import org.blackstamp.sleepyChronicles.util.Registrable;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static org.blackstamp.sleepyChronicles.sleepyChronicles.PREFIX;

@Registrable
public class onPDeath implements Listener {
    globalClass global = new globalClass();

    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        Location dL = p.getLocation();
        Random r = new Random();
        HashMap<String, String> deathSources = getDeathSources();
        HashMap<String, String> worldTypes = getWorldTypes();
        HashMap<UUID, String> deathMessages = getDeathMessages();
        String deathInWorld = "Unknown";

        global.createTomb(p);

        String[] banQuotes = {
                "What a shame.",
                "Was it worth it?",
                "Not surprised.",
                "Too bad.",
                "Wake up.",
                "It seems that you've found an end.."
        };

        String deathCause = e.getEntity().getLastDamageCause().getCause().toString().toUpperCase();
        String finalCause;
        Entity directEntity = e.getEntity().getLastDamageCause().getDamageSource().getDirectEntity();
        Entity originEntity = e.getEntity().getLastDamageCause().getDamageSource().getCausingEntity();
        String deathMessage = "now rests in peace.";

        if(deathMessages.containsKey(p.getUniqueId())){
            deathMessage = deathMessages.get(p.getUniqueId());
        }

        if(worldTypes.containsKey(dL.getWorld().getName())){
            deathInWorld = worldTypes.get(dL.getWorld().getName());
        }

        if(deathSources.containsKey(deathCause)){
            switch(directEntity){
                case Projectile ignored -> finalCause = deathSources.get(deathCause).concat(directEntity.getName() + " from " + originEntity.getName());
                case Entity ignored -> finalCause = deathSources.get(deathCause).concat(directEntity.getName());
                case null -> finalCause = deathSources.get(deathCause);

            }

            if(p.getLastDamageCause().getDamageSource().getDamageType() instanceof Block block){
                finalCause = deathSources.get(deathCause).concat(" (" + block.getType().name() + ")");
            }

        } else {
            finalCause = deathSources.get("NULL");
        }

        global.showDiscordDeath(p, dL, finalCause, deathInWorld, p.getName() + ", " + deathMessage + ".");

        p.setGameMode(GameMode.SPECTATOR);
        p.getWorld().setStorm(false);
        scoreboard.getTeam("dead").addPlayer(p);
        e.setDeathMessage(PREFIX + "§c" + p.getName() + " has died! Reason: " + finalCause + "\n" +
                "§8| §7X: " + (int) dL.getX() + ", §7Y: " + (int) dL.getY() + ", §7Z: " + (int) dL.getZ() + "; §7(" + deathInWorld + "§7)" + "\n" +
                "§8" + p.getName() + ", " + deathMessage + ".");

        String deathReason = finalCause;

        Bukkit.getOnlinePlayers().forEach(all -> {
            all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 70, 0, false, false, false));
            all.sendTitle("§c" + banQuotes[r.nextInt(banQuotes.length)],"§c" + p.getName() + " has died!");
            playDeathMelody();

            Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
                p.getWorld().setStorm(true);
                p.getWorld().setWeatherDuration(60);

                if(!p.isOp()) p.banPlayer("§c''" + banQuotes[r.nextInt(banQuotes.length)] + "'' \n Cause of death: " + deathReason);
                all.playSound(all, Sound.BLOCK_TRIAL_SPAWNER_DETECT_PLAYER,1,0);
                all.playSound(all, Sound.ENTITY_CREAKING_ACTIVATE,1,0);
            }, 60);
        });

    }

    private HashMap<String, String> getDeathSources() {
        HashMap<String, String> deathSources = new HashMap<>();

        deathSources.put("ENTITY_ATTACK", "Killed by ");
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
        deathSources.put("CUSTOM", "Intentional Plugin-Design");
        deathSources.put("NULL", "Unknown");

        return deathSources;
    }

    private HashMap<String, String> getWorldTypes() {
        HashMap<String, String> worldTypes = new HashMap<>();
        worldTypes.put("world", "Overworld");
        worldTypes.put("world_nether", "The Nether");
        worldTypes.put("world_the_end", "The End");
        worldTypes.put("world_aftermath", "Aftermath");

        return worldTypes;
    }

    private void playDeathMelody(){
        Bukkit.getOnlinePlayers().forEach(all -> {
            all.playSound(all, Sound.BLOCK_TRIAL_SPAWNER_ABOUT_TO_SPAWN_ITEM, 1, 0);

            Bukkit.getScheduler().runTaskLater(sleepyChronicles.getter(), () -> {
                all.playSound(all, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5F, 0.5F);
            }, 20);

        });

    }

    private HashMap<UUID, String> getDeathMessages() {
        HashMap<UUID, String> deathMessages = new HashMap<>();
        deathMessages.put(UUID.fromString("994702e0-1a8b-459a-9d4e-ef9d06469d0d"), "he did found the Eclipsini Bombini");

        return deathMessages;
    }
}
