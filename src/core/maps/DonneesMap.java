package core.map;

import utils.Vecteur;
import java.util.List;

/** Classe transporteur de données pour la map. */
public class DonneesMap{
    public Vecteur positionInitiale;
    public Vecteur vitesseInitiale;
    public Vecteur ciblePosition;
    public int budget;
    public List<ObstacleData> obstacles;

    /** Classe anonyme pour les obstacles en particuliers. */
    public static class ObstacleData {
        public String type;
        public Vecteur position;
        public Vecteur fin;
        public Vecteur direction;
    }
}
