package main.java;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import main.java.thermometer.*;

/**
 * Created by carlosballadares on 2018-06-08.
 */


public class Main {
    public static boolean CELSIUS= true;
    public static boolean FARENHEIT= false;


    public static void main(String[] args){
        Thermometer tm = new Thermometer();
//        tm.reportTemperature(FARENHEIT);
//        tm.setTemperature(33f, FARENHEIT);
//        tm.reportTemperature(CELSIUS);
//
//        Float temp = 50f;
//
//
//        TemperatureListener tlFreezing = tm.listen("freezing", new Callback() {
//            @Override
//            public void execute() {
//                System.out.println("its freezing");
//            }
//        });
//
//        TemperatureListener tlBoiling = tm.listen("boiling", new Callback() {
//            @Override
//            public void execute() {
//                System.out.println("its boiling");
//            }
//        });
//
//        tm.setTemperature(100f, FARENHEIT);
//        tm.setTemperature(100f, CELSIUS);
//
//
//        tm.unlisten(tlBoiling);
//
//        tm.setTemperature(100f, CELSIUS);
//        tm.setTemperature(0f, CELSIUS);
//
//        tm.unlisten(tlFreezing);
//        tm.setTemperature(0f, CELSIUS);
//        tm.setTemperature(100f, CELSIUS);
//
//        ListenerSettings freezing = new ListenerSettings();
//
//
//        TemperatureListener listen = tm.listen(freezing, new Callback() {
//            @Override
//            public void execute() {
//                System.out.println("freezing");
//            }
//        });

        ListenerSettings fall= new ListenerSettings();
        fall.temperature = 0f;
        fall.direction = ListenerSettings.Direction.DOWN;
        fall.ignoreBand = 0f;

        tm.setTemperature(5f, CELSIUS);

//        tm.listen(fall, new Callback() {
//            @Override
//            public void execute() {
//                System.out.println("We are falling");
//            }
//        });

        ListenerSettings freezing = new ListenerSettings();
        freezing.temperature = 0f;
        freezing.direction = ListenerSettings.Direction.ANY;
        freezing.ignoreBand = 0.5f;

        tm.listen(freezing, new Callback() {
            @Override
            public void execute() {
                System.out.println("We are freezing");
            }
        });

        tm.setTemperature(10f, CELSIUS);

        tm.setTemperature(0f, CELSIUS);
        tm.setTemperature(0.5f, CELSIUS);
        tm.setTemperature(-0.5f, CELSIUS);
        tm.setTemperature(0f, CELSIUS);
        tm.setTemperature(0f, CELSIUS);
        tm.setTemperature(0f, CELSIUS);
        tm.setTemperature(0f, CELSIUS);
        tm.setTemperature(0f, CELSIUS);
        tm.setTemperature(0f, CELSIUS);

        tm.setTemperature(1f, CELSIUS);







    }



}
