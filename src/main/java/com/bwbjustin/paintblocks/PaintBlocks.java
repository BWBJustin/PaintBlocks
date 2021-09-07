package com.bwbjustin.paintblocks;

import java.util.*;
import java.util.function.Supplier;

import com.bwbjustin.paintblocks.client.screens.SelectPaintBlockScreen;
import com.bwbjustin.paintblocks.common.menus.SelectPaintBlockMenu;
import com.bwbjustin.paintblocks.core.datagen.*;
import com.bwbjustin.paintblocks.core.init.*;
import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import net.minecraftforge.fmllegacy.network.*;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod("paintblocks")
public class PaintBlocks
{
	public static final String MOD_ID = "paintblocks";
	public static final PaintBlocksCreativeModeTab TAB_PAINT_BLOCKS = new PaintBlocksCreativeModeTab();
	public static final KeyMapping OPEN_GUI = new KeyMapping(
		"key.paintblocks.open_gui",
		KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM.getOrCreate(90),
		"category.paintblocks.paint_blocks"
	);
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, "main"), () -> "1", "1"::equals, "1"::equals);
	public static HashMap<String, BlockState> CURRENT_STATES = new HashMap<>();

	public PaintBlocks()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::commonSetup);
		bus.addListener(this::generateData);
		bus.addListener(this::setup);
		
		BlockInit.BLOCKS.register(bus);
		ItemInit.ITEMS.register(bus);
		MenuTypeInit.MENU_TYPES.register(bus);
		
		MinecraftForge.EVENT_BUS.addListener(this::onTick);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void commonSetup(FMLCommonSetupEvent event)
	{
		CHANNEL.registerMessage(0, InputMessage.class, (message, data) -> {}, data -> new InputMessage(), InputMessage::handle);
	}
	
	private void generateData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		
		if (event.includeServer())
		{
			gen.addProvider(new RecipeGen(gen));
			gen.addProvider(new LootTableGen(gen));
			gen.addProvider(new BlockTagGen(gen, event.getExistingFileHelper()));
			gen.addProvider(new ItemTagGen(gen, new BlockTagGen(gen, event.getExistingFileHelper()), event.getExistingFileHelper()));
		}
		
		if (event.includeClient())
		{
			gen.addProvider(new BlockStateGen(gen, event.getExistingFileHelper()));
			gen.addProvider(new ItemModelGen(gen, event.getExistingFileHelper()));
		}
	}
	
	private void setup(FMLClientSetupEvent event)
	{
		event.enqueueWork(() -> MenuScreens.register(MenuTypeInit.SELECT_PAINT_BLOCK_MENU_TYPE.get(), SelectPaintBlockScreen::new));
		ClientRegistry.registerKeyBinding(OPEN_GUI);
	}
	
	private void onTick(TickEvent.ClientTickEvent event)
	{
		if (OPEN_GUI.consumeClick())
			CHANNEL.sendToServer(new InputMessage());
	}
	
	public static class PaintBlocksCreativeModeTab extends CreativeModeTab
	{
		public PaintBlocksCreativeModeTab()
		{
			super("paint_blocks");
		}

		@Override
		public ItemStack makeIcon()
		{
			return ItemInit.RAINBOW_BLOCK.get().getDefaultInstance();
		}
	}
	
	public static class InputMessage
	{
		public static void handle(InputMessage message, Supplier<NetworkEvent.Context> context)
		{
			context.get().enqueueWork(() ->
			{
				MenuProvider provider = new MenuProvider()
				{
					@Override
					public TranslatableComponent getDisplayName()
					{
						return new TranslatableComponent("container.paintblocks.select_paint_block");
					}

					@Override
					public SelectPaintBlockMenu createMenu(int id, Inventory inventory, Player player)
					{
						return new SelectPaintBlockMenu(id, inventory, null);
					}
				};
			
				NetworkHooks.openGui(context.get().getSender(), provider);
			});
			context.get().setPacketHandled(true);
		}
	}
	
	public static final Map<String, Integer> HEXES = Map.ofEntries(
		Map.entry("alice_blue", 0xF0F8FF),
		Map.entry("antique_white", 0xFAEBD7),
		Map.entry("aquamarine", 0x7FFFD4),
		Map.entry("azure", 0xF0FFFF),
		Map.entry("beige", 0xF5F5DC),
		Map.entry("bisque", 0xFFE4C4),
		Map.entry("black", 0x000000),
		Map.entry("blanched_almond", 0xFFEBCD),
		Map.entry("blue", 0x0000FF),
		Map.entry("blue_violet", 0x8A2BE2),
		Map.entry("brown", 0xA52A2A),
		Map.entry("burlywood", 0xDEB887),
		Map.entry("cadet_blue", 0x5F9EA0),
		Map.entry("chartreuse", 0x7FFF00),
		Map.entry("chocolate", 0xD2691E),
		Map.entry("coral", 0xFF7F50),
		Map.entry("cornflower_blue", 0x6495ED),
		Map.entry("cornsilk", 0xFFF8DC),
		Map.entry("crimson", 0xDC143C),
		Map.entry("cyan", 0x00FFFF),
		Map.entry("dark_blue", 0x00008B),
		Map.entry("dark_cyan", 0x008B8B),
		Map.entry("dark_goldenrod", 0xB8860B),
		Map.entry("dark_gray", 0xA9A9A9),
		Map.entry("dark_green", 0x006400),
		Map.entry("dark_khaki", 0xBDB76B),
		Map.entry("dark_magenta", 0x8B008B),
		Map.entry("dark_olive_green", 0x556B2F),
		Map.entry("dark_orange", 0xFF8C00),
		Map.entry("dark_orchid", 0x9932CC),
		Map.entry("dark_red", 0x8B0000),
		Map.entry("dark_salmon", 0xE9967A),
		Map.entry("dark_sea_green", 0x8FBC8F),
		Map.entry("dark_slate_blue", 0x483D8B),
		Map.entry("dark_slate_gray", 0x2F4F4F),
		Map.entry("dark_turquoise", 0x00CED1),
		Map.entry("dark_violet", 0x9400D3),
		Map.entry("deep_pink", 0xFF1493),
		Map.entry("deep_sky_blue", 0x00BFFF),
		Map.entry("dim_gray", 0x696969),
		Map.entry("dodger_blue", 0x1E90FF),
		Map.entry("firebrick", 0xB22222),
		Map.entry("floral_white", 0xFFFAF0),
		Map.entry("forest_green", 0x228B22),
		Map.entry("gainsboro", 0xDCDCDC),
		Map.entry("ghost_white", 0xF8F8FF),
		Map.entry("gold", 0xFFD700),
		Map.entry("goldenrod", 0xDAA520),
		Map.entry("gray", 0x808080),
		Map.entry("green", 0x008000),
		Map.entry("green_yellow", 0xADFF2F),
		Map.entry("honeydew", 0xF0FFF0),
		Map.entry("hot_pink", 0xFF69B4),
		Map.entry("indian_red", 0xCD5C5C),
		Map.entry("indigo", 0x4B0082),
		Map.entry("ivory", 0xFFFFF0),
		Map.entry("khaki", 0xF0E68C),
		Map.entry("lavender", 0xE6E6FA),
		Map.entry("lavender_blush", 0xFFF0F5),
		Map.entry("lawn_green", 0x7CFC00),
		Map.entry("lemon_chiffon", 0xFFFACD),
		Map.entry("light_blue", 0xADD8E6),
		Map.entry("light_coral", 0xF08080),
		Map.entry("light_cyan", 0xE0FFFF),
		Map.entry("light_goldenrod_yellow", 0xFAFAD2),
		Map.entry("light_gray", 0xD3D3D3),
		Map.entry("light_green", 0x90EE90),
		Map.entry("light_pink", 0xFFB6C1),
		Map.entry("light_salmon", 0xFFA07A),
		Map.entry("light_sea_green", 0x20B2AA),
		Map.entry("light_sky_blue", 0x87CEFA),
		Map.entry("light_slate_gray", 0x778899),
		Map.entry("light_steel_blue", 0xB0C4DE),
		Map.entry("light_yellow", 0xFFFFE0),
		Map.entry("lime", 0x00FF00),
		Map.entry("lime_green", 0x32CD32),
		Map.entry("linen", 0xFAF0E6),
		Map.entry("magenta", 0xFF00FF),
		Map.entry("maroon", 0x800000),
		Map.entry("medium_aquamarine", 0x66CDAA),
		Map.entry("medium_blue", 0x0000CD),
		Map.entry("medium_orchid", 0xBA55D3),
		Map.entry("medium_purple", 0x9370DB),
		Map.entry("medium_sea_green", 0x3CB371),
		Map.entry("medium_slate_blue", 0x7B68EE),
		Map.entry("medium_spring_green", 0x00FA9A),
		Map.entry("medium_turquoise", 0x48D1CC),
		Map.entry("medium_violet_red", 0xC71585),
		Map.entry("midnight_blue", 0x191970),
		Map.entry("mint_cream", 0xF5FFFA),
		Map.entry("misty_rose", 0xFFE4E1),
		Map.entry("moccasin", 0xFFE4B5),
		Map.entry("navajo_white", 0xFFDEAD),
		Map.entry("navy", 0x000080),
		Map.entry("old_lace", 0xFDF5E6),
		Map.entry("olive", 0x808000),
		Map.entry("olive_drab", 0x6B8E23),
		Map.entry("orange", 0xFFA500),
		Map.entry("orange_red", 0xFF4500),
		Map.entry("orchid", 0xDA70D6),
		Map.entry("pale_goldenrod", 0xEEE8AA),
		Map.entry("pale_green", 0x98FB98),
		Map.entry("pale_turquoise", 0xAFEEEE),
		Map.entry("pale_violet_red", 0xDB7093),
		Map.entry("papaya_whip", 0xFFEFD5),
		Map.entry("peach_puff", 0xFFDAB9),
		Map.entry("peru", 0xCD853F),
		Map.entry("pink", 0xFFC0CB),
		Map.entry("plum", 0xDDA0DD),
		Map.entry("powder_blue", 0xB0E0E6),
		Map.entry("purple", 0x800080),
		Map.entry("rebecca_purple", 0x663399),
		Map.entry("red", 0xFF0000),
		Map.entry("rosy_brown", 0xBC8F8F),
		Map.entry("royal_blue", 0x4169E1),
		Map.entry("saddle_brown", 0x8B4513),
		Map.entry("salmon", 0xFA8072),
		Map.entry("sandy_brown", 0xF4A460),
		Map.entry("sea_green", 0x2E8B57),
		Map.entry("seashell", 0xFFF5EE),
		Map.entry("sienna", 0xA0522D),
		Map.entry("silver", 0xC0C0C0),
		Map.entry("sky_blue", 0x87CEEB),
		Map.entry("slate_blue", 0x6A5ACD),
		Map.entry("slate_gray", 0x708090),
		Map.entry("snow", 0xFFFAFA),
		Map.entry("spring_green", 0x00FF7F),
		Map.entry("steel_blue", 0x4682B4),
		Map.entry("tan", 0xD2B48C),
		Map.entry("teal", 0x008080),
		Map.entry("thistle", 0xD8BFD8),
		Map.entry("tomato", 0xFF6347),
		Map.entry("turquoise", 0x40E0D0),
		Map.entry("violet", 0xEE82EE),
		Map.entry("wheat", 0xF5DEB3),
		Map.entry("white", 0xFFFFFF),
		Map.entry("white_smoke", 0xF5F5F5),
		Map.entry("yellow", 0xFFFF00),
		Map.entry("yellow_green", 0x9ACD32)
	);
}
