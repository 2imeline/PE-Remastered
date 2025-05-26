package net.timeline.likepe.block.custom;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import me.emafire003.dev.structureplacerapi.StructurePlacerAPI;
import net.fabricmc.fabric.impl.event.interaction.FakePlayerNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.timeline.likepe.block.ModBlocks;


public class nether_reactor_core_block extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");


    public nether_reactor_core_block(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ACTIVE, false));

    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (state.get(ACTIVE)) {
            return 0.0F; // can't be broken
        }
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }


    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (state.get(ACTIVE)) {
            world.setBlockState(pos, ModBlocks.DEAD_NETHER_REACTOR_CORE.getDefaultState());
            BlockPos upPos = pos.up();
            for (int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    BlockPos pos1 = upPos.down(i).east().west(j);
                    if(world.getBlockState(pos1).getBlock() != Blocks.AIR && world.getBlockState(pos1).getBlock() != Blocks.FIRE && world.getBlockState(pos1).getBlock() != ModBlocks.DEAD_NETHER_REACTOR_CORE)
                    {
                        world.setBlockState(pos1, Blocks.OBSIDIAN.getDefaultState());
                    }
                    BlockPos pos2 = upPos.down(i).north().east().west(j);
                    if(world.getBlockState(pos2).getBlock() != Blocks.AIR && world.getBlockState(pos1).getBlock() != Blocks.FIRE)
                    {
                        world.setBlockState(pos2, Blocks.OBSIDIAN.getDefaultState());
                    }

                    BlockPos pos3 = upPos.down(i).south().east().west(j);
                    if(world.getBlockState(pos3).getBlock() != Blocks.AIR && world.getBlockState(pos1).getBlock() != Blocks.FIRE)
                    {
                        world.setBlockState(pos3, Blocks.OBSIDIAN.getDefaultState());
                    }
                }
            }
        }
        world.getServer().getCommandManager().executeWithPrefix(world.getServer().getCommandSource().withLevel(4).withSilent(),
                "time set midnight");
        StructurePlacerAPI placer = new StructurePlacerAPI(
                (ServerWorld) world,
                new Identifier("likepe", "nether_spire_final_1"),
                pos.up(2), // spawn the spire a bit above the reactor
                BlockMirror.NONE,
                BlockRotation.NONE,
                false,    // keep entities
                1.0f,     // full integrity
                new BlockPos(-8, -4, -8) // no offset
        );
                placer.loadStructure();
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {



        if (state.get(ACTIVE)) {
            // Do nothing if already active
            return ActionResult.PASS;
        }

        boolean correct = checkFirstLayer(world, pos) && checkSecondLayer(world, pos) && checkThirdLayer(world, pos);
        if(!world.isClient)
        {
            if(correct) {
                ((ServerWorld) world).scheduleBlockTick(pos, this, 700);
                StructurePlacerAPI placer = new StructurePlacerAPI(
                        (ServerWorld) world,
                        new Identifier("likepe", "nether_spire_nri_5"),
                        pos.up(2), // spawn the spire a bit above the reactor
                        BlockMirror.NONE,
                        BlockRotation.NONE,
                        false,    // keep entities
                        1.0f,     // full integrity
                        new BlockPos(-8, -4, -8) // no offset
                );

                world.setBlockState(pos, state.with(ACTIVE, true));

                placer.loadStructure();
                player.sendMessage(Text.literal("Active!"));


                BlockPos upPos = pos.up(1);

                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);

                lightning.setPosition(pos.getX(), pos.getY(), pos.getZ());



                for (int i = 0; i < 8; i++) {
                    Random random = world.getRandom();
                    double d = pos.getX() + random.nextInt(6) + 2;
                    double e = pos.down().getY();
                    double f = pos.getZ() + random.nextInt(6) + 2;

                    ZombifiedPiglinEntity pigman = new ZombifiedPiglinEntity(EntityType.ZOMBIFIED_PIGLIN, world);
                    pigman.setPosition(d, e, f);
                    world.spawnEntity(pigman);
                    pigman.setAngryAt(player.getUuid());

                }


                world.spawnEntity(lightning);

                for (int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        BlockPos pos1 = upPos.down(i).east().west(j);
                        if(world.getBlockState(pos1).getBlock() != Blocks.AIR && world.getBlockState(pos1).getBlock() != ModBlocks.NETHER_REACTOR_CORE)
                        {
                            world.setBlockState(pos1, ModBlocks.GLOWING_OBSIDIAN.getDefaultState());
                        }
                        BlockPos pos2 = upPos.down(i).north().east().west(j);
                        if(world.getBlockState(pos2).getBlock() != Blocks.AIR)
                        {
                            world.setBlockState(pos2, ModBlocks.GLOWING_OBSIDIAN.getDefaultState());
                        }

                        BlockPos pos3 = upPos.down(i).south().east().west(j);
                        if(world.getBlockState(pos3).getBlock() != Blocks.AIR)
                        {
                            world.setBlockState(pos3, ModBlocks.GLOWING_OBSIDIAN.getDefaultState());
                        }
                    }
                }

            }else{
                player.sendMessage(Text.literal("Not the correct pattern!"));
            }
        }
        if(correct)
        {
            world.playSound(player, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0F, 1.3F);
        }else{
            world.playSound(player, pos, SoundEvents.BLOCK_BEACON_DEACTIVATE, SoundCategory.BLOCKS, 1.0F, 0.8F);
        }
        return ActionResult.SUCCESS;
    }

    public boolean checkFirstLayer(World world, BlockPos pos)
    {
        BlockPos downPos = pos.down();
        if(world.getBlockState(downPos).getBlock() == Blocks.COBBLESTONE && world.getBlockState(downPos.west()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(downPos.east()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(downPos.south()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(downPos.north()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(downPos.south().west()).getBlock() == Blocks.GOLD_BLOCK && world.getBlockState(downPos.south().east()).getBlock() == Blocks.GOLD_BLOCK && world.getBlockState(downPos.north().west()).getBlock() == Blocks.GOLD_BLOCK && world.getBlockState(downPos.north().east()).getBlock() == Blocks.GOLD_BLOCK)
        {
            return true;
        }else{ return false; }
    }
    public boolean checkSecondLayer(World world, BlockPos pos)
    {
        if(world.getBlockState(pos.south()).getBlock() == Blocks.AIR && world.getBlockState(pos.west()).getBlock() == Blocks.AIR && world.getBlockState(pos.east()).getBlock() == Blocks.AIR && world.getBlockState(pos.north()).getBlock() == Blocks.AIR && world.getBlockState(pos.south().west()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(pos.south().east()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(pos.north().west()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(pos.north().east()).getBlock() == Blocks.COBBLESTONE)
        {
            return true;
        }else{ return false; }
    }

    public boolean checkThirdLayer(World world, BlockPos pos)
    {
        BlockPos upPos = pos.up();
        if(world.getBlockState(upPos).getBlock() == Blocks.COBBLESTONE && world.getBlockState(upPos.west()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(upPos.east()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(upPos.south()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(upPos.north()).getBlock() == Blocks.COBBLESTONE && world.getBlockState(upPos.south().west()).getBlock() == Blocks.AIR && world.getBlockState(upPos.south().east()).getBlock() == Blocks.AIR && world.getBlockState(upPos.north().west()).getBlock() == Blocks.AIR && world.getBlockState(upPos.north().east()).getBlock() == Blocks.AIR)
        {
            return true;
        }else{ return false; }
    }
}
