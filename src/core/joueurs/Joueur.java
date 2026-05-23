package core.joueurs;
import core.Map;

/** Classe joueur. */
public class Joueur {

    /** Budget du joueur. */
    private int budget;
    /** Nom du joueur. */
    private String nom;

    /** Constructeur du joueur.
     * @param nom String.
     * @param budget int budget initial.
     */
    public Joueur(String nom, int budget) {
        this.budget = Math.max(0, budget);
        this.nom = nom;
    }
    
    /** Constructeur du joueur.
     * @param nom String.
     */
    public Joueur(String nom) {
        this(nom, 0);
    }

    /** Getter du nom.
     * @return String nom.
     */
    public String getNom() {
        return this.nom;
    }

    /** Getter du budget.
     * @return int budget.
     */
    public int getBudget() {
        return this.budget;
    }

    /** Setter budget.
     * @param int budget.
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /** Méthode pour retirer le budget.
     * @param prix int à supprimer du budget.
     */
    public void retirerBudget(int prix) {
        this.budget -= prix;
    }

    /** Mérhode pour ajouter du budget.
     * @param prix int prix à ajouter.
     */
    public void ajouterBudget(int prix) {
        this.budget += prix;
    }
}
