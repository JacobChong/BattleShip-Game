/**
* Lead Author(s):Jacob Chong
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

import java.util.HashSet;

/**
 * Purpose: The responsibility of Ship is to represent one ship and track its damage
 */
public final class Ship
{
	private final String name;
	private final int length;
	
	// A Ship has-many coordinates values for its positions
	private Coordinate[] cells;
	
	// A ship has-many Coordinate values for its hits
	private final HashSet<Coordinate> hits;
	
	public Ship(String name, int length)
	{
		if (name == null || name.trim().isEmpty())
		{
			throw new IllegalArgumentException("A ship must have a name.");
		}
		
		if (length < 2 || length > 5)
		{
			throw new IllegalArgumentException("Ship length must be between 2 and 5.");
		}
		
		this.name = name.trim();
		this.length = length;
		
		cells = new Coordinate[0];
		hits = new HashSet<Coordinate>();
	}
	
	public String getName() 
	{
		return name;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public boolean isPlaced()
	{
		return cells.length == length;
	}
	
	public Coordinate[] getCells() 
	{
		return cells.clone();
	}
	
	public int getHitCount()
	{
		return hits.size();
	}
	
	public boolean occupies(Coordinate position)
	{
		for ( Coordinate cell : cells) 
		{
			if (cell.equals(position))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isSunk() 
	{
		return isPlaced() && hits.size() == length;
	}
	
	/**
	 * Package access allows Board to assign positions
	 * The array is copied to protect the ships state
	 */
	void assignCells(Coordinate[] positions) {
	    if (isPlaced()) {
	        throw new IllegalArgumentException(
	                "This ship has already been placed.");
	    }

	    if (positions == null || positions.length != length) {
	        throw new IllegalArgumentException(
	                "Incorrect number of ship positions.");
	    }

	    cells = positions.clone();
	}
	
	/**
	 * Board calls this method when an attack hits
	 * HashSet prevents the same position counting twice
	 */
	void registerHit(Coordinate position) {
		if (!occupies(position)) {
			throw new IllegalArgumentException("That position is not on this ship");
		}
		
		hits.add(position);
	}
}
