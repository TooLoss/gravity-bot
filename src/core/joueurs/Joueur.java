package core.joueurs;
import core.Map;

public interface Joueur {

    public static final int budget = 5000;

    public void faireJouer(Map map);
    public String getNom();

}
