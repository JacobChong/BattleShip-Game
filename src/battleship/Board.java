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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Purpose: The responsibility of Board is to store ships and shot history
 * validate placement and resolve attacks
 */
public final class Board
{
	public static final int SIZE = 10;
	
	private static final int[] FLEET_LENGTHS = 
		{	5, 4, 3, 3, 2	};
	
	// A board has-many Ship objects supplied by the caller
	private final ArrayList<Ship> ships;
	
	private final ShotResult[][] shots;
	
	public Board() 
	{
		ships = new ArrayList<Ship>();
		shots = new ShotResult[SIZE][SIZE];
		
		for (int row = 0; row < SIZE; row++)
		{
			for (int col = 0; col < SIZE; col++)
			{
				shots[row][col] = ShotResult.UNTRIED;
			}
		}
	}
	
	/**
	 * Places a ship rightward or downward from start
	 * Adjacent ships are allowed, but overlap is not
	 * @throws InvalidPlacementException if placement is invalid
	 */
	public void placeShip(Ship ship, Coordinate start, boolean vertical) throws InvalidPlacementException
	{
		if (ship == null || start == null)
		{
			throw new InvalidPlacementException ("Select a ship and starting position.");
		}
		
		if (ship.isPlaced())
		{
			throw new InvalidPlacementException ("This ship has already been placed.");
		}
		
	     if (hasAnyShots())
	     {
	    	 throw new InvalidPlacementException ("Ships cannot be added after attacks begin.");
	     }
	     
	     int allowed = 0;
	     
	     for(int length : FLEET_LENGTHS) 
	     {
	    	 if (length == ship.getLength())
	    	 {
	    		 allowed++;
	    	 }
	     }
	     
	     int placed = 0;
	     
	     for(Ship existing : ships) 
	     {
	    	 if (existing.getLength() == ship.getLength())
	    	 {
	    		 placed++;
	    	 }
	     }
	     
	     if (placed >= allowed) {
	    	 throw new InvalidPlacementException
	    	 ("The fleet already has all ships of this length.");
	     }
	     
	     int endRow= start.getRow();
	     int endCol = start.getCol();
	     
	     if (vertical)
	     {
	    	 endRow += ship.getLength() - 1;
	     }
	     else
	     {
	    	 endCol += ship.getLength() - 1;
	     }
	     
	     if (endRow >= SIZE || endCol >= SIZE)
	     {
	    	 throw new InvalidPlacementException
	    	 ("The ship extends beyond the board.");
	     }
	     
	     Coordinate[] positions = new Coordinate[ship.getLength()];
	     
	     for (int offset = 0; offset < ship.getLength(); offset++)
	     {
	    	 int row = start.getRow();
	    	 int col = start.getCol();
	    	 
	    	 if (vertical)
	    	 {
	    		 row += offset;
	    	 }
	    	 else
	    	 {
	    		 col+= offset;
	    	 }
	    	 
	    	 Coordinate position = new Coordinate(row, col);
	    	 
	    	 if (findShip(position) != null)
	    	 {
	    		 throw new InvalidPlacementException 
	    		 ("The ship overlaps another ship at" + position + ".");
	    	 }
	    	 
	    	 positions[offset] = position;
	     }
	     
	     // Commit only after every position passes validation
	     ship.assignCells(positions);
	     ships.add(ship);
	}
	
	/**
	 * Resolves an attack on an untried position
	 * @throws IllegalArgumentExcetpion for null or repeated attacks
	 */
	public ShotResult receiveAttack(Coordinate position)
	{
		requirePosition(position);
		
		if (getShotResult(position) != ShotResult.UNTRIED)
		{
			throw new IllegalArgumentException 
			("This position has already been attacked.");
		}
		
		Ship ship = findShip(position);
		ShotResult result = ShotResult.MISS;
		
		if (ship != null)
		{
			ship.registerHit(position);
			
			if (ship.isSunk())
			{
				result = ShotResult.SUNK;
			}
			else
			{
				result = ShotResult.HIT;
			}
		}
		
		shots[position.getRow()][position.getCol()] = result;
		
		return result;
	}
	
	public ShotResult getShotResult (Coordinate position)
	{
		requirePosition(position);
		
		return shots[position.getRow()] [position.getCol()];
	}
	
	/**
	 * Returns a read-only copy of the fleet list
	 * @return copy of fleet list
	 */
	public List<Ship> getShips()
	{
		return Collections.unmodifiableList(new ArrayList<Ship>(ships));
	}
	
	public boolean isFleetReady()
	{
		return ships.size() == FLEET_LENGTHS.length;
	}
	
	/**
	 * Reports whether every placed ship is sunk
	 * An empty board is not considered defeat
	 */
	public boolean allSunk()
	{
		if (ships.isEmpty())
		{
			return false;
		}
		for (Ship ship : ships)
		{
			if (!ship.isSunk()) 
			{
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Returns a new list of all untried positions
	 * @return updated list
	 */
	public ArrayList<Coordinate> getUntriedCells()
	{
		ArrayList<Coordinate> available = new ArrayList<Coordinate>();
		
		for (int row = 0; row < SIZE; row++)
		{
			for (int col = 0; col < SIZE; col++)
			{
				if (shots[row][col] == ShotResult.UNTRIED)
				{
					available.add(new Coordinate(row, col));
				}
			}
		}
		
		return available;
	}
	
	private Ship findShip(Coordinate position)
	{
		for (Ship ship : ships)
		{
			if (ship.occupies(position))
			{
				return ship;
			}
		}
		
		return null;
	}
	
	private boolean hasAnyShots()
	{
		for (int row = 0; row < SIZE; row++)
		{
			for (int col = 0; col < SIZE; col++)
			{
				if (shots[row][col] != ShotResult.UNTRIED)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void requirePosition(Coordinate position)
	{
		if (position == null)
		{
			throw new IllegalArgumentException
			("Choose a position on the board");
		}
	}
}
