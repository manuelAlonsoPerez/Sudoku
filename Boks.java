
/**
 * Creates an array of Rute corresponding to a box in the main grid
 */
public class Boks {

	///////      Parameters      //////

	private int radAnt;
	private int kolonAnt;
	private int posisjon;
	private static int id = -1;

	Rute[][] boksRuter;

	///////      Constructor      //////

	/**
	 * Constructor, creates a new Rute[][]  with the number of elements specified
	 * Assigns the boks a position to find it afterwards
	 * @param rad, kolonner, posisjon
	 */
	Boks(int rad, int kolonner, int posisjon ){
		id++;
		this.radAnt = rad;
		this.kolonAnt = kolonner;
		this.posisjon = posisjon;

		boksRuter = new Rute[radAnt][kolonAnt];

	}

	//////      Get and set methods      //////

	public int getId() {return posisjon;}

	public int getRad() {return radAnt;}

	public int getKolonner() {return kolonAnt;}

	public int getPosisjon() {return posisjon;}

	public Rute[][] getBoks() {return boksRuter;}

	
	///////      toString Methods       //////

	/**
	 * Returns the values of all the rute in the box as a string
	 * @return boxVerdier
	 */
	public String returnVerdier(){
		String boxVerdier ="";
		for (Rute[] ruteLine : boksRuter){
			for(Rute ruteSelect : ruteLine){
				int verdi = ruteSelect.getVerdi();
				if(verdi == 0 ){boxVerdier += ". ";}
				else{boxVerdier += verdi + " ";}
			}
		}
		return boxVerdier;
	}
}
