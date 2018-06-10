package main.java.thermometer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.HashMap;


/**
 * Created by carlosballadares on 2018-06-08.
 */

class SimpleTemperatureListener extends TemperatureListener{

    protected Float       observedTemp;


    public SimpleTemperatureListener(Float observedTemp, Callback callback) {
        super(callback);
        this.observedTemp = observedTemp;
    }

    @Subscribe
    public void temperatureReportEvent(Float newTemp){
        if(newTemp.equals(observedTemp)){
            callback.execute();
        }
    }

}

class ThresholdTemperatureListener extends  TemperatureListener{

    protected Float target;
    protected Float range;

    public ThresholdTemperatureListener(Float target, Float range, Callback callback) {
        super(callback);
        this.target = target;
        this.range = range;
    }

    @Override
    public void temperatureReportEvent(Float newTemperature) {
        if(inRange(newTemperature)){
            callback.execute();
        }
    }

    protected boolean inRange(Float temp){
        return target-range<=temp && temp <= target+range;
    }
}

class BandThresholdTemperatureListener extends TemperatureListener{
    protected Float target;
    protected Float band;
    protected boolean inBand;

    public BandThresholdTemperatureListener(Float target, Float band, Callback callback) {
        super(callback);
        this.target = target;
        this.band = band;
        this.inBand = false;
    }

    @Override
    public void temperatureReportEvent(Float newTemperature) {
        if(!inBand){
            if(newTemperature == target){
                inBand = true;
                callback.execute();
            }
        }else{
            if(!inRange(newTemperature)){
                inBand = false;
            }
        }
    }

    protected boolean inRange(Float temp){
        return target-band<=temp && temp <= target+band;
    }
}

class slopedTemperatureListener extends TemperatureListener{
    public slopedTemperatureListener(Callback callback) {
        super(callback);
    }

    @Override
    public void temperatureReportEvent(Float newTemperature) {

    }
}



class AdvancedTemperatureListener extends TemperatureListener{

    private Float prevTemp;
    private Float currTemp;
    private boolean ignore;

    ListenerSettings settings;


    public AdvancedTemperatureListener(ListenerSettings settings, Callback callback) {
        super(callback);
        this.settings = settings;
        this.prevTemp = 0f;
        this.currTemp = 0f;

    }

    @Override
    public void temperatureReportEvent(Float newTemp) {

        Float threshold = settings.temperature;
        Float ignoreBand = settings.ignoreBand;


        Float delta;
        prevTemp= currTemp;
        currTemp = newTemp;

        delta = currTemp - prevTemp;

        switch (settings.direction){
            case ANY:
                if(!ignore){
                    if(newTemp.equals(threshold)){
                        if(ignoreBand != 0f) {
                            ignore = true;
                        }
                        callback.execute();
                    }
                }else{
                    if(!inRange(newTemp)){
                        ignore = false;
                    }
                }
                break;

            case DOWN:
                if(!ignore && delta < 0f && newTemp <= threshold){
                    if(ignoreBand != 0f)
                        ignore = true;

                    callback.execute();
                }else if(ignore && threshold - ignoreBand <= newTemp){
                    ignore = false;
                }
                break;
            case UP:
                if(!ignore&& delta >0f && newTemp >=threshold){
                    if(ignoreBand != 0f)
                        ignore = true;

                    callback.execute();
                }else if(ignore && newTemp<=threshold+ignoreBand){
                    ignore = false;
                }
                break;
            default:

                break;
        }


    }

    protected boolean inRange(Float temp){
        Float threshold = settings.temperature;
        Float band      = settings.ignoreBand;

        return threshold-band<=temp && temp <= threshold+band;
    }
}




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


    public TemperatureListener listen(String temperature, Callback callback){
        TemperatureListener tl= null;
        String tempCondition = temperature.toLowerCase();

        if(tempCondition.equals(FREEZING_STRING)){
            tl = new SimpleTemperatureListener(FREEZINGTEMP, callback);
        }else if(tempCondition.equals(BOILING_STRING)){
            tl = new SimpleTemperatureListener(BOILINGTEMP, callback);
        }

        eb.register(tl);
        return tl;
    }

    public TemperatureListener listen(ListenerSettings lp, Callback cb){

        TemperatureListener tl =new AdvancedTemperatureListener(lp, cb);

        eb.register(tl);

        return tl;
    }

    public void unlisten(TemperatureListener tl){
        eb.unregister(tl);
    }


}

