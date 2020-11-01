import java.util.HashSet;

public class Simulation {
    private Strategy strategy;
    private Number firstGuess;

    public Simulation(Number firstGuess) {
        this.strategy = Strategy.getInstance();
        this.firstGuess = firstGuess;
    }

    private byte simulateNumber(Number secretNum){
        byte guessCount = 1;
        HitsAndStrikes hitsAndStrikes = new HitsAndStrikes(0,0);
        Number errorSignal = new Number(new byte[]{-1, -1, -1, -1});
        Number guess = this.strategy.firstGuess(this.firstGuess);
        System.out.println("Guess 1: " + guess.toString());

        while (hitsAndStrikes.strikes != 4){
            hitsAndStrikes = guess.compare(secretNum);
            System.out.println(hitsAndStrikes);

            if (hitsAndStrikes.strikes == 4)
                break;

            guess = this.strategy.knuthGuess(hitsAndStrikes);
            System.out.println("Guess " + ++guessCount + ": " + guess.toString());

            if (guess.equals(errorSignal))
                break;
        }
        return guessCount;
    }

    public void run(){
        byte guessCount;
        HashSet<Number> allNums = this.strategy.allNumbers;
        HashSet<Number> worstCases = new HashSet<>();

        for (Number number : allNums) {
            System.out.println("\nSimulating number " + number + "\n------------------------------");
            guessCount = simulateNumber(number);
            if (guessCount > 9)
                worstCases.add(number);
        }

        System.out.println("\nWorst cases for " + this.firstGuess + " = " + worstCases);
    }
}
