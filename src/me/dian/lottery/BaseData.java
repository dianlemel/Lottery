package me.dian.lottery;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class BaseData {

    private static final String POSITION = "Position";
    private static final String DISPLAY_POSITION = "DisplayPosition";
    private static final String DISPLAY_TYPE = "Type";
    private static final String BUTTON = "Button";
    private static final String BUTTON_COUNT = "Count";
    private static final String NAME_COLOR = "NameColor";
    private static final String SPECIAL_TIME = "SpecialTime";

    private static Map<Integer, List<Location>> displayPositions = Maps.newConcurrentMap();
    private static Map<Location, Integer> button = Maps.newConcurrentMap();
    private static ChatColor nameColor;
    private static int specialTime;

    /**
     * 重新讀取基礎資料
     */
    public static void reload() {
        //當設定黨不存在，儲存預設設定檔案
        Lottery.getPlugin().saveDefaultConfig();
        //重新讀取設定檔
        Lottery.getPlugin().reloadConfig();
        displayPositions.clear();
        FileConfiguration config = Lottery.getPlugin().getConfig();
        config.getMapList(DISPLAY_POSITION).forEach(map -> {
            String[] types = ((String) map.get(DISPLAY_TYPE)).split(",");
            String locStr = (String) map.get(POSITION);
            for (String type : types) {
                Location loc = toLocation(locStr);
                loc.getChunk().addPluginChunkTicket(Lottery.getPlugin());
                Stream.of(loc.getChunk().getEntities()).filter(Entity::isCustomNameVisible).forEach(e -> {
                    e.remove();
                });
                displayPositions.computeIfAbsent(Integer.parseInt(type), v -> Lists.newArrayList()).add(loc);
            }
        });
        button.clear();
        config.getMapList(BUTTON).forEach(map -> {
            int size = (int) map.get(BUTTON_COUNT);
            String loc = (String) map.get(POSITION);
            button.put(toLocation(loc), size);
        });
        nameColor = ChatColor.getByChar(config.getString(NAME_COLOR));
        specialTime = config.getInt(SPECIAL_TIME);
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

    public static int getSpecialTime() {
        return specialTime;
    }

    public static Map<Integer, List<Location>> getDisplayPositions() {
        return displayPositions;
    }

    public static Map<Location, Integer> getButton() {
        return button;
    }

    public static ChatColor getNameColor() {
        return nameColor;
    }
}
