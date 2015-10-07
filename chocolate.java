import java.util.ArrayList;
import java.util.List;

/**
 * Class Chocolate
 * @author COJEZ Arnaud, LEPRETRE Rémy
 *
 */


public class Main {
	public static void main(String[] args) {
		
		
	}
	
	public int valPos(List<Integer> list) {
	    int min, max = 0;
	    for (int i=0; i<list.size(); i++) {
		iElement = list.get(i);
		if (iElement < 0)
		    if (iElement > min)
			min = iElement;
		else if (iElement > max)
		    max = iElement;
	    }
	    if (min > 0)
		return -max - 1;
	    else if (min == 0)
		return 1;
	    else
		
	}
}
