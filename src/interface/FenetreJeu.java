import javax.swing.*;
import java.awt.*;

/** Fenetre principale du jeu.
 * Nord : bandeau info (budget et tour en cours)
 * Centre : plateau de jeu
 * Est - barre laterale (boutique des elements a placer)
 * Sud - boutons de controle
 * Cette fenetre s'ouvre quand on clique sur Jouer dans MenuPrincipal
 */
 public class FenetreJeu extends JFrame {
     /** Le budget. **/
     private int budget = 1000;
     
     /** Le tour. **/
     private int tour = 1;

    /** Le label du budget. **/
     private Jlabel labelBudget;
    
    /** Le label du numero de tour. **/
    private Jlabel labelTour;

    public FenetreJeu() {
        setTitle("Gravity-Bot - Partie");
        setSize(900,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construireInterface();

    }

    private void construireInterface() {
        setLayout(new BorderLayout(5,5));

        add(construireBandeauNord(), BorderLayout.NORTH);
        add(construireCentre(), BorderLayout.CENTER);
        add(construireSidebar(), BorderLayout.EAST);
        add(construireFooter(), BorderLayout.SOUTH);

    }

    // Bandeau nord : affiche le budget et le tour en cours
    private JPanel construireBandeauNord() {
        JPanel bandeau = new JPanel( new FlowLayout(FlowLayout.CENTER, 40, 8));
        bandeau.setBackground(new Color(30, 30, 30));
        
        labelBudget = new JLabel("Budget : " + budget + " euros");
        labelBudget.setForeground(Color.WHITE);
        labelBudget.setFont(new Font("Arial", Font.BOLD, 16));
        
        labelTour = new JLabel("Tour : " + tour);
        labelTour.setForeground(Color.LIGHT_GRAY);
        labelTour.setFont(new Font("Arial", Font.BOLD, 16));
        
        bandeau.add(labelBudget);
        bandeau.add(labelTour);

        return bandeau;

    }

    // Zone Centrale : liée à AfficherGrille
    private JPanel construireCentre() {

    }

    // Barre Laterale : liste les elements que le joueur peut acheter et placer
    private JPanel construireSidebar() {

    }

    // Footer : boutons de controle pour lancer la simualtion ou retourner au menu
    private JPanel construireFooter() {

    }

    // Creer boutons stylise pour la barre laterale
    private JButton creerBoutonElement(String nom, String info) {

    }

    // Appliquer meme style aux boutons du Footer
    private void styliserBoutonControle(JButton btn, Color couleur) {

    }



 }