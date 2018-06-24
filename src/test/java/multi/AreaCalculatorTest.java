package multi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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

	private Random randomizer = new Random(1234);

	@Test
	public void shouldProperlyCalculateArea() {
		// 1000 powtórzeń
		for (int i = 0; i < 1000; i++) {
			calculateAreaInParallel(true);
		}
	}
	
	// tutaj gwałcimy założenia thread-safe, więc asercja może nie pyknąć
	@Test (expected = AssertionError.class)
	public void shouldMessUpCalculatingArea() {
		for (int i = 0; i < 1000; i++) {
			calculateAreaInParallel(false);
		}
	}

	private void calculateAreaInParallel(boolean doItProperly) {
		List<Runnable> threadPool = new ArrayList<Runnable>();
		// dla procesora 8-wątkowego
		for (int i = 0; i < 8; i++) {
			// wyliczamy uprzednio poprawny wynik, przed inicjalizacją wątków
			int arg1 = randomizer.nextInt(8) + 1;
			int arg2 = randomizer.nextInt(8) + 1;
			Integer area = arg1 * arg2;
			
			Runnable t;
			t = () -> {
				// różne wątki wykonują się z różną prędkością
				try {
					TimeUnit.MILLISECONDS.sleep(randomizer.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (doItProperly) {
					assertEquals(area, areaCalculator.calculateRectangleArea(arg1, arg2));
				} else {
					assertEquals(area, areaCalculator.wronglyCalculateRectangleAreaInParallel(arg1, arg2));
				}
			};
			threadPool.add(t);
		}
		// odpalenie wszystkich wątków równolegle
		threadPool.parallelStream().forEach(Runnable::run);
	}
}
