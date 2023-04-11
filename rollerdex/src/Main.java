import util.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    Input input = new Input();
    Boolean keepGoing = true;
    HashMap<String, String> contacts = new HashMap<>();

    public static void main (String[] args){
        Main interact = new Main();
        interact.createContacts();
        interact.parseSavedData();

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
        createDashes();
        contactName("Name", "Phone Number");
        createDashes();
        for (String contact: contacts.keySet()){

            if (contact.contains(search)){

                foundMatch = true;

                contactName(contact,contacts.get(contact));
            }

        }
        if(!foundMatch){
            System.out.println("Please check spelling or that \nperson does not exist");
        }
        createDashes();
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
        System.out.println("Save Changes?");
        saveChanges();
        keepGoing = false;

    }

    public void saveChanges(){
        List<String> newContactList = reverseParse();
        try{
            Files.write(getDataFile(),newContactList);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void createContacts (){
//        contacts.put("Paul Garcia", "7025500156");
//        contacts.put("Gage Jackson", "5053335678");
//        contacts.put("John Voyt", "5122355567");
//        contacts.put("Jim Vahn", "5122355568");
        List<String> contactList = Arrays.asList("Paul Garcia|7025500156", "Gage Jackson|5053335678");

        Path dataDirectory = getDataDirectory();
        Path dataFile = getDataFile();
        if(Files.notExists(dataDirectory)){
            try {
                Files.createDirectories(dataDirectory);
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }
        }
        if(!Files.exists(dataFile)){
            try {
                Files.createFile(dataFile);
                Files.write(dataFile,contactList);
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }
        }

    }
    public List<String> savedContacts(){
        List<String> aList = new ArrayList<>();
        try{
            aList = Files.readAllLines(getDataFile());
            System.out.println(aList);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        return aList;
    }

    public void parseSavedData(){
        List<String> savedContactsList = savedContacts();
        for(String savedContact: savedContactsList){
            String [] splitter = savedContact.split("\\|");
            contacts.put(splitter[0],splitter[1]);
        }

    }
    public List<String> reverseParse(){
        List<String> newContactsList = new ArrayList<>();
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                String contact = key  + "|" + value;
                newContactsList.add(contact);
        }
        return newContactsList ;
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

        createDashes();
        contactName("Name","Phone Number");
        createDashes();
        for (String contact: contacts.keySet()){
            contactName(contact,contacts.get(contact));

        }
        createDashes();
    }
    public void contactName( String contactName, String contactPhone){
        int longestName = longestName();
        int longestPhone = 12;
        String formattedContact = "| ";
        formattedContact += String.format("%-" + longestName + "." + longestName + "s", contactName);
        formattedContact += " | ";
        formattedContact += String.format("%-" + longestPhone + "." + longestPhone + "s", formatPhone(contactPhone));
        formattedContact += " | ";

        System.out.println(formattedContact);
    }


    public void createDashes(){
        int longestName = longestName();
        int longestPhone = 12;
        int numOfDashes = longestName + longestPhone + 7;
        String dashes = "";
        for(int i = 0; i < numOfDashes; i++ ){
           dashes += "-";

        }
        System.out.println(dashes);
    }
    public String formatPhone(String phoneNumber){

        StringBuilder sb = new StringBuilder(phoneNumber);
        if (phoneNumber.length() == 10 ) {

            sb.insert(3, "-").insert(7, "-");

        }
        if (phoneNumber.length() == 7){

            sb.insert(3, "-");
        }
        return sb.toString();
    }

    public Path getDataFile(){
        String directory = "data";
        String filename = "contacts.txt";
        return Paths.get(directory, filename);
    }
    public Path getDataDirectory(){
        String directory = "data";

        return Paths.get(directory);
    }
}
