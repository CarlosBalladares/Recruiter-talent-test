# Thermometer_question

Check the test results in the Travis Build:
[![Build Status](https://travis-ci.org/CarlosBalladares/Recruiter-talent-test.svg?branch=master)](https://travis-ci.org/CarlosBalladares/Recruiter-talent-test)
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


If you are still reading I apologize for my excesive use of buzzwords.
