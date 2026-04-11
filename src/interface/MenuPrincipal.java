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
            afficherParametres();
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

    //Affichage des paramètres
    private void afficherParametres() {
        JDialog menuParam = new JDialog(this, "Paramètres", true);
        menuParam.setSize(500,600); 
        menuParam.setLocation(300,200);

        //Affichage organisé
        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contenu.add(Box.createVerticalGlue());

        //Afficher titre
        JLabel titre = new JLabel("Options du jeu");
        titre.setFont(new Font("Arial", Font.BOLD, 22));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenu.add(titre);
        contenu.add(Box.createVerticalStrut(30));

        //Option de contraste
        JButton btnContraste = new JButton("Contraste : Normal");
        btnContraste.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnContraste.setMaximumSize(new Dimension(300,50));
        btnContraste.addActionListener(e -> {
            if (btnContraste.getText().equals("Contraste : Normal")){
                btnContraste.setText("Contraste : Elevé");
            } else {
                btnContraste.setText("Contraste : Normal");
            } 
        } );
        contenu.add(btnContraste);
        contenu.add(Box.createVerticalStrut(20));

        //Options Volume
        JPanel Volume = new JPanel();
        JLabel afficherVol = new JLabel("Volume : 50");
        JButton moins = new JButton("-");
        JButton plus = new JButton("+");
        moins.addActionListener(e -> afficherVol.setText("Volume : 0"));
        plus.addActionListener(e -> afficherVol.setText("Volume : 100"));
        Volume.add(moins);
        Volume.add(afficherVol);
        Volume.add(plus);
        Volume.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenu.add(Volume);

        //Espace pour aérer un peu le menu
        contenu.add(new JPanel());

        //Sauvegarder les changements
        JButton btnSauvegarder = new JButton("Sauvegarder les changements");
        btnSauvegarder.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSauvegarder.setMaximumSize(new Dimension(350, 50));
        btnSauvegarder.addActionListener(e -> {
            System.out.println("Paramètres sauvegardées");
        } );
        contenu.add(btnSauvegarder);
        contenu.add(Box.createVerticalStrut(10));

        //Quitter ce menu
        JButton btnRetour = new JButton("Retour au menu");
        btnRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRetour.setMaximumSize(new Dimension(350, 50));
        btnRetour.addActionListener(ev -> menuParam.dispose() );
        contenu.add(btnRetour);

        contenu.add(Box.createVerticalGlue());

        //Visibilité du menu
        menuParam.add(contenu);
        menuParam.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}