package com.bwbjustin.paintblocks.core.datagen;

import java.io.IOException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;

import com.bwbjustin.paintblocks.core.init.BlockInit;
import com.google.gson.GsonBuilder;

import net.minecraft.data.*;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class LootTableGen extends LootTableProvider
{
	private final DataGenerator generator;
	
	public LootTableGen(DataGenerator gen)
	{
		super(gen);
		generator = gen;
	}
	
	@Override
	public void run(HashCache cache)
	{
		HashMap<ResourceLocation, LootTable> tables = new HashMap<>(140);
		
		BlockInit.BLOCKS.getEntries().forEach(block ->
		{
			tables.put(block.get().getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool()
				.name(block.getId().getPath())
				.setRolls(ConstantValue.exactly(1))
				.add(LootItem.lootTableItem(block.get()))
			).setParamSet(LootContextParamSets.BLOCK).build());
		});
		
		tables.forEach((key, table) ->
		{
			try
			{
				DataProvider.save(
					new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(),
					cache,
					LootTables.serialize(table),
					generator.getOutputFolder().resolve("data/"+key.getNamespace()+"/loot_tables/"+key.getPath()+".json")
				);
			}
			catch (IOException e)
			{
				LogManager.getLogger().error("Error writing loot table! {}", e);
			}
		});
	}
}
