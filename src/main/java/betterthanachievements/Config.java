package betterthanachievements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.compress.utils.IOUtils;

import betterachievements.api.util.ColourHelper;
import betterthanachievements.achievements.AchievementGenerator;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Config {
	public static void loadConfig(File confug) {
		BetterThanAchievements.confpath = confug.getPath();
		File conf = new File(confug.getPath() + "/betterthanachievements/betterthanachievements.cfg");
		String[] achs = {""};
		if(!conf.exists()){
			try {
				conf.getParentFile().mkdir();
				conf.createNewFile();
				OutputStream output = new FileOutputStream(conf);
				InputStream stream = BetterThanAchievements.class.getResourceAsStream("/betterthanachievements.cfg");
				IOUtils.copy(stream, output);
				output.flush();
				output.close();
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Configuration config = new Configuration(conf);
		config.getBoolean("override", "general", true,
				"Server can override this config and reload your achievements on the run?");
		String[] list = config.getStringList("Achievements", "general", achs,
				"(Input the format like) String-internalname: String-name : String-description : String-icon : int-metadata: int-posy : int-posx : boolean-special : string-ParentInternalName:#RRGGBB Backgroundcolor:String statid:int count");
		config.save();
		for (Achievement x : BetterThanAchievements.mainpage.getAchievements()) {
			AchievementList.ACHIEVEMENTS.remove(x);
			StatList.ALL_STATS.remove(x);
			if (BetterThanAchievements.instance.oneShotStats != null) {
				BetterThanAchievements.instance.oneShotStats.remove(x.statId);
			}
		}

		try {
			// prepare folders and files
			ArrayList<AchievementGenerator> achievements = new ArrayList<AchievementGenerator>();
			File f = new File(confug.getPath() + "/betterthanachievements/lang/en_US.lang");
			if (!f.exists()) {
				new File(confug.getPath() + "/betterthanachievements/lang").mkdir();
				f.createNewFile();
			}
			FileWriter fstream = new FileWriter(f, false);
			BufferedWriter out = new BufferedWriter(fstream);
			// iterate over config file
			for (String entry : list) {
				String[] dataEntry = entry.split(",");
				if (dataEntry.length > 8) {
					try {
						String internalname = dataEntry[0];
						String name = dataEntry[1];
						String description = dataEntry[2];
						String iconName = dataEntry[3];
						Integer metadata = Integer.parseInt(dataEntry[4]);
						Integer posy = Integer.parseInt(dataEntry[5]);
						Integer posx = Integer.parseInt(dataEntry[6]);
						Boolean special = Boolean.valueOf(dataEntry[7]);
						String parentListInternalName = dataEntry[8];

						Item item = (Item) Item.REGISTRY.getObject(new ResourceLocation(iconName));
						ItemStack achievementitem;
						if (item == null) {
							achievementitem = new ItemStack(Items.BONE);
						}else{
							achievementitem = new ItemStack(item);
							achievementitem.setItemDamage(metadata);
						}
						AchievementGenerator parent = null;
						if(!parentListInternalName.isEmpty() && !parentListInternalName.equals("null")){
							for(AchievementGenerator achievement:achievements){
								if(achievement.getStatName().equals("achievement."+parentListInternalName)){
									parent = achievement;
									break;
								}
							}
						}

						AchievementGenerator st = new AchievementGenerator("achievement." + internalname, internalname,
								posy, posx, achievementitem, parent);
						if (special) {
							st.setSpecial();
						}
						if (st.parentAchievement == null) {
							st.initIndependentStat();
						}
						if (dataEntry.length > 9) {
							try {
								String backgroundcolor = dataEntry[9];
								Integer color = ColourHelper.RGB(backgroundcolor);
								st.setBackgroundColor(color);
							} catch (IllegalArgumentException e) {

							}
						}
						if (dataEntry.length > 11) {
							StatBase staty = StatList.getOneShotStat(dataEntry[10]);
							if (staty != null) {
								int county = Integer.parseInt(dataEntry[11]);
								st.setStatToCheck(staty.statId);
								st.setStatCount(county);
							}
						}
						st.registerStat();
						achievements.add(st);
						// write to language file
						out.write("achievement." + internalname + "=" + name);
						out.newLine();
						out.write("achievement." + internalname + ".desc=" + description);
						out.newLine();
						// done :p
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
			out.close();
			BetterThanAchievements.mainpage.getAchievements().clear();
			BetterThanAchievements.mainpage.getAchievements().addAll(achievements);

			/*
			 * File w = new
			 * File(confug.getPath()+"/betterthanachievements/wat.lang");
			 * if(!w.exists()){ w.createNewFile(); } FileWriter fsdtream = new
			 * FileWriter(w, false); BufferedWriter ouet = new
			 * BufferedWriter(fsdtream); for(StatBase x: StatList.ALL_STATS){
			 * ouet.write(x.statId+"  "
			 * +(x.isAchievement()?((Achievement)x).theItemStack.getItem().
			 * getRegistryName():"")); ouet.newLine(); } ouet.close();
			 */
		} catch (IOException e) {

		}
	}

}
