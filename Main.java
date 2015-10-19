import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Class Chocolate
 * @author COJEZ Arnaud, LEPRETRE Rémy
 *
 */

public class Main {

    public static Integer[][][][] configurations = null;

    public static void main(String[] args) {
	/*
	 
	System.out.println(dyn_winRate(10,7,7,3));
	System.out.println(dyn_winRate(10,7,5,3));
	*/
/*
	   q4
	  System.out.println(dyn_winRate(100,100,50,50));
	  System.out.println(dyn_winRate(100,100,48,52));
	
	
*/
	/*
	  q5
	
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
	*/
	
	
	/*
	  System.out.println(winRate(1,1,1,1));
	  System.out.println(winRate(2,1,2,1));
	  System.out.println(winRate(1,2,1,2));
	  System.out.println(winRate(2,2,2,2));
	  System.out.println(winRate(3,2,3,2));
	  System.out.println(winRate(3,1,3,1));
	  System.out.println(winRate(3,2,3,2));*/

	int m = Integer.parseInt(args[0]);
	int n = Integer.parseInt(args[1]);
	int i = Integer.parseInt(args[2]);
	int j = Integer.parseInt(args[3]);
	game(m,n,i,j);
    }

    
    /** calculate the previous value of a position
     *@param list of the value to compare
     *@return int the previous value of the position
     */
    static public int valPreviousPos(List<Integer> list) {
	//maybe do a try catch to check if the list is empty or not?
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
     * Computes the winRate of a chocolateBar in a 
     * @param width of the chocolateBar
     * @param height of the chocolateBar
     * @param widthSKull the width of the skull
     * @param heightSkull the height of the skull
     * @return the winrate
     */
    static int rec_winRate(int width, int height, int widthSkull, int heightSkull) {
	List<Integer> chocolateBar = new ArrayList<Integer>();
	//if juste one square
	if ( width == 1 && height == 1) {
	    return 0;
	}
	//else calculate all the different possibilities of cutting
	//cut by the left
	for (int k=1;k<=widthSkull;k++) {
	    chocolateBar.add(rec_winRate(width-k, height, widthSkull-k, heightSkull));
	}
	//cut by the up
	for (int f=1;f<=heightSkull;f++) {
	    chocolateBar.add(rec_winRate(width, height-f, widthSkull, heightSkull-f));
	}
	//cut by the right

	for (int q=1;q<(width-widthSkull);q++) {
	    chocolateBar.add(rec_winRate(width-q, height, widthSkull, heightSkull));
	}
	//cut by the down
	for (int u=1;u<(height-heightSkull);u++) {
	    chocolateBar.add(rec_winRate(width, height-u, widthSkull, heightSkull));
	}
	return valPreviousPos(chocolateBar);
    }
    
    /**
     * Computes the winRate of a chocolateBar in a dynamic way
     * @param width of the chocolateBar
     * @param height of the chocolateBar
     * @param widthSKull the width of the skull
     * @param heightSkull the height of the skull
     * @return the winrate
     */
    public static int dyn_winRate(int width, int height, int widthSkull, int heightSkull) {
	initConfigurationTable2(width, height, widthSkull, heightSkull);
	
	List<Integer> chocolateBar = new ArrayList<Integer>(0);
	//if juste one square
	if ( width == 1 && height == 1) {
	    addConfiguration(width, height, widthSkull, heightSkull, 0);
	}
	
	else
	    //else calculate all the different possibilities of cutting
	    if(getConfiguration(width, height, widthSkull, heightSkull) == null) {
		//cut by the left
		for (int k=1;k<=widthSkull;k++) {
		    chocolateBar.add(dyn_winRate(width-k, height, widthSkull-k, heightSkull));
		}
		//cut by the up
		for (int f=1;f<=heightSkull;f++) {
		    chocolateBar.add(dyn_winRate(width, height-f, widthSkull, heightSkull-f));
		}
		//cut by the right
		for (int q=1;q<width-widthSkull;q++) {
		    chocolateBar.add(dyn_winRate(width-q, height, widthSkull, heightSkull));
		}
		//cut by the down
		for (int u=1;u<height-heightSkull;u++) {
		    chocolateBar.add(dyn_winRate(width, height-u, widthSkull, heightSkull));
		}

		//adding the result to the configurations table
		addConfiguration(width, height, widthSkull, heightSkull, valPreviousPos(chocolateBar));
	    }

	//returns the content of the current configuration from the table
	return getConfiguration(width, height, widthSkull, heightSkull);
    }

    /**
     * Initializes the configurations tables
     *@param width of the chocolateBar
     *@param height of the chocolateBar
     *@param widthSKull the width of the skull
     *@param heightSkull the height of the skull
     *@return the winrate
     */
    public static void initConfigurationTable(int width, int height, int widthSkull, int heightSkull) {
	if(configurations == null || configurations.length < width || configurations[0].length < height || configurations[0][0].length < widthSkull || configurations[0][0][0].length < heightSkull) {
	    configurations = new Integer[width][height][widthSkull+1][heightSkull+1];
	    for(int i = 0; i < width; i++) {
		for(int j = 0; j < height; j++) {
		    for(int k = 0; k <= widthSkull; k++) {
			for(int l = 0; l <= heightSkull; l++) {
			    //System.out.println("init : " + i + " " + j + " " + k + " " + l + " " );
			    configurations[i][j][k][l] = null;
			}		    
		    }
		}
	    }
	}
    }
	
/**
     * Initializes the configurations tables
     *@param width of the chocolateBar
     *@param height of the chocolateBar
     *@param widthSKull the width of the skull
     *@param heightSkull the height of the skull
     *@return the winrate
     */
    public static void initConfigurationTable2(int width, int height, int widthSkull, int heightSkull) {
	/*
	int newM = Integer.max(width,height);
	int newN = Integer.min(width,height);
	
	int tmpI = newM / 2;
	int tmpJ = newN / 2;
	
	int newI = Integer.max(tmpI, tmpJ);
	int newJ = Integer.min(tmpI, tmpJ);
*/

	int newM;
	int newN;
	int tmpI;
	int tmpJ; 

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
	
	int newI = Integer.min(tmpI, newM / 2);
	int newJ = Integer.min(tmpJ, newN / 2);
	
	if(configurations == null || configurations.length < newM || configurations[0].length < newN || configurations[0][0].length < newI || configurations[0][0][0].length < newJ) {
	    //System.out.println("init : " + newM + " " + newN + " " + newI + " " + newJ );
	    configurations = new Integer[newM][newN][newI+1][newJ+1];
	    for(int i = 0; i < newM; i++) {
		for(int j = 0; j < newN; j++) {
		    for(int k = 0; k <= newI; k++) {
			for(int l = 0; l <= newJ; l++) {
			    //System.out.println("init : " + i + " " + j + " " + k + " " + l + " " );
			    configurations[i][j][k][l] = null;
			}		    
		    }
		}
	    }
	}
    }
    
    public static void addConfiguration(int m, int n, int i, int j, int result) {
	
	/*int newM = Integer.max(m,n);
	int newN = Integer.min(m,n);
	*/
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
	
	
	int newI = Integer.min(tmpI, newM - tmpI - 1);
	int newJ = Integer.min(tmpJ, newN - tmpJ - 1);
	
	configurations[newM-1][newN-1][newI][newJ] = result;
    }
    
    public static Integer getConfiguration(int m, int n, int i, int j) {
	/*
	  int newM = Integer.max(m,n);
	int newN = Integer.min(m,n);
	
	int tmpI = Integer.min(i, m - i - 1);
	int tmpJ = Integer.min(j, n - j - 1);
	
	int newI = Integer.max(tmpI, tmpJ);
	int newJ = Integer.min(tmpI, tmpJ);
	*/
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
	
	
	int newI = Integer.min(tmpI, newM - tmpI - 1);
	int newJ = Integer.min(tmpJ, newN - tmpJ - 1);
	
	//System.out.println("get : " + newM + " " + newN + " " + newI + " " + newJ );
	return configurations[newM -1][newN -1][newI][newJ];
    }


    public static void game(int m, int n, int i, int j) {
	Scanner keyboard = new Scanner( System.in ) ; 
	Configuration configuration = new Configuration(m,n,i,j);
	boolean human = false;
	int input;

	while(configuration.value != 0) {
	    human = !human;
	    
	    System.out.println("================================================");
	    System.out.println("Actual configuration : " + configuration);
	    
	    if(human) {
		System.out.println("Possible configurations :");
		
		int k = 1;
		
		List<Configuration> possibilities = configuration.getChildren();
		
		for (Configuration conf : possibilities)
		    System.out.println((k++) + ". " + conf);
		
		while (true) {
		    input = keyboard.nextInt() -1;
		    if (input >= 0 && input < possibilities.size())
			break;
		    System.out.println("Wrong input");
		}

		System.out.print("Human gives ");
		configuration = possibilities.get(input);
		System.out.println(configuration);
	    }
	    else {
		System.out.print("Computer gives ");
		configuration = configuration.getBestChild();
		System.out.println(configuration + " (turn(s) planned : " + Math.abs(configuration.value) + ")");
	    }
	}
	
		System.out.println("================================================");
	if(human)
		System.out.println("Human Won !");
	    else
		System.out.println("Computer Won !");
	keyboard.close();
    }    
    

    public static class Configuration {
	public int m;
	public int n;
	public int i;
	public int j;
	public Integer value = null;
	
	public Configuration(int m, int n, int i, int j) {
	    this.m = m;
	    this.n = n;
	    this.i = i;
	    this.j = j;
	    this.value = dyn_winRate(m, n, i, j);
	}

	public List<Configuration> getChildren() {
	    List<Configuration> children = new ArrayList<Configuration>();
	    //cut by the left
	    for (int k=1;k<=this.i;k++) {
		children.add(new Configuration(this.m-k, this.n, this.i-k, this.j));
	    }
	    
	    //cut by the up
	    for (int f=1;f<=this.j;f++) {
		children.add(new Configuration(this.m, this.n-f, this.i, this.j-f));
	    }
	    
	    //cut by the right
	    for (int q=1;q<this.m-this.i;q++) {
		children.add(new Configuration(this.m-q, this.n, this.i, this.j));
	    }
	    
	    //cut by the down
	    for (int u=1;u<this.n-this.j;u++) {
		children.add(new Configuration(this.m, this.n-u, this.i, this.j));
	    }

	    return children;
	}

	public Configuration getBestChild() {
	    List<Configuration> configurations = this.getChildren();
	    if(configurations.isEmpty())
		throw new IllegalArgumentException();

	    if(this.value < 0) {
		for(Configuration el : configurations)
		    if(el.value == -(this.value +1))
			return el;
	    } else {
		for(Configuration el : configurations)
		    if(el.value == -(this.value -1))
			return el;
	    }

	    return this;
	}

	public String toString() {
	return "" + this.m + " " + this.n + " " + this.i + " " + this.j;
	    
	}

	
    }
    
}

