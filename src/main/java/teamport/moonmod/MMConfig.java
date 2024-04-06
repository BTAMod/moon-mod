package teamport.moonmod;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static teamport.moonmod.MoonMod.MOD_ID;

public class MMConfig {
	private static final Toml properties = new Toml("Moon Mod's TOML Config");
	public static TomlConfigHandler cfg;

	static {
		properties.addCategory("IDs")
			.addEntry("startingItemID", 16600)
			.addEntry("startingBlockID", 2000)
			.addEntry("dimension", 3)
			.addEntry("ufo", 200);

		cfg = new TomlConfigHandler(MOD_ID, properties);
	}
}
