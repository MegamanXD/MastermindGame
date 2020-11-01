import java.util.HashSet;

public class HitsAndStrikes {
	//Properties
	public final int strikes;
	public final int hits;

	//Constructor
	public HitsAndStrikes(int strikes, int hits) {
		this.strikes = strikes;
		this.hits = hits;
	}

	//Check if the values of Strikes & Hits are the exact same
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof HitsAndStrikes)) return false;
		HitsAndStrikes that = (HitsAndStrikes) o;
		return strikes == that.strikes && hits == that.hits;
	}

	//Override Hash function to minimize memory usage
	@Override
	public int hashCode() {
		return strikes * 5 + hits;
	}

	//Calculate all combinations of Strikes & Hits
	public static HashSet<HitsAndStrikes> createAllHitsAndStrikes() {
		HashSet<HitsAndStrikes> result = new HashSet<>(16);
		for (int strikes = 0; strikes < 4; strikes++) {
			for (int hits = 0; hits <= 4; hits++) {
				int sum = hits + strikes;
				if (sum <= 4) {
					result.add(new HitsAndStrikes(strikes, hits));
				}
			}
		}
		return result;
	}

	//toString
	@Override
	public String toString() {
		return "strikes: " + this.strikes + ", hits: " + this.hits;
	}
}
