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

import net.minecraft.client.MinecraftClient;

import java.util.HashMap;

public class Preset {
    /**
     * A version number for the preset file, currently always 1
     */
    public int version = 1;

    public HashMap<String, Keybind> keybinds = new HashMap<>();


    public Preset() {

        for (var keybinding: MinecraftClient.getInstance().options.allKeys) {
            keybinds.put(keybinding.getTranslationKey(), new Keybind(keybinding.getBoundKeyTranslationKey()));
        }
    }

    /**
     * This is an object to allow future expansion of the preset file format, for example AMECS support
     */
    public static class Keybind {
        public String key;

        public Keybind(String key) {
            this.key = key;
        }
    }
}
