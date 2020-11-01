import java.util.Random;
import java.util.Scanner;

public class CodeMaker {
    //Singleton to save memory
    private static CodeMaker instance = new CodeMaker(){};
    private CodeMaker(){}
    public static CodeMaker getInstance(){return instance;}

    //Properties
    private Number secretNumber;

    //Create a random secret number
    private void generateSecretNum() {
        Random r = new Random();

        //Perfect random (usually result in 5 guesses, if Knuth is used)
//        byte[] num = {(byte) rnd.nextInt(10), (byte) rnd.nextInt(10), (byte) rnd.nextInt(10), (byte) rnd.nextInt(10)};

        //Selective random (usually result in 6-7 guesses, if Knuth is used)
        byte[] num = new byte[4];
        num[0] = (byte) r.nextInt(4);           //First digit random from 0 to 3
        num[1] = (byte) (r.nextInt(3) + 7);     //Second digit random from 7 to 9
        num[2] = (byte) (r.nextInt(3) + 4);     //Third digit random from 4 to 6
        num[3] = num[r.nextInt(3)];             //Last digit randomly repeat first/second/third digit

        //Shuffle all digits
        int randomIndexToSwap;
        byte temp;
        for (int i = 0; i < 4; i++) {
            randomIndexToSwap = r.nextInt(4);
            temp = num[randomIndexToSwap];
            num[randomIndexToSwap] = num[i];
            num[i] = temp;
        }

        //Return the secret num
        this.secretNumber = new Number(num);
    }

    //Run the CodeMaker program
    public void run(){
        //Create a secretNumber and display it to the screen
        generateSecretNum();
        System.out.println(this.secretNumber.toString());

        //Initialize the value of HitsAndStrikes
        HitsAndStrikes hitsAndStrikes = new HitsAndStrikes(0,0);

        //Collect the guess and return Hits & Strikes until 4 strikes are reached
        while (hitsAndStrikes.strikes != 4){
            //Receive the guess as a String
            Scanner scanner = new Scanner(System.in);
            String guessString;

            //Check for errors and display appropriate error messages
            while (true)
                try {
                    System.out.print("\nGuess (Eg: 1234): ");
                    guessString = scanner.nextLine();
                    int converted = Integer.parseInt(guessString);
                    //If the inputed number is larger than 9999, display error message
                    if (converted > 9999)
                        System.out.println("Your guess must be 4 digits !!");
                    //Else, if the inputed number is negative, display error message
                    else if (converted < 0)
                        System.out.println("Your guess must be positive number !!");
                    //Otherwise, it is correct. Exit the checking.
                    else
                    	scanner.close();
                        break;
                } catch (Exception e) {
                    //If the input has invalid characters, display error message
                    System.out.println("Invalid character found. Please enter numeric values only !!");
                }

            //Convert the guess string to a Number object
            byte[] num = new byte[4];
            num[0] = (byte) Integer.parseInt(guessString.substring(0,1));
            num[1] = (byte) Integer.parseInt(guessString.substring(1,2));
            num[2] = (byte) Integer.parseInt(guessString.substring(2,3));
            num[3] = (byte) Integer.parseInt(guessString.substring(3,4));
            Number guessNum = new Number(num);

            //Compare the number with the secret number and return the Hits & Strikes
            hitsAndStrikes = guessNum.compare(this.secretNumber);
            System.out.println(hitsAndStrikes.toString());
        }
    }
}
