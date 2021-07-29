package org.skychipmunk.flybook.data;

import org.bukkit.entity.Player;

import java.util.*;

public class FlyBookPlayerData {

    private final Map<UUID, Long> flyDataMap = new HashMap<>();
    private final List<UUID> flyDatas = new ArrayList<>();

    public void addFlyData(Player player) {
        this.flyDatas.add(player.getUniqueId());
    }

    public void removeFlyData(Player player) {
        this.flyDatas.remove(player.getUniqueId());
    }

    public boolean hasFlyData(Player player) {
        return this.flyDatas.contains(player.getUniqueId());
    }

    public Long flyPlayerData(Player player) {
        return this.flyDataMap.computeIfAbsent(player.getUniqueId(), uuid -> 0L);
    }

    public void flyPlayerData(Player player , long time) {
        this.flyDataMap.put(player.getUniqueId(), time);
    }

    public void flyPlayerData(UUID uuid , long time) {
        this.flyDataMap.put(uuid, time);
    }

    public void flyTaskPlayerData(Player player) {
        this.flyDataMap.put(player.getUniqueId(), (this.flyPlayerData(player) - 2000L));
    }

    public Set<UUID> allFlyData() {
        return this.flyDataMap.keySet();
    }

    public Boolean isFlyPlayerData(Player player) {
        return this.flyDataMap.containsKey(player.getUniqueId());
    }


    public long flyUuidData(UUID uuid) {
        return this.flyDataMap.get(uuid);
    }
}
