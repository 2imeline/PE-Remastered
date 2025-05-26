package net.timeline.likepe.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.timeline.likepe.PERemastered;
import net.timeline.likepe.block.custom.nether_reactor_core_block;

import static net.timeline.likepe.block.custom.nether_reactor_core_block.ACTIVE;

public class ModBlocks {

    public static final Block NETHER_REACTOR_CORE = registerBlock("nether_reactor_core",
            new nether_reactor_core_block(FabricBlockSettings.copyOf(Blocks.DIAMOND_BLOCK).luminance(state -> state.get(ACTIVE) ? 15 : 0)));

    public static final Block GLOWING_OBSIDIAN = registerBlock("glowing_obsidian",
            new Block(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).luminance(state -> 11)));

    public static final Block DEAD_NETHER_REACTOR_CORE = registerBlock("dead_nether_reactor_core",
            new Block(FabricBlockSettings.copyOf(Blocks.TUFF)));


    public static final Block CYAN_ROSE = registerBlock("cyan_rose",
            new FlowerBlock(StatusEffects.SPEED, 5, FabricBlockSettings.copyOf(Blocks.POPPY).nonOpaque().noCollision()));

    public static final Block POTTED_CYAN_ROSE = Registry.register(Registries.BLOCK, new Identifier(PERemastered.MOD_ID, "potted_cyan_rose"),
        new FlowerPotBlock(CYAN_ROSE, FabricBlockSettings.copyOf(Blocks.POTTED_POPPY).nonOpaque()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(PERemastered.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, new Identifier(PERemastered.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks()
    {

    }
}
