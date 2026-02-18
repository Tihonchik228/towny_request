package com.github.Tihonchik228.towny_request.command;

import com.github.Tihonchik228.towny_request.Towny_request;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NAsk implements CommandExecutor {

    private final Towny_request plugin;

    public NAsk(Towny_request plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        // Переменные:
        String messagePermission = plugin.getConfig().getString("broadcasts.n-ask.no-permission");
        messagePermission = messagePermission.replace("&", "\u00a7");
        messagePermission = messagePermission.replace("{PLAYER}", sender.getName());

        String messageNAsk = plugin.getConfig().getString("broadcasts.n-ask.message");
        messageNAsk = messageNAsk.replace("&", "\u00a7");

        String buttonMessage = plugin.getConfig().getString("broadcasts.n-ask.button-message");
        buttonMessage = buttonMessage.replace("&", "\u00a7");

        String buttonPrompt = plugin.getConfig().getString("broadcasts.n-ask.button-prompt");
        buttonPrompt = buttonPrompt.replace("&", "\u00a7");

        String refusal = plugin.getConfig().getString("broadcasts.n-ask.refusal");
        refusal = refusal.replace("&", "\u00a7");
        refusal = refusal.replace("{PLAYER}", sender.getName());

        String successMessage = plugin.getConfig().getString("broadcasts.n-ask.message-successfully");
        successMessage = successMessage.replace("&", "\u00a7");

        String soundTAskValue = plugin.getConfig().getString("broadcasts.n-ask.sound-n-ask");

        // Sender игрок?
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cЭту команду могут использовать только игроки!");
            return true;
        }
        // Permission на команду
        if (!sender.hasPermission("townyrequest.n.ask")) {
            sender.sendMessage(messagePermission);
            return true;
        }

        Player player = (Player) sender;
        Town town = TownyAPI.getInstance().getTown(player);
        Nation nation = TownyAPI.getInstance().getNation(player);
        messageNAsk = messageNAsk.replace("{TOWN}", town.getName());
        buttonMessage = buttonMessage.replace("{TOWN}", town.getName());
        buttonPrompt = buttonPrompt.replace("{TOWN}", town.getName());
        refusal = refusal.replace("{TOWN}", town.getName());

        // Является ли Sender уже членом нации?
        if (nation != null) {
            sender.sendMessage(refusal);
            return true;
        }

        // Объявление о поиске города
        Bukkit.broadcastMessage(messageNAsk);

        // Кнопка:
        TextComponent button = new TextComponent(buttonMessage);
        button.setColor(net.md_5.bungee.api.ChatColor.WHITE);
        button.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.RUN_COMMAND,
                "/n invite " + town.getName()
        ));

        // Подсказка:
        button.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new Text(buttonPrompt)
        ));
        // Отправка кнопки всем игрокам:
        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            onlinePlayers.spigot().sendMessage(button);
        }
        player.sendMessage(successMessage);
        // Звук при прописывании команды //
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.valueOf(soundTAskValue), 0.5F, 1.0F);
        }

        return true;
    }
}
