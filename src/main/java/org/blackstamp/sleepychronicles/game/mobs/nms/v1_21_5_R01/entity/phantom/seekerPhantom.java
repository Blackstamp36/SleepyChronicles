package org.blackstamp.sleepychronicles.game.mobs.nms.v1_21_5_R01.entity.phantom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.blackstamp.sleepychronicles.global.utils.color.ChatColor;
import org.blackstamp.sleepychronicles.global.utils.nms.NMSEntity;
import org.bukkit.craftbukkit.util.CraftChatMessage;

import java.util.Random;

@NMSEntity
public class seekerPhantom extends Phantom {
    Random r = new Random();
    int damage = 12;
    int health = 20;
    float speed = 0.55F;
    int chance = r.nextInt(0,3);

    public seekerPhantom(EntityType<? extends Phantom> type, Level world) {
        super(type, world);

        this.addTag("seekerPhantom");
        this.setCustomName(CraftChatMessage.fromStringOrNull(ChatColor.of("#b83d3d") + "Seeker"));
        switch(chance){
            case 1:
                this.getAttribute(Attributes.SCALE).setBaseValue(0.65D);
                this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(1.75 + (speed * 3));
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health);
                this.setHealth(health);
                break;

            case 2:
                this.getAttribute(Attributes.SCALE).setBaseValue(1.75D);
                this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(1.75 + (speed * 2));
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage * 2);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health * 2);
                this.setHealth(health * 2);
                break;

            case 3:
                this.getAttribute(Attributes.SCALE).setBaseValue(3.5D);
                this.getAttribute(Attributes.FLYING_SPEED).setBaseValue(1.75 + speed);
                this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(damage * 3);
                this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(health * 3);
                this.setHealth(health * 3);
                break;
        }

        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(20);
        this.setSilent(true);
        this.shouldBurnInDay = false;

    }
}


