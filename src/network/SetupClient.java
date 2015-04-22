package network;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import components.Player;
import components.Question;
import components.Quiz;


/**
 * 
 * @author Jamie
 *
 */
public class SetupClient {
	
	Player player;
	List<Quiz> quizList ;
	SetupInterface server;
	Quiz currentQuiz;  //Used when editing quiz
	Question currentQuestion; //Used when editing quiz
	
	public SetupClient(){
		player = null;
		quizList = null;
		currentQuiz = null;
		currentQuestion = null;
		server = null;
	}
	
	
	public static void main(String[] args) {
		SetupClient client = new SetupClient();
		client.launch();
	}
	
	public void launch(){
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "QuizServer";
			Registry registry = LocateRegistry.getRegistry(1099);
			this.server = (SetupInterface) registry.lookup(name);
			
		}catch (Exception e){
			System.err.println("SetupClient Excpetion");
			e.printStackTrace();
		}
	}
	//Player management
	/**Logs the player in if the ID and Name match to a corresponding
	 * ID and Name within the system. If successful, the player will
	 * be assigned to the this.player variable. This will return true if 
	 * successfully logged in. Be aware that nothing guarantees uniqueness
	 * of logged in players.
	 * 
	 * @param id player's ID
	 * @param name name of the player
	 * @return true if successfully logged in,false otherwise
	 */
	public boolean login(int id, String name){
		return false;
	}
	
	/**Creates a new player on the server to be used. The player will be
	 * assigned to the this.player variable.
	 * 
	 * @param name name of the player
	 * @return true if successfully logged in,false otherwise
	 */
	public boolean createPlayer(String name){
		return false;
	}

	//Quiz list  management
	/**Assigns the list of all quizzes accessible by the player to the 
	 * this.quizList() variable. This should be run whenever the quiz list
	 * is changed by the user.
	 * 
	 * @return
	 */
	public boolean assignQuizList(){
		return false;
	}
	
	/**Returns a String representation of the player's quiz list that will 
	 * show all main information, e.g.:
	 * 
	 * [ID = 0, Name = Quiz0, Status = Active]
	 * [ID = 1, Name = Quiz2, Status = Inactive]
	 * 
	 * @return String representation of the list
	 */
	public String getPrettyQuizList(){
		return null;
	}
	
	/**Returns a String representation of all games that the quiz with the
	 * chosen ID has had played or that are currently in progress. e.g.
	 * 
	 * Quiz: Quiz0
	 * [Player = Player1, Status = Active , Score = 0]
	 * [Player = Player2, Status = Completed, Score = 23]
	 * 
	 * @param quiz
	 * @return
	 */
	public String getPrettyGamesList(int id){
		return null;
	}
	
	//Edit/Create quiz
	
	/**Creates a new quiz and returns it's ID. Will also refresh the QuizList
	 * after creation. 
	 * 
	 * @param name Name of the quiz
	 * @return int Quiz ID
	 */
	public int createQuiz(String name){
		return 0;
	}
	
	/**Interface to allow the user to edit the quiz with the specific ID.
	 * Will assign the quiz to edit to the this.currentQuiz variable.
	 * 
	 * 
	 * @param id
	 * @return true if successful, false otherwise
	 */
	public boolean editQuiz(int id){
		return false;
	}
	
	/**Removes the question with given ID from the quiz currently being edited.
	 * 
	 * @param id ID of questionto be edited
	 * @return true if successful, false otherwise
	 */
	public boolean removeQuestion(int id){
		return false;
	}
	
	/**Adds a question with the name passed to the question being edited
	 * 
	 * @param name title of the question
	 * @return true if successful, false otherwise
	 */
	public Question addQuestion(String name){
		return null;
	}
	
	/**Activates the quiz with the ID given, if it is inactive
	 * 
	 * @param id ID of the quiz
	 * @return true if successful, false otherwise
	 */
	public boolean activateQuiz(int id){
		return false;
	}
	
	/**Completes the quiz with the given ID, if it is active
	 * 
	 * @param id
	 * @return true if successful, false otherwise
	 */
	public boolean completeQuiz(int id){
		return false;
	}
	
	/**Opens up the interface to edit the question with the given ID.
	 * Cannot complete if the correct answer is not set or there are fewer 
	 * than 2 answers.
	 * 
	 * @param id ID of the question
	 * @return true if successful, false otherwise
	 */
	public boolean editQuestion(int id){
		return false;
	}
	
	//Question editing methods
	/**Returns a String representing a pretty display of the question,
	 * as the player would see it, but with the correct answer displayed.
	 * 
	 * @return String representation of the question
	 */
	public String getPrettyQuestion(){
		return null;
	}
	
	/**Adds a possible answer to the question
	 * 
	 * @param answer the answer to add 
	 */
	public void addAnswer(String answer){
	}
	
	/**Changes an existing answer 
	 * 
	 * @param id ID of answer to change
	 * @param answer new answer
	 * @return true if successful, false otherwise
	 */
	public boolean changeAnswer(int id, String answer){
		return false;
	}
	
	
	/**Removes an answer from the current question. If the correct
	 * answer is removed, it will be reset to -1
	 * 
	 * @param id ID of answer to change
	 * @return true if successful, false otherwise
	 */
	public boolean removeAnswer(int id){
		return false;
	}
	
	/**Changes the correct answer of the current question
	 * 
	 * @param id ID of the new correct answer
	 * @return true if successful, false otherwise
	 */
	public boolean setCorrectAnswer(int id){
		return false;
	}
	
	/**Changes the question
	 * 
	 * @param name new question
	 */
	public void setQuestion(String name){

	}
	
	/**Swaps two answers in order. If one is the correct answer, it will 
	 * change the correct answer.
	 * 
	 * @param id1 answer to swap
	 * @param id2 answer to swap
	 * @return true if successful, false otherwise
	 */
	public boolean swapAnswer(int id1, int id2){
		return false;
	}
		
}
