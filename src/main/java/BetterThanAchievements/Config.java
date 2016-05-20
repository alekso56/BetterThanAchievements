package BetterThanAchievements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import BetterThanAchievements.achievements.AchievementBPage;
import BetterThanAchievements.achievements.AchievementGenerator;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.config.Configuration;

public class Config {
	public static void loadConfig(File confug) {
		BetterThanAchievements.confpath = confug.getPath();
		File conf = new File(confug.getPath()+"/betterthanachievements/betterthanachievements.cfg");
		if(!conf.exists()) {
			try {
				new File(confug.getPath()+"/betterthanachievements").mkdir();
				conf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Configuration config = new Configuration(conf);
		String[] list = config.getStringList("Achievements", "general",new String[]{"hit-block:Adventure !:The start of something great!:redstone:0:0:false:-1:"}, "(Input the format like) String-internalname: String-name : String-description : String-icon : int-posy : int-posx : boolean-special : int-ParentInternal:String Backgroundcolor:String statid:int count");
		config.save();
		for(Achievement x : BetterThanAchievements.mainpage.getAchievements()){
			AchievementList.ACHIEVEMENTS.remove(x);
	        StatList.allStats.remove(x);
	        if( BetterThanAchievements.instance.oneShotStats != null){
	         BetterThanAchievements.instance.oneShotStats.remove(x.statId);
	        }
		}
		try{
			//prepare folders and files
            ArrayList<AchievementGenerator> achievements = new ArrayList<AchievementGenerator>();
			File f = new File(confug.getPath()+"/betterthanachievements/lang/en_US.lang");
			if(!f.exists()){
				new File(confug.getPath()+"/betterthanachievements/lang").mkdir();
				f.createNewFile();
			}
			FileWriter fstream = new FileWriter(f, false);
			BufferedWriter out = new BufferedWriter(fstream);
			//iterate over config file
			for(String entry: list){
				String[] dataEntry = entry.split(":");
				if(dataEntry.length == 8){
					try{
						String internalname = dataEntry[0];
						String name = dataEntry[1];
						String description = dataEntry[2];
						String iconName = dataEntry[3];
						Integer posy = Integer.parseInt(dataEntry[4]);
						Integer posx = Integer.parseInt(dataEntry[5]);
						Boolean special = Boolean.valueOf(dataEntry[6]);
						Integer parentListNumber =  Integer.parseInt(dataEntry[7]);

						Item item = (Item)Item.itemRegistry.getObject(new ResourceLocation(iconName));
						if(item == null){
							item = Items.bone;
						}
						AchievementGenerator st = new AchievementGenerator("achievement."+internalname, internalname, posy, posx, item, parentListNumber==-1?null:achievements.get(parentListNumber));
						//Achievement rehash  =  new Achievement("achievement."+internalname, internalname, posx, posy, item, parentListNumber==-1?null:achievements[parentListNumber]);
						if(special){
							st.setSpecial();
						}
						if(st.parentAchievement == null){
							st.initIndependentStat();
						}
						
						st.registerStat();
						achievements.add(st);
						//write to language file
						out.write("achievement."+internalname+"="+name);
						out.newLine();
						out.write("achievement."+internalname+".desc="+description);
						out.newLine();
						//done :pz
					}catch(NumberFormatException e){
						e.printStackTrace();
					}
				}
			}
			out.close();
			BetterThanAchievements.mainpage.getAchievements().clear();
			BetterThanAchievements.mainpage.getAchievements().addAll(achievements);
		}catch(IOException e){

		}
	}

}
