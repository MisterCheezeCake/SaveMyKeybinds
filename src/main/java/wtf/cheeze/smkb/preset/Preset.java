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
import java.util.Map;
import java.util.Set;

public class Preset {
    /**
     * A version number for the preset file. V1 is the original version, V2 is the
     * new version that supports modifiers. They are fully backwards compatible and partially
     * forwards compatible, mod version 1.0.0 will be able to read V2 presets, but will always
     * treat them with strict mode disabled and without modifiers, since it has no knowledge of those features.
     */
    public int version = 2;

    /**
     * Whether the preset is strict or not. If it's strict, it will unbind any key
     * not found within it.
     */
    public boolean strict = false;

    /**
     * A map of the translation key of the keybinding to the keybind object
     */
    public Map<String, Keybind> keybinds = new HashMap<>();


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
        public Set<Modifier> modifiers = Set.of();

        public Keybind(String key) {
            this.key = key;
        }
        public Keybind(String key, Set<Modifier> modifiers) {
            this.key = key;
            this.modifiers = modifiers;
        }
    }
}
