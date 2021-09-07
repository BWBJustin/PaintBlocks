package com.bwbjustin.paintblocks.core.datagen;

import java.util.function.Consumer;

import com.bwbjustin.paintblocks.core.init.BlockInit;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

public class RecipeGen extends RecipeProvider
{
	public RecipeGen(DataGenerator gen)
	{
		super(gen);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
	{
		ShapedRecipeBuilder.shaped(BlockInit.RAINBOW_BLOCK.get())
		.pattern("123")
		.pattern("456")
		.pattern("789")
		.define('1', Items.RED_DYE)
		.define('2', Items.ORANGE_DYE)
		.define('3', Items.YELLOW_DYE)
		.define('4', Items.LIME_DYE)
		.define('5', Items.WHITE_CONCRETE)
		.define('6', Items.GREEN_DYE)
		.define('7', Items.LIGHT_BLUE_DYE)
		.define('8', Items.BLUE_DYE)
		.define('9', Items.PURPLE_DYE)
		.unlockedBy("has_item", has(Items.WHITE_CONCRETE))
		.save(consumer);
	}
}
