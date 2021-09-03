package me.dian.lottery;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class Command implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if(strings.length != 0){
            switch (strings[0]){
                case "reload":
                    Lottery.getPlugin().reloadConfig();
                    commandSender.sendMessage(ChatColor.AQUA + "基礎設定重新讀取完成");
                    break;
                case "reloadData":
                    break;
            }
        }
        commandSender.sendMessage("/lo reload 重新讀取基礎設定");
        commandSender.sendMessage("/lo reloadData 重新讀取抽獎名單");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        return null;
    }

}
