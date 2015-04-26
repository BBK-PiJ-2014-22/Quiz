package components;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import network.Log;
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
public class GameImpl extends UnicastRemoteObject implements Game{
	
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
			throw new IllegalStateException("Quiz Inactive");
		this.player = player;
		this.quiz = quiz;
		this.answers = new ArrayList<Integer>();
		this.score = 0;
		this.completed = false;
		Log.log("Game Created:"+player.display()+" : "+quiz.display());
	}
	
	//Standard methods
	/**Displays a human readable string representation of the Game. To be used
	 * when the game object is called remotely.
	 */
	@Override
	public String display(){
		return "Game [player=" + player + ", quiz=" + quiz + ", answers="
				+ answers + ", score=" + score + ", completed=" + completed
				+ "]";
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
			Log.log(this.quiz.display()+":"+this.player.display()+"answered: Q"+(this.answers.size()-1)+":"+answer);
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
		Log.log(this.quiz.display()+":"+this.player.display()+" Scored:"+this.score);
	}
}

