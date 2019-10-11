package com.github.splo.gol;

import com.beust.jcommander.Parameter;

public class Configuration {

    @Parameter(names = {"-n", "--new"},
               order = 10,
               description = "Start with a new grid instead of loading the save file")
    private boolean newGrid = false;

    @Parameter(names = {"-w", "--width"}, order = 20, description = "Starting grid width")
    private int width = 10;

    @Parameter(names = {"-h", "--height"}, order = 21, description = "Starting grid height")
    private int height = 10;

    @Parameter(names = {"-a", "--alive"}, order = 22, description = "Starting grid ratio of alive cells, in %")
    private int alivePercent = 15;

    @Parameter(names = {"-f", "--frequency"}, order = 30, description = "Cell generation update frequency, in Hz")
    private float frequency = 1.f;

    @Parameter(names = {"-s", "--strategy"},
               order = 31,
               description = "Strategy for each update, one of <B3S23|B12345S12345>")
    private String strategy = "B3S23";

    @Parameter(names = {"-g", "--grid-file"}, order = 40, description = "Filename of the grid file")
    private String gridFilename = "grid.gol";

    @Parameter(names = {"--server"}, order = 50, description = "Run as a server host")
    private boolean server;

    @Parameter(names = {"--client"}, order = 51, description = "Run as a client")
    private boolean client;

    @Parameter(names = {"-p", "--port"}, order = 60, description = "Port to listen in client or server mode")
    private int port = 9988;

    @Parameter(names = {"--host"}, order = 61, description = "Server hostname to connect to")
    private String host = "127.0.0.1";

    @Parameter(names = "--version", order = 99, description = "Display version")
    private boolean version;

    @Parameter(names = "--help", order = 100, description = "Display help", help = true)
    private boolean help;

    public Configuration() {
    }

    public boolean isNewGrid() {
        return newGrid;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getAlivePercent() {
        return alivePercent;
    }

    public float getFrequency() {
        return frequency;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getGridFilename() {
        return gridFilename;
    }

    public boolean isServer() {
        return server;
    }

    public boolean isClient() {
        return client;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public boolean isVersion() {
        return version;
    }

    public boolean isHelp() {
        return help;
    }
}
