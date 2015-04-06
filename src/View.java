import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * View class handles the graphical user interface for Minesweeper
 *
 * @author Michael Ramos
 * @version 1.0
 */
@SuppressWarnings("serial")
public class View extends JFrame
{	
	
	private int numMines;
	private int winCon = 0;
	private static final int frameHeight = 1000;
	private static final int frameWidth = 1000;
	private final int rows = numRows();
	private final int columns = numColumns();
	public int level = setLevel();
	public double MINEPERCENTAGE;
	public int numLives;
	public int numCount = 0;
	public int livesLeft = numLives - numCount;
	private Cell[][] button;
	private JButton resetButton;
	private JButton undoButton;
	private JButton redoButton;
	private JPanel playArea;
	private JPanel controls;
	private JLabel explain2;
	
	/**
	 * View.view lays out grid of buttons
	 * 
	 */
	public View()
	{
		this.setLayout(new BorderLayout());
		button = new Cell[rows][columns];
		playArea = new JPanel();
		playArea.setSize(frameWidth, frameHeight);
		this.setLocation(50, 50);
		playArea.setLayout(new GridLayout(rows, columns));
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				button[i][j] = new Cell();
				button[i][j].addActionListener( new changeButtonHandler() );
				button[i][j].addMouseListener( new handleRight() );
				playArea.add(button[i][j]);
				
			}
		}
		setMines();
		updateMineCounts();
		controls = new JPanel();
		controls.setLayout(new FlowLayout());
		undoButton = new JButton("Undo");
		undoButton.addActionListener( new undoButtun() );
		controls.add(undoButton);
		redoButton = new JButton("Redo");
		redoButton.addActionListener( new redoButton() );
		controls.add(redoButton);
		resetButton = new JButton("New Game");
		resetButton.addActionListener(  new resetButtonHandler() );
		controls.add(resetButton);
		explain2 = new JLabel("Lives: " + livesLeft);
		controls.add(explain2);	
		this.add(playArea, BorderLayout.CENTER);
		this.add(controls, BorderLayout.PAGE_END);
		this.setSize(frameWidth, frameHeight);
		this.setResizable(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Sets Level
	 * (Uses JOptionPane)
	 */
	private int setLevel()
	{	
        String bigList[] = new String[4];
	    for (int i = 1; i < bigList.length; i++) {
	      bigList[i] = Integer.toString(i);
	    }
	     Object foo = JOptionPane.showInputDialog(playArea, "Select Level: 1-Easy 2-Medium 3-Hard", "Input",
			 JOptionPane.QUESTION_MESSAGE, null, bigList, "Titan");
	     if(foo == null){
		    	System.exit(0);
		    }
	     int val = Integer.parseInt((String) foo);
	   
	
		    if(val == 1){
		    	numLives = 5;
		    	MINEPERCENTAGE = 0.156;
		    }
		    else if(val == 2){
		    	numLives = 3;
		    	MINEPERCENTAGE = 0.2;	
		    }
		    else if(val == 3){
		    	numLives = 1;
		    	MINEPERCENTAGE = 0.25;	
		    }
	    
	return val;
          		
	}
	
	/**
	 * Sets number of rows used
	 * (Uses JOptionPane)
	 */
	private int numRows()
	{	
		
        String bigList[] = new String[101];
	    for (int i = 4; i < bigList.length; i++) {
	      bigList[i] = Integer.toString(i);
	    }
	    Object foo = JOptionPane.showInputDialog(playArea, "Pick Rows", "Input", 
	    		JOptionPane.QUESTION_MESSAGE, null, bigList, "Titan");
		if(foo == null){
	    	System.exit(0);//system exits if cancel button pushed
	    }
		int val = Integer.parseInt((String) foo);
		
	return val;      		
	}
	
	/**
	 * Sets number of columns used
	 * (Uses JOptionPane)
	 */
	private int numColumns()
	{
		
	       String bigList[] = new String[101];
		    for (int i = 4; i < bigList.length; i++) {
		      bigList[i] = Integer.toString(i);
		    }
		    Object foo = JOptionPane.showInputDialog(playArea, "Pick Columns", "Input", 
		    		JOptionPane.QUESTION_MESSAGE, null, bigList, "Titan");
			if(foo == null){
		    	System.exit(0);//system exits if cancel button pushed
		    }
			int val = Integer.parseInt((String) foo);
			
		return val; 
	}
	
	/**
	 * Set the mines in the grid (note the private helper method)
	 *
	 */
	private void setMines()
	{
		Random rand = new Random();
		numMines = (int)(rows * columns * MINEPERCENTAGE);
		int count = 0;
		int x, y;
				
		while (count < numMines)
		{
			x = rand.nextInt(rows);
			y = rand.nextInt(columns);
			
			if (button[x][y].mine == false)
			{
				button[x][y].setMine();
				count++;
			}
		}
	}	
	
	/**
	 * update the mine counts in the grid based upon the placement of mines
	 * (note the private helper method)
	 */
	private void updateMineCounts()
	{
		int mc;
		
		for (int i=0; i<rows; i++) 
		{
			for (int j=0; j<columns; j++) 
			{
				if (button[i][j].mine == true)
					button[i][j].setMineCount(0);
				else
				{
					mc = 0;
					//count the number of neighbors that are mines
					for (int x=-1; x<2; x++) 
					{
						for (int y=-1; y<2; y++) 
						{
							if (valid(i,j,x,y))
							{
								if (button[i+x][j+y].isMine())
									mc++;
							}
						}
					}
					button[i][j].setMineCount(mc);
				}
			}
		}
	}
	
	/**
	 * private helper method to determine if a given position is valid
	 * @param i is the base column coordinate
	 * @param j is the base row coordinate
	 * @param x is the column offset
	 * @param y is the row offset
	 */
	private boolean valid(int i, int j, int x, int y)
	{
		boolean result = true;
		if ((x==0) && (y==0))
			result = false;
		else if (((i+x)<0) || ((i+x)>=rows))
			result = false;
		else if (((j+y)<0) || ((j+y)>=columns))
			result = false;
		
		return result;
	}
	
	/**
	 * Returns a boolean indicating whether a mine has been hit
	 *
	 * @param x the x coordinate of the given cell
	 * @param y the y coordinate of the given cell
	 * @return boolean indicating whether a mine has been hit
	 */
	protected boolean checkMine(int row, int col)
	{
		button[row][col].reveal();
		return button[row][col].isMine();
	}
	
	/**
	 * Returns the number of cells that are not mines
	 *
	 * @return int number of cells that are not mines
	 */
	protected int cellCount()
	{
		return ((rows * columns) - numMines);
	}
	
	
	/**
	 * Stacks are created here (within View class)
	 */
	@SuppressWarnings("rawtypes")
	ArrayStack stack = new ArrayStack();
	@SuppressWarnings("rawtypes")
	ArrayStack redoStack = new ArrayStack();
	
	/** 
	 * Initial Action Event (non-right click)
	 * 
	 */
	private class changeButtonHandler implements ActionListener
	{
		
		
		/**
		 * Action performed after button is clicked
		 * 
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e)
		{
			
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++)
				{
					if (button[i][j] == e.getSource())
					{	
						
						stack.push(i);
						stack.push(j);
						button[i][j].setClicked();
						if(button[i][j].mine==true){	
							button[i][j].setText("*");
							button[i][j].setEnabled(false);
							numCount++;
							calculate();
							isLoss();
								if(numCount == numLives){
									for (int x = 0; x < rows; x++)
									{
										for (int y = 0; y < columns; y++)
										{
											button[x][y].setEnabled(false);
											
										}
									}
								}
						}
						else if(button[i][j].mine==false){
							int echo = button[i][j].getMineCount();
							button[i][j].setText(Integer.toString(echo));
							button[i][j].setEnabled(false);
						}	
					}
					
				}
			}	
		}	
	}
	
	/**
	 * Class that handles Mouse Listener
	 */
	private class handleRight implements MouseListener {
		
		/**
		 * Method that handles right-click
		 */
		@SuppressWarnings("unchecked")
		public void mouseClicked(MouseEvent e)
		{
			if (SwingUtilities.isRightMouseButton(e) || e.isControlDown())  	{
				for (int i = 0; i < rows; i++)
				{
					for (int j = 0; j < columns; j++)
					{
						if (button[i][j] == e.getSource())
						{	
							stack.push(i);
							stack.push(j);
							button[i][j].setText("F");
							button[i][j].setBackground(Color.white);
						if (button[i][j].isMine()==true)
						{	
							winCon++;
							if(winCon == numMines) {
									
									updateWin();
									for (int a = 0; a < rows; a++)
									{
										for (int b = 0; b < columns; b++)
										{
											button[a][b].setEnabled(false);
											button[a][b].setBackground(Color.cyan);
											button[a][b].setBorderPainted(false);
										}
									}
							}
						}
					}
					}
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
	}
	/**
	 * undoButton class uses Stack to control "undos"
	 * @author michaelramos
	 *
	 */
	private class undoButtun implements ActionListener{
		
		/**
		 * ActionListener for undo
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e)
		{
			
			if(stack.isEmpty()){
				JOptionPane.showMessageDialog(null, "No previous move!");
			}
			else{
				int y = (Integer) stack.pop();
				int x = (Integer) stack.pop();
				redoStack.push(x);
				redoStack.push(y);
				button[x][y].setText("");
				button[x][y].setEnabled(true);
				button[x][y].setBorderPainted(true);
				button[x][y].setBackground(Color.white);
				if(button[x][y].mine==true){
					--numCount;
					for (int i = 0; i < rows; i++)
					{
						for (int j = 0; j < columns; j++)
						{
							if(button[i][j].clicked==false){
							button[i][j].setEnabled(true);
							}
						}
					}
				}
			}
			
				
			
	
		}//ends method
		
	}//end undo class
	
	/**
	 * redoButton class uses Stack to control "redos"
	 * @author michaelramos
	 *
	 */
	private class redoButton implements ActionListener{
		
		/**
		 * ActionListener for redo button
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e)
		{
			try{
			int j = (Integer) redoStack.pop();
			int i = (Integer) redoStack.pop();
			stack.push(i);
			stack.push(j);
			button[i][j].setClicked();
			if(button[i][j].mine==true){	
				button[i][j].setText("*");
				button[i][j].setEnabled(false);
				numCount++;
					if(numCount == numLives){
						for (int x = 0; x < rows; x++)
						{
							for (int y = 0; y < columns; y++)
							{
								button[x][y].setEnabled(false);
								controls.add(explain2);	
							}
						}
					}
				}
				else if(button[i][j].mine==false){
					int echo = button[i][j].getMineCount();
					button[i][j].setText(Integer.toString(echo));
					button[i][j].setEnabled(false);
				}
			}
			catch(Exception q){
				JOptionPane.showMessageDialog(null, "No future move!");
			}
				
		}
		
	}
	
	/**
	 * 
	 *Restarts entire game
	 * 
	 */
	private class resetButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			dispose ();
            GameDriver.main (null);
		}
	}
	
	/**
	 * Method that determines how many lives are left
	 */
	private void calculate(){
		   livesLeft--;
		   this.explain2.setText("Lives " + livesLeft);
		} 
	
	/**
	 * Method that determines what happens for a win
	 */
	private void updateWin(){
		redoButton.setEnabled(false);
		undoButton.setEnabled(false);
		   this.explain2.setText("Winner!");  
		} 
	
	/**
	 * Method that determines what happens for a loss
	 */
	private void isLoss(){
		if(livesLeft==0){
			this.explain2.setText("You Lose - All lives used up"); 
			redoButton.setEnabled(false);
			undoButton.setEnabled(false);
			
		}
		
	}
	
}