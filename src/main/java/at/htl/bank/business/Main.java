package at.htl.bank.business;

import at.htl.bank.model.BankKonto;
import at.htl.bank.model.GiroKonto;
import at.htl.bank.model.SparKonto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Legen Sie eine statische Liste "konten" an, in der Sie die einzelnen Konten speichern
 *
 */
public class Main {

static ArrayList<BankKonto> konten = new ArrayList<BankKonto>();

  // die Konstanten sind package-scoped wegen der Unit-Tests
  static final double GEBUEHR = 0.02;
  static final double ZINSSATZ = 3.0;

  static final String KONTENDATEI = "erstellung.csv";
  static final String BUCHUNGSDATEI = "buchungen.csv";
  static final String ERGEBNISDATEI = "ergebnis.csv";

  
  /**
   * Führen Sie die drei Methoden erstelleKonten, fuehreBuchungenDurch und
   * findKontoPerName aus
   *
   * @param args
   */
  public static void main(String[] args) {
    erstelleKonten(KONTENDATEI);
    fuehreBuchungenDurch(BUCHUNGSDATEI);
    schreibeKontostandInDatei(ERGEBNISDATEI);
    for(BankKonto k: konten){
      System.out.println(k.getName()+" "+k.getKontoStand());
    }
  }

  /**
   * Lesen Sie aus der Datei (erstellung.csv) die Konten ein.
   * Je nach Kontentyp erstellen Sie ein Spar- oder Girokonto.
   * Gebühr und Zinsen sind als Konstanten angegeben.
   *
   * Nach dem Anlegen der Konten wird auf der Konsole folgendes ausgegeben:
   * Erstellung der Konten beendet
   *
   * @param datei KONTENDATEI
   */
  private static void erstelleKonten(String datei) {


    try(Scanner scanner = new Scanner(new FileReader(datei))) {
      scanner.nextLine();

      String[] zeilenElemente;
      String zeile;

        while (scanner.hasNextLine()){
          zeile = scanner.nextLine();
          zeilenElemente = zeile.split(";");

          if (zeilenElemente[0].equals("Sparkonto")){
            konten.add(new SparKonto(zeilenElemente[1], ZINSSATZ, Double.parseDouble(zeilenElemente[2])));
          }else if(zeilenElemente[0].equals("Girokonto")){
            konten.add(new GiroKonto(zeilenElemente[1], GEBUEHR, Double.parseDouble(zeilenElemente[2])));
          }
        }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    System.out.println("Erstellung der Konten beendet.");

  }

  /**
   * Die einzelnen Buchungen werden aus der Datei eingelesen.
   * Es wird aus der Liste "konten" jeweils das Bankkonto für
   * kontoVon und kontoNach gesucht.
   * Anschließend wird der Betrag vom kontoVon abgebucht und
   * der Betrag auf das kontoNach eingezahlt
   *
   * Nach dem Durchführen der Buchungen wird auf der Konsole folgendes ausgegeben:
   * Buchung der Beträge beendet
   *
   * Tipp: Verwenden Sie hier die Methode 'findeKontoPerName(String name)'
   *
   * @param datei BUCHUNGSDATEI
   */
  private static void fuehreBuchungenDurch(String datei) {

    try(Scanner scanner = new Scanner(new FileReader(datei))) {
      scanner.nextLine();

      String[] zeilenElemente;
      String zeile;

      while (scanner.hasNextLine()){
        zeile = scanner.nextLine();
        zeilenElemente = zeile.split(";");

          for(BankKonto b: konten){

            if(b.getName().equals(zeilenElemente[0]) ){
              b.abheben(Double.parseDouble(zeilenElemente[2]));
            }

            if(b.getName().equals(zeilenElemente[1])){
              b.einzahlen(Double.parseDouble(zeilenElemente[2]));
            }
          }

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    System.out.println("Buchung der Beträge beendet.");
  }

  /**
   * Es werden die Kontostände sämtlicher Konten in die ERGEBNISDATEI
   * geschrieben. Davor werden bei Sparkonten noch die Zinsen dem Konto
   * gutgeschrieben
   *
   * Die Datei sieht so aus:
   *
   * name;kontotyp;kontostand
   * Susi;SparKonto;875.5
   * Mimi;GiroKonto;949.96
   * Hans;GiroKonto;1199.96
   *
   * Vergessen Sie nicht die Überschriftenzeile
   *
   * Nach dem Schreiben der Datei wird auf der Konsole folgendes ausgegeben:
   * Ausgabe in Ergebnisdatei beendet
   *
   * @param datei ERGEBNISDATEI
   */
  private static void schreibeKontostandInDatei(String datei) {

    int anzahl = 0;
    String print = "";

    for(BankKonto b: konten){
      if(konten.get(anzahl) instanceof SparKonto){
        ((SparKonto) konten.get(anzahl)).zinsenAnrechnen();

        print = print+ konten.get(anzahl).getName()+";SparKonto;"+konten.get(anzahl).getKontoStand()+"\n";
      }
      if(konten.get(anzahl) instanceof GiroKonto){
        print = print+ konten.get(anzahl).getName()+";GiroKonto;"+konten.get(anzahl).getKontoStand()+"\n";
      }
      anzahl++;
    }

    try(PrintWriter writer = new PrintWriter(new FileWriter(datei))) {

      writer.println("name;kontotyp;kontostand");
     writer.print(print);

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("Ausgabe der Ergebnisdatei beendet");
  }

  /**
   */
  /**
   * Durchsuchen Sie die Liste "konten" nach dem ersten Konto mit dem als Parameter
   * übergebenen Namen
   * @param name
   * @return Bankkonto mit dem gewünschten Namen oder NULL, falls der Namen
   *         nicht gefunden wird
   */
  public static BankKonto findeKontoPerName(String name) {
       return null;
  }

}
