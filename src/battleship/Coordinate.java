/**
* Lead Author(s): Jacob Chong

* <<Add additional lead authors here>>
*
* Other Contributors:
* Full name; student ID or contact information if not in class
* <<Add additional contributors (mentors, tutors, friends) here, with contact information>>
*
* References:
* Morelli, R., & Walde, R. (2016).
* Java, Java, Java: Object-Oriented Problem Solving
* https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
*
* <<Add more references here>>
*
* Version: 2026-11-11
*/
package battleship;

/**
 * Purpose: The responsibility of Coordinate is to represent an immutable position on the board
 *
 */
public final class Coordinate
{
	private final int row;
	private final int col;
	
	public Coordinate(int row, int col)
	{
		if (row < 0 || row >= 10 || col < 0 || col >= 10 )
		{
			throw new IllegalArgumentException ("Row and column must be between 0 and 9.");
		}
		
		this.row = row;
		this.col = col;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (this == other) 
		{
			return true;
		}
		
		if (other == null || getClass() != other.getClass())
		{
			return false;
		}
		
		Coordinate coordinate = (Coordinate) other;
		
		return row == coordinate.row && col == coordinate.col;
	}
	
	@Override
	public int hashCode()
	{
		return 31 * row + col;
	}
	
	@Override
	public String toString()
	{
		return "" + (char) ('A' + row) + (col + 1);
	}
}
