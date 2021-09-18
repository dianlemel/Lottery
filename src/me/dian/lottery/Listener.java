package me.dian.lottery;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

public class Listener implements org.bukkit.event.Listener {

    /**
     * 紅石訊號觸發，獲得按鈕的紅石訊號
     *
     * @param event
     */
    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        Location loc = event.getBlock().getLocation();
        if (event.getNewCurrent() != 15) {
            return;
        }
        BaseData.ButtonData button = BaseData.getButton(loc);
        if (button == null) {
            return;
        }
        if (NameData.isEmpty()) {
            Bukkit.broadcastMessage(ChatColor.RED + "名單抽完囉!");
            return;
        }
        if (SpecialEffect.isPlay()) {
            Bukkit.broadcastMessage(ChatColor.RED + "動畫進行中，請稍後嘗試");
            return;
        }
        Display.clear();
        if (button.isSpecial()) {
            SpecialEffect.play(button.getSize());
        } else {
            Display.showName(button.getSize(), NameData.getNames(button.getSize()));
        }
    }
}
