import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used for the TP3 of ACT
 * @author COJEZ Arnaud, LEPRETRE Rémy
 *
 */
public class Main {

    //Fields
    public static Integer[][][][] configurations = null;

    //Methods

    /**
     * Main class used to experiment the TP3's algorithms 
     * @param args command-line parameters
     */
    public static void main(String[] args) {
	//QUESTION 10 -- GAME
	int m = 10;
	int n = 7;
	int i = 7;
	int j = 3;
	
	if(args.length == 4) {
	    m = Integer.parseInt(args[0]);
	    n = Integer.parseInt(args[1]);
	    i = Integer.parseInt(args[2]);
	    j = Integer.parseInt(args[3]);
	} else 
	    System.out.println("You should enter 4 parameters in the command. ex : java Main 10 7 7 3");
	
	if(m <= i || n <= j || i < 0 || j < 0) {
	    m = 10;
	    n = 7;
	    i = 7;
	    j = 3;
	    System.out.println("You should enter a valid configuration in the command. ex : java Main 10 7 7 3");
	}
	    game(m,n,i,j);
    }

    /**
     * Method used to answer to the question 3
     */
    public static void q3() {
	System.out.println(dyn_winRate(10,7,7,3));
	System.out.println(dyn_winRate(10,7,5,3));
    }

    /**
     * Method used to answer to the question 4
     */
    public static void q4() {
	  System.out.println(dyn_winRate(100,100,50,50));
	  System.out.println(dyn_winRate(100,100,48,52));
    }

    /**
     * Method used to answer to the question 5
     */
    public static void q5() {
	LinkedList<String> list = new LinkedList<String>();
	for(int i = 126; i >= 0; i--)
	    for(int j = 126; j >= 0; j--) {
		System.out.print("conf: 127, 127, " + i + ", " + j);
		int res = dyn_winRate(127,127,i,j);
		System.out.print(" = " + res);
		if(res == 127) {
		    System.out.print("<=======");
		    list.add("conf: 127, 127, " + i + ", " + j + " = " + res);
		}
		System.out.println("");
	    }

	System.out.println("results :");
	
	for(String str : list)
	    System.out.println(str);
    }
    
    /** Computes the previous value of a position
     * @param list of the value to compare
     * @return int the previous value of the position
     */
    static public int valPreviousPos(List<Integer> list) {
	if(list.isEmpty())
	    throw new IllegalArgumentException();
	
	int val = list.get(0);
	for (int i=1; i<list.size(); i++) {
	    int iElement = list.get(i);
	    if (val > 0) {
		if (iElement > val) {
		    val = iElement;
		}
		else if (iElement <= 0) {
		    val = iElement;
		}
	    }
	    else if (val == 0) {
		if (iElement < val) {
		    val = iElement;
		}
	    }
	    else {
		if(iElement > val && iElement < 0) {
		    val = iElement;
		}
	    }
	}

	//Return a different value depending the min and max values
	
	if (val == 0) {
	    return 1;
	}
	else if (val > 0) {
	    return -val - 1;
	}
	else {
	    return -val + 1;
	}
    }

    /** 
     * Computes the winRate of a configuration in a naive way
     * @param width of the configuration
     * @param height of the configuration
     * @param widthSKull the width of the skull
     * @param heightSkull the height of the skull
     * @return the winrate
     */
    static int rec_winRate(int width, int height, int widthSkull, int heightSkull) {
	List<Integer> configuration = new ArrayList<Integer>();

	//if the configuration is juste one square
	if ( width == 1 && height == 1) {
	    return 0;
	}

	//else calculate all the different possibilities of cutting
	//cut from the left
	for (int k=1;k<=widthSkull;k++) {
	    configuration.add(rec_winRate(width-k, height, widthSkull-k, heightSkull));
	}
	//cut from the top
	for (int f=1;f<=heightSkull;f++) {
	    configuration.add(rec_winRate(width, height-f, widthSkull, heightSkull-f));
	}
	//cut from the right

	for (int q=1;q<(width-widthSkull);q++) {
	    configuration.add(rec_winRate(width-q, height, widthSkull, heightSkull));
	}
	//cut from the bottom
	for (int u=1;u<(height-heightSkull);u++) {
	    configuration.add(rec_winRate(width, height-u, widthSkull, heightSkull));
	}
	return valPreviousPos(configuration);
    }
    
    /**
     * Computes the winRate of a configuration in a dynamic way
     * @param width of the configuration
     * @param height of the configuration
     * @param widthSKull the width of the skull
     * @param heightSkull the height of the skull
     * @return the winrate
     */
    public static int dyn_winRate(int width, int height, int widthSkull, int heightSkull) {
	initConfigurationTable(width, height, widthSkull, heightSkull);
	
	List<Integer> configuration = new ArrayList<Integer>(0);
	//if juste one square
	if ( width == 1 && height == 1) {
	    addConfiguration(width, height, widthSkull, heightSkull, 0);
	}
	
	else
	    //else calculate all the different possibilities of cutting
	    if(getConfiguration(width, height, widthSkull, heightSkull) == null) {
		//cut from the left
		for (int k=1;k<=widthSkull;k++) {
		    configuration.add(dyn_winRate(width-k, height, widthSkull-k, heightSkull));
		}
		//cut from the top
		for (int f=1;f<=heightSkull;f++) {
		    configuration.add(dyn_winRate(width, height-f, widthSkull, heightSkull-f));
		}
		//cut from the right
		for (int q=1;q<width-widthSkull;q++) {
		    configuration.add(dyn_winRate(width-q, height, widthSkull, heightSkull));
		}
		//cut from the bottom
		for (int u=1;u<height-heightSkull;u++) {
		    configuration.add(dyn_winRate(width, height-u, widthSkull, heightSkull));
		}

		//adding the result to the configurations table
		addConfiguration(width, height, widthSkull, heightSkull, valPreviousPos(configuration));
	    }

	//returns the content of the current configuration from the table
	return getConfiguration(width, height, widthSkull, heightSkull);
    }
	
    /**
     * Initializes the configurations table with the minimum size (using the symetry)
     * @param width of the configuration
     * @param height of the configuration
     * @param widthSKull the width of the skull
     * @param heightSkull the height of the skull
     * @return the winrate
     */
    public static void initConfigurationTable(int width, int height, int widthSkull, int heightSkull) {
	int newM;
	int newN;
	int tmpI;
	int tmpJ; 

	// Compute the symetric of the configuration where m > n, and i and j are the smallest possible
	if(width < height) {
	    newM = height;
	    newN = width;
	    tmpI = heightSkull;
	    tmpJ = widthSkull;
	} else {
	    newM = width;
	    newN = height;
	    tmpI = widthSkull;
	    tmpJ = heightSkull;
	}
	
	int newI = Math.min(tmpI, newM / 2);
	int newJ = Math.min(tmpJ, newN / 2);

	//Initializes the 4 dimensions array
	if(configurations == null || configurations.length < newM || configurations[0].length < newN || configurations[0][0].length < newI || configurations[0][0][0].length < newJ) {
	    configurations = new Integer[newM][newN][newI+1][newJ+1];
	    for(int i = 0; i < newM; i++) {
		for(int j = 0; j < newN; j++) {
		    for(int k = 0; k <= newI; k++) {
			for(int l = 0; l <= newJ; l++) {
			    configurations[i][j][k][l] = null;
			}		    
		    }
		}
	    }
	}
    }

    /**
     * Adds the configuration to the configurations table with the minimum size (using the symmetry)
     * @param m of the configuration
     * @param n of the configuration
     * @param i the width of the skull
     * @param j the height of the skull
     * @param result the value of the configuration
     */
    public static void addConfiguration(int m, int n, int i, int j, int result) {

	// Compute the symetric of the configuration where m > n, and i and j are the smallest possible
	int newM;
	int newN;
	int tmpI;
	int tmpJ; 

	if(m < n) {
	    newM = n;
	    newN = m;
	    tmpI = j;
	    tmpJ = i;
	} else {
	    newM = m;
	    newN = n;
	    tmpI = i;
	    tmpJ = j;
	}

	int newI = Math.min(tmpI, newM - tmpI - 1);
	int newJ = Math.min(tmpJ, newN - tmpJ - 1);

	//Adds the result to the table
	configurations[newM-1][newN-1][newI][newJ] = result;
    }

    /**
     * Fetches the configuration to the configurations table with the minimum size (using the symmetry)
     * @param m of the configuration
     * @param n of the configuration
     * @param i the width of the skull
     * @param j the height of the skull
     */    
    public static Integer getConfiguration(int m, int n, int i, int j) {
	// Compute the symetric of the configuration where m > n, and i and j are the smallest possible
	int newM;
	int newN;
	int tmpI;
	int tmpJ; 

	if(m < n) {
	    newM = n;
	    newN = m;
	    tmpI = j;
	    tmpJ = i;
	} else {
	    newM = m;
	    newN = n;
	    tmpI = i;
	    tmpJ = j;
	}
		
	int newI = Math.min(tmpI, newM - tmpI - 1);
	int newJ = Math.min(tmpJ, newN - tmpJ - 1);
	
	//Fetches the configuration's value from the table
	return configurations[newM -1][newN -1][newI][newJ];
    }

    /**
     * Plays the "Poisonnous Chocolate" game.
     * @param m the width of the configuration
     * @param n the height of the configuration
     * @param i the width of the poisonnous chocolate
     * @param j the height of the poisonnous chocolate
     */
    public static void game(int m, int n, int i, int j) {
	Scanner keyboard = new Scanner( System.in ) ; 
	Configuration configuration = new Configuration(m,n,i,j);
	boolean human = false;
	int input;

	//Game Loop
	while(configuration.value != 0) {
	    //next turn we change the player
	    human = !human;

	    //Prints the actual configuration as a chocolate plack
	    System.out.println("================================================");
	    System.out.print("Actual configuration : " + configuration.toGrid());

	    //Human plays
	    if(human) {
		//Prints the configurations
		System.out.println("Possible configurations :");
		
		int k = 1;
		
		List<Configuration> possibilities = configuration.getChildren();
		
		for (Configuration conf : possibilities) {
		    System.out.print((k) + ". " + conf + "\t");
		    if((k++ % 3) == 0)
			System.out.println("");
		}
		if(((k-1) % 3) != 0)
			System.out.println("");

		//Scans the user choice
		while (true) {
		    System.out.print("> ");

		    input = keyboard.nextInt() -1;
		    
		    if (input >= 0 && input < possibilities.size())
			break;
		    System.out.println("Wrong input");
		}

		//Prints the choice of the user
		System.out.print("Human gives ");
		configuration = possibilities.get(input);
		System.out.println(configuration.toGrid());
	    }
	    //Computer plays
	    else {
		System.out.print("Computer gives ");
		//Fetches the better configuration to choose
		configuration = configuration.getBestChild();
		System.out.println(configuration.toGrid() + "(turn(s) planned : " + Math.abs(configuration.value) + ")");
	    }
	}
	
	//The game is finished
	System.out.println("================================================");
	if(human)
	    System.out.println("Human Won !");
	else
	    System.out.println("Computer Won !");

	keyboard.close();
    }    
    
    /**
     * Class used in the Game to represent a configuration.
     */
    public static class Configuration {

	//Fields
	public int m;
	public int n;
	public int i;
	public int j;
	public Integer value = null;

	/**
	 * Configuration constructor.
	 * Computes the value of the configuration using the given parameters.
	 * @param m the width of the configuration
	 * @param n the height of the configuration
	 * @param i the width of the poisonnous chocolate
	 * @param j the height of the poisonnous chocolate
	 */
	public Configuration(int m, int n, int i, int j) {
	    this.m = m;
	    this.n = n;
	    this.i = i;
	    this.j = j;
	    this.value = dyn_winRate(m, n, i, j);
	}

	/**
	 * Creates all the children of the current configuration
	 * @return list of the current configuration's children 
	 */
	public List<Configuration> getChildren() {
	    List<Configuration> children = new ArrayList<Configuration>();
	    //cut from the left
	    for (int k=1;k<=this.i;k++) {
		children.add(new Configuration(this.m-k, this.n, this.i-k, this.j));
	    }
	    
	    //cut from the right
	    for (int q=this.m-this.i -1; q>=1 ;q--) {
		children.add(new Configuration(this.m-q, this.n, this.i, this.j));
	    }
	    
	    //cut from the top
	    for (int f=1;f<=this.j;f++) {
		children.add(new Configuration(this.m, this.n-f, this.i, this.j-f));
	    }
	    
	    //cut from the bottom
	    for (int u=this.n-this.j -1; u>=1;u--) {
		children.add(new Configuration(this.m, this.n-u, this.i, this.j));
	    }

	    return children;
	}

	/**
	 * Fetches all the children using getChildren() then computes and returns the best choice as a configuration
	 * @return the best choice as a configuration
	 */
	public Configuration getBestChild() {

	    //Gets the children
	    List<Configuration> configurations = this.getChildren();
	    
	    if(configurations.isEmpty())
		throw new IllegalArgumentException();

	    //Choose the best configuration
	    if(this.value < 0) {
		for(Configuration el : configurations)
		    if(el.value == -(this.value +1))
			return el;
	    } else {
		for(Configuration el : configurations)
		    if(el.value == -(this.value -1))
			return el;
	    }

	    //if nothing found 
	    return this;
	}

	/**
	 * Returns a String representing the current configuration
	 * @return a String representing the current configuration
	 */
	public String toString() {
	    return "" + this.m + " " + this.n + " " + this.i + " " + this.j;
	}

	/**
	 * Draws then returns a String representing the current configuration as a chocolate plack
	 * @return a String representing the current configuration as a chocolate plack
	 */
	public String toGrid() {
	    String res = "\n";

	    //Top edge
	    for(int i = 0; i < this.m; i++)
		res += "+-";
	    res += "+\n";

	    //Content
	    for(int j = 0; j < this.n; j++) {
		for(int i = 0; i < this.m; i++) 
		    if(i == this.i && j == this.j)
			//poisonnous chocolate
			res += "|@";
		    else
			//classic chocolate
			res += "| ";
		//Right edge
		res += "|\n";

		//Line Separator
		for(int i = 0; i < this.m; i++)
		    res += "+-";
		res += "+\n";
	    }
	    
	    return res += "\n";
	}
	
    }
    
}

