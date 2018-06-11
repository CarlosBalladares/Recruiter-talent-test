package test.java;

import main.java.thermometer.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static main.java.thermometer.Settings.Direction;

/**
 * Created by carlosballadares on 2018-06-10.
 * Tests for the Thermometer class
 *
 */
public class ThermometerTests {


    public static boolean CELSIUS   = true;
    public static boolean FARENHEIT = false;

    Thermometer tm = new Thermometer();

    static boolean flag = false;

    @Test
    public void testCase1(){
        flag= false;

        Settings settings;

        settings = SettingsFactory.get(50.0f, Direction.ANY, 0f);

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

        tm.unlisten(tl);
    }


    @Test
    public void testCase2(){

        flag= false;

        Settings settings;
        settings = SettingsFactory.get(0.0f, Direction.ANY, 0.5f);


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
        assertTrue(flag);

        tm.unlisten(tl);

    }

    @Test
    public void testCase3(){
        flag = false;

        Settings settings;
        settings = SettingsFactory.get(5.0f, Direction.DOWN, 0.0f);

        TemperatureListener tl = tm.listen(settings, new Callback() {
            @Override
            public void execute() {
                flag = !flag;
            }
        });
        // Test fall in temperature  below threshold
        tm.setTemperature(-1f, CELSIUS);
        tm.setTemperature(-5f, CELSIUS);
        assertFalse(flag);


        // Test fall in temperature  above threshold
        tm.setTemperature(10f, CELSIUS);
        tm.setTemperature(7f, CELSIUS);
        assertFalse(flag);

        // Test fall in trigger notification by falling belo threshold
        tm.setTemperature(4f, CELSIUS);
        assertTrue(flag);

        // Test fall in trigger notification again
        tm.setTemperature(10f, CELSIUS);
        assertTrue(flag);
        tm.setTemperature(-1f, CELSIUS);
        assertFalse(flag);

        tm.unlisten(tl);

    }

    @Test
    public void testCase4(){
        flag = false;

        Settings settings;
        settings = SettingsFactory.get(-1.0f, Direction.DOWN, 2.0f);

        TemperatureListener tl = tm.listen(settings, new Callback() {
            @Override
            public void execute() {
                flag = !flag;
            }
        });


        // Test fall in temperature  below threshold
        tm.setTemperature(-1f, CELSIUS);
        tm.setTemperature(-5f, CELSIUS);
        assertFalse(flag);

        // Test fall in temperature  above threshold
        tm.setTemperature(10f, CELSIUS);
        tm.setTemperature(7f, CELSIUS);
        assertFalse(flag);

        // Test proper notification fall on threshold
        tm.setTemperature(-1.0f, CELSIUS);
        assertTrue(flag);

        // Tesh ignore band of some degrees around threshold after being triggered
        tm.setTemperature(1.0f, CELSIUS);
        assertTrue(flag);
        tm.setTemperature(-1.5f, CELSIUS);
        assertTrue(flag);

        // Test Leave ignore band and fall back on threshold
        tm.setTemperature(2.0f, CELSIUS);
        assertTrue(flag);
        tm.setTemperature(-1.5f, CELSIUS);
        assertFalse(flag);

        tm.unlisten(tl);

    }

    @Test
    public void testCase5(){
        flag = false;

        Settings settings;
        settings = SettingsFactory.get(5.0f, Direction.UP, 0.0f);

        TemperatureListener tl = tm.listen(settings, new Callback() {
            @Override
            public void execute() {
                flag = !flag;
            }
        });
        // Test rise in temperature  above threshold
        tm.setTemperature(10f, CELSIUS);
        tm.setTemperature(15f, CELSIUS);
        assertFalse(flag);


        // Test rise in temperature  below threshold
        tm.setTemperature(2f, CELSIUS);
        tm.setTemperature(3f, CELSIUS);
        assertFalse(flag);

        // Test fall in trigger notification by rising above threshold
        tm.setTemperature(10f, CELSIUS);
        assertTrue(flag);

        // Test rise and trigger notification again
        tm.setTemperature(-100f, CELSIUS);
        assertTrue(flag);
        tm.setTemperature(5f, CELSIUS);
        assertFalse(flag);

        tm.unlisten(tl);

    }

    @Test
    public void testCase6(){
        flag = false;

        Settings settings;
        settings = SettingsFactory.get(30.0f, Direction.UP, 0.2f);

        TemperatureListener tl = tm.listen(settings, new Callback() {
            @Override
            public void execute() {
                flag = !flag;
            }
        });


        // Test rise in temperature  above threshold
        tm.setTemperature(50f, CELSIUS);
        tm.setTemperature(100f, CELSIUS);
        assertFalse(flag);

        // Test rise in temperature  below threshold
        tm.setTemperature(-20f, CELSIUS);
        tm.setTemperature(25f, CELSIUS);
        assertFalse(flag);

        // Test proper notification rise on threshold
        tm.setTemperature(30.0f, CELSIUS);
        assertTrue(flag);

        // Test ignore band of some degrees around threshold after being triggered
        tm.setTemperature(30.1f, CELSIUS);
        assertTrue(flag);
        tm.setTemperature(29.8f, CELSIUS);
        assertTrue(flag);

        // Test Leave ignore band and rise back above threshold
        tm.setTemperature(2.0f, CELSIUS);
        assertTrue(flag);
        tm.setTemperature(30.5f, CELSIUS);
        assertFalse(flag);

        tm.unlisten(tl);

    }

    @Test
    public void testFarenheitToCelsius(){
        tm.setTemperature(0f, CELSIUS);
        Float farenheit = tm.getTemperature(FARENHEIT);
        assertEquals(farenheit,(Float) 32.0F);
        tm.setTemperature(32f, FARENHEIT);

        Float celsius = tm.getTemperature(CELSIUS);
        assertEquals(celsius,(Float) 0.0f);

        assertEquals((Float) 10.0F, tm.toCelsius(50.0F));
        assertEquals((Float) 50.0F, tm.toFarenheit(10.0F));

        tm.setTemperature(10.0F, CELSIUS);
        tm.reportTemperature(FARENHEIT);

        tm.setTemperature(50.0F, FARENHEIT);
        tm.reportTemperature(CELSIUS);

    }

    @Test
    public void testReadData(){
        //tm.ReadData("src/main/resources/data/LinearRise.csv");
        //tm.ReadData("src/main/resources/data/LinearFall.csv");
        Settings ls1 = SettingsFactory.get(0.5F, Direction.UP, 0.0F);

        Settings ls2 = SettingsFactory.get(-0.5F, Direction.DOWN, 0.0F);

        tm.listen(ls1, new Callback() {
            @Override
            public void execute() {
                //fail("Sine doesn't exceed 1");
                System.out.println("value above 0.5");
            }
        });

        tm.listen(ls2, new Callback() {
            @Override
            public void execute() {
                //fail("Sine is greater then  -1");
                System.out.println("value below -0.5");

            }
        });
        tm.ReadData("src/main/resources/data/sinesavalues2.csv");

    }


}
