package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import utils.Vecteur;
import core.Map;
import core.obstacles.Obstacle;
import core.map.DonneesMap.ObstacleData;
import core.map.ObstaclesRegistre;

public class ChargeurMap {

    public static boolean chargerDepuisFichier(String cheminFichier, Map map) {
        map.viderCarte();

        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;

            while ((ligne = br.readLine()) != null) {
                traiterLigne(ligne, map);
            }

            map.initialiserParametres();
            return true;

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erreur de chargement du niveau : " + e.getMessage());
            return false;
        }
    }

    private static void traiterLigne(String ligne, Map map) {
        ligne = ligne.trim();
        
        if (ligne.isEmpty() || ligne.startsWith("#")) {
            return;
        }

        String[] parties = ligne.split("=");
        if (parties.length < 2) return;

        String cle = parties[0].trim().toUpperCase();
        String[] tokens = parties[1].trim().split("\\s+");

        if (cle.equals("OBSTACLE")) {
            parserEtAjouterObstacle(tokens, map);
        } else {
            parserParametreGlobal(cle, tokens, map);
        }
    }

    private static void parserParametreGlobal(String cle, String[] tokens, Map map) {
        switch (cle) {
            case "POSITIONINITIALE":
                map.setPositionInitiale(extraireVecteur(tokens, 0));
                break;
            case "VITESSEINITIALE":
                map.setVitesseInitiale(extraireVecteur(tokens, 0));
                break;
            case "CIBLEPOSITION":
                map.setCiblePosition(extraireVecteur(tokens, 0));
                break;
            default:
                System.err.println("Clé inconnue ignorée : " + cle);
                break;
        }
    }

    private static void parserEtAjouterObstacle(String[] tokens, Map map) {
        if (tokens.length < 3) return; // Sécurité : il faut au moins un TYPE et un VECTEUR (X, Y)

        ObstacleData obsData = new ObstacleData();
        obsData.type = tokens[0];
        obsData.position = extraireVecteur(tokens, 1);

        if (tokens.length >= 5) {
            obsData.fin = extraireVecteur(tokens, 3);
        }

        Obstacle vraiObstacle = ObstaclesRegistre.creerObstacle(obsData);
        if (vraiObstacle != null) {
            map.ajouterObstacle(vraiObstacle);
        }
    }

    private static Vecteur extraireVecteur(String[] tokens, int indexDepart) {
        float x = Float.parseFloat(tokens[indexDepart]);
        float y = Float.parseFloat(tokens[indexDepart + 1]);
        return new Vecteur(x, y);
    }
}
