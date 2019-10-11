package com.github.splo.gol.api;

public interface Strategy {
    CellState getNextCellState(final CellState previousState, final long livingCells);
}
