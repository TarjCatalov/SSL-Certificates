/** main program
 * @author M A Hakim Newton
 */

/* use tabsize 2 to see proper indentation */

/* ALL INDEXES ARE EFFECTIVELY 1 BASED WITH NULL or 0 AT INDEX 0 */

// YOU CAN CREATE OTHER CLASSES HERE AS NEEDED

public class mainProgram
{
	// YOU CAN ADD ATTRIBUTES OR METHODS HERE

  /** main function
   * @param args array of string arguments
   */
  public static void main(String[] args) 
	{
		try
		{
			// the first parameter is the number of persons (max 67600)
			// the second parameter is the number of categories (max 6) per person
			// the third parameter is the number of number of options (max 5) per category
			// the fourth parameter is the max cost for an option in a category
			// the fifth parameter is the max value for an option in a category
			problemGenerator p = new problemGenerator(4, 6, 5, 20, 20, System.nanoTime());

			// YOU CAN PROVIDE SEVERAL PROBLEM GENERATOR ABOVE.
			// EACH ONE CAN HAVE A DIFFERENT PARAMETER SETTING.
			// SOME FIXED SEEDS MAY BE LISTED FOR NICE SCENARIOS. 
			// ALL THOSE SHOULD BE COMMENTED OUT, EXCEPT ONE.
			// WE WILL COMMENT OR UNCOMMENT TO RUN ONLY ONE. 

			System.out.println("seed: " + p.randomSeed);	// do not change this line

			// YOU ARE FREE TO WRITE YOUR CODE FROM HERE BELOW FOR THE TASKS

			p.printProblem();		// print the problem details

			// keep track of the clock to measure time
			long startTime = System.nanoTime();

			// we are going to construct a dummy solution here
			// you will develop two algorithms to construct solutions
			// a dynamic programming method and a (backtracking plus branch and bound) method
			for(int i = 1; i <= p.numItem; ++i)
				p.choices[i] = 1;		// just select the first option

			// keep track of the clock to measure time
			long endTime = System.nanoTime();

			p.printSolution();	// print the solution details
	
			// print the execution time between start and end
			System.out.println("execution time: " + (endTime - startTime));

			// YOU MOST LIKELY DO NOT NEED TO MAKE ANY CHANGE BELOW
		}
		catch(problemGenerator.error e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	// YOU CAN ADD ATTRIBUTES OR METHODS HERE
}
