package lab06.asciiart;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BMPtoASCIITest {

	private BMPtoASCII bmp;
	@Before
	public void setUp() throws Exception {
		String filename = "src/lab06/asciiart/images/monalisa";
		bmp = new BMPtoASCII(filename);
	}
		
	@Test
	public void testReadFile() {
		bmp.readFile();
		assertEquals("Wrong width", 516, bmp.getWidth());
		assertEquals("Wrong height", 547, bmp.getHeight());
	}
		
	@Test 
	public void testGetIntensity() { 
		bmp.readFile();
		
		assertEquals("Wrong intensity", 69, bmp.getIntensity(0, 0));
		assertEquals("Wrong intensity", 72, bmp.getIntensity(0, 515));
		assertEquals("Wrong intensity", 85, bmp.getIntensity(1, 0));
		assertEquals("Wrong intensity", 82, bmp.getIntensity(1, 515));
		assertEquals("Wrong intensity", 107, bmp.getIntensity(546, 0));
		assertEquals("Wrong intensity", 106, bmp.getIntensity(546, 515)); 
	}
}