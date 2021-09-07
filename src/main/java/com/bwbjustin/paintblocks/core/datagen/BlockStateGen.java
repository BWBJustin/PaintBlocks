package com.bwbjustin.paintblocks.core.datagen;

import com.bwbjustin.paintblocks.PaintBlocks;
import com.bwbjustin.paintblocks.core.init.BlockInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGen extends BlockStateProvider
{
	public BlockStateGen(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, PaintBlocks.MOD_ID, exFileHelper);
	}
	
	@Override
	protected void registerStatesAndModels()
	{
		BlockInit.BLOCKS.getEntries().forEach(block ->
			simpleBlock(block.get(), models().cubeAll(
				block.getId().getPath(),
				new ResourceLocation(PaintBlocks.MOD_ID, block.getId().getPath().endsWith("block") ? "block/"+block.getId().getPath() : block.getId().getPath())
			))
		);
	}
}
