import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * La classe Parametres gère l'interface de configuration du jeu GravityBot.
 * Elle permet principalement de régler le volume sonore via un curseur (JSlider)
 * et de sauvegarder ces changements de manière persistante durant la session.
 */
public class Parametres {
    /** Largeur de la fenêtre des paramètres. */
    static int boardWidth = 700;
    /** Hauteur de la fenêtre des paramètres. */
    static int boardHeight = 600;

    /** Couleur violette pour l'effet de survol (glow). */
    private static final Color loganGlow = new Color(170, 169, 205);
    /** Couleur blanche pour le texte par défaut. */
    private static final Color white = new Color(246, 246, 246);
    /** Couleur grise utilisée lorsque le bouton Sauvegarder est désactivé. */
    private static final Color customGrey = new Color(92, 93, 95);

    /** Options de navigation du menu. */
    private static final String[] menuOptions = {"Retour"};
    
    /** Valeur actuelle du volume sélectionnée sur le slider. */
    private static int volumeJeu = 50;
    /** Ancienne valeur du volume (utilisée pour détecter s'il y a des changements à sauvegarder). */
    private static int volumeOld = volumeJeu;
    /** Étiquette affichant dynamiquement le pourcentage du volume. */
    private final JLabel afficherVol = new JLabel("Volume : " + volumeJeu + "%");
    
    /** Bouton permettant d'appliquer les modifications de volume. */
    JButton btnSauvegarder = createMenuButton("Sauvegarder");
    
    /** Fenêtre principale des paramètres. */
    JFrame frame = new JFrame("GravityBot");
    
    /**
     * Constructeur de la classe Parametres.
     * Initialise l'interface graphique, le fond animé, le curseur personnalisé
     * et le JSlider pour le contrôle du volume.
     */
    public Parametres() {
        ImageIcon bgIcon = new ImageIcon("ressources/gifGravity.gif");
        JPanel buttonsPanel = new JPanel();
        JPanel Volume = new JPanel();
        JLabel backgroundLabel = new JLabel(bgIcon);
        
        // Setup de la frame
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Setup du fond (GIF)
        backgroundLabel.setLayout(new BorderLayout());
        frame.setContentPane(backgroundLabel); 

        // Gestion du curseur
        try {
            ImageIcon icon = new ImageIcon("ressources/cursor.png");
            Image originalImg = icon.getImage();
            int cursorSize = 32;
            BufferedImage resizedImg = new BufferedImage(cursorSize, cursorSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(originalImg, 0, 0, cursorSize, cursorSize, null);
            g2.dispose();
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(resizedImg, new Point(0, 0), "GravityCursor");
            frame.setCursor(customCursor);
        } catch (Exception e) {
            frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }

        // Setup du titre
        JLabel title = new JLabel("GRAVITYBOT");
        title.setFont(new Font("Monospaced", Font.BOLD, 60));
        title.setForeground(white);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        backgroundLabel.add(title, BorderLayout.NORTH);
        
        // Setup des boutons
        buttonsPanel.setLayout(new GridLayout(5, 1, 0, 16));
        buttonsPanel.setOpaque(false); // Transparent
        
        // Setup du volume avec un slider
        Volume.setOpaque(false); 
        
        // Setup du texte 
        afficherVol.setForeground(loganGlow); 
        afficherVol.setFont(new Font("Monospaced", Font.PLAIN, 25));
        
        // Création du Slider
        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, volumeJeu);
        volumeSlider.setOpaque(false); 
        volumeSlider.setForeground(loganGlow); 
        volumeSlider.setBackground(loganGlow); 
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        
        // Interaction du curseur
        volumeSlider.addChangeListener(e -> {
            volumeJeu = volumeSlider.getValue();
            afficherVol.setText("Volume : " + volumeJeu + "%");
            
            // On met à jour uniquement la couleur de base
            if (volumeOld != volumeJeu) {
                btnSauvegarder.setForeground(white);
            } else {
                btnSauvegarder.setForeground(customGrey);
            }
        });
        
        Volume.add(afficherVol);
        Volume.add(volumeSlider);
        
        // Ajout du panneau Volume et du bouton sauvegarder
        buttonsPanel.add(Volume);
        buttonsPanel.add(btnSauvegarder);

        // Ajout du bouton Retour
        for (String opt : menuOptions) {
            JButton btn = createMenuButton(opt);
            buttonsPanel.add(btn);
        }
        
        // Centrage
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(buttonsPanel);
        backgroundLabel.add(centerPanel, BorderLayout.CENTER);

        // Affichage
        frame.setVisible(true);
    }

    /**
     * Crée un bouton stylisé avec des bords arrondis et une gestion spécifique du survol.
     * Pour le bouton "Sauvegarder", l'interactivité dépend de la modification du volume.
     * @param text Le libellé du bouton.
     * @return Le JButton configuré.
     */
    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.PLAIN, 25));
        btn.setForeground(white);
        btn.setContentAreaFilled(false); 
        btn.setOpaque(false);
        btn.setFocusable(false);
        btn.setPreferredSize(new Dimension(250, 50));

        // On définit la bordure lissée et arrondie
        int radius = 30; // Rayon d'arrondissement
        
        btn.setBorder(new javax.swing.border.Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(btn.getForeground()); 
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(10, 20, 10, 20);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });

        // Couleur initiale grise pour le bouton Sauvegarder
        if (text.equals("Sauvegarder")) {
            btn.setForeground(customGrey);
        }

        // Un seul MouseListener pour gérer le survol intelligemment
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Si c'est le bouton Sauvegarder et qu'il n'y a aucun changement, on bloque l'effet de survol
                if (text.equals("Sauvegarder") && volumeJeu == volumeOld) {
                    return;
                }
                btn.setForeground(loganGlow);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (text.equals("Sauvegarder") && volumeJeu == volumeOld) {
                    return; // Reste gris
                }
                btn.setForeground(white);
            }
        });
        
        // Gestion des actions
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Retour")) {
                	volumeJeu = volumeOld; // Réinitialise le volume si aucune modification n'a été faite
                    frame.dispose();
                    MenuPrincipal menu = new MenuPrincipal();
                    menu.frame.setVisible(true);
                }
                else if (text.equals("Sauvegarder")) { 
                    // On ne sauvegarde que si c'est nécessaire
                    if (volumeJeu != volumeOld) {
                        volumeOld = volumeJeu;
                        btnSauvegarder.setForeground(customGrey); // On le regrise après sauvegarde
                    }
                }
            }
        });
        
        return btn;
    }
}
