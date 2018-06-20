# Thermometer_question

[![Build Status](https://travis-ci.org/CarlosBalladares/Thermometer-question.svg?branch=master)](https://travis-ci.org/CarlosBalladares/Thermometer-question)

# To use the Thermometer class

```java
Thermometer tm = new Thermometer();

Settings settings = SettingsFactory.get("Freezing");

tm.listen(settings, new Callback() {
            public void execute() {
                //Do something when its freezing.
            }
});

tm.readData(somecsvfilepath);

```

# Original question
![Alt text](src/main/resources/thermometerq.png)

