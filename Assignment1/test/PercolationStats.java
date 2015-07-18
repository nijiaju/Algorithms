public class PercolationStats {
    private double[] results;

    public PercolationStats(int N, int T) {
        results = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation exp = new Percolation(N);
            while(!exp.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);
                exp.open(row, col);
//                StdOut.println("Opened Site: " + row + " and " + col);
//                StdOut.println("Is Full:     " + exp.isFull(row, col));
            }
            results[i] = (double)exp.getNumOpenSites() / (N * N);
        }
    }

    public double mean() {
        double sum = 0;

        for(int i = 0; i < results.length; i++)
            sum += results[i];

        return sum / results.length;
    }

    public double stddev() {
        double mean = mean();
        double sum = 0;

        for(int i = 0; i < results.length; i++) {
            sum += Math.pow((results[i] - mean), 2);
        }

        return Math.sqrt(sum / (results.length - 1));
    }

    public double confidenceL0() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats simulation = new PercolationStats(N, T);
        StdOut.printf("mean                    = %f\n", simulation.mean());
        StdOut.printf("stddev                  = %f\n", simulation.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", simulation.confidenceL0(), simulation.confidenceHi());
        StdOut.println();
    }
}