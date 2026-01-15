package wtf.cheeze.smkb.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

public class CenteredTextWidget extends TextWidget {

    public CenteredTextWidget(Text message, int x, int y) {
        super(x - MinecraftClient.getInstance().textRenderer.getWidth(message) / 2, y, MinecraftClient.getInstance().textRenderer.getWidth(message), 9, message, MinecraftClient.getInstance().textRenderer);
    }

    public CenteredTextWidget(Text message, int y) {
        super(SMKBScreen.centerX() - MinecraftClient.getInstance().textRenderer.getWidth(message) / 2, y, MinecraftClient.getInstance().textRenderer.getWidth(message), 9, message, MinecraftClient.getInstance().textRenderer);
    }
}
