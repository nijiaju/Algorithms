public class Percolation {
    private final int N;
    private final int dummySiteTop;
    private final int dummySiteBot;
    private WeightedQuickUnionUF uf1;
    private WeightedQuickUnionUF uf2;
    private boolean[] opened;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be greater than zero");
        this.N = N;
        dummySiteTop = 0;
        dummySiteBot = N * N + 1;

        uf1 = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 2);
        opened = new boolean[N * N + 2];
    }

    public void open(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("col index j out of bounds");
        int currentSiteID = (i - 1) * N + j;

        opened[currentSiteID] = true;
        if (j % N != 1 && opened[currentSiteID - 1]) {
            uf1.union(currentSiteID - 1, currentSiteID);
            uf2.union(currentSiteID - 1, currentSiteID);
        }
        if (j % N != 0 && opened[currentSiteID + 1]) {
            uf1.union(currentSiteID + 1, currentSiteID);
            uf2.union(currentSiteID + 1, currentSiteID);
        }
        if (i != N && opened[currentSiteID + N]) {
            uf1.union(currentSiteID + N, currentSiteID);
            uf2.union(currentSiteID + N, currentSiteID);
        }
        if (i != 1 && opened[currentSiteID - N]) {
            uf1.union(currentSiteID - N, currentSiteID);
            uf2.union(currentSiteID - N, currentSiteID);
        }
        if (i == 1) {
            uf1.union(currentSiteID, dummySiteTop);
            uf2.union(currentSiteID, dummySiteTop);
        }
        if (i == N)
            uf1.union(currentSiteID, dummySiteBot);
    }

    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("col index j out of bounds");
        int currentSiteID = (i - 1) * N + j;
        return opened[currentSiteID];
    }

    public boolean isFull(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("col index j out of bounds");
        int currentSiteID = (i - 1) * N + j;
        return uf2.connected(currentSiteID, dummySiteTop);
    }

    public boolean percolates() {
        return uf1.connected(dummySiteTop, dummySiteBot);
    }

    public static void main(String[] args) {
 //       Percolation test = new Percolation(Integer.parseInt(args[0]));
    }
}