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
package wtf.cheeze.smkb.mixin;


import com.blamejared.controlling.client.NewKeyBindsScreen;
import com.llamalad7.mixinextras.sugar.Local;
import com.moulberry.mixinconstraints.annotations.IfModLoaded;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.KeybindsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.cheeze.smkb.screen.LoadPresetScreen;
import wtf.cheeze.smkb.screen.SavePresetScreen;


@IfModLoaded("controlling")
@Mixin(NewKeyBindsScreen.class)
public abstract class ControllingKeybindsScreenMixin extends KeybindsScreen {


    private ControllingKeybindsScreenMixin(Screen a, GameOptions b) {
        super(a, b);
    }

    @Shadow public abstract ButtonWidget resetButton();

    @Inject(method = "initFooter", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 2))
    public void addFooter(CallbackInfo ci, @Local GridWidget.Adder adder) {
        ci.cancel();
        var bottomLeft = adder.add(DirectionalLayoutWidget.horizontal());
        bottomLeft.spacing(4);
        bottomLeft.add(SavePresetScreen.getButton());
        bottomLeft.add(LoadPresetScreen.getButton());
        var bottomRight = adder.add(DirectionalLayoutWidget.horizontal());
        bottomRight.spacing(4);
        var reset = this.resetButton();
        reset.setWidth(74);
        bottomRight.add(reset);
        var done = ButtonWidget.builder(ScreenTexts.DONE, ($$0x) -> {
            this.close();
        }).build();

        done.setWidth(74);
        bottomRight.add(done);

    }


}
