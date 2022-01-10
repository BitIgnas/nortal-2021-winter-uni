package com.nortal.platformer;

public class Main {

    public static void main(String[] args) {
        Game game = new Game("platforms.csv");
        game.run();
    }
}
