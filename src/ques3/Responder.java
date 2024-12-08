package ques3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Responder implements Runnable {
    private static boolean aligned = false;
    private static String instruction = "";

    private final Main droneWars;

    public Responder(Main droneWars) {
        this.droneWars = droneWars;
    }

    public static synchronized void setAligned(boolean value) {
        aligned = value;
    }

    public static synchronized void setInstruction(String instr) {
        instruction = instr;
    }

    @Override
    public void run() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:/Users/KunalGoswami/Downloads/Test1_JAVA/src/ques3/recvrs.mxt"))) {
            while (droneWars.isRunning() || instruction != null) {
                synchronized (droneWars) {
                    try {
                        droneWars.wait();  // Wait for notification from BaseStation
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                synchronized (droneWars) {
                    if (instruction != null) {
                        if (aligned) {
                            String ack = encodeAcknowledgement(instruction);
                            bw.write(ack);
                            bw.newLine();
                            bw.flush();  // Ensure data is written immediately
                            System.out.println("Acknowledgement written: " + ack);
                        } else {
                            bw.write("Not aligned");
                            bw.newLine();
                            bw.flush();  // Ensure data is written immediately
                            System.out.println("No alignment for instruction: " + instruction);
                        }
                        instruction = null;  // Reset instruction after processing
                    }
                }

                Thread.sleep(100);  // Delay to prevent busy-waiting
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Encode the acknowledgement (simulate with a simple transformation for now)
    private String encodeAcknowledgement(String instruction) {
        StringBuilder ack = new StringBuilder();
        for (char c : instruction.toCharArray()) {
            if (c == '>') ack.append('<');
            else if (c == '<') ack.append('>');
            else if (c == '/') ack.append('.');
            else if (c == '.') ack.append('/');
        }
        return ack.toString();
    }
}
