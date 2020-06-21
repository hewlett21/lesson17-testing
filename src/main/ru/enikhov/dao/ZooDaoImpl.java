package ru.enikhov.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enikhov.Connections.ConnectionJdbc;
import ru.enikhov.pojo.Animal;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.stream.Collectors;


public class ZooDaoImpl implements ZooDao {
    private final Logger logger = LogManager.getLogger(this.getClass());

    public static final String INSERT_ANIMAL = "insert into animals " +
            "values (DEFAULT, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_ANIMALS = "select * from animals";
    public static final String SELECT_ANIMAL_ID = "select * from animals where idanimal = ?";
    public static final String SELECT_ANIMAL_TYPE = "select * from animals where type_animal = ?";
    public static final String UPDATE_ANIMAL = "update animals " +
            "set weight = ?, feed_type = ?, volume = ? " +
            "where idanimal = ?";
    public static final String SELECT_COUNT_MEAT = "select sum(volume) cnt from animals where feed_type = ?";

    private Connection connection;

    public ZooDaoImpl(ConnectionJdbc conn) {
        connection = conn.getConnection();
    }

    @Override
    public int addAnimal(Animal animal) {
        try (PreparedStatement ps =
                     connection.prepareStatement(INSERT_ANIMAL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, animal.gettypeAnimal());
            ps.setString(2, animal.getSpecAnimal());
            ps.setInt(3, animal.getWeight());
            ps.setInt(4, animal.getCage());
            ps.setString(5, animal.getFeed());
            ps.setInt(6, animal.getVolume());
            ps.executeUpdate();
            try (ResultSet genKey = ps.getGeneratedKeys()) {
                if (genKey.next()) {
                    return genKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("Видимо что-то случилось в методе addAnimal", e);
        }
        return 0;
    }

    @Override
    public Animal getAnimalById(int id) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ANIMAL_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Animal(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getString(6),
                            rs.getInt(7));
                }
            }
        } catch (SQLException e) {
            logger.error("Видимо что-то случилось в методе getAnimalById", e);
        }
        return null;
    }

    @Override
    public void getAnimal(String name) {
        try (PreparedStatement ps = connection.prepareStatement(SELECT_ANIMAL_TYPE)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logger.info(getRs(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Видимо что-то случилось в методе getAnimal", e);
        }
    }

    @Override
    public void listAnimals() {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT_ANIMALS)) {
            while (rs.next()) {
                logger.info(getRs(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String getRs(ResultSet rs) throws SQLException {
        String strInfo =
                "id = " + rs.getInt("idanimal")
                        + "; вид животного = " + rs.getString("type_animal")
                        + "; животное = " + rs.getString("spec_animal")
                        + "; вес = " + rs.getString("weight") + " кг."
                        + "; № клетки = " + rs.getInt("cage")
                        + "; чем кормят = " + rs.getString("feed_type")
                        + "; кол-во = " + rs.getString("volume") + " кг.";

        return strInfo;
    }

    @Override
    public boolean updateAnimal(Animal animal) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_ANIMAL)) {
            ps.setInt(1, animal.getWeight());
            ps.setString(2, animal.getFeed());
            ps.setInt(3, animal.getVolume());
            ps.setInt(4,animal.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Видимо что-то случилось в методе updateAnimal", e);
        }
        return false;
    }

    @Override
    public int countVolumeFeed(String name) {
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(SELECT_COUNT_MEAT)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count =  rs.getInt("cnt");
                }
            }
        } catch (SQLException e) {
            logger.error("Видимо что-то случилось в методе getAnimal", e);
            return 0;
        }
        return count;
    }

    @Override
    public void createTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (FileInputStream fis = new FileInputStream("datagen.sql");
                 InputStreamReader isr = new InputStreamReader(fis, Charset.forName("CP1251"));
                 BufferedReader reader = new BufferedReader(isr);
            ) {
                String ddl = reader.lines().collect(Collectors.joining("\n"));
                statement.executeUpdate(ddl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
