package core.maps;

import core.maps.DonneesMap.ObstacleData;
import core.obstacles.*;
import java.util.HashMap;
import core.Map;

public class ObstaclesRegistre {

    public interface ObstacleCreation {
        Obstacle creer(ObstacleData data);
    }

    private static final java.util.Map<String, ObstacleCreation> registre = java.util.Map.of(
        "MUR", data -> new ObstacleMur(data.position, data.fin),
        "SPHERE", data -> new ObstacleCirculaire(data.position, data.fin.getNorme())
    );

    public static Obstacle creerObstacle(ObstacleData data) {
        ObstacleCreation obstacle = registre.get(data.type.toUpperCase());
        return obstacle.creer(data);
    }
}
