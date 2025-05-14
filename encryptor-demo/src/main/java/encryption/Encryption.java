package encryption;
import java.util.Random;

/**
 * Array and String manipulation in Java and 
 * an intro to utility classes and static features 
 *
 */
public class Encryption {
	private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";
	private final static int LENGTH = 64;
	
	//Testing using another example
	public static void main(String[] args) {
	String encrypted = Encryption.encrypt("Hi my name is sirat", "ABCDEFGH");
	String decrypted = Encryption.decrypt(encrypted, "ABCDEFGH");
	
	System.out.println(encrypted);
	System.out.println(decrypted);
	
	}

	public static String encrypt(String textToEncrypt, String key) { //textToEncrypt = plain text
        int hash = key.hashCode(); // you get 2042300548 when generating hashcode for the key "ABCDEFGH".
        String substitute = substitutionPattern(hash); // generating substitution pattern using the generated hash as seed in this substitutionPattern function.
        StringBuilder encrypt = new StringBuilder(); // Calling StringBuilder object to instantiate encrypt so this variable can use the object's attributes
        for (int i = 0; i < textToEncrypt.length(); i += LENGTH) { // Using for-loop to go over each line in the plain text starting at 0 then incrementing 64 letters each time.
            String lineOfChars = textToEncrypt.substring(i, Math.min(i + LENGTH, textToEncrypt.length())); // Note: textToEncrypt.legnth() = 350 so 1st will be substring(0, 64), 2nd will be (64,128), 3rd will be (128,192) etc.. and the last 34 will just be padding (space) to make up for the 64 letters.
            String substitutedChars = applySubstitution(true, lineOfChars.toUpperCase(), substitute); // Substituting the lineOfChars and making it uppercase before encrypting with the substitution pattern thats generated through seed (hashcode key) randomly by calling the function applySubstitution and true for encryption
            int[] columnOrder = generateColumnOrder(hash); // Generating a column order with the hash key given
            String transposedChars = applyTransposition(true, substitutedChars, columnOrder); // Transposing the substituted sentence with the generated column order.
            encrypt.append(transposedChars); // Appending transposedChars to encrypt each time // the last step
        }

        return encrypt.toString(); // returning encrypt to string
	}

	public static String decrypt(String textToDecrypt, String key) {
        int hash = key.hashCode(); // 2042300548
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < textToDecrypt.length(); i += LENGTH) {
            String chunk = textToDecrypt.substring(i, Math.min(i + LENGTH, textToDecrypt.length()));
            int[] columnOrder = generateColumnOrder(hash);
            String reversedTransposedChunk = applyTransposition(false, chunk, columnOrder);
            String substitutedChars = substitutionPattern(hash);
            String reversedSubstitutedChunk = applySubstitution(false, reversedTransposedChunk.toUpperCase(), substitutedChars);
            decryptedText.append(reversedSubstitutedChunk);
        }

        return decryptedText.toString();
	}
	
	// PRIVATE METHODS THAT ARE INTERNALLY USED BY THE PUBLIC METHODS.
	 private static String substitutionPattern(int hashkey) {
	        StringBuilder alphabet = new StringBuilder(ALPHABET);
	        Random random = new Random(hashkey); 
	        for (int i = 0; i < 100; i++) {
	            int m = random.nextInt(alphabet.length()); // Generating first random number from 0 to 26 since alphabet.length() = 27
	            int n = random.nextInt(alphabet.length()); // Generating 2nd random number from 0 to 26 since alphabet.length() = 27
	            char temp = alphabet.charAt(m); // assigning temp to m (first random number) for later use   
	            alphabet.setCharAt(m, alphabet.charAt(n)); // assigning m to n (2nd random number into m) 
	            alphabet.setCharAt(n, temp); // assigning n to temp (1st random number into n)
	        }
	        return alphabet.toString(); // alphabet now = GCWHAKSXJMDLFUB ITVYRPZENQO
	    }

	    private static int[] generateColumnOrder(int hashkey) {
	    	 
	        int[] order = {0, 1, 2, 3, 4, 5, 6, 7};
	        Random random = new Random(hashkey); 
	        for (int i = 0; i < 100; i++) {
	            int m = random.nextInt(order.length); // generating a random number between 0 to 7 indices in the order array elements and assigning it to pos1
	            int n = random.nextInt(order.length); // generating second random number between 0 to 7 indices in the order array elements and assigning it to pos2
	            int temp = order[m]; // assigning temp to m (first random number) for later use
	            order[m] = order[n];// assigning m to n (2nd random number)
	            order[n] = temp; // assinging n to m (first random number)
	        }
	        return order; //returning order of column = [3, 2, 7, 4, 1, 0, 5, 6] 
	    }
     // for the first 64 characters:
	 // status = true
     // lineOfChars = "IN CRYPTOGRAPHY, A SUBSTITUTION CIPHER IS A METHOD OF ENCODING B"
     // pattern = GCWHAKSXJMDLFUB ITVYRPZENQO
     private static String applySubstitution(boolean status, String lineOfChars, String pattern) {
         StringBuilder substituedResult = new StringBuilder(); 
         String originalAlphabet = ALPHABET; // originalAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ "
         String punctuations = ",.;:\"'()";
     
         if (!status) { // if already encrypted = false
             originalAlphabet = pattern; // then originalAlphabet = "GCWHAKSXJMDLFUB ITVYRPZENQO"
             pattern = ALPHABET; // pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZ "
         }
     
         for (int i = 0; i < lineOfChars.length(); i++) {
             char c = lineOfChars.charAt(i); // assigning lineOfChars to c each time
             if (punctuations.indexOf(c) != -1) { // Check if there's a punctuation
            	 substituedResult.append('O'); // replace with O
             } else {
                 if (originalAlphabet.indexOf(c) != -1) { // the text character (c) matches with  one of originalAlphabet character
                	 substituedResult.append(pattern.charAt(originalAlphabet.indexOf(c))); // append the location (originalAlphabet.indexOf(c)) of pattern into result.
                 } else { // no text character match
                	 substituedResult.append(c); // then append the text character to result
                 }
             }
         }

         while (substituedResult.length() < LENGTH) { //if result.length() is less than 64 then we add O for the rest of the left over length to make up the 64 characters in total. 
        	 substituedResult.append('O');
         }
 
         return substituedResult.toString(); // return the result
     }
     
     // status = true
     // substitutedChars = the result previously
     // columnOrder = [3, 2, 7, 4, 1, 0, 5, 6] 

	    private static String applyTransposition(boolean status, String substitutedChars, int[] columnOrder) {
	        int numCols = columnOrder.length; // the length is 8 so # of columns = 8
         int numRows = substitutedChars.length() / numCols; // numRows = 64 / 8 = 8
	        char[][] matrix = new char[numRows][numCols]; // 2D matrix/array; 
 
	        if (status) { // if status = true ; proceed to transposing for further encryption
	            for (int i = 0; i < substitutedChars.length(); i++) {
	                matrix[i / numCols][i % numCols] = substitutedChars.charAt(i); // filling in the matrix with substituedChars characters in each index row and column
	            }

	            StringBuilder result = new StringBuilder();
	            for (int col : columnOrder) { // col assigned to each columnOrder  
	                for (int row = 0; row < numRows; row++) { // filling the rows each time to numRows max = 8
	                    result.append(matrix[row][col]);
	                }
	            }
	            return result.toString();
	        } else { // if status = false ; reverse transposing for further decryption
	            int i = 0;
	            for (int col : columnOrder) {
	                for (int row = 0; row < numRows; row++) {
	                        matrix[row][col] = substitutedChars.charAt(i);	// filling back to substituedChars for decryption
	                        i++;
	                }
	            }
	            
	            // filling the matrix rows and columns into result 
	            StringBuilder result = new StringBuilder();
	            for (int row = 0; row < numRows; row++) {
	                for (int col = 0; col < numCols; col++) {
	                    result.append(matrix[row][col]);
	                }
	            }
	            return result.toString();
	        }
	    }


}
	


