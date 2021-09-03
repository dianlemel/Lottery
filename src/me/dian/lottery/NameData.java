package me.dian.lottery;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NameData {

    private static Gson gson = new Gson();
    private static Path namePath = Paths.get(Lottery.getPlugin().getDataFolder().toString(), "name.txt");
    private static Path historyPath = Paths.get(Lottery.getPlugin().getDataFolder().toString(), "history.txt");

    private static List<String> names = Lists.newArrayList();
    private static List<History> historys = Lists.newArrayList();

    /**
     *
     * @param size 抽幾個暱稱
     * @return 暱稱清單
     */
    public static List<String> getNames(int size) {
        //順序打亂
        Collections.shuffle(names);
        List<String> select = Lists.newArrayList();
        for (int i = 0; i < size && !names.isEmpty(); i++) {
            select.add(names.remove(0));
        }
        return select;
    }

    /**
     * 重新讀取暱稱、紀錄，並且過濾已經抽中過的暱稱
     * @throws IOException
     */
    public static void reload() throws IOException {
        if(!Files.exists(namePath)){
            Bukkit.broadcastMessage(ChatColor.RED + "遺失 name.txt 檔案，無法重新讀取資料");
            return;
        }
        //讀取所有暱稱
        names = Lists.newArrayList(new String(Files.readAllBytes(namePath)).split(","));

        historys.clear();
        if(!Files.exists(historyPath)){
            return;
        }
        //讀取抽獎紀錄
        List<Map<String,Object>> list = gson.fromJson(new String(Files.readAllBytes(historyPath)), List.class);
        historys = list.stream().map(History::new).collect(Collectors.toList());
        //過濾已經抽獎過的暱稱
        historys.stream().flatMap(h -> h.getNames().stream()).forEach(names::remove);
    }

}
