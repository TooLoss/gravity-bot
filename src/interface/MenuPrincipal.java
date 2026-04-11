import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    //Variable volume instanciée à 50
    private int volumeJeu = 50;
    private CardLayout gestionnaireVues = new CardLayout();
    private JPanel conteneurPrincipal = new JPanel(gestionnaireVues);

    public MenuPrincipal() {
        setTitle("Gravity-Bot - Menu Principal");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel vueMenu = creerPanneauMenu();
        JPanel vueParam = creerPanneauParam();
        JPanel vueCredit = creerPanneauCredits();

        conteneurPrincipal.add(vueMenu, "ACCUEIL");
        conteneurPrincipal.add(vueParam, "SETTINGS");
        conteneurPrincipal.add(vueCredit, "CREDITS");

        // On affiche le conteneur principal
        this.setContentPane(conteneurPrincipal);
    } 

        private JPanel creerPanneauMenu(){
            JPanel panneauPrincipal = new JPanel();
            // le titre et les 5 boutons
            panneauPrincipal.setLayout(new GridLayout(6, 1, 10, 15));
            panneauPrincipal.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
            panneauPrincipal.setBackground(Color.DARK_GRAY);

            // Titre du jeu
            JLabel titre = new JLabel("GRAVITY-BOT", SwingConstants.CENTER);
            titre.setFont(new Font("Arial", Font.BOLD, 40));
            titre.setForeground(Color.WHITE);
            panneauPrincipal.add(titre);

            // les 5 boutons
            JButton btnJouer = creerBoutonMenu("Jouer");
            JButton btnEditeur = creerBoutonMenu("Éditeur");
            JButton btnSettings = creerBoutonMenu("Paramètres");
            JButton btnCredit = creerBoutonMenu("Crédits");
            JButton btnQuitter = creerBoutonMenu("Quitter");

            

            // Action Jouer : ferme le menu principal et ouvre la fenetre de jeu
            btnJouer.addActionListener(e -> {
                this.dispose(); //ferme la fenetre du menu
                FenetreJeu jeu = new FenetreJeu();
                jeu.setVisible(true); // afficher la fenetre du jeu
            });

            // Action Éditeur : ferme le menu et ouvre le menu editeur

            btnEditeur.addActionListener(e -> {
                this.dispose();
                ModeEditeur editeur = new ModeEditeur();
                editeur.setVisible(true);
            });

            // Action Paramètres
            btnSettings.addActionListener(e -> {
                gestionnaireVues.show(conteneurPrincipal, "SETTINGS");
            });

            // Action Crédits
            btnCredit.addActionListener(e ->{
                gestionnaireVues.show(conteneurPrincipal, "CREDITS");
            } );

            // Action Quitter : Ferme le programme
            btnQuitter.addActionListener(e -> System.exit(0));

            // Ajout des boutons au panneau principal
            panneauPrincipal.add(btnJouer);
            panneauPrincipal.add(btnEditeur);
            panneauPrincipal.add(btnSettings);
            panneauPrincipal.add(btnCredit);
            panneauPrincipal.add(btnQuitter);

            this.setContentPane(panneauPrincipal);

            return panneauPrincipal;
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

    //Afficher les crédits
    private JPanel creerPanneauCredits() {
        JPanel panneauCredit = new JPanel();
        panneauCredit.setLayout(new BoxLayout(panneauCredit, BoxLayout.Y_AXIS));
        panneauCredit.setBackground(new Color(10, 10, 25)); // Ton bleu nuit
        panneauCredit.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titre = new JLabel("EQUIPAGE CD3");
        titre.setForeground(new Color(51, 204, 255)); // Bleu Néon
        titre.setFont(new Font("Monospaced", Font.BOLD, 26));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea noms = new JTextArea(message);
        noms.setBackground(new Color(10, 10, 25));
        noms.setForeground(Color.WHITE);
        noms.setFont(new Font("Monospaced", Font.PLAIN, 16));
        noms.setEditable(false);
        noms.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnRetour = new JButton("Retour");
        styliserBouton(btnRetour, Color.GRAY, new Color(10, 10, 25), new Font("Arial", Font.BOLD, 14));
        btnRetour.addActionListener(e -> gestionnaireVues.show(conteneurPrincipal, "ACCUEIL"));

        panneauCredit.add(titre);
        panneauCredit.add(Box.createVerticalStrut(20));
        panneauCredit.add(noms);
        panneauCredit.add(Box.createVerticalStrut(20));
        panneauCredit.add(btnRetour);

        return panneauCredit;
    }

    //Affichage des paramètres
    private JPanel creerPanneauParam() {
    
        //Couleurs et thématiques
        Color fondEspace = new Color(10, 10, 25); //Bleu foncé
        Color bleuNeon = new Color(51, 204, 255); //bleu clair brillant
        Color texteBlanc = new Color(240, 240, 240);
        Font fontSpatiale = new Font("Monospaced", Font.BOLD, 18); //Police

        //Affichage organisé
        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBackground(fondEspace);
        contenu.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contenu.add(Box.createVerticalGlue());

        //Afficher titre
        JLabel titre = new JLabel("Options du jeu");
        titre.setFont(new Font("Monospaced", Font.BOLD, 24));
        titre.setForeground(bleuNeon);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenu.add(titre);
        contenu.add(Box.createVerticalStrut(30));

        //Option de contraste
        JButton btnContraste = new JButton("Contraste : Normal");
        styliserBouton(btnContraste, bleuNeon, fondEspace, fontSpatiale);
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
        Volume.setBackground(fondEspace);
        JLabel afficherVol = new JLabel("Volume : " + volumeJeu + " ");
        afficherVol.setForeground(texteBlanc);
        afficherVol.setFont(fontSpatiale);

        JButton moins = new JButton("-");
        JButton plus = new JButton("+");
        styliserBouton(moins, bleuNeon, fondEspace, fontSpatiale);
        styliserBouton(plus, bleuNeon, fondEspace, fontSpatiale);
        moins.addActionListener(e ->{
            if (volumeJeu >= 10){
                volumeJeu -= 10;
                afficherVol.setText("Volume : " + volumeJeu + "%");
            } 
        } );
        plus.addActionListener(e -> {
            if (volumeJeu <= 90){
                volumeJeu += 10;
                afficherVol.setText("Volume : " + volumeJeu + "%");
            } 
        });
        Volume.add(moins);
        Volume.add(afficherVol);
        Volume.add(plus);
        Volume.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenu.add(Volume);

        //Espace pour aérer un peu le menu
        JPanel Vide = new JPanel();
        Vide.setBackground(fondEspace);
        contenu.add(Vide);

        //Sauvegarder les changements
        JButton btnSauvegarder = new JButton("Sauvegarder les changements");
        styliserBouton(btnSauvegarder, Color.GREEN, fondEspace, fontSpatiale);
        btnSauvegarder.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSauvegarder.setMaximumSize(new Dimension(350, 50));
        btnSauvegarder.addActionListener(e -> {
            Audio.GestionnaireAudio.setVolume(volumeJeu);
            System.out.println("Paramètres sauvegardées");
            JOptionPane.showMessageDialog(
                this, "Les modifications ont été appliquées avec succès !",
                    "Système mis à jour", JOptionPane.INFORMATION_MESSAGE );
        } );
        contenu.add(btnSauvegarder);
        contenu.add(Box.createVerticalStrut(10));

        //Quitter ce menu
        JButton btnRetour = new JButton("Retour au menu");
        styliserBouton(btnRetour, Color.GRAY, fondEspace, fontSpatiale);
        btnRetour.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRetour.setMaximumSize(new Dimension(350, 50));
        btnRetour.addActionListener(ev -> gestionnaireVues.show(conteneurPrincipal, "ACCUEIL"));
        contenu.add(btnRetour);

        contenu.add(Box.createVerticalGlue());

        return contenu;
    }

    //Styliser les boutons
    private void styliserBouton(JButton b, Color neon, Color fond, Font f) {
        b.setFont(f);
        b.setForeground(neon);
        b.setBackground(fond);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createLineBorder(neon, 2));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(350, 50));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuPrincipal menu = new MenuPrincipal();
            menu.setVisible(true);
        });
    }
}