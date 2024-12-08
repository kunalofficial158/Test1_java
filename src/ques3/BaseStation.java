package ques3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BaseStation implements Runnable {
    private final Main droneWars;

    public BaseStation(Main droneWars) {
        this.droneWars = droneWars;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/KunalGoswami/Downloads/Test1_JAVA/src/ques3/trans.mxt"))) {
            String line;
            while ((line = br.readLine()) != null && droneWars.isRunning()) {
                double arrakisPosition, giediPrimePosition;

                synchronized (droneWars) {
                    arrakisPosition = Main.calculatePosition(droneWars.getTime(), Main.ARRACKIS_ORBIT_PERIOD);
                    giediPrimePosition = Main.calculatePosition(droneWars.getTime(), Main.GIEDI_PRIME_ORBIT_PERIOD);

                    Responder.setInstruction(line); // Set instruction first

                    if (Main.checkAlignment(arrakisPosition, giediPrimePosition)) {
                        Responder.setAligned(true);
                        System.out.println("Aligned at time: " + droneWars.getTime() + " | Instruction: " + line);
                    } else {
                        Responder.setAligned(false);
                        System.out.println("No alignment for instruction: " + line);
                    }

                    droneWars.incrementTime();
                }

                // Notify the responder to process the current instruction
                synchronized (droneWars) {
                    droneWars.notifyAll();
                }

                Thread.sleep(200);  // Wait for the responder to process before moving to the next line
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            droneWars.setRunning(false);
            synchronized (droneWars) {
                droneWars.notifyAll();  // Ensure responder exits when done
            }
        }
    }
}
