
/**
 * Creates an array of Rute corresponding to a row in the main grid
 */

public class Rad {

	///////      Parameters      //////

	private int kolonAnt;
	private int posisjon;
	private static int id = 1;

	Rute[] rad;

	///////      Constructor      //////
	
	/**
	 * Constructor, creates a new Rute[] with the number of elements specified
	 * Assigns the row a position to find it afterwards
	 * @param kolonne, posisjon
	 */
	Rad(int kolonne, int posisjon){
		id++;
		this.kolonAnt = kolonne;
		this.posisjon = posisjon;
		rad = new Rute[kolonAnt];
	}

	//////      Get and set methods      //////

	public int getId() {return id;}

	public int getKolonne() {return kolonAnt;}

	public int getPosisjon() {return posisjon;}

	public Rute[] getRad() {return rad;}


	///////      toString Methods       //////
	
	/**
	 * Returns the values of all the rute in the row as a string
	 * @return radVerdier
	 */
	public String returnVerdier(){
		String radVerdier ="";

		for (Rute rute : rad){
			int verdi = rute.getVerdi();
			if(verdi == 0 ){radVerdier += ". ";}
			else{radVerdier += verdi + " ";}
		}
		return radVerdier;
	}
}
