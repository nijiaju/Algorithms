public class PercolationStats {
    private double[] results;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N and T must be greater than zero");
        results = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation exp = new Percolation(N);
            while (!exp.percolates()) {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);
                exp.open(row, col);
            }
            int counter = 0;
            for (int row = 1; row <= N; row++)
                for (int col = 1; col <= N; col++)
                    if (exp.isOpen(row, col))
                        counter++;
            results[i] = (double) counter / (N * N);
        }
    }

    public double mean() {
        double sum = 0;

        for (int i = 0; i < results.length; i++)
            sum += results[i];

        return sum / results.length;
    }

    public double stddev() {
        double mean = mean();
        double sum = 0;

        for (int i = 0; i < results.length; i++) {
            sum += (results[i] - mean) * (results[i] - mean);
        }

        return Math.sqrt(sum / (results.length - 1));
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats simulation = new PercolationStats(N, T);
        StdOut.printf("mean                    = %18.16f\n", simulation.mean());
        StdOut.printf("stddev                  = %18.16f\n", simulation.stddev());
        StdOut.printf("95%% confidence interval = %18.16f, %18.16f\n",
                      simulation.confidenceLo(), simulation.confidenceHi());
        StdOut.println();
    }
}