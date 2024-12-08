package ques2;

import java.text.NumberFormat;
import java.util.*;

public class Sip {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input details
        System.out.print("\nEnter the principal amount (in Rupees): ");
        double principal = handlecomma(scanner.next());

        System.out.print("Enter the loan tenure (years): ");
        int tenure = scanner.nextInt();

        System.out.print("Enter the annual interest rate for EMI (%): ");
        double annualInterestRate = scanner.nextDouble();

        System.out.print("Enter the annual rate of return for SIP (%): ");
        double sipRateOfReturn = scanner.nextDouble();

        System.out.print("Enter the annual depreciation rate of the asset (%): ");
        double depreciationRate = scanner.nextDouble();

        System.out.print("Enter the annual appreciation rate of the asset (%): ");
        double appreciationRate = scanner.nextDouble();

        // EMI Calculation
        double monthlyInterestRate = annualInterestRate / 12 / 100;
        int numberOfPayments = tenure * 12;
        double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfPayments))
                / (Math.pow(1 + monthlyInterestRate, numberOfPayments) - 1);

        // SIP Returns Calculation
        double monthlySIPRate = sipRateOfReturn / 12 / 100;
        double sipValue = 0;
        for (int i = 0; i < numberOfPayments; i++) {
            sipValue += emi * Math.pow(1 + monthlySIPRate, numberOfPayments - i);
        }

        // Depreciation and Appreciation Calculation
        double depreciatedValue = principal;
        double appreciatedValue = principal;
        for (int i = 0; i < tenure; i++) {
            depreciatedValue -= depreciatedValue * (depreciationRate / 100);
            appreciatedValue += appreciatedValue * (appreciationRate / 100);
        }

        // Summary
        double totalEMIPaid = emi * numberOfPayments;

        // Number formatting for output
        NumberFormat formatter = NumberFormat.getInstance(new Locale( "en"));


        System.out.println("-----------------------------");
        System.out.println("\nSummary of the Calculation:");
        System.out.printf("Total amount paid over %d years: ₹%s%n", tenure, formatter.format(totalEMIPaid));
        System.out.printf("Future value of SIP after %d years: ₹%s%n", tenure, formatter.format(sipValue));
        System.out.printf("Depreciated value of the asset after %d years: ₹%s%n", tenure, formatter.format(depreciatedValue));
        System.out.printf("Appreciated value of the asset after %d years: ₹%s%n", tenure, formatter.format(appreciatedValue));

        if (depreciatedValue > sipValue) {
            System.out.println("Purchasing the asset through a home loan is a better option.");
        } else if (appreciatedValue > sipValue) {
            System.out.println("Purchasing the asset through a home loan is a better option due to appreciation.");
        } else {
            System.out.println("Investing through SIP is a better option.");
        }

        scanner.close();
    }

    // Method to parse input and handle commas using regular expressions
    //input like 1,00,00 will be converted to 100000 for calculations
    public static double handlecomma(String input) {
        // Remove commas from the input string
        input = input.replaceAll(",", "");
        return Double.parseDouble(input);
    }
}
