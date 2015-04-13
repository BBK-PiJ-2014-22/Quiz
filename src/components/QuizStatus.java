package components;

/**Represents the three possible statuses of a quiz. 
 * 
 * @author Jamie
 *
 */
public enum QuizStatus {

	/**The base state of the quiz. A quiz can be edited whilst it is inactive.
	 * 
	 */
	INACTIVE,
	
	/**A live quiz. New games can be created whilst the quiz is active. No changes
	 * can be made to the quiz.
	 */
	
	ACTIVE,
	
	/**A finished quiz. No game can be created and the quiz cannot be edited after
	 * it has been completed.
	 */
	
	COMPLETED;
}
