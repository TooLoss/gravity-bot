package core.map;

import core.map.DonneesMap.ObstacleData;
import core.obstacles.*;
import java.util.HashMap;
import core.Map; // TODO Map est déjà pris pour java.utils

/** Registre obstacle pour enregistrer les différents obstacles. */
public class ObstaclesRegistre {

    /** Interface anonyme. */
    public interface ObstacleCreation {
        Obstacle creer(ObstacleData data);
    }

    /** Map qui associe un nom à un lambdas pour crée les obstacles. */
    private static final java.util.Map<String, ObstacleCreation> registre = java.util.Map.of(
        "MUR", data -> new ObstacleMur(data.position, data.fin),
        "SPHERE", data -> new ObstacleCirculaire(data.position, data.fin.getNorme())
    );

    /** Méthode pour crée un obstacle en fonction des données.
     * @param data ObstacleData données de l'obstacle.
     * @return Obstacle obstacle retourné.
     */
    public static Obstacle creerObstacle(ObstacleData data) {
        ObstacleCreation obstacle = registre.get(data.type.toUpperCase());
        return obstacle.creer(data);
    }
}
