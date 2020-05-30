package cz.spsejecna.titera2.stravenky;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import Exceptions.dveStejneStravenkyException;
import Exceptions.hodnotaException;
import Exceptions.zapornaHodnotaPlaceniException;

public class Stravenka implements Serializable {

	private int value;
	private int pocet = 0;
	private int doplatek;

	public static Stravenka factoryStravenka(int value) throws hodnotaException {
		if (value <= 0) {
			throw new hodnotaException();
		} else {
			return new Stravenka(value);
		}
	}

	private Stravenka(int value) {
		this.value = value;
	}

	public static void Kontrola(ArrayList<Stravenka> a, int value)
			throws dveStejneStravenkyException, hodnotaException {
		int size = a.size();
		for (int i = 0; i < size; i++) {
			if (value == a.get(i).value) {
				throw new dveStejneStravenkyException();
			} else {
				a.add(Stravenka.factoryStravenka(value));
			}
		}
		if (a.size() == 0) {
			a.add(Stravenka.factoryStravenka(value));
		}
	}

	public static void Ulozeni(ArrayList<Stravenka> a) {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File stravenky = new File(tempDir.getAbsolutePath() + File.separator + "stravenky.dat");
		try {
			FileOutputStream fileo = new FileOutputStream(stravenky);
			ObjectOutputStream out = new ObjectOutputStream(fileo);

			out.writeObject(a);

			out.close();
			fileo.close();
		} catch (IOException e) {
			System.err.println("IO Exception");
		} catch (Exception e) {
			System.err.println("An unexpected error has occurred");
		}
	}

	public static ArrayList<Stravenka> Nacteni(ArrayList<Stravenka> arr) {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File stravenky = new File(tempDir.getAbsolutePath() + File.separator + "stravenky.dat");
		try {
			FileInputStream filei = new FileInputStream(stravenky);
			ObjectInputStream in = new ObjectInputStream(filei);

			arr = (ArrayList) in.readObject();

			in.close();
			filei.close();

		} catch (IOException e) {
			System.err.println("IO Exception");
		} catch (Exception e) {
			System.err.println("An unexpected error has occurred");
		}
		return arr;
	}

	public static Stravenka nejnizsiHodnota(ArrayList<Stravenka> arr) {
		Stravenka nejmensi = arr.get(0);
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).value < nejmensi.value) {
				nejmensi = arr.get(i);
			}
		}
		return nejmensi;
	}

	public static Stravenka nejvyssiHodnota(ArrayList<Stravenka> arr) {
		Stravenka nejvetsi = arr.get(0);
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).value > nejvetsi.value) {
				nejvetsi = arr.get(i);
			}
		}
		return nejvetsi;
	}

	public static Stravenka nejnizsiDoplatek(ArrayList<Stravenka> arr) {
		Stravenka nejmensiDoplatek = null;
		do {
			int j = 0;
			if (arr.get(j).doplatek >= 0) {
				nejmensiDoplatek = arr.get(j);
			} else {
				nejmensiDoplatek = null;
			}
			j++;
		} while (nejmensiDoplatek == null);

		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i).doplatek < nejmensiDoplatek.doplatek && arr.get(i).doplatek >= 0) {
				nejmensiDoplatek = arr.get(i);
			}
		}
		return nejmensiDoplatek;
	}

	public static void Vypocitej(int pay, ArrayList<Stravenka> arr) throws zapornaHodnotaPlaceniException {
		if (pay < 0) {
			throw new zapornaHodnotaPlaceniException();
		} else {
			for (int i = 0; i < arr.size(); i++) {
				if (pay % arr.get(i).value == 0) {
					arr.get(i).pocet = pay / arr.get(i).value;
					System.out.println("Použijte " + arr.get(i).pocet + "x " + arr.get(i));
				} else {
					if (pay <= 20 && pay <= 0) {
						if (arr.get(i).value <= 20) {
							int zbytek = pay - arr.get(i).value;
							if (zbytek <= 0) {
								System.out.println("Použijte 1x " + arr.get(i));
							} else {
								System.out.println("Použijte 1x " + arr.get(i) + " Doplate hotovì " + zbytek);
							}
						} else {
							System.out.println("Hodnota na zaplacení je moc malá zaplate " + pay + " hotovì");
						}
					}
				}
			}
		}
		int zbytek = 0;
		zbytek = pay - Stravenka.nejvyssiHodnota(arr).value;
		if (zbytek < 0) {
			zbytek = pay - Stravenka.nejnizsiHodnota(arr).value;
			if (zbytek >= -20) {
				Stravenka.nejnizsiHodnota(arr).pocet++;
				System.out.println(
						"Použijte " + Stravenka.nejnizsiHodnota(arr).pocet + "x " + Stravenka.nejnizsiHodnota(arr));
			} else {
				System.out.println("Zaplate " + pay + " v hotovosti. \n"
						+ "Pokud nemáte tuto èástku v hotovosti použijte 1x " + Stravenka.nejnizsiHodnota(arr));
			}
		}
		if (zbytek > 0) {
			Stravenka.nejvyssiHodnota(arr).pocet++;
		}
		for (int i = 0; i < arr.size(); i++) {
			if(zbytek == Stravenka.nejnizsiHodnota(arr).value) {
				zbytek = zbytek - Stravenka.nejnizsiHodnota(arr).value;
				Stravenka.nejnizsiHodnota(arr).pocet ++;
			}
			if (zbytek > Stravenka.nejnizsiHodnota(arr).value) {
				int pomocna = zbytek;
				zbytek = zbytek - Stravenka.nejvyssiHodnota(arr).value;
				if (zbytek > 0) {
					Stravenka.nejvyssiHodnota(arr).pocet++;
				}
				if (zbytek < 0) {
					for (int j = 0; j < arr.size(); j++) {
						zbytek = pomocna;
						zbytek = zbytek - arr.get(j).value;
						arr.get(j).doplatek = zbytek;
					}
				}
				zbytek = pomocna;
				zbytek = zbytek - Stravenka.nejnizsiDoplatek(arr).value;
			}
		}
		System.out.println("Použijte:");
		for(int i = 0; i<arr.size(); i++) {
			if(arr.get(i).pocet != 0) {
				System.out.println(arr.get(i).pocet + "x " + arr.get(i));
			}
		}
	}

	@Override
	public String toString() {
		return "Stravenku o hodnotì: " + value;
	}

}