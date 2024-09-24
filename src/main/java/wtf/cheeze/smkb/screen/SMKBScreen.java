/*
 * Copyright (C) 2024 MisterCheezeCake
 *
 * This file is part of SaveMyKeybinds.
 *
 * SaveMyKeybinds is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * SkyblockTweaks is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SaveMyKeybinds. If not, see <https://www.gnu.org/licenses/>.
 */
package wtf.cheeze.smkb.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public abstract class SMKBScreen extends Screen {

    private final Screen parent;
    private final ButtonWidget doneButton;

    public SMKBScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
        this.doneButton = ButtonWidget.builder(ScreenTexts.DONE, (b) -> {
            this.close();
        }).dimensions(centerX() - 100, (int) (screenHeight() * 0.9), 200, 20).build();
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, centerX(), 10, 0xffffff);

    }

    @Override
    public void init() {
        this.addDrawableChild(doneButton);
    }

    public static int centerX() {
        return MinecraftClient.getInstance().getWindow().getScaledWidth() / 2;
    }
    public static int screenHeight() {
        return MinecraftClient.getInstance().getWindow().getScaledHeight();
    }





}
