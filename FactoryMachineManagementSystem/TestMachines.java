
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
       boolean again = false;
       do{
           System.out.print("Status of power (on/off) : ");
           String status = input.next();
           if(!status.equalsIgnoreCase("on") && !status.equalsIgnoreCase("off"))
            {
                System.out.println("Invalid input. Try again");
                again = true;
            }
            else if(status.equalsIgnoreCase("on"))
            {
                machine.switchOn();
                again = false;
            }
            else
            {
                machine.switchOff();
                again = false;
            }
       }while(again);
       boolean added = manager.getMachines().add(machine);
       if(!added)
       {
           System.out.println("Adding Failed");
       }
       System.out.println("Adding Successded");
    }
    public static void removeMachine(MachineManager manager)
    {
       Scanner input = new Scanner(System.in);
       boolean retry = false;
       do{
           try
            {
                System.out.println("Enter ID : ");
                String idvalue = input.next();
                boolean  removed = manager.removeMachineByid(idvalue);
                if(removed)
                {
                    System.out.println("Deleting Successuffly");
                }
                else
                {
                    System.out.println("Deleting Failed. ID not found");
                }
                retry = !removed;
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage()+"\nTry again.");
                retry = true;
            }
       }while(retry);
    }
    
    public static void displayMachinebyId(MachineManager manager)
    {
       System.out.println("Enter ID : ");
       Scanner input = new Scanner(System.in);
       try
        {
            String idvalue = input.next();
            Machine machine = manager.getMachineById(idvalue);
            System.out.println(machine.toString());
        }
       catch(Exception e)
        {
           System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) {
       Scanner input = new Scanner(System.in);
       System.out.println("Factory Machine Management System");
       boolean isvalid = true;
       String password = "";
       do{
           System.out.println("Enter the password : ");
           password = input.next();
           isvalid = isValidPassword(password);
           if(!isvalid)
           {
               System.out.println("Invalid Password.\nThe Constraints : \n1-Password must be at least 6 characters long.\n2-Password must contain at least one uppercase letter (A-Z)."
                       + "\n3-Password must contain at least one lowercase letter (a-z).\n4-Password must contain at least one digit (0-9)."
                       + "\n5-Password must contain at least one special character from a specific set (e.g., !@#$%^&*()-_+=)."
                       + "\n6- Password should not have consecutive repeated characters (e.g., aaa).");
               isvalid = false;
           }
       }while(!isvalid);
       MachineManager manager = new MachineManager(password);
       while(true)
       {
           System.out.println("Factory Machine Management System");
       System.out.println("Menu : ");
       System.out.println("1-Display details of all machines");
       System.out.println("2-Add machine");
       System.out.println("3-Delete machine");
       System.out.println("4-Display details of a machine");
       System.out.println("5-Exit");
       System.out.println("Choose an option : ");
       boolean again = true;
       do{
            String option = input.next();
            switch(option) 
            {
                case "1":
                    System.out.println(manager.toString());
                    break;
                case "2":
                    addMachine(manager);
                    break;
                case "3":
                    removeMachine(manager);
                    break;
                case "4":
                    displayMachinebyId(manager);
                    break;
                case "5":
                    System.exit(0);
                default:
                    System.out.println("Invalid Option. Try again.");
                    again = true;
                    break;
            }
       }while(again);
       }
      
    }
}
