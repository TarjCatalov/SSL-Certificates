/** queens problem using backtracking
 * @author M A Hakim Newton
 */

import java.util.*;
import java.lang.*;

public class prog04QueenBacktracking
{
	/** a simple implementation
	 */
	static class queenSimple
	{
		/** the main driving function
		 * @param n the number of queens
		 */
		public static void simpleMain(int n)
		{
			int[] q = new int[n + 1]; // since queen index starts from 1
			simpleRecur(q, 1, n);
		}
		/** the recursive function
		 * @param q the array of queen positions
		 * @param k the next position for a queen to assign to
		 * @param n the total number of queens
		 */
		public static void simpleRecur(int []q, int k, int n)
		{
			if (k > n)
			{
				System.out.print("queens:");
				for(int i = 1; i <= n; ++i)
					System.out.print(" " + q[i]);
				System.out.println();
				return;
			}
			for(q[k] = 1; q[k] <= n; ++q[k])
				if (checkSafe(q, k, n))
					simpleRecur(q, k + 1, n);
		}
		/** check whether kth queen is save w.r.t. prev queens
		 * @param q the array of queen positions
		 * @param k the index of the queen to check
		 * @param n the total number of queens
		 */
		static boolean checkSafe(int[] q, int k, int n)
		{
			for(int i = 1; i < k; ++i)
				if ((q[i] == q[k]) || (Math.abs(q[i] - q[k]) == (k - i)))
					return false;
			return true;
		}
	}

	/** an efficient implementation
	 */
	static class queenEfficient
	{
		/** the main driving function
		 * @param n the number of queens
		 */
		public static void efficientMain(int n)
		{
			int[] q = new int[n + 1]; 					// since queen index starts from 1
			boolean [] c = new boolean [n + 1]; // whether a column is occupied
			boolean [] db = new boolean [2*n];	// whether a backslash diagonal is occupied	
			boolean [] df = new boolean [2*n];	// whether a foreslash diagonal is occupied	
															
			for(int i = 1; i <= n; ++i)
				c[i] = false;
			for(int i = 1; i <= 2 * n - 1; ++i)
				db[i] = df[i] = false;	
															
			efficientRecur(q, c, db, df, 1, n);
		}

		/** the recursive function
		 * @param q the array of queen positions
		 * @param c the array to denote whether columns are occupied
		 * @param db the array to denote whether backslash diagonals are occupied
		 * @param df the array to denote whether foreslash diagonals are occupied
		 * @param k the next position for a queen to assign to
		 * @param n the total number of queens
		 */
		public static void efficientRecur(int []q, boolean[] c, 
											boolean [] db, boolean [] df, int k, int n)
		{
			if (k > n)
			{
				System.out.print("queens:");
				for(int i = 1; i <= n; ++i)
					System.out.print(" " + q[i]);
				System.out.println();
				return;
			}
			for(q[k] = 1; q[k] <= n; q[k]++)
				if (checkSafe(q, c, db, df, k, n))
				{
					c[q[k]] = db[(n-q[k] + k)] = df[(q[k] + k - 1)] = true; 
					efficientRecur(q, c, db, df, k + 1, n);
					c[q[k]] = db[(n-q[k] + k)] = df[(q[k] + k - 1)] = false; 
				}
		}

		/** check whether kth queen is save w.r.t. prev queens
		 * @param q the array of queen positions
		 * @param c the array to denote whether columns are occupied
		 * @param db the array to denote whether backslash diagonals are occupied
		 * @param df the array to denote whether foreslash diagonals are occupied
		 * @param k the index of the queen to check
		 * @param n the total number of queens
		 */
		static boolean checkSafe(int[] q, boolean[] c, 
					boolean [] db, boolean [] df, int k, int n)
		{
			return !(c[q[k]] || db[(n - q[k] +k)] || df[(q[k] + k - 1)]);
		}
	}

  /** main function
	 * @param args array of string arguments
	 */
  public static void main(String[] args)
  {
		int n = 10;
		long startTime, endTime;
	  startTime	= System.nanoTime();  
		queenSimple.simpleMain(n);
		endTime = System.nanoTime();
		System.out.println("time: " + (endTime - startTime));

		startTime	= System.nanoTime();  
		queenEfficient.efficientMain(n);
		endTime = System.nanoTime();
		System.out.println("time: " + (endTime - startTime));
	}
}
