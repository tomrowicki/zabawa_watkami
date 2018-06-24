package multi;

import org.springframework.stereotype.Component;

@Component
public class AreaCalculator {

	public int calculateRectangleArea(Integer a, Integer b) {
		Integer c = a * b;
		return c;
	}
}
