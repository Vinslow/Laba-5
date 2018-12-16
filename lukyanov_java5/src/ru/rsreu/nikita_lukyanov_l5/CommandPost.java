package ru.rsreu.nikita_lukyanov_l5;

import java.util.Queue;

class CommandPost {
    private Queue<Dock> dockQueue;

    CommandPost(Queue<Dock> dockQueue) {
        this.dockQueue = dockQueue;
    }

    synchronized void passShip(Ship ship){
        while (this.dockQueue.isEmpty()){
            try {
                wait();
                Logger.logOccupyImpossibility(ship);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        ship.setDock(this.dockQueue.poll());
        notifyAll();
    }

    synchronized void releaseDock(Dock dock){
        this.dockQueue.add(dock);
        notifyAll();
    }
}
