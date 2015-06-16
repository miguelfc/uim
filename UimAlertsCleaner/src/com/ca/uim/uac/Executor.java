package com.ca.uim.uac;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * @author Miguel Fernandes (fermi14/miguelfc)
 */

public class Executor {

    Logger log = Logger.getLogger(Executor.class.getName());

    private String alertId;

    public Boolean setAlertId(String sourceAlertId) {
        if (!sourceAlertId.matches(".*-[A-Z]{2}[0-9]{8}-[0-9]{5}-.*")) {
            return false;
        }
        else {
            this.alertId = sourceAlertId.replaceAll(".*-([A-Z]{2}[0-9]{8}-[0-9]{5})-.*", "$1");
            return true;
        }
    }

    public Boolean execute() {
        return this.sendAck(this.alertId);
    }

    private Boolean sendAck(String alertId) {
        URL url;
        Boolean result = true;
        log.info("Proceeding to clear the alert with ID <" + this.alertId + ">");
        try {
            url = new URL(Configuration.getUimUrl() + "/rest/alarms/" + alertId + "/ack");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            byte[] encodedAuth = Base64.encodeBase64((Configuration.getUsername() + ":" + Configuration.getPassword()).getBytes());
            conn.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
            OutputStream os = conn.getOutputStream();
            os.flush();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                log.warn("Alert was not found in UIM. No action was performed. HTTP Code: <" + HttpURLConnection.HTTP_NOT_FOUND + ">");
                result = false;
            }
            else if (conn.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                log.warn("Authentication credentials are invalid. Please check the configuration file. HTTP Code: <" + HttpURLConnection.HTTP_UNAUTHORIZED + ">");
                result = false;
            }
            else if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.error("An error occurred in the acknowledge request. See details below.", e);
            result = false;
        }

        return result;
    }
}
