package BetterThanAchievements.achievements;

import BetterThanAchievements.BetterThanAchievements;
import betterachievements.api.components.page.ICustomArrows;
import betterachievements.api.components.page.ICustomIcon;
import betterachievements.api.util.ColourHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementBPage extends AchievementPage implements ICustomIcon,ICustomArrows
{
    public AchievementBPage(String name, Achievement... achievements)
    {
        super(name, achievements);
    }

	@Override
	public ItemStack getPageIcon() {
		return new ItemStack(Item.getItemFromBlock(BetterThanAchievements.blocky));
	}

	@Override
	public int getColourForUnlockedArrow() {
		return ColourHelper.RGB(255, 255, 255);
	}

	@Override
	public int getColourForCanUnlockArrow() {
		return ColourHelper.getRainbowColour(0.3F, 0.3F, 0.3F, 0, 2, 4, 128, 127, 50);
	}

	@Override
	public int getColourForCantUnlockArrow() {
		return ColourHelper.RGB(255, 255, 0);
	}
}
