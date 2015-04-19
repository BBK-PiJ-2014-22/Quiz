package components;

import tests.GameTest;


public class Scratchpad {

	public static void main(String[] args) {
	
		Player player = new Player(0, "Player 0");
		Quiz quiz = new Quiz(0, "Quiz 0", new Player(1, "Quiz Master 0"));
		for (int i = 0 ; i < 4 ; i++)
			quiz.addQuestion(GameTest.createQuestion(i));
		
		quiz.activate();
		GameImpl game = new GameImpl(player, quiz);
		
		for (int i = 0; i < 5 ; i++){
			game.answerQuestion(0);
			System.out.println(game.isCompleted());
		}
	}
}
