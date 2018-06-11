package main.java.thermometer;

/**
 * Created by carlosballadares on 2018-06-10.
 */
public class SettingsFactory {

    public static Settings get(Float temp, boolean celsius, Settings.Direction dir, Float ignoreRange){
        Settings ls = new Settings();
        if(!celsius){
            temp = Thermometer.toCelsius(temp);
        }
        ls.temperature = temp;
        ls.direction = dir;
        ls. ignoreBand = ignoreRange;
        return  ls;
    }

    public static Settings get(Float temp , Settings.Direction dir, Float ignoreRange){

        return  get(temp, true, dir, ignoreRange);
    }
}
