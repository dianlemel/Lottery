package me.dian.lottery;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Lottery extends JavaPlugin {

    private static Lottery plugin;

    public static Lottery getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        //註冊監聽
        Bukkit.getPluginManager().registerEvents(new Listener(),this);
        //註冊指令
        PluginCommand pluginCommand = Bukkit.getPluginCommand("lo");
        Command command = new Command();
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
        //處存預設設定檔案
        saveDefaultConfig();
        //重新讀取設定檔
        reloadConfig();
    }

    @Override
    public void onDisable() {

    }

}
