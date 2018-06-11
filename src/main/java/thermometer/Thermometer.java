package main.java.thermometer;

import com.google.common.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by carlosballadares on 2018-06-08.
 */
public class Thermometer{


    private static boolean CELSIUS= true;
    private static boolean FARENHEIT= false;
    private static String CELSIUS_STRING = "CELSIUS";
    private static String FARENHEIT_STRING = "FARENHEIT";
    private EventBus eb;
    private static Float FREEZINGTEMP= 0f;
    private static Float BOILINGTEMP= 100f;
    private static String FREEZING_STRING = "freezing";
    private static String BOILING_STRING = "boiling";

    private HashMap<String, Float> definitions;



    private Float temperature;


    /**
     * Instantiates a new Thermometer.
     */
    public Thermometer() {
        this.eb = new EventBus();
        this.temperature = 0f;
        this.definitions = new HashMap<>();
    }

    /**
     * Gets temperature.
     *
     * @param celsius indicates the desired format
     * @return the temperature
     */
    public Float getTemperature(boolean celsius) {
        if(!celsius){
            return (temperature*9/5)+32;
        }
        return temperature;
    }

    /**
     * Converts Farenheit to Celsius.
     *
     * @param far Temperature in farenheit
     * @return Celsius  degrees as Float
     */
    public static Float toCelsius(Float far){
        return (far-32)*5/9;
    }

    /**
     * Converts Celsius to Farenheit.
     *
     * @param cel Temperature in Celsius
     * @return Temperature in Farenheit
     */
    public static Float toFarenheit(Float cel){
        return (cel*9/5)+32;
    }

    /**
     * Sets temperature and notifies all listeners
     *
     * @param temperature the new temperature
     * @param isCelsius     indicates the new temperature's system
     */
    public void setTemperature(Float temperature, boolean isCelsius) {
        if(!isCelsius){
            temperature  = (temperature-32)*5/9;
        }

        this.temperature= temperature;

        eb.post(this.temperature);

    }

    /**
     * Reports temperature to the commandline
     *
     * @param isCelsius the desired temperature system for the report
     */
    public void reportTemperature(boolean isCelsius){
        Float temp;
        String type;
        if (isCelsius){
            temp = this.temperature;
            type = CELSIUS_STRING;
        }else{
            temp = getTemperature(FARENHEIT);
            type = FARENHEIT_STRING;
        }

        System.out.println("The temperature is "+temp+"  degrees "+type);
    }


    /**
     * Registers a listener with the desired properties. See the class
     * Settings. And when the specified conditions are met the callback's execute method
     * is called. The SettingsFacotoryClass can provide an easier way to define settings.
     *
     * @param lp the preferences, which define temperature, Direction of change, ignore after trigger(within a range)
     * @param cb A callback object with an execute method that will be called when the desired even occurs
     * @return the temperature listener which is registered.
     */
    public TemperatureListener listen(Settings lp, Callback cb){

        TemperatureListener tl =new ThermometerDataHandler(lp, cb);

        eb.register(tl);

        return tl;
    }

    /**
     * Unlisten. Removes a temperature listener. No more notifications, will be
     * sent once the corresponding listener is removed. Whoever calls this must
     * keep track of the TemperatureListener object
     *
     * @param tl the Temerature listener object
     */
    public void unlisten(TemperatureListener tl){
        eb.unregister(tl);
    }

    /**
     * Read data. Reads a csv file form a path. It is expected that the
     * CSV file consists of one line and only temperature data.
     *
     * @param path the path
     */
    public void ReadData(String path){
        System.out.println("reading : "+path);

        String LinearRise = path;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(LinearRise));
            while ((line = br.readLine()) != null) {

                String[] data = line.split(cvsSplitBy);

                //System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
                for(int i =0; i<data.length;i++){
                    setTemperature(Float.parseFloat(data[i]), CELSIUS);
                   // reportTemperature(CELSIUS);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }



}

