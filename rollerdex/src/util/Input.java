package util;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    private Scanner scanner;

    public Input() {
        scanner = new Scanner(System.in);
    }

    public String getString() {
        return scanner.nextLine();
    }

    public String getString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public boolean yesOrNo() {
        String input = scanner.nextLine();
        boolean isYes = input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");

        return isYes;
    }

    public boolean yesOrNo(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        boolean isYes = input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes");

        return isYes;
    }

    public int getInt(int min, int max) {
        int input = 0;

        do {
            try{
                input = scanner.nextInt();
            } catch(InputMismatchException e){
                System.out.println("That was not a valid input");
                scanner.nextLine();
                continue;
            }
        }while (input < min || input > max);
        scanner.nextLine(); // clear the input buffer
        return input;
    }

    public int getInt() {
        System.out.println("Please enter an integer: ");
        int input = 0;
        try {
            input = scanner.nextInt();
            scanner.nextLine(); // clear the input buffer

        } catch (NumberFormatException e) {
            System.out.println("That does not work dude.");
            scanner.nextLine(); // clear the input buffer

        }
        return input;
    }
}

