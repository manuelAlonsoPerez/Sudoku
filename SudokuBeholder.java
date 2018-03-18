import java.util.ArrayList;

public class SudokuBeholder {

	///////      Parameters      //////
	
	static final int MAX_LOSNINGER = 2500;
	static int antallLosninger = 0;
	static Integer[][] losninger;
	static ArrayList<Integer[]> fylltLosninger;
	
	///////      Constructor      //////
	
	public SudokuBeholder(int lenght) {
		
		losninger = new Integer[MAX_LOSNINGER][lenght];
		fylltLosninger = new ArrayList<>();
	}

	//////      Get and set methods      //////
	
	public static Integer[][] getLosninger() {
		return losninger;
	}


	public static ArrayList<Integer[]> getFylltLosninger() {
		return fylltLosninger;
	}


	public static int getMaxLosninger() {
		return MAX_LOSNINGER;
	}


	public static int hentAntallLosninger() {
		return antallLosninger;
	}
	
	//////      Main  Methods     //////

	/**
	 * adds a solution in the arrayList containing all the solutions.
	 * If the array is full just counts a new solution was founded
	 * @param losning
	 */
	public void settInn(Integer[] losning){
	
		if(antallLosninger <= MAX_LOSNINGER){
			losninger[antallLosninger] = losning;
			fylltLosninger.add(losning);
		}
		antallLosninger++;
	}

	/**
	 * Returns a sotulion at a particular index
	 * @param index
	 * @return
	 */
	public Integer[] taUt(Integer index){
		return losninger[index];
	}
	
	/**
	 * Resets the instance variables
	 */
	public static void resetValues(){
		antallLosninger = 0;
		losninger = null;
		fylltLosninger = null;
	}



}
