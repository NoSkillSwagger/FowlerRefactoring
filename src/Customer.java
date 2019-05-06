import java.util.*;

class Customer {
    private String name;
    private ArrayList<Rental> rentals = new ArrayList<Rental>();
    public Customer (String newname){
        name = newname;
    }
    public void addRental(Rental arg) {
        rentals.add(arg);
    }
    public String getName (){
        return name;
    }
    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration enumRentals = Collections.enumeration(rentals);	    
        String result = "Rental Record for " + this.getName() + "\n";
        result += "\tTitle\t\tDays\tAmount\n";

        while (enumRentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) enumRentals.nextElement();
            //determine amounts for each line
            thisAmount = amountFor(each);
            // add frequent renter points
            frequentRenterPoints ++;
            // add bonus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) && each.getDaysRented() > 1) 
                frequentRenterPoints ++;
            //show figures for this rental
            result += String.format("\t%s\t\t%d\t%s\n", each.getMovie().getTitle(), each.getDaysRented(), thisAmount);
            totalAmount += thisAmount;
        }
        //add footer lines
        result += String.format("Amount owed is %s\n", totalAmount);
        result += String.format("You earned %d frequent renter points", frequentRenterPoints);
        return result;
    }

    private double amountFor(Rental each) {
        double thisAmount = 0;
        switch (each.getMovie().getPriceCode()) {
            case Movie.REGULAR:
                thisAmount += 2;
                if (each.getDaysRented() > 2)
                    thisAmount += (each.getDaysRented() - 2) * 1.5;
                break;
            case Movie.NEW_RELEASE:
                thisAmount += each.getDaysRented() * 3;
                break;
            case Movie.CHILDRENS:
                thisAmount += 1.5;
                if (each.getDaysRented() > 3)
                    thisAmount += (each.getDaysRented() - 3) * 1.5;
                break;
        }
        return thisAmount;
    }

}
    