package application.Models;

public class CarLoansModels {

    public enum VehicleType { CAR, TRUCK, VAN }
    public enum VehicleAge { NEW, USED }
    public enum PaymentFrequency { WEEKLY, BI_WEEKLY, MONTHLY }

    private double calculateMonthlyPayment(double principal, double interestRate, int months) 
    {
        interestRate /= 12.0; 
        return (principal * interestRate) / (1 - Math.pow(1 + interestRate, -months));
    }

    public double computePayment(double price, double downPayment, double interestRate, int months, PaymentFrequency freq) 
    {
        double principal = price - downPayment;
        double monthlyPayment = calculateMonthlyPayment(principal, interestRate, months);

        switch (freq) 
        {
            case WEEKLY: return (monthlyPayment * 12) / 52;
            case BI_WEEKLY: return (monthlyPayment * 12) / 26;
            default: return monthlyPayment;
        }
    }
}
