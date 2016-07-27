package betterthanachievements;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AchievementCheckBlock extends Block {
	public static final AxisAlignedBB FULL_BLOCK_AABB1 = new AxisAlignedBB(0.0D, 0.1D, 0.0D, 1.0D, 0.9D, 1.0D);

	protected AchievementCheckBlock(Material materialIn) {
		super(Material.CLOTH);
		setHardness(1.5f);
		setUnlocalizedName("checkblock");
		setRegistryName("checkblock");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(getRegistryName()));
		GameRegistry.registerTileEntity(AchievementsCheckBlockTilentity.class, "betterthanachievements:checkblock");
		setCreativeTab(BetterThanAchievements.AchTab);
		setTickRandomly(false);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		collisionEvent(worldIn, pos, entityIn);
	}

	void collisionEvent(World worldIn, BlockPos pos, Entity entityIn) {
		if (worldIn != null && !worldIn.isRemote) {
			if (entityIn instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) entityIn;
				if (worldIn.getTileEntity(pos) != null) {
					AchievementsCheckBlockTilentity tile = (AchievementsCheckBlockTilentity) worldIn.getTileEntity(pos);
					tile.onCollision(player);
				}

			}
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new AchievementsCheckBlockTilentity();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB1;
	}
}
