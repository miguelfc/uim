package com.ca.uim.uac;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author Miguel Fernandes (fermi14/miguelfc)
 */

public class Main {
    // Arguments:
    // <source_alert_id>
    // Example: CA:00036:VMWTBSOIIFWP01.wtes.whitees.corp:bebfacoori02-KY12300049-76631-bebfacoori02
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ExecutionLock ua = new ExecutionLock("uac");

        PropertyConfigurator.configure(Configuration.getConfigPath() + "/log4j.properties");
        Logger log = Logger.getLogger(Main.class.getName());

        // MFC Verify if this is needed
        if (ua.isAppActive()) {
            Random random = new Random(System.currentTimeMillis()); // or new Random(someSeed);
            int delay = 5 + random.nextInt(30);
            log.warn("There is another instance of this application active. This execution will be delayed " + delay + " seconds.");
            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            log.info("Retrying...");

            if (ua.isAppActive()) {
                log.error("There is another instance of this application active. This execution will be aborted.");
                log.error("Stopped.");
                System.exit(1);
            }
        }

        log.info("Starting application...");

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(Configuration.getConfigPath() + "/config.properties");
            // load a properties file
            prop.load(input);
            // get all configuration properties
            Configuration.setUimUrl(prop.getProperty("uim.url"));
            Configuration.setUsername(prop.getProperty("uim.username"));

            BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
            textEncryptor.setPassword("caencryptor");
            try {
                Configuration.setPassword(textEncryptor.decrypt(prop.getProperty("uim.password")));
            } catch (Exception e) {
                log.error("Password is malformed. Please encrypt it and try again.", e);
                return;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (args.length != 1) {
            log.error("The source alert ID is needed. Please specify it after the command in the command line.");
            System.exit(1);
        }

        Executor executor = new Executor();
        if (!executor.setAlertId(args[0])) {
            log.error("The alert syntax is not correct. Please verify it.");
            log.error("Example: CA:00036:FORWRMDIFWP01.forwardinc.com:fefdacoori02-KY12300049-76631-forwrmd01");
            System.exit(1);
        }

        if (!executor.execute()) {
            log.error("A problem occurred while executing the application. Aborting.");
        }
        else {
            log.info("The UIM Alert cleaner finished successfully");
        }

    }
}
