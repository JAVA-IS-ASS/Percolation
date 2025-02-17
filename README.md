# Percolation
Percolation is a fundamental problem in statistical physics and computer science that models the movement of liquids through a porous medium. In this project, we simulate an n-by-n grid where each site can either be open or blocked.

A site is open if water can pass through it and blocked otherwise.
Water percolates from the top row down to the bottom row through adjacent open sites.
The goal is to determine whether a full path exists from the top to the bottom of the grid.
To efficiently check percolation, we use the Union-Find (Disjoint Set) data structure to manage site connectivity.

# PercolationStats
Since percolation is a probabilistic problem, PercolationStats runs multiple Monte Carlo simulations to estimate the percolation threshold—the probability that percolation occurs for a randomly opened grid.

Runs T trials on random n-by-n grids.
Calculates the mean, standard deviation, and confidence interval of the percolation threshold.
Provides statistical insights into percolation behavior.

# Implementaion
Uses Weighted Quick Union (WQU) with Path Compression for efficient union-find operations.
Implements Monte Carlo simulation to estimate statistical properties.
