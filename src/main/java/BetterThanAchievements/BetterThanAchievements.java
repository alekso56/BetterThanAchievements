package BetterThanAchievements;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import BetterThanAchievements.achievements.AchievementBPage;
import BetterThanAchievements.achievements.ReloadSyncCommand;
import BetterThanAchievements.opencomputers.AchCard;
import BetterThanAchievements.opencomputers.AchCardItem;
import BetterThanAchievements.proxy.CommonProxy;
import betterachievements.handler.MessageHandler;
import betterachievements.reference.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(version = "0.1337", modid = Reference.RESOURCE_ID,name= Reference.ID, dependencies = "after:OpenComputers")
public class BetterThanAchievements {
//goal3: block that outputs redstone signal when the player has the achievement for the set achievement
	@Mod.Instance(Reference.RESOURCE_ID)
    public static BetterThanAchievements instance;
    
    @SidedProxy(serverSide = Reference.ID+".proxy.CommonProxy", clientSide = Reference.ID+".proxy.ClientProxy")
    public static CommonProxy proxy;
    
    public static AchievementCheckBlock blocky;
    public static AchievementAdjusterItem itemy;
    public static AchCardItem achycardy;
    public static AchievementBPage mainpage;
    public static String confpath;
    public static Map<String, StatBase> oneShotStats;
    public static Map<UUID, Integer> PlayersInServerCrashes; //player, amount of crashes
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        mainpage = new AchievementBPage(Reference.ID,new Achievement(Reference.ID, Reference.ID, 0, 0, Items.GUNPOWDER, null));
        mainpage.registerAchievementPage(mainpage);
        // load config
        proxy.initConfig(event.getModConfigurationDirectory());
        Config.loadConfig(event.getSuggestedConfigurationFile().getParentFile());
        //register stuff
        blocky = new AchievementCheckBlock(Material.CLOTH);
        itemy = new AchievementAdjusterItem(Material.CLOTH);
        if(Loader.isModLoaded("OpenComputers")){
       	 achycardy = new AchCardItem(Material.IRON);
      	}
        proxy.setupTextures();
        proxy.registerHandlers();
        proxy.registerSounds();
        MessageHandler.init();
        this.oneShotStats = idmap.getOneShotStats();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
    	if(Loader.isModLoaded("OpenComputers")){
            li.cil.oc.api.Driver.add(new AchCard());
    	}
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
    
    static Map<String, UUID> invert(Map<UUID, String> map) {

    	Map<String, UUID> hashy = new HashMap<String, UUID>();
		for(Entry<UUID, String> entry : map.entrySet()){
			hashy.put(entry.getValue(), entry.getKey());
    	}
		return hashy;
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	event.registerServerCommand(new ReloadSyncCommand());
    	
    	//here we parse crashlogs for the crash achievements.
    	PlayersInServerCrashes = new HashMap<UUID, Integer>();
    	try {
    		Iterator<java.nio.file.Path> data = Files.list(Paths.get("./crash-reports")).iterator();
    		Map<String, UUID> playerdata = invert(UsernameCache.getMap());
    		while(data.hasNext()){
    			java.nio.file.Path dEntry = data.next();
    			String line;
    			try (
    					InputStream fis = new FileInputStream(dEntry.toFile().getPath());
    					InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
    					BufferedReader br = new BufferedReader(isr);
    					) {
    				while ((line = br.readLine()) != null) {
    					if(line.startsWith("\tPlayer Count") && !line.startsWith("\tPlayer Count: 0")){
    						String lines = line.split("; ",2)[1];
    						String[] players = lines.split("], ");
    						for(String x : players){
    							x = x.replace("lr[","");
    							x = x.replace("GCEntityPlayerMP[", "");
    							x = x.replace("[","");
    							x = x.replace("]", "");

    							String username = x.split(",",2)[0];
    							username = username.replace("'", "");
    							username = username.split("/",2)[0];
    							UUID uuid = playerdata.get(username);
    							if(uuid != null){
    								Integer uuidEntry = PlayersInServerCrashes.containsKey(uuid)?PlayersInServerCrashes.get(uuid):0;
    								uuidEntry = uuidEntry+1;
    								PlayersInServerCrashes.put(uuid, uuidEntry);
    							}
    						}
    					}
    				}
    			}
    		}
    	} catch (IOException e) {}
    }
    public static String readFile(String path, Charset encoding) 
    		  throws IOException 
    		{
    		  byte[] encoded = Files.readAllBytes(Paths.get(path));
    		  return new String(encoded, encoding);
    		}
    
}
class idmap extends StatList {
    static Map<String, StatBase> getOneShotStats() {
        return StatList.ID_TO_STAT_MAP;
    }
}