package BetterThanAchievements.proxy;

import java.io.File;

import BetterThanAchievements.BetterThanAchievements;
import betterachievements.handler.AchievementHandler;
import betterachievements.handler.ConfigHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

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
    }
