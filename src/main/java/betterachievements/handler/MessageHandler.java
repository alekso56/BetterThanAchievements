package betterachievements.handler;

import betterachievements.handler.message.AchievementUnlockMessage;
import betterachievements.handler.message.ConfigFetchMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandler
{
    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper("BTA");
    private static final int ach = 0;
    private static final int conf = 1;
    
    public static void init()
    {
        INSTANCE.registerMessage(AchievementUnlockMessage.class, AchievementUnlockMessage.class, ach, Side.SERVER);
        INSTANCE.registerMessage(ConfigFetchMessage.class, ConfigFetchMessage.class, conf, Side.CLIENT);
    }
}