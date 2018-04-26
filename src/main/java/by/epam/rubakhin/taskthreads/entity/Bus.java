package by.epam.rubakhin.taskthreads.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class Bus extends Thread {
    private long busId;
    private static long idCounter;
    private Semaphore semaphore;
    private Lock lock;
    private List<Passenger> busPassengers;
    private Route busRoute;

    private final static Logger LOG = LogManager.getLogger("Bus");

    public Bus(Route busRoute, List busPassengers, Semaphore semaphore, Lock lock) {
        this.semaphore = semaphore;
        this.busRoute = busRoute;
        this.busPassengers = busPassengers;
        this.lock = lock;
        busId = createID();
    }

    private void startTrip() {
        List<BusStop> busStops = busRoute.getBusStopsList();
        System.out.println("Bus " + busId + " have " + getBusPassengers());

        for (BusStop busStop : busStops) {
            busStop.checkInBusStop(this);
            lock.lock();

            System.out.println("resource locked by bus " + this);
            busStop.makeBusWaitingDoRandomAction(this);
            for (Passenger passenger : busPassengers) {
                passenger.makePassengersDoRandomAction(busStop, passenger, this);
            }
            busStop.addPassengersChangingBus(this);

            lock.unlock();
            System.out.println("resource unlocked by bus " + this);
            busStop.checkOutBusStop(this);
        }

    }

    @Override
    public void run() {
        try {
            System.out.println(this + " waiting semaphore permission.");
            semaphore.acquire();
            System.out.println(this + " gets semaphore permission.");
            startTrip();
            System.out.println(this + " release semaphore permission.");
            semaphore.release();
        } catch (InterruptedException e) {
            LOG.warn("InterruptedException " + e);
        }
    }

    private static long createID() {
        return idCounter++;
    }

    public long getBusId() {
        return busId;
    }

    public List<Passenger> getBusPassengers() {
        return busPassengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bus bus = (Bus) o;

        if (busId != bus.busId) return false;
        if (semaphore != null ? !semaphore.equals(bus.semaphore) : bus.semaphore != null) return false;
        if (lock != null ? !lock.equals(bus.lock) : bus.lock != null) return false;
        if (busPassengers != null ? !busPassengers.equals(bus.busPassengers) : bus.busPassengers != null) return false;
        return busRoute == bus.busRoute;
    }

    @Override
    public int hashCode() {
        int result = (int) (busId ^ (busId >>> 32));
        result = 31 * result + (semaphore != null ? semaphore.hashCode() : 0);
        result = 31 * result + (lock != null ? lock.hashCode() : 0);
        result = 31 * result + (busPassengers != null ? busPassengers.hashCode() : 0);
        result = 31 * result + (busRoute != null ? busRoute.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                '}';
    }
}

