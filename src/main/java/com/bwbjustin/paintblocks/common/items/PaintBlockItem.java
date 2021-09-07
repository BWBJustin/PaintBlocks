package com.bwbjustin.paintblocks.common.items;

import java.util.List;

import com.bwbjustin.paintblocks.PaintBlocks;
import com.bwbjustin.paintblocks.common.blocks.PaintBlock;

import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class PaintBlockItem extends BlockItem
{
	public PaintBlockItem(PaintBlock block)
	{
		super(block, new Item.Properties().tab(PaintBlocks.TAB_PAINT_BLOCKS));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag)
	{
		super.appendHoverText(stack, level, list, flag);
		
		list.add(
			new TranslatableComponent("message."+PaintBlocks.MOD_ID+"."+getBlock().getRegistryName().getPath(), PaintBlocks.OPEN_GUI.getKey().getDisplayName().getString().toUpperCase())
			.withStyle(
				PaintBlocks.HEXES.get(getBlock().getRegistryName().getPath()) != null ?
				Style.EMPTY.withColor(TextColor.fromRgb(PaintBlocks.HEXES.get(getBlock().getRegistryName().getPath()))) :
				Style.EMPTY
			)
		);
	}
}
