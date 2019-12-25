/* Vamsi Veeramasu
 * 11/17/2019
 * This class was created for the sole reason of being able to throw this specific exception.
 */
public class EmptyInputException extends Exception{ //How to tie in exceptions with main and throw exceptions to throw up the right JPanes in main?
	
	EmptyInputException(String s){
		super(s);
	}
	
	
}