import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;


/**
 * Class to show a non solved sudoku file, with the possibilities of 
 * loading a new Gui with the solutions or a new sudoku file
 */
public class SudokuLoaderGui {
	
	
	///////      Parameters      //////
	
	JFrame mainWindow = new JFrame();
		
	static JPanel mainGrid;
	static JPanel panelButtons;
	
	static int radIboks;
	static int kolIboks;
	static int lenghtGrid;
	
	static int frameWidth;
	static int frameHeight;
	
	///////      Constructor      //////
	
	/**
	 * Constructor that sets the size of the panels and creates them  according to
	 * the sudoku loaded
	 * @param radIboks, kolIboks, values
	 */
	public SudokuLoaderGui(int radIboks, int kolIboks, Integer[] values){
		
		resetValues();
		this.radIboks = radIboks;
		this.kolIboks = kolIboks;
		lenghtGrid = radIboks*kolIboks;

		frameWidth = 75 * radIboks * kolIboks;
		frameHeight = 75 * radIboks * kolIboks ;
		mainGrid.setSize(frameWidth, frameHeight);
		mainGrid.setLayout(new GridLayout(lenghtGrid,lenghtGrid));
		createSudokuGrid(radIboks, kolIboks,values);
		createJpanel();
	}
	
	
	//////////////////////////////////////////////////
	//////////    Main  Algorithms         ///////////
	//////////////////////////////////////////////////
	
	
	/**
	 * Shows the main window consisting of two panels: one with the sudoku grid
	 * and one with the interactive buttons  
	 * @param title
	 */
	public void showWindow(String title){
		
		mainWindow.setTitle(title);
		mainWindow.setSize(frameWidth, frameHeight);
		mainWindow.add(mainGrid, BorderLayout.CENTER);
		mainWindow.add(panelButtons, BorderLayout.PAGE_END);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setLocationRelativeTo(null);
		mainWindow.setVisible(true);	
	}
	
	
	
	/**
	 * Resets the Panels and static variables to load a new sudoku file
	 */
	public static void resetValues(){
		
		mainGrid = new JPanel();
		panelButtons = new JPanel();
		radIboks = 0;
		kolIboks = 0;
		lenghtGrid = 0;
		frameWidth = 0;
		frameHeight= 0;
		
	}
	
	/**
	 * Creates the panel containing the sudoku grid using a GridbagLayout
	 * @param radIboks, kolIBoks, values
	 */
	private void createSudokuGrid(int radIboks, int kolIBoks, Integer[] values){
		
		int Lenght = radIboks * kolIBoks;
		int boksCounter = 0;
		
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		GridBagConstraints c = new GridBagConstraints();

		// For each value in the matrix with all the values in the sudoku a
		// new button is created and added to the gridbaglayout panel
		for(int i = 0 ; i < Lenght ; i++){
						
			for(int j = 0; j < Lenght; j++){
				
				Integer verdiIarray  = values[boksCounter];
				String verdiTostring = "";

				if(verdiIarray  == 0){verdiTostring =" ";}
				else if(verdiIarray  == 10){verdiTostring ="A ";}
				else if(verdiIarray  == 11){verdiTostring ="B ";}
				else if(verdiIarray == 12){verdiTostring ="C ";}
				else if(verdiIarray == 13){verdiTostring ="D ";}
				else if(verdiIarray == 14){verdiTostring ="E ";}
				else if(verdiIarray == 15){verdiTostring ="F ";}
				else if(verdiIarray == 16){verdiTostring ="G ";}
				else{verdiTostring = Integer.toString(values[boksCounter]);}
				
				JButton newButton = new JButton(verdiTostring);
		        newButton.setPreferredSize(new Dimension(10,10));
		        
	        	Border basicBorder = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
		        		     
	        	// The borders delimiting a box are darkened
	        	
		        if(i % radIboks == 0)
		        {
		        	if(j % kolIBoks == 0){
		        		Border newBorder = BorderFactory.createMatteBorder(5,5,0,0, Color.BLACK);
			        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
			        	newButton.setBorder(compound);
		        	} else if (j == Lenght -1) {
			        	Border newBorder = BorderFactory.createMatteBorder(5,0,0,5, Color.BLACK);
			        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
			        	newButton.setBorder(compound);
		        	} else {
			        	Border newBorder = BorderFactory.createMatteBorder(5,0,0,0, Color.BLACK);
			        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
			        	newButton.setBorder(compound);}
		        }
		        
		        else if ( i == Lenght - 1)
		        {
		        	if (j % kolIBoks == 0){
			        	Border newBorder = BorderFactory.createMatteBorder(0,5,5,0, Color.BLACK);
			        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
			        	newButton.setBorder(compound);
		        	} else if (j == Lenght -1) {
				        Border newBorder = BorderFactory.createMatteBorder(0,0,5,5, Color.BLACK);
			        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
			        	newButton.setBorder(compound);
		        	} else {
				        Border newBorder = BorderFactory.createMatteBorder(0,0,5,0, Color.BLACK);
			        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
			        	newButton.setBorder(compound);}
		        }
		        
		        else if(j %kolIBoks == 0){
			        Border newBorder = BorderFactory.createMatteBorder(0,5,0,0, Color.BLACK);
		        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
		        	newButton.setBorder(compound);
		        }
		        
		        else if(j  == Lenght -1){
			        Border newBorder = BorderFactory.createMatteBorder(0,0,0,5, Color.BLACK);
		        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
		        	newButton.setBorder(compound);
		        }
		        
		        else{
			        newButton.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.BLACK));
			        Border newBorder = BorderFactory.createMatteBorder(0,0,0,0, Color.BLACK);
		        	Border compound  = BorderFactory.createCompoundBorder(newBorder, basicBorder);
		        	newButton.setBorder(compound);
		        }
		        
		        // parameter constrains to each of the buttons in the sudoku grid
				c.weightx = 1./lenghtGrid;
				c.weighty = 1./lenghtGrid;
				c.gridx = j;
				c.gridy = i;
				mainGrid.add(newButton,c);
				boksCounter++;
			}
		}
		
		
	}
	
	/**
	 * Creates a panel with the interactive buttons of the GUI
	 */
	public void createJpanel(){
		
		panelButtons = new JPanel();
		panelButtons.setSize(frameWidth, 25);
		
		GridBagConstraints d = new GridBagConstraints();

		// Button that loads the solutions
		JButton loadSol = new JButton("Vis løsninger");
		LoadListener listener1 = new LoadListener();
		loadSol.addActionListener(listener1);
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridwidth = frameWidth/2;
		d.gridx = 0;
		d.gridy = frameHeight +1;
		panelButtons.add(loadSol,d);
		
		// Button that loads a new sudoku file
		JButton lagreNy = new JButton("Åpne fil");
		NyListener listener2 = new NyListener();
		lagreNy.addActionListener(listener2);
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridwidth = frameWidth/2;
		d.gridx = (int)  frameWidth/2;
		d.gridy = frameHeight + 1;
		panelButtons.add(lagreNy,d);
		
		
	}
	
	
	/////////////////////////////////////////////////////
	//////////    Implementing listeners      ///////////
	/////////////////////////////////////////////////////
	
	/**
	 * Loads a new Gui with the solutions found
	 */
	class LoadListener  implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			MainProgram.SolverRunnable.notifyAndContinue();
		}		
	}
	
	/**
	 *loads a new sudoku
	 */
	class NyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			try
			{
				resetValues();
				MainProgram.resetValues();
				Runtime.getRuntime().exec("java MainProgram");
				System.exit(0);
			} catch (IOException exception) {
					exception.printStackTrace();}
		}
	}



}
