# jOrganizer - Organize your daily needs!

## Configuration
Any optional feature can be activated by putting the corresponding feature key into the properties file. The values to this keys are, in cases of 'first level features', that means features without a submenu (or rather a sub-submenu such as the `clock`), fully optional and serve merely as descriptions. The values of feature keys with an own submenu, like `print`, represent the according subfeature. You can use the below listed configuration file, which is complete, as a template to create your own configuration by removing (or commenting out) the features you don't need:

```properties
# non optional application features
calendar=calendar function

# optional first level features
clock=show date and time
alarm=set up an alarm
calc=start basic calculator
notes=write down some notes
users=advanced user management

# impoort and export functions for calendar
import=ics,csv
export=md,html,txt,ics,csv

# reminder
#reminder=mute
reminder=sound

# notes history
history=history function for your notes
share=facebook,email,twitter,linkedin
```


## Running the application
Just type the following line into your favorite shell (__bash/zsh__ are recommended) and the application will run:

    java -jar jOrganizer.jar

The application starts with the default (minimal) configuration. If you are interested in further features and functions, you should provide an own configuration file (see the aforementioned example). You can also simply copy the `full.properties` file inside of __jOrganizer.jar__ and alter the configuration. Save this file somewhere and type the following line:

    java -jar jOrganizer.jar <pathToConfigFile>