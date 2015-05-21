import junit.framework.TestCase;

import org.junit.Test;

public class TestNumbersToWords extends TestCase {

	@Test
	public void testToWord() {
		assertEquals("minus eightteen", new NumbersToWords().toWord(-18L));
		assertEquals("eightteen", new NumbersToWords().toWord(18));
		assertEquals("thirty five", new NumbersToWords().toWord(35));
		assertEquals("one hundred and seventy eight", new NumbersToWords().toWord(178));
		assertEquals("three hundred", new NumbersToWords().toWord(300));
		assertEquals("one thousand two hundred and thirty four", new NumbersToWords().toWord(1234));
		assertEquals("one thousand two hundred and thirty four", new NumbersToWords().toWord(1234));
		assertEquals("eleven thousand two hundred and thirty four", new NumbersToWords().toWord(11234));
		assertEquals("two hundred and thirty one thousand two hundred and thirty four", new NumbersToWords().toWord(231234));
		assertEquals("seven million two hundred and thirty one thousand two hundred and thirty four", new NumbersToWords().toWord(7231234));
		assertEquals("one billion three hundred and twenty seven million two hundred and thirty one thousand two hundred and thirty four",
			new NumbersToWords().toWord(1327231234));
		assertEquals("two trillion one billion three hundred and twenty seven million two hundred and thirty one thousand two hundred and thirty four",
			new NumbersToWords().toWord(2001327231234L));
	}

	@Test
	public void testDecimals() {
		assertEquals("one point four five six", new NumbersToWords().toWord(1.456));
		assertEquals("one thousand two hundred and twenty three point four five six three three three", new NumbersToWords().toWord(1223.456333));
	}
}
