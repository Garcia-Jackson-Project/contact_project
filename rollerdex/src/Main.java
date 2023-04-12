import util.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    Input input = new Input();
    Boolean runProgram = true;
    HashMap<String, String> contacts = new HashMap<>();

    /*
    -------------------------------
    Primary program functionality section
    -------------------------------
     */
    public static void main (String[] args){
        Main rollerDex = new Main();
        rollerDex.setupFile();
        rollerDex.parseSavedContacts();

        do {
            int userInput = rollerDex.createCLI();
            rollerDex.runCLI(userInput);
        } while(rollerDex.runProgram);

        System.out.println("Have a great day!");
    }

    public int createCLI(){
        System.out.println("Welcome");

        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");

        System.out.println("Enter an option (1, 2, 3, 4 or 5):");

        return input.getInt(1,5);
    }

    public void runCLI(int input){
        switch (input){
            case 1 -> viewContacts();
            case 2 -> addContacts() ;
            case 3 -> searchContacts();
            case 4 -> deleteContacts();
            case 5 -> exit();
            default ->
                System.out.println("Improper input");
        }
    }

    /*
    -------------------------------
    CLI primary functions section
    -------------------------------
     */
    public void viewContacts (){
        printTableHeader();
        for (String contact: contacts.keySet()){
            printFormattedContact(contact,contacts.get(contact));
        }
        printDashes();
        System.out.println("\n");
    }

    public void addContacts(){
        createHeading("ADD NEW CONTACT");

        String userInputName = input.getString("Enter new contact name:");
        String userInputPhone = input.getString("Enter new contact phone number (numbers only)");

        if(contacts.containsKey(userInputName)){
            if(shouldOverwriteContact()){
                contacts.put(userInputName,userInputPhone);
            } else {
                System.out.println("Contact not added");
            }
        } else {
            contacts.put(userInputName,userInputPhone);
        }
    }

    public void searchContacts(){
        createHeading("SEARCH FOR CONTACT");

        String searchInput = input.getString("Enter the name you are searching for");
        boolean foundMatch = false;

        printTableHeader();

        for (String contact: contacts.keySet()){
            if (contact.contains(searchInput)){
                foundMatch = true;
                printFormattedContact(contact,contacts.get(contact));
            }
        }

        if(!foundMatch){
            System.out.println("Please check spelling or that\nperson does not exist");
        }
        printDashes();
        System.out.println("\n");
    }

    public void deleteContacts(){
        createHeading("DELETE A CONTACT");
        String userInput = input.getString("Enter the full name you want to delete:");
        try {
            if(contacts.containsKey(userInput)){
                contacts.remove(userInput);
            }
            else{
                System.out.println("That name doesn't match, please check spelling");
            }

        }catch(InputMismatchException e){
            throw new RuntimeException(e);
        }
    }

    public void exit(){
        createHeading("EXIT PROGRAM");

        if(input.yesOrNo("Save Changes? y or n")){
            saveChanges();
            System.out.println("Saving changes...");
        }

        runProgram = false;
    }

    /*
    -------------------------------
    File import and export section
    -------------------------------
     */
    public Path getDataFile(){
        String directory = "rollerdex/src/data";
        String filename = "contacts.txt";
        return Paths.get(directory, filename);
    }

    public Path getDataDirectory(){
        String directory = "rollerdex/src/data";
        return Paths.get(directory);
    }

    public void saveChanges(){
        List<String> newContactList = formatNewContacts();
        try{
            Files.write(getDataFile(),newContactList);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void setupFile(){
        List<String> contactList = Arrays.asList("Paul Garcia|7025500156", "Gage Jackson|5053335678");
        Path dataDirectory = getDataDirectory();
        Path dataFile = getDataFile();

        if(!Files.exists(dataFile)){
            try {
                Files.createDirectories(dataDirectory);
                Files.createFile(dataFile);
                Files.write(dataFile,contactList);
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    public List<String> getSavedContacts(){
        List<String> contactList = new ArrayList<>();
        try{
            contactList = Files.readAllLines(getDataFile());
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        return contactList;
    }

    public void parseSavedContacts(){
        List<String> savedContactsList = getSavedContacts();
        for(String savedContact: savedContactsList){
            String [] splitter = savedContact.split("\\|");
            contacts.put(splitter[0],splitter[1]);
        }
    }

    public List<String> formatNewContacts(){
        List<String> newContactsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String contact = key  + "|" + value;
                newContactsList.add(contact);
        }
        return newContactsList ;
    }

    /*
    -------------------------------
    Displays CONTACTS table section
    -------------------------------
     */
    public void printTableHeader(){
        printDashes();
        printFormattedContact("Name","Phone Number");
        printDashes();
    }

    public void printFormattedContact(String contactName, String contactPhone){
        int longestName = findLongestContactName();
        int longestPhone = 12;

        String formattedContact = "| ";
        formattedContact += String.format("%-" + longestName + "." + longestName + "s", contactName);
        formattedContact += " | ";
        formattedContact += String.format("%-" + longestPhone + "." + longestPhone + "s", formatPhoneNumber(contactPhone));
        formattedContact += " | ";

        System.out.println(formattedContact);
    }

    public void printDashes(){
        int longestName = findLongestContactName();
        int longestPhone = 12;
        int numOfDashes = longestName + longestPhone + 7;
        String dashes = "";

        for(int i = 0; i < numOfDashes; i++ ){
           dashes += "-";
        }
        System.out.println(dashes);
    }


    /*
    -------------------------------
    Simple Utility functions section
    -------------------------------
     */
    public String formatPhoneNumber(String phoneNumber){
        StringBuilder sb = new StringBuilder(phoneNumber);
        if (phoneNumber.length() == 10 ) {
            sb.insert(3, "-").insert(7, "-");
        }

        if (phoneNumber.length() == 7){
            sb.insert(3, "-");
        }
        return sb.toString();
    }

    public int findLongestContactName(){
        int nameLength = 0;

        for (String contact: contacts.keySet()){
            if(contact.length()>nameLength){
                nameLength = contact.length();
            }
        }
        return nameLength;
    }

    public void createHeading(String heading){
        String dashes = "";
        int numOfDashes = heading.length();
        for(int i = 0; i < numOfDashes; i++ ){
            dashes += "-";
        }
        System.out.println(dashes);
        System.out.println(heading);
        System.out.println(dashes);
    }

    public boolean shouldOverwriteContact(){
        return input.yesOrNo("There is a person with that name already in\nyour contacts already, would you like to\noverwrite them? (y or n)");
    }
}
