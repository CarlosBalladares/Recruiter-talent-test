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


    public Thermometer() {
        this.eb = new EventBus();
        this.temperature = 0f;
        this.definitions = new HashMap<>();
    }

    public Float getTemperature(boolean celsius) {
        if(!celsius){
            return (temperature*9/5)+32;
        }
        return temperature;
    }

    public static Float toCelsius(Float far){
        return (far-32)*5/9;
    }

    public static Float toFarenheit(Float cel){
        return (cel*9/5)+32;
    }

    public void setTemperature(Float temperature, boolean celsius) {
        if(!celsius){
            temperature  = (temperature-32)*5/9;
        }

        this.temperature= temperature;

        eb.post(this.temperature);

    }

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


    public TemperatureListener listen(Settings lp, Callback cb){

        TemperatureListener tl =new ThermometerDataHandler(lp, cb);

        eb.register(tl);

        return tl;
    }

    public void unlisten(TemperatureListener tl){
        eb.unregister(tl);
    }

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

