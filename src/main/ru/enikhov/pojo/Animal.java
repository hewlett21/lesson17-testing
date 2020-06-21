package ru.enikhov.pojo;

public class Animal {
    private Integer id;
    private String typeAnimal;
    private String specAnimal;
    private int weight;
    private int cage;
    private String feed;
    private int volume;

    public Animal() {
    }

    public Animal(Integer id, String typeAnimal, String specAnimal, int weight, int cage, String feed, int volume) {
        this.id = id;
        this.typeAnimal = typeAnimal;
        this.specAnimal = specAnimal;
        this.weight = weight;
        this.cage = cage;
        this.feed = feed;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeAnimal() {
        return typeAnimal;
    }

    public void setTypeAnimal(String typeAnimal) {
        this.typeAnimal = typeAnimal;
    }

    public String gettypeAnimal() {
        return typeAnimal;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "typeAnimal='" + typeAnimal + '\'' +
                ", specAnimal='" + specAnimal + '\'' +
                ", weight=" + weight +
                ", cage=" + cage +
                ", feed='" + feed + '\'' +
                ", volume=" + volume +
                '}';
    }

    public void settypeAnimal(String typeAnimal) {
        this.typeAnimal = typeAnimal;
    }

    public String getSpecAnimal() {
        return specAnimal;
    }

    public void setSpecAnimal(String specAnimal) {
        this.specAnimal = specAnimal;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCage() {
        return cage;
    }

    public void setCage(int cage) {
        this.cage = cage;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
