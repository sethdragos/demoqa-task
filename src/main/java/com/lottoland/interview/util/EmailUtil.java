package com.lottoland.interview.util;

import javax.mail.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class EmailUtil {

    static Properties mailConfig;
    private String password;
    private String email;

    public EmailUtil(String email, String password) {

        InputStream inputStr = null;
        mailConfig = new Properties();
        try {
            inputStr = new FileInputStream(".//src//test//resources//mailconfig.properties");
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        try {
            mailConfig.load(inputStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.email = email;
        this.password = password;
    }

    private Properties getServerProperties(String protocol, String host, String port) {

        Properties properties = new Properties();
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        properties.setProperty(String.format("mail.%s.ssl.enable", protocol), "true");

        return properties;
    }

    public String[] fetchEmailsAndDelete() {

        String protocol = mailConfig.getProperty("protocol");
        String host = mailConfig.getProperty("host");
        String port = mailConfig.getProperty("port");
        String[] emailBodies = new String[0];

        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore(mailConfig.getProperty("protocol"));
            store.connect(email, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.getMessages();
            emailBodies = new String[messages.length];

            for (int i = 0; i < messages.length; i++) {
                try {
                    emailBodies[i] = String.valueOf(messages[i].getContent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                messages[i].setFlag(Flags.Flag.DELETED, true);
            }

            inbox.close(false);
            store.close();

        } catch (NoSuchProviderException nsp) {
            System.out.println("No provider for protocol: " + protocol);
            nsp.printStackTrace();
        } catch (MessagingException me) {
            System.out.println("Could not connect to the message store");
            me.printStackTrace();
        }

        return emailBodies;
    }
}
