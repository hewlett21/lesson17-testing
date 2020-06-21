package ru.enikhov;

import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.enikhov.Connections.ConnectionJdbc;
import ru.enikhov.Connections.ConnectionJdbcImpl;
import ru.enikhov.dao.ZooDao;
import ru.enikhov.dao.ZooDaoImpl;
import ru.enikhov.pojo.Animal;

public class AppZoo {
    private static final Logger logger = LogManager.getLogger(AppZoo.class);

    public static void main(String[] args) throws SQLException {
        ConnectionJdbc connectionJdbc = ConnectionJdbcImpl.getInstance();
        ZooDao zooDao = new ZooDaoImpl(connectionJdbc);
        logger.trace("Инициализация таблиц ...");
        zooDao.createTable();
        logger.trace("Таблицы подготовлены ...");
        AppZoo appZoo = new AppZoo();
        appZoo.listAnimal(zooDao);
        appZoo.listPredators(zooDao);
        appZoo.countFeed(zooDao);
        int newAnimal = appZoo.addMethod(zooDao);
        appZoo.anyChange(zooDao, newAnimal);
    }

    public void listAnimal(ZooDao zooDao) {
        logger.info("Список всех животных:");
        zooDao.listAnimals();

    }

    public void listPredators(ZooDao zooDao) {
        logger.info("Список всех хищников:");
        zooDao.getAnimal("Хищник");

    }

    public void countFeed(ZooDao zooDao) {
        int result = 0;
        double cost = 250.0;
        result = zooDao.countVolumeFeed("мясо");
        logger.info("Животные съедают {} кг. мяса, на сумму: {}", result, getSumm(result, cost));
    }

    public double getSumm(int volume, double price) {
        return volume * price;
    }

    public int addMethod(ZooDao zooDao) {
        Animal animal = new Animal(
                null,
                "Примат",
                "Шимпанзе",
                80,
                310,
                "банан",
                5);
        logger.info("Добавляем животное: " + animal);
        int newAnimal = zooDao.addAnimal(animal);
        return newAnimal;
    }

    public void anyChange(ZooDao zooDao, int newAnimal) {
        Animal animal = zooDao.getAnimalById(newAnimal);
        logger.info("Начальный объем еды добавленного животного {}", animal);
        animal.setVolume(getVol(animal.getVolume(), 2));
        zooDao.updateAnimal(animal);
        animal = zooDao.getAnimalById(newAnimal);
        logger.info("Измененный объем еды добавленного животного {}", animal);
    }

    public int getVol(int oldVolume, int x) {
        return oldVolume * x;
    }
}
