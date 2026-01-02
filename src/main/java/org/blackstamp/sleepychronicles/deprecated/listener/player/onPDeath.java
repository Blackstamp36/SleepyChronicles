package org.blackstamp.sleepychronicles.deprecated.listener.player;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.global.GlobalClass;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.registrable.Registrable;
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

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import static org.blackstamp.sleepychronicles.SleepyChronicles.chatPrefix;

@Registrable
public class onPDeath implements Listener {
    GlobalClass global = new GlobalClass();
    HashMap<String, String> damageSources = global.getDamageSources();

    Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getPlayer();
        Location dL = p.getLocation();
        Random r = new Random();
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

        if(damageSources.containsKey(deathCause)){
            switch(directEntity){
                case Projectile ignored -> finalCause = damageSources.get(deathCause).concat(" from " + originEntity.getName());
                case Entity ignored -> finalCause = damageSources.get(deathCause).concat(directEntity.getName());
                case null -> finalCause = damageSources.get(deathCause);

            }

            if(p.getLastDamageCause().getDamageSource().getDamageType() instanceof Block block){
                finalCause = damageSources.get(deathCause).concat(" (" + block.getType().name() + ")");
            }

        } else finalCause = damageSources.get("NULL");

        global.showDiscordDeath(p, dL, finalCause, deathInWorld, p.getName() + ", " + deathMessage + ".");

        p.setGameMode(GameMode.SPECTATOR);
        p.getWorld().setStorm(false);
        e.setDeathMessage(chatPrefix + "§c" + p.getName() + " has died! \nCause of death: " + finalCause + "\n" +
                "§8| §7X: " + (int) dL.getX() + ", §7Y: " + (int) dL.getY() + ", §7Z: " + (int) dL.getZ() + "; §7(" + deathInWorld + "§7)" + "\n" +
                "§8" + p.getName() + ", " + deathMessage + ".");

        String deathReason = finalCause;

        Bukkit.getOnlinePlayers().forEach(all -> { // doesn't execute somehow
            all.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0, false, false, false));
            all.sendTitle("",ChatColor.of("#d62411") + "[☠]");
        });

        playDeathMelody();

        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () ->
                p.sendTitle("\uE102",null), 40);

        Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
            p.getWorld().setStorm(true);
            p.getWorld().setWeatherDuration(60);

            if(!p.isOp())
                p.banPlayer("§c''" + banQuotes[r.nextInt(banQuotes.length)] + "'' \n Cause of death: " + deathReason);
        }, 60);
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
            all.playSound(all, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 0.5F, 0.5F);

            Bukkit.getScheduler().runTaskLater(SleepyChronicles.getInstance(), () -> {
                all.playSound(all, Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5F, 0.5F);
            }, 20);

        });

    }

    private HashMap<UUID, String> getDeathMessages() {
        HashMap<UUID, String> deathMessages = new HashMap<>();
        deathMessages.put(UUID.fromString("994702e0-1a8b-459a-9d4e-ef9d06469d0d"), "he did found the Eclipsini Bombini");
        deathMessages.put(UUID.fromString("a86298e9-00b0-4311-a505-8d4e2f818077"), "their builds didn't save him");
        deathMessages.put(UUID.fromString("4baea546-a92d-4ee6-b096-f3c5ead71ada"), "graduated from throwing in Valorant");

        return deathMessages;
    }
}
