package cz.spsejecna.titera2.stravenky;

import java.util.ArrayList;
import java.util.Scanner;

import Exceptions.dveStejneStravenkyException;
import Exceptions.hodnotaException;
import Exceptions.neznamyPrikazException;
import Exceptions.zapornaHodnotaPlaceniException;

public class Main {

	public static void main(String[] args) throws hodnotaException, dveStejneStravenkyException, zapornaHodnotaPlaceniException, neznamyPrikazException {
		ArrayList<Stravenka> arr = new ArrayList<Stravenka>();
		arr = Stravenka.Nacteni(arr);
		Scanner sc = new Scanner(System.in);
		System.out.println(
				"Prikazy: \nVYTVOR - vytvori novou stravenku, budete muset zadat hodnotu \nVYPOCITEJ - zadate hodnotu k zaplaceni, kterou chcete vypocitat");
		System.out.println("Co chcete udelat?");
		String command = sc.nextLine();
		if (command.equalsIgnoreCase("VYTVOR")) {
			System.out.println("Zadejte hodnotu:");
			int value = sc.nextInt();
			Stravenka.Kontrola(arr, value);
			Stravenka.Ulozeni(arr);
		}else {
			if (command.equalsIgnoreCase("VYPOCITEJ")) {
				System.out.println("Zadejte cenu, kterou chcete vypoèítat: ");
				int value = sc.nextInt();
				Stravenka.Vypocitej(value, arr);
			}else {
				throw new neznamyPrikazException();
			}
		}
	}
}
