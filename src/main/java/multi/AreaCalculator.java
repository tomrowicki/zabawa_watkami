package multi;

import org.springframework.stereotype.Component;

@Component
public class AreaCalculator {

	private Integer dangerousField1;

	private Integer dangerousField2;

	private Integer dangerousField3;

	public Integer calculateRectangleArea(Integer a, Integer b) {
		Integer c = a * b;
		return c;
	}
	
	public Integer wronglyCalculateRectangleAreaInParallel(Integer a, Integer b) {
		this.dangerousField1 = a;
		this.dangerousField2 = b;
		this.dangerousField3 = this.dangerousField1 * this.dangerousField2;
		return this.dangerousField3;
	}
}
