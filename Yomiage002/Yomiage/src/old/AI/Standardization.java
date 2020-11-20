package old.AI;


/**
 * 
 * Ｐｙｔｈｏｎで機械学習はツクリマショウ！
 * 
 */
public class Standardization {
	
	private int n;
	
	public Standardization(int n) {
		this.n = n;
	}
	
	// f(x) = w_0 + w_1 * x
	public double f_w(double x_std, double w_0, double w_1) {
		return w_0 + w_1 * x_std;
	}
	
	public double E(double x_std, double y, double w_0, double w_1) {
		return 0.5 * Math.pow(this.sum(this.n, y - this.f_w(x_std, w_0, w_1)), 2);
	}
	
	public double sum(int n, double value) {
		return (n > 0)? this.sum(n - 1, value) : value;
	}
}
