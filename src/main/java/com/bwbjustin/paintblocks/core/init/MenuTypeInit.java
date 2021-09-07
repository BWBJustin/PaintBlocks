package com.bwbjustin.paintblocks.core.init;

import com.bwbjustin.paintblocks.PaintBlocks;
import com.bwbjustin.paintblocks.common.menus.SelectPaintBlockMenu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.*;

public class MenuTypeInit
{
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, PaintBlocks.MOD_ID);
	
	public static final RegistryObject<MenuType<SelectPaintBlockMenu>> SELECT_PAINT_BLOCK_MENU_TYPE = MENU_TYPES.register(
		"select_paint_block",
		() -> IForgeContainerType.create(SelectPaintBlockMenu::new)
	);
}
