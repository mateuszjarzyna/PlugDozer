package com.github.mateuszjarzyna.examples.repository;

import java.util.Optional;

public class UserRepository {

    public Optional<String> getEmail(String username) {
        switch (username) {
            case "haxxor69":
                return Optional.of("haxxor@protonmail.co.uk");
            case "patrick12":
                return Optional.of("patrick.12@yahoo.com");
            default:
                return Optional.empty();
        }
    }

}
