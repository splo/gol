package com.github.splo.gol;

import java.util.Optional;

public class GameOfLifeRunner {

    private static final String VERSION =
            Optional.ofNullable(GameOfLifeRunner.class.getPackage().getImplementationVersion()).orElse("");

    public static void main(String[] args) {
        System.out.println(("gol " + VERSION).trim());
    }
}
