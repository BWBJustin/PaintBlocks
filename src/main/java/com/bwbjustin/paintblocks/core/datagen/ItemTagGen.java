package com.bwbjustin.paintblocks.core.datagen;

import com.bwbjustin.paintblocks.PaintBlocks;
import com.bwbjustin.paintblocks.core.init.ItemInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagGen extends ItemTagsProvider
{
	public ItemTagGen(DataGenerator gen, BlockTagsProvider provider, ExistingFileHelper exFileHelper)
	{
		super(gen, provider, PaintBlocks.MOD_ID, exFileHelper);
	}
	
	@Override
	protected void addTags()
	{
		TagAppender<Item> paint_blocks = tag(ItemTags.createOptional(new ResourceLocation("paintblocks:paint_blocks")));
		TagAppender<Item> paint_blocks_with_rainbow = tag(ItemTags.createOptional(new ResourceLocation("paintblocks:paint_blocks_with_rainbow")));
		
		ItemInit.ITEMS.getEntries().forEach(item ->
		{
			if (!item.getId().getPath().equals("rainbow_block"))
				paint_blocks.add(item.get());
			paint_blocks_with_rainbow.add(item.get());
		});
	}
}
