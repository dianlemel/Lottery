package me.dian.lottery;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.util.List;

public class SpecialEffect implements Runnable {

    private static boolean run = false;

    public static boolean isPlay() {
        return run;
    }

    public static void play(int size) {
        if (run) {
            return;
        }
        run = true;
        new SpecialEffect(size);
    }


    private long time;
    private int size;
    private int level = 0;
    private List<Location> loc;

    private SpecialEffect(int size) {
        this.size = size;
        this.time = System.currentTimeMillis();
        this.loc = BaseData.getDisplayPositions().get(size);
        this.loc.get(0).getWorld().playSound(this.loc.get(0), Sound.BLOCK_PORTAL_TRAVEL, 3.0F, 0.533F);
        run();
    }

    private void spawnParticle(Particle particle, int size) {
        loc.forEach(l -> {
            l.getWorld().spawnParticle(particle, l.clone().add(0.5, 1.3, 0.5), size);
        });
    }

    private void playSound(Sound sound) {
        loc.forEach(l -> {
            l.getWorld().playSound(l.clone().add(0.5, 1.3, 0.5), sound, 3.0F, 0.533F);
        });
    }

    @Override
    public void run() {
        switch (level) {
            case 0:
                if ((System.currentTimeMillis() - time) <= BaseData.getSpecialTime() * 1000) {
                    spawnParticle(Particle.PORTAL, 50);
                } else {
                    level++;
                }
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lottery.getPlugin(), this, 1);
                return;
            case 1:
                level++;
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Lottery.getPlugin(), this, 50);
                return;
            case 2:
                spawnParticle(Particle.TOTEM, 200);
                playSound(Sound.BLOCK_END_GATEWAY_SPAWN);
                Display.showName(size, NameData.getNames(size));
                run = false;
                return;
        }
    }
}
