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
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.intellij.lang.annotations.Language;
import wtf.cheeze.smkb.SaveMyKeybinds;
import wtf.cheeze.smkb.preset.PresetManager;

import java.util.regex.Pattern;

public class SavePresetScreen extends SMKBScreen {

    private TextFieldWidget nameWidget;
    private ButtonWidget saveWidget;

    @Language("RegExp")
    public final static String[] FORBIDDEN_CHARS = {"\\\\", "/", ":", "<", ">", "\"", "\\|", "\\?", "\\*"};

    public static final Pattern FORBIDDEN_CHARS_PATTERN = Pattern.compile(String.join("|", FORBIDDEN_CHARS));


    public SavePresetScreen(Screen parent) {
        super(Text.literal("Save Presets"), parent);
        this.nameWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, centerX() - 150, 40, 300, 20, Text.literal("Preset"));
        this.saveWidget = ButtonWidget.builder(Text.literal("Save"), (button) -> {
            var t = nameWidget.getText();
            if (t.isBlank()) {
                SaveMyKeybinds.sendToast("Invalid Preset Name", "Preset name cannot be empty");
                return;
            }
            if (FORBIDDEN_CHARS_PATTERN.matcher(t).find()) {
                SaveMyKeybinds.sendToast("Invalid Preset Name", "Preset name cannot contain any of the following characters: \\/:*?\"<>|");
                return;
            }
                    if (PresetManager.savePreset(t) != 0) {
                        close();
                    }
        }).dimensions(centerX() - 100, 70, 200, 20)
                .build();
    }


    @Override
    public void init() {
        super.init();
        this.addDrawableChild(nameWidget);
        this.addDrawableChild(saveWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Enter Preset Name"), centerX(), 27, 0xbbbbbb);
    }

    public static ButtonWidget getButton() {
        return ButtonWidget.builder(Text.literal("Save Presets"), button -> {
            MinecraftClient.getInstance().setScreen(new SavePresetScreen(MinecraftClient.getInstance().currentScreen));
        }).width(74).build();
    }
}
