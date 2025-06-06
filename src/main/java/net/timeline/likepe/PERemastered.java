package net.timeline.likepe;

import net.fabricmc.api.ModInitializer;

import net.timeline.likepe.block.ModBlocks;
import net.timeline.likepe.item.ModItemGroups;
import net.timeline.likepe.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PERemastered implements ModInitializer {
	public static final String MOD_ID = "likepe";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {


		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

	}
}