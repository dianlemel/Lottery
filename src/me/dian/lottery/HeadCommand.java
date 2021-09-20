package me.dian.lottery;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * head指令，定豪想要的功能，想把怪怪的物品放在頭上
 */
public class HeadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null) {
                commandSender.sendMessage(ChatColor.RED + "請將物品拿在手上");
            } else {
                player.getInventory().setHelmet(item);
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "不支持後端輸入");
        }
        return true;
    }

}
