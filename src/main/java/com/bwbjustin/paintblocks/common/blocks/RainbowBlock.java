package com.bwbjustin.paintblocks.common.blocks;

import com.bwbjustin.paintblocks.PaintBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RainbowBlock extends PaintBlock
{
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult target)
	{
		if (PaintBlocks.CURRENT_STATES.get(player.getDisplayName().getString()) != null)
		{
			level.setBlockAndUpdate(pos, PaintBlocks.CURRENT_STATES.get(player.getDisplayName().getString()));
			return InteractionResult.SUCCESS;
		}
		
		return InteractionResult.PASS;
	}
}
