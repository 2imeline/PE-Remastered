package net.timeline.likepe.block.custom;

import net.fabricmc.fabric.impl.event.interaction.FakePlayerNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class nether_reactor_core_block extends Block {
    public nether_reactor_core_block(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        boolean correct = checkFirstLayer(world, pos) && checkSecondLayer(world, pos) && checkThirdLayer(world, pos);
        if(!world.isClient)
        {
            if(correct) {
                player.sendMessage(Text.literal("Active!"));
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
