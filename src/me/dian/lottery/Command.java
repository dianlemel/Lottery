package me.dian.lottery;

import org.bukkit.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if(!commandSender.isOp()){
            commandSender.sendMessage(ChatColor.RED + "你沒有該指令的使用權限");
            return true;
        }
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
                case "clear":
                    Display.clear();
                    Bukkit.broadcastMessage(ChatColor.AQUA + "已清除顯示名稱");
                    return true;
                case "info":
                    Bukkit.broadcastMessage(ChatColor.AQUA + "暱稱剩餘: " + NameData.getNames().size());
                    return true;
                case "delete":
                    NameData.delete();
                    Bukkit.broadcastMessage(ChatColor.AQUA + "已清除所有紀錄");
                    return true;
            }
        }
        commandSender.sendMessage(ChatColor.AQUA + "/lo reload 重新讀取基礎設定");
        commandSender.sendMessage(ChatColor.AQUA + "/lo reloadData 重新讀取抽獎名單、紀錄");
        commandSender.sendMessage(ChatColor.AQUA + "/lo clear 清除顯示名稱");
        commandSender.sendMessage(ChatColor.AQUA + "/lo delete 清除紀錄");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String ss, String[] strings) {
        return Arrays.asList("reload", "reloadData", "clear", "info").stream().filter(s -> s.toLowerCase().startsWith(strings[0].toLowerCase())).collect(Collectors.toList());
    }

}
