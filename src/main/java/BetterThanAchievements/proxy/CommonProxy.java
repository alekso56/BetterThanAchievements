package BetterThanAchievements.proxy;

import java.io.File;

import BetterThanAchievements.BetterThanAchievements;
import betterachievements.handler.AchievementHandler;
import betterachievements.handler.ConfigHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public static SoundEvent achievement_hitblock;
	public void setupTextures() {
	}
	public void registerHandlers()
    {
        MinecraftForge.EVENT_BUS.register(AchievementHandler.getInstance());
    }

    public void initConfig(File configDir)
    {
        ConfigHandler.initConfigDir(configDir);
    }
	/**
	 * Register the {@link SoundEvent}s.
	 */
	public static void registerSounds() {
		achievement_hitblock = registerSound("achievement.hitblock");
	}

	/**
	 * Register a {@link SoundEvent}.
	 *
	 * @param soundName The SoundEvent's name without the testmod3 prefix
	 * @return The SoundEvent
	 */
	private static SoundEvent registerSound(String soundName) {
		final ResourceLocation soundID = new ResourceLocation(betterachievements.reference.Reference.RESOURCE_ID, soundName);
		return GameRegistry.register(new SoundEvent(soundID).setRegistryName(soundID));
    }
    }
