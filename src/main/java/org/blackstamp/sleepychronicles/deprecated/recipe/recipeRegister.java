package org.blackstamp.sleepychronicles.deprecated.recipe;

import org.blackstamp.sleepychronicles.SleepyChronicles;
import org.blackstamp.sleepychronicles.deprecated.items.drop.phantomDrops;
import org.blackstamp.sleepychronicles.deprecated.items.misc.usableItems;
import org.blackstamp.sleepychronicles.deprecated.items.pale.paleItems;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class recipeRegister {

    public void registerRecipes(){
        createPSRecipe();
        createMERecipe();
        createPaleSwordRecipe();
    }

    public void createPSRecipe(){
        paleItems paleItems = new paleItems();
        ItemStack paleShard = paleItems.createPaleShard();

        NamespacedKey paleShardKey = new NamespacedKey(SleepyChronicles.getInstance(), "pale_shard");
        ShapedRecipe shapedRecipe = new ShapedRecipe(paleShardKey, paleShard);

        shapedRecipe.shape(
                "PPP",
                "PNP",
                "PPP"
        );

        shapedRecipe.setIngredient('P', paleShard);
        shapedRecipe.setIngredient('N', Material.NETHERITE_INGOT);

        SleepyChronicles.getInstance().getServer().addRecipe(shapedRecipe);
    }

    public void createMERecipe(){
        usableItems usableItems = new usableItems();
        phantomDrops phantomDrops = new phantomDrops();

        ItemStack mechanicalEye = usableItems.createMechanicalEye();

        NamespacedKey mechanicalEyeKey = new NamespacedKey(SleepyChronicles.getInstance(), "mechanical_eye");
        ShapedRecipe shapedRecipe = new ShapedRecipe(mechanicalEyeKey, mechanicalEye);

        shapedRecipe.shape(
                "LLL",
                "LSL",
                "LLL"
        );

        shapedRecipe.setIngredient('L', phantomDrops.createLens());
        shapedRecipe.setIngredient('S', Material.NETHER_STAR);

        SleepyChronicles.getInstance().getServer().addRecipe(shapedRecipe);
    }

    public void createPaleSwordRecipe(){
        paleItems paleItems = new paleItems();

        ItemStack paleCrystal = paleItems.createPaleCrystal();

        NamespacedKey paleCrystalKey = new NamespacedKey(SleepyChronicles.getInstance(), "pale_crystal");
        ShapedRecipe shapedRecipe = new ShapedRecipe(paleCrystalKey, paleCrystal);

        shapedRecipe.shape(
                " P ",
                " P ",
                " S "
        );

        shapedRecipe.setIngredient('P', paleCrystal);
        shapedRecipe.setIngredient('S', Material.STICK);

        SleepyChronicles.getInstance().getServer().addRecipe(shapedRecipe);
    }
}
