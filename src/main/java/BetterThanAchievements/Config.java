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
import java.util.Random;

import BetterThanAchievements.achievements.AchievementBPage;
import BetterThanAchievements.achievements.AchievementGenerator;
import betterachievements.api.util.ColourHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
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
		config.getBoolean("override", "general", true, "Server can override this config and reload your achievements on the run?");
		List<String> achlist = new ArrayList<String>();
		//booth vist achs
		String[] booths = new String[]{"Actually Additions",
				"Additional Banners",
				"Automated Redstone",
				"Autoverse",
				"BetterMinecraftChat",
				"BetterThanAchievements",
				"Charset",
				"Chisel",
				"Cooking for Blockheads",
				"Cömputronics",
				"Correlated Potentialistics",
				"Crafting Table IV",
				"Crafting Tweaks",
				"Dark Utilities",
				"Deep Resonance",
				"E-Flux",
				"Ender IO",
				"Ender IO Addons",
				"EnderStorage",
				"Ender Utilities",
				"Extra Food",
				"Fire's Random Things",
				"Flamingo",
				"Fluxed Crystals 3",
				"Forestry",
				"Framez 3",
				"HolographicVictoryMonument",
				"Inductive Automation",
				"IndustrialCraft 2",
				"Integrated Dynamics",
				"Magneticraft",
				"MahMeat",
				"Metallurgy 5",
				"More Anvils",
				"NotEnoughWands",
				"OpenAutomation Reborn",
				"OpenComputers",
				"OpenComputers MIPS",
				"OpenRadio",
				"Parcels and Packages",
				"Progressive Automation",
				"Quaritum",
				"Random Things",
				"Rarmor",
				"RFTools",
				"Roots",
				"Simple Grinder",
				"Sprockets",
				"Soul Shards: The Old Ways",
				"Starvation Ahoy",
				"Taam",
				"???",
				"TechReborn",
				"The One Probe",
				"TIS-3D",
				"Totemic",
				"Translocators",
				"TrashSlot",
				"Wireless Redstone - CBE",
				"XNet",
				"YeOldeTanks"};
		Integer achy = -10;
		Integer row = 0;
		Random rand = new Random();
		
		for(String modbooth :booths){
			achlist.add(modbooth.replace(" ", "")+","+modbooth+"!,Visited the "+modbooth+" booth :p,redstone,"+achy+","+row+",false,-1,"+ColourHelper.hex(rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1)));
		    achy++;achy++;
		    if(achy == 0){
		    	row++;
		    	achy = -10;
		    }else if(achy == 10){
		    	row = row+2;
		    	achy = -10;
		    }else if(achy == 20){
		    	row++;
		    	achy = -10;
		    }else if(achy == 30){
		    	row = row+2;
		    	achy = -10;
		    }else if(achy == 40){
		    	row++;
		    	achy = -10;
		    }else if(achy == 50){
		    	row = row+2;
		    	achy = -10;
		    }else if(achy == 60){
		    	row++;
		    	achy = -10;
		    }
		}
		achlist.add("servercrash1,Crashy one!,Survied the first servercrash :O!,barrier,0,0,true,-1,"+ColourHelper.hex(rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1)));
		achlist.add("servercrash2,Crashy two?,Survied the second servercrash :O!,barrier,0,2,true,"+(achlist.size()-1)+","+ColourHelper.hex(rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1)));
		achlist.add("servercrash5,Crash team!,Survied the fifth servercrash :O!,barrier,0,4,true,"+(achlist.size()-1)+","+ColourHelper.hex(rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1)));
		achlist.add("servercrash10,Crash apocalypse!,Survied the tenth servercrash :O!,barrier,0,6,true,"+(achlist.size()-1)+","+ColourHelper.hex(rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1)));
		achlist.add("servercrash25,Crashtacular!,Survied the 25th servercrash :O!,barrier,0,8,true,"+(achlist.size()-1)+","+ColourHelper.hex(rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1), rand.nextInt((255 - 0) + 1)));
		String[] achs = achlist.toArray(new String[achlist.size()]);
		String[] list = config.getStringList("Achievements", "general",achs,"(Input the format like) String-internalname: String-name : String-description : String-icon : int-posy : int-posx : boolean-special : int-ParentInternal:#RRGGBB Backgroundcolor:String statid:int count");
		config.save();
		for(Achievement x : BetterThanAchievements.mainpage.getAchievements()){
			AchievementList.ACHIEVEMENTS.remove(x);
	        StatList.ALL_STATS.remove(x);
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
				String[] dataEntry = entry.split(",");
				if(dataEntry.length > 7){
					try{
						String internalname = dataEntry[0];
						String name = dataEntry[1];
						String description = dataEntry[2];
						String iconName = dataEntry[3];
						Integer posy = Integer.parseInt(dataEntry[4]);
						Integer posx = Integer.parseInt(dataEntry[5]);
						Boolean special = Boolean.valueOf(dataEntry[6]);
						Integer parentListNumber =  Integer.parseInt(dataEntry[7]);

						Item item = (Item)Item.REGISTRY.getObject(new ResourceLocation(iconName));
						if(item == null){
							item = Items.BONE;
						}

						AchievementGenerator st = new AchievementGenerator("achievement."+internalname, internalname, posy, posx, item, parentListNumber==-1?null:achievements.get(parentListNumber));
						if(special){
							st.setSpecial();
						}
						if(st.parentAchievement == null){
							st.initIndependentStat();
						}
						if(dataEntry.length > 8){
							try{
								String backgroundcolor =  dataEntry[8];
								Integer color = ColourHelper.RGB(backgroundcolor);
								st.setBackgroundColor(color);
							}catch(IllegalArgumentException e){

							}
						}
						if(dataEntry.length > 10){ 
							StatBase staty = StatList.getOneShotStat(dataEntry[9]);
							if (staty != null) {
								int county = Integer.parseInt(dataEntry[10]);
								st.setStatToCheck(staty.statId);
								st.setStatCount(county);
							}
						}
						st.registerStat();
						achievements.add(st);
						//write to language file
						out.write("achievement."+internalname+"="+name);
						out.newLine();
						out.write("achievement."+internalname+".desc="+description);
						out.newLine();
						//done :p
					}catch(NumberFormatException e){
						e.printStackTrace();
					}
				}
			}
			out.close();
			BetterThanAchievements.mainpage.getAchievements().clear();
			BetterThanAchievements.mainpage.getAchievements().addAll(achievements);
			
			/*File w = new File(confug.getPath()+"/betterthanachievements/wat.lang");
			if(!w.exists()){
				w.createNewFile();
			}
			FileWriter fsdtream = new FileWriter(w, false);
			BufferedWriter ouet = new BufferedWriter(fsdtream);
			for(StatBase x: StatList.ALL_STATS){
				ouet.write(x.statId+"  "+(x.isAchievement()?((Achievement)x).theItemStack.getItem().getRegistryName():""));
				ouet.newLine();
			}
			ouet.close();
			*/
		}catch(IOException e){

		}
	}

}
