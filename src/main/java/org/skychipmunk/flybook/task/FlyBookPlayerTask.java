package org.skychipmunk.flybook.task;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.skychipmunk.flybook.data.FlyBookPlayerData;

public class FlyBookPlayerTask implements Runnable {

    private FlyBookPlayerData flyBookPlayerData;

    public FlyBookPlayerTask(FlyBookPlayerData flyBookPlayerData) {
        this.flyBookPlayerData = flyBookPlayerData;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (this.flyBookPlayerData.isFlyPlayerData(player) && this.flyBookPlayerData.flyPlayerData(player) > 0) {
                if (player.isFlying() && this.flyBookPlayerData.hasFlyData(player)) {
                    this.flyBookPlayerData.flyTaskPlayerData(player);
                }
            }else if (player.isFlying() && player.getGameMode() != GameMode.CREATIVE){
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage("§7§l[ §bDustFarm §7§l ]§f플라이 이용시간이 끝났습니다.");
            }
        }
    }
}