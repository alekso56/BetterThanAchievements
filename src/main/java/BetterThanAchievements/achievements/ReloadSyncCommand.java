package BetterThanAchievements.achievements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import BetterThanAchievements.BetterThanAchievements;
import BetterThanAchievements.Config;
import betterachievements.handler.MessageHandler;
import betterachievements.handler.message.ConfigFetchMessage;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.util.math.BlockPos;

public class ReloadSyncCommand implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "achreload";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/achreload";
	}

	@Override
	public List<String> getCommandAliases() {
		 ArrayList aliases = new ArrayList();
		 aliases.add("rsc");
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		Config.loadConfig(new File(BetterThanAchievements.confpath));
		File x = new File(BetterThanAchievements.confpath+"/betterthanachievements/betterthanachievements.cfg");
		try {
			String data = BetterThanAchievements.readFile(x.getAbsolutePath(), StandardCharsets.UTF_8);
			MessageHandler.INSTANCE.sendToAll(new ConfigFetchMessage(data));
		} catch (IOException e) {}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		if(sender instanceof EntityPlayerMP){
			EntityPlayerMP x = (EntityPlayerMP) sender;
		    UserListOpsEntry i = server.getPlayerList().getOppedPlayers().getEntry(x.getGameProfile());
			if(i != null && i.getPermissionLevel() > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos pos) {
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}

}
