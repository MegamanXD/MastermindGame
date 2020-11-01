import java.util.HashSet;

public class Number{
	//Properties
	private final byte[] num;

	//Constructor
	public Number(byte[] secretNum) {
		this.num = secretNum.clone();
	}

	//Compare the guess with the secretNum and return Hits & Strikes
	public HitsAndStrikes compare(Number secretNum) {
		//Add all digits of the number to a HashSet
		HashSet<Integer> set = new HashSet<>(4);
		set.add(secretNum.get(0));
		set.add(secretNum.get(1));
		set.add(secretNum.get(2));
		set.add(secretNum.get(3));

		int strikes = 0, hits = 0;
		for (int i = 0; i < 4; i++) {
			//If the value and the position are both the same, it's a strike
			if (this.num[i] == secretNum.get(i))
					strikes++;
			//Otherwise, if the value exists in the set, it's a hit
			else if (set.contains((int) this.num[i]))
				hits++;
		}
		return new HitsAndStrikes(strikes, hits);
	}

	//Create a set of all possible 4-digit numbers, from 0000 to 9999
	public static HashSet<Number> createAllPotentialNums() {
		final HashSet<Number> result = new HashSet<>(10000);
		_createAllPotentialNums(result,4,new byte[4]);
		return result;
	}

	private static void _createAllPotentialNums(HashSet<Number> numbers, int length, byte[] temp) {
		if (length == 0) {
			numbers.add(new Number(temp));
		} else {
			for (byte digit : new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}) {
				temp[length-1] = digit;
				_createAllPotentialNums(numbers, length - 1, temp);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Number)) return false;
		Number number = (Number) o;

		for (int i = 0; i < 4; i++) {
			if (num[i] != number.num[i])
				return false;
		}
		return true;
	}

	//Override Hash function to minimize memory usage
	@Override
	public int hashCode() {
		return num[3] + num[2] * 10 + num[1] * 100 + num[0] * 1000;
	}

	//Get a particular digit using its index
	public int get(int i) {
		return this.num[i];
	}

	//toString
	@Override
	public String toString() {
		return "["+ num[0] + ", " + num[1] + ", " + num[2] + ", " + num[3] + "]";
	}
}