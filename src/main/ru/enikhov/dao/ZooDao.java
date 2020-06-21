package ru.enikhov.dao;

import ru.enikhov.pojo.Animal;

import java.sql.SQLException;

public interface ZooDao {
    int addAnimal(Animal animal);
    int countVolumeFeed(String name);

    void getAnimal(String name);
    Animal getAnimalById(int id);

    void listAnimals();

    boolean updateAnimal(Animal animal);

    void createTable() throws SQLException;
}
