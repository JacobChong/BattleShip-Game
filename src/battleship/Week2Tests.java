/**
* Lead Author(s): Jacob Chong
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
 * Purpose: The responsibility of Week2Tests is to run the basic checks for week 2
 */
public class Week2Tests {
    public static void main(String[] args) {
        try {
            Board board = new Board();
            Ship destroyer = new Ship("Destroyer", 2);

            check(board.getUntriedCells().size() == 100,
                    "New board has 100 untried cells");

            check(new Coordinate(9, 9).toString().equals("J10"),
                    "Last board position is J10");

            check(new Coordinate(2, 3).equals(
                    new Coordinate(2, 3)),
                    "Coordinates compare by position");

            // Horizontal placement at the bottom-right edge.
            board.placeShip(
                    destroyer, new Coordinate(9, 8), false);

            check(destroyer.occupies(new Coordinate(9, 9)),
                    "Horizontal ship reaches the board edge");

            // This placement would overlap the destroyer.
            Ship cruiser = new Ship("Cruiser", 3);
            boolean rejected = false;

            try {
                board.placeShip(
                        cruiser, new Coordinate(9, 7), false);
            } catch (InvalidPlacementException expected) {
                rejected = true;
            }

            check(rejected && !cruiser.isPlaced(),
                    "Overlap rejected without placing the ship");

            // This vertical placement would go off the board.
            rejected = false;

            try {
                board.placeShip(
                        cruiser, new Coordinate(8, 0), true);
            } catch (InvalidPlacementException expected) {
                rejected = true;
            }

            check(rejected && board.getShips().size() == 1,
                    "Off-board placement leaves board unchanged");

            // Legal vertical placement ends at row 9.
            board.placeShip(
                    cruiser, new Coordinate(7, 0), true);

            check(cruiser.occupies(new Coordinate(9, 0)),
                    "Vertical ship reaches the board edge");

            check(board.receiveAttack(new Coordinate(0, 0))
                    == ShotResult.MISS, "Water produces a miss");

            check(board.receiveAttack(new Coordinate(9, 8))
                    == ShotResult.HIT, "Ship produces a hit");

            rejected = false;

            try {
                board.receiveAttack(new Coordinate(9, 8));
            } catch (IllegalArgumentException expected) {
                rejected = true;
            }

            check(rejected && destroyer.getHitCount() == 1,
                    "Repeated attack does not add damage");

            check(board.receiveAttack(new Coordinate(9, 9))
                    == ShotResult.SUNK, "Final hit sinks destroyer");

            check(!board.allSunk(),
                    "Cruiser remains afloat");

            board.receiveAttack(new Coordinate(7, 0));
            board.receiveAttack(new Coordinate(8, 0));
            board.receiveAttack(new Coordinate(9, 0));

            check(board.allSunk(),
                    "All placed ships are now sunk");

            check(board.getUntriedCells().size() == 94,
                    "Six attacks leave 94 untried cells");

            // Check the complete standard fleet separately.
            Board fleetBoard = new Board();
            int[] lengths = {5, 4, 3, 3, 2};

            for (int index = 0; index < lengths.length; index++) {
                Ship ship = new Ship(
                        "Ship " + (index + 1), lengths[index]);

                fleetBoard.placeShip(
                        ship, new Coordinate(index, 0), false);
            }

            check(fleetBoard.isFleetReady(),
                    "Standard five-ship fleet is ready");

            System.out.println("\nAll basic Week 2 checks passed.");

        } catch (Exception problem) {
            System.err.println("TEST FAILED: "
                    + problem.getMessage());
            problem.printStackTrace();
        }
    }

    private static void check(
            boolean condition, String description) {

        if (!condition) {
            throw new IllegalStateException(description);
        }

        System.out.println("PASS: " + description);
    }
}