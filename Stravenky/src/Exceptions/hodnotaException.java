package Exceptions;

public class hodnotaException extends Exception {
	
	public hodnotaException() {
		super("Hodnota stravenky nesmí být záporná nebo rovna nule");
	}

}
