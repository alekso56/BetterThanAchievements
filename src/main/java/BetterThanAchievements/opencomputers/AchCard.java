package BetterThanAchievements.opencomputers;

import BetterThanAchievements.BetterThanAchievements;
import li.cil.oc.api.Network;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.DriverItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatBase;
import net.minecraftforge.fml.server.FMLServerHandler;

public class AchCard extends DriverItem {
	public AchCard() {
        super(new ItemStack(BetterThanAchievements.achycardy));
    }
	@Override
	public ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
		return new Environment(host);
	}

	@Override
	public String slot(ItemStack stack) {
		return Slot.Card;
	}
	public static EntityPlayerMP getPlayer(String toMatch){
		for(EntityPlayerMP player : FMLServerHandler.instance().getServer().getPlayerList().getPlayerList()){
			if(player.getName().equals(toMatch)){
				return player;
			}
		}
		return null;
	}
	public static Achievement getAchievement(String achievement){
		StatBase x = BetterThanAchievements.oneShotStats.get("achievement."+achievement);
		if(x != null && x.isAchievement()){
			Achievement y = (Achievement) x;
			return y;
		}
		return null;
	}
    public class Environment extends li.cil.oc.api.prefab.ManagedEnvironment {
        public Environment(EnvironmentHost container) {
            this.setNode(Network.newNode(this, Visibility.Neighbors).
                    withComponent("ach").
                    create());
        }
        //goal1: have achievements that can be unlocked by computers .unlockAchievement("playername","achievementname")
        @Callback(direct = true, limit = 1)
        public Object[] unlockAchievement(Context context, Arguments args) {
        	String playername = args.checkString(0);
        	String achievementname = args.checkString(1);
        	Achievement achievement = getAchievement(achievementname);
        	if(achievement != null){
        		EntityPlayerMP player = getPlayer(playername);
        		if(player !=null){
        			player.getStatFile().unlockAchievement(player, achievement, 1);
        		}
        	}
        	return null;

        }
        //goal2: be able to check achievements from computer .checkAchievement("playername","achievementname")
        @Callback(direct = true, limit = 1)
        public Object[] checkAchievement(Context context, Arguments args) {
        	String playername = args.checkString(0);
        	String achievementname = args.checkString(1);
        	Achievement achievement = getAchievement(achievementname);
        	if(achievement != null){
        		EntityPlayerMP player = getPlayer(playername);
        		if(player !=null){
        			Object[] result = new Object[]{
        			player.getStatFile().hasAchievementUnlocked(achievement),
        			player.getStatFile().countRequirementsUntilAvailable(achievement)};
        		}
        	}
			return null;
            
        }
    }
}
