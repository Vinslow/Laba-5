package ru.rsreu.java5.logic;

import ru.rsreu.java5.entity.Port;
import ru.rsreu.java5.entity.Ship;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class CommandPort {
    private Semaphore semaphore;
    private ReentrantLock lock = new ReentrantLock();
    private Port port;

    public CommandPort(Semaphore semaphore, Port port){
        this.semaphore = semaphore;
        this.port = port;
    }

    public void takeShip(Ship ship) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            System.out.println("acquire failed");
            Thread.currentThread().interrupt();
        }
        lock.lock();
        int j = 0;
        boolean b = false;
        while (!b&&j<port.getDocks().length){
            if (!port.getDocks()[j].isBusy().get()){
                port.getDocks()[j].setBusy(true);
                ship.setDockNumber(j);
                b = true;
            }
            j++;
        }
        System.out.println("Ship "+Thread.currentThread().getName()+" get dock"+ship.getDockNumber());
        lock.unlock();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("taking ship failed");
        }
    }

    public void leaveShip(Ship ship) {
        lock.lock();
        if (ship.getCargo().get() > (Port.getStorageCapacity()/2)) {// если такой большой корабль, то не будем разгружать,
            // а то места на складе не хватит или товаров.
            System.out.println("Ship " + Thread.currentThread().getName() + " leave dock with nothing " + ship.getDockNumber());
            port.getDocks()[ship.getDockNumber().get()].setBusy(false);
            lock.unlock();
            semaphore.release();
        } else {
            if (ship.getIsFull().get()) {
                if ((Port.getStorageCapacity() - port.getStorage().get()) >= ship.getCargo().get()) {
                    port.setStorage(port.getStorage().get() + ship.getCargo().get());
                    ship.getCargo().set(0);
                    System.out.println("Ship " + Thread.currentThread().getName() + " leave dock" + ship.getDockNumber());
                    port.getDocks()[ship.getDockNumber().get()].setBusy(false);
                    lock.unlock();
                    semaphore.release();
                } else {
                    System.out.println("waiting remove item " + Thread.currentThread().getName());
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("waiting remove error with "+Thread.currentThread().getName());
                    }
                    lock.unlock();
                    leaveShip(ship);
                }
            } else {
                if (port.getStorage().get() >= ship.getCargo().get()) {
                    port.setStorage(port.getStorage().get() - ship.getCargo().get());
                    ship.getCargo().set(0);
                    System.out.println("Ship " + Thread.currentThread().getName() + " leave dock" + ship.getDockNumber());
                    port.getDocks()[ship.getDockNumber().get()].setBusy(false);
                    lock.unlock();
                    semaphore.release();
                } else {
                    System.out.println("waiting get item "+Thread.currentThread().getName());
                    lock.unlock();
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("waiting get error with "+Thread.currentThread().getName());
                    }
                    leaveShip(ship);
                }
            }
        }
    }
}
