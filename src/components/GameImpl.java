package components;

import java.rmi.RemoteException;
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
public class GameImpl implements Game{
	
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
	public GameImpl(Player player, Quiz quiz) throws RemoteException{
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
	public String toString(){
		return "Game [player=" + player + ", quiz=" + quiz + ", answers="
				+ answers + ", score=" + score + ", completed=" + completed
				+ "]";
	}
	@Override
	public int hashCode(){
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
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameImpl other = (GameImpl) obj;
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
	@Override
	public Player getPlayer() throws RemoteException{
		return player;
	}
	@Override
	public Quiz getQuiz() throws RemoteException{
		return quiz;
	}
	@Override
	public List<Integer> getAnswers() throws RemoteException{
		return answers;
	}
	@Override
	public int getScore() throws RemoteException{
		return score;
	}
	@Override
	public boolean isCompleted() throws RemoteException{
		return completed;
	}

	//Game methods
	/** Returns a string describing the next question to answer, with a list of possible
	 * answers. The number preceding each answer will reflect the answer needed in 
	 * answerQuestion().
	 * 
	 * @return a string displaying the question details
	 */
	@Override
	public String getNextQuestion() throws RemoteException{
		String result;
		if (this.quiz.getStatus() != QuizStatus.ACTIVE || this.completed)
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
	 * @throws RemoteException 
	 */
	@Override
	public boolean answerQuestion(int answer) throws RemoteException{
		if (this.isCompleted() || this.quiz.getStatus() != QuizStatus.ACTIVE)
			return false;
		Question question = this.quiz.getQuestionList().get(this.answers.size());
		if (answer < question.getAnswers().size() && answer >= 0){
			this.answers.add(answer);
			if (this.getAnswers().size() == quiz.getQuestionList().size()) //All answers have been recorded
				this.completeGame();
			return true;
		}else{
			return false;
		}
	}
	
	/**Completes the game and totals the score, setting the game to completed and the 
	 * score to whatever matches the answers
	 */
	private void completeGame() throws RemoteException{
		this.completed = true;
		this.score = 0;
		for (int i = 0 ; i < this.getAnswers().size() ; i++){
			if (this.getAnswers().get(i) == this.quiz.getQuestion(i).getCorrectAnswer())
				this.score += 1;
		}
	}
}

