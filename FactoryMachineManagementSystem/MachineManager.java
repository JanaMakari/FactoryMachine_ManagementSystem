
package javaapplication15;

import java.util.ArrayList;

public class MachineManager {
    private String password;
    private ArrayList<Machine> machines;

    public MachineManager() {
        this.password = "";
        this.machines = new ArrayList<>();
    }
    
    public MachineManager(String password) {
        this.password = password;
        this.machines = new ArrayList<>();
    }
    
    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    
    public boolean checkpassword(String password)
    {
        if(password == null)
        {
            return false;
        }
        return password.equals(this.password);
    }
    
    public ArrayList<Machine> getMachines() {
        return machines;
    }
    public boolean addMachine(Machine machine)
    {
        return this.machines.add(machine);
    }
    public boolean removeMachineByid(String id)
    {
        if(!Machine.isValidId(id))
        {
            throw new IllegalArgumentException("Invalid ID");
        }
        if(this.machines == null || this.machines.isEmpty())
        {
            System.out.println("There is No Machine to remove, The List is empty");
            return false;
        }
        for(int i = 0; i < this.machines.size(); i++)
        {
            if(this.machines.get(i).getId().equals(id))
                {
                    machines.remove(i);
                    return true;
                }
        }
        System.out.println("Machine ID not found");
        return false;
    }
    
    public Machine getMachineById(String id)
    {
        if(!Machine.isValidId(id))
        {
            throw new IllegalArgumentException("Invalid ID");
        }
        
        if(this.machines == null || this.machines.isEmpty())
        {
            throw new IllegalArgumentException("There is No Machine to retrieve, The List is empty");
        }
        
        for(Machine machine : this.machines)
        {
            if(machine.getId().equals(id))
            {
                return machine;
            }
        }
        
        throw new IllegalArgumentException("Machine with ID " + id + " not found");
    }
    
    @Override
    public String toString() 
    {
        StringBuilder s = new StringBuilder("List of Machines that are in the Factory:\n");
        if (this.machines == null || this.machines.isEmpty()) 
        {
            s.append("No machines found. The list is empty.\n");
        } 
        else 
        {
            for (Machine machine : this.machines) 
            {
                s.append(machine.toString());
            }
        }
        return s.toString();
    }
    public ArrayList<Machine> getMachinesByCategory(String category, String value) 
    {
        if (this.machines == null || this.machines.isEmpty()) 
        {
            return new ArrayList<>();
        }
        ArrayList<Machine> machinesByCategory = new ArrayList<>();
        for (Machine machine : machines) 
        {
            switch (category.toLowerCase()) 
            {
                case "name","brandname":
                    if (machine.getBrandName().equalsIgnoreCase(value)) 
                    {
                        machinesByCategory.add(machine);
                    }
                    break;
                case "type":
                    if (machine.getType().equalsIgnoreCase(value)) 
                    {
                        machinesByCategory.add(machine);
                    }
                    break;
                case "statuson":
                    if (machine.isPower()) 
                    {
                        machinesByCategory.add(machine);
                    }
                    break;
                case "statusoff":
                    if (!machine.isPower()) 
                    {
                        machinesByCategory.add(machine);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid category");
            }
        }
        return machinesByCategory;
    }
    
    public void switchStatus(String category, String value, boolean statusValue) 
    {
        if (category == null || value == null) 
        {
            throw new IllegalArgumentException("Category and value cannot be null");
        }

        if (this.machines == null || this.machines.isEmpty()) 
        {
            throw new IllegalStateException("The machine list is empty");
        }
        for (Machine machine : machines) 
        {
            boolean matchesCategory = false;
            switch (category.toLowerCase()) 
            {
                case "id":
                    if(Machine.isValidId(value))
                    {
                        matchesCategory = machine.getId().equalsIgnoreCase(value);
                    }
                    break;
                case "name":
                    matchesCategory = machine.getBrandName().equalsIgnoreCase(value);
                    break;
                case "type":
                    matchesCategory = machine.getType().equalsIgnoreCase(value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid category: " + category);
            }
            if (matchesCategory) 
            {
                if (statusValue) 
                {
                    machine.switchOn();
                } 
                else 
                {
                    machine.switchOff();
                }
            }
        }
    }
}
