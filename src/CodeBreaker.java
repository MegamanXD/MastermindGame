import java.util.InputMismatchException;
import java.util.Scanner;

public class CodeBreaker {
    //Singleton to save memory
    private static CodeBreaker instance = new CodeBreaker();
    private CodeBreaker(){}
    public static CodeBreaker getInstance(){return instance;}

    //Properties
    private Strategy strategy = Strategy.getInstance();
    private Number errorSignal = new Number(new byte[]{-1, -1, -1, -1});

    //Gather input for Hits/Strikes can check it is valid
    private int validateHitStrike(String input_type) {
        Scanner scanner = new Scanner(System.in);
        int input;
        while (true)
            try {
                System.out.print( input_type + " = ");
                input = scanner.nextInt();
                scanner.nextLine();
                //Hit and Strike must be from 0 to 4
                if (input <= 4 && input >= 0) {
                	scanner.close();
                    return input;
                }
                //If it is not in the correct range, display error message
                System.out.println(input_type + " must be <= 4 and >= 0 !!\n");
            } catch (InputMismatchException ime) {
                //If the input has invalid characters, display error message
                System.out.println("Invalid character found, Please enter numeric values only !!\n");
                scanner.nextLine();
            }
    }
    //Methods
    public void run() {
        //Initialize values
        int strikes, hits;
        int i = 2;

        //Make the first guess and display it to the screen
        Number guess = this.strategy.firstGuess();
        System.out.println("\nGuess 1: " + guess.toString());

        //Keep gathering the HitsAndStrikes for the previous guess, and deduce the next move accordingly
        while (true) {
            //Input number of Strikes
            strikes = validateHitStrike("Strikes");

            //If strikes = 4, the game is won. Display message and exit the loop
            if (strikes == 4){
                System.out.println("Yay! We did it :)");
                break;
            }

            //Otherwise, continue to gather number of Hits
            hits = validateHitStrike("Hits");

            //From the value of Hits & Strikes, make the next guess and display it to the screen
            HitsAndStrikes hitsAndStrikes = new HitsAndStrikes(strikes,hits);
            guess = this.strategy.knuthGuess(hitsAndStrikes);

            //If the next guess is the error signal, stop the program
            if (guess.equals(errorSignal))
                break;

            //Otherwise, print the next guess out
            System.out.println("\nGuess " + i++ + ": " + guess);
            }
        }



}
