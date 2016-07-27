package betterthanachievements.opencomputers;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AchCardItem extends Item {
	public AchCardItem(Material materialIn) {
		setUnlocalizedName("achcard");
		setRegistryName("achcard");
		GameRegistry.register(this);
		setCreativeTab(CreativeTabs.REDSTONE);
	}
}
