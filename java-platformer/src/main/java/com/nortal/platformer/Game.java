package com.nortal.platformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game {

    public static void main(String[] args) {
        Game game = new Game("platforms.csv");
        game.run();
    }

    private Integer points = 500;
    private Platform activePlatform;
    private final String gameFile;
    private final List<Platform> platforms;
    private Integer totalJumps = 0;

    public Game(String gameFile) {
        this.gameFile = gameFile;
        this.platforms = readPlatforms();
        this.activePlatform = this.platforms.get(0);
    }

    public void run() {
        int index = 0;

        while (index < 10) {
            Platform nextPlatform = findNextPlatform();
            System.out.println(nextPlatform);
            this.jumpTo(nextPlatform);
            index++;
        }
    }

    private Platform findNextPlatform() {
        int totalBalance = this.points - this.platforms.get(activePlatform.getIndex() + 1).getCost();
        int neededCost = this.platforms.get(activePlatform.getIndex() + 1).getCost();

        if (totalBalance >= 0) {
            this.points = this.points - this.platforms.get(activePlatform.getIndex() + 1).getCost();
            this.activePlatform = this.platforms.get(activePlatform.getIndex() + 1);
            return this.platforms.get(activePlatform.getIndex());
        }

        for (int i = activePlatform.getIndex() - 1; i >= 0; i--) {
            for (int j = i; j <= activePlatform.getIndex(); j++) {
                if ((calculateStartToEndIndexCost(activePlatform.getIndex() - 1, i) + calculateStartToEndIndexCost(j, activePlatform.getIndex())) >= neededCost) {
                    this.points = calculateStartToEndIndexCost(activePlatform.getIndex() - 1, i) + calculateStartToEndIndexCost(j, activePlatform.getIndex());
                    this.activePlatform = this.platforms.get(activePlatform.getIndex() + 1);
                }

            }

        }

        return null;
    }

    private int calculateStartToEndIndexCost(int start, int end) {
        List<Integer> costs = new ArrayList<>();
        IntStream stream = IntStream.rangeClosed(start, end);
        stream.forEach(index -> {
            costs.add(this.platforms.get(index).getCost());
            this.totalJumps++;
        });

        return costs.stream().mapToInt(Integer::valueOf).sum();
    }

    private List<Platform> readPlatforms() {
        List<Platform> platforms = new ArrayList<>();
        InputStream inputStream = Game.class.getClassLoader().getResourceAsStream(this.gameFile);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
            Stream<String> lines = bufferedReader.lines().skip(1);
            lines.forEachOrdered(line -> {
                String[] properties = line.split(",");
                Platform platform = Platform.builder()
                        .index(Integer.parseInt(properties[0].trim()))
                        .cost(Integer.parseInt(properties[1].trim()))
                        .build();

                platforms.add(platform);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return platforms;
    }

    private void jumpTo(Platform platform) {
        activePlatform = platform;
    }
}
