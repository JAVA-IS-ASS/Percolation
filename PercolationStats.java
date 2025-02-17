public class PercolationStats {
    
    private double[] threshold;
    private int trials; // Add trials as a class member
    
    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid must be greater then zero. Number must be greater then zero");
        }
        this.trials = trials;  // Initialize trials

        threshold = new double[trials];
    
        for (int t = 0; t < trials; t++) {
            Percolation percolation = new Percolation(n); // Create a new Percolation object for each trial
            int sitesOpen = 0;

            // n x n grid blocked initially, simulate opening sites until percolation
            while (!percolation.percolates()) {
                int row = (int) (Math.random() * n) + 1;
                int col = (int) (Math.random() * n) + 1;

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    sitesOpen++;
                }
            }

            // Store the percolation threshold for this trial
            threshold[t] = (double) sitesOpen / (n * n);
        }
    }

    // Sample mean of percolation threshold
    public double mean() {
        double sumOfAllPercolation = 0;
        for (double t : threshold) {
            sumOfAllPercolation += t;
        }
        return sumOfAllPercolation / threshold.length;
    }

    // Sample standard deviation of percolation threshold
    public double stddev() {
        if (threshold.length == 1) {
            return Double.NaN; // Standard deviation is not defined for a single trial
        }

        double mean = mean();
        double sumSquaredDifferences = 0.0;

        for (double t : threshold) {
            sumSquaredDifferences += Math.pow(t - mean, 2);
        }

        return Math.sqrt(sumSquaredDifferences / (threshold.length - 1)); // Corrected here
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: java PercolationStats <grid size> <number of trials>");
        }
    
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
    
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size and number of trials must be greater than 0.");
        }
    
        PercolationStats stats = new PercolationStats(n, trials);
    
        System.out.printf("mean = %.16f\n", stats.mean());
        System.out.printf("stddev = %.16f\n", stats.stddev());
        System.out.printf("95%% confidence interval = [%.16f, %.16f]\n",
                          stats.confidenceLo(), stats.confidenceHi());
    }
}
