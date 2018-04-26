package by.epam.rubakhin.taskthreads.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BusStop {
    private long busStopId;
    private static long idCounter;
    private List<Passenger> passengersWishedToSitInBus = new ArrayList<>();
    private List<Passenger> passengersWishedChangeBus = new ArrayList<>();
    private List<Passenger> busStopPassengers;
    private List<Bus> buses;

    public BusStop(List<Passenger> busStopPassengers) {
        busStopId = createID();
        this.busStopPassengers = busStopPassengers;
        buses = new ArrayList<>();
    }

    public void makeBusWaitingDoRandomAction(Bus bus) {
        System.out.println("№ BusStop - " + busStopId);
        for (Passenger passenger : busStopPassengers) {
            passenger.makeBusWaitersDoSomething(this, passenger, bus);
        }
        addPassengersToBusFromBusStop(bus);
    }

    public void addPassengersToBusFromBusStop(Bus bus) {
        if (passengersWishedToSitInBus.size() != 0) {
            for (Passenger passenger : passengersWishedToSitInBus) {
                bus.getBusPassengers().add(passenger);
            }
            System.out.println("passengers joined to bus " + bus + passengersWishedToSitInBus);
            for (Passenger passenger : passengersWishedToSitInBus) {
                busStopPassengers.remove(passenger);
            }
            passengersWishedToSitInBus.clear();
        }
    }

    public void addPassengersChangingBus(Bus fromBus) {
        if (passengersWishedChangeBus.size() != 0) {
            Random random = new Random();
            int toBus;
            int fromBusInt = buses.indexOf(fromBus);

            do {
                toBus = random.nextInt(buses.size());
            } while (toBus == fromBusInt);

            for (Passenger passenger : passengersWishedChangeBus) {
                buses.get(toBus).getBusPassengers().add(passenger);
                System.out.println(passenger + " changed bus and sit in the bus "
                        + buses.get(toBus));
            }

            for (Passenger passenger : passengersWishedChangeBus) {
                fromBus.getBusPassengers().remove(passenger);
                System.out.println(passenger + " removed from the bus list " + fromBus);
            }

            passengersWishedChangeBus.clear();
        }
    }

    private static long createID() {
        return idCounter++;
    }

    public void checkInBusStop(Bus bus) {
        buses.add(bus);
    }

    public void checkOutBusStop(Bus bus) {
        buses.remove(bus);
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Passenger> getPassengersWishedToSitInBus() {
        return passengersWishedToSitInBus;
    }

    public List<Passenger> getPassengersWishedChangeBus() {
        return passengersWishedChangeBus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusStop busStop = (BusStop) o;

        if (busStopId != busStop.busStopId) return false;
        if (passengersWishedToSitInBus != null ? !passengersWishedToSitInBus
                .equals(busStop.passengersWishedToSitInBus) : busStop.passengersWishedToSitInBus != null)
            return false;
        if (busStopPassengers != null ? !busStopPassengers
                .equals(busStop.busStopPassengers) : busStop.busStopPassengers != null)
            return false;
        if (buses != null ? !buses.equals(busStop.buses) : busStop.buses != null) return false;
        return passengersWishedChangeBus != null ? passengersWishedChangeBus
                .equals(busStop.passengersWishedChangeBus) : busStop.passengersWishedChangeBus == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (busStopId ^ (busStopId >>> 32));
        result = 31 * result + (passengersWishedToSitInBus != null ? passengersWishedToSitInBus.hashCode() : 0);
        result = 31 * result + (busStopPassengers != null ? busStopPassengers.hashCode() : 0);
        result = 31 * result + (buses != null ? buses.hashCode() : 0);
        result = 31 * result + (passengersWishedChangeBus != null ? passengersWishedChangeBus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BusStop №" + busStopId;
    }
}
