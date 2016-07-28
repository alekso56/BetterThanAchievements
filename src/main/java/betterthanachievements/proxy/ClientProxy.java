package betterthanachievements.proxy;

import java.io.File;
import java.util.List;

import betterachievements.handler.ConfigHandler;
import betterachievements.handler.GuiOpenHandler;
import betterachievements.handler.SaveHandler;
import betterthanachievements.BetterThanAchievements;
import betterthanachievements.achievements.AchievementsTextLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ClientProxy extends CommonProxy {
	@Override
	public void setupTextures() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BetterThanAchievements.blocky), 0,
				new ModelResourceLocation(BetterThanAchievements.blocky.getRegistryName(), "normal"));
		ModelResourceLocation resource = new ModelResourceLocation(BetterThanAchievements.itemy.getRegistryName(), "normal");
		ModelLoader.setCustomMeshDefinition(BetterThanAchievements.itemy,  MeshDefinitionFix.create(stack -> resource));
		ModelBakery.registerItemVariants(BetterThanAchievements.itemy, resource);
		List<IResourcePack> DefaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class,
				Minecraft.getMinecraft(), "field_110449_ao", "field_110449_ao");
		DefaultResourcePacks.add(new AchievementsTextLoader());
		if (Loader.isModLoaded("OpenComputers")) {
			ModelLoader.setCustomModelResourceLocation(BetterThanAchievements.achycardy, 0,
					new ModelResourceLocation(BetterThanAchievements.achycardy.getRegistryName(), "normal"));
		}
	}

	@Override
	public void registerHandlers() {
		super.registerHandlers();
		MinecraftForge.EVENT_BUS.register(new GuiOpenHandler());
		MinecraftForge.EVENT_BUS.register(new SaveHandler());
	}

	@Override
	public void initConfig(String configDir) {
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());
	}

}
