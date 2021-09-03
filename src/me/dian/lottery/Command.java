package me.dian.lottery;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (strings.length != 0) {
            switch (strings[0]) {
                case "reload":
                    Lottery.getPlugin().reloadConfig();
                    commandSender.sendMessage(ChatColor.AQUA + "基礎設定重新讀取完成");
                    return true;
                case "reloadData":
                    try {
                        NameData.reload();
                        commandSender.sendMessage(ChatColor.AQUA + "重新讀取抽獎名單、紀錄");
                    } catch (IOException e) {
                        commandSender.sendMessage(ChatColor.RED + e.getMessage());
                        e.printStackTrace();
                    }
                    return true;
            }
        }
        commandSender.sendMessage(ChatColor.AQUA + "/lo reload 重新讀取基礎設定");
        commandSender.sendMessage(ChatColor.AQUA + "/lo reloadData 重新讀取抽獎名單、紀錄");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String ss, String[] strings) {
        return Arrays.asList("reload", "reloadData").stream().filter(s -> s.toLowerCase().startsWith(strings[0].toLowerCase())).collect(Collectors.toList());
    }

}
