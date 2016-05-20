package BetterThanAchievements;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import BetterThanAchievements.achievements.AchievementBPage;
import BetterThanAchievements.proxy.CommonProxy;
import betterachievements.handler.MessageHandler;
import betterachievements.reference.Reference;
import betterachievements.registry.AchievementRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(version = "0.1337", modid = Reference.RESOURCE_ID,name= Reference.ID)
public class BetterThanAchievements {
//goal1: have achievements that can be unlocked by computers .unlockAchievement("playername","achievementname")
//goal2: be able to check achievements from computer .checkAchievement("playername","achievementname")
//goal3: block that outputs redstone signal when the player has the achievement for the set achievement
	@Mod.Instance(Reference.RESOURCE_ID)
    public static BetterThanAchievements instance;
    
    @SidedProxy(serverSide = "BetterThanAchievements.proxy.CommonProxy", clientSide = "BetterThanAchievements.proxy.ClientProxy")
    public static CommonProxy proxy;
    
    public static AchievementCheckBlock blocky;
    public static AchievementAdjusterItem itemy;
    public static AchievementBPage mainpage;
    public static String confpath;
    public static Map<String, StatBase> oneShotStats;
    static enum states {
    	walk,
    	hithead
    };
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        mainpage = new AchievementBPage("BetterThanAchievements",new Achievement("placeholder", "placeholder", 0, 0, Items.gunpowder, null));
        mainpage.registerAchievementPage(mainpage);
        // load config
        Config.loadConfig(event.getSuggestedConfigurationFile().getParentFile());
        //register stuff
        blocky = new AchievementCheckBlock(Material.cloth);
        itemy = new AchievementAdjusterItem(Material.cloth);
        proxy.setupTextures();
        proxy.registerHandlers();
        proxy.initConfig(event.getModConfigurationDirectory());
        MessageHandler.init();
        this.oneShotStats = idmap.getOneShotStats();
    }
    
    public static Achievement getAchievement(int i){
    	if(mainpage.getAchievements().size() > i && mainpage.getAchievements().get(i) != null){
    		return mainpage.getAchievements().get(i);
    	}
    	return null;
    }
    
    public static Long unixtime(){
		return System.currentTimeMillis() / 1000L;
	}
    static String readFile(String path, Charset encoding) 
    		  throws IOException 
    		{
    		  byte[] encoded = Files.readAllBytes(Paths.get(path));
    		  return new String(encoded, encoding);
    		}
    
}
class idmap extends StatList {
    static Map<String, StatBase> getOneShotStats() {
        return StatList.idToStatMap;
    }
}
