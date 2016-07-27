package betterachievements.handler;

import java.lang.reflect.Field;

import betterachievements.gui.GuiBetterAchievements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GuiOpenHandler {
	private static Field prevScreen, currentPage;

	static {
		try {
			prevScreen = ReflectionHelper.findField(GuiAchievements.class, "parentScreen", "field_146562_a");
			prevScreen.setAccessible(true);
			currentPage = ReflectionHelper.findField(GuiAchievements.class, "currentPage");
			currentPage.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
		if (event.getGui() instanceof GuiAchievements) {
			event.setCanceled(true);
			try {
				Minecraft.getMinecraft().displayGuiScreen(new GuiBetterAchievements(
						(GuiScreen) prevScreen.get(event.getGui()), (Integer) currentPage.get(event.getGui()) + 1));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
