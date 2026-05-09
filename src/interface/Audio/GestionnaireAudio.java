package Audio;

import javax.sound.sampled.*;
import java.io.File;

/**
 * Gère l'ambiance sonore du jeu (musique de fond et effets sonores).
 * Permet le contrôle du volume et la lecture en boucle.
 */
public class GestionnaireAudio {
    private static Clip musiqueFond;
    private static boolean estMuet = false;
    private static float volumeActuel = 0.8f;

    /**
     * Lance la musique de fond en boucle continue.
     * @param cheminFichier Chemin vers le fichier audio (format .wav recommandé).
     */
    public void jouerMusique(String cheminFichier) {
        try {
            File file = new File(cheminFichier);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();
        
            DataLine.Info info = new DataLine.Info(Clip.class, format);
        
            if (musiqueFond != null) musiqueFond.close();
        
                musiqueFond = (Clip) AudioSystem.getLine(info); // On récupère la ligne précise
                musiqueFond.open(stream);

            // Réglage du volume (Master Gain)
                if (musiqueFond.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) musiqueFond.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(0.0f); 
                }

            musiqueFond.loop(Clip.LOOP_CONTINUOUSLY);
            musiqueFond.start();
        
            // Petit délai de sécurité pour laisser le thread audio démarrer
            Thread.sleep(50); 

        } catch (Exception e) {
            System.err.println("Erreur de ligne audio : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Joue un effet sonore court (bouton, collision, explosion).
     * @param cheminFichier Chemin vers le fichier SFX.
     */
    public void jouerSFX(String cheminFichier) {
        if (estMuet) return;

        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(cheminFichier));
            Clip sfx = AudioSystem.getClip();
            sfx.open(stream);
            
            // Libère les ressources du système une fois le son terminé
            sfx.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    sfx.close();
                }
            });
            
            sfx.start();
        } catch (Exception e) {
            System.err.println("Erreur SFX : " + e.getMessage());
        }
    }

    /**
     * Modifie le volume de la musique de fond.
     * @param volume Valeur entre 0.0f (muet) et 1.0f (max).
     */
    public static void setVolume(int volumeInt) {
        float volumeF = volumeInt/100f;
        volumeActuel = volumeF;
        if (musiqueFond != null && musiqueFond.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) musiqueFond.getControl(FloatControl.Type.MASTER_GAIN);
            
            // Sécurité pour éviter le log(0) qui casse tout
            float volumeFinal = (volumeF <= 0.0f) ? 0.0001f : volumeF;
            float dB = (float) (Math.log(volumeFinal) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    /**
     * Active ou désactive l'ensemble des sons du jeu.
     * @param mute True pour couper le son, False pour le rétablir.
     */
    public void setMute(boolean mute) {
        this.estMuet = mute;
        if (musiqueFond != null) {
            if (mute) musiqueFond.stop();
            else musiqueFond.start();
            System.out.println("Le clip joue-t-il ? " + musiqueFond.isActive());
        }
    }
}