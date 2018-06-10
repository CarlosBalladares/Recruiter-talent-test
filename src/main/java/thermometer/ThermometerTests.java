package main.java.thermometer;

import main.java.thermometer.Callback;
import main.java.thermometer.ListenerSettings;
import main.java.thermometer.TemperatureListener;
import main.java.thermometer.Thermometer;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by carlosballadares on 2018-06-10.
 */
public class ThermometerTests {


    public static boolean CELSIUS   = true;
    public static boolean FARENHEIT = false;

    Thermometer tm = new Thermometer();

    static boolean flag = false;

    @Test
    public void TestCase1(){
        flag= false;


        ListenerSettings settings = new ListenerSettings();
        settings.temperature=50f;
        settings.ignoreBand=0f;
        settings.direction = ListenerSettings.Direction.ANY;


        TemperatureListener tl = tm.listen(settings, new Callback() {

            @Override
            public void execute() {
                flag = true;
            }
        });

        tm.setTemperature(0f, CELSIUS);
        assertFalse(flag);

        tm.setTemperature(100f, CELSIUS);
        assertFalse(flag);

        tm.setTemperature(50f, CELSIUS);
        assertTrue(flag);

    }


    @Test
    public void TestCase2(){

        flag= false;

        ListenerSettings settings = new ListenerSettings();
        settings.temperature=0f;
        settings.ignoreBand=0.5f;
        settings.direction = ListenerSettings.Direction.ANY;


        TemperatureListener tl = tm.listen(settings, new Callback() {

            @Override
            public void execute() {
                flag = !flag;
            }
        });


        tm.setTemperature(100f, CELSIUS);
        assertFalse(flag);

        tm.setTemperature(0.5f, CELSIUS);
        assertFalse(flag);

        tm.setTemperature(-0.5f, CELSIUS);
        assertFalse(flag);

        tm.setTemperature(0f, CELSIUS);
        assertTrue(flag);

        tm.setTemperature(0.5f, CELSIUS);
        assertTrue(flag);

        tm.setTemperature(-0.5f, CELSIUS);
        assertTrue(flag);

        tm.setTemperature(1f, CELSIUS);
        assertFalse(flag);


    }
}
