package com.github.splo.gol;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class GameOfLifeRunner {

    public static void main(String[] args) {
        try {
            configureStdout();
            final Configuration configuration = new Configuration();
            final JCommander jCommander = JCommander.newBuilder().programName("gol").addObject(configuration).build();
            jCommander.parse(args);

            if (configuration.isHelp()) {
                jCommander.usage();

            } else if (configuration.isVersion()) {
                System.out.println((String.format("gol %s",
                        Optional.ofNullable(GameOfLifeRunner.class.getPackage().getImplementationVersion())
                                .orElse("unknown"))));

            } else if (configuration.isClient()) {
                // Client mode.
                final SocketClient socketClient = new SocketClient();
                socketClient.run(configuration.getHost(), configuration.getPort());

            } else {
                final Strategy strategy =
                        toStrategy(configuration.getStrategy()).orElseThrow(() -> new IllegalArgumentException(String.format(
                                "Wrong strategy name: \"%s\"; expected one of \"%s\", \"%s\"",
                                configuration.getStrategy(),
                                "B3S23",
                                "B12345S12345")));

                final Path gridFilePath = Paths.get(configuration.getGridFilename());
                final Grid initialGrid;
                if (configuration.isNewGrid() || !Files.exists(gridFilePath)) {
                    initialGrid = buildRandomGrid(configuration.getWidth(),
                            configuration.getHeight(),
                            configuration.getAlivePercent());
                } else {
                    initialGrid = readGridFromFile(gridFilePath);
                }

                Consumer<Grid> listener;
                if (configuration.isServer()) {
                    // Server mode.
                    final SocketServer socketServer = new SocketServer();
                    new Thread(() -> socketServer.run(configuration.getPort())).start();
                    listener = grid -> {
                        socketServer.sendGrid(grid);
                        writeGridToFile(grid, gridFilePath);
                    };
                } else {
                    // Solo mode.
                    listener = grid -> {
                        System.out.println(grid);
                        writeGridToFile(grid, gridFilePath);
                    };
                    System.out.println(initialGrid);
                }

                final GameOfLife gameOfLife = new GameOfLife(strategy, listener, initialGrid);

                final float frequency = Math.min(configuration.getFrequency(), 1000f);

                final ConstantFrequencyUpdater updater =
                        new ConstantFrequencyUpdater(gameOfLife::computeNextGrid, frequency);
                updater.start();
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    updater.stop();
                    updater.awaitStopped();
                }));
                updater.awaitStopped();
            }
        } catch (final ParameterException e) {
            final StringBuilder out = new StringBuilder();
            e.getJCommander().getUsageFormatter().usage(out);
            System.err.println(e.getMessage());
            System.err.println(out.toString());
            System.exit(1);
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void configureStdout() {
        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8.name()));
        } catch (final UnsupportedEncodingException e) {
            throw new IllegalStateException("Error while configuring stdout in UTF-8", e);
        }
    }

    private static Optional<Strategy> toStrategy(final String strategyName) {
        switch (strategyName) {
            case "B3S23":
                return Optional.of(new B3S23Strategy());
            case "B12345S12345":
                return Optional.of(new B12345S12345Strategy());
            default:
                return Optional.empty();
        }
    }

    private static void writeGridToFile(final Grid grid, final Path filePath) {
        try {
            Files.write(filePath, GridCodec.encodeGrid(grid));
        } catch (final IOException e) {
            throw new IllegalStateException(String.format("Error while writing file %s", filePath), e);
        }
    }

    private static Grid readGridFromFile(final Path filePath) {
        try {
            return GridCodec.decodeGrid(Files.readAllBytes(filePath));
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error while reading file %s", filePath), e);
        }
    }

    private static Grid buildRandomGrid(final int width, final int height, final int alivePercent) {
        final SecureRandom randomGenerator = new SecureRandom();
        final Grid.Builder builder = Grid.newBuilder();
        builder.setWidth(width).setHeight(height);
        IntStream.range(0, height)
                .boxed()
                .flatMap(y -> IntStream.range(0, width).mapToObj(x -> new Coordinates(x, y)))
                .forEach(coordinates -> {
                    builder.setCellState(coordinates,
                            randomGenerator.nextInt(100) < alivePercent ? CellState.ALIVE : CellState.DEAD);
                });
        return builder.build();
    }
}
