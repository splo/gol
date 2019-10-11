package com.github.splo.gol;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class GameOfLifeRunner {

    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        final JCommander jCommander = JCommander.newBuilder().programName("gol").addObject(configuration).build();
        try {
            jCommander.parse(args);
        } catch (final ParameterException e) {
            final StringBuilder out = new StringBuilder();
            e.getJCommander().getUsageFormatter().usage(out);
            System.err.println(e.getMessage());
            System.err.println(out.toString());
            System.exit(1);
        }
        if (configuration.isHelp()) {
            jCommander.usage();
            System.exit(0);
        }
        if (configuration.isVersion()) {
            System.out.println((String.format("gol %s",
                    Optional.ofNullable(GameOfLifeRunner.class.getPackage().getImplementationVersion())
                            .orElse("unknown"))));
            System.exit(0);
        }

        Strategy strategy = null;
        if (configuration.getStrategy().equals("B3S23")) {
            strategy = new B3S23Strategy();
        } else if (configuration.getStrategy().equals("B12345S12345")) {
            strategy = new B12345S12345Strategy();
        } else {
            System.err.printf("Wrong strategy name: \"%s\"; expected one of \"%s\", \"%s\"%n",
                    configuration.getStrategy(),
                    "B3S23",
                    "B12345S12345");
            System.exit(1);
        }

        final Path gridFilePath = Paths.get(configuration.getGridFilename());
        final Grid initialGrid;
        if (configuration.isNewGrid() || !Files.exists(gridFilePath)) {
            initialGrid = buildRandomGrid(configuration.getWidth(),
                    configuration.getHeight(),
                    configuration.getAlivePercent());
        } else {
            initialGrid = readGridFromFile(gridFilePath);
        }

        Consumer<Grid> listener = null;
        if (configuration.isServer()) {
            // Server mode.
            final SocketServer socketServer = new SocketServer();
            new Thread(() -> socketServer.run(configuration.getPort())).start();
            listener = grid -> {
                socketServer.sendGrid(grid);
                writeGridToFile(grid, gridFilePath);
            };
        } else if (configuration.isClient()) {
            // Client mode.
            final SocketClient socketClient = new SocketClient();
            socketClient.run(configuration.getHost(), configuration.getPort());
            System.exit(0);
        } else {
            // Solo mode.
            listener = grid -> {
                System.out.println(grid);
                writeGridToFile(grid, gridFilePath);
            };
            System.out.println(initialGrid);
        }

        final GameOfLife gameOfLife = new GameOfLife(strategy, listener, initialGrid);

        float frequency = configuration.getFrequency();
        if (frequency > 1000f) {
            frequency = 1000f;
        }
        final ConstantFrequencyUpdater updater = new ConstantFrequencyUpdater(gameOfLife::computeNextGrid, frequency);
        updater.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            updater.stop();
            updater.awaitStopped();
        }));
        updater.awaitStopped();
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
