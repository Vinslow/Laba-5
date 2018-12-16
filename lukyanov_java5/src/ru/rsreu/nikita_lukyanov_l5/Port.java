package ru.rsreu.nikita_lukyanov_l5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Port {
    public static void main(String[] args){
        Random random = new Random();
        Queue<Dock> dockQueue = new ArrayDeque<>();
        for (int i = 0; i < 2; i++) {
            dockQueue.add(new Dock(random.nextInt(200)));
        }

        CommandPost commandPost = new CommandPost(dockQueue);
        ArrayList<Ship> ships = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ships.add(new Ship(random.nextInt(200), commandPost));
        }

        for (int i = 0; i < 2; i++) {
            ships.add(new Ship(0, commandPost));
        }

        int shipsCargoAtStart = calculateCargo(ships);
        int docksCargoAtStart = calculateCargo(dockQueue);
        int totalCargoAtStart = shipsCargoAtStart + docksCargoAtStart;

        Logger.log(new StringBuilder("Total cargo in system is ").append(totalCargoAtStart));

        for (Ship ship :
                ships) {
            ship.start();
        }
        for (Ship ship :
                ships) {
            try {
                ship.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        int shipsCargoAtTheEnd = calculateCargo(ships);
        int docksCargoAtTheEnd = calculateCargo(dockQueue);
        int totalCargoAtTheEnd = shipsCargoAtTheEnd + docksCargoAtTheEnd;

        Logger.log("Ratio of dock's/ship's cargo :");
        Logger.log(new StringBuilder("\t\t- it was at the start:\t ").append(docksCargoAtStart).append("/").append(shipsCargoAtStart).append(" (units)"));
        Logger.log(new StringBuilder("\t\t- it became at the end:\t ").append(docksCargoAtTheEnd).append("/").append(shipsCargoAtTheEnd).append(" (units)"));
        if (totalCargoAtStart == totalCargoAtTheEnd){
            Logger.log("Congratulations!");
        } else {
            Logger.log("Attention!");
        }
        Logger.log(new StringBuilder().append(totalCargoAtTheEnd).append(" out of ").append(totalCargoAtStart).append(" units of cargo delivered to the destination."));
    }

    private static int calculateCargo(Queue<Dock> dockQueue) {
        int cargo = 0;
        for (Dock dock :
                dockQueue) {
            cargo += dock.getCargo();
        }
        return cargo;
    }

    private static int calculateCargo(ArrayList<Ship> ships) {
        int cargo = 0;
        for (Ship ship :
                ships) {
            cargo += ship.getCargo();
        }
        return cargo;
    }
}
