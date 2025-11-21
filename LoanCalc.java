// Computes the periodical payment necessary to pay a given loan.
public class LoanCalc {

	static double epsilon = 0.001; // Approximation accuracy
	static int iterationCounter; // Number of iterations

	// Gets the loan data and computes the periodical payment.
	// Expects to get three command-line arguments: loan amount (double),
	// interest rate (double, as a percentage), and number of payments (int).
	public static void main(String[] args) {
		// Gets the loan data
		double loan = Double.parseDouble(args[0]);
		double rate = Double.parseDouble(args[1]);
		int n = Integer.parseInt(args[2]);
		System.out.println("Loan = " + loan + ", interest rate = " + rate + "%, periods = " + n);

		// Computes the periodical payment using brute force search
		System.out.print("\nPeriodical payment, using brute force: ");
		System.out.println((int) bruteForceSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);

		// Computes the periodical payment using bisection search
		System.out.print("\nPeriodical payment, using bi-section search: ");
		System.out.println((int) bisectionSolver(loan, rate, n, epsilon));
		System.out.println("number of iterations: " + iterationCounter);
	}

	// Computes the ending balance of a loan, given the loan amount, the periodical
	// interest rate (as a percentage), the number of periods (n), and the
	// periodical payment.
	private static double endBalance(double loan, double rate, int n, double payment) {
		// Replace the following statement with your code
		double realRate = rate / 100;
		double balance = loan;
		for (int i = 0; i < n; i++) {
			balance = (balance - payment) * (1 + realRate);
		}
		return balance;
	}

	// Uses sequential search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
	public static double bruteForceSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0;
		double g = loan / n;
		// As long as the ending balance is positive → still owe money → payment too low
		while (endBalance(loan, rate, n, g) > 0) {
			g = g + epsilon;
			iterationCounter++;
		}
		return g;
	}

	// Uses bisection search to compute an approximation of the periodical payment
	// that will bring the ending balance of a loan close to 0.
	// Given: the sum of the loan, the periodical interest rate (as a percentage),
	// the number of periods (n), and epsilon, the approximation's accuracy
	// Side effect: modifies the class variable iterationCounter.
	public static double bisectionSolver(double loan, double rate, int n, double epsilon) {
		iterationCounter = 0;
		double L = loan / n; // L is a low payment guess: f(L) > 0 (balance positive → payment too low)
		double H = L * 2; // H is a high payment guess: must satisfy f(H) < 0 (balance negative → payment
							// too high)
		iterationCounter++;
		while (endBalance(loan, rate, n, H) > 0) {
			H = H * 2;
			iterationCounter++;
		}
		iterationCounter++;
		double g = (L + H) / 2; // Initial guess is midpoint between L and H
		iterationCounter++;
		iterationCounter++;
		while ((H - L) > epsilon) {
			iterationCounter++;
			// Compute f(L) and f(g)
			double fL = endBalance(loan, rate, n, L);
			double fG = endBalance(loan, rate, n, g);
			// If f(L) and f(g) have the same sign, the root is between g and H → update L
			if (fL * fG > 0) {
				L = g;
			}
			// Otherwise, the root is between L and g → update H
			else {
				H = g;
			}
			// Recalculate midpoint
			g = (L + H) / 2; // New guess is midpoint between L and H
		}
		return g;
	}
}