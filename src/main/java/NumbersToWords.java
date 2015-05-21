import java.util.Arrays;
import java.util.List;

/**
 * Class to convert a number as digits to it's word form. ie. 123 == one hundred and twenty three
 *
 */
public class NumbersToWords {

	private class Scale {

		private final long factor;
		private final String word;

		public Scale(final long factor, final String word) {
			super();
			this.factor = factor;
			this.word = word;
		}

		public long getFactor() {
			return factor;
		}

		public String getWord() {
			return word;
		}

	}

	// These words and scales can be defined in constructor or some configuration for multi language support
	private final String minus = "minus";
	private final String point = "point";
	private final String and = "and";
	private final String sep = " ";
	private final String zero = "zero";
	private final String hundred = "hundred";
	private final String[] units = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
		"fifteen", "sixteen", "seventeen", "eightteen", "nineteen", "twenty"};
	private final String[] tens = {"twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};

	// TODO Handle negative scales ie. 1/1000
	private final List<Scale> scales = Arrays.asList(new Scale(3, "thousand"), new Scale(6, "million"), new Scale(9, "billion"), new Scale(12, "trillion"));

	public String toWord(final double n) {
		final long wholeNumber = (long) n;
		final StringBuilder sb = new StringBuilder();
		buildWord(sb, wholeNumber);
		final double decimals = n - wholeNumber;
		if (decimals > 0) {
			sb.append(sep);
			sb.append(point);
			buildDecimals(sb, wholeNumber, n);
		}
		return sb.toString();
	}

	/**
	 * Decimals are just the digits listed in order
	 */
	private void buildDecimals(final StringBuilder sb, final long wholeNumber, final double n) {
		// Use substring here to get around double point precision errors
		final int wholeNumberSize = new Long(wholeNumber).toString().length();
		final String decimalStr = new Double(n).toString().substring(wholeNumberSize + 1);
		for (int i = 0; i < decimalStr.length(); i++) {
			sb.append(sep);
			sb.append(getUnit(Integer.parseInt(decimalStr.substring(i, i + 1))));
		}
	}

	public String toWord(final int n) {
		return toWord((long) n);
	}

	public String toWord(final long n) {
		final StringBuilder sb = new StringBuilder();
		buildWord(sb, n);
		return sb.toString();
	}

	private void buildWord(final StringBuilder sb, final long n) {
		if (n == 0) {
			// Zero
			sb.append(zero);
		} else if (n < 0) {
			// Negative
			sb.append(minus);
			sb.append(sep);
			sb.append(toWord(-n));
		} else if (n / 20 == 0) {
			// Teens
			sb.append(getUnit(n));
		} else if (n / 100 == 0) {
			// Other Tens
			sb.append(getTens(n / 10));
			sb.append(sep);
			sb.append(getUnit(n % 10));
		} else if (n / 1000 == 0) {
			// Hundreds
			sb.append(toWord(n / 100));
			sb.append(sep);
			sb.append(hundred);
			final long remainder = n % 100;
			if (remainder != 0) {
				sb.append(sep);
				// TOOD Need to handle 1023 case where the and needs to be after thousand
				sb.append(and);
				sb.append(sep);
				sb.append(toWord(remainder));
			}
		} else {
			// Other scales generalised into factors of 3
			for (final Scale scale : scales) {
				if (n / (long) Math.pow(10, scale.getFactor() + 3) == 0) {
					buildWord(sb, n, scale);
					break;
				}
			}
		}
	}

	private void buildWord(final StringBuilder sb, final Long n, final Scale scale) {
		final long scaleFactor = (long) Math.pow(10, scale.getFactor());
		sb.append(toWord(n / scaleFactor));
		sb.append(sep);
		sb.append(scale.getWord());
		final long remainder = n % scaleFactor;
		if (remainder != 0) {
			sb.append(sep);
			sb.append(toWord(remainder));
		}
	}

	private String getUnit(final long i) {
		return units[(int) (i - 1)];
	}

	private String getTens(final long i) {
		return tens[(int) (i - 2)];
	}
}
