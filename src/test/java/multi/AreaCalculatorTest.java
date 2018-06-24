package multi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AreaCalculatorTest {

	// TODO executor
	// TODO losowanie argumentów, wyliczać wynik w teście i wpuszczać liczby do
	// komponentu, porównując z wynikiem
	// TODO sleep z losową ilością ms dla każdego wątku
	// TODO druga metoda, stanowa, pokazująca jak nie należy robić
	
	// korzysta ze springowego singletona
	@Autowired
	private AreaCalculator areaCalculator;

	@Test
	public void shouldProperlyCalculateArea() {
		// 1000 powtórzeń
		for (int i = 0; i < 1000; i++) {
			test();
		}
	}

	private void test() {
		Runnable[] threadPool = new Runnable[8];
		// dla procesora 8-wątkowego
		for (int i = 0; i < 8; i++) {
			Runnable t;
			// różne argumenty dla parzystych/nieparzystych wątków
			if (i % 2 == 0) {
				t = () -> {
					assertEquals(6, areaCalculator.calculateRectangleArea(2, 3));
				};

			} else {
				t = () -> {
					assertEquals(12, areaCalculator.calculateRectangleArea(3, 4));
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
