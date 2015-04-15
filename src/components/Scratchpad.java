package components;


public class Scratchpad {

	public static void main(String[] args) {
		String expected = "This is a question"
				+  "\n	0: Answer 0"
				+  "\n	1: Answer 1"
				+  "\n	2: Answer 2"
				+  "\n	3: Answer 3";
		
		
		Question question = new Question("This is a question");
	
		for (int i = 0; i < 4; i++){
			
			String answer = "Answer "+i;
			System.out.println(answer);
			question.addAnswer(answer);
		}
		
		
		
		System.out.println(expected);
		System.out.println(question);

	}

}
