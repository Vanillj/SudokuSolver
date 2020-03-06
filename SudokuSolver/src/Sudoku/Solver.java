package Sudoku;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Solver {

	/**
	 * Lösningen av sudokut
	 * @param s
	 * @return
	 */
	public static boolean solve(Sudoku s) {

		// går igenom hela från 0 till 9
		boolean firsttest = true;
		for (int i = 1; i <= 9; i++) {
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					if (s.checkAll(i, col, row) && firsttest) {
						firsttest = true;
					} else {
						firsttest = false;
					}
				}
			}
		}

		if (firsttest) {
			return solveRec(s, 0, 0);
		} else {
			System.out.println("saknas lösning");
			return false;
		}

	}
	
	/**
	 * vår Rekursiv algoritm
	 * @param s
	 * @param row
	 * @param col
	 * @return
	 */
	private static boolean solveRec(Sudoku s, int row, int col) {
		int staticrow = row;
		int staticcol = col;
		
		if (s.getValuexy(row, col) == -1) {
			for (int value = 1; value <= 9; value++) {
				
				s.setValuexy(row, col, value);
				
				if (s.checkAll(value, col, row)) {
					
					if (row == 8 && col == 8) {
						return true;
					}

					if (col == 8) {
						col = -1;
						row++;
					}
					
					col++;
					
					if(solveRec(s, row, col)) {
						return true;
					}else {
						s.resetValuexy(staticrow, staticcol);
						row = staticrow;
						col = staticcol;
					}
				}
				s.resetValuexy(staticrow, staticcol);

			}
			return false;
		}else {
			
			if (row == 8 && col == 8) {
				return true;
			}

			if (col == 8) {
				if (row < 8) {
					col = -1;
					row++;
				}
			}
			col++;
			if(solveRec(s, row, col)) {
				return true;
			}else {
				row = staticrow;
				col = staticcol;
			}
		}

		return false;
	}

	/**
	 * skapar vårt sudoku från gui
	 * @param panel
	 * @param s
	 */
  public static void createSudoku(JPanel panel, Sudoku s) {
	  int x = 0;
	  int y = 0;
		for(Component comp : panel.getComponents()) {
			if(comp instanceof JTextField) {
				
				JTextField textf = (JTextField)comp;
				
				if(x % 9 == 0 && x != 0) {
					y++;
					x = 0;
				}
				if(y % 9 == 0 && y != 0) {
					y = 0;
				}
				//System.out.println(x + " " + y);
				int i = -1;
				
				try {
					i = Integer.parseInt(textf.getText());
				} catch(Exception e) {
					if(i != -1)
						System.out.println("One or more values are invalid, they will be ignored.");
				}
				
				s.setValuexy(y, x, i);

				x++;
			}
		}
		
		//löser sudokut
		if(Solver.solve(s)) {
			//skruver ut det i rutorna
			Solver.matrixtopanels(panel, s);
			s.print();
		}
	}
  
  /**
   * sätter in det lösta sudokut i gui
   * @param panel
   * @param s
   */
	private static void matrixtopanels(JPanel panel, Sudoku s) {
		int x = 0;
		int y = 0;
		for(Component comp : panel.getComponents()) {
			if(comp instanceof JTextField) {
				
				JTextField textf = (JTextField)comp;
				
				if(x % 9 == 0 && x != 0) {
					y++;
					x = 0;
				}
				if(y % 9 == 0 && y != 0) {
					y = 0;
				}
				textf.setText(Integer.toString(s.getMatrix()[y][x]));

				x++;
			}
		}
		
	}

	/**
	 * tar bort alla värden i sudokut.
	 * @param sudPanel
	 */
	public static void clearSudoku(JPanel sudPanel) {
		for(Component comp : sudPanel.getComponents()) {
			if(comp instanceof JTextField) {
				JTextField textf = (JTextField)comp;
				textf.setText("");
			}
		}
	}



}
