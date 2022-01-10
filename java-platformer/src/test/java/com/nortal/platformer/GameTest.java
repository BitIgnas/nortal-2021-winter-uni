package com.nortal.platformer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game("platforms-test.csv");
    }

    @Test
    void findNextPlatform() {
        Platform expectedNextPlatform = new Platform(1, 200);

        Platform actualPlatform = game.findNextPlatform();

        assertThat(actualPlatform).isNotNull();
        assertThat(actualPlatform).isExactlyInstanceOf(Platform.class);
        assertThat(actualPlatform).isEqualTo(expectedNextPlatform);
    }

    @Test
    void calculateStartToEndIndexCost() {
        int expectedIndexSum = 300;

        int actualIndexSum = game.calculateStartToEndIndexCost(0, 1);

        assertThat(actualIndexSum).isNotNull();
        assertThat(actualIndexSum).isEqualTo(expectedIndexSum);
    }

    @Test
    void readPlatforms() {
        List<Platform> expectedPlatforms = List.of(new Platform(0, 100), new Platform(1, 200), new Platform(3, 400));

        List<Platform> actualPlatforms = game.readPlatforms();

        assertThat(actualPlatforms).isNotNull();
        assertThat(actualPlatforms.get(0)).isExactlyInstanceOf(Platform.class);
        assertThat(actualPlatforms.size()).isEqualTo(3);
        assertThat(expectedPlatforms).isIn(actualPlatforms);
    }
}