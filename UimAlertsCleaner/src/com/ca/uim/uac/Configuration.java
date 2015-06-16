package com.ca.uim.uac;

/**
 * @author Miguel Fernandes (fermi14/miguelfc)
 */

public class Configuration {

    private static String configPath = "config";
    private static String controlPath = "control";
    
    private static String uimUrl;
    private static String username;
    private static String password;
    private static String closeMessage;
    
    public static String getConfigPath() {
        return configPath;
    }
    public static void setConfigPath(String configPath) {
        Configuration.configPath = configPath;
    }
    public static String getControlPath() {
        return controlPath;
    }
    public static void setControlPath(String controlPath) {
        Configuration.controlPath = controlPath;
    }

    public static String getUimUrl() {
        return uimUrl;
    }
    public static void setUimUrl(String uimHost) {
        Configuration.uimUrl = uimHost;
    }
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        Configuration.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public static void setPassword(String password) {
        Configuration.password = password;
    }
    public static String getCloseMessage() {
        return closeMessage;
    }
    public static void setCloseMessage(String closeMessage) {
        Configuration.closeMessage = closeMessage;
    }
    
}
