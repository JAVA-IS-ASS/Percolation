import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int virtualTop, virtualBottom;
    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }

        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);  // Extra 2 for virtual top and bottom
        virtualTop = n * n;
        virtualBottom = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new IllegalArgumentException("Invalid site");
        }
        
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites++;
            int index = (row - 1) * grid.length + (col - 1);

            // Connect to virtual top if in the first row
            if (row == 1) uf.union(index, virtualTop);

            // Connect to virtual bottom if in the last row
            if (row == grid.length) uf.union(index, virtualBottom);

            // Connect to neighbors if they are open
            if (row > 1 && isOpen(row - 1, col)) uf.union(index, (row - 2) * grid.length + (col - 1));
            if (row < grid.length && isOpen(row + 1, col)) uf.union(index, row * grid.length + (col - 1));
            if (col > 1 && isOpen(row, col - 1)) uf.union(index, (row - 1) * grid.length + (col - 2));
            if (col < grid.length && isOpen(row, col + 1)) uf.union(index, (row - 1) * grid.length + col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > grid.length || col < 1 || col > grid.length) {
            throw new IllegalArgumentException("Invalid site");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) {
            return false;
        }
        
        int index = (row - 1) * grid.length + (col - 1);
        return uf.find(index) == uf.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        p.open(4, 1);
        p.open(5, 1);
        System.out.println("Percolates: " + p.percolates()); // Expected: true
    }
}
