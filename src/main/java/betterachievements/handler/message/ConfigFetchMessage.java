package betterachievements.handler.message;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Files;

import BetterThanAchievements.BetterThanAchievements;
import BetterThanAchievements.Config;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ConfigFetchMessage implements IMessage, IMessageHandler<ConfigFetchMessage, ConfigFetchMessage>{
    public String toSend = null;
	public ConfigFetchMessage(String string) {
		this.toSend = string;
	}
	public ConfigFetchMessage(){
		
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.toSend = ByteBufUtils.readUTF8String(buf); 
	}

	@Override
	public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, toSend); 
	}

	@Override
	public ConfigFetchMessage onMessage(ConfigFetchMessage message, MessageContext ctx) {
		File x = new File(BetterThanAchievements.confpath+"/betterthanachievements/betterthanachievements.cfg");
		try {
			if(new Configuration(x).getBoolean("override", "general", true, "server can override")){
			 Files.write(message.toSend, x, StandardCharsets.UTF_8);
			 Config.loadConfig(x.getParentFile());
			 Minecraft.getMinecraft().scheduleResourcesRefresh();
			}
		} catch (IOException e) {}
		return null;
	}

}
