package betterachievements.handler;

import betterachievements.api.util.ColourHelper;
import betterachievements.gui.GuiBetterAchievements;
import betterachievements.reference.Reference;
import betterachievements.registry.AchievementRegistry;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler
{
    public static Configuration config;
    private static File configDir;

    public static void init()
    {
        if (config == null)
        {
            config = new Configuration(new File(configDir, Reference.ID + ".cfg"));
            loadConfig();
        }
    }

    public static void initConfigDir(File configDir)
    {
        configDir = new File(configDir, Reference.ID);
        configDir.mkdir();
        ConfigHandler.configDir = configDir;
    }

    public static File getConfigDir()
    {
        return configDir;
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equalsIgnoreCase(Reference.ID))
        {
            loadConfig();
        }
    }

    private static void loadConfig()
    {
        Property prop;
        String colourCode;

        prop = config.get(Configuration.CATEGORY_GENERAL, "scrollButtons", true);
        prop.setComment(I18n.translateToLocal("config.scrollButtons.desc"));
        prop.setLanguageKey("config.scrollButtons");
        GuiBetterAchievements.scrollButtons = prop.getBoolean();

        prop = config.get(Configuration.CATEGORY_GENERAL, "cantUnlockArrowColour", "#000000");
        prop.setComment(I18n.translateToLocal("config.cantUnlockArrowColour.desc"));
        prop.setLanguageKey("config.cantUnlockArrowColour");
        colourCode = prop.getString();
        if (colourCode.startsWith("#"))
        {
            GuiBetterAchievements.colourCantUnlockRainbow = false;
            GuiBetterAchievements.colourCantUnlock = ColourHelper.RGB(colourCode);
        }
        else if (colourCode.startsWith("rainbow"))
        {
            GuiBetterAchievements.colourCantUnlockRainbow = true;
            GuiBetterAchievements.colourCantUnlockRainbowSettings = ColourHelper.getRainbowSettings(colourCode);
        }

        prop = config.get(Configuration.CATEGORY_GENERAL, "canUnlockArrowColour", "#00FF00");
        prop.setComment(I18n.translateToLocal("config.canUnlockArrowColour.desc"));
        prop.setLanguageKey("config.canUnlockArrowColour");
        colourCode = prop.getString();
        if (colourCode.startsWith("#"))
        {
            GuiBetterAchievements.colourCanUnlockRainbow = false;
            GuiBetterAchievements.colourCanUnlock = ColourHelper.RGB(colourCode);
        }
        else if (colourCode.startsWith("rainbow"))
        {
            GuiBetterAchievements.colourCanUnlockRainbow = true;
            GuiBetterAchievements.colourCanUnlockRainbowSettings = ColourHelper.getRainbowSettings(colourCode);
        }

        prop = config.get(Configuration.CATEGORY_GENERAL, "completeArrowColour", "#A0A0A0");
        prop.setComment(I18n.translateToLocal("config.completeArrowColour.desc"));
        prop.setLanguageKey("config.completeArrowColour");
        colourCode = prop.getString();
        if (colourCode.startsWith("#"))
        {
            GuiBetterAchievements.colourUnlockedRainbow = false;
            GuiBetterAchievements.colourUnlocked = ColourHelper.RGB(colourCode);
        }
        else if (colourCode.startsWith("rainbow"))
        {
            GuiBetterAchievements.colourUnlockedRainbow = true;
            GuiBetterAchievements.colourUnlockedRainbowSettings = ColourHelper.getRainbowSettings(colourCode);
        }

        prop = config.get(Configuration.CATEGORY_GENERAL, "userColourOverride", false);
        prop.setComment(I18n.translateToLocal("config.userColourOverride.desc"));
        prop.setLanguageKey("config.userColourOverride");
        GuiBetterAchievements.userColourOverride = prop.getBoolean();

        prop = config.get(Configuration.CATEGORY_GENERAL, "iconReset", false);
        prop.setComment(I18n.translateToLocal("config.iconReset.desc"));
        prop.setLanguageKey("config.iconReset");
        GuiBetterAchievements.iconReset = prop.getBoolean();

        prop = config.get(Configuration.CATEGORY_GENERAL, "listTabIcons", new String[0]);
        prop.setComment(I18n.translateToLocal("config.listTabIcons.desc"));
        prop.setLanguageKey("config.listTabIcons");
        SaveHandler.userSetIcons = prop.getStringList();

        if (config.hasChanged())
            config.save();
    }

    public static void saveUserSetIcons()
    {
        SaveHandler.userSetIcons = AchievementRegistry.instance().dumpUserSetIcons();

        Property prop = config.get(Configuration.CATEGORY_GENERAL, "listTabIcons", new String[0]);
        prop.setComment(I18n.translateToLocal("config.listTabIcons.desc"));
        prop.setLanguageKey("config.listTabIcons");
        prop.set(SaveHandler.userSetIcons);

        config.save();
    }

    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        return list;
    }
}
