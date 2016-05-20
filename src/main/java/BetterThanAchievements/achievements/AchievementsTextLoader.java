package BetterThanAchievements.achievements;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Sets;

import BetterThanAchievements.BetterThanAchievements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class AchievementsTextLoader implements IResourcePack{

	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {
		if(!resourceExists(location)){
			return null;
		}
		File f = new File(BetterThanAchievements.confpath+"/betterthanachievements/lang/en_US.lang");
		if(!f.exists()){
			new File(BetterThanAchievements.confpath+"/betterthanachievements/lang").mkdir();
			f.createNewFile();
		}
		 return new FileInputStream(f);
	}

	@Override
	public boolean resourceExists(ResourceLocation location) {
		if(location.toString().equals("betterthanachievementz:lang/en_US.lang")){
			return true;
		}
		return false;
	}

	@Override
	public Set<String> getResourceDomains() {
		 LinkedHashSet<String> x = Sets.<String>newLinkedHashSet();
		 x.add("betterthanachievementz");
		return x;
	}

	@Override
	public String getPackName() {
		return "BetterThanAchievementsLanguage";
	}
	
	@Override
	public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer metadataSerializer,
			String metadataSectionName) throws IOException {return null;}

	@Override
	public BufferedImage getPackImage() throws IOException {return null;}

}
