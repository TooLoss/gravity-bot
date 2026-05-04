import utils.Vecteur;
import core.Map;

class GravityBot {
    public static void main(String[] args) {
        Map map = new Map();
        map.position_initiale = new Vecteur(10.0f);
        map.vitesse_initiale = new Vecteur(10.0f, .0f);
        map.lancerSimulation();
    }
}
