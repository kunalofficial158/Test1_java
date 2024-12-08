package ques3;

public class Main {

    // Constants
    public static final double ARRACKIS_ORBIT_PERIOD = 12.0;  // LTU
    public static final double GIEDI_PRIME_ORBIT_PERIOD = 60.0;  // LTU
    public static final double ALIGNMENT_THRESHOLD = 10.0;  // Degrees

    private double time = 0;
    private boolean running = true;

    public static void main(String[] args) {
        Main droneWars = new Main();
        Thread baseStationThread = new Thread(new BaseStation(droneWars));
        Thread responderThread = new Thread(new Responder(droneWars));

        baseStationThread.start();
        responderThread.start();

        try {
            baseStationThread.join();
            droneWars.setRunning(false);
            synchronized (droneWars) {
                droneWars.notifyAll();  // Notify the responder thread to exit
            }
            responderThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Shared time incrementer
    public synchronized void incrementTime() {
        time++;
    }

    public synchronized double getTime() {
        return time;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    // Calculate the position of a planet given its orbital period
    public static double calculatePosition(double time, double orbitalPeriod) {
        double position = (time / orbitalPeriod) * 360.0;
        return position % 360.0;  // Ensure the position is between 0 and 360 degrees
    }

    // Check if the planets are aligned (angular separation <= 10 degrees)
    public static boolean checkAlignment(double arrakisPosition, double giediPrimePosition) {
        double separation = Math.abs(arrakisPosition - giediPrimePosition);
        if (separation > 180) {
            separation = 360 - separation;  // Shortest path across the circle
        }
        return separation <= ALIGNMENT_THRESHOLD;
    }
}
