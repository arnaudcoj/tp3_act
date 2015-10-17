import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class Chocolate
 * @author COJEZ Arnaud, LEPRETRE Rémy
 *
 */

public class Main {

    public static int[][][][] configurations = null;

    public static void main(String[] args) {
	System.out.println(dyn_winRate(3,2,2,0));
	/* 
	   q4
	  System.out.println(dyn_winRate(100,100,50,50));
	  System.out.println(dyn_winRate(100,100,48,52));
	
	*/
	
	/*
	  q5
	*/

	LinkedList<String> list = new LinkedList<String>();
	for(int i = 126; i >= 0; i--)
	    for(int j = 126; j >= 0; j--) {
		int res = dyn_winRate(127,127,i,j);
		if(res == 127) {
		    System.out.print("=>>>>>");
		    list.add("conf: 127, 127, " + i + ", " + j + " = " + res);
		}
		System.out.println("conf: 127, 127, " + i + ", " + j + " = " + res);
	    }

	System.out.println("results :");
	
	for(String str : list)
	    System.out.println(str);
	
	
	/*
	  System.out.println(winRate(1,1,1,1));
	  System.out.println(winRate(2,1,2,1));
	  System.out.println(winRate(1,2,1,2));
	  System.out.println(winRate(2,2,2,2));
	  System.out.println(winRate(3,2,3,2));
	  System.out.println(winRate(3,1,3,1));
	  System.out.println(winRate(3,2,3,2));*/
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
	if(configurations == null || configurations.length < width || configurations[0].length < height || configurations[0][0].length < widthSkull || configurations[0][0][0].length < heightSkull)
	    initConfigurationTable(width, height, widthSkull, heightSkull);
	
	List<Integer> chocolateBar = new ArrayList<Integer>(0);
	//if juste one square
	if ( width == 1 && height == 1) {
	    addConfiguration(width, height, widthSkull, heightSkull, 0);
	}
	
	else
	    //else calculate all the different possibilities of cutting
	    if(getConfiguration(width, height, widthSkull, heightSkull) < 0) {
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
	//	System.out.println("conf :" + width + "," + height + "," + widthSkull + "," + heightSkull + " = " + configurations[width-1][height-1][widthSkull][heightSkull]);
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
	configurations = new int[width][height][widthSkull + 1][heightSkull + 1];
	for(int i = 0; i < width; i++) {
	    // configurations[i] = new int[][][][](height);
	    for(int j = 0; j < height; j++) {
		//	configurations[i][j] = new int[][][][](widthSkull);
		for(int k = 0; k <= widthSkull; k++) {
		    //  configurations[i][j][k] = new int[][][][](heightSkull);
		    for(int l = 0; l <= heightSkull; l++) {
			//System.out.println("init : " + i + " " + j + " " + k + " " + l + " " );
			configurations[i][j][k][l] = -1;
		    }		    
		}
	    }
	}
	
    }

    //useless ?
    public static int encode(int m, int n, int i, int j) {
	if (m > 127 || n > 127 || i > 127 || j > 127)
	    throw new IllegalArgumentException("encode : parameters must not exceed 255");
	
	i = (i << 7);
	n = (n << 14);
        m = (m << 21);
	
	return m | n | i | j;
    }

    public static void addConfiguration(int m, int n, int i, int j, int result) {
	int newM = Integer.max(m,n);
	int newN = Integer.min(m,n);
	
	int tmpI = Integer.min(i, m - i - 1);
	int tmpJ = Integer.min(j, n - j - 1);
	
	int newI = Integer.max(tmpI, tmpJ);
	int newJ = Integer.min(tmpI, tmpJ);
	
	configurations[newM-1][newN-1][newI][newJ] = result;
    }
    
    public static int getConfiguration(int m, int n, int i, int j) {
	int newM = Integer.max(m,n);
	int newN = Integer.min(m,n);
	
	int tmpI = Integer.min(i, m - i - 1);
	int tmpJ = Integer.min(j, n - j - 1);
	
	int newI = Integer.max(tmpI, tmpJ);
	int newJ = Integer.min(tmpI, tmpJ);
	
	return configurations[newM -1][newN -1][newI][newJ];
    }
}


    
