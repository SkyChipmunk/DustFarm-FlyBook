package org.skychipmunk.flybook.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.skychipmunk.flybook.data.FlyBookPlayerData;

public class FlyBookPlayerListener implements Listener {

    private FlyBookPlayerData flyBookPlayerData;

    public FlyBookPlayerListener(FlyBookPlayerData flyBookPlayerData) {
        this.flyBookPlayerData = flyBookPlayerData;
    }

    @EventHandler
    public void onClickEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (itemMeta !=null && itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals("§7§l[ §bDustFarm §7§l] §f플라이북") && itemMeta.getLore() != null) {
                String lore = itemMeta.getLore().get(1);
                lore = lore.replace("§f우클릭시 플라이 시간 " , "");
                long hour = Integer.parseInt(lore.substring(0 , lore.indexOf("시간")));
                long minute = Integer.parseInt(lore.substring(lore.indexOf("시간") + 3 , lore.indexOf("분")));
                long second = Integer.parseInt(lore.substring(lore.indexOf("분") + 2 , lore.indexOf("초")));
                player.sendMessage("§7§l[ §bDustFarm §7§l] §f플라이 시간 " + hour +"시간 " + minute + "분 " + second + "초가 추가 되었습니다.");

                long millis = 0; millis += (hour * (2000L) * 3600); millis += (minute * (2000L) * 60); millis += (second * 2000L);
                this.flyBookPlayerData.flyPlayerData(player , this.flyBookPlayerData.flyPlayerData(player) + millis);
            }
        }
    }
}
