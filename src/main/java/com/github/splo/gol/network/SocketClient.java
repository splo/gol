package com.github.splo.gol.network;

import com.github.splo.gol.io.GridCodec;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient {

    private static final long TIMEOUT_MS = 5000;

    public void run(final String host, final int port) {
        try (final var socket = new Socket(host, port);
             final var input = new BufferedInputStream(socket.getInputStream())) {
            System.out.println("Connected to " + socket.getRemoteSocketAddress());
            long lastReadTimeMs = System.currentTimeMillis();
            while (System.currentTimeMillis() - lastReadTimeMs < TIMEOUT_MS) {
                if (input.available() > 0) {
                    // First read the data size in bytes.
                    final var dataLength = input.read();
                    final var buffer = new byte[dataLength];
                    // Then read the whole grid.
                    input.read(buffer);
                    lastReadTimeMs = System.currentTimeMillis();
                    final var grid = GridCodec.decodeGrid(buffer);
                    System.out.println(grid);
                }
            }
            System.out.println("Server connection timed out");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
