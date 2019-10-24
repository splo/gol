package com.github.splo.gol.io;

import com.github.splo.gol.api.CellState;
import com.github.splo.gol.api.Coordinates;
import com.github.splo.gol.api.Grid;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class GridCodec {

    public static final int ENCODING_VERSION = 1;

    public static byte[] encodeGrid(final Grid grid) {
        try (final var outBytes = new ByteArrayOutputStream();
             final var outData = new DataOutputStream(outBytes)) {
            outData.writeByte(ENCODING_VERSION);
            outData.writeInt(grid.getWidth());
            outData.writeInt(grid.getHeight());
            for (int y = 0; y < grid.getHeight(); y++) {
                for (int x = 0; x < grid.getWidth(); x++) {
                    final var cellState = grid.getCellState(new Coordinates(x, y));
                    outData.writeBoolean(cellState == CellState.ALIVE);
                }
            }
            return outBytes.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected error while encoding grid", e);
        }
    }

    public static Grid decodeGrid(final byte[] bytes) {
        try (final var inBytes = new ByteArrayInputStream(bytes);
             final var inData = new DataInputStream(inBytes)) {
            final var version = inData.readByte();
            if (version != ENCODING_VERSION) {
                throw new IllegalArgumentException(String.format("Cannot decode a grid with version %s", version));
            }
            final var width = inData.readInt();
            final var height = inData.readInt();
            final Grid.Builder gridBuilder = Grid.newBuilder().setWidth(width).setHeight(height);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    final var isAlive = inData.readBoolean();
                    gridBuilder.setCellState(new Coordinates(x, y), isAlive ? CellState.ALIVE : CellState.DEAD);
                }
            }
            return gridBuilder.build();
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected error while decoding grid", e);
        }
    }
}
