
/**
 * Creates an array of Rute corresponding to a column in the main grid
 */

public class Kolonne {

	///////      Parameters      //////

	private int radAnt;
	private int posisjon;
	private static int id = 1;

	Rute[] kolonne;

	///////      Constructor      //////

	
	/**
	 * Constructor, creates a new Rute[] with a number of elements specified
	 * Assigns the colummn a position to find it afterwards
 	 * @param rad, posisjon
	 */
	Kolonne(int rad, int posisjon ){
		id++;
		this.radAnt = rad;
		this.posisjon = posisjon;

		kolonne = new Rute[radAnt];
	}

	//////      Get and set methods      //////

	public int getId() {return id;}

	public int getRad() {return radAnt;}

	public int getPosisjon() {return posisjon;}

	public Rute[] getKolonne() {return kolonne;}

	
	///////      toString Methods       //////

	/**
	 * Returns the values of all the rute in the column as a string
	 * @return kolonneVerdier
	 */
	public String returnVerdier(){
		String kolonneVerdier="";
		for (Rute kolon : kolonne){
			int verdi = kolon.getVerdi();

			if(verdi == 0 ){kolonneVerdier += ". ";}
			else{kolonneVerdier += verdi + " ";}
		}
		return kolonneVerdier;
	}

}
