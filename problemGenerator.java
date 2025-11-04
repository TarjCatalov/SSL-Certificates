/** problem generator for people to be quarantined.
 * 	@author M A Hakim Newton
 */

/* use tabsize 2 to see proper indentation */

/* ALL INDEXES ARE EFFECTIVELY 1 BASED WITH NULL or 0 AT INDEX 0 */

// some useful stuffs
import java.lang.*;
import java.util.*;

/** generates items using persons and categories
 *  generates costs and values per items and budget
 */
class problemGenerator
{
	// to throw an error message
	public class error extends Exception 
	{
		/** constructor
		 */
  	public error(String message)
		{
     	super(message);
  	}
	}

	// collection of 260 first names, ignoring the Null value
	private static final String[] firstNames = 
		{
			"Null",
    	"Aaron","Abigail","Adam","Adrian","Aisha","Alan","Albert","Alexa","Alice","Amelia",
			"Barbara","Benjamin","Bethany","Bianca","Blake","Bob","Bonnie","Brandon","Brian","Bruce",
			"Caleb","Cameron","Carlos","Caroline","Carter","Catherine","Chad","Chloe","Chris","Clara",
			"Daisy","Dakota","Daniel","Danielle","David","Deborah","Dennis","Derek","Diana","Diego",
			"Edward","Elaine","Eleanor","Elena","Eli","Elijah","Elizabeth","Ella","Emily","Ethan",
			"Faith","Farhan","Fatima","Felix","Fiona","Florence","Frances","Francis","Frank","Freya",
			"Gabriel","Gail","Garrett","Gavin","George","Georgia","Gerald","Grace","Gregory","Gwen",
			"Hannah","Harold","Harper","Harry","Hazel","Heather","Helen","Henry","Holly","Hunter",
			"Ian","Ibrahim","Ida","Imogen","India","Ingrid","Irene","Iris","Isabel","Isabella",
			"Jack","Jacob","Jade","James","Jamie","Jasmine","Jason","Jayden","Jennifer","Jessica",
			"Kai","Karen","Katherine","Katie","Kayla","Keith","Kelly","Kenneth","Kevin","Kimberly",
			"Lara","Laura","Lauren","Leah","Leo","Leon","Liam","Lily","Linda","Logan",
			"Madeline","Madison","Marcus","Maria","Mark","Martha","Martin","Mary","Mateo","Matthew",
			"Naomi","Natalie","Nathan","Neil","Nelson","Nicholas","Nicole","Nina","Noah","Nora",
			"Oliver","Olivia","Omar","Opal","Ophelia","Oscar","Otis","Owen","Ozzy","Octavia",
			"Paige","Pamela","Parker","Patricia","Patrick","Paul","Paula","Penelope","Peter","Phoebe",
			"Quentin","Quincy","Quinn","Quiana","Quintin","Quinlan","Quiana","Queenie","Quin","Quisha",
			"Rachel","Rafael","Ralph","Ramona","Randall","Ravi","Raymond","Rebecca","Richard","Robert",
			"Sabrina","Sahil","Samuel","Sandra","Sara","Sarah","Scott","Sean","Sebastian","Sophia",
			"Tamara","Tanya","Taylor","Teresa","Theodore","Thomas","Tiffany","Timothy","Tracy","Trevor",
			"Umar","Una","Uriah","Uriel","Ulises","Umberto","Unity","Ursula","Usman","Ulysses",
			"Valerie","Vanessa","Vera","Veronica","Victor","Victoria","Vincent","Viola","Violet","Vivian",
			"Walter","Wanda","Warren","Wayne","Wendy","Wesley","Whitney","William","Willow","Wyatt",
			"Xander","Xavier","Xena","Ximena","Xiomara","Xochitl","Xyla","Xylia","Xyra","Xyrus",
			"Yara","Yasmin","Yasmine","Yehuda","Yesenia","Yolanda","Yosef","Yuna","Yuri","Yvette",
			"Zachary","Zahra","Zain","Zara","Zeke","Zelda","Zoe","Zofia","Zora","Zubair"
    };

	// collection of 260 last names, ignoring the Null value
   private static final String[] lastNames = 
		{
				"Null",
        "Adams","Aguilar","Ahmed","Aiken","Akers","Allan","Allen","Alvarez","Anderson","Armstrong",
        "Bailey","Baker","Ball","Banks","Barber","Barker","Barnes","Barrett","Barton","Bates",
        "Cabrera","Calderon","Cameron","Campbell","Campos","Cardenas","Carlson","Carpenter","Carr","Carroll",
        "Daniels","Davidson","Davis","Dawson","Day","Dean","Delgado","Dennis","Diaz","Dixon",
        "Edwards","Elliott","Ellis","Emerson","Erickson","Eriksen","Escobar","Espinoza","Estrada","Evans",
        "Ferguson","Fernandez","Fields","Fisher","Fleming","Fletcher","Flores","Ford","Foster","Fox",
        "Gaines","Gallagher","Garcia","Gardner","Garner","George","Gibbs","Gibson","Gilbert","Gomez",
        "Hall","Hamilton","Hansen","Hanson","Hardy","Harmon","Harper","Harrison","Hart","Hawkins",
        "Ibrahim","Ingram","Irwin","Isaacs","Iverson","Ivory","Irizarry","Ismail","Inoue","Inman",
        "Jackson","Jacobs","James","Jefferson","Jenkins","Jennings","Jensen","Jimenez","Johns","Johnson",
        "Kane","Kapoor","Karlsson","Kaur","Kelley","Kelly","Kerr","Khan","Kim","King",
        "Lam","Lambert","Lane","Lang","Lara","Larson","Lawrence","Le","Lee","Lewis",
        "MacDonald","Mack","Madden","Maldonado","Malik","Mann","Marshall","Martinez","Mason","Matthews",
        "Navarro","Neal","Nelson","Newman","Ng","Nguyen","Nichols","Nielsen","Nolan","Norris",
        "Obrien","Ochoa","Oconnor","Odom","Oliver","Olsen","Olson","Oneal","Ortega","Ortiz",
        "Pace","Pacheco","Padilla","Page","Palmer","Park","Parker","Patel","Patrick","Perez",
        "Qadir","Qasim","Qualls","Quang","Queen","Quick","Quinlan","Quinn","Quintero","Quirke",
        "Ramirez","Ramos","Randall","Ray","Reed","Reid","Reyes","Reynolds","Rice","Richardson",
        "Salazar","Sampson","Sanchez","Sanders","Santos","Scott","Shah","Sharma","Shaw","Silva",
        "Tanner","Taylor","Terry","Thomas","Thompson","Torres","Townsend","Tran","Trevino","Turner",
        "Uddin","Ulrich","Underwood","Unger","Upadhyay","Urban","Uribe","Usman","Upton","Ursu",
        "Valdez","Valencia","Valentine","Valenzuela","Vance","Vargas","Vasquez","Vaughn","Vega","Velasquez",
        "Wade","Wagner","Walker","Wallace","Walsh","Walter","Ward","Washington","Watkins","Watson",
        "Xavier","Xenos","Xin","Xiong","Xu","Xue","Ximenez","Xiang","Xuan","Xiu",
        "Yamamoto","Yang","Yates","Ye","Yilmaz","Yin","Yoder","Yoon","Young","Yu",
        "Zamora","Zapata","Zavala","Zayas","Zeller","Zeng","Zhang","Zhao","Zimmerman","Zuniga"
    };

	// store the combinations of first and last names, ignore the first one as it is Null Null
	// only numPerson names will be essentially used for the problem where numPerson is a parameter
	private static String[] persons;	// values will be assigned in the constructor	

	// categories of items per person, ignoring the Null value
	public String[] categories = {"Null", "Food", "Medical", "Sanitation", "Equipment", "Support", "Utility"};  

	// options for each category, ignoring the Null value
	public String[] options = { "Null" , "Basic", "Standard", "Extended", "Family", "Comprehensive"};

	// item is a combination of a person and a category
	public class item
	{
		public int personIndex;			//	person index, 1 based index
		public int categoryIndex;		// category index, 1 based index

		// constructor to create an item from person and category indexes 
		public item(int personIndex, int categoryIndex)
		{
			this.personIndex = personIndex;
			this.categoryIndex = categoryIndex;
		} 
	}

	public long randomSeed;				// seed of the random number generator

	public int numPerson;					// the number of persons quarantined
	public int numCategory;				// the number of categories for each person
	public int numOption;					// the number of options in each category

	public int numItem;						// the number of items = numPerson * numCategory
	public item[] items; 					// the items (personIndex, categoryIndex)

	public int[][] costs;					// costs for each category each option (numCategory * numOption)
	public int[][] values;				// values for each category each option (numCategory * numOption)

	public int maxCost;						// max cost per option per category
	public int maxValue;					// max value per option per category

	public int budget;						// budget allowed for the reliefwork

	public int[] choices;					// choices mades for each item: this is the solution essentially.

	/** constructor !!!!!
	 *	@param numPerson the number of persons quarantined
	 *	@param numCategory the number of categories for each person
	 *	@param numOption the number of options in each category
	 *	@param maxCost the maximum cost for an option in a category
	 *	@param maxValue the maximum value for an option in a category
	 *	@param randomSeed the seed to be used for random number generation
	 */
	problemGenerator(int numPerson, int numCategory, int numOption, int maxCost, int maxValue, long randomSeed) 
				throws problemGenerator.error
	{
		// store the given seed for random number generation
		// also create a number number generator object
		this.randomSeed = randomSeed;
		Random rng = new Random(randomSeed);

		// get all possible combinations of first and last names.
		this.persons = new String[(firstNames.length - 1)*(lastNames.length - 1) + 1];
		this.persons[0] = new String("Null Null");
		for(int f = 1; f < firstNames.length; f++)
			for(int l = 1; l < lastNames.length; l++)
				this.persons[(f - 1) * (lastNames.length - 1) + l] = 
										new String(firstNames[f] + " " + lastNames[l]);

		// perform random shuffling of the names
		for(int p1 = 1; p1 < persons.length; p1++)
		{
			int p2 = rng.nextInt(persons.length - 1) + 1;
			String temp = persons[p1];
			persons[p1] = persons[p2];
			persons[p2] = temp;
		}

		// check the limits of the input parameters
		if (numPerson < 1)
			throw new error("number of persons must be at least 1, but given " + numPerson); 
		if (numCategory < 1)
			throw new error("number of categories must be at least 1, but given " + numCategory); 
		if (numOption < 1)
			throw new error("number of options must be at least 1, but given " + numOption); 
		if (numPerson >= persons.length)
			throw new error("number of persons must be smaller than " + persons.length + ", but given " + numPerson); 
		if (numCategory >= categories.length)
			throw new error("number of categories must be smaller than " + categories.length + ", but given " + numCategory); 
		if (numOption >= options.length)
			throw new error("number of options must be smaller than " + options.length + ", but given " + numOption); 
		if (maxCost < numOption)
			throw new error("max cost per option per unit category must be at least " + numOption + ", but given " + maxCost); 
		if (maxValue < numOption)
			throw new error("max value per option per unit category must be at least " + numOption + ", but given " + maxValue); 

		// store the given parameters
		this.numPerson = numPerson;
		this.numCategory = numCategory;
		this.numOption = numOption;
		this.maxCost = maxCost;
		this.maxValue = maxValue;

		// create items using persons and categories
		this.numItem = numPerson * numCategory;		
		this.items = new item[numItem + 1];
		this.items[0] = new item(0,0); 
		for(int p = 1; p <= numPerson; p++)
			for(int c = 1; c <= numCategory; c++)
				this.items[(p-1) * numCategory + c] = new item(p, c);

		// generate the costs for each category each option
		this.costs = new int[numCategory + 1][numOption + 1];
		for(int c = 1; c <= numCategory; c++)
		{
			for(int o = 1; o <= numOption; o++)
				this.costs[c][o] = rng.nextInt(maxCost) + 1; 
			Arrays.sort(costs[c]);
		}

		// generate the values for each category each option
		this.values = new int[numCategory + 1][numOption + 1];
		for(int c = 1; c <= numCategory; c++)
		{
			for(int o = 1; o <= numOption; o++)
				this.values[c][o] = rng.nextInt(maxValue) + 1; 
			Arrays.sort(values[c]);
		}

		this.budget = rng.nextInt(numItem * maxCost / 2) + numItem * maxCost / 4;

		// just create the array for the choices made
		this.choices = new int[numItem + 1];
	}

	/** print the problem details
	 */
	public void printProblem()
	{
		System.out.println("problem details:");
		// print the persons
		System.out.println("persons: " + numPerson);
		for(int p = 1; p <= numPerson; p++)
		{
			System.out.print(persons[p]);
			if (p % 10 == 0)
				System.out.println();
			else if (p != numPerson)
				System.out.print(", ");
		}
		if (numPerson % 10 != 0)
			System.out.println();

		// print the categories
		System.out.println("categories: " + numCategory);
		for(int c = 1; c <= numCategory; c++)
		{
			System.out.print(categories[c]);
			if (c != numCategory)
				System.out.print(", ");
		}
		System.out.println();

		// print the options
		System.out.println("options: " + numOption);
		for(int o = 1; o <= numOption; o++)
		{
			System.out.print(options[o]);
			if (o != numOption)
				System.out.print(", ");
		}
		System.out.println();

		// print the costs
		System.out.println("costs per option per category");
		for(int c = 1; c <= numCategory; c++)
		{
			System.out.print(categories[c] + " ");
			for(int o = 1; o <= numOption; o++)
			{
				System.out.print(costs[c][o]);
				if (o != numOption)
					System.out.print(", ");
			}
			System.out.println();
		}
		
		// print the values
		System.out.println("values per option per category");
		for(int c = 1; c <= numCategory; c++)
		{
			System.out.print(categories[c] + " ");
			for(int o = 1; o <= numOption; o++)
			{
				System.out.print(values[c][o]);
				if (o != numOption)
					System.out.print(", ");
			}
			System.out.println();
		}
	
		// print the budget
		System.out.println("budget: " + budget);
	}

	/** print the solution details
	 */
	void printSolution()
	{
		System.out.println("solution details:");
		System.out.println("person category choice cost value");
		int totalCost = 0, totalValue = 0;
		for(int i = 1; i <= numItem; i++)
		{
			System.out.print(persons[items[i].personIndex] + " " + categories[items[i].categoryIndex] + " " + options[choices[i]]);
			if (choices[i] > 0)
			{ 
				System.out.println(" " + costs[items[i].categoryIndex][choices[i]] + " " + values[items[i].categoryIndex][choices[i]]);
				totalCost = totalCost + costs[items[i].categoryIndex][choices[i]];
				totalValue = totalValue + values[items[i].categoryIndex][choices[i]];
			}
			else
				System.out.println();
		}
		System.out.println("total cost: " + totalCost + " " + "total value: " + totalValue);
		if (totalCost <= budget)
			System.out.println("total cost is within the budget");
		else
			System.out.println("total cost is not within the budget.");
	}
}
