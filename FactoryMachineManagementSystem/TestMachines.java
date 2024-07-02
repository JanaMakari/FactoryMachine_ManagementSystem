
package javaapplication15;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMachines {

    public static boolean isValidPassword(String password) {

        if (password.trim().length() != 6) {
            return false;
        }

        String uppercasePattern = ".*[A-Z].*";
        String lowercasePattern = ".*[a-z].*";
        String numberPattern = ".*[0-9].*";
        String specialCharPattern = ".*[!@#$%^&*()-_+=].*";
        String repeatedCharPattern = ".*(.)\\1\\1.*"; 

        Pattern uppercase = Pattern.compile(uppercasePattern);
        Pattern lowercase = Pattern.compile(lowercasePattern);
        Pattern number = Pattern.compile(numberPattern);
        Pattern specialChar = Pattern.compile(specialCharPattern);
        Pattern repeatedChar = Pattern.compile(repeatedCharPattern);

        Matcher hasUppercase = uppercase.matcher(password);
        Matcher hasLowercase = lowercase.matcher(password);
        Matcher hasNumber = number.matcher(password);
        Matcher hasSpecialChar = specialChar.matcher(password);
        Matcher hasRepeatedChar = repeatedChar.matcher(password);
        
        return hasUppercase.matches() &&
               hasLowercase.matches() &&
               hasNumber.matches() &&
               hasSpecialChar.matches() &&
               !hasRepeatedChar.matches();
    }
    
    public static void addMachine(MachineManager manager)
    {
       Scanner input = new Scanner(System.in);
       System.out.println("Fill the following fields : \nName : ");
       String brandName = input.next();
       System.out.println("Type : ");
       String type = input.next();
       Machine machine = new Machine(brandName,type);
       boolean validInput;
       do{
           System.out.print("Status of power (on/off) : ");
           String status = input.next();
           if (status.equalsIgnoreCase("on")) 
           {
                machine.switchOn();
                validInput = true;
            } 
           else if (status.equalsIgnoreCase("off"))
           {
                machine.switchOff();
                validInput = true;
            } 
           else 
           {
                System.out.println("Invalid input. Please enter on or off.");
                validInput = false;
            }
       }while(!validInput);
       if(manager.addMachine(machine))
       {
           System.out.println("Machine added successfully.");
       }
       else 
       {
            System.out.println("Failed to add machine.");
       }
    }
    public static void removeMachine(MachineManager manager)
    {
       Scanner input = new Scanner(System.in);
       boolean retry;
       do{
           try
            {
                System.out.println("Enter Machine ID : ");
                String idvalue = input.next();
                boolean  removed = manager.removeMachineByid(idvalue);
                if(removed)
                {
                    System.out.println("Deletion successful");
                }
                retry = false;
            }
            catch(IllegalArgumentException e)
            {
                System.out.println(e.getMessage()+"\nTry again.");
                retry = true;
            }
       }while(retry);
    }
    
    public static void displayDetailsByCategory(MachineManager manager)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Change status of Machine by (id | brandname | type): ");
        String category = input.next();
        System.out.println("Enter the value: ");
        String categoryValue = input.next();
        if (category.equalsIgnoreCase("id"))
        {
            try
            {
                Machine machine = manager.getMachineById(categoryValue);
                System.out.println(machine.toString());
            }
           catch(Exception e)
            {
               System.out.println(e.getMessage());
            }
        }
        else 
        {
            ArrayList<Machine> machines;
            try
            {
                machines = manager.getMachinesByCategory(category, categoryValue);
            }
           catch(Exception e)
            {
               System.out.println(e.getMessage());
               return;
            }
            if (machines.isEmpty()) 
            {
                System.out.println("No machines found with the given value.");
                return;
            }
            for (Machine m : machines)
            {
                System.out.println(m.toString());
            }
        }
    }
    public static void changeStatus(MachineManager manager) 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Change status of Machine by (id | brandname | type): ");
        String category = input.next();
        System.out.println("Enter the value: ");
        String categoryValue = input.next();
        ArrayList<Machine> machines = new ArrayList<>();
        Machine machine;
        if (category.equalsIgnoreCase("id"))
        {
            try
            {
                machine = manager.getMachineById(categoryValue);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
                return;
            }
            if (machine != null)
            {
                machines.add(machine);
            } 
            else 
            {
                System.out.println("No machine found with the given ID.");
            }
        } 
        else 
        {
            machines = manager.getMachinesByCategory(category, categoryValue);
            if (machines.isEmpty()) 
            {
                System.out.println("No machines found with the given category value.");
            }
        }
        if (!machines.isEmpty())
        {
            System.out.println("Enter the status value (on/off): ");
            String statusValue = input.next();
            if (statusValue.equalsIgnoreCase("on") || statusValue.equalsIgnoreCase("off")) 
            {
                boolean isOn = statusValue.equalsIgnoreCase("on");
                for (Machine m : machines)
                {
                    if (isOn) 
                    {
                        m.switchOn();
                    }
                    else 
                    {
                        m.switchOff();
                    }
                }
                System.out.println("Status changed successfully.");
            }
            else 
            {
                System.out.println("Invalid status value. Please enter 'on' or 'off'.");
            }
        }
    }
    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       System.out.println("Factory Machine Management System");
       boolean isvalid;
       String password = "";
       do{
           System.out.println("Enter the password : ");
           password = input.next();
           isvalid = isValidPassword(password);
           if(!isvalid)
           {
               System.out.println("""
                                  Invalid Password.
                                  The Constraints : 
                                  1-Password must be at least 6 characters long.
                                  2-Password must contain at least one uppercase letter (A-Z).
                                  3-Password must contain at least one lowercase letter (a-z).
                                  4-Password must contain at least one digit (0-9).
                                  5-Password must contain at least one special character from a specific set (e.g., !@#$%^&*()-_+=).
                                  6- Password should not have consecutive repeated characters (e.g., aaa).""");
               isvalid = false;
           }
       }while(!isvalid);
       MachineManager manager = new MachineManager(password);
       boolean again = true;
       do
       {
       System.out.println("Factory Machine Management System");
       System.out.println("Menu : ");
       System.out.println("1-Display details of all machines");
       System.out.println("2-Add machine");
       System.out.println("3-Delete machine");
       System.out.println("4-Display details of a machine");
       System.out.println("5-Change status of machine");
       System.out.println("6-Exit");
       System.out.println("Choose an option : ");
       boolean retry = false;
       do{
            String option = input.next();
            switch(option) 
            {
                case "1":
                    System.out.println(manager.toString());
                    retry = false;
                    break;
                case "2":
                    addMachine(manager);
                    retry = false;
                    break;
                case "3":
                    removeMachine(manager);
                    retry = false;
                    break;
                case "4":
                    displayDetailsByCategory(manager);
                    retry = false;
                    break;
                case "5":   
                    changeStatus(manager);
                    retry = false;
                    break;
                case "6":
                    retry = false;
                    again = false;
                    break;
                default:
                    System.out.println("Invalid Option. Try again.");
                    again = true;
                    break;
            }
        }while(retry);
       }while(again);
      
    }
}
