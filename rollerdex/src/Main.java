import util.Input;

public class Main {

    Input input = new Input();
    public static void main (String[] args){
        Main interact = new Main();

        int userInput = interact.CLIcreate();

        interact.doStuff(userInput);
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
        System.out.println("View Contacts");
    }
    public void addContacts(){
        System.out.println("AddContacts");
    }
    public void searchContacts(){
        System.out.println("Search Contacts");
    }
    public void deleteContacts(){
        System.out.println("Delete Contacts");
    }
    public void exit(){
        System.out.println("Exit");
    }



}
