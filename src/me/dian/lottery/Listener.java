package me.dian.lottery;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

public class Listener implements org.bukkit.event.Listener {

    private boolean run = false;

    /**
     * 紅石訊號觸發
     *
     * @param event
     */
    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event) {
        Location loc = event.getBlock().getLocation();
        if (event.getNewCurrent() != 15) {
            return;
        }
        Integer size = BaseData.getStart().get(loc);
        if (size == null) {
            return;
        }
        if(NameData.isEmpty()){
            Bukkit.broadcastMessage(ChatColor.RED + "名單抽完囉!");
            return;
        }
        if (run) {
            Bukkit.broadcastMessage(ChatColor.RED + "動畫進行中，請稍後嘗試");
            return;
        }
        if (size == 1) {
            run = true;
            new Special().run();
        } else {
            show(size);
        }
    }

    private void show(int size) {
        Display.showName(String.valueOf(size), NameData.getNames(size));
    }

    class Special implements Runnable {

        long time;
        Location loc;
        int level = 0;

        Special() {
            Display.clear();
            time = System.currentTimeMillis();
            loc = BaseData.getNameLocations().get("1").get(0);
        }

        @Override
        public void run() {
            switch (level) {
                case 0:
                    if ((System.currentTimeMillis() - time) <= BaseData.getOnlyTime() * 1000) {
                        loc.getWorld().spawnParticle(Particle.PORTAL, loc.clone().add(0.5, 1.3, 0.5), 50);
                    } else {
                        level++;
                    }
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lottery.getPlugin(), this, 1);
                    return;
                case 1:
                    level++;
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lottery.getPlugin(), this, 40);
                    return;
                case 2:
                    loc.getWorld().spawnParticle(Particle.TOTEM, loc.clone().add(0.5, 1.3, 0.5), 200);
                    show(1);
                    run = false;
                    return;
            }
        }

    }
}
