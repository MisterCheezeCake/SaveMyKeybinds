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
package wtf.cheeze.smkb.preset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import wtf.cheeze.smkb.SaveMyKeybinds;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class PresetManager {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static int savePreset(String name) {

        try {
            Preset preset = new Preset();
            Path presetFile = SaveMyKeybinds.FOLDER_PATH.resolve(name + ".preset.json");
            FileWriter writer = new FileWriter(presetFile.toFile());
            writer.write(GSON.toJson(preset));
            writer.close();
            SaveMyKeybinds.sendToast(Text.translatable("smkb.toast.save.success.title").formatted(Formatting.GREEN), Text.translatable("smkb.toast.save.success.body", name));
        } catch (Exception e) {
            SaveMyKeybinds.LOGGER.error("Failed to write preset file", e);
            SaveMyKeybinds.sendToast(Text.translatable("smkb.toast.save.failure.title").formatted(Formatting.RED), Text.translatable("smkb.toast.save.failure.body", name));
            return 0;
        }
        return 1;
    }

    public static int loadPreset(String name) {
        try {
            Path presetFile = SaveMyKeybinds.FOLDER_PATH.resolve(name + ".preset.json");
            String content = Files.readString(presetFile);
            Preset preset = GSON.fromJson(content, Preset.class);
            for (var keybinding: MinecraftClient.getInstance().options.allKeys) {
                var presetBinding = preset.keybinds.get(keybinding.getTranslationKey());
                if (presetBinding != null) {
                    keybinding.setBoundKey(InputUtil.fromTranslationKey(presetBinding.key));
                } else if (preset.strict) {
                    keybinding.setBoundKey(InputUtil.UNKNOWN_KEY);
                }
            }
        SaveMyKeybinds.sendToast(Text.translatable("smkb.toast.load.success.title").formatted(Formatting.GREEN), Text.translatable("smkb.toast.load.success.body", name));
        } catch (Exception e) {
            SaveMyKeybinds.LOGGER.error("Failed to read preset file", e);
            SaveMyKeybinds.sendToast(Text.translatable("smkb.toast.load.failure.title").formatted(Formatting.RED), Text.translatable("smkb.toast.load.failure.body", name));
            return 0;
        }
        return 1;
    }

    public static int deletePreset(String name) {
        try {
            Path presetFile = SaveMyKeybinds.FOLDER_PATH.resolve(name + ".preset.json");
            Files.delete(presetFile);
            SaveMyKeybinds.sendToast(Text.translatable("smkb.toast.delete.success.title").formatted(Formatting.GREEN), Text.translatable("smkb.toast.delete.success.body", name));
        } catch (Exception e) {
            SaveMyKeybinds.LOGGER.error("Failed to delete preset file", e);
            SaveMyKeybinds.sendToast(Text.translatable("smkb.toast.delete.failure.title").formatted(Formatting.RED), Text.translatable("smkb.toast.delete.failure.body", name));
            return 0;
        }
        return 1;
    }

    public static String[] getPresets() {
        return SaveMyKeybinds.FOLDER_PATH.toFile().list((dir, name) -> name.endsWith(".preset.json"));
    }
}
