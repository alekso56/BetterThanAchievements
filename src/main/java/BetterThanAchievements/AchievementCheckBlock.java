package BetterThanAchievements;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class AchievementCheckBlock extends Block {

	protected AchievementCheckBlock(Material materialIn) {
		super(Material.cloth);
		setHardness(1.5f);
		setUnlocalizedName("checkblock");
		setRegistryName("checkblock");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(getRegistryName()));
		setCreativeTab(CreativeTabs.tabRedstone);
		setStepSound(SoundType.CLOTH);
	}
	 @Override
	 public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	    {
		 System.out.println("act!");
	        return false;
	    }
	 /**
	     * How many world ticks before ticking
	     */
	 @Override
	    public int tickRate(World worldIn)
	    {
	        return 40;
	    }
    /**
     * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
     * on its own
     */
    @Override
    public void onLanded(World worldIn, Entity entityIn)
    {
        entityIn.motionY = 0.0D;
        
        System.out.println("Landed!");
    }
}
