//package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.iron_golem.quantumBeast;
//
//import lombok.Getter;
//import lombok.Setter;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.world.entity.Mob;
//import net.minecraft.world.entity.ai.attributes.Attributes;
//import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
//import net.minecraft.world.entity.monster.Creeper;
//import net.minecraft.world.level.Level;
//import org.blackstamp.sleepychronicles.global.GlobalClass;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.bossMob;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.creeper.quantumCorePhase2.qcMinionsSpellGoal;
//import org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.goals.creeper.quantumCorePhase1.qcQuantumBulletsGoal;
//import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
//import org.bukkit.*;
//import org.bukkit.boss.BarColor;
//import org.bukkit.craftbukkit.util.CraftChatMessage;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.scoreboard.Scoreboard;
//import org.bukkit.scoreboard.Team;
//
//public class quantumCore extends Creeper implements bossMob {
//
//    double coreAngle = 0;
//    final double coreRadius = 4.75;
//
//    @Setter
//    @Getter
//    private quantumBeast owner;
//    @Setter
//    @Getter
//    private int bossPhase = 1;
//    @Getter
//    int tickCount = 0;
//    @Getter
//    @Setter
//    int tickCooldown = 0;
//
//    @Getter
//    @Setter
//    String bossName = "Qᴜᴀɴᴛᴜᴍ Cᴏʀᴇ";
//
//    public enum bossAttacks{
//        QUANTUM_BULLETS,
//        MINIONS_SPELL
//    }
//
//    public quantumCore.bossAttacks currentAttack = bossAttacks.QUANTUM_BULLETS;
//
//    GlobalClass global = new GlobalClass();
//    private final int maxHealth = 750;
//    private final double mobScale = 0.75D;
//    private final int projectileDamage = 9;
//    private int projectileCount = 3;
//
//    private final String bossThemeKey = "sleepy.boss.quantum_core.phase_1";
//    private final int bossThemeDuration = 288;
//
//    public quantumCore(net.minecraft.world.entity.EntityType<? extends Creeper> entityType, Level level){
//        super(entityType, level);
//
//        registerAttributes();
//        registerGoals();
//
//        global.initBossBarTask(this, bossName, BarColor.GREEN,"#70ba6d");
//    }
//
//    public void registerGoals() {
//        this.goalSelector.getAvailableGoals().clear();
//
//        this.goalSelector.addGoal(0, new qcQuantumBulletsGoal(this,
//                projectileDamage, projectileCount,80));
//
//        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this,
//                net.minecraft.world.entity.player.Player.class, true));
//    }
//
//    private void registerAttributes(){
//        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#70ba6d") + "Quantum Core"));
//
//        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
//        this.getAttribute(Attributes.SCALE).setBaseValue(mobScale);
//        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
//        this.setHealth(this.getMaxHealth());
//    }
//
//    public void initSecondPhase(quantumCore entity){
//        if(entity.getBossPhase() == 2) return;
//
//        entity.setBossPhase(2);
//
//        this.goalSelector.addGoal(1, new qcMinionsSpellGoal(this,
//                projectileCount,80));
//    }
//
//    @Override
//    public void tick(){
//        super.tick();
//        tickCount++;
//        decrementTickCooldown();
//        LivingEntity bukkitEntity = this.getBukkitLivingEntity();
//
//        if(tickCount >= 100) tickCount = 0;
//
//        if(!this.isAlive()) {
//            getOwner().kill((ServerLevel) this.level());
//            return;
//        }
//
//        if(this.getBossPhase() == 1) {
//            if(!(tickCount % 3 == 0)) return;
//            teleportCore(this);
//
//        } else if(this.getBossPhase() == 2){
//            teleportCore(this);
//
//            bukkitEntity.setGlowing(true);
//            getCoreTeam().addEntity(bukkitEntity);
//            this.setPowered(true);
//        }
//    }
//
//    private void teleportCore(quantumCore entity){
//        LivingEntity bukkitEntity = entity.getBukkitLivingEntity();
//
//        Location bossLoc = getOwner().getBukkitEntity().getLocation();
//        coreAngle += 0.1;
//        if(coreAngle >= (Math.PI * 2)) coreAngle = 0.0;
//
//        double x = bossLoc.getX() + (coreRadius * Math.cos(coreAngle));
//        double y = bossLoc.getY() + 2.25;
//        double z = bossLoc.getZ() + (coreRadius * Math.sin(coreAngle));
//        Location newLoc = new Location(bossLoc.getWorld(), x, y, z);
//
//        bukkitEntity.teleport(newLoc);
//    }
//
//    private Team getCoreTeam(){
//        Scoreboard manager = Bukkit.getScoreboardManager().getMainScoreboard();
//        Team greenTeam = manager.getTeam("greenTeam");
//
//        if(greenTeam != null) return greenTeam;
//
//        Team team = manager.registerNewTeam("greenTeam");
//        team.setColor(org.bukkit.ChatColor.GREEN);
//
//        return team;
//    }
//
//    @Override
//    public void decrementTickCooldown() {
//        if(tickCooldown > 0) tickCooldown--;
//    }
//
//    @Override
//    public void increaseTickCooldown(int value) {
//        tickCooldown+= value;
//    }
//
//    @Override
//    public int getThemeDurationTicks() {
//        return bossThemeDuration * 20;
//    }
//
//    @Override
//    public String getBossTheme() {
//        return bossThemeKey;
//    }
//
//    @Override
//    public Mob getEntity() {
//        return this;
//    }
//}
