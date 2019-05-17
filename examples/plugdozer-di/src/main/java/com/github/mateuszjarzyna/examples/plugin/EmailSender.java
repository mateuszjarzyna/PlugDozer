package com.github.mateuszjarzyna.examples.plugin;

import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

@Plugin
public class EmailSender {

    public void sendEmail(String email, String msg) {
        System.out.println("Sending email to " + email + " : " + msg);
    }

}
