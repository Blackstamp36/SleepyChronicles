package org.blackstamp.sleepyChronicles.recipe;

import org.blackstamp.sleepyChronicles.item.drop.phantomDrops;
import org.blackstamp.sleepyChronicles.item.misc.usableItems;
import org.blackstamp.sleepyChronicles.item.pale.paleItems;
import org.blackstamp.sleepyChronicles.sleepyChronicles;
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

        NamespacedKey paleShardKey = new NamespacedKey(sleepyChronicles.getter(), "pale_shard");
        ShapedRecipe shapedRecipe = new ShapedRecipe(paleShardKey, paleShard);

        shapedRecipe.shape(
                "PPP",
                "PNP",
                "PPP"
        );

        shapedRecipe.setIngredient('P', paleShard);
        shapedRecipe.setIngredient('N', Material.NETHERITE_INGOT);

        sleepyChronicles.getter().getServer().addRecipe(shapedRecipe);
    }

    public void createMERecipe(){
        usableItems usableItems = new usableItems();
        phantomDrops phantomDrops = new phantomDrops();

        ItemStack mechanicalEye = usableItems.createMechanicalEye();

        NamespacedKey mechanicalEyeKey = new NamespacedKey(sleepyChronicles.getter(), "mechanical_eye");
        ShapedRecipe shapedRecipe = new ShapedRecipe(mechanicalEyeKey, mechanicalEye);

        shapedRecipe.shape(
                "LLL",
                "LSL",
                "LLL"
        );

        shapedRecipe.setIngredient('L', phantomDrops.createLens());
        shapedRecipe.setIngredient('S', Material.NETHER_STAR);

        sleepyChronicles.getter().getServer().addRecipe(shapedRecipe);
    }

    public void createPaleSwordRecipe(){
        paleItems paleItems = new paleItems();

        ItemStack paleCrystal = paleItems.createPaleCrystal();

        NamespacedKey paleCrystalKey = new NamespacedKey(sleepyChronicles.getter(), "pale_crystal");
        ShapedRecipe shapedRecipe = new ShapedRecipe(paleCrystalKey, paleCrystal);

        shapedRecipe.shape(
                " P ",
                " P ",
                " S "
        );

        shapedRecipe.setIngredient('P', paleCrystal);
        shapedRecipe.setIngredient('S', Material.STICK);

        sleepyChronicles.getter().getServer().addRecipe(shapedRecipe);
    }
}
