package ru.rsreu.nikita_lukyanov_l5;

class Logger {
    private Logger() {
        throw new IllegalStateException("Utility class");
    }

    static void logCargo(Ship ship){
        System.out.println(new StringBuilder("Ship ").append(ship.getShipNumber()).append(" has ").append(ship.getCargo()).append(" cargo."));
    }

    static void logOccupyImpossibility(Ship ship){
        System.out.println(new StringBuilder("Ship ").append(ship.getShipNumber()).append(" can't occupy dock."));
    }

    static void log(String string){
        System.out.println(string);
    }

    static void log(StringBuilder stringBuilder) {
        System.out.println(stringBuilder);
    }
}
