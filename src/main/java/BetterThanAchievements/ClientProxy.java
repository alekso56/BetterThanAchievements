package BetterThanAchievements;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ClientProxy extends CommonProxy {
	@Override
	public void setupTextures(){
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BetterThanAchievements.blocky), 0, new ModelResourceLocation(BetterThanAchievements.blocky.getRegistryName(), "normal"));

        List<IResourcePack> DefaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "defaultResourcePacks", "defaultResourcePacks" ) ; 
        DefaultResourcePacks.add(new AchievementsTextLoader()) ;
	}

}
