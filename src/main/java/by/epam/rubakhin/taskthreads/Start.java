package by.epam.rubakhin.taskthreads;

import by.epam.rubakhin.taskthreads.initialize.Initializer;

public class Start {
    public static void main(String[] args) {

        Initializer init = new Initializer();
        init.initialize("data//datum.txt");
        init.startAllBuses();

    }
}
