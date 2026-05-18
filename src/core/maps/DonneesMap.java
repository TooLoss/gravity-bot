package core.map;

import utils.Vecteur;
import java.util.List;

public class DonneesMap{
    public Vecteur positionInitiale;
    public Vecteur vitesseInitiale;
    public Vecteur ciblePosition;
    public List<ObstacleData> obstacles;

    public static class ObstacleData {
        public String type;
        public Vecteur position;
        public Vecteur fin;
        public Vecteur direction;
    }
}
