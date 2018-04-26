package by.epam.rubakhin.taskthreads.entity;

import java.util.ArrayList;
import java.util.List;

public enum Route {
    GRODNO_VILNIUS(new ArrayList<BusStop>()){};

    List<BusStop> busStops = new ArrayList<>();

    Route(ArrayList<BusStop> busStops) { }

    public void addBusStop (BusStop busStop){
        busStops.add(busStop);
    }

    public List<BusStop> getBusStopsList() {
        return busStops;
    }
}
