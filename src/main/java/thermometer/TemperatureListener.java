package main.java.thermometer;

import com.google.common.eventbus.Subscribe;

/**
 * Created by carlosballadares on 2018-06-10.
 */
public abstract class TemperatureListener {

    protected Callback    callback;

    public TemperatureListener(Callback callback) {
        this.callback = callback;
    }

    @Subscribe
    public abstract void temperatureReportEvent(Float newTemperature);
}
