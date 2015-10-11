import java.util.ArrayList;
import java.util.List;

/**
 * Class Chocolate
 * @author COJEZ Arnaud, LEPRETRE Rémy
 *
 */


public class Main {
	public static void main(String[] args) {
	    List<Integer> list = new ArrayList<Integer>();
	    list.add(0);
	    System.out.println(valPreviousPos(list));
	    list.add(5);
	    System.out.println(valPreviousPos(list));
	    list.add(3);
	    System.out.println(valPreviousPos(list));
	    list.add(-4);
	    System.out.println(valPreviousPos(list));
	    list.add(-1);
	    System.out.println(valPreviousPos(list));
	}
	
	static int valPreviousPos(List<Integer> list) {
	    int min = 0;
	    int max = 0;
	    //Search the min and max of the list
	    for (int i=0; i<list.size(); i++) {
		int iElement = list.get(i);
		if (iElement < 0) {
		    if (min==0 || iElement > min) {
			min = iElement;
		    }
		}
		else if (iElement > max) {
		    max = iElement;
		}
	    }

	    //Return a different value depending the min and max values
	    if (max > 0 && min == 0) {
		return -max - 1;
	    }
	    else if (min == 0 && max == 0) {
		return 1;
	    }
	    else {
		return -min + 1;
	    }
	}
}
