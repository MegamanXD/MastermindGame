import java.util.*;

public class Strategy {
	//Properties
	final HashSet<Number> allNumbers;
	private final Number[] optimalFirstGuess;
	private final HashSet<HitsAndStrikes> allHitsAndStrikes;
	private HashSet<Number> potentialAnswers;
	private Number lastGuess;

	//Singleton to save memory
	private static Strategy instance = new Strategy();
	private Strategy() {
		this.allNumbers = Number.createAllPotentialNums();
		this.allHitsAndStrikes = HitsAndStrikes.createAllHitsAndStrikes();
		this.potentialAnswers = new HashSet<Number>();
		this.potentialAnswers.addAll(allNumbers);
		this.optimalFirstGuess = getFirstGuesses();
	}
	public static Strategy getInstance(){return instance;}

	//Calculate a list of 20 optimal first guess
	private Number[] getFirstGuesses() {
		Number[] firstGuesses = new Number[20];
		int index = 0;

		//For each potential solution
		for (Number number: this.potentialAnswers){
			int currentMax = 0;
			//Calculate the maximum remaining candidates
			for (HitsAndStrikes a : this.allHitsAndStrikes){
				int remainingGuesses = getPotentialSolutions(this.potentialAnswers, number, a).size();
				currentMax = Math.max(remainingGuesses, currentMax);
			}
			//If a number has the remaining candidates = 2401, add it to the array
			//The number 2401 is the smallest remaining candidates for first guess.
			//We got this number by running knuthGuess on allNumbers
			if (currentMax == 2401){
				if (index < 20)
					firstGuesses[index++] = number;
				else
					break;
			}
		}

		return firstGuesses;
	}



	//Pick a random first guess from the array of optimalFirstGuess
	public Number firstGuess() {
		this.lastGuess = this.optimalFirstGuess[new Random().nextInt(20)];
		return this.lastGuess;
	}

	public Number firstGuess(Number number) {
		this.potentialAnswers.clear();
		this.potentialAnswers.addAll(allNumbers);
		this.lastGuess = number;
		return this.lastGuess;
	}

	//Filter out numbers that don't produce the same HitsAndStrikes when compared with the guess
	//Those numbers cannot be the answer
	//The remaining are potential answers
	private void filterPotentialSolutions(HashSet<Number> numbers, Number lastGuess, HitsAndStrikes hitsAndStrikes) {
		Number number;
		for (Iterator<Number> potentialSolutions = numbers.iterator(); potentialSolutions.hasNext();){
			number = potentialSolutions.next();
			if (!lastGuess.compare(number).equals(hitsAndStrikes))
				potentialSolutions.remove();
		}
	}

	//Use Knuth's algorithm to make the next guess
	public Number knuthGuess(HitsAndStrikes hitsAndStrikes) {
		//Filter out numbers that cannot be the solution
		this.filterPotentialSolutions(this.potentialAnswers, this.lastGuess, hitsAndStrikes);

		//If the list of potentialAnswers is empty, that means the opponent inputted wrong Hits & Strikes somewhere.
		//So we display the error message and return the errorSignal of [-1,-1,-1,-1]
		if (this.potentialAnswers.isEmpty()){
			System.out.println("\nYour opponent gave the wrong Strikes & Hits somewhere. Go disqualify them :)");
			this.lastGuess = new Number(new byte[]{-1, -1, -1, -1});
			return this.lastGuess;
		}

		int minimumMax = Integer.MAX_VALUE;
		//For each potential solution
		for (Number number: this.potentialAnswers){
			int currentMax = 0;
			//Calculate the maximum remaining candidates
			for (HitsAndStrikes a : this.allHitsAndStrikes){
				int remainingGuesses = getPotentialSolutions(this.potentialAnswers, number, a).size();
				currentMax = Math.max(remainingGuesses, currentMax);
			}
			//If a number has smaller maximum remaining candidates than the global max, pick it
			//This is to minimize the remaining sample size
			if (currentMax < minimumMax){
				minimumMax = currentMax;
				this.lastGuess = number;
			}
		}

		return this.lastGuess;
	}

	//Also filter out potential candidates, but this one does not delete any number from the set
	protected HashSet<Number> getPotentialSolutions(HashSet<Number> numbers, Number lastGuess, HitsAndStrikes hitsAndStrikes) {
		HashSet<Number> result = new HashSet<Number>();
		for (Number number : numbers) {
			if (lastGuess.compare(number).equals(hitsAndStrikes)) {
				result.add(number);
			}
		}
		return result;
	}
}