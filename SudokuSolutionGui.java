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
 


public class SudokuSolutionGui {
	
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
	 * Constructor that sets the size of the pannels and creates them  acording to
	 * the sudoku loaded
	 * @param radIboks, kolIboks, values
	 */
	public SudokuSolutionGui(int radIboks, int kolIboks, Integer[] values){
		
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
	 * Resets the Panels and static variables to show the next solution
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
				
				if(verdiIarray  == 10){verdiTostring ="A ";}
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

		// Button that closes the GUI window
		JButton lukk = new JButton("Lukk");
		CloseListener listener1 = new CloseListener();
		lukk.addActionListener(listener1);
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridwidth = frameWidth/4;
		d.gridx = 0;
		d.gridy = frameHeight +1;
		panelButtons.add(lukk,d);
		
		// Button that shows the previous solution
		JButton previous = new JButton("Tidligere løsn.");
		PreviousListener listener2 = new PreviousListener();
		previous.addActionListener(listener2);
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridwidth = frameWidth/4;
		d.gridx = (int)  frameWidth/4;
		d.gridy = frameHeight + 1;
		panelButtons.add(previous,d);
		
		// Button that shows the next solution
		JButton neste = new JButton("Neste løsn.");
		NextListener listener3 = new NextListener();
		neste.addActionListener(listener3);
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridwidth = frameWidth/4;
		d.gridx = (int) 2*frameWidth/4;
		d.gridy = frameHeight +1;
		panelButtons.add(neste,d);
		
		// Button that loads a new  sudoku
		JButton newSudo = new JButton("Åpne fil");
		NyListener listener4 = new NyListener();
		newSudo.addActionListener(listener4);
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridwidth = frameWidth/4;
		d.gridx = (int) 3 * frameWidth/4;
		d.gridy = frameHeight + 1;
		panelButtons.add(newSudo,d);
	}
	
	
	/////////////////////////////////////////////////////
	//////////    Implementing listeners      ///////////
	/////////////////////////////////////////////////////
	
	/**
	 * Exits the program when the Close button is clicked
	 */
	class CloseListener  implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}		
	}
	
	/**
	 * If possible shows the next solution of the sudoku
	 * a message is shown in the GUI otherwise
	 */
	class NextListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!MainProgram.isLastSolution()){
				mainWindow.setVisible(false);
				MainProgram.ShowSolutionRunnable.showNext();
			}
			else{
				mainWindow.setTitle("Siste losninger funnet!!!");
			}	
		}
	}
	
	/**
	 * If possible shows the previous solution of the sudoku
	 * a message is shown in the GUI otherwise
	 */
	class PreviousListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!MainProgram.isFirstSolution()){
				mainWindow.setVisible(false);
				MainProgram.ShowSolutionRunnable.showPrevious();
			}
			else{
				mainWindow.setTitle("Den er det forste losningen funnet!!!");
			}			
			
		}	
		
	}
	
	/**
	 * Resets the static variables in use and runs again the Main program
	 * allowing to select and open a  new file 
	 */
	class NyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e){
			try{
			resetValues();
			MainProgram.resetValues();
			Runtime.getRuntime().exec("java MainProgram");
			System.exit(0);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}	
		
	}
	

}
