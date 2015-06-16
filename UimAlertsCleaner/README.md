# UIM Alerts Cleaner

## Description
This application allows to Acknowledge (*clear* in UIM terminology) an alert in UIM from the command line. It is 
specially useful to integrate to another system like SOI, that also manages alerts.

## Build

You will need maven (and of course Java) in order to build the application. Go into the main directory and execute 
```
mvn clean package
```
Then, in the `target` directory you will have a jar file with a name similar to `uac-1.0.0-RELEASE.jar`. Create a `lib`
directory inside the main one and copy the jar file there.

## Installation

1. Create a directory where you will install the application. For example: `D:\CA\UAC`.
2. Copy the following directories from your build path to the destination directory:
  * lib
  * config

3. Manually create the `control` and `log` directories inside the installation directory.
4. Edit the following files and substitute the example paths with the ones in your environment (e.g., Java path, 
application directory), as well as the username and log file destination.
  * config/config.properties
  * config/log4j.properties
  * bin/run.bat
  * bin/encrypt.bat

5. Finally, execute the script `bin/encrypt.bat` in order to encrypt the connection password, and copy the result to the `uim.password` property in the `config.properties` file. For example:
```
bin\encrypt.bat mypassword
```

## Usage

To execute it, run the script `bin/run.bat` from the command line, passing the id of the alert as an argument. For example:

```
bin\run.bat YV12340000-23211
```

As the application uses the following regular expression to validate the id: `".*-([A-Z]{2}[0-9]{8}-[0-9]{5})-.*"`,
you can also pass the Source Alert Id from SOI, as in the following example:

```
bin\run.bat CA:0003:VDFJGFEDLE.forwardinc.com:sdfsdjr3w01-KY12300049-76631-bkdeoe2e01
```

## Uninstallation 

Simply remove the directory where you copied everything.

