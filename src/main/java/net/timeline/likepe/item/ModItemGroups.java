package net.timeline.likepe.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.timeline.likepe.PERemastered;
import net.timeline.likepe.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup PE_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(PERemastered.MOD_ID, "pe"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.pe"))
                    .icon(() -> new ItemStack(ModBlocks.GLOWING_OBSIDIAN.asItem())).entries((displayContext, entries) -> {
                        entries.add(ModBlocks.NETHER_REACTOR_CORE);

                        entries.add(ModBlocks.GLOWING_OBSIDIAN);
                        entries.add(ModBlocks.DEAD_NETHER_REACTOR_CORE);


                        entries.add(ModBlocks.CYAN_ROSE);
                    }).build());

    public static void registerItemGroups() {

    }
}
