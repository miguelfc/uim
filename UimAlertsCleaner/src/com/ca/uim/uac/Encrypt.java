package com.ca.uim.uac;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author Miguel Fernandes (fermi14/miguelfc)
 */

public class Encrypt {
    public static void main(String[] args) {
        if(args.length > 0){
        
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("caencryptor");
        String myEncryptedText = textEncryptor.encrypt(args[0]);
        String plainText = textEncryptor.decrypt(myEncryptedText);
        System.out.println("The encrypted password for <"+plainText+"> is: " + myEncryptedText);
        }
        else {
            System.out.println("Please include the password to encrypt after the command.");
        }
    }

}
