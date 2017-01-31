package com.company;

import java.util.Scanner;
import java.lang.Math;

public class Main {

    /****************************************************************
     This is the method to get a year input from the user.
     *****************************************************************/
    public static int getUserInputYear() {
        /* Variable declarations */
        int userYear = 0;           // Year that user enters
        boolean yearValid = false;   // Validity flag for user-entered year
        Scanner scnr = new Scanner(System.in);

        /* Get the user input */
        System.out.print("Enter year of date (e.g. 2016): ");
        userYear = scnr.nextInt();

        //Don't accept negative dates
        if (userYear >= 0) {
            yearValid = true;
        }

        while (yearValid == false) {
            System.out.println("That is an invalid year.  Please try again.");
            System.out.print("Enter year of first date (e.g. 2016): ");
            userYear = scnr.nextInt();
            if (userYear >= 0) {
                yearValid = true;
            }
        }

        /* Pass back valid year value */
        return userYear;
    }

    /*********************************************************
     This is the method to get a month input from the user.
     **********************************************************/
    public static int getUserInputMonth() {
        /* Variable declarations */
        int userMonth = 0;           // month that user enters
        boolean monthValid = false;   // Validity flag for user-entered month
        Scanner scnr = new Scanner(System.in);

        // Get user input
        System.out.print("Enter month of first date in number format (e.g. May = 5): ");  // Possible improvement perhaps change to take in string input
        userMonth = scnr.nextInt();

        // Don't accept invalid month values
        if ((userMonth >= 1) && (userMonth <= 12)) {
            monthValid = true;
        }

        while (monthValid == false) {
            System.out.println("That is an invalid month.  Please try again.");
            System.out.print("Enter month of first date in number format (e.g. May = 5): ");
            userMonth = scnr.nextInt();
            if ((userMonth >= 1) && (userMonth <= 12)) {
                monthValid = true;
            }
        }

         /* Pass back valid year value */
        return userMonth;
    }


    /*****************************************************************
     This is the method to get a day input from the user.
     ******************************************************************/
    public static int getUserInputDay(int year, int month) {
        /* Declaration of variables */
        int userDay = 0;                            // user inputted day
        boolean dayValid = false;                   // validity flag of user-inputted day
        int[] daysPerMonth = {31, 28, 31, 30,       // array that holds number of days per month
                31, 30, 31, 31, 30, 31, 30, 31};
        Scanner scnr = new Scanner(System.in);


        System.out.print("Enter day of date in number format (e.g. 31): ");  // Possible improvement perhaps change to take in string input
        userDay = scnr.nextInt();

        // Don't accept invalid days

        // First check if leap year
        if ((month == 2) && (userDay == 29)) {
            if (year % 4 != 0) {
                dayValid = false;
            } else if (year % 100 != 0) {
                dayValid = true;
            } else if (year % 400 != 0) {
                dayValid = false;
            } else {
                dayValid = true;
            }
        }
        else if ((userDay > 0) && (userDay <= daysPerMonth[month - 1])) {
            dayValid = true;
        }
        else {
            dayValid = false;
        }

        // Don't accept invalid days, there has to be a "cleaner" way
        while (dayValid == false) {
            System.out.println("That is an invalid day for that month and year.  Please try again.");
            System.out.print("Enter day of date in number format (e.g. 31): ");
            userDay = scnr.nextInt();

            // Don't accept invalid days

            // First check if leap year
            if ((month == 2) && (userDay == 29)) {
                if (year % 4 != 0) {
                    dayValid = false;
                } else if (year % 100 != 0) {
                    dayValid = true;
                } else if (year % 400 != 0) {
                    dayValid = false;
                } else {
                    dayValid = true;
                }
            }
            else if((userDay > 0) && (userDay <= daysPerMonth[month - 1])) {
                dayValid = true;
            }
            else {
                dayValid = false;
            }
        }

        return userDay;
    }

    /*****************************************************************
     This is the method to calculate the time between two dates
     ******************************************************************/
    public static int[] getTimeDiff(int year1, int month1, int day1,
                                    int year2, int month2, int day2) {

        /* Declaration of variables */
        int[] timeYMD = new int[3];     // Array to hold the amount of time between two days, in years, month, days
        boolean date1Newer = false;   // Boolean to determine branch to calculate time difference
        int diffYears = 0;
        int diffMonths = 0;
        int diffDays = 0;
        boolean subtractYear = false;
        int[] daysPerMonth = {31, 28, 31, 30,       // array that holds number of days per month
                31, 30, 31, 31, 30, 31, 30, 31};

        /* Determine which date is earlier */

        /* First determine which date is later */
        if (year1 > year2) {
            date1Newer = true;
        }
        else if ((year1 == year2) && (month1 > month2)) {
            date1Newer = true;
        }
        else if ((year1 == year2) && (month1 == month2) && (day1 > day2)) {
            date1Newer = true;
        }

        /* Algorithm to calculate difference, notes that the two highest level branches are the
           same except the two dates flipped.  */
        if (date1Newer == true) {

            //calculate years
            if (month1 > month2) {
                diffYears = year1 - year2;
            }
            else if ((month1 == month2) && (day1 > day2)) {
                diffYears = year1 - year2;
            }
            else if (month1 < month2) {
                diffYears = year1 - year2 - 1; // account for when there is less than a year
            }
            else if ((month1 == month2) && (day1 < day2)) {
                diffYears = year1 - year2 - 1; // account for when there is less than a year
            }

            //calculate months
            if (month1 > month2) {
                diffMonths = month1 - month2;
            }
            else if (month1 < month2) {
                diffMonths = 12 - Math.abs(month1 - month2);
            }
            else if ((month1 == month2) && (day1 < day2)) {
                diffMonths = 11;
            }
            else {
                diffMonths = 0;
            }

            //calculate days
            if (day1 >= day2) {
                diffDays = day1 - day2;
            }
            else if (day1 < day2) {
                if (month2 == 2 && day2 == 29) {
                    diffDays = 29 - day2 + day1;
                    diffMonths -= 1;   // take away a month
                }
                else {
                    diffDays = daysPerMonth[month2 - 1] - day2 + day1;
                    if (month1 != month2) { // if statement here for special case when months are the same
                        diffMonths -= 1;
                    }
                }
            }
        }
        // case for if second date is newer, same algorithm
        else {

            //calculate years
            if (month2 > month1) {
                diffYears = year2 - year1;
            }
            else if ((month2 == month1) && (day2 > day1)) {
                diffYears = year2 - year1;
            }
            else if (month2 < month1) {
                diffYears = year2 - year1 - 1; // account for when there is less than a year
            }
            else if ((month2 == month1) && (day2 < day1)) {
                diffYears = year2 - year1 - 1; // account for when there is less than a year
            }

            //calculate months
            if (month2 > month1) {
                diffMonths = month2 - month1;
            }
            else if (month2 < month1) {
                diffMonths = 12 - Math.abs(month2 - month1);
            }
            else if ((month2 == month1) && (day2 < day1)) {
                diffMonths = 11;
            }
            else {
                diffMonths = 0;
            }

            //calculate days
            if (day2 >= day1) {
                diffDays = day2 - day1;
            }
            else if (day2 < day1) {
                if (month1 == 2 && day1 == 29) {
                    diffDays = 29 - day1 + day2;
                    diffMonths -= 1;   // take away a month
                }
                else {
                    diffDays = daysPerMonth[month1 - 1] - day1 + day2;
                    if (month2 != month1) { // if statement here for special case when months are the same
                        diffMonths -= 1;
                    }
                }
            }
        }

        // if the same month and year, simply calculate the year difference
        // this also takes care of the same date
        if ((month1 == month2) && (day1 == day2)) {
            diffYears = Math.abs(year1 - year2);
            diffMonths = 0;
            diffDays = 0;
        }

        // popoulate timeYMD array
        timeYMD[0] = diffYears;
        timeYMD[1] = diffMonths;
        timeYMD[2] = diffDays;

        return timeYMD;
    }

    /*********************************************
     This is the main program.
     *********************************************/
    public static void main(String[] args) {
	/* Declaration of variables */
        int userYear1 = 0;                      // user input for year for first date
        int userYear2 = 0;                      // user input for year for second date
        int userMonth1 = 0;                     // user input for month for first date
        int userMonth2 = 0;                     // user input for month for second date
        int userDay1 = 0;                       // user input for day for first date
        int userDay2 = 0;                       // user input for day for second date
        int[] diffTime = new int[3];            // Length of time between the two dates, in an array, by year, month, and days.
        boolean contProg = true;                // Boolean that keeps the program looping until user exits.
        Scanner scnr = new Scanner(System.in);  // Scanner input



        /* Beginning of program and description */
        System.out.println("This program will take two dates from the user as an input, \n" +
                "and calculate the number of years, months, and days between the dates.  ");

        while (contProg) {
            /* Get user inputs */
            // First date
            System.out.println("Enter the information of the first date");
            userYear1 = getUserInputYear();
            //System.out.println("Debugging, userYear1 = " + userYear1);
            userMonth1 = getUserInputMonth();
            //System.out.println("Debugging, userMonth1 = " + userMonth1);
            userDay1 = getUserInputDay(userYear1, userMonth1);
            //System.out.println("Debugging, userDay1 = " + userDay1);

            // Second date
            System.out.println("Enter the information of the second date");
            userYear2 = getUserInputYear();
            //System.out.println("Debugging, userYear2 = " + userYear2);
            userMonth2 = getUserInputMonth();
            //System.out.println("Debugging, userMonth2 = " + userMonth2);
            userDay2 = getUserInputDay(userYear2, userMonth2);
            //System.out.println("Debugging, userDay2 = " + userDay2);

            // Display the user inputs
            System.out.println("You entered two dates: ");
            System.out.println("    " + userMonth1 + "/" + userDay1 + "/" + userYear1);
            System.out.println("    " + userMonth2 + "/" + userDay2 + "/" + userYear2);

            /* Calculate time between dates */
            diffTime = getTimeDiff(userYear1, userMonth1, userDay1,
                    userYear2, userMonth2, userDay2);

            // Display the difference in time between the two dates
            System.out.println("The time between these dates are " +
                    diffTime[0] + " years, " +
                    diffTime[1] + " months, and " +
                    diffTime[2] + " days.");

            System.out.print("Do you want to continue? (y/n): ");
            if (scnr.next().trim().charAt(0) == 'n') {
                contProg = false;
            }
        }
    }
}
