import javax.swing.*;
import java.awt.*;

/** Fenetre du mode Editeur de Gravity-Bot.
 * Permet au joueur de construire son propre contrat en :
 *   - placant des obstacles sur le plateau
 *   - reglant le budget de depart
 *   - definissant la position de l'objectif
 *
 * Cette fenetre s'ouvre depuis le bouton "Editeur" du MenuPrincipal.
 * La structure visuelle est identique a FenetreJeu, seule la sidebar change.
 */
public class ModeEditeur extends JFrame {
    /** Le budget de départ. **/
    private int budgetDepart = 1000;
    
    /**Le label du budget de départ. **/
    private JLabel labelBudgetDepart;

    public ModeEditeur() {
        setTitle("Gravity-Bot - Editeur de niveaux");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        construireInterface();
    }

    private void construireInterface() {
        setLayout(new BorderLayout(5, 5));

        add(construireBandeauNord(), BorderLayout.NORTH);
        add(construireCentre(), BorderLayout.CENTER);
        add(construireSidebar(), BorderLayout.EAST);
        add(construireFooter(), BorderLayout.SOUTH);

    }
    
    // Bandeau nord : affiche le budget de départ que le joueur est en train de configurer
    private JPanel construireBandeauNord() {
        JPanel bandeau = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 8));
        bandeau.setBackground(new Color(30, 20, 10));

        JLabel labelTitre = new JLabel("Mode Editeur");
        labelTitre.setForeground(new Color(255, 200, 80));
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));

        labelBudgetDepart = new JLabel("Budget de depart : " + budgetDepart + " euros");
        labelBudgetDepart.setForeground(Color.LIGHT_GRAY);
        labelBudgetDepart.setFont(new Font("Arial", Font.BOLD, 16));

        bandeau.add(labelTitre);
        bandeau.add(labelBudgetDepart);

        return bandeau;
    }

    // Cree un bouton stylise pour la sidebar de l'editeur
    private JButton creerBoutonOutil(String nom, String description) {
        JButton btn = new JButton("<html><b>" + nom + "</b><br><small>" + description + "</small></html>");
        btn.setBackground(new Color(60, 40, 10));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(165, 55));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(new Color(180, 130, 20), 1));
        return btn;
    }
    
    //Applique le meme style aux boutons du footer
    private void styliserBoutonControle(JButton btn, Color couleur) {
        btn.setBackground(couleur.darker());
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(couleur, 1),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)));

    }


}