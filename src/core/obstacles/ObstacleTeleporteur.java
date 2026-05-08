package core.obstacles;

import core.Obstacle;
import core.Projectile;
import core.collisions.CollisionCirculaire;
import utils.Vecteur;

/**
 * Obstacle téléporteur.
 *
 * Deux instances doivent être liées ensemble via {@link #lier(ObstacleTeleporteur)}.
 * Quand le projectile entre dans l'un des portails, il est instantanément
 * déplacé à la sortie de l'autre.
 *
 * Pour éviter qu'il ne soit immédiatement re-téléporté depuis le portail de
 * sortie, sa position est décalée d'un rayon au-delà du portail de sortie
 * dans la direction de sa vitesse courante.
 *
 * Usage minimal :
 * <pre>
 *   ObstacleTeleporteur portailA = new ObstacleTeleporteur(new Vecteur(100, 100));
 *   ObstacleTeleporteur portailB = new ObstacleTeleporteur(new Vecteur(400, 400));
 *   portailA.lier(portailB);   // lie automatiquement A→B et B→A
 * </pre>
 */
public class ObstacleTeleporteur extends Obstacle {

    /** Rayon de la zone de détection du portail. */
    public static final double RAYON = 20.0;

    /** L'autre portail vers lequel téléporter le projectile. */
    private ObstacleTeleporteur partenaire;

    // ── Constructeurs ────────────────────────────────────────────────────────

    public ObstacleTeleporteur(Vecteur position) {
        super(position);
        this.setCollision(new CollisionCirculaire(this.getPosition(), RAYON));
    }

    // ── Liaison ──────────────────────────────────────────────────────────────

    /**
     * Lie ce portail à {@code autre} (et réciproquement).
     * Appeler cette méthode sur l'un des deux suffit.
     *
     * @param autre Le portail partenaire. Ne doit pas être {@code null} ni
     *              ce portail lui-même.
     * @throws IllegalArgumentException si {@code autre} est {@code null} ou
     *                                  identique à {@code this}.
     */
    public void lier(ObstacleTeleporteur autre) {
        if (autre == null) {
            throw new IllegalArgumentException("Le portail partenaire ne peut pas être null.");
        }
        if (autre == this) {
            throw new IllegalArgumentException("Un portail ne peut pas être lié à lui-même.");
        }
        this.partenaire = autre;
        // Liaison réciproque sans boucle infinie
        if (autre.partenaire != this) {
            autre.partenaire = this;
        }
    }

    /** Indique si ce portail est actuellement lié à un partenaire. */
    public boolean estLie() {
        return partenaire != null;
    }

    // ── Logique de téléportation ─────────────────────────────────────────────

    @Override
    public void eventAction(Projectile projectile) {
        if (partenaire == null) {
            // Portail inactif : aucune action
            return;
        }

        Vecteur sortie = new Vecteur(partenaire.getPosition());

        // Décaler la position de sortie au-delà du rayon du portail partenaire,
        // dans la direction de la vitesse courante du projectile, afin qu'il ne
        // soit pas immédiatement en collision avec le portail de sortie.
        Vecteur vitesse = new Vecteur(projectile.getVitesse());
        double norme = vitesse.getNorme();

        if (norme > 0) {
            vitesse.normaliser();
            vitesse.multiplier(RAYON + 1.0); // +1 pixel de marge
            sortie.ajouter(vitesse);
        }

        projectile.setPosition(sortie);
    }

    @Override
    public void actualiser(double dt) {
        // Pas de comportement dynamique
    }

    /**
     * Retourne une copie NON liée de ce portail.
     * Après copie, appelez {@link #lier} pour reconstituer la paire.
     */
    @Override
    public Obstacle copier() {
        return new ObstacleTeleporteur(new Vecteur(this.getPosition()));
    }

    // ── Accesseur ────────────────────────────────────────────────────────────

    public ObstacleTeleporteur getPartenaire() {
        return partenaire;
    }
}