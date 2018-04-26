package by.epam.rubakhin.taskthreads.entity;

import java.util.Random;

public class Passenger {
    private String name;

    public Passenger(String name) {
        this.name = name;
    }

    public void makePassengersDoRandomAction(BusStop busStop, Passenger passenger, Bus bus) {
        Random random = new Random();
        switch (random.nextInt(2)) {
            case 0:
                stayInBus();
                break;
            case 1:
                goToAnotherBus(busStop, passenger, bus);
                break;
        }
    }

    public void makeBusWaitersDoSomething(BusStop busStop, Passenger passenger, Bus bus) {
        Random random = new Random();
        switch (random.nextInt(2)) {
            case 0:
                stayInBusStop();
                break;
            case 1:
                sitInBus(busStop, passenger);
                break;
        }
    }

    private void sitInBus(BusStop busStop, Passenger passenger) {
        busStop.getPassengersWishedToSitInBus().add(passenger);
        System.out.println(passenger + " added to list of [passengersWishedToSitInBus]");
    }

    private void stayInBus() {
        System.out.println(name + " decided to stay in bus");
    }

    private void goToAnotherBus(BusStop busStop, Passenger passenger, Bus bus) {
        if (busStop.getBuses().size() <= 1) {
            System.out.println("there only 1 bus on the station");
            return;
        }
        busStop.getPassengersWishedChangeBus().add(passenger);
        System.out.println(passenger + " decided to change bus from " + bus +
                "and added to [PassengersWishedChangeBus]");
    }

    private void stayInBusStop() {
        System.out.println("Passenger " + name + " decided to stay in the bus Stop ");
    }

    @Override
    public String toString() {
        return name;
    }
}
