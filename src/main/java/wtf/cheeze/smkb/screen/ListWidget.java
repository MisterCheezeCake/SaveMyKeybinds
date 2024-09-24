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
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;
import wtf.cheeze.smkb.preset.PresetManager;

public class ListWidget extends AlwaysSelectedEntryListWidget<ListWidget.Entry> {

    public ListWidget(MinecraftClient minecraftClient, int width, int height, int top, int entryHeight) {
        super(minecraftClient, width, height, top, entryHeight);
        for (var preset : PresetManager.getPresets()) {
            this.addEntry(new Entry(preset.split("\\.")[0]));
        }
    }

    public void reload() {
        clearEntries();
        for (var preset : PresetManager.getPresets()) {
            this.addEntry(new Entry(preset.split("\\.")[0]));
        }
    }

    public static class Entry extends AlwaysSelectedEntryListWidget.Entry<Entry> {

        public final String text;

        public Entry(String text) {
            super();
            this.text = text;
        }


        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            context.drawCenteredTextWithShadow(
                    MinecraftClient.getInstance().textRenderer,
                    text,
                    SMKBScreen.centerX(),
                    y + 3,
                    !hovered && !isFocused() ? 0xC0C0C0: 0xFFFFFF
            );
        }

        @Override
        public Text getNarration() {
            return Text.of(text);
        }
    }
}