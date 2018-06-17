# Thermometer_question

[![Build Status](https://travis-ci.org/CarlosBalladares/Thermometer_question.svg?branch=master)](https://travis-ci.org/CarlosBalladares/Thermometer_question)
[![Coverage Status](https://coveralls.io/repos/github/CarlosBalladares/Thermometer_question/badge.svg?branch=master)](https://coveralls.io/github/CarlosBalladares/Thermometer_question?branch=master)


# To use the Thermometer class

'''
Thermometer tm = new Thermometer();


Settings settings = SettingsFactory.get("Freezing");

tm.listen(settings, new Callback() {

            public void execute() {
                //Do something when its freezing.
            }
});

tm.readData(somecsvfilepath);



'''

# Original question
![Alt text](src/main/resources/thermometerq.png)
