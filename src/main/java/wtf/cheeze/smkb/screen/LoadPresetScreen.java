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

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import wtf.cheeze.smkb.mixin.ControllingKeybindsScreenAccessor;
import wtf.cheeze.smkb.mixin.KeybindsScreenAccessor;
import wtf.cheeze.smkb.preset.PresetManager;

public class LoadPresetScreen extends SMKBScreen {

    private final ListWidget listWidget;
    private final ButtonWidget deleteButton;
    private final ButtonWidget loadButton;

    public LoadPresetScreen(Screen parent) {
        super(Text.literal("Load Presets"), parent);
        this.listWidget = new ListWidget(
                MinecraftClient.getInstance(),
                MinecraftClient.getInstance().getWindow().getScaledWidth(),
                (int) (SMKBScreen.screenHeight() * 0.75f) - 25, 25, 20);
        int h = (int) (SMKBScreen.screenHeight() * 0.8f);
        this.deleteButton = ButtonWidget.builder(Text.literal("Delete"), button -> {
            var t = listWidget.getSelectedOrNull();
            if (t == null) return;
            PresetManager.deletePreset(t.text);
            listWidget.reload();

        }).dimensions(centerX() - 160 ,h,150,20).build();

        this.loadButton = ButtonWidget.builder(Text.literal("Load"), button -> {
            var t = listWidget.getSelectedOrNull();
            if (t == null) return;

            if (PresetManager.loadPreset(t.text) != 0 ) close();
            if (FabricLoader.getInstance().isModLoaded("controlling")) {
                if (MinecraftClient.getInstance().currentScreen instanceof com.blamejared.controlling.client.NewKeyBindsScreen) {
                   ((ControllingKeybindsScreenAccessor) MinecraftClient.getInstance().currentScreen).getNewKeyList().get().update();
                }
            } else {
                if (MinecraftClient.getInstance().currentScreen instanceof KeybindsScreen) {
                    ((KeybindsScreenAccessor) MinecraftClient.getInstance().currentScreen).getControlsListWidget().update();
                }
            }
        }).dimensions(centerX() + 10, h, 150, 20).build();


    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        deleteButton.active = listWidget.getSelectedOrNull() != null;
        loadButton.active = listWidget.getSelectedOrNull() != null;
    }

    @Override
    public void init() {
        super.init();
        this.addDrawableChild(listWidget);
        this.addDrawableChild(deleteButton);
        this.addDrawableChild(loadButton);
    }

    public static ButtonWidget getButton() {
        return ButtonWidget.builder(Text.literal("Load Presets"), button -> {
            MinecraftClient.getInstance().setScreen(new LoadPresetScreen(MinecraftClient.getInstance().currentScreen));
        }).width(74).build();
    }
}
