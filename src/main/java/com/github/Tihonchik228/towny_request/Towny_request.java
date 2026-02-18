package com.github.Tihonchik228.towny_request;

import com.github.Tihonchik228.towny_request.command.NAsk;
import com.github.Tihonchik228.towny_request.command.NSelection;
import com.github.Tihonchik228.towny_request.command.TAsk;
import com.github.Tihonchik228.towny_request.command.TSelection;
import com.palmergames.bukkit.towny.TownyCommandAddonAPI;
import com.palmergames.bukkit.towny.object.AddonCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.Arrays;

public final class Towny_request extends JavaPlugin {

    private static Towny_request instance;

    @Override
    public void onEnable() {

        instance = this; // Сохраняем экземпляр при включении

        getLogger().info("Плагин активирован!");

        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            getLogger().info("Создание папки для config.yml");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        // Регистрация /t ask
        AddonCommand TAskCommand = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN, "ask", new TAsk(this));
        TAskCommand.setTabCompletion(0, Arrays.asList("ask"));
        TownyCommandAddonAPI.addSubCommand(TAskCommand);
        getLogger().info("Команда /t ask успешно зарегистрирована!");

        // Регистрация /n ask
        AddonCommand NAskCommand = new AddonCommand(TownyCommandAddonAPI.CommandType.NATION, "ask", new NAsk(this));
        NAskCommand.setTabCompletion(0, Arrays.asList("ask"));
        TownyCommandAddonAPI.addSubCommand(NAskCommand);
        getLogger().info("Команда /n ask успешно зарегистрирована!");

        // Регистрация /t selection
        AddonCommand TSelectionCommand = new AddonCommand(TownyCommandAddonAPI.CommandType.TOWN, "selection", new TSelection(this));
        TSelectionCommand.setTabCompletion(0, Arrays.asList("selection"));
        TownyCommandAddonAPI.addSubCommand(TSelectionCommand);
        getLogger().info("Команда /t selection успешно зарегистрирована!");

        // Регистрация /n selection
        AddonCommand NSelectionCommand = new AddonCommand(TownyCommandAddonAPI.CommandType.NATION, "selection", new NSelection(this));
        NSelectionCommand.setTabCompletion(0, Arrays.asList("selection"));
        TownyCommandAddonAPI.addSubCommand(NSelectionCommand);
        getLogger().info("Команда /n selection успешно зарегистрирована!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Плагин деактивирован!");
    }
    public static Towny_request getInstance() {
        return instance;
    }
}
