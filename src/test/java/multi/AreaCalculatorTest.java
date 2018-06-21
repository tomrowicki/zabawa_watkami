package multi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaCalculatorTest {

	// korzysta ze springowego singletona
	@Autowired
	private AreaCalculator areaCalculator;
	
	@Test
	public void shouldProperlyCalculateArea() {
		// 1000 powtórzeń
		for (int i=0; i<1000; i++) {
			test();
		}
	}

	private void test() {
		Runnable[] threadPool = new Runnable[8];
		// dla procesora 8-wątkowego
		for (int i=0; i<8; i++) {
			Runnable t;
			// różne argumenty dla parzystych/nieparzystych wątków
			if (i % 2 == 0) {
				t = () -> {
				System.out.println(6 == areaCalculator.calculateRectangleArea(2, 3));	
				};
				
			} else {
				t = () -> {
				System.out.println(12 == areaCalculator.calculateRectangleArea(3, 4));
				};
			}
			threadPool[i] = t;
		}
		// odpalenie wszystkich wątków
		for (Runnable thread : threadPool) {
			thread.run();
		}
	}
}
