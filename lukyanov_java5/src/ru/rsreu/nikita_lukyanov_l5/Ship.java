package ru.rsreu.nikita_lukyanov_l5;

import java.util.Random;

public class Ship extends Thread{

    private static int shipsCount;
    private int cargo;
    private int shipNumber;
    private CommandPost commandPost;
    private Dock dock;

    Ship(int cargo, CommandPost commandPost) {
        this.cargo = cargo;
        shipsCount++;
        this.shipNumber = shipsCount;
        this.commandPost = commandPost;
    }

    @Override
    public void run() {
        swim();
        this.commandPost.passShip(this);
        if (this.dock != null){
            this.dock.dockShip(this);
            this.commandPost.releaseDock(this.dock);
            this.dock = null;
        }
    }

    private void swim(){
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(((5000-1000)+1)+1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    void addCargo(int cargo){
        this.cargo += cargo;
    }

    private void rmCargo(int cargo){
        addCargo(-cargo);
    }

    void rmAllCargo(){
        rmCargo(this.cargo);
    }

    int getCargo() {
        return cargo;
    }

    int getShipNumber() {
        return shipNumber;
    }

    void setDock(Dock dock) {
        this.dock = dock;
    }
}
