package BetterThanAchievements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import betterachievements.handler.MessageHandler;
import betterachievements.handler.message.AchievementUnlockMessage;
import betterachievements.handler.message.ConfigFetchMessage;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AchievementAdjusterItem extends Item{
	public Integer achievement;
	protected AchievementAdjusterItem(Material materialIn) {
		setUnlocalizedName("achadjuster");
		setRegistryName("achadjuster");
		GameRegistry.register(this);
		setCreativeTab(CreativeTabs.tabRedstone);
		this.achievement = 0;
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
					Achievement data = BetterThanAchievements.getAchievement(achievement);
					if(data != null){
						tile.setAchievementInt(achievement);
						playerIn.addChatMessage(new TextComponentString("Block-Achievement set to: "+data.getStatName().getFormattedText()));
					}
				}else{
					Achievement data = BetterThanAchievements.getAchievement(tile.getAchievementInt());
					if(data != null){
						playerIn.addChatMessage(new TextComponentString("Block-Achievement is currently: "+data.getStatName().getFormattedText()));
					}
				}
			}else{
				if(BetterThanAchievements.mainpage.getAchievements().size()-1 > achievement){
					achievement++;
				}else{
					achievement = 0;
				}
				Achievement data = BetterThanAchievements.getAchievement(achievement);
				if(data != null){
				 playerIn.addChatMessage(new TextComponentString("Achievement set to: "+data.getStatName().getFormattedText()));
				}
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
