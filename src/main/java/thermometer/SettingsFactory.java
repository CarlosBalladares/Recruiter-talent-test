package main.java.thermometer;

/**
 * Created by carlosballadares on 2018-06-10.
 */
public class SettingsFactory {

    /**
     * Get settings. Makes the settings object for the temperature listener.
     *
     * @param temp        The threshold temperature
     * @param celsius     the format of the threshold temperature
     * @param dir         the direction of change (DOWNWARD, UPWARD, ANY)
     * @param ignoreRange Defines a band around the threshold that will be ignored once a notification is                    sent. If the data leaves the band the sender will receive notifications again.                    USeful for data that oscilates around a value.
     * @return the settings object
     */
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

    /**
     * Get settings. Generates the settings object, this constructor assumes that the temperature
     * format is Celsius
     *
     * @param temp        The threshold temperature
     * @param dir         the direction of change (DOWNWARD, UPWARD, ANY)
     * @param ignoreRange Defines a band around the threshold that will be ignored once a notification is                    sent. If the data leaves the band the sender will receive notifications again.                    USeful for data that oscilates around a value.
     * @return the settings object
     */
    public static Settings get(Float temp , Settings.Direction dir, Float ignoreRange){

        return  get(temp, true, dir, ignoreRange);
    }

    /**
     * Get settings. Generates settings objects for common settings, like boiling and freezing temperature.
     *
     * @param name the name
     * @return the settings object
     */
    public static Settings get(String name){

        if (name.toLowerCase().equals("Freezing")){
            return get(0.0F, Settings.Direction.ANY, 0.0F);
        }else if(name.toLowerCase().equals("Boiling")){
            return get(100.0F, Settings.Direction.ANY, 0.0F);
        }else{
            throw new RuntimeException("Unknown parameter "+name);
        }
    }
}
