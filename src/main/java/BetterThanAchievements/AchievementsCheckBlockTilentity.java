package BetterThanAchievements;

import BetterThanAchievements.BetterThanAchievements.states;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AchievementsCheckBlockTilentity extends TileEntity{
	private int achievement;
    private Long timestamp;
    public AchievementsCheckBlockTilentity(){
    	this.achievement = 0;
    }
	@Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        achievement = tag.getInteger("achievement");
    }
	@Override
    public void writeToNBT(NBTTagCompound tag)
    {
        tag.setInteger("achievement", achievement);
        super.writeToNBT(tag);
    }
	@Override
    public boolean shouldRefresh(World world,BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }
	@Override
    public Packet getDescriptionPacket() {
		NBTTagCompound syncData = new NBTTagCompound();
		syncData.setInteger("achievement", this.achievement);
		return new SPacketUpdateTileEntity(this.pos, this.getBlockMetadata(), syncData);
	}
	void setAchievementInt(Integer i){
		this.achievement = i;
	}
	Integer getAchievementInt(){
		return this.achievement;
	}
	void onCollision(states e, EntityPlayerMP player){
		if(timestamp == null || BetterThanAchievements.unixtime()-timestamp >= 1L){
			timestamp = BetterThanAchievements.unixtime();
			Achievement unlockable = BetterThanAchievements.getAchievement(achievement);
			if(unlockable != null){
				if(!player.getStatFile().hasAchievementUnlocked(unlockable)){
					player.addStat(unlockable, 1);
				}}
		}
	}
}
