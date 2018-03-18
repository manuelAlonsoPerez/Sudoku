import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Two inner classes:  SolverRunnable which solves the sudoku and
 * ShowSolutionRunnable that shows the solutions found
 * A main method starts a threads for each one of the inner classes that are synchronized
 * @param args[] for the possible files to work with
 */
public class MainProgram {
	
	///////      Parameters      //////
	
	static String[] files;
	
	static Brett sudoku;
	
	static int kolIboks;
	static int radIboks;
	static int[][] values;
	
	static int losningVist = 0; 
	static boolean firstSolution = true;
	static boolean lastSolution = false;
	
	static boolean logoShown = false;
	static boolean solvingSudoku = true;
	static boolean canShowSols = false;
	static Lock solvingLock = new ReentrantLock();
	static Condition isStillsolving = solvingLock.newCondition();
	
	
	///////      Constructor      //////
	
	/**
	 * Main method that creates thow threads:
	 * One with the files(if specified) to solve and save the solution
	 * And another to show the solutions in a GUI
	 * @param args
	 */
	
	public static void main(String[] args){
		
		final SolverRunnable prog = new SolverRunnable(args);
		final ShowSolutionRunnable showing = new ShowSolutionRunnable();
		
		Thread t1 = new Thread(prog);
		Thread t2 = new Thread(showing);
		
		t1.start();
		t2.start();
	}
	
	/**
	 * to reset the values when a new sudoku is load from the gui 
	 */
	public static  void resetValues(){
		files = null;
		sudoku = null;
		kolIboks = 0;
		radIboks = 0;
		values = null;
		losningVist = 0;
		firstSolution = true;
		lastSolution = false;
		canShowSols = false;
		solvingSudoku = true;
		solvingLock = new ReentrantLock();
		isStillsolving = solvingLock.newCondition();
	}
	
	/**
	 * Checks if we have reached the last solution found
	 * @return lastSolution
	 */
	public static boolean isLastSolution() {
		return lastSolution;
	}

	

	/**
	 * Checks if we are in the first solution found
	 * @return firstSolution
	 */
	public static boolean isFirstSolution() {
		return firstSolution;
	}
	
	
	////////////////////////////////////////////////////////////////
	/////                 Sudoku Solver class                 //////
	////////////////////////////////////////////////////////////////
	
	/**
	 * Class that solves the sudoku with three options: to solve a sudoku  and show the solutions in a GUI looking in the system, 
	 * or specifying the file to solve. And finally to solve a sudoku specified and save the results in a new file specified.
	 * @param fileTosolve(args[0]) and fileTosave(arg[1]) or none
	 */
	static class SolverRunnable implements Runnable {
		
		/**
		 * Main constructor that includes the possible files to work with
		 * @param args
		 */
		public SolverRunnable(String[] args) {
			
			files = args;
			
		}

		
		/**
		 * When the thread starts solving the sudoku acquires the lock avoiding other methods to work,
		 * after solving notifies the other threads and unlock it
		 * If we specify one file the program will try to solve it and show the solutions in a GUI
		 * If we specify two files the program will try to solve the first file saving the results in the second one
		 * If we dont specify any file the program will open  a window with JFileChooser try to solve it and sho the
		 * solutions in a GUI
		 */
		public void run(){
			
			solvingLock.lock();
			
			try{
				if (!logoShown){
					LoadLogo logoInitial = new LoadLogo();
					Thread.sleep(3000);
					logoShown = true;
					logoInitial.closeLogo();}
			}catch (InterruptedException e) {}
			
			if (files.length == 1){
				try{
				System.out.println("\nLosninger funnet:\n" );
				solveAndPrint(files[0]);
				showUnsolved();
				while(!canShowSols){isStillsolving.await();}
				solvingLock.unlock();
				}catch (InterruptedException e) {}
				
			} else if (files.length == 2){
				System.out.println("\nLosninger skrevet ut inn fil " + files[1] + "\n");
				solveAndSave(files[0],files[1]);
				solvingLock.unlock();
				
			} else {
				try{
					JFileChooser chooser = new JFileChooser();
					chooser.setDialogTitle("Velge sudoku fil");
					if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
						File selectedFile = chooser.getSelectedFile();
						String fileName = selectedFile.getName();
						solveAndPrint(fileName);
						showUnsolved();
						while(!canShowSols){isStillsolving.await();}
						isStillsolving.notifyAll();
						solvingLock.unlock();}
				}catch (InterruptedException e) {}
				
			}
		}

		/**
		 * Reads the first two lines in the file corresponding to the number of rows and columns in a box
		 * of the main grid.
		 * Creates the matrix of the corresponding size that will hold all the values for the main grid
		 */
		public  void delInnRuter(String filnavn) throws IOException{

			File inputFile = new File(filnavn);
			Scanner in = new Scanner(inputFile);

			String line;

			for(int i = 0 ; i < 2; i++){
				line = in.nextLine();
				if(line.trim().length() < 2){
					if(radIboks == 0){ radIboks = Integer.parseInt(line.trim());}
					kolIboks = Integer.parseInt(line.trim());
				}
			}
			in.close();
			values = new int[radIboks * kolIboks][radIboks * kolIboks];

		}

		/**
		 * Reads the file, ignoring all the lines shorter than 2 characters
		 * for each line converts each character to an int and updates the matrix values.
		 * Finally creates a main grid Brett object with the information of the file
		 */
		public  void lesFil(String filnavn){

			try{

				delInnRuter(filnavn);
				String line;
				String[] lineSplit;
				int i = 0;
				File inputFile = new File(filnavn);
				Scanner in = new Scanner(inputFile);

				while(in.hasNextLine()){

					line = in.nextLine();

					if (line.length() > 2 & i < radIboks * kolIboks){
						lineSplit = line.split(" ");
						for (int j = 0; j < radIboks * kolIboks ; j++){
							if(lineSplit[j].trim().equals(".")){values[i][j] = 0;}
							else if(lineSplit[j].trim().equals("A")){values[i][j] = 10;}
							else if(lineSplit[j].trim().equals("B")){values[i][j] = 11;}
							else if(lineSplit[j].trim().equals("C")){values[i][j] = 12;}
							else if(lineSplit[j].trim().equals("D")){values[i][j] = 13;}
							else if(lineSplit[j].trim().equals("E")){values[i][j] = 14;}
							else if(lineSplit[j].trim().equals("F")){values[i][j] = 15;}
							else if(lineSplit[j].trim().equals("G")){values[i][j] = 16;}
							else{
								int value = Integer.parseInt(lineSplit[j]);
								values[i][j] = value;
							}
						}
						i++;
					}
				}
				sudoku = new Brett(radIboks, kolIboks, values);
				in.close();
			}
			catch (IOException  FileNotFoundException){
				System.out.println("\n Kune ikke Apne filen spesifisert\n Skjekk navn til input fil\n");
			}

		}

		/**
		*In case we have only one file specified we calculate the solutions and print them on the screen
		*/
		public   void solveAndPrint(String input){
			lesFil(input);
			sudoku.brett[0][0].fillUtDenneRuteOgResten();
			if (sudoku.sudokuBeholder.hentAntallLosninger() == 1){ lastSolution = true;}
		    skriveUtLosninger();
		    System.out.println("av en total av " + sudoku.sudokuBeholder.hentAntallLosninger() + " losninger\n");

		}

		/**
		*In case we have to files specified the first one is the file containing the sudoku to solve
		* and the second the file that will hold the solutions
		*/
		public  void solveAndSave(String input, String output){

			try{
				lesFil(input);
				sudoku.brett[0][0].fillUtDenneRuteOgResten();
				if (sudoku.sudokuBeholder.hentAntallLosninger() == 1){ lastSolution = true;}
				Integer[][] alleLosninger = sudoku.sudokuBeholder.getLosninger();
				File outputFile = new File(output);
				PrintWriter out = new PrintWriter(outputFile);

				for(Integer[] enLosning : alleLosninger){
					
					String str ="";
					int indexOfChar = 0;
					
					for (Integer number : enLosning){
						
						if(number  == 10){str +="A ";}
						else if(number  == 11){str +="B ";}
						else if(number == 12){str +="C ";}
						else if(number == 13){str +="D ";}
						else if(number == 14){str +="E ";}
						else if(number == 15){str +="F ";}
						else if(number == 16){str +="G ";}
						else{str += Integer.toString(number);}
						
						if (indexOfChar ==  radIboks * kolIboks -1) {
							str += "//";
							indexOfChar = -1;
						}
						indexOfChar++;
					}
					out.println(str);
				}
				out.close();
			}
			catch (IOException  FileNotFoundException){
				System.out.println("Kune ikke Apne filen");
			}
		}

		public void showUnsolved(){
			SudokuLoaderGui unsolvedGui = new SudokuLoaderGui(radIboks, kolIboks, sudoku.getSudokuOriginalValues());
			unsolvedGui.showWindow("Klikk for å vise løsninger");
		}
		
		public static void notifyAndContinue(){
			solvingLock.lock();
			solvingSudoku = false;
			canShowSols = false;
			isStillsolving.signalAll();
			solvingLock.unlock();
			
		}

		/**
		 * Prints put the solutions
		 */
		public  void skriveUtLosninger(){
				System.out.println(sudoku.losningerToString());
		}

	}
	
	
	
	////////////////////////////////////////////////////////////////
	/////                 GUI loader class                    //////
	////////////////////////////////////////////////////////////////
	
	/**
	 * Class thaw shows the solutions found in an independent thread
	 *
	 */
	static class ShowSolutionRunnable  implements Runnable{
		
		
		/**
		 * the method waits for the program to solve the sudoku, when its notified show the solution of the last index recorded
		 * beginning in 0
		 */
		public void run(){
			
			try{
				solvingLock.lock();
				while(solvingSudoku || canShowSols){isStillsolving.await();}
				show();
				solvingLock.unlock();
			}catch (InterruptedException e) {}
			
		}
		
		/**
		 * Shows the solution in the last index recorded
		 */
		public static void show(){
			
			if(losningVist < sudoku.sudokuBeholder.hentAntallLosninger() )
			{
				int i = losningVist +1;
				Integer[] oneSolution = sudoku.getAlleLosninger()[losningVist];
				SudokuSolutionGui oneWindow = new SudokuSolutionGui(radIboks, kolIboks, oneSolution);
				String title = "Løsning nummer " + i + " av " + sudoku.sudokuBeholder.hentAntallLosninger() + " løsninger"; 
				oneWindow.showWindow(title);
			}
			
		}
		
		/**
		 * Decreases the index of the last solution shown and shows it if possible,
		 * if we are in the first solution the state is recorded to avoid going to minus index
		 */
		public static void showPrevious(){		
			if (losningVist >= 0){
				losningVist--;
				show();
				if (losningVist == 0){firstSolution = true;}	
				lastSolution = false;
			}
		}
		
		/**
		 * Increases the index of the last solution shown and shows it if possible,
		 * if we are in the last solution the state is recorded to avoid going to outofbounds index
		 */
		public static void showNext(){
			
			if (losningVist < sudoku.sudokuBeholder.hentAntallLosninger() & !lastSolution ){
				firstSolution = false;
				losningVist++;
				show();
				if (losningVist == sudoku.sudokuBeholder.hentAntallLosninger() - 1){lastSolution=true;} 	
			}
		}
			
	}
	
	////////////////////////////////////////////////////////////////
	/////                 Initial logo class                  //////
	////////////////////////////////////////////////////////////////
	
	/**
	 * Shows the program image
	 */
	static class LoadLogo {
		
		static JFrame iniLogo = new JFrame();
		
	    LoadLogo() {
	    	try{
	        String fileName = "logo.jpg";
	        File file = new File(fileName);
	        BufferedImage image = ImageIO.read(file);
	        JLabel label = new JLabel(new ImageIcon(image));
	        
	        iniLogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        iniLogo.getContentPane().add(label);
	        iniLogo.pack();
	        iniLogo.setLocationRelativeTo(null);
	        iniLogo.setVisible(true);
	        } catch (IOException ex){System.out.println("Logo ikke funnet");}
	    }
	    
	    public static void closeLogo(){
	    	iniLogo.setVisible(false);
	    }
	}



}
