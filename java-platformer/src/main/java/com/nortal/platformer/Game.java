package com.nortal.platformer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game {

    private final static Logger LOGGER = Logger.getLogger(Game.class.getName());

    private Integer points = 500;
    private Platform activePlatform;
    private final String gameFile;
    private final List<Platform> platforms;

    public Game(String gameFile) {
        this.gameFile = gameFile;
        this.platforms = readPlatforms();

    }

    public void run() {
        this.activePlatform = this.platforms.get(0);

        for (int i = 0; i < platforms.size() - 1; i++) {
            Platform platform = findNextPlatform();
            this.jumpTo(platform);
        }

    }

    public Platform findNextPlatform() {
        int totalBalance = this.points - this.platforms.get(activePlatform.getIndex() + 1).getCost();
        int neededCost = this.platforms.get(activePlatform.getIndex() + 1).getCost() - this.points;

        /**
         * If total balance is greater or equal than 0, the next platform is unlocked, taking away required points.
         * Prior to not having enough points, search moves on to for loops to determine how much to go back and
         * move forward to have enough points to unlock next platform.
         */

        if (totalBalance >= 0) {
            LOGGER.info(String.format("PLATFORMER: Moving to #%d platform with [Total points: %d; %d - %d]",
                    this.platforms.get(activePlatform.getIndex() + 1).getIndex(),
                    this.points - this.platforms.get(activePlatform.getIndex() + 1).getCost(),
                    this.points,
                    this.platforms.get(activePlatform.getIndex() + 1).getCost()
            ));
            this.points = this.points - this.platforms.get(activePlatform.getIndex() + 1).getCost();
            return this.platforms.get(activePlatform.getIndex() + 1);
        }

        /**
         * If needed costs are not met, the for loop determines how much to go back and later get back to
         * current platform in platforms, to have enough to move to the next platform. Each for loop iteration
         * checks if needed costs is equal or greater than current points. If condition is met than loop proceeds
         * adding detour credits to global points.
         */

        for (int i = activePlatform.getIndex() - 1; i >= 0; i--) {
            for (int j = i; j <= activePlatform.getIndex(); j++) {
                if ((calculateStartToEndIndexCost(i, activePlatform.getIndex() - 1) + calculateStartToEndIndexCost(j + 1, activePlatform.getIndex())) >= neededCost) {

                    LOGGER.info(String.format("PLATFORMER: detouring to %d and going to %d AND returning from %d to %d ; [ Total Points: %d + Detouring back: %d + Going back: %d] = %d",
                            activePlatform.getIndex() - 1, i, j, activePlatform.getIndex(),
                            points, calculateStartToEndIndexCost(i, activePlatform.getIndex() - 1), calculateStartToEndIndexCost(j + 1, activePlatform.getIndex()),
                            (points + calculateStartToEndIndexCost(i, activePlatform.getIndex() - 1) + calculateStartToEndIndexCost(j + 1, activePlatform.getIndex()))
                    ));

                    this.points = this.points + calculateStartToEndIndexCost(i, activePlatform.getIndex() - 1) + calculateStartToEndIndexCost(j + 1, activePlatform.getIndex());
                    this.points = this.points - this.platforms.get(this.activePlatform.getIndex() + 1).getCost();
                    LOGGER.info(String.format("PLATFORMER: Moving to #%d platform [AFTER DETOUR]", this.platforms.get(activePlatform.getIndex() + 1).getIndex()));
                    return this.platforms.get(activePlatform.getIndex() + 1);
                }
            }
        }

        return null;
    }

    public int calculateStartToEndIndexCost(int start, int end) {

        /**
         * With the help of streams, calculateStartToEndIndexCost() method finds the sum of cost in declared
         * range of indexes.
         */

        List<Integer> costs = new ArrayList<>();
        IntStream.rangeClosed(start, end).forEach(index -> {
            costs.add(this.platforms.get(index).getCost());
        });

        return costs.stream().mapToInt(Integer::valueOf).sum();
    }

    public List<Platform> readPlatforms() {
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
