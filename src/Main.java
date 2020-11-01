public class Main {
	public static void main(String[] args) {
		/* CodeMaker program */
		CodeMaker codeMaker = CodeMaker.getInstance();
		codeMaker.run();

		/* CodeBreaker program */
		CodeBreaker codeBreaker = CodeBreaker.getInstance();
		codeBreaker.run();

		/* Simulation program */
//		Number[] firstGuesses = new Number[8];
//		firstGuesses[0] = new Number(new byte[]{1, 8, 2, 3});		//Group 1
//		firstGuesses[1] = new Number(new byte[]{0, 0, 1, 2});		//Group 2
//		firstGuesses[2] = new Number(new byte[]{0, 0, 8, 5});		//Group 3
//		firstGuesses[3] = new Number(new byte[]{5, 5, 6, 7});		//Group 4
//		firstGuesses[4] = new Number(new byte[]{1, 1, 2, 3});		//Group 5 (us)
//		firstGuesses[5] = new Number(new byte[]{9, 8, 7, 9});		//Group 6
//		firstGuesses[6] = new Number(new byte[]{2, 2, 3, 4});		//Group 7
//		firstGuesses[7] = new Number(new byte[]{1, 2, 3, 4});		//Group 8
//
//		byte groupNumber = 1;
//		Number firstGuess = firstGuesses[groupNumber-1];
//
//		Simulation simulation = new Simulation(firstGuess);
//		simulation.run();
	}
}