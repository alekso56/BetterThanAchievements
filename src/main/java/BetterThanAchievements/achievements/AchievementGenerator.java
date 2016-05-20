package BetterThanAchievements.achievements;

import betterachievements.api.components.achievement.ICustomBackgroundColour;
import betterachievements.api.util.ColourHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class AchievementGenerator extends Achievement implements ICustomBackgroundColour{

	public AchievementGenerator(String id, String name, int column, int row, Item item, Achievement parent)
    {
        super(id, name, column, row, item, parent);
    }

    public AchievementGenerator(String id, String name, int column, int row, Block block, Achievement parent)
    {
        super(id, name, column, row, block, parent);
    }

    public AchievementGenerator(String id, String name, int column, int row, ItemStack itemStack, Achievement parent)
    {
        super(id, name, column, row, itemStack, parent);
    }

	@Override
	public int recolourBackground(float greyScale) {
		return ColourHelper.blendWithGreyScale(ColourHelper.RGB(255, 0, 0), greyScale);
	}

}
