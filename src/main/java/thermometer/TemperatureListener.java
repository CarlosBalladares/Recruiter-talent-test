package main.java.thermometer;

import com.google.common.eventbus.Subscribe;

/**
 * Created by carlosballadares on 2018-06-10.
 *
 * Abstract temperature listener. Instances of this class will
 * be subbed to the guava eventbus. temperatureReportEvent handles
 * the data operations then a new data point is received.
 *
 */
public abstract class TemperatureListener {

    protected Callback    callback;

    public TemperatureListener(Callback callback) {
        this.callback = callback;
    }

    @Subscribe
    public abstract void temperatureReportEvent(Float newTemperature);
}
