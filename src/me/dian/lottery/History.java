package me.dian.lottery;

import com.google.common.collect.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class History {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String NAMES = "NAMES";
    private static final  String CREATE_TIME = "CREATE_TIME";

    private String createTime;
    private List<String> names;

    public History(List<String> names) {
        this.names = names;
        createTime = format.format(new Date());
    }

    public String getCreateTime() {
        return createTime;
    }

    public List<String> getNames() {
        return names;
    }

    public History(Map<String, Object> map) {
        names = (List<String>) map.get(NAMES);
        createTime = (String) map.get(CREATE_TIME);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = Maps.newConcurrentMap();
        map.put(CREATE_TIME, createTime);
        map.put(NAMES, names);
        return map;
    }
}
