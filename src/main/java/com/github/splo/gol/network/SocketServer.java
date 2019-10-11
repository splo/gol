package com.github.splo.gol.network;

import com.github.splo.gol.api.Grid;
import com.github.splo.gol.io.GridCodec;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {

    private final List<Socket> clientSockets = new ArrayList<>();

    public void run(final int port) {
        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                final Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from " + clientSocket.getRemoteSocketAddress());
                synchronized (clientSockets) {
                    clientSockets.add(clientSocket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendGrid(final Grid grid) {
        final byte[] encodedGrid = GridCodec.encodeGrid(grid);
        synchronized (clientSockets) {
            final List<Socket> closedSockets = new ArrayList<>();
            clientSockets.forEach(socket -> {
                try {
                    final OutputStream outputStream = socket.getOutputStream();
                    // First send the data size in bytes.
                    outputStream.write(encodedGrid.length);
                    // TODO: Improve the protocol to allow grids larger than 255 bytes.
                    // Then send the whole grid.
                    outputStream.write(encodedGrid);
                } catch (IOException e) {
                    System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
                    try {
                        socket.close();
                    } catch (final IOException ignored) {
                    }
                    closedSockets.add(socket);
                }
            });
            clientSockets.removeAll(closedSockets);
        }
    }
}
