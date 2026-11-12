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
 * Purpose: The responsibility of InvalidPlacementException is to be an exception for an invalid ship placement
 *
 * InvalidPlacementException is-an exception for invalid ship placement

 */
public class InvalidPlacementException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public InvalidPlacementException(String message) 
	{
		super(message);
	}
}
