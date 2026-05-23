package core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import utils.Vecteur;
import core.Map;
import core.obstacles.Obstacle;
import core.map.DonneesMap.ObstacleData;
import core.map.ObstaclesRegistre;

/** Class pour charger des maps depuis un fichier texte. */
public class ChargeurMap {

    /** Méthode pour charger une map depuis un fichier.
     * @param cheminFichier String path du fichier.
     * @param map Map où il faut appliquer les paramètres.
     * @return true si le chargement est un succès.
     */
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

    /** Méthode pour traiter une ligne du fichier texte.
     * @param ligne String ligne à analyser.
     * @param map Map pour appliquer les paramètres.
     */
    private static void traiterLigne(String ligne, Map map) {
        ligne = ligne.trim();
        
        if (ligne.isEmpty() || ligne.startsWith("#")) {
            return;
        }

        String[] parties = ligne.split("=");
        if (parties.length < 2) return;

        String cle = parties[0].trim().toUpperCase();
        String[] tokens = parties[1].trim().split("\\s+");
        parserParametreGlobal(cle, tokens, map);
    }

    /** Méthode pour parser les paramètres du fichier.
     * @param cle String nom du paramètre à modifier.
     * @param tokens String[] les jetons pour paramétrer le paramètre.
     * @param map Map pour appliquer les paramètres.
     */
    private static void parserParametreGlobal(String cle, String[] tokens, Map map) {
        // TODO rendre ça plus objet
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
            case "OBSTACLE":
                this.parserEtAjouterObstacle(tokens, map);
            default:
                System.err.println("Clé inconnue ignorée : " + cle);
                break;
        }
    }

    /** Parser les obstacles en particuliers.
     * @param tokens String[] jetons pour les paramètres.
     * @param map Map pour appliquer les paramètres.
     */
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

    /** Extraire un vecteur depuis les jetons.
     * @param tokens String[] jetons.
     * @param indexDepart int début du vecteur.
     * @return Vecteur crée à partir du String.
     */
    private static Vecteur extraireVecteur(String[] tokens, int indexDepart) {
        float x = Float.parseFloat(tokens[indexDepart]);
        float y = Float.parseFloat(tokens[indexDepart + 1]);
        return new Vecteur(x, y);
    }
}
