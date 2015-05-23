package Model;

/**
 * Enum that represents state in which Field can be, how it is supposed to be shown on the game board.
 * @author Damian Mazurkiewicz
 */
public enum FieldOutlook {
	/**Field wasn't uncovered yet.*/
	COVERED,
	/**Field was uncovered and it proved to be a mine.*/
	MINE,
	/**Player put a flag on the field.*/
	FLAGGED,
	/**Field was uncovered and it has no adjacent mines.*/
	ZERO_ADJACENT,
	/**Field was uncovered and it has one adjacent mine.*/
	ONE_ADJACENT,
	/**Field was uncovered and it has two adjacent mines.*/
	TWO_ADJACENT,
	/**Field was uncovered and it has three adjacent mines.*/
	THREE_ADJACENT,
	/**Field was uncovered and it has four adjacent mines.*/
	FOUR_ADJACENT,
	/**Field was uncovered and it has five adjacent mines.*/
	FIVE_ADJACENT,
	/**Field was uncovered and it has six adjacent mines.*/
	SIX_ADJACENT,
	/**Field was uncovered and it has seven adjacent mines.*/
	SEVEN_ADJACENT,
	/**Field was uncovered and it has eight adjacent mines.*/
	EIGHT_ADJACENT
}
