package model;

/**
 * A decorator class that makes ball comparable
 * @author kq2
 *
 */
public class CompareDecoratorBall implements Comparable<CompareDecoratorBall> {
	
	private Ball ball;
	private double value;
	
	public CompareDecoratorBall(Ball ball, double value) {
		this.ball = ball;
		this.value = value;
	}
	
	public Ball getBall() {
		return ball;
	}
	
	public double getVal() {
		return value;
	}

	@Override
	public int compareTo(CompareDecoratorBall o) {
		return (int) (o.getVal() - value);
	}

}
