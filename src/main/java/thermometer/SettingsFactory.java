package main.java.thermometer;

/**
 * Created by carlosballadares on 2018-06-10.
 */
public class SettingsFactory {

    public static ListenerSettings get(Float temp, boolean celsius, ListenerSettings.Direction dir, Float ignoreRange){
        ListenerSettings ls = new ListenerSettings();
        if(!celsius){
            temp = Thermometer.toCelsius(temp);
        }
        ls.temperature = temp;
        ls.direction = dir;
        ls. ignoreBand = ignoreRange;
        return  ls;
    }

    public static ListenerSettings get(Float temp , ListenerSettings.Direction dir, Float ignoreRange){
        ListenerSettings ls = new ListenerSettings();
        ls.temperature = temp;
        ls.direction = dir;
        ls. ignoreBand = ignoreRange;
        return  ls;
    }
}
