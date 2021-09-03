package me.dian.lottery;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Map;

public class BaseData {

    private static Map<String, List<Location>> nameLocations = Maps.newConcurrentMap();
    private static Map<Location, Integer> start = Maps.newConcurrentMap();
    private static ChatColor nameColor;
    private static int onlyTime;

    /**
     * 重新讀取基礎資料
     */
    public static void reload() {
        //當設定黨不存在，儲存預設設定檔案
        Lottery.getPlugin().saveDefaultConfig();
        //重新讀取設定檔
        Lottery.getPlugin().reloadConfig();
        nameLocations.clear();
        FileConfiguration config = Lottery.getPlugin().getConfig();
        config.getMapList("NamePosition").forEach(map -> {
            String[] types = ((String) map.get("Type")).split(",");
            String loc = (String) map.get("Loc");
            for (String type : types) {
                nameLocations.computeIfAbsent(type, v -> Lists.newArrayList()).add(toLocation(loc));
            }
        });
        start.clear();
        config.getMapList("Start").forEach(map -> {
            int size = (int) map.get("Size");
            String loc = (String) map.get("Loc");
            start.put(toLocation(loc), size);
        });
        nameColor = ChatColor.getByChar(config.getString("NameColor"));
        onlyTime = config.getInt("OnlyTime");
    }

    /**
     * 字串轉換為座標
     *
     * @param value 字串
     * @return 座標
     */
    private static Location toLocation(String value) {
        String[] v = value.split(",");
        Location loc = new Location(Bukkit.getWorlds().get(0), Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2]));
        return loc;
    }

    public static int getOnlyTime() {
        return onlyTime;
    }

    public static Map<String, List<Location>> getNameLocations() {
        return nameLocations;
    }

    public static Map<Location, Integer> getStart() {
        return start;
    }

    public static ChatColor getNameColor() {
        return nameColor;
    }
}
