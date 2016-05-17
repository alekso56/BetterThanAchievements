package BetterThanAchievements;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.relauncher.Side;

public class AchievementCheckBlock extends Block {
	public static final AxisAlignedBB FULL_BLOCK_AABB1 = new AxisAlignedBB(0.0D, 0.1D, 0.0D, 1.0D, 1.0D, 1.0D);
	static Long timestamp = null;

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
	//player walks on block
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn)
	{
		if(!worldIn.isRemote){
			onCollision("collide1");
		}
	}
	//player hits head on block
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(!worldIn.isRemote){
			onCollision("collide2");
		}
	}
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return FULL_BLOCK_AABB1;
	}
	@Override
	public int tickRate(World worldIn)
	{
		return 80;
	}
	void onCollision(String string) {
		if(timestamp == null || BetterThanAchievements.unixtime()-timestamp >= 1L){
			System.out.println(string);
			timestamp = BetterThanAchievements.unixtime();
		}
	}
}
