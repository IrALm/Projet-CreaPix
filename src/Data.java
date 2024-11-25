
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Data {
    
    protected int n , m , k , e; 

    /* n : taille de l'image de depart
       m : nombre des centres
       k : nombre des endroits à recolorier
       e : epaisseur */
    
    protected ArrayList<Centre> LesCentres ;
    protected ArrayList<Recoloriage> LesRecoloriages ;

    private Data(){

        this.n = this.m = this.k = this.e = 0;
        this.LesCentres = new ArrayList<>();
        this.LesRecoloriages = new ArrayList<>();
    }

    private static ArrayList<String> lecture_du_fichier(String nomFichier) {

        ArrayList<String> liste_de_Lignes = new ArrayList<>();
        BufferedReader lecteur_de_ligne = null;

        try {
                // Ouverture du fichier en utilisant un BufferedReader
                lecteur_de_ligne = new BufferedReader(new FileReader(nomFichier));
                String ligne;

                // Lire chaque ligne du fichier et l'ajouter à l'ArrayList
                while ((ligne = lecteur_de_ligne.readLine()) != null) {
                 liste_de_Lignes.add(ligne);
                }
        } catch (IOException e) {
                e.printStackTrace(); // Gestion d'erreur en cas de problème de lecture du fichier
        } finally {
            try {
                // Fermeture du BufferedReader pour éviter des fuites de mémoire
                if (lecteur_de_ligne != null) {
                        lecteur_de_ligne.close();
                }
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }

        // Retourne l'ArrayList contenant toutes les liste_de_Lignes du fichier
        return liste_de_Lignes;
    }

    private static int conversionStringToInt( int nb , String str){

        try {
            str = str.trim(); // élimine les espaces avant et après
            int nombre = Integer.parseInt(str);
            nb = nombre ;
        } catch (NumberFormatException e) {
            System.out.println(str + " n'a pas le format d'un nombre");
        }
        return nb;
    }

    private static Couleur choixColor( String str){

        Couleur c = null ;
        switch (str) {
            case "R":
                c = Couleur.R;
                break;
            case "B":
                c = Couleur.B;
                break;
            case "J":
                c = Couleur.J;
                break;
            case "G":
                c = Couleur.G;    
                break;
            case "N":
                c = Couleur.N;    
                break;
        
            default:
                System.out.println(" Couleur non répertorié");
                break;
        }
        return c ;
        
    }

    private static ArrayList<Centre> recupCentres( Data data , ArrayList<String> liste){

        for( int i = 2 ; i <= data.m + 1 ; i++){

            String[] centre = liste.get(i).split(",");
            int x = Data.conversionStringToInt(i, centre[0]);
            int y = Data.conversionStringToInt(i, centre[1]);
            y = data.n - y ; // pour les coordonnés en suivant l'abscisse et l'ordonnée comme en math
            if( centre.length == 5){
                for( int j = 2 ; j < centre.length ; j++){
                    centre[j] = centre[j].trim();// élimine les espaces avant et après
                }
                Couleur c1 = Data.choixColor(centre[2]);
                Couleur c2 = Data.choixColor(centre[3]);
                Couleur c3 = Data.choixColor(centre[4]);
                data.LesCentres.add(new Centre(x, y, c1, c2, c3, null));
            }
            else{
            
                for( int j = 2 ; j < centre.length ; j++){
                    centre[j] = centre[j].trim();// élimine les espaces avant et après
                }
                Couleur c1 = Data.choixColor(centre[2]);
                Couleur c2 = Data.choixColor(centre[3]);
                Couleur c3 = Data.choixColor(centre[4]);
                Couleur c4 = Data.choixColor(centre[5]);
                data.LesCentres.add(new Centre(x, y, c1, c2, c3, c4));
            }
        }
        return data.LesCentres;
    }

    private static ArrayList<Recoloriage> recupRecoloriages( Data data , ArrayList<String> liste ){

        for( int i = data.m + 4 ; i <= data.m + 3+ data.k ; i++){
            String[] recoloriage = liste.get(i).split(",");
            int x = Data.conversionStringToInt(i, recoloriage[0]);
            int y = Data.conversionStringToInt(i, recoloriage[1]);
            y = data.n - y ; // pour les coordonnés en suivant l'abscisse et l'ordonnée comme en math
            recoloriage[2] = recoloriage[2].trim();// élimine les espaces avant et après
            Couleur c = Data.choixColor(recoloriage[2]);
            data.LesRecoloriages.add(new Recoloriage(x, y, c));
        }
        return data.LesRecoloriages;
    }

    public static Data Lecture_et_Recuperation_DesDonnes( String nom_fichier ){
        
        Data data = new Data();

        /* Récuperation des lignes du fichier.txt */
        ArrayList<String> lignes = Data.lecture_du_fichier(nom_fichier);
        /* Récuperation de n , m , k et e */
        data.n = Data.conversionStringToInt(data.n , lignes.get(0));
        data.m = Data.conversionStringToInt(data.m , lignes.get(1));
        data.e = Data.conversionStringToInt(data.e , lignes.get(data.m + 2));
        data.k = Data.conversionStringToInt(data.k , lignes.get(data.m + 3));
        /*Récuperer tous les centres*/
        data.LesCentres = Data.recupCentres(data, lignes);
        /*Récuperer tous les récoloriages */
        data.LesRecoloriages = Data.recupRecoloriages(data, lignes);
        return data;
    }

    public static void afficheData( Data data){
        System.out.println(" n = " + data.n + " m = " + data.m + " e = " + data.e
                            + " k = " + data.k + " LesCentres : ");
                            for(Centre c : data.LesCentres) c.affiCentre(data);
        System.out.println(" les recoloriages");
        for( Recoloriage r : data.LesRecoloriages) r.affiRecoloriage(data);
    }

}
