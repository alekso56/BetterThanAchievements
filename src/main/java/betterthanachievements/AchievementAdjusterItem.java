package betterthanachievements;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AchievementAdjusterItem extends Item{
	protected AchievementAdjusterItem(Material materialIn) {
		setUnlocalizedName("achadjuster");
		setRegistryName("achadjuster");
		GameRegistry.register(this);
		setHasSubtypes(true);
	}
	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(worldIn != null && !worldIn.isRemote){
			TileEntity tiley = worldIn.getTileEntity(pos);
			if(tiley != null && tiley instanceof AchievementsCheckBlockTilentity){
				AchievementsCheckBlockTilentity tile = (AchievementsCheckBlockTilentity) tiley;
				if(playerIn.isSneaking()){
					Achievement data = BetterThanAchievements.getAchievement(getDamage(stack));
					if(data != null){
						tile.setAchievementInt(getDamage(stack));
						playerIn.addChatMessage(new TextComponentString("Block-Achievement set to: "+data.getStatName().getFormattedText().replace("achievement.", "")));
					}
				}else{
					Achievement data = BetterThanAchievements.getAchievement(tile.getAchievementInt());
					if(data != null){
						playerIn.addChatMessage(new TextComponentString("Block-Achievement is currently: "+data.getStatName().getFormattedText().replace("achievement.", "")));
					}
				}
			}else{
				Achievement data = BetterThanAchievements.getAchievement(getDamage(stack));
				if(data != null){
				 playerIn.addChatMessage(new TextComponentString("Achievement on item is: "+data.getStatName().getFormattedText().replace("achievement.", "")));
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
