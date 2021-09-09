package me.dian.lottery;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

public class Display extends EntityArmorStand {

    private static List<Display> displays = Lists.newArrayList();

    /**
     * 清除所有顯示器
     */
    public static void clear() {
        displays.forEach(EntityArmorStand::die);
        displays.clear();
    }

    /**
     * @param type  顯示種類
     * @param names 名稱清單
     */
    public static void showName(int type, List<String> names) {
        clear();
        List<Location> loc = BaseData.getDisplayPositions().get(type);
        for (int i = 0; i < type && i < names.size() && i < loc.size(); i++) {
            Display display = new Display(loc.get(i));
            display.setNameMsg(names.get(i));
            displays.add(display);
        }
    }

    public Display(Location loc) {
        super(((CraftWorld) loc.getWorld()).getHandle(), loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5);
        //名稱顯示
        setCustomNameVisible(true);
        //底座關掉
        setMarker(false);
        //隱藏
        setInvisible(true);
        //小型
        setSmall(true);
        //無重力
        setNoGravity(true);
        //生成生物
        ((CraftWorld) loc.getWorld()).getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    /**
     * 顯示名稱
     *
     * @param msg
     */
    public void setNameMsg(String msg) {
        setCustomName(new ChatComponentText(BaseData.getNameColor() + ChatColor.BOLD.toString() + msg));
    }

}
