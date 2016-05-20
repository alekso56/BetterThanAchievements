package betterachievements.handler.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.CharSet;

import com.google.common.io.Files;

import BetterThanAchievements.BetterThanAchievements;
import BetterThanAchievements.Config;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.translation.LanguageMap;
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
