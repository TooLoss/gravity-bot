package core.joueurs;
import core.Map;

public class JoueurHumain implements Joueur {

    private String nom;

    public JoueurHumain(String nom) {
        this.nom = nom;
    }

    @Override
    public void faireJouer(Map map) {
        // TODO
    }

    public String getNom() {
        return this.nom;
    }

}
