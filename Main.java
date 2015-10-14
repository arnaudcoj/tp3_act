import java.util.ArrayList;
import java.util.List;

/**
 * Class Chocolate
 * @author COJEZ Arnaud, LEPRETRE Rémy
 *
 */


public class Main {
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

	System.out.println(winRate(1,1,1,1));
	System.out.println(winRate(2,1,2,1));
	System.out.println(winRate(1,2,1,2));
	System.out.println(winRate(2,2,2,2));
	System.out.println(winRate(3,2,3,2));
	System.out.println(winRate(3,1,3,1));
	System.out.println(winRate(10,7,7,3));
    }

    
    /** calculate the previous value of a position
     *@param list of the value to compare
     *@return int the previous value of the position
     */
    static int valPreviousPos(List<Integer> list) {
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

    /** calculte the winRate of a chocolateBar
     *@param width of the chocolateBar
     *@param height of the chocolateBar
     *@param widthSKull the width of the skull
     *@param heightSkull the height of the skull
     *@return int the winrate
     */
    static int winRate(int width, int height, int widthSkull, int heightSkull) {
	List<Integer> chocolateBar = new ArrayList<Integer>();
	//if juste one square
	if ( width == 1 && height == 1) {
	    return 0;
	}
	//else calculate all the different possibilities of cutting
	//cut by the left
	for (int k=1;k<widthSkull;k++) {
	    chocolateBar.add(winRate(width-k, height, widthSkull-k, heightSkull));
	}
	//cut by the up
	for (int f=1;f<heightSkull;f++) {
	    chocolateBar.add(winRate(width, height-f, widthSkull, heightSkull-f));
	}
	//cut by the right
	for (int q=1;q<=(width-widthSkull);q++) {
	    chocolateBar.add(winRate(width-q, height, widthSkull, heightSkull));
	}
	//cut by the down
	for (int u=1;u<=(height-heightSkull);u++) {
	    chocolateBar.add(winRate(width, height-u, widthSkull, heightSkull));
	}
	return valPreviousPos(chocolateBar);
    }
}
