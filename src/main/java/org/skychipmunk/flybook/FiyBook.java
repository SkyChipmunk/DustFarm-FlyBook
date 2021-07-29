package org.skychipmunk.flybook;

import api.cosmoage.bukkit.commands.$BukkitCommandHandler;
import api.cosmoage.global.sql.ConnectionPoolManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.skychipmunk.flybook.comands.FlyBookCommands;
import org.skychipmunk.flybook.data.FlyBookPlayerData;
import org.skychipmunk.flybook.database.dao.FlyPlayerDataDao;
import org.skychipmunk.flybook.listener.FlyBookPlayerListener;
import org.skychipmunk.flybook.task.FlyBookPlayerTask;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FiyBook extends JavaPlugin {
    private final String hostname = this.getConfig().getString("Hostname");
    private final String port = this.getConfig().getString("Port");
    private final String username = this.getConfig().getString("Username");
    private final String password = this.getConfig().getString("Password");
    private final String database = this.getConfig().getString("Database");

    private final Logger logger = this.getLogger();
    private final FlyBookPlayerData flyBookPlayerData = new FlyBookPlayerData();
    private ConnectionPoolManager connectionPoolManager;
    private FlyPlayerDataDao flyPlayerDataDao;

    @Override
    public void onEnable() {
        this.getConfig().set("Hostname" , "localhost");
        this.getConfig().set("Port" , "1234");
        this.getConfig().set("Username" , "root");
        this.getConfig().set("Password" , "1234");
        this.getConfig().set("Database" , "??");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        this.connectionPoolManager = new ConnectionPoolManager(hostname, port, username, password , database);
        this.flyPlayerDataDao = new FlyPlayerDataDao(this.logger, this.flyBookPlayerData, this.connectionPoolManager);
        this.flyPlayerDataDao.allSelectLoad();
        Bukkit.getPluginManager().registerEvents(new FlyBookPlayerListener(this.flyBookPlayerData), this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new FlyBookPlayerTask(this.flyBookPlayerData), 1L, 20L);
        try {
            $BukkitCommandHandler.registerCommands(this, new FlyBookCommands(this.flyBookPlayerData));
        } catch (Exception exception) {
            this.getLogger().log(Level.WARNING, "Commands Error");
        }
    }

    @Override
    public void onDisable() {
        this.flyPlayerDataDao.allInsertSave();
    }
}
