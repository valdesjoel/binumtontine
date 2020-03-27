package com.example.binumtontine.modele;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.binumtontine.activity.adherent.Record;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FraisTenueCompteAnnuel {

    //Attributs
    private String MvDateDeValeur;
    private String MvMontant;
    private String MvSensMvm;
    List<Record> MouvemDAV = new ArrayList<>();

    public FraisTenueCompteAnnuel(List<Record> mouvemDAV) {
        MouvemDAV = mouvemDAV;
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public double Calc_Frais_Tenue_Cpt_Annuel (String numeroCompte, int Annee){
//    public double Calc_Frais_Tenue_Cpt_Annuel (int Annee){
//        int i, j, jour, MaxJours;
//        Date DateJr;
//        String ModeInter; //ModeInter: Char/1; // Pour indiquer si Min/Max/Moy,
//        String TypValInt ;//TypValInt: Char/1; // Pour indiquer si Taux ou Montant fixe
//        double SoldeInit, TotalCred, TotalDeb, NewMtSolde, TxInterCpt, MtTotalInter;
//
//        double[][] TablMinMaxCpt = new double[366][5];
///*TablMinMaxCpt: Tableau 1..366, 1..5 de Réels
// Colonne 1:Jour, 2:SoldeMin, 3:SoldeMax, 4:SoldeMoy, 5:Val*Taux_Jr
// ou Val represente le choix du solde lu dans le paramétrage du compte
// et sera soit SoldeMin(par défaut), SoldeMoyen, SoldeMax.*/
//
//        //BEGIN
//        // Prendre en compte année Bixestille / IsLeapYear pour Fevrier = 29 jours
//        Year anotherYear = Year.of(Annee);
//        boolean isLeap = anotherYear.isLeap();
//        //Si AnneeBixestille alors MaxJours=366 sinon MaxJours=365
//        if (isLeap) {
//            MaxJours=366;
//        }else{
//            MaxJours=365;
//        }
//
//        DateJour=ConvertirDate(01/01/Annee);
//        SoldeInit = selectMontantFrom_31_12_Annee_1(); //Initialiser SoldeInit du compte NumCompte avec MtSolde fin 31/12/(Annee -1)
//
//        jour=1;
//        MtTotalInter=0;	// Montant Total des Intérêts calculés
//
//        //Filtrer Table des MouvemDAV Filtré NumCompte/Membre, sur Annee et la trier par Date Opération
//        //Se positionner sur le premier enregistrement
//        ArrayList<> MouvemDAV = new ArrayList<>;
//  /*Fetch all MouvementDAV
//  Mouvement.add();
//  */
//
//        int compteurMvt =0;
//        while(compteurMvt<MouvemDAV.size()){ // Tant que l'on n'est pas en fin de table/filtrée
//
//            // debut
//            // Initialisations du jour
//            TablMinMaxCpt[jour][0]=jour;
//            TablMinMaxCpt[jour][1]=SoldeInit; 	// Mt. SoldeMin
//            TablMinMaxCpt[jour][2]=SoldeInit; 	// Mt. SoldeMax
//            TablMinMaxCpt[jour][3]=SoldeInit; 	// Mt. SoldeMoyen
//            NewMtSolde=SoldeInit;
//
//            TotalCred=0; TotalDeb=0;   i=0;
//            while(MouvemDAV.get(compteurMvt).getMvDateDeValeur=DateJour){ // Tant que l'on est sur le jours en cours/DateJour
//                //debut
//                if (MvSensMvm.equals("E")) {
//                    TotalCred=MouvemDAV.get(compteurMvt).getMvMontant;
//                }else{
//                    TotalDeb=MouvemDAV.get(compteurMvt).getMvMontant;
//                }
//                NewMtSolde=NewMtSolde+(TotalCred-TotalDeb);
//                i=i+1;
//                if ((NewMtSolde<TablMinMaxCpt[jour][1]) || (TablMinMaxCpt[jour][1]=0)) {
//                    TablMinMaxCpt[jour][1]=NewMtSolde;
//                }
//
//                if ((NewMtSolde>TablMinMaxCpt[jour][2]) || (TablMinMaxCpt[jour][2]=0)) {
//                    TablMinMaxCpt[jour][2]=NewMtSolde;
//                }
//
//                TablMinMaxCpt[jour][3]=SoldeInit+((TotalCred-TotalDeb)/i);
//
//                SoldeInit=NewMtSolde;
//
//                TxInterCpt, TypValInt(T,F) et ModeInter = {Requêe SQL comme indiqué ci-dessous pour les obtenir}
//                //  Faire une Requête avec NumCompte= AdherDAV.AvNumero,
//                //  et prendre AvProduit pour Obtenir EpargneAVue.EvMtTxAgioPrelev à affecter à "TxInterCpt"
//                //  Si EvTypFrAgio(F/T/P) est de Type plage, alors, Faire une Requête sur la table PlageData
//                //  avec PdTypData=TIV et la clé du compte pour obtenir PdValTaux(affecter TxInterCpt) selon la plage,
//                //  ainsi que PdNature(T,F) Taux ou valeur fixe à affecter à TypValInt
//                //  Prendre aussi le champ EpargneAVue.EvModValInter /A1, ** à ajouter, pour le mode de calcul
//                //  du Taux d'intérêt qui sera Minimum/M par défaut ou Maximum/X, ou Moyenne/Y, à renvoyer
//                //  dans la variable "ModeInter".
//
//                // La valeur retournée est soit un Taux soit une valeur pour 1 An, ramené pour 1 jour. TxInterCpt=(TxInterCpt/MaxJours);
//                if (ModeInter='M') { // Cas de Valeur Minimale
//                    if (TypValInt='T') {
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//                }else if (ModeInter='X') { // Cas de Valeur Maximale
//                    if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;
//                    }
//                }else if (ModeInter='Y') { // Cas de Valeur Moyenne
//                    if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }
//                }
//
//
//                TablMinMaxCpt[jour][4]=TablMinMaxCpt[jour][4]/MaxJours;
//
//                MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][5];
//
//
//
//
//
//
//            } //fin		// Fin du Tant que l'on est sur la même DateJour
//
//            if (i!=0) { // Avancer si Seulement si jour traité ci-dessus,
//                DateJour=DateJour+1;
//                jour=jour+1;
//                compteurMvt++ // MouvemDAV.EnregSuivant; // Avancer sur le mouvement suivant de ce compte Filtré.
//            }else{
//                while (compteurMvt<MouvemDAV.size() && (DateJour<MouvemDAV.get(compteurMvt).getMvDateDeValeur) ) {
//                    // debut
//                    // Gestion des cas où il n'existe pas de mouvement pour ce jour sur la table "MouvemDAV", et calculer
//                    // les intérêts pour ces jours, pour forcément remplir le tableau pour les jours où il n'y a pas de mouvements
//                    // On verra si il faudra créer un param_tre dans EpargneAVue.IsCalcIntJrNonOuvres /A1 pour activer ces calculs ci-dessous...
//
//                    //  Initialiser SoldeInit du compte EAV NumCompte avec MtSolde au jour du DateJour
//                    //  Pas besoin de re-initialiser SoldeInit, car, la dernière valeur acant de rentrer dans cette boucle serait bonne
//
//
//                    // Initialisations du jour
//                    TablMinMaxCpt[jour][0]=jour;
//                    TablMinMaxCpt[jour][1]=SoldeInit; 	// Mt. SoldeMin
//                    TablMinMaxCpt[jour][2]=SoldeInit; 	// Mt. SoldeMax
//                    TablMinMaxCpt[jour][3]=SoldeInit; 	// Mt. SoldeMoyen
//
//                    if (ModeInter='M') { // Cas de Valeur Minimale
//                        if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                            TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                        }else{
//                            TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                        }
//
//                    }
//
//                    if (ModeInter='X') { // Cas de Valeur Maximale
//                        if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                            TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][2])*TxInterCpt)  ;
//                        }else{
//                            TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                        }
//
//                    }
//
//                    if (ModeInter='Y') { // Cas de Valeur Moyenne
//                        if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                            TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][3])*TxInterCpt)  ;
//                        }else{
//                            TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                        }
//
//                    }
//
//                    MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4]/MaxJours;
//
//                    MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4];
//
//                    DateJour=DateJour+1;
//                    jour=jour+1;
//
//
//
//
//                } //fin		// Fin boucle tant qu'on n'est pas sur un jour ayant des mouvements.
//
//            }
//
//
//        } // fin   // Fin de Parcours de la Table des Mouvements sur le compte "MouvemDAV" donc du traitement
//
//        // GERER LE CAS OU IL Y'A DES JOURs NON TRAITES APRES DERNIER ENREG DE "MouvemDAV"
//        if (compteurMvt>=MouvemDAV.size()) {
//            while (DateJour<=366){
//
//                //  Initialiser SoldeInit du compte EAV NumCompte avec MtSolde au jour du DateJour
//                //  Pas besoin de re-initialiser SoldeInit, car, la dernière valeur acant de rentrer dans cette boucle serait bonne
//
//                // Initialisations du jour
//                TablMinMaxCpt[jour][0]=jour;
//                TablMinMaxCpt[jour][1]=SoldeInit; 	// Mt. SoldeMin
//                TablMinMaxCpt[jour][2]=SoldeInit; 	// Mt. SoldeMax
//                TablMinMaxCpt[jour][3]=SoldeInit; 	// Mt. SoldeMoyen
//
//                if (ModeInter='M') { // Cas de Valeur Minimale
//                    if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][1])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//
//                }
//
//                if (ModeInter='X') { // Cas de Valeur Maximale
//                    if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][2])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//
//                }
//
//                if (ModeInter='Y') { // Cas de Valeur Moyenne
//                    if (TypValInt='T') { // L'intérêt est un Taux/T, sinon il serait une valeur fixe
//                        TablMinMaxCpt[jour][4]=((TablMinMaxCpt[jour][3])*TxInterCpt)  ;
//                    }else{
//                        TablMinMaxCpt[jour][4]=TxInterCpt;	// Cette variable contient dans ce cas la valeur de l'intérêt et non un taux
//                    }
//
//                }
//
//                MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4]/MaxJours;
//
//                MtTotalInter=MtTotalInter+TablMinMaxCpt[jour][4];
//
//                DateJour=DateJour+1;
//                jour=jour+1;
//
//            } // fin		// Fin boucle tant qu'on n'est pas sur un jour ayant des mouvements.
//
//        } //fin si
//
//        return MtTotalInter; //Calc_Frais_Tenue_Cpt_Annuel=MtTotalInter;	// Renvoyer le Résultat du Calcul total des frais à la fonction.
//
//        //FIN
//    } //FIN  // FIN DE LA FONCTION


}
