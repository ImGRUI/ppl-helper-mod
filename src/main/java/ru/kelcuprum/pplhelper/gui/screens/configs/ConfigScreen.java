package ru.kelcuprum.pplhelper.gui.screens.configs;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import ru.kelcuprum.abi.ActionBarInfo;
import ru.kelcuprum.alinlib.AlinLib;
import ru.kelcuprum.alinlib.config.Config;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBooleanBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.button.ButtonBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.editbox.EditBoxBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.slider.SliderBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.text.HorizontalRuleBuilder;
import ru.kelcuprum.alinlib.gui.components.builder.text.TextBuilder;
import ru.kelcuprum.alinlib.gui.screens.ConfigScreenBuilder;
import ru.kelcuprum.pplhelper.PepelandHelper;

import static ru.kelcuprum.alinlib.gui.Icons.*;

public class ConfigScreen {
    public Screen parent;
    public Screen build(Screen parent){
        this.parent = parent;
        ConfigScreenBuilder builder = new ConfigScreenBuilder(parent, Component.translatable("pplhelper"))
                .addPanelWidget(new ButtonBuilder(Component.translatable("pplhelper.configs")).setOnPress((s) -> AlinLib.MINECRAFT.setScreen(new ConfigScreen().build(parent))).setIcon(OPTIONS));
        if(PepelandHelper.config.getBoolean("IM_A_TEST_SUBJECT", false))
            builder.addPanelWidget(new ButtonBuilder(Component.translatable("pplhelper.test"), (s) ->AlinLib.MINECRAFT.setScreen(new TestConfigScreen().build(parent))).setIcon(CLOWNFISH));

        builder.addWidget(new TextBuilder(Component.translatable("pplhelper.configs")))
                // Пока-что как заглушка
                // .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.enable_toast"), true).setConfig(PepelandHelper.config,"ENABLE.TOAST"))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.menu.lobby"), true).setConfig(PepelandHelper.config,"MENU.LOBBY"))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.menu.lobby.alinlib"), false).setConfig(PepelandHelper.config,"MENU.LOBBY.ALINLIB"))
                .addWidget(new SliderBuilder(Component.translatable("pplhelper.configs.selected_project.auto_hide")).setDefaultValue(5).setMin(1).setMax(32).setConfig(PepelandHelper.config, "SELECTED_PROJECT.AUTO_HIDE"));
                if(PepelandHelper.isInstalledABI) builder.addWidget(new HorizontalRuleBuilder(Component.translatable("pplhelper.configs.abi.title")))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.abi"), false).setConfig(PepelandHelper.config,"ABI"))
                .addWidget(new EditBoxBuilder(Component.translatable("pplhelper.configs.abi.info")).setValue(PepelandHelper.config.getString("INFO.PPLHELPER", ActionBarInfo.localization.getLocalization("info.pplhelper", false, false, false))).setConfig(PepelandHelper.config, "INFO.PPLHELPER"))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.sproject.abi"), true).setConfig(PepelandHelper.config,"SPROJECT.ABI"));
                builder.addWidget(new HorizontalRuleBuilder(Component.translatable("pplhelper.project.schematic")))
                        .addWidget(new SliderBuilder(Component.translatable("pplhelper.project.schematic.total_blocks")).setMin(25).setMax(1000).setDefaultValue(50).setConfig(PepelandHelper.config, "SCHEMATIC.MAX_SIZE"));
                builder.addWidget(new HorizontalRuleBuilder(Component.translatable("pplhelper.configs.chat")))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.chat.global"), false).setConfig(PepelandHelper.config,"CHAT.GLOBAL"))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.chat.global.toggle"), true).setConfig(PepelandHelper.config,"CHAT.GLOBAL.TOGGLE"))
                .addWidget(new TextBuilder(Component.translatable("pplhelper.configs.chat.global.description")).setType(TextBuilder.TYPE.BLOCKQUOTE))
                .addWidget(new HorizontalRuleBuilder(Component.translatable("pplhelper.configs.timer")))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.timer.restart"), true).setConfig(PepelandHelper.config,"TIMER.RESTART"))
                .addWidget(new ButtonBooleanBuilder(Component.translatable("pplhelper.configs.timer.join"), true).setConfig(PepelandHelper.config,"TIMER.JOIN"))
                .addWidget(new HorizontalRuleBuilder(Component.translatable("pplhelper.configs.api")))
                .addWidget(new EditBoxBuilder(Component.translatable("pplhelper.configs.api_url")).setValue("https://api.pplh.ru/").setConfig(PepelandHelper.config, "API_URL"))
                .addWidget(new HorizontalRuleBuilder(Component.translatable("pplhelper.configs.urls")))
                .addWidget(new EditBoxBuilder(Component.translatable("pplhelper.configs.modrinth_url")).setValue("https://modrinth.com/").setConfig(PepelandHelper.config, "MODRINTH_URL"));

        return builder.build();
    }
}
