package at.htl.bank.model;

public class SparKonto extends BankKonto {

    private double zinsSatz;

    public SparKonto(String name, double zinsSatz) {
        super(name);
        this.zinsSatz = zinsSatz;
    }

    public SparKonto(String name, double zinsSatz, double anfangsBestand){
        super(name,anfangsBestand);
        this.zinsSatz = zinsSatz;
    }

    public void zinsenAnrechnen(){

        this.zinsSatz = zinsSatz * this.kontoStand / 100;
        this.kontoStand = kontoStand + this.zinsSatz;
    }
}
