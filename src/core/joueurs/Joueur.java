package core.joueurs;
import core.Map;

public class Joueur {

    public static final int BUDGET_INITIALE = 5000;

    private int budget;
    private String nom;

    public Joueur(String nom) {
        this.budget = BUDGET_INITIALE;
        this.nom = nom;
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
}
