import java.util.*;
/** knapsack problem using dynamic programming
 * @author M A Hakim Newton
 */
public class prog05KnapsackProblem
{
	static int inf = 999999;

	public static void main(String[] args) 
	{
		// dimensions start from 1, ignore first values
		int [] w = {inf,3,2,4,1};					// weight
		int [] v = {0, 10, 40, 30, 50};		// value
		int W = 5;												// weight capacity
		int n = 4; 												// number of items
																	
		int [][] f = new int[n+1][W+1]; // total value of the picked item 
    boolean [][] p = new boolean[n+1][W+1]; // item picked or not picked

		knapsackSolution(n, W, v, w, f, p);
		System.out.println(showResult(n,W,w,f,p));
  }

	/** Solve an instance of the knapsack problem using iterative forward dynamic programming
   * @param n the number of items
   * @param W the capacity of the knapsack
	 * @param v the array containing the item values
	 * @param w the array containing the item weights
   * @param f the optimal value function f(k,l)
	 * @param p the optimal policy function p(k,l)
   */
 	public static void knapsackSolution(int n, int W, int[] v, int[]w, int [][] f, boolean [][] p) 
	{
  	// initialise the base cases
    for (int l = 0; l <= W; l++) 
		{
    	f[0][l] = 0; 		 // when no item, the value is 0
      p[0][l] = false; // when no item, no item picked
    }
		for (int k = 1; k <= n; k++) 
		{
    	f[k][0] = 0; 			// when capacity is 0, the value must be 0 regardless of the item
			p[k][0] = false; 	// when capacity is 0, no item can be picked regardless of the item
    }
		// calculate the maximum value that can be obtained by using the first k items and a knapsack of capacity l
    for (int k = 1; k <= n; k++) 
		{
    	for (int l = 1; l <= W; l++) 
			{
        if (l < w[k]) // if the new item is too heavy to be included in the knapsack
				{
        	f[k][l] = f[k-1][l]; 	// item is not picked, just take solution from k-1 items
					p[k][l] = false; 			// the item has not been picked
        } 
        else 
				{
        	f[k][l] = Math.max(f[k-1][l], f[k-1][l-w[k]] + v[k]);
          p[k][l] = (f[k-1][l-w[k]] + v[k] > f[k-1][l]);
        }
     	}	
		}
  }

  /** Output the items picked
   * @param n the number of items
   * @param W the knapsack weight capacity
   * @param w the array holding item weights
   * @param f the optimal value function
	 * @param p the optimal policy function
   */
  public static String showResult(int n, int W, int [] w, int[][] f, boolean[][] p) 
	{
		// determine which items are included in the knapsack
		String result = "";
		result += "total value: " + f[n][W] + "\n";
		int l = W;
		for (int k = n; k >= 1; k--) 
		{
			result += "item " + k + " included: " + p[k][l] + "\n";
			if (p[k][l]) 
				l = l - w[k];
		}
		return result;
  }
}
