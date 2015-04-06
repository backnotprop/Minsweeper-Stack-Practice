import java.awt.Color;
import javax.swing.JButton;

/**
 * Cell class represents a single cell in the game Minesweeper, extends JButton
 *
 * @author Michael Ramos
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Cell extends JButton
{
	//private attributes to represent the neighboring mine count, the current
	//representation of the character, and whether or not it is a mine
	protected int mineCount;
	protected char visibleChar;
	protected boolean mine;
	protected boolean clicked;
		
	/**
	 * Constructor for the Cell class.  
	 */
	protected Cell()
	{
		this.mine = false;
		this.clicked = false;
		this.mineCount = 0;
		this.setOpaque(true);
		this.setBackground(Color.white);
		this.setSize(50, 50);
	}
	
	/**
	 * returns whether or not the cell is a mine
	 * @return a boolean representation of the cell
	 */
	protected boolean isMine()
	{
	    return mine;
	}
	
	/**
	 * Determines if button has been clicked
	 * @return a boolean representation of the cell
	 */
	protected boolean isClicked()
	{
	    return clicked;
	}
	
	/**
	 * returns whether or not the cell has been revealed
	 * @return a boolean whether or not the cell has been revealed
	 */
	protected boolean beenRevealed()
	{
	    return (!(visibleChar == '^'));
	}
	
	/**
	 * sets this Cell to be a mine
	 */
	protected void setMine()
	{
		mine = true;
	}
	
	/**
	 * Sets button "clicked"
	 */
	protected void setClicked()
	{
		clicked = true;
	}
	
	/**
	 * reveals this cell by changing the visibleChar
	 */
	protected void reveal()
	{
		if (this.isMine())
			visibleChar = '*';
		else 
			visibleChar = (mineCount + "").charAt(0);
	}
	
	/**
	 * returns the mine count
	 * @return an int representing the mine count
	 */
	protected int getMineCount()
	{
	    return mineCount;
	}
	
	/**
	 * sets the mine count for this Cell
	 *
	 * @param value the new value for this cell
	 */
	protected void setMineCount(int value)
	{
	    this.mineCount = value;
	}
		
}