package main.java.thermometer;

/**
 * Created by carlosballadares on 2018-06-10.
 */

public class ListenerSettings {

    public enum  Direction{
        DOWN, UP, ANY
    };


    public Float temperature;

    public Direction direction;

    public Float ignoreBand;

    public ListenerSettings(Float temperature, Direction direction, Float ignoreBand) {
        this.temperature = temperature;
        this.direction = direction;
        this.ignoreBand = ignoreBand;
    }

    public ListenerSettings(){
        this(0f, Direction.ANY, 0f);
    }
}
