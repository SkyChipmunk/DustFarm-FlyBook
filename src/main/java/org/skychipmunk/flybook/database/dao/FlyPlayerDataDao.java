package org.skychipmunk.flybook.database.dao;

import api.cosmoage.global.sql.ConnectionPoolManager;
import org.skychipmunk.flybook.data.FlyBookPlayerData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlyPlayerDataDao {

    private Logger logger;
    private FlyBookPlayerData flyBookPlayerData;
    private ConnectionPoolManager connectionPoolManager;

    public FlyPlayerDataDao(Logger logger, FlyBookPlayerData flyBookPlayerData, ConnectionPoolManager connectionPoolManager) {
        this.logger = logger;
        this.flyBookPlayerData = flyBookPlayerData;
        this.connectionPoolManager = connectionPoolManager;
    }

    public void allInsertSave() {
        for (UUID uuid : this.flyBookPlayerData.allFlyData()) {
            String sql = "INSERT INTO FlyTime_Data VALUES (? , ?)";
            try {
                PreparedStatement statement = this.connectionPoolManager.getConnection().prepareStatement(sql);
                statement.setString(1, uuid.toString());
                statement.setLong(2, (this.flyBookPlayerData.flyUuidData(uuid)));
                statement.executeUpdate();
            } catch (SQLException exception) {
                this.logger.log(Level.INFO , "Fly Player Data Save Fail");
            }
            this.logger.log(Level.INFO , "Fly Player Data Save Success");
        }
    }

    public void allSelectLoad() {
        String sql = "SELECT * FROM FlyTime_Data";
        try {
            PreparedStatement statement = this.connectionPoolManager.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                this.flyBookPlayerData.flyPlayerData(UUID.fromString(resultSet.getString(1)), resultSet.getLong(2));
            }
        }catch (SQLException exception) {
            this.logger.log(Level.INFO , "Fly Player Data Load Fail");
        }
        this.logger.log(Level.INFO , "Fly Player Data Load Success");
    }


}
