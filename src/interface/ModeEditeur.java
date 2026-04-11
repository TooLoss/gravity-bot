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