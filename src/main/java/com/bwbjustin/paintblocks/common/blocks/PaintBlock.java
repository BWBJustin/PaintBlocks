package com.bwbjustin.paintblocks.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;

public class PaintBlock extends Block
{
	public PaintBlock()
	{
		super(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.8f));
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player)
	{
		if (player.isCreative())
			return super.getPickBlock(state, target, world, pos, player);
		
		ItemStack stack = state.getBlock().asItem().getDefaultInstance();
		stack.setCount(64);
		
		player.addItem(stack);
		return stack;
	}
}
