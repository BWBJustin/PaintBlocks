package com.bwbjustin.paintblocks.core.datagen;

import com.bwbjustin.paintblocks.PaintBlocks;
import com.bwbjustin.paintblocks.core.init.BlockInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagGen extends BlockTagsProvider
{
	public BlockTagGen(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, PaintBlocks.MOD_ID, exFileHelper);
	}
	
	@Override
	protected void addTags()
	{
		TagAppender<Block> mineable_with_pickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
		TagAppender<Block> paint_blocks = tag(BlockTags.createOptional(new ResourceLocation("paintblocks:paint_blocks")));
		TagAppender<Block> paint_blocks_with_rainbow = tag(BlockTags.createOptional(new ResourceLocation("paintblocks:paint_blocks_with_rainbow")));
		
		BlockInit.BLOCKS.getEntries().forEach(block ->
		{
			mineable_with_pickaxe.add(block.get());
			if (!block.getId().getPath().equals("rainbow_block"))
				paint_blocks.add(block.get());
			paint_blocks_with_rainbow.add(block.get());
		});
	}
}
