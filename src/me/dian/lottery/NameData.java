package me.dian.lottery;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NameData {

    private static final Gson gson = new Gson();
    private static final Path namePath = Paths.get(Lottery.getPlugin().getDataFolder().toString(), "name.txt");
    private static final Path historyPath = Paths.get(Lottery.getPlugin().getDataFolder().toString(), "history.txt");

    private static List<String> names = Lists.newArrayList();
    private static List<History> historys = Lists.newArrayList();

    /**
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
        if (!select.isEmpty()) {
            historys.add(new History(select));
            save();
        }
        return select;
    }

    public static List<String> getNames() {
        return names;
    }

    /**
     * 抽獎名單是否已經空了
     *
     * @return
     */
    public static boolean isEmpty() {
        return names.isEmpty();
    }

    /**
     * 異步儲存抽獎紀錄
     */
    private static void save() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(Lottery.getPlugin(), () -> {
            try {
                Files.deleteIfExists(historyPath);
                Files.write(historyPath, gson.toJson(historys.stream().map(History::toMap).collect(Collectors.toList())).getBytes(Charset.forName("UTF-8")));
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.broadcastMessage(ChatColor.RED + "儲存記錄異常! 請查看後台訊息");
            }
        }, 0);
    }

    /**
     * 清除紀錄、並且刪除紀錄檔案
     */
    public static void delete(){
        try {
            Files.deleteIfExists(historyPath);
            historys.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新讀取暱稱、紀錄，並且過濾已經抽中過的暱稱
     *
     * @throws IOException
     */
    public static void reload() throws IOException {
        if (!Files.exists(namePath)) {
            System.out.println("遺失 name.txt 檔案，無法重新讀取資料");
            Bukkit.broadcastMessage(ChatColor.RED + "遺失 name.txt 檔案，無法重新讀取資料");
            return;
        }
        //讀取所有暱稱
        names = Lists.newArrayList(new String(Files.readAllBytes(namePath), "UTF-8").split(","));
        Bukkit.broadcastMessage(ChatColor.GOLD + "成功讀取，暱稱總共數量 " + names.size());
        System.out.println("成功讀取，暱稱總共數量 " + names.size());
        historys.clear();
        if (!Files.exists(historyPath)) {
            return;
        }
        //讀取抽獎紀錄
        List<Map<String, Object>> list = gson.fromJson(new String(Files.readAllBytes(historyPath), "UTF-8"), List.class);
        historys = list.stream().map(History::new).collect(Collectors.toList());
        //過濾已經抽獎過的暱稱
        historys.stream().flatMap(h -> h.getNames().stream()).forEach(names::remove);
        Bukkit.broadcastMessage(ChatColor.GOLD + "過濾成功，暱稱剩餘 " + names.size());
        System.out.println("過濾成功，暱稱剩餘 " + names.size());
    }

}
