package Audio;

public class GestionnaireAudio{
    private static float volumeGlobal = 0.5f;

    //méthode pour régler le volume
    public static void setVolume(int volumeBouton){
        //java sound utilise 0.0-1.0 
        volumeGlobal = volumeBouton/100f;
        System.out.println("Le volume du jeu est maintenant de : " + volumeGlobal);
    }

    public static float getVolume(){
        return volumeGlobal;
    }  
} 