package betterthanachievements.opencomputers;

import betterthanachievements.BetterThanAchievements;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AchCardItem extends Item {
	public AchCardItem(Material materialIn) {
		setUnlocalizedName("achcard");
		setRegistryName("achcard");
		GameRegistry.register(this);
		setCreativeTab(BetterThanAchievements.AchTab);
	}
}
