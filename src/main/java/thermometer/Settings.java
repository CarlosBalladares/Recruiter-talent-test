package main.java.thermometer;

/**
 * Created by carlosballadares on 2018-06-10.
 *
 * Definition of the settings object and some default settings
 *
 */

public class Settings {

    public enum  Direction{
        DOWN, UP, ANY
    };


    public Float temperature;

    public Direction direction;

    public Float ignoreBand;

    public Settings(Float temperature, Direction direction, Float ignoreBand) {
        this.temperature = temperature;
        this.direction = direction;
        this.ignoreBand = ignoreBand;
    }

    public Settings(){
        this(0f, Direction.ANY, 0f);
    }
}
