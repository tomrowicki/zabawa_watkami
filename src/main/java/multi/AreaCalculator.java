package multi;

import org.springframework.stereotype.Component;

@Component
public class AreaCalculator {

	public int calculateRectangleArea(int a, int b) {
		return a * b;
	}
}
