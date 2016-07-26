package betterachievements.handler;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import BetterThanAchievements.BetterThanAchievements;
import betterachievements.util.LogHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsManager;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class AchievementHandler
{
    private final Map<UUID, Set<Achievement>> playerAchievementMap;
    private final Set<UUID> currentItrs;
    private static final String FILENAME = "toUnlockAchievements.dat";
    private static AchievementHandler instance;

    public static AchievementHandler getInstance()
    {
        if (instance == null)
            instance = new AchievementHandler();
        return instance;
    }

    private AchievementHandler()
    {
        this.playerAchievementMap = new HashMap<UUID, Set<Achievement>>();
        this.currentItrs = new HashSet<UUID>();
    }
    
    @SubscribeEvent
    public void playerjoin(PlayerLoggedInEvent ev){
    	Integer CrashSurvivalCount = BetterThanAchievements.PlayersInServerCrashes.get(ev.player.getUniqueID());
    	if(CrashSurvivalCount != null){
    		Achievement achievement = getAchievement("servercrash"+CrashSurvivalCount);
        	if(achievement != null){
        			if(ev.player instanceof EntityPlayerMP){
        				EntityPlayerMP player = (EntityPlayerMP) ev.player;
            			player.getStatFile().unlockAchievement(player, achievement, 1);
        			}
        	}
    	}
		/*for(AchievementPage page :AchievementRegistry.instance().getAllPages()){
		 ItemStack itemStack = AchievementRegistry.instance().getItemStack(page);
		 System.out.println(page.getName()+":"+itemStack.getItem().getRegistryName());
		}*/
    }
    
	public static Achievement getAchievement(String achievement){
		StatBase x = BetterThanAchievements.oneShotStats.get("achievement."+achievement);
		if(x != null && x.isAchievement()){
			Achievement y = (Achievement) x;
			return y;
		}
		return null;
	}

    @SubscribeEvent
    public void onAchievementUnlocked(AchievementEvent event)
    {
        if(event.getEntityPlayer() instanceof EntityPlayerMP)
        {
        	StatisticsManager stats = ((EntityPlayerMP) event.getEntityPlayer()).getStatFile();
            if (stats.canUnlockAchievement(event.getAchievement()))
            {
                stats.unlockAchievement(event.getEntityPlayer(), event.getAchievement(), 1);
                event.setCanceled(true);
            }
            else
                addAchievementToMap(event.getEntityPlayer().getUniqueID(), event.getAchievement());
            if (!this.currentItrs.contains(event.getEntityPlayer().getUniqueID()))
                tryUnlock((EntityPlayerMP) event.getEntityPlayer());
        }
    }

    public void addAchievementToMap(UUID uuid, Achievement achievement)
    {
        Set<Achievement> achievements = this.playerAchievementMap.get(uuid);
        if (achievements == null) achievements = new HashSet<Achievement>();
        achievements.add(achievement);
        this.playerAchievementMap.put(uuid, achievements);
    }

    public void tryUnlock(EntityPlayerMP player)
    {
        this.currentItrs.add(player.getUniqueID());
        Set<Achievement> achievements = this.playerAchievementMap.get(player.getUniqueID());
        boolean doItr = achievements != null;
        while (doItr)
        {
            doItr = false;
            Iterator<Achievement> itr = achievements.iterator();
            while (itr.hasNext())
            {
                Achievement current = itr.next();
                if (player.getStatFile().canUnlockAchievement(current))
                {
                    player.addStat(current);
                    itr.remove();
                    doItr = true;
                }
            }
        }
        this.currentItrs.remove(player.getUniqueID());
    }

    public void dumpAchievementData(String worldName)
    {
        List<String> lines = new ArrayList<String>();
        for (Map.Entry<UUID, Set<Achievement>> entry : this.playerAchievementMap.entrySet())
        {
            StringBuilder sb = new StringBuilder();
            sb.append(entry.getKey().toString()).append("->");
            for (Achievement achievement : entry.getValue())
                sb.append(achievement.statId).append(",");
            lines.add(sb.toString());
        }
        try
        {
            Files.write(new File(ConfigHandler.getConfigDir(), worldName + " " + FILENAME).toPath(), lines, Charset.defaultCharset());
        } catch (IOException e)
        {
            LogHelper.instance().error(e, "couldn't write " + worldName + " " + FILENAME);
        }
    }

    public void constructFromData(String worldName)
    {
        this.playerAchievementMap.clear();
        Map<String, Achievement> achievementMap = new HashMap<String, Achievement>();
        for (Achievement achievement : AchievementList.ACHIEVEMENTS)
            achievementMap.put(achievement.statId, achievement);
        try
        {
            File file = new File(ConfigHandler.getConfigDir(), worldName + " " + FILENAME);
            if (!file.exists()) return;

            List<String> lines = Files.readAllLines(file.toPath() , Charset.defaultCharset());
            for (String line : lines)
            {
                String[] splitted = line.split("->");
                if (splitted.length != 2) continue;
                UUID uuid;
                try
                {
                     uuid = UUID.fromString(splitted[0]);
                }
                catch (IllegalArgumentException e)
                {
                    LogHelper.instance().error(e, "bad uuid \"" + splitted[0] + "\" in " + worldName + " " + FILENAME);
                    continue;
                }
                Set<Achievement> achievementSet = new HashSet<Achievement>();
                for (String sAchievement : splitted[1].split(","))
                {
                    Achievement achievement = achievementMap.get(sAchievement);
                    if (achievement == null) continue;
                    achievementSet.add(achievement);
                }
                this.playerAchievementMap.put(uuid, achievementSet);
            }
        } catch (IOException e)
        {
            LogHelper.instance().error(e, "couldn't read " + worldName + " " + FILENAME);
        }
    }
}
