package BetterThanAchievements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(version = "0.1337", modid = "betterthanachievements",name= "BetterThanAchievements")
public class BetterThanAchievements {
//goal: have achievements that can be unlocked by stepping on a block
//goal1: have achievements that can be unlocked by computers .unlockAchievement("playername","achievementname")
//goal2: be able to check achievements from computer .checkAchievement("playername","achievementname")
//goal3: block that outputs redstone signal when the player has the achievement for the set achievement
//goal4: config to specify achievements
	@Mod.Instance("betterthanachievements")
    public static BetterThanAchievements instance;
    
    @SidedProxy(serverSide = "BetterThanAchievements.CommonProxy", clientSide = "BetterThanAchievements.ClientProxy")
    public static CommonProxy proxy;
    
    public static AchievementCheckBlock blocky;
    public static AchievementPage mainpage;
    static String confpath;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // load config
        Config.loadConfig(event.getSuggestedConfigurationFile().getParentFile());
        //register stuff
        blocky = new AchievementCheckBlock(Material.cloth);
        proxy.setupTextures();
        mainpage.
    }
    
    public static Long unixtime(){
		return System.currentTimeMillis() / 1000L;
	}
    
}