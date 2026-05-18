package core;

import java.awt.Graphics;
import java.awt.Color;
import utils.Vecteur;
import java.util.ArrayList;
import java.util.List;

public class Projectile extends Acteur {

    public Vecteur vitesse;

    public static final double vitesseSatellite = 10.f;
    private final List<Vecteur> historiquePositions = new ArrayList<>();

    public Projectile(Vecteur position, Vecteur vitesse) {
        this.setVitesse(vitesse);
        this.setPosition(position);
    }

    @Override
    public void actualiser(double dt) {
        // Translater le vecteur actualiser
        historiquePositions.add(new Vecteur(this.getPosition()));
        double x = vitesse.getX();
        double y = vitesse.getY();
        Vecteur vecteurTranslation = new Vecteur(x * dt, y * dt);
        this.translater(vecteurTranslation);
    }

    /** Ajoute à la vitesse du projecteur v.
     * @param v Vecteur à ajouter.
     */
    public void ajouterVitesse(Vecteur v) {
        this.vitesse.ajouter(v);
        vitesse.normaliser();
        vitesse.multiplier(vitesseSatellite);
    }

    public void setVitesse(Vecteur v) {
        this.vitesse = new Vecteur(v);
    }

    public Vecteur getVitesse() {
        return this.vitesse;
    }

    public void reset() {
        historiquePositions.clear();
    }

    @Override
    public void afficher(Graphics g) {
        g.setColor(new Color(255, 255, 0, 150));
        for (Vecteur point : historiquePositions) {
            int dotX = (int) point.getX();
            int dotY = (int) point.getY();
            g.fillOval(dotX - 1, dotY - 1, 3, 3);
        }

        g.setColor(Color.GREEN);
        int x1 = (int) this.getPosition().getX();
        int y1 = (int) this.getPosition().getY();
        int x2 = x1 + (int) (vitesse.getX() * 5);
        int y2 = y1 + (int) (vitesse.getY() * 5);
        g.drawLine(x1, y1, x2, y2);
        g.fillRect((int) this.getPosition().getX() - 5, (int) this.getPosition().getY() - 5, 10, 10);
    }

}
