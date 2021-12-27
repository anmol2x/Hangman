import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class hangman {

	public static void main(String[] args) {
		playGame();
		while (true){
			System.out.println("Press 1, if you want to play the game again.");
			Scanner scanner = new Scanner(System.in); 
		    char c = scanner.next().charAt(0); 
		    if (Character.compare('1', c) == 0){
		    	playGame();
		    }s
		    else{
		    	break;
		    }
		}
	}
	public static void playGame(){
		int attempts = 8;
		boolean flag = false;
		ArrayList used_words = new ArrayList();
		String word = pickRandomWords("src/words.txt", used_words);
		String temp = word;
		System.out.println("******* WELCOME TO HANGMAN *******");
		System.out.println("Press 1 to select Easy level");
		System.out.println("Press 2 to select Difficulty level");
		System.out.print("Enter choice: ");
		char c = getCharacterInput();
		System.out.println("");
	    int reduction_factor = getReductionFactor(c);
	    int word_size = word.length();  
	    char word_dash[] ;
	    word_dash = new char[word_size];
	    for (int i = 0; i < word_size; i++){
	    	word_dash[i] = '_';
	    }
	    
	    int warnings = 0;
	    while (attempts > 0){
	    	flag = checkWinningCondition(word);
	    	if (flag == true){
	    		System.out.println(" CONGRATULATIONS! You win. ");
	    		break;
	    	}
	    	printTopSeq(attempts, word_dash);
	    	System.out.print("Enter guess character: ");
	    	char inp = getCharacterInput();
	    	if (word.contains(String.valueOf(inp))){
	    		for (int i = 0; i < word.length() ; i++){
		    		if (Character.compare(word.charAt(i), inp) == 0){
		    			word_dash[i] = inp;	
		    		}	
	    		}
	    		word = word.replace(inp,'-');
	    		System.out.println("Right Attempt!");
	    	}
	    	else{
	    		if (used_words.contains(inp)){
	    			System.out.println("WARNING! The letter already been tried.");
	    			warnings ++;
	    			if (warnings == 3){
	    				attempts --;
	    				warnings = 0;
	    			}
	    		}
	    		else{
	    			System.out.println("Wrong Attempt!");
	    			attempts = attempts - reduction_factor;
	    		}
    		}
	    	used_words.add(inp);
	    }
	    if (flag == false){
	    	System.out.println();
		    System.out.print("Attempts left:");
		    System.out.println(attempts+1);
		    System.out.print("The word is : ");
		    System.out.println(temp);
		    System.out.println("");
		    System.out.println("------ GAME OVER ------");
		    System.out.println();
	    }
	}
	public static boolean checkWinningCondition(String word){
		int length = word.length();
		int count = 0;
		for (int i=0; i<length ; i++){
			if (Character.compare(word.charAt(i), '-') == 0 ){
				count ++;
			}
		}
		if (count == length){
			return true;
		}
		return false;
	}
	public static void printSequence(char [] word_dash){
		for (int i = 0; i < word_dash.length; i++){
	    	System.out.print(word_dash[i]);
	    	System.out.print(" ");
	    }
		System.out.println();
	}
	public static void printTopSeq(int attempts, char [] word_dash){
    	System.out.print("Attempts left: ");
    	System.out.println(attempts);
    	printSequence(word_dash);
    	System.out.println("");
		
	}
	public static char getCharacterInput(){
		Scanner scanner = new Scanner(System.in); 
	    char c = scanner.next().charAt(0); 
	    return c;
		
	}
	public static int getReductionFactor(char c){
	    if (c == '1')
	    	return 1;
	    else
	    	return 2;
	}
 	public static String pickRandomWords(String filename, ArrayList<String> used_words){
		ArrayList<String> list = null;
		try {
			while (true){
				list = getWordsList(filename);
				int index = generateRandomNumber(0, list.size());
				String w = list.get(index);
				if (used_words.contains(w) == false){
					return w;
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static ArrayList<String> getWordsList(String filename) throws IOException{
		File file = new File(filename);   
		FileReader fileReader = new FileReader(file);  
		BufferedReader br = new BufferedReader(fileReader);
		ArrayList <String> list = new ArrayList<String>();
		
		String line;  
		while((line = br.readLine())!=null)  {  
			list.add(line);      
		}  
		try {
			fileReader.close();
		} 
		catch (IOException e){
			e.printStackTrace();
		}   
		return list;
	}
	public static int generateRandomNumber(int a, int b) {
		Random random = new Random();
	    int number = random.nextInt(b - a) + a;
	    return number;
	}
}
