/** Functions for checking if a given string is an anagram. */
public class Anagram {
	public static void main(String args[]) {
		// Tests the isAnagram function.
		System.out.println(isAnagram("silent","listen"));  // true
		System.out.println(isAnagram("William Shakespeare","I am a weakish speller")); // true
		System.out.println(isAnagram("Madam Curie","Radium came")); // true
		System.out.println(isAnagram("Tom Marvolo Riddle","I am Lord Voldemort")); // true

		// Tests the preProcess function.
		System.out.println(preProcess("What? No way!!!"));
		
		// Tests the randomAnagram function.
		System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");
		
		// Performs a stress test of randomAnagram 
		String str = "1234567";
		Boolean pass = true;
		//// 10 can be changed to much larger values, like 1000
		for (int i = 0; i < 10; i++) {
			String randomAnagram = randomAnagram(str);
			System.out.println(randomAnagram);
			pass = pass && isAnagram(str, randomAnagram);
			if (!pass) break;
		}
		System.out.println(pass ? "test passed" : "test Failed");
	}  

	// Returns true if the two given strings are anagrams, false otherwise.
	public static boolean isAnagram(String str1, String str2) {
		String preorocesseStr1 = preProcess(str1);
		String preorocesseStr2 = preProcess(str2);
		int lengthStr1 = preorocesseStr1.length();
		if (lengthStr1 != preorocesseStr2.length()) {
			return false;
		}
		else {
			for (int i = 0; i<lengthStr1; i++) {
				char currentChar = preorocesseStr1.charAt(i);
				int countInStr1 = 0;
				int countInStr2 = 0;
				for (int j = 0; j<lengthStr1; j++) {
					if (preorocesseStr1.charAt(j) == currentChar) {
						countInStr1++;
					}
					if (preorocesseStr2.charAt(j) == currentChar) {
						countInStr2++;
					}
				}
				if (countInStr1 != countInStr2) {
					return false;
				}
			}
		}
		return true;
	}
	   
	// Returns a preprocessed version of the given string: all the letter characters are converted
	// to lower-case, and all the other characters are deleted, except for spaces, which are left
	// as is. For example, the string "What? No way!" becomes "whatnoway"
	public static String preProcess(String str) {
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";	// all upper-case letters
		String lowerCase = "abcdefghijklmnopqrstuvwxyz";	// all lower-case letters
		String preprocessedVersion = "";
		for (int i=0; i<str.length(); i++){
			char currentChar = str.charAt(i);
			// Check if character is in upperCase or lowerCase
			if (upperCase.indexOf(currentChar) != -1){
				int index = upperCase.indexOf(currentChar);
				preprocessedVersion += lowerCase.charAt(index);    // convert to lower-case and add to result
			}
			else if (lowerCase.indexOf(currentChar) != -1){
				preprocessedVersion += currentChar;    // already lower-case, just add to result	
			}
		}
		return preprocessedVersion;
	} 
	   
	// Returns a random anagram of the given string. The random anagram consists of the same
	// characters as the given string, re-arranged in a random order. 
	public static String randomAnagram(String str) {
		String randomAnagram = "";
		String currentStr = str;	// string of characters not yet used
		// repeatedly pick a random character from currentStr and add it to randomAnagram
		while (currentStr.length() > 0) {
			int randomIndex = (int)(Math.random() * currentStr.length());	// random index in currentStr
			char randomChar = currentStr.charAt(randomIndex);
			randomAnagram += randomChar;
			currentStr = currentStr.substring (0, randomIndex) + currentStr.substring(randomIndex + 1);
		}
		return randomAnagram;
	}
}
