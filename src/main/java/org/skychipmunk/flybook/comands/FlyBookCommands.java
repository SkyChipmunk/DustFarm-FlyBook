package org.skychipmunk.flybook.comands;

import api.cosmoage.bukkit.item.ItemStackBuilder;
import api.cosmoage.global.commandannotation.CosmoCMD;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.skychipmunk.flybook.data.FlyBookPlayerData;

public class FlyBookCommands {

    private FlyBookPlayerData flyBookPlayerData;

    public FlyBookCommands(FlyBookPlayerData flyBookPlayerData) {
        this.flyBookPlayerData = flyBookPlayerData;
    }

    @CosmoCMD(parent = "플라이")
    public void onFlyBook(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        long time = (this.flyBookPlayerData.flyPlayerData(player) / 2000);
        long day = time / (60 * 60 * 24);
        long hour = (time - day * 60 * 60 * 24) / (60 * 60);
        long minute = (time - day * 60 * 60 * 24 - hour * 3600) / 60;
        long second = time % 60;
        player.sendMessage("§7§l[ §bDustFarm §7§l]§f 남은 플라이 시간: " + day + "일 " + hour + "시간 "  + minute + "분 " + second + "초");
        if (player.isOp()) {
            player.sendMessage("§7§l[ §bDustFarm §7§l]§f /플라이북 생성 (시간)");
        }
    }

    @CosmoCMD(parent = "플라이", child = "활성화")
    public void onFlyBookButton(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (this.flyBookPlayerData.isFlyPlayerData(player) && this.flyBookPlayerData.flyPlayerData(player) > 0) {
            if (!this.flyBookPlayerData.hasFlyData(player)) {
                player.setAllowFlight(true);
                player.setFlying(true);
                this.flyBookPlayerData.addFlyData(player);
                player.sendMessage("§7§l[ §bDustFarm §7§l] §f플라이가 활성화 되었습니다.");
            }else {
                player.sendMessage("§7§l[ §bDustFarm §7§l] §f당신은 플라이가 활성화 상태 입니다.");
            }

        } else {
            player.sendMessage("§7§l[ §bDustFarm §7§l] §f당신은 남은 플라이 시간이 없습니다.");
        }
    }

    @CosmoCMD(parent = "플라이", child = "비활성화")
    public void onFlyBookButtonOff(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (this.flyBookPlayerData.isFlyPlayerData(player)) {
            if (this.flyBookPlayerData.hasFlyData(player)) {
                player.setAllowFlight(false);
                player.setFlying(false);
                this.flyBookPlayerData.removeFlyData(player);
                player.sendMessage("§7§l[ §bDustFarm §7§l] §f플라이가 비활성화 되었습니다.");
            }else {
                player.sendMessage("§7§l[ §bDustFarm §7§l] §f당신은 현재 플라이 활성화 상태가 아닙니다.");
            }
        }
    }

    @CosmoCMD(parent = "플라이북", child = "생성", op = true)
    public void onFlyBookCreate(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 2) {
            int time = Integer.parseInt(args[1]);
            int day = time / (60 * 60 * 24);
            int hour = (time - day * 60 * 60 * 24) / (60 * 60);
            int minute = (time - day * 60 * 60 * 24 - hour * 3600) / 60;
            int second = time % 60;
            player.getInventory().addItem(new ItemStackBuilder(Material.BOOK).displayname("§7§l[ §bDustFarm §7§l] §f플라이북")
                    .addLore("").addLore("§f우클릭시 플라이 시간 " + hour + "시간 " + minute + "분 " + second + "초를 추가로 얻습니다.").build());
        } else {
            player.sendMessage("§7§l[ §bDustFarm §7§l] §f시간을 적어주세요");
        }

    }

}
