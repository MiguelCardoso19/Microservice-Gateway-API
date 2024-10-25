package com.example.Exercicio15.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class UserAccountUtil {

    @Named("capitalizeName")
    public String capitalizeName(String name) {
        LoggerUtil.log.info("Capitalizing name: {}", name);
        return Arrays.stream(name.split(" "))
                .filter(word -> !word.isEmpty())
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
