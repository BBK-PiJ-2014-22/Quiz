package components;

import java.util.ArrayList;
import java.util.List;

import components.QuizStatus;

/**The Game object represents in in progress game of a specific quiz. 
 * When it is not completed, the player will be able to answer the next question.
 * Once the last question is answered, it will be set to completed,  a score will be 
 * calculated and it will be closed. Games can only be created for active quizzes - 
 * inactive or completed quizzes cannot have games. If a game is set to completed mid quiz,
 * the game will be completed and the score set to 0. 
 * 
 * @author Jamie MacIver
 *
 */
public class Game {
	
	//Note: no normal setters for any fields
	private Player player;  		//Set only by constructor
	private Quiz quiz; 				//Set only by constructor
	private List<Integer> answers;	//Added to by answerQuestion()
	private int score;				//Set after answering final question by completeGame method
	private boolean completed;		//Set after answering final question by completeGame method
	
	//Constructors
	
	/**Constructs and initialises a new game object for a given quiz and player.
	 * Games can only be constructed if the Quiz' QuizStatus is Active. This
	 * constructor will throw an IllegalStateException if the quiz is in any other
	 * state.
	 * 
	 * @param player The player who is playing this game
	 * @param quiz The quiz that this game will draw questions from
	 * @throws IllegalStateException if the quiz is not active
	 */
	public Game(Player player, Quiz quiz){
		if (quiz.getStatus() != QuizStatus.ACTIVE)
			throw new IllegalStateException();
		this.player = player;
		this.quiz = quiz;
		this.answers = new ArrayList<Integer>();
		this.score = 0;
		this.completed = false;
	}
	
	
	//Standard methods
	@Override
	public String toString() {
		return "Game [player=" + player + ", quiz=" + quiz + ", answers="
				+ answers + ", score=" + score + ", completed=" + completed
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((quiz == null) ? 0 : quiz.hashCode());
		result = prime * result + score;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (completed != other.completed)
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (quiz == null) {
			if (other.quiz != null)
				return false;
		} else if (!quiz.equals(other.quiz))
			return false;
		if (score != other.score)
			return false;
		return true;
	}
	
	//Getters
	public Player getPlayer() {
		return player;
	}
	public Quiz getQuiz() {
		return quiz;
	}
	public List<Integer> getAnswers() {
		return answers;
	}
	public int getScore() {
		return score;
	}
	public boolean isCompleted() {
		return completed;
	}

	//Game methods
	/** Returns a string describing the next question to answer, with a list of possible
	 * answers. The number preceding each answer will reflect the answer needed in 
	 * answerQuestion().
	 * 
	 * @return a string displaying the question details
	 */
	public String getNextQuestion(){
		String result;
		if (this.quiz.getStatus() != QuizStatus.ACTIVE)
			result = "none";
		else
			result = this.quiz.getQuestion(answers.size()).toString();
		return result;
	}
	
	/**Records the answer to the current question. If the final question is answered,
	 * it completes the game, and saves the total score. It will return true/false
	 * if an answer has been recorded, REGARDLESS of whether it is the correct answer.
	 * 
	 * @param answer ID of the answer the player thinks is correct
	 * @return true if answered successfully, false otherwise (quiz or game complete)
	 */
	public boolean answerQuestion(int answer){
		if (this.isCompleted() || this.quiz.getStatus() != QuizStatus.ACTIVE)
			return false;
		Question question = this.quiz.getQuestions().get(this.answers.size());
		if (answer < question.getAnswers().size() && answer >= 0){
			this.answers.add(answer);
			if (question.getAnswers().size() == quiz.getQuestions().size()) //All answers have been recorded
				this.completeGame();
			return true;
		}else{
			return false;
		}
	}
	
	private void completeGame(){
		this.completed = true;
		this.score = 0;
		for (int i = 0 ; i < this.getAnswers().size() ; i++){
			if (this.getAnswers().get(i) == this.quiz.getQuestion(i).getCorrectAnswer())
				this.score += 1;
		}
	}
}
