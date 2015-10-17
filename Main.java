import java.util.ArrayList;
import java.util.List;

/**
 * Class Chocolate
 * @author COJEZ Arnaud, LEPRETRE Rémy
 *
 */

public class Main {

    public static int[][][][] configurations = null;

    public static void main(String[] args) {
	/*List<Integer> list = new ArrayList<Integer>();
	  list.add(5);
	  System.out.println(valPreviousPos(list));
	  list.add(3);
	  System.out.println(valPreviousPos(list));
	  list.add(0);
	  System.out.println(valPreviousPos(list));
	  list.add(-4);
	  System.out.println(valPreviousPos(list));
	  list.add(-1);
	  System.out.println(valPreviousPos(list));*/

	System.out.println(winRate(3,2,2,0, 0));


	/*
	  System.out.println(winRate(1,1,1,1));
	  System.out.println(winRate(2,1,2,1));
	  System.out.println(winRate(1,2,1,2));
	  System.out.println(winRate(2,2,2,2));
	  System.out.println(winRate(3,2,3,2));
	  System.out.println(winRate(3,1,3,1));
	  System.out.println(winRate(3,2,3,2));*/
    }

    
    
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

    public static int winRate(int width, int height, int widthSkull, int heightSkull, int profondeur) {
	if(configurations == null || configurations.length < width || configurations[0].length < height || configurations[0][0].length < widthSkull || configurations[0][0][0].length < heightSkull)
	    initConfigurationTable(width, height, widthSkull, heightSkull);
	
	List<Integer> chocolateBar = new ArrayList<Integer>(0);
	//if juste one square
	if ( width == 1 && height == 1) {
	    System.out.println("return 0");
	    return 0;
	}

	
	
	//else calculate all the different possibilities of cutting
	if(configurations[width -1][height -1][widthSkull][heightSkull] < 0) {
	    //cut by the left
	    for (int k=1;k<=widthSkull;k++) {
		System.out.println(profondeur + " 1chocolateBar.add(winRate(" + (width-k) + ", " + (height) + ", " + (widthSkull-k) + ", " + (heightSkull) + "));");
		chocolateBar.add(winRate(width-k, height, widthSkull-k, heightSkull, profondeur + 1));
		System.out.println("1 added");
	    }
	    //cut by the up
	    for (int f=1;f<=heightSkull;f++) {
		System.out.println(profondeur + " 2chocolateBar.add(winRate(" + (width) + ", " + (height-f) + ", " + widthSkull + ", " + (heightSkull-f) + "));");
		chocolateBar.add(winRate(width, height-f, widthSkull, heightSkull-f, profondeur + 1));
		System.out.println("2 added");
	    }
	    //cut by the right
	    for (int q=1;q<width-widthSkull/*-1*/;q++) {
		System.out.println(profondeur + " 3chocolateBar.add(winRate(" + (width-q) + ", " + (height) + ", " + widthSkull + ", " + (heightSkull) + "));");
		chocolateBar.add(winRate(width-q, height, widthSkull, heightSkull, profondeur + 1));
		System.out.println("3 added");
	    }
	    //cut by the down
	    for (int u=1;u<height-heightSkull/*-1*/;u++) {
		System.out.println(profondeur + " 4chocolateBar.add(winRate(" + (width) + ", " + (height-u) + ", " + widthSkull + ", " + (heightSkull) + "));");
		chocolateBar.add(winRate(width, height-u, widthSkull, heightSkull, profondeur + 1));
		System.out.println("4 added");
	    }

	    configurations[width-1][height-1][widthSkull][heightSkull] = valPreviousPos(chocolateBar);
	}

	System.out.println("" + configurations[width-1][height-1][widthSkull][heightSkull]);
	return configurations[width-1][height-1][widthSkull][heightSkull];
    }

    public static void initConfigurationTable(int width, int height, int widthSkull, int heightSkull) {
	configurations = new int[width][height][widthSkull + 1][heightSkull + 1];
	for(int i = 0; i < width; i++) {
	    // configurations[i] = new int[][][][](height);
	    for(int j = 0; j < height; j++) {
		//	configurations[i][j] = new int[][][][](widthSkull);
		for(int k = 0; k <= widthSkull; k++) {
		    //  configurations[i][j][k] = new int[][][][](heightSkull);
		    for(int l = 0; l <= heightSkull; l++) {
			//			System.out.println("init : " + i + " " + j + " " + k + " " + l + " " );
			configurations[i][j][k][l] = -1;
		    }		    
		}
	    }
	}
	
    }
    
}
