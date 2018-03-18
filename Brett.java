import java.util.ArrayList;
import java.util.Arrays;


/**
 * Represents the main grid
 */

public class Brett {

	///////      Parameters      //////

	static int radIboks;
	static int kolIboks;

	static int RAD ;
	static int KOLONNER;

	static Rad[]     rader;
	static Kolonne[] kolonner;
	static Boks[][]  bokser;
	static Rute[][]  brett;

	static ArrayList<Rute>  alleRuter = new ArrayList<>();
	static SudokuBeholder sudokuBeholder = new SudokuBeholder(KOLONNER);
	static Integer[] sudokuOriginalValues;
	
	
	///////      Constructor      //////
	
	/**
	 * Constructor, creates a new Rute[][]  with the number of elements specified
	 * For each value in the matrix values creates a new rute object with its value and position.
	 * The rute is assigned to its corresponding row, column and box
	 * @param rad, kolonner, posisjon
	 */
	Brett (int rad, int kolon, int[][] values){

		radIboks = rad;
		kolIboks = kolon;
		RAD = rad * kolon;
		KOLONNER = kolon* rad;
		

		new SudokuBeholder(KOLONNER);
		
		bokser = new Boks[kolIboks][radIboks];
		rader = new Rad[RAD];
		kolonner = new Kolonne[KOLONNER];
		brett = new Rute[RAD][KOLONNER];
		
		sudokuOriginalValues = new Integer[RAD*KOLONNER];		

		int w = 1;
		int k;
		int boksId = 0;
		int boksCounter = 0;

		// Initializes the rads ids to fill them after all the rute are created
		for (int radI = 0; radI < RAD; radI++){
			Rad newRad = new Rad(KOLONNER, radI);
			rader[radI] = newRad;
		}

		// Initializes the kolonne ids to fill them after all the rute are created
		for (int kolJ = 0; kolJ < KOLONNER; kolJ++){
			Kolonne newKol = new Kolonne(KOLONNER, kolJ);
			kolonner[kolJ] = newKol;
		}

		// Initializes the Boks ids to fill them after all the rute are created
		for(int i = 0; i < kolIboks; i++){
			for (int j = 0; j < radIboks; j++){
				Boks boks = new Boks(radIboks, kolIboks, boksCounter);
				bokser[i][j]= boks;
				boksCounter++;
			}
		}

		// For all the index in the sudoku creates a new rute object
		// and place it in the corresponding position of the line and main grid

		for (int i = 0; i  < values.length ; i++){
			Rad newRad = new Rad(KOLONNER, i);

			k = 1;
			if (i/radIboks == (double)i/radIboks & i/radIboks !=0 ){
				boksId = boksId + radIboks;
				w = 1;
			}

			for (int j = 0; j < values[i].length; j++){

				if (k > kolIboks){
					boksId = boksId +1;
					k = 1;
				}

				Rute rute = new Rute(values[i][j],i,j,boksId);

				rute.setRadNummer(i);
				rute.setKolonneNummer(j);
				rute.setBoksNummer(boksId);

				newRad.rad[j] = rute;
				brett[i][j] = rute;
				alleRuter.add(rute);
				sudokuOriginalValues[rute.getRuteId()-1] = rute.getVerdi();

				if(findBoks(boksId) != null){
					Boks boksFound = findBoks(boksId);
					boksFound.boksRuter[w-1][k-1] = rute;
				}

				k++;

				if (j == values[i].length - 1){boksId = boksId - radIboks + 1;}
			}
			w++;
			rader[i] = newRad;
		}

		// Updates the array with all the columns
		for (int j = 0; j < KOLONNER; j++){
			Kolonne newKolon = new Kolonne(KOLONNER, j);
			for (int i = 0; i  < values.length ; i++){
				newKolon.kolonne[i] = brett[i][j];
			}
			kolonner[j] = newKolon;
		}



		// Assigns each rute the corresponding rad
		for (int i = 0; i < RAD; i++ ){
			Rute[] ruteRad  = rader[i].getRad();
			for (Rute ruteInrad : ruteRad){
				ruteInrad.setRad(rader[i]);
			}
		}

		// Assigns each rute the corresponding kolonne
		for (int j = 0; j < KOLONNER; j++){
			Rute[] ruteKolonne = kolonner[j].getKolonne();
			for (Rute ruteInkol : ruteKolonne){
				ruteInkol.setKolonnne(kolonner[j]);
			}
		}


		// Assigns each rute the corresponding boks and fills the possible numbers to fill
		for (Rute[] ruteLine : brett){
			for (Rute ruteInLine : ruteLine){
				Boks boksContain = findBoks(ruteInLine.getBoksNr());
				ruteInLine.setBoks(boksContain);
			}
		}


		// Assigns each empty rute the corresponding next empty rute

		for (Rute rute : alleRuter){
			if(alleRuter.indexOf(rute) < alleRuter.size() -1){

					rute.setNextRute(alleRuter.get(alleRuter.indexOf(rute) + 1));

			}


		}
		// control methods
		//radToString();
		//kolonneToString();
		//bokserToString();
		//System.out.println(brettToString());

	}
	
	
	//////////////////////////////////////////////////
	//////////    Main  Algorithms         ///////////
	//////////////////////////////////////////////////
	
	
	public static void resetValues(){
		radIboks = 0;
		kolIboks = 0;
		RAD = 0;
		KOLONNER =0;
		alleRuter = new ArrayList<>();

		
	}
	
	/**
	 * Returns the rute with the id specified
	 * @param posisjon
	 * @return ruteSeeked
	 */
	public static Rute findRute(int id){

		for(Rute[]  ruteLine : brett){
			for(Rute ruteSeeked : ruteLine){
				if (ruteSeeked.getRuteId() == id){return ruteSeeked;}
			}
		}
		return null;
	}

	/**
	 * Returns the box with the id specified
	 * @param posisjon
	 * @return boksSeeked
	 */
	public static Boks findBoks(int posisjon){

		for(Boks[]  boksLine : bokser){
			for(Boks boksSeeked : boksLine){
				if (boksSeeked.getPosisjon() == posisjon){return boksSeeked;}
			}
		}
		return null;
	}
	
	/**
	 * Converts the main grid in an array of integers where each integer is the value 
	 * for the corresponding cell
	 */
	public static Integer[] brettToArray(){
	
		Integer[] brettArray = new Integer[RAD * KOLONNER];
		int i =0;
		for(Rute rute : alleRuter){
			brettArray[i] = rute.getVerdi();
			i++;
		}
		return brettArray;
	}


	//////////////////////////////////////////////////
	//////////  Get and Set methods        ///////////
	//////////////////////////////////////////////////

	public int getVerdi(int i, int j){
		return brett[i][j].getVerdi();
	}

	public Rad getRad(int i, int j){
		return brett[i][j].getRad();
	}

	public Kolonne getKolonne(int i, int j){
		return brett[i][j].getKolonne();
	}

	public Boks getboks(int i, int j){
		return brett[i][j].getBoks();
	}

	public int getKOLONNER() {
		return KOLONNER;
	}
	
	public static  Integer[] getSudokuOriginalValues() {
		return sudokuOriginalValues;
	}

	public static void addLosning(){
		sudokuBeholder.settInn(brettToArray());
	}
	
	/**
	 * Returs the array with all the solutions
	 * @return
	 */
	public  static Integer [][] getAlleLosninger(){
		return sudokuBeholder.getLosninger();
	}

	

	
	//////////////////////////////////////////////////
	//////////    toString Methods         ///////////
	//////////////////////////////////////////////////
	
	/**
	 * Prints all the lines of the main grid one by one
	 */
	public void radToString(){
		for (Rad rad : rader){
			System.out.println("rad " + rad.getPosisjon() + " : " + rad.returnVerdier());
		}
	}

	/**
	 * Prints all the columns of the main grid one by one
	 */
	public void kolonneToString(){
		for(Kolonne kolonne : kolonner){
			System.out.println("kol " + kolonne.getPosisjon() + " : " + kolonne.returnVerdier());
		}
	}

	/**
	 * Prints all the box of the main grid one by one
	 */
	public void bokserToString(){
		for(Boks[]  boksLine : bokser){
			for(Boks boksChecked: boksLine){
				System.out.println("Boks " + boksChecked.getPosisjon() + " : " + boksChecked.returnVerdier());
			}
		}
	}

	/**
	 * Returns all the values in the main grid as a string line per line
	 */

	public static  String brettToString(){

		String brettVerdier ="";
		for(int i = 0; i < RAD; i++){
			for(int j = 0; j < KOLONNER; j++ ){
				if(brett[i][j].getVerdi() == 0){brettVerdier +=". ";}
				else if(brett[i][j].getVerdi() == 10){brettVerdier +="A ";}
				else if(brett[i][j].getVerdi() == 11){brettVerdier +="B ";}
				else if(brett[i][j].getVerdi() == 12){brettVerdier +="C ";}
				else if(brett[i][j].getVerdi() == 13){brettVerdier +="D ";}
				else if(brett[i][j].getVerdi() == 14){brettVerdier +="E ";}
				else if(brett[i][j].getVerdi() == 15){brettVerdier +="F ";}
				else if(brett[i][j].getVerdi() == 16){brettVerdier +="G ";}

				else{brettVerdier += brett[i][j].getVerdi() + " ";
				}
			}
			brettVerdier += "//";
		}
		return brettVerdier;
	}
		

	/**
	 * @return all the solutions recorded in the solutions container
	 */
	public static String losningerToString(){
		String str ="";
		int i = 1;
		for (Integer[] enlosning : sudokuBeholder.getFylltLosninger()){
			if (enlosning != null){
				str += Integer.toString(i) + ": ";
				int j = 1;
				for (int number : enlosning){
					str += Integer.toString(number) + "  ";
					if(j == KOLONNER ){
						str += "// ";
						j = 0;
					}
					j++;
				}
				str += "\n";
				i++;
			}
			
		}
		return str;
	}



}
