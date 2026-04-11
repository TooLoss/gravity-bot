import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Gravity-Bot - Menu Principal");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panneauPrincipal = new JPanel();
        // le titre et les 4 boutons
        panneauPrincipal.setLayout(new GridLayout(5, 1, 10, 15));
        panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panneauPrincipal.setBackground(Color.DARK_GRAY);

        // Titre du jeu
        JLabel titre = new JLabel("GRAVITY-BOT", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 40));
        titre.setForeground(Color.WHITE);
        panneauPrincipal.add(titre);

        // les 4 boutons
        JButton btnJouer = creerBoutonMenu("Jouer");
        JButton btnSettings = creerBoutonMenu("Paramètres");
        JButton btnCredit = creerBoutonMenu("Crédits");
        JButton btnQuitter = creerBoutonMenu("Quitter");


        // Action Jouer : ferme le menu principal et ouvre la fenetre de jeu
        btnJouer.addActionListener(e -> {
           this.dispose(); //ferme la fenetre du menu
           FenetreJeu jeu = new FenetreJeu();
           jeu.setVisible(true); // afficher la fenetre du jeu
        });

        // Action Paramètres
        btnSettings.addActionListener(e -> {
            System.out.println("Clic sur Paramètres - Logique à implémenter plus tard");
        });

        // Action Crédits
        btnCredit.addActionListener(e -> afficherCredits());

        // Action Quitter : Ferme le programme
        btnQuitter.addActionListener(e -> System.exit(0));

        // Ajout des boutons au panneau principal
        panneauPrincipal.add(btnJouer);
        panneauPrincipal.add(btnSettings);
        panneauPrincipal.add(btnCredit);
        panneauPrincipal.add(btnQuitter);

        this.setContentPane(panneauPrincipal);
    }

    // le design des boutons
    private JButton creerBoutonMenu(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setFont(new Font("Arial", Font.BOLD, 20));
        bouton.setFocusPainted(false);
        bouton.setBackground(Color.LIGHT_GRAY);
        return bouton;
    }

    // Affichage des crédits
    private void afficherCredits() {
        String message = "GRAVITY-BOT\n\n"
                + "Développé par le Groupe CD3 :\n"
                + "- Abid Mohamed\n"
                + "- El Amrany Anas\n"
                + "- El Haddadi Bilele\n"
                + "- El Hauat Chada\n"
                + "- El Qabbab Oussama\n"
                + "- Hosayn Mohamed Anis\n"
                + "- Kibou Yassine\n"
                + "- Masbah Louay\n"
                + "- Samari Yassine\n\n"
                + "ENSEEIHT - 2026";

        JOptionPane.showMessageDialog(this, message, "Crédits", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}