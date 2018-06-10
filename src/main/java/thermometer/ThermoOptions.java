package main.java.thermometer;

import java.util.Observer;

/**
 * Created by carlosballadares on 2018-06-08.
 */
public abstract class ThermoOptions implements Observer {
    public int notificationTemp;
    public boolean isCelsius;


    public ThermoOptions(int notificationTemp, boolean isCelsius){
        this.notificationTemp = notificationTemp;
        this.isCelsius = isCelsius;
    }

    public abstract void callback(int temp);

    public abstract void onNotification(int temp);

}
