package com.bwbjustin.paintblocks.core.datagen;

import com.bwbjustin.paintblocks.PaintBlocks;
import com.bwbjustin.paintblocks.core.init.ItemInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGen extends ItemModelProvider
{
	public ItemModelGen(DataGenerator gen, ExistingFileHelper exFileHelper)
	{
		super(gen, PaintBlocks.MOD_ID, exFileHelper);
	}
	
	@Override
	protected void registerModels()
	{
		ItemInit.ITEMS.getEntries().forEach(item -> withExistingParent(item.getId().getPath(), new ResourceLocation(PaintBlocks.MOD_ID, "block/"+item.getId().getPath())));
	}
}
