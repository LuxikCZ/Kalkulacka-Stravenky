package Exceptions;

public class zapornaHodnotaPlaceniException extends Exception {
	
	public zapornaHodnotaPlaceniException() {
		super("Hodnota, kterou chcete zaplatit nesm� b�t z�porn�");
	}

}
