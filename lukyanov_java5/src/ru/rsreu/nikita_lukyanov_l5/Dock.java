package ru.rsreu.nikita_lukyanov_l5;

class Dock {
    private int cargo;
    private Ship ship;

    Dock(int cargo) {
        this.cargo = cargo;
    }

    void dockShip(Ship ship){
        this.ship = ship;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        moveCargo();
        this.ship = null;
    }

    private void moveCargo(){
        if (this.ship.getCargo() > 0){
            this.unloadShip();
        } else {
            this.loadShip();
        }
    }

    private void unloadShip(){
        this.setCargo(this.cargo + this.ship.getCargo());
        this.ship.rmAllCargo();
        Logger.logCargo(this.ship);
    }

    private void loadShip(){
        this.ship.addCargo(this.cargo);
        this.setCargo(0);
        Logger.logCargo(ship);
    }

    int getCargo() {
        return cargo;
    }

    private void setCargo(int cargo) {
        this.cargo = cargo;
    }
}
