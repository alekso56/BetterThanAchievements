package BetterThanAchievements.achievements;

import betterachievements.api.components.achievement.ICustomBackgroundColour;
import betterachievements.api.util.ColourHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public class AchievementGenerator extends Achievement implements ICustomBackgroundColour{
    Integer color = 0;
    Integer statRepeatCount = null;
    String statid = null;
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
    
    public void setBackgroundColor(Integer d){
    	this.color = d;
    }

	@Override
	public int recolourBackground(float greyScale) {
		return ColourHelper.blendWithGreyScale(color, greyScale);
	}

	public void setStatCount(int parseInt) {
		this.statRepeatCount = parseInt;
	}

	public void setStatToCheck(String statId) {
		this.statid = statId;
	}

}
