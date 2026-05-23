package core.joueurs;
import core.Map;

public class Joueur {

    public static final int BUDGET_INITIALE = 1000;

    private int budget;
    private String nom;

    public Joueur(String nom, int budget) {
        this.budget = Math.max(0, budget);
        this.nom = nom;
    }
    
    public Joueur(String nom) {
        this(nom, BUDGET_INITIALE);
    }

    public String getNom() {
        return this.nom;
    }

    public int getBudget() {
        return this.budget;
    }

    public void retirerBudget(int prix) {
        this.budget -= prix;
    }

    public void ajouterBudget(int prix) {
        this.budget += prix;
    }
}
