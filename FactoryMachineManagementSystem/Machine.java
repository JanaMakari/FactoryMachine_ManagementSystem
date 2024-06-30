/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Machine {
    private String id;
    static int count = 0;
    private String brandName;
    private String type;
    private boolean power;

    public Machine() {
        count++;
        this.id = "M#"+count;
        this.brandName = "";
        this.power = false;
        this.type = "";
    }
    public Machine(String brandName,String type) {
        count++;
        this.id = "M#"+count;
        setBrandName(brandName);
        this.power = false;
        setType(type);
    }

    public String getId() {
        return id;
    }
    
    public static boolean isValidId(String id) {
        if (id == null || id.trim().length() == 0) {
            return false;
        }
        if(!id.startsWith("M#"))
        {
            return false;
        }
        String numericPart = id.substring(2); 
        String numberPattern = "^[0-9]+$";
        Pattern number = Pattern.compile(numberPattern);
        Matcher hasNumber = number.matcher(numericPart);
    
        return hasNumber.matches();
    }

    public void changeId(String id) {
        if(isValidId(id))
        {
            this.id = id;
        }
        else
        {
            throw new IllegalArgumentException("Invalid ID");
        }
    }
    
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        if(brandName == null || brandName.trim().length()==0)
        {
           throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        this.brandName = capitalizeBrandName(brandName);
    }
    
    public String capitalizeBrandName(String brandName) {
        String magazinename = "";
        String[] names = brandName.split("\\s+");
        String[] partofname = new String[names.length];
        for(int i = 0; i < names.length; i++)
        {
            partofname[i] = names[i].substring(0, 1).toUpperCase();
            partofname[i] += names[i].substring(1);
        }
        magazinename = String.join(" ", partofname);
        return magazinename;
    }
    
    public boolean isPower() {
        return power;
    }

    public void switchOn()
    {
        if(!isPower())
        {
            this.power = true;
        }
    }
    
    public void switchOff()
    {
        if(isPower())
        {
            this.power = false;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type == null || type.trim().length() == 0)
        {
           throw new IllegalArgumentException("Type cannot be null or empty");
        }
        this.type = type.toUpperCase();
    }
    
    @Override
    public String toString() {
        String s = "Machine ID : "+this.id+" | BrandName : "+ this.getBrandName() + " | Type : "+this.type+" | Status : ";
        if(this.isPower())
        {
            s+="ON\n";
        }
        else
        {
            s+="OFF\n";
        }
        return s;
    }
    
}
