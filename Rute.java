import java.util.ArrayList;

/**
 * Main class rute corresponding to a single tile of the grid.
 * Contains the value inside and the position in the board(column, row and box)
 * The are variables pointing to the corresponding Box, column, Row and the
 * corresponding next rute in the main board
 * @param verdi, rad, kolonne, boks
 */

public class Rute {

	///////      Parameters      //////
	
	private int ruteId;
	private boolean filledInStart;
	private int verdi;
	private int radNummer;
	private int kolonneNummer;
	private int boksNummer;
	
	static int id;

	Rad radAvrute;
	Kolonne kolonneAvrute;
	Boks boksAvrute;
	Rute nextRute;

	///////      Constructor      //////
	
	/**
	 * Constructor of the class
	 * @param verdi, rad, kolonne, boks
	 */
	Rute (int verdi, int rad, int kolonne, int boks){
		id++;
		ruteId = id;
		this.verdi = verdi;
		this.radNummer = rad;
		this.kolonneNummer = kolonne;
		this.boksNummer = boks;
		if(verdi==0){this.filledInStart=false;}
		if(verdi!=0){this.filledInStart = true;}
	}

	
	
	//////////////////////////////////////////////////
	//////////    Main  Algorithms         ///////////
	//////////////////////////////////////////////////
	
	/**
	 * Checks if the number intended to set in the rute is already in the Rad
	 * @param nummer
	 * @return boolean
	 */
	public boolean isInRad(int nummer){

		boolean isinrad = false;
		Rute[] thisRad = radAvrute.getRad();

		for(Rute ruteInThisrad : thisRad){
			if (ruteInThisrad.getVerdi() == nummer){isinrad = true;}
		}
		return isinrad;

	}

	/**
	 * Checks if the number intended to set in the rute is already in the Kolonne
	 * @param nummer
	 * @return boolean
	 */
	public boolean isInKolonne(int nummer){

		boolean isinkol = false;
		Rute[] thisKolonne = kolonneAvrute.getKolonne();

		for(Rute ruteInThisKolonne : thisKolonne){
			if (ruteInThisKolonne.getVerdi() == nummer){isinkol =  true;}
		}
		return isinkol;
	}

	/**
	 * Checks if the number intented to set in the rute is already in the Kolonne
	 * @param nummer
	 * @return boolean
	 */
	public boolean isInBoks(int nummer){

		boolean isinboks = false;
		Rute[][] thisBoks = boksAvrute.getBoks();

		for(Rute[] ruteLineInThisBoks : thisBoks){
			for(Rute ruteInThisRuteLine : ruteLineInThisBoks){
				if (ruteInThisRuteLine.getVerdi() == nummer){isinboks =  true;}
			}

		}
		return isinboks;
	}

	/**
	 * Find the associated objects to its position (column, row and box) and
	 * returns an array with all the possible values.
	 * @return tallArray
	 */
	public Integer[] finnAlleMuligeTall(){

		ArrayList<Integer> muligeTall = new ArrayList<>();

		if(this.verdi == 0){
			for(int i = 1 ; i <= Brett.kolonner.length; i++){
				if(!isInRad(i) & !isInBoks(i) & !isInKolonne(i)){muligeTall.add(i);}
			}
		} else{
			muligeTall.add(this.verdi);
		}

		Integer [] tallArray = muligeTall.toArray(new Integer[muligeTall.size()]);
		return tallArray;
	}


	/**
	 * Finds all the possible numbers for this rute and for each value sets it in
	 * and call recursive the next rute.
	 * If the rute is the last one stores the solution as a string in the solutions beholder
	 */
	public void fillUtDenneRuteOgResten(){

		if(nextRute == null){

			for (int i : finnAlleMuligeTall()){
				verdi = i;
				//System.out.println(Brett.brettToString());
				Brett.addLosning();
				if(!filledOriginally()){verdi = 0;}
				return;
			}
			return;
		}  else  {
			for (int i : finnAlleMuligeTall()){
				verdi = i;
				nextRute.fillUtDenneRuteOgResten();
				if(!filledOriginally()){verdi = 0;}
			}
		}
	}

	//////////////////////////////////////////////////
	//////////  Get and Set methods        ///////////
	//////////////////////////////////////////////////


	public int getRuteId(){
		return id;
	}
	
	public boolean filledOriginally(){
		return filledInStart;
	}

	public int getVerdi() {
		return verdi;
	}

	public void setVerdi(int verdi) {
		this.verdi = verdi;
	}
	
	public Rad getRad() {
		return radAvrute;
	}
	
	public void setRad(Rad rad){
		this.radAvrute = rad;
	}

	public void setRadNummer(int radnr) {
		this.radNummer = radnr;
	}

	
	public Kolonne  getKolonne() {
		return kolonneAvrute;
	}
	
	public void setKolonnne(Kolonne kol){
		this.kolonneAvrute = kol;
	}

	public void setKolonneNummer(int kolonnenr) {
		this.kolonneNummer = kolonnenr;
	}

	
	public int getBoksNr(){
		return boksNummer;
	}
	
	public void setBoks(Boks boks){
		this.boksAvrute = boks;
	}

	public Boks getBoks() {
		return boksAvrute;
	}

	public void setBoksNummer(int boks) {
		this.boksNummer = boks;
	}

	
	public Rute getNextRute() {
		return nextRute;
	}

	public void setNextRute(Rute nextRute) {
		this.nextRute = nextRute;
	}


	//////////////////////////////////////////////////
	//////////    toString Methods         ///////////
	//////////////////////////////////////////////////

	/**
	 * Converts the possible values that can be filled in as a string
	 * @return (string)tallArray
	 */
	public String alleMuligeToString(){
		String str = "";
		for (int nummer : finnAlleMuligeTall()){
			str += " " + nummer;
		}
		return str;
	}

	/**
	 * Returns all the info about the Rute as a string
	 */
	@Override
	public String toString() {
		return "Rute:  [id: " + ruteId + ", verdi=" + verdi + ", rad=" + radAvrute.getPosisjon() + 
				", kolonne=" + kolonneAvrute.getPosisjon()+ ", boks=" + boksAvrute.getPosisjon() +  "]";
	}



}
