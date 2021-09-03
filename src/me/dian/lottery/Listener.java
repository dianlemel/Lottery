package me.dian.lottery;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

public class Listener implements org.bukkit.event.Listener {

    /**
     * 紅石訊號觸發
     * @param event
     */
    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        Location loc = event.getBlock().getLocation();
        Integer size = BaseData.getStart().get(loc);
        if (size == null) {
            return;
        }
        Display.showName(String.valueOf(size), NameData.getNames(size));
    }
}
