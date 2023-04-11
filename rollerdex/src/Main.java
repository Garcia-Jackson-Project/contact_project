import util.Input;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Objects;

public class Main {

    Input input = new Input();
    Boolean keepGoing = true;
    HashMap<String, String> contacts = new HashMap<>();

    public static void main (String[] args){
        Main interact = new Main();
        interact.createContacts();
        do {
            int userInput = interact.CLIcreate();

            interact.doStuff(userInput);

        }while(interact.keepGoing);
    }

    public int  CLIcreate(){
        System.out.println("Welcome");
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.println("Enter an option (1, 2, 3, 4 or 5):");

        return input.getInt(1,5);
    }

    public void doStuff (int input){
        switch (input){
            case 1  -> viewContacts();
            case 2 -> addContacts() ;
            case 3 -> searchContacts();
            case 4 -> deleteContacts();
            case 5 -> exit();
            default ->
                System.out.println("Improper input");

        }
    }

    public void viewContacts(){

//        System.out.println("View Contacts");
//        System.out.println(contacts + "-");
        displayInfo();
    }
    public void addContacts(){
        System.out.println("AddContacts");
        System.out.println("Enter a name you'd like to add:");
        String usernameInput = input.getString();
        System.out.println("Enter the phone number for them, no dashes");
        String userPhone = input.getString();

        contacts.put(usernameInput,userPhone);
    }
    public void searchContacts(){

        System.out.println("Search Contacts");
        System.out.println("Enter the name you are searching for");

        String search = input.getString();
        boolean foundMatch = false;
        for (String contact: contacts.keySet()){

            if (contact.contains(search)){
                System.out.print(contact + " ");
                foundMatch = true;
                System.out.println(contacts.get(contact));
            }

        }
        if(!foundMatch){
            System.out.println("Please check spelling or that person does not exist");
        }

    }
    public void deleteContacts(){

        System.out.println("Enter the full name you want to delete:");
        String userInput = input.getString();
        try {

            if(contacts.containsKey(userInput)){
                contacts.remove(userInput);
            }
            else{
                System.out.println("That name doesn't match, please check spelling");
            }
        }catch(InputMismatchException e){

        }

    }
    public void exit(){
        System.out.println("Exiting...");
        keepGoing = false;
    }



    public void createContacts (){
        contacts.put("Paul Garcia", "7025500156");
        contacts.put("Gage Jackson", "5053335678");
        contacts.put("John Voyt", "5122355567");
        contacts.put("Jim Vahn", "5122355568");
    }

    public int longestName(){
        int nameLength = 0;
        for (String contact: contacts.keySet()){

            if(contact.length()>nameLength){
                nameLength = contact.length();
            }



        }
        return nameLength;

    }
    public void displayInfo (){
        int longestName = longestName();
        int longestPhone = 12;
        createDashes(longestName + longestPhone + 7);
        contactName(longestName, longestPhone,"Name","Phone Number");
        createDashes(longestName + longestPhone + 7);
        for (String contact: contacts.keySet()){
            contactName(longestName, longestPhone,contact,contacts.get(contact));

        }
        createDashes(longestName + longestPhone + 7);
    }
    public void contactName(int longestName, int longestPhone, String contactName, String contactPhone){

        String formattedContact = "| ";
        formattedContact += String.format("%-" + longestName + "." + longestName + "s", contactName);
        formattedContact += " | ";
        formattedContact += String.format("%-" + longestPhone + "." + longestPhone + "s", contactPhone);
        formattedContact += " | ";

        System.out.println(formattedContact);
    }


    public void createDashes(int numOfDashes){
        String dashes = "";
        for(int i = 0; i < numOfDashes; i++ ){
           dashes += "-";

        }
        System.out.println(dashes);
    }
}
