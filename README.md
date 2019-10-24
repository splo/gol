#  Game of Life

A simple clone of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life).

## Features

- [x] Customizable grid width and height.
- [x] Customizable grid update frequency and strategy that decides when cells are born and survives.
- [x] Automatically save grid state into a file and reload it at next run.
- [x] Start with a new grid with a random and customizable amound of living cells.
- [x] Run in solo mode or only as a server or client.
- [x] Beautifully display the grid in the console! 

## Usage

Once you have a built package, run the command line program `gol`.
If you manually built it, you can find it in `./target/appassembler/bin/gol`.
 
You can specify the following options:

```
Usage: gol [options]
  Options:
    -n, --new
      Start with a new grid instead of loading the save file
      Default: false
    -w, --width
      Starting grid width
      Default: 10
    -h, --height
      Starting grid height
      Default: 10
    -a, --alive
      Starting grid ratio of alive cells, in %
      Default: 15
    -f, --frequency
      Cell generation update frequency, in Hz
      Default: 1.0
    -s, --strategy
      Strategy for each update, one of <B3S23|B12345S12345>
      Default: B3S23
    -g, --grid-file
      Filename of the grid file
      Default: grid.gol
    --server
      Run as a server host
      Default: false
    --client
      Run as a client
      Default: false
    -p, --port
      Port to listen in client or server mode
      Default: 9988
    --host
      Server hostname to connect to
      Default: 127.0.0.1
    --version
      Display version
      Default: false
    --help
      Display help
```

## Development

### Requirements

- Java JDK 13.
- Maven 3.

### Building

```bash
mvn package
```

### Running

```bash
# On Linux/macOS
./target/appassembler/bin/gol
# On Windows
.\target\appassembler\bin\gol.bat
```

To stop the process, hit your Ctrl-C key, or send a SIGINT signal to the process.

### Examples

```bash
# Run with a new grid of size 20x10, with 40% alive cells, updated every 250 ms, with the B12345/S1234,
# saving the grid into the /tmp/grid.gol file, running as a server listening on port 9091:
bin/gol --new --width 20 --alive 40 --frequency 4 --strategy B12345S12345 --grid-file /tmp/grid.gol --port 9091 --server

# Run as a client, connecting to a server at address `127.0.0.1:9091`:
bin/gol --port 9091 --client
```
