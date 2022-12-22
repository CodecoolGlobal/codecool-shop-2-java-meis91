package com.codecool.shop.dao.implementationJDBC;

import com.codecool.shop.controller.RegistrationController;
import com.codecool.shop.dao.*;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {

        private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

        private CartDao cartDao;
        private CustomerDao customerDao;
        private ProductCategoryDao productCategoryDao;
        private ProductDao productDao;
        private SupplierDao supplierDao;


        public void setup() throws SQLException {
            DataSource dataSource = connect();

        }



       /* public void savePlayer(Player player) {
            model = new PlayerModel(player);
            playerDao.add(model);
        }

        public void saveGameState(GameMap map, int level) {
            gameModel = new GameState(map.toString(), Date.valueOf(LocalDate.now()), model, level);
            gameStateDao.add(gameModel);
        }

        public void updatePlayer(Player player) {
            model = new PlayerModel(player);
            playerDao.update(model);
        }

        public void updateGameState(GameMap map, int level) {
            gameModel = new GameState(map.toString(), Date.valueOf(LocalDate.now()), model, level);
            String playerName = model.getPlayerName();
            gameStateDao.update(gameModel, playerName);

        }

        public List<HashMap<String, String>> getAll() {
            return gameStateDao.getAll();
        }

        public GameState loadGameState(int id) {
            GameState game = gameStateDao.get(id);
            return game;

        }*/

        public DataSource connect() throws SQLException {
            PGSimpleDataSource dataSource = new PGSimpleDataSource();
            String dbName = System.getenv("DB_NAME");
            String user = System.getenv("USER_NAME");
            String password = System.getenv("PASSWORD");
            dataSource.setDatabaseName(dbName);
            dataSource.setUser(user);
            dataSource.setPassword(password);
            System.out.println("Trying to connect");
            dataSource.getConnection().close();
            System.out.println("Connection ok.");
            return dataSource;
        }
}
