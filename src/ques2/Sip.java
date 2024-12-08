package ques2;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

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

        // Number formatting for output
        NumberFormat formatter = NumberFormat.getInstance(new Locale("en", "IN"));
        formatter.setMaximumFractionDigits(0);  // Set to 0 for integer values
        formatter.setMinimumFractionDigits(0);

        //Final answer variables
        double depreciatedValue = principal;
        double appreciatedValue = principal;
        double lastSipValue = 0;
        double lastDepreciatedValue = principal;
        double lastAppreciatedValue = principal;

        System.out.println("-----------------------------");
        System.out.println("\nYearly Breakdown:");


        // Table header
        System.out.printf("%-6s %-20s %-25s %-25s %-20s%n", "Year", "Total EMI Paid (₹)", "Future Value of SIP (₹)", "Depreciated Value (₹)", "Appreciated Value (₹)");

        for (int year = 1; year <= tenure; year++) {
            // EMI paid until this year
            double totalEMIPaid = emi * year * 12;

            // Future value of SIP until this year
            sipValue = 0;
            for (int i = 0; i < year * 12; i++) {
                sipValue += emi * Math.pow(1 + monthlySIPRate, (year * 12) - i);
            }

            // Depreciated and appreciated values for this year
            depreciatedValue -= depreciatedValue * (depreciationRate / 100);
            appreciatedValue += appreciatedValue * (appreciationRate / 100);

            // Print row for this year with integer values
            System.out.printf("%-6d %-20s %-25s %-25s %-20s%n", year, formatter.format(totalEMIPaid), formatter.format(sipValue), formatter.format(depreciatedValue), formatter.format(appreciatedValue));

            // Store the last year's values for the final comparison
            lastSipValue = sipValue;
            lastDepreciatedValue = depreciatedValue;
            lastAppreciatedValue = appreciatedValue;
        }

        // Summary to determine which investment is better
        System.out.println("\nSummary of the Calculation:");
        if (lastDepreciatedValue > lastSipValue) {
            System.out.println("Purchasing the asset through a home loan is a better option.");
        } else if (lastAppreciatedValue > lastSipValue) {
            System.out.println("Purchasing the asset through a home loan is a better option due to appreciation.");
        } else {
            System.out.println("Investing through SIP is a better option.");
        }

        scanner.close();
    }

    // Method to parse input and handle commas using regular expressions
    // Input like 1,00,00 will be converted to 100000 for calculations
    public static double handlecomma(String input) {
        // Remove commas from the input string
        input = input.replaceAll(",", "");
        return Double.parseDouble(input);
    }
}
