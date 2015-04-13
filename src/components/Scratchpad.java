package components;


public class Scratchpad {

	public static void main(String[] args) {
		Question testquestion = new Question("Some question");
		testquestion.addAnswer("Answer 1");
		testquestion.addAnswer("Answer 2");
		System.out.println(testquestion.answers.size());

	}

}
