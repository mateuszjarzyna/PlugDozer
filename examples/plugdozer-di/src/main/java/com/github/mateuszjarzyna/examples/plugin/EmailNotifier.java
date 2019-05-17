package com.github.mateuszjarzyna.examples.plugin;

import com.github.mateuszjarzyna.examples.repository.UserRepository;
import com.github.mateuszjarzyna.plugdozer.annotation.Plugin;

import javax.inject.Inject;
import java.util.Optional;

@Plugin
public class EmailNotifier implements UserNotifier {

    private final UserRepository userRepository;
    private final EmailSender emailSender;

    // PlugDozer can inject other plugins as a dependency or can use instance of any class provided by programmer
    // Don't forget about @Inject annotation
    @Inject
    public EmailNotifier(UserRepository userRepository, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.emailSender = emailSender;
    }

    @Override
    public void notifyUser(String username) {
        Optional<String> email = userRepository.getEmail(username);
        email.ifPresent(e -> emailSender.sendEmail(e, "Hey, wake up!"));
    }
}
