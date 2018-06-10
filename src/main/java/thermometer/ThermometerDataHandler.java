package main.java.thermometer;

/**
 * Created by carlosballadares on 2018-06-10.
 */
public class ThermometerDataHandler extends TemperatureListener{

    private Float prevTemp;
    private Float currTemp;
    private boolean ignore;

    private boolean inited;

    ListenerSettings settings;


    public ThermometerDataHandler(ListenerSettings settings, Callback callback) {
        super(callback);
        this.settings = settings;
        this.prevTemp = null;
        this.currTemp = null;

    }

    @Override
    public void temperatureReportEvent(Float newTemp) {

        Float threshold = settings.temperature;
        Float ignoreBand = settings.ignoreBand;


        Float delta=null;
        prevTemp= currTemp;
        currTemp = newTemp;
        if(prevTemp!=null)
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
                if(prevTemp == null) break;
                if(!ignore && delta < 0f && newTemp <= threshold && prevTemp>threshold){
                    if(ignoreBand != 0f)
                        ignore = true;

                    callback.execute();
                }else if(ignore && threshold + ignoreBand < newTemp){
                    ignore = false;
                }
                break;
            case UP:
                if(prevTemp == null) break;

                if(!ignore&& delta >0f && newTemp >=threshold && prevTemp<threshold){
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