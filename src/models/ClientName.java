package models;

import java.util.ArrayList;

public class ClientName {
    private int jobID;
    private String name;
    private double balance;
    static ArrayList<String> names = new ArrayList<>();

    public ClientName(int jobID, String name, double balance) {
        this.jobID = jobID;
        this.name = name;
        this.balance = balance;
    }

    public ClientName(int jobID, String name) {
        this.jobID = jobID;
        this.name = name;

        names.add(name);
    }

    public int getJobID() {
        return jobID;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public static boolean isDuplicate(String name) {

        int counter = 0;

        for (String s : names) {
            if (s.equals(name)) {
                counter++;
            }
        }

        if (counter > 1) {
            return true;
        }

        return false;
    }

    public static void clearNames() {
        names.clear();
    }
}
