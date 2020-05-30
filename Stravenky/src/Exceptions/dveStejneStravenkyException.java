package Exceptions;

public class dveStejneStravenkyException extends Exception {
	
	public dveStejneStravenkyException() {
		super("Stravenky se stejnou hodnotou nehomou být dvì");
	}

}
