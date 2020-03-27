package com.example.binumtontine.modele;

public class Credit {
    //Attributes Start
   private String CrNumero;
   private String CrCode;
    private String CrLibelle;
    private String CrDureeMin;
    private String CrDureeMax;
    private String CrNaturePas;
    private String CrNbreUPas;
    private String CrTypTxInter;
    private String CrValTxInter;
    private Boolean CrIsTxIntNeg;
    private String CrNbreAvalDmde;
    private String CrNbreMinAvalExig;
    private String CrTxCouvCrAval;
    private Boolean CrIsTxCouvAvalOblig;
    private Boolean CrIsCautionMorAvalAcc;
    private Boolean CrIsGarBloqCptOblig;
    private Boolean CrIsGarCptEAVOn;
    private Boolean CrIsGarCptEATOn;
    private Boolean CrIsGarCptEAPOn;
    private String CrMtMaxSansAval;
    private Boolean CrIsAvalSansCredOn;
    private Boolean CrIsTxGarMemObl;
    private String CrTauxGarMemb;
    private Boolean CrIsPersMorAvalOn;
    private Boolean CrIsCouvPartSOn;
    private String CrTxCouvPSOblig;
    private Boolean CrIsAffCollCredOn;
    private String CrNbreAnAncMinCred;
    private Boolean CrNbAnAncNeg;
    private String CrMtPlafondMax;
    private Boolean CrIsMtPlafCredLeve;
    private Boolean CrIsGarMatExige;
    private Boolean CrIsFraisEtudDossOn;
    private String CrNatFrEtudDoss;
    private String CrValTxFrEtudDoss;
    private String CrBaseTxFrEtudDoss;
    private Boolean CrIsFraisDeblocCredOn;
    private String CrNatFraisDeblocCred;
    private String CrValTxFraisDeblocCred;
    private String CrBaseTxFraisDeblocCred;
    private Boolean CrIsFraisDecaissCredOn;
    private String CrNatFraisDecaissCred;
    private String CrValTxFraisDecaissCred;
    private String CrBaseFraisDecaissCred;
    private Boolean CrIsFraisEtudByDAV;
    private Boolean CrIsFraisDeblocByDAV;
    private Boolean CrIsFraisDecaissByDAV;
    private Boolean CrIsModDecaissByObjet;
    private Boolean CrIsDeblocTransfDAVOn;
    private Boolean CrIsMtPlafByObjet;
    private String CrModeRemb;
    private Boolean CrIsCptEATRemCredOn;
    private Boolean CrIsCptEAPRemCredOn;
    private Boolean CrIsInterOffSiCapRembAnt;
    private String CrTxInterEchNHon;
    private String CrBaseInterEchNHon;
    private String CrPlanningRembCred;
    private Boolean CrIsRappDatEchCred;
    private String CrModelTextRappEchRemb;
    private String CrNbreJrAvantDatEch;
    private String CrNbreJrApreEchSiNHon;
    private String CrUser;
    private String CrDateHCree;
    private String CrUserModif;
    private String CrDatHModif;
    private String CrCaisseId;
    private String CrGuichetId;

    //Attributes END
//JSON Parameters START
    public static final String KEY_CREDIT_Numero ="CrNumero";
    public static final String KEY_CREDIT_Code = "CrCode";
    public static final String KEY_CREDIT_Libelle = "CrLibelle";
    public static final String KEY_CREDIT_DureeMin = "CrDureeMin";
    public static final String KEY_CREDIT_DureeMax = "CrDureeMax";
    public static final String KEY_CREDIT_NaturePas = "CrNaturePas";
    public static final String KEY_CREDIT_NbreUPas = "CrNbreUPas";
    public static final String KEY_CREDIT_TypTxInter = "CrTypTxInter";
    public static final String KEY_CREDIT_ValTxInter = "CrValTxInter";
    //Base taux à indiquer
    public static final String KEY_CREDIT_IsTxIntNeg = "CrIsTxIntNeg";
    public static final String KEY_CREDIT_NbreAvalDmde = "CrNbreAvalDmde";
    public static final String KEY_CREDIT_NbreMinAvalExig = "CrNbreMinAvalExig";
    public static final String KEY_CREDIT_TxCouvCrAval = "CrTxCouvCrAval";
    public static final String KEY_CREDIT_IsTxCouvAvalOblig = "CrIsTxCouvAvalOblig";
    public static final String KEY_CREDIT_IsCautionMorAvalAcc = "CrIsCautionMorAvalAcc";
    public static final String KEY_CREDIT_IsGarBloqCptOblig = "CrIsGarBloqCptOblig";
    public static final String KEY_CREDIT_IsGarCptEAVOn = "CrIsGarCptEAVOn";
    public static final String KEY_CREDIT_IsGarCptEATOn = "CrIsGarCptEATOn";
    public static final String KEY_CREDIT_IsGarCptEAPOn = "CrIsGarCptEAPOn";
    public static final String KEY_CREDIT_MtMaxSansAval = "CrMtMaxSansAval";
    public static final String KEY_CREDIT_IsAvalSansCredOn = "CrIsAvalSansCredOn";
    public static final String KEY_CREDIT_IsTxGarMemObl = "CrIsTxGarMemObl";
    public static final String KEY_CREDIT_TauxGarMemb = "CrTauxGarMemb";
    public static final String KEY_CREDIT_IsPersMorAvalOn = "CrIsPersMorAvalOn";
    public static final String KEY_CREDIT_IsCouvPartSOn = "CrIsCouvPartSOn";
    public static final String KEY_CREDIT_TxCouvPSOblig = "CrTxCouvPSOblig";
    public static final String KEY_CREDIT_IsAffCollCredOn = "CrIsAffCollCredOn";
    public static final String KEY_CREDIT_NbreAnAncMinCred = "CrNbreAnAncMinCred";
    public static final String KEY_CREDIT_NbAnAncNeg = "CrNbAnAncNeg";
    public static final String KEY_CREDIT_MtPlafondMax = "CrMtPlafondMax";
    public static final String KEY_CREDIT_IsMtPlafCredLeve = "CrIsMtPlafCredLeve";
    public static final String KEY_CREDIT_IsGarMatExige = "CrIsGarMatExige";
    public static final String KEY_CREDIT_IsFraisEtudDossOn = "CrIsFraisEtudDossOn";
    public static final String KEY_CREDIT_NatFrEtudDoss = "CrNatFrEtudDoss";
    public static final String KEY_CREDIT_ValTxFrEtudDoss = "CrValTxFrEtudDoss";
    public static final String KEY_CREDIT_BaseTxFrEtudDoss = "CrBaseTxFrEtudDoss";
    public static final String KEY_CREDIT_IsFraisDeblocCredOn ="CrIsFraisDeblocCredOn";
    public static final String KEY_CREDIT_NatFraisDeblocCred ="CrNatFraisDeblocCred";
    public static final String KEY_CREDIT_ValTxFraisDeblocCred ="CrValTxFraisDeblocCred";
    public static final String KEY_CREDIT_BaseTxFraisDeblocCred ="CrBaseTxFraisDeblocCred";
    public static final String KEY_CREDIT_IsFraisDecaissCredOn ="CrIsFraisDecaissCredOn";
    public static final String KEY_CREDIT_NatFraisDecaissCred ="CrNatFraisDecaissCred";
    public static final String KEY_CREDIT_ValTxFraisDecaissCred ="CrValTxFraisDecaissCred";
    public static final String KEY_CREDIT_BaseFraisDecaissCred ="CrBaseFraisDecaissCred";
    public static final String KEY_CREDIT_IsFraisEtudByDAV ="CrIsFraisEtudByDAV";
    public static final String KEY_CREDIT_IsFraisDeblocByDAV ="CrIsFraisDeblocByDAV";
    public static final String KEY_CREDIT_IsFraisDecaissByDAV ="CrIsFraisDecaissByDAV";
    public static final String KEY_CREDIT_IsModDecaissByObjet ="CrIsModDecaissByObjet";
    public static final String KEY_CREDIT_IsDeblocTransfDAVOn ="CrIsDeblocTransfDAVOn";
    public static final String KEY_CREDIT_IsMtPlafByObjet ="CrIsMtPlafByObjet";
    public static final String KEY_CREDIT_ModeRemb ="CrModeRemb";
    public static final String KEY_CREDIT_IsCptEATRemCredOn ="CrIsCptEATRemCredOn";
    public static final String KEY_CREDIT_IsCptEAPRemCredOn ="CrIsCptEAPRemCredOn";
    public static final String KEY_CREDIT_IsInterOffSiCapRembAnt ="CrIsInterOffSiCapRembAnt";
    public static final String KEY_CREDIT_TxInterEchNHon ="CrTxInterEchNHon";
    public static final String KEY_CREDIT_BaseInterEchNHon ="CrBaseInterEchNHon";
    public static final String KEY_CREDIT_PlanningRembCred ="CrPlanningRembCred";
    public static final String KEY_CREDIT_IsRappDatEchCred ="CrIsRappDatEchCred";
    public static final String KEY_CREDIT_ModelTextRappEchRemb ="CrModelTextRappEchRemb";
    public static final String KEY_CREDIT_NbreJrAvantDatEch ="CrNbreJrAvantDatEch";
    public static final String KEY_CREDIT_NbreJrApreEchSiNHon ="CrNbreJrApreEchSiNHon";
    public static final String KEY_CREDIT_User ="CrUser";
    public static final String KEY_CREDIT_DateHCree ="CrDateHCree";
    public static final String KEY_CREDIT_UserModif ="CrUserModif";
    public static final String KEY_CREDIT_DatHModif ="CrDatHModif";
    public static final String KEY_CREDIT_CaisseId ="CrCaisseId";
    public static final String KEY_CREDIT_GuichetId ="CrGuichetId";
// JSON Parameters END


    /**
     * Constructor Credit's Class without CrNumero (id)
     * @param crCode
     * @param crLibelle
     * @param crDureeMin
     * @param crDureeMax
     * @param crNaturePas
     * @param crNbreUPas
     * @param crTypTxInter
     * @param crValTxInter
     * @param crIsTxIntNeg
     * @param crNbreAvalDmde
     * @param crNbreMinAvalExig
     * @param crTxCouvCrAval
     * @param crIsTxCouvAvalOblig
     * @param crIsCautionMorAvalAcc
     * @param crIsGarBloqCptOblig
     * @param crIsGarCptEAVOn
     * @param crIsGarCptEATOn
     * @param crIsGarCptEAPOn
     * @param crMtMaxSansAval
     * @param crIsAvalSansCredOn
     * @param crIsTxGarMemObl
     * @param crTauxGarMemb
     * @param crIsPersMorAvalOn
     * @param crIsCouvPartSOn
     * @param crTxCouvPSOblig
     * @param crIsAffCollCredOn
     * @param crNbreAnAncMinCred
     * @param crNbAnAncNeg
     * @param crMtPlafondMax
     * @param crIsMtPlafCredLeve
     * @param crIsGarMatExige
     * @param crIsFraisEtudDossOn
     * @param crNatFrEtudDoss
     * @param crValTxFrEtudDoss
     * @param crBaseTxFrEtudDoss
     * @param crIsFraisDeblocCredOn
     * @param crNatFraisDeblocCred
     * @param crValTxFraisDeblocCred
     * @param crBaseTxFraisDeblocCred
     * @param crIsFraisDecaissCredOn
     * @param crNatFraisDecaissCred
     * @param crValTxFraisDecaissCred
     * @param crBaseFraisDecaissCred
     * @param crIsFraisEtudByDAV
     * @param crIsFraisDeblocByDAV
     * @param crIsFraisDecaissByDAV
     * @param crIsModDecaissByObjet
     * @param crIsDeblocTransfDAVOn
     * @param crIsMtPlafByObjet
     * @param crModeRemb
     * @param crIsCptEATRemCredOn
     * @param crIsCptEAPRemCredOn
     * @param crIsInterOffSiCapRembAnt
     * @param crTxInterEchNHon
     * @param crBaseInterEchNHon
     * @param crPlanningRembCred
     * @param crIsRappDatEchCred
     * @param crModelTextRappEchRemb
     * @param crNbreJrAvantDatEch
     * @param crNbreJrApreEchSiNHon
     * @param crUser
     * @param crDateHCree
     * @param crUserModif
     * @param crDatHModif
     * @param crCaisseId
     * @param crGuichetId
     */
    public Credit(String crCode, String crLibelle, String crDureeMin, String crDureeMax, String crNaturePas,
                  String crNbreUPas, String crTypTxInter, String crValTxInter, Boolean crIsTxIntNeg,
                  String crNbreAvalDmde, String crNbreMinAvalExig, String crTxCouvCrAval, Boolean crIsTxCouvAvalOblig,
                  Boolean crIsCautionMorAvalAcc, Boolean crIsGarBloqCptOblig, Boolean crIsGarCptEAVOn, Boolean crIsGarCptEATOn,
                  Boolean crIsGarCptEAPOn, String crMtMaxSansAval, Boolean crIsAvalSansCredOn, Boolean crIsTxGarMemObl,
                  String crTauxGarMemb, Boolean crIsPersMorAvalOn, Boolean crIsCouvPartSOn, String crTxCouvPSOblig,
                  Boolean crIsAffCollCredOn, String crNbreAnAncMinCred, Boolean crNbAnAncNeg, String crMtPlafondMax,
                  Boolean crIsMtPlafCredLeve, Boolean crIsGarMatExige, Boolean crIsFraisEtudDossOn, String crNatFrEtudDoss,
                  String crValTxFrEtudDoss, String crBaseTxFrEtudDoss, Boolean crIsFraisDeblocCredOn, String crNatFraisDeblocCred,
                  String crValTxFraisDeblocCred, String crBaseTxFraisDeblocCred, Boolean crIsFraisDecaissCredOn,
                  String crNatFraisDecaissCred, String crValTxFraisDecaissCred, String crBaseFraisDecaissCred,
                  Boolean crIsFraisEtudByDAV, Boolean crIsFraisDeblocByDAV, Boolean crIsFraisDecaissByDAV,
                  Boolean crIsModDecaissByObjet, Boolean crIsDeblocTransfDAVOn, Boolean crIsMtPlafByObjet,
                  String crModeRemb, Boolean crIsCptEATRemCredOn, Boolean crIsCptEAPRemCredOn, Boolean crIsInterOffSiCapRembAnt,
                  String crTxInterEchNHon, String crBaseInterEchNHon, String crPlanningRembCred, Boolean crIsRappDatEchCred,
                  String crModelTextRappEchRemb, String crNbreJrAvantDatEch, String crNbreJrApreEchSiNHon,
                  String crUser, String crDateHCree, String crUserModif, String crDatHModif, String crCaisseId, String crGuichetId) {
        CrCode = crCode;
        CrLibelle = crLibelle;
        CrDureeMin = crDureeMin;
        CrDureeMax = crDureeMax;
        CrNaturePas = crNaturePas;
        CrNbreUPas = crNbreUPas;
        CrTypTxInter = crTypTxInter;
        CrValTxInter = crValTxInter;
        CrIsTxIntNeg = crIsTxIntNeg;
        CrNbreAvalDmde = crNbreAvalDmde;
        CrNbreMinAvalExig = crNbreMinAvalExig;
        CrTxCouvCrAval = crTxCouvCrAval;
        CrIsTxCouvAvalOblig = crIsTxCouvAvalOblig;
        CrIsCautionMorAvalAcc = crIsCautionMorAvalAcc;
        CrIsGarBloqCptOblig = crIsGarBloqCptOblig;
        CrIsGarCptEAVOn = crIsGarCptEAVOn;
        CrIsGarCptEATOn = crIsGarCptEATOn;
        CrIsGarCptEAPOn = crIsGarCptEAPOn;
        CrMtMaxSansAval = crMtMaxSansAval;
        CrIsAvalSansCredOn = crIsAvalSansCredOn;
        CrIsTxGarMemObl = crIsTxGarMemObl;
        CrTauxGarMemb = crTauxGarMemb;
        CrIsPersMorAvalOn = crIsPersMorAvalOn;
        CrIsCouvPartSOn = crIsCouvPartSOn;
        CrTxCouvPSOblig = crTxCouvPSOblig;
        CrIsAffCollCredOn = crIsAffCollCredOn;
        CrNbreAnAncMinCred = crNbreAnAncMinCred;
        CrNbAnAncNeg = crNbAnAncNeg;
        CrMtPlafondMax = crMtPlafondMax;
        CrIsMtPlafCredLeve = crIsMtPlafCredLeve;
        CrIsGarMatExige = crIsGarMatExige;
        CrIsFraisEtudDossOn = crIsFraisEtudDossOn;
        CrNatFrEtudDoss = crNatFrEtudDoss;
        CrValTxFrEtudDoss = crValTxFrEtudDoss;
        CrBaseTxFrEtudDoss = crBaseTxFrEtudDoss;
        CrIsFraisDeblocCredOn = crIsFraisDeblocCredOn;
        CrNatFraisDeblocCred = crNatFraisDeblocCred;
        CrValTxFraisDeblocCred = crValTxFraisDeblocCred;
        CrBaseTxFraisDeblocCred = crBaseTxFraisDeblocCred;
        CrIsFraisDecaissCredOn = crIsFraisDecaissCredOn;
        CrNatFraisDecaissCred = crNatFraisDecaissCred;
        CrValTxFraisDecaissCred = crValTxFraisDecaissCred;
        CrBaseFraisDecaissCred = crBaseFraisDecaissCred;
        CrIsFraisEtudByDAV = crIsFraisEtudByDAV;
        CrIsFraisDeblocByDAV = crIsFraisDeblocByDAV;
        CrIsFraisDecaissByDAV = crIsFraisDecaissByDAV;
        CrIsModDecaissByObjet = crIsModDecaissByObjet;
        CrIsDeblocTransfDAVOn = crIsDeblocTransfDAVOn;
        CrIsMtPlafByObjet = crIsMtPlafByObjet;
        CrModeRemb = crModeRemb;
        CrIsCptEATRemCredOn = crIsCptEATRemCredOn;
        CrIsCptEAPRemCredOn = crIsCptEAPRemCredOn;
        CrIsInterOffSiCapRembAnt = crIsInterOffSiCapRembAnt;
        CrTxInterEchNHon = crTxInterEchNHon;
        CrBaseInterEchNHon = crBaseInterEchNHon;
        CrPlanningRembCred = crPlanningRembCred;
        CrIsRappDatEchCred = crIsRappDatEchCred;
        CrModelTextRappEchRemb = crModelTextRappEchRemb;
        CrNbreJrAvantDatEch = crNbreJrAvantDatEch;
        CrNbreJrApreEchSiNHon = crNbreJrApreEchSiNHon;
        CrUser = crUser;
        CrDateHCree = crDateHCree;
        CrUserModif = crUserModif;
        CrDatHModif = crDatHModif;
        CrCaisseId = crCaisseId;
        CrGuichetId = crGuichetId;
    }

    public Credit(String crNumero, String crCode, String crLibelle, String crDureeMin, String crDureeMax, String crNaturePas,
                  String crNbreUPas, String crTypTxInter, String crValTxInter, Boolean crIsTxIntNeg, String crNbreAvalDmde,
                  String crNbreMinAvalExig, String crTxCouvCrAval, Boolean crIsTxCouvAvalOblig, Boolean crIsCautionMorAvalAcc,
                  Boolean crIsGarBloqCptOblig, Boolean crIsGarCptEAVOn, Boolean crIsGarCptEATOn, Boolean crIsGarCptEAPOn,
                  String crMtMaxSansAval, Boolean crIsAvalSansCredOn, Boolean crIsTxGarMemObl, String crTauxGarMemb,
                  Boolean crIsPersMorAvalOn, Boolean crIsCouvPartSOn, String crTxCouvPSOblig, Boolean crIsAffCollCredOn,
                  String crNbreAnAncMinCred, Boolean crNbAnAncNeg, String crMtPlafondMax, Boolean crIsMtPlafCredLeve,
                  Boolean crIsGarMatExige, Boolean crIsFraisEtudDossOn, String crNatFrEtudDoss, String crValTxFrEtudDoss,
                  String crBaseTxFrEtudDoss, Boolean crIsFraisDeblocCredOn, String crNatFraisDeblocCred, String crValTxFraisDeblocCred,
                  String crBaseTxFraisDeblocCred, Boolean crIsFraisDecaissCredOn, String crNatFraisDecaissCred,
                  String crValTxFraisDecaissCred, String crBaseFraisDecaissCred, Boolean crIsFraisEtudByDAV,
                  Boolean crIsFraisDeblocByDAV, Boolean crIsFraisDecaissByDAV, Boolean crIsModDecaissByObjet,
                  Boolean crIsDeblocTransfDAVOn, Boolean crIsMtPlafByObjet, String crModeRemb, Boolean crIsCptEATRemCredOn,
                  Boolean crIsCptEAPRemCredOn, Boolean crIsInterOffSiCapRembAnt, String crTxInterEchNHon, String crBaseInterEchNHon,
                  String crPlanningRembCred, Boolean crIsRappDatEchCred, String crModelTextRappEchRemb, String crNbreJrAvantDatEch,
                  String crNbreJrApreEchSiNHon,
                  String crUser, String crDateHCree, String crUserModif, String crDatHModif, String crCaisseId, String crGuichetId) {
        CrNumero = crNumero;
        CrCode = crCode;
        CrLibelle = crLibelle;
        CrDureeMin = crDureeMin;
        CrDureeMax = crDureeMax;
        CrNaturePas = crNaturePas;
        CrNbreUPas = crNbreUPas;
        CrTypTxInter = crTypTxInter;
        CrValTxInter = crValTxInter;
        CrIsTxIntNeg = crIsTxIntNeg;
        CrNbreAvalDmde = crNbreAvalDmde;
        CrNbreMinAvalExig = crNbreMinAvalExig;
        CrTxCouvCrAval = crTxCouvCrAval;
        CrIsTxCouvAvalOblig = crIsTxCouvAvalOblig;
        CrIsCautionMorAvalAcc = crIsCautionMorAvalAcc;
        CrIsGarBloqCptOblig = crIsGarBloqCptOblig;
        CrIsGarCptEAVOn = crIsGarCptEAVOn;
        CrIsGarCptEATOn = crIsGarCptEATOn;
        CrIsGarCptEAPOn = crIsGarCptEAPOn;
        CrMtMaxSansAval = crMtMaxSansAval;
        CrIsAvalSansCredOn = crIsAvalSansCredOn;
        CrIsTxGarMemObl = crIsTxGarMemObl;
        CrTauxGarMemb = crTauxGarMemb;
        CrIsPersMorAvalOn = crIsPersMorAvalOn;
        CrIsCouvPartSOn = crIsCouvPartSOn;
        CrTxCouvPSOblig = crTxCouvPSOblig;
        CrIsAffCollCredOn = crIsAffCollCredOn;
        CrNbreAnAncMinCred = crNbreAnAncMinCred;
        CrNbAnAncNeg = crNbAnAncNeg;
        CrMtPlafondMax = crMtPlafondMax;
        CrIsMtPlafCredLeve = crIsMtPlafCredLeve;
        CrIsGarMatExige = crIsGarMatExige;
        CrIsFraisEtudDossOn = crIsFraisEtudDossOn;
        CrNatFrEtudDoss = crNatFrEtudDoss;
        CrValTxFrEtudDoss = crValTxFrEtudDoss;
        CrBaseTxFrEtudDoss = crBaseTxFrEtudDoss;
        CrIsFraisDeblocCredOn = crIsFraisDeblocCredOn;
        CrNatFraisDeblocCred = crNatFraisDeblocCred;
        CrValTxFraisDeblocCred = crValTxFraisDeblocCred;
        CrBaseTxFraisDeblocCred = crBaseTxFraisDeblocCred;
        CrIsFraisDecaissCredOn = crIsFraisDecaissCredOn;
        CrNatFraisDecaissCred = crNatFraisDecaissCred;
        CrValTxFraisDecaissCred = crValTxFraisDecaissCred;
        CrBaseFraisDecaissCred = crBaseFraisDecaissCred;
        CrIsFraisEtudByDAV = crIsFraisEtudByDAV;
        CrIsFraisDeblocByDAV = crIsFraisDeblocByDAV;
        CrIsFraisDecaissByDAV = crIsFraisDecaissByDAV;
        CrIsModDecaissByObjet = crIsModDecaissByObjet;
        CrIsDeblocTransfDAVOn = crIsDeblocTransfDAVOn;
        CrIsMtPlafByObjet = crIsMtPlafByObjet;
        CrModeRemb = crModeRemb;
        CrIsCptEATRemCredOn = crIsCptEATRemCredOn;
        CrIsCptEAPRemCredOn = crIsCptEAPRemCredOn;
        CrIsInterOffSiCapRembAnt = crIsInterOffSiCapRembAnt;
        CrTxInterEchNHon = crTxInterEchNHon;
        CrBaseInterEchNHon = crBaseInterEchNHon;
        CrPlanningRembCred = crPlanningRembCred;
        CrIsRappDatEchCred = crIsRappDatEchCred;
        CrModelTextRappEchRemb = crModelTextRappEchRemb;
        CrNbreJrAvantDatEch = crNbreJrAvantDatEch;
        CrNbreJrApreEchSiNHon = crNbreJrApreEchSiNHon;
        CrUser = crUser;
        CrDateHCree = crDateHCree;
        CrUserModif = crUserModif;
        CrDatHModif = crDatHModif;
        CrCaisseId = crCaisseId;
        CrGuichetId = crGuichetId;
    }

    //Getters and setters


    public String getCrNumero() {
        return CrNumero;
    }



    public String getCrCode() {
        return CrCode;
    }

    public void setCrCode(String crCode) {
        CrCode = crCode;
    }

    public String getCrLibelle() {
        return CrLibelle;
    }

    public void setCrLibelle(String crLibelle) {
        CrLibelle = crLibelle;
    }

    public String getCrDureeMin() {
        return CrDureeMin;
    }

    public void setCrDureeMin(String crDureeMin) {
        CrDureeMin = crDureeMin;
    }

    public String getCrDureeMax() {
        return CrDureeMax;
    }

    public void setCrDureeMax(String crDureeMax) {
        CrDureeMax = crDureeMax;
    }

    public String getCrNaturePas() {
        return CrNaturePas;
    }

    public void setCrNaturePas(String crNaturePas) {
        CrNaturePas = crNaturePas;
    }

    public String getCrNbreUPas() {
        return CrNbreUPas;
    }

    public void setCrNbreUPas(String crNbreUPas) {
        CrNbreUPas = crNbreUPas;
    }

    public String getCrTypTxInter() {
        return CrTypTxInter;
    }

    public void setCrTypTxInter(String crTypTxInter) {
        CrTypTxInter = crTypTxInter;
    }

    public String getCrValTxInter() {
        return CrValTxInter;
    }

    public void setCrValTxInter(String crValTxInter) {
        CrValTxInter = crValTxInter;
    }

    public Boolean getCrIsTxIntNeg() {
        return CrIsTxIntNeg;
    }

    public void setCrIsTxIntNeg(Boolean crIsTxIntNeg) {
        CrIsTxIntNeg = crIsTxIntNeg;
    }

    public String getCrNbreAvalDmde() {
        return CrNbreAvalDmde;
    }

    public void setCrNbreAvalDmde(String crNbreAvalDmde) {
        CrNbreAvalDmde = crNbreAvalDmde;
    }

    public String getCrNbreMinAvalExig() {
        return CrNbreMinAvalExig;
    }

    public void setCrNbreMinAvalExig(String crNbreMinAvalExig) {
        CrNbreMinAvalExig = crNbreMinAvalExig;
    }

    public String getCrTxCouvCrAval() {
        return CrTxCouvCrAval;
    }

    public void setCrTxCouvCrAval(String crTxCouvCrAval) {
        CrTxCouvCrAval = crTxCouvCrAval;
    }

    public Boolean getCrIsTxCouvAvalOblig() {
        return CrIsTxCouvAvalOblig;
    }

    public void setCrIsTxCouvAvalOblig(Boolean crIsTxCouvAvalOblig) {
        CrIsTxCouvAvalOblig = crIsTxCouvAvalOblig;
    }

    public Boolean getCrIsCautionMorAvalAcc() {
        return CrIsCautionMorAvalAcc;
    }

    public void setCrIsCautionMorAvalAcc(Boolean crIsCautionMorAvalAcc) {
        CrIsCautionMorAvalAcc = crIsCautionMorAvalAcc;
    }

    public Boolean getCrIsGarBloqCptOblig() {
        return CrIsGarBloqCptOblig;
    }

    public void setCrIsGarBloqCptOblig(Boolean crIsGarBloqCptOblig) {
        CrIsGarBloqCptOblig = crIsGarBloqCptOblig;
    }

    public Boolean getCrIsGarCptEAVOn() {
        return CrIsGarCptEAVOn;
    }

    public void setCrIsGarCptEAVOn(Boolean crIsGarCptEAVOn) {
        CrIsGarCptEAVOn = crIsGarCptEAVOn;
    }

    public Boolean getCrIsGarCptEATOn() {
        return CrIsGarCptEATOn;
    }

    public void setCrIsGarCptEATOn(Boolean crIsGarCptEATOn) {
        CrIsGarCptEATOn = crIsGarCptEATOn;
    }

    public Boolean getCrIsGarCptEAPOn() {
        return CrIsGarCptEAPOn;
    }

    public void setCrIsGarCptEAPOn(Boolean crIsGarCptEAPOn) {
        CrIsGarCptEAPOn = crIsGarCptEAPOn;
    }

    public String getCrMtMaxSansAval() {
        return CrMtMaxSansAval;
    }

    public void setCrMtMaxSansAval(String crMtMaxSansAval) {
        CrMtMaxSansAval = crMtMaxSansAval;
    }

    public Boolean getCrIsAvalSansCredOn() {
        return CrIsAvalSansCredOn;
    }

    public void setCrIsAvalSansCredOn(Boolean crIsAvalSansCredOn) {
        CrIsAvalSansCredOn = crIsAvalSansCredOn;
    }

    public Boolean getCrIsTxGarMemObl() {
        return CrIsTxGarMemObl;
    }

    public void setCrIsTxGarMemObl(Boolean crIsTxGarMemObl) {
        CrIsTxGarMemObl = crIsTxGarMemObl;
    }

    public String getCrTauxGarMemb() {
        return CrTauxGarMemb;
    }

    public void setCrTauxGarMemb(String crTauxGarMemb) {
        CrTauxGarMemb = crTauxGarMemb;
    }

    public Boolean getCrIsPersMorAvalOn() {
        return CrIsPersMorAvalOn;
    }

    public void setCrIsPersMorAvalOn(Boolean crIsPersMorAvalOn) {
        CrIsPersMorAvalOn = crIsPersMorAvalOn;
    }

    public Boolean getCrIsCouvPartSOn() {
        return CrIsCouvPartSOn;
    }

    public void setCrIsCouvPartSOn(Boolean crIsCouvPartSOn) {
        CrIsCouvPartSOn = crIsCouvPartSOn;
    }

    public String getCrTxCouvPSOblig() {
        return CrTxCouvPSOblig;
    }

    public void setCrTxCouvPSOblig(String crTxCouvPSOblig) {
        CrTxCouvPSOblig = crTxCouvPSOblig;
    }

    public Boolean getCrIsAffCollCredOn() {
        return CrIsAffCollCredOn;
    }

    public void setCrIsAffCollCredOn(Boolean crIsAffCollCredOn) {
        CrIsAffCollCredOn = crIsAffCollCredOn;
    }

    public String getCrNbreAnAncMinCred() {
        return CrNbreAnAncMinCred;
    }

    public void setCrNbreAnAncMinCred(String crNbreAnAncMinCred) {
        CrNbreAnAncMinCred = crNbreAnAncMinCred;
    }

    public Boolean getCrNbAnAncNeg() {
        return CrNbAnAncNeg;
    }

    public void setCrNbAnAncNeg(Boolean crNbAnAncNeg) {
        CrNbAnAncNeg = crNbAnAncNeg;
    }

    public String getCrMtPlafondMax() {
        return CrMtPlafondMax;
    }

    public void setCrMtPlafondMax(String crMtPlafondMax) {
        CrMtPlafondMax = crMtPlafondMax;
    }

    public Boolean getCrIsMtPlafCredLeve() {
        return CrIsMtPlafCredLeve;
    }

    public void setCrIsMtPlafCredLeve(Boolean crIsMtPlafCredLeve) {
        CrIsMtPlafCredLeve = crIsMtPlafCredLeve;
    }

    public Boolean getCrIsGarMatExige() {
        return CrIsGarMatExige;
    }

    public void setCrIsGarMatExige(Boolean crIsGarMatExige) {
        CrIsGarMatExige = crIsGarMatExige;
    }

    public Boolean getCrIsFraisEtudDossOn() {
        return CrIsFraisEtudDossOn;
    }

    public void setCrIsFraisEtudDossOn(Boolean crIsFraisEtudDossOn) {
        CrIsFraisEtudDossOn = crIsFraisEtudDossOn;
    }

    public String getCrNatFrEtudDoss() {
        return CrNatFrEtudDoss;
    }

    public void setCrNatFrEtudDoss(String crNatFrEtudDoss) {
        CrNatFrEtudDoss = crNatFrEtudDoss;
    }

    public String getCrValTxFrEtudDoss() {
        return CrValTxFrEtudDoss;
    }

    public void setCrValTxFrEtudDoss(String crValTxFrEtudDoss) {
        CrValTxFrEtudDoss = crValTxFrEtudDoss;
    }

    public String getCrBaseTxFrEtudDoss() {
        return CrBaseTxFrEtudDoss;
    }

    public void setCrBaseTxFrEtudDoss(String crBaseTxFrEtudDoss) {
        CrBaseTxFrEtudDoss = crBaseTxFrEtudDoss;
    }

    public Boolean getCrIsFraisDeblocCredOn() {
        return CrIsFraisDeblocCredOn;
    }

    public void setCrIsFraisDeblocCredOn(Boolean crIsFraisDeblocCredOn) {
        CrIsFraisDeblocCredOn = crIsFraisDeblocCredOn;
    }

    public String getCrNatFraisDeblocCred() {
        return CrNatFraisDeblocCred;
    }

    public void setCrNatFraisDeblocCred(String crNatFraisDeblocCred) {
        CrNatFraisDeblocCred = crNatFraisDeblocCred;
    }

    public String getCrValTxFraisDeblocCred() {
        return CrValTxFraisDeblocCred;
    }

    public void setCrValTxFraisDeblocCred(String crValTxFraisDeblocCred) {
        CrValTxFraisDeblocCred = crValTxFraisDeblocCred;
    }

    public String getCrBaseTxFraisDeblocCred() {
        return CrBaseTxFraisDeblocCred;
    }

    public void setCrBaseTxFraisDeblocCred(String crBaseTxFraisDeblocCred) {
        CrBaseTxFraisDeblocCred = crBaseTxFraisDeblocCred;
    }

    public Boolean getCrIsFraisDecaissCredOn() {
        return CrIsFraisDecaissCredOn;
    }

    public void setCrIsFraisDecaissCredOn(Boolean crIsFraisDecaissCredOn) {
        CrIsFraisDecaissCredOn = crIsFraisDecaissCredOn;
    }

    public String getCrNatFraisDecaissCred() {
        return CrNatFraisDecaissCred;
    }

    public void setCrNatFraisDecaissCred(String crNatFraisDecaissCred) {
        CrNatFraisDecaissCred = crNatFraisDecaissCred;
    }

    public String getCrValTxFraisDecaissCred() {
        return CrValTxFraisDecaissCred;
    }

    public void setCrValTxFraisDecaissCred(String crValTxFraisDecaissCred) {
        CrValTxFraisDecaissCred = crValTxFraisDecaissCred;
    }

    public String getCrBaseFraisDecaissCred() {
        return CrBaseFraisDecaissCred;
    }

    public void setCrBaseFraisDecaissCred(String crBaseFraisDecaissCred) {
        CrBaseFraisDecaissCred = crBaseFraisDecaissCred;
    }

    public Boolean getCrIsFraisEtudByDAV() {
        return CrIsFraisEtudByDAV;
    }

    public void setCrIsFraisEtudByDAV(Boolean crIsFraisEtudByDAV) {
        CrIsFraisEtudByDAV = crIsFraisEtudByDAV;
    }

    public Boolean getCrIsFraisDeblocByDAV() {
        return CrIsFraisDeblocByDAV;
    }

    public void setCrIsFraisDeblocByDAV(Boolean crIsFraisDeblocByDAV) {
        CrIsFraisDeblocByDAV = crIsFraisDeblocByDAV;
    }

    public Boolean getCrIsFraisDecaissByDAV() {
        return CrIsFraisDecaissByDAV;
    }

    public void setCrIsFraisDecaissByDAV(Boolean crIsFraisDecaissByDAV) {
        CrIsFraisDecaissByDAV = crIsFraisDecaissByDAV;
    }

    public Boolean getCrIsModDecaissByObjet() {
        return CrIsModDecaissByObjet;
    }

    public void setCrIsModDecaissByObjet(Boolean crIsModDecaissByObjet) {
        CrIsModDecaissByObjet = crIsModDecaissByObjet;
    }

    public Boolean getCrIsDeblocTransfDAVOn() {
        return CrIsDeblocTransfDAVOn;
    }

    public void setCrIsDeblocTransfDAVOn(Boolean crIsDeblocTransfDAVOn) {
        CrIsDeblocTransfDAVOn = crIsDeblocTransfDAVOn;
    }

    public Boolean getCrIsMtPlafByObjet() {
        return CrIsMtPlafByObjet;
    }

    public void setCrIsMtPlafByObjet(Boolean crIsMtPlafByObjet) {
        CrIsMtPlafByObjet = crIsMtPlafByObjet;
    }

    public String getCrModeRemb() {
        return CrModeRemb;
    }

    public void setCrModeRemb(String crModeRemb) {
        CrModeRemb = crModeRemb;
    }

    public Boolean getCrIsCptEATRemCredOn() {
        return CrIsCptEATRemCredOn;
    }

    public void setCrIsCptEATRemCredOn(Boolean crIsCptEATRemCredOn) {
        CrIsCptEATRemCredOn = crIsCptEATRemCredOn;
    }

    public Boolean getCrIsCptEAPRemCredOn() {
        return CrIsCptEAPRemCredOn;
    }

    public void setCrIsCptEAPRemCredOn(Boolean crIsCptEAPRemCredOn) {
        CrIsCptEAPRemCredOn = crIsCptEAPRemCredOn;
    }

    public Boolean getCrIsInterOffSiCapRembAnt() {
        return CrIsInterOffSiCapRembAnt;
    }

    public void setCrIsInterOffSiCapRembAnt(Boolean crIsInterOffSiCapRembAnt) {
        CrIsInterOffSiCapRembAnt = crIsInterOffSiCapRembAnt;
    }

    public String getCrTxInterEchNHon() {
        return CrTxInterEchNHon;
    }

    public void setCrTxInterEchNHon(String crTxInterEchNHon) {
        CrTxInterEchNHon = crTxInterEchNHon;
    }

    public String getCrBaseInterEchNHon() {
        return CrBaseInterEchNHon;
    }

    public void setCrBaseInterEchNHon(String crBaseInterEchNHon) {
        CrBaseInterEchNHon = crBaseInterEchNHon;
    }

    public String getCrPlanningRembCred() {
        return CrPlanningRembCred;
    }

    public void setCrPlanningRembCred(String crPlanningRembCred) {
        CrPlanningRembCred = crPlanningRembCred;
    }

    public Boolean getCrIsRappDatEchCred() {
        return CrIsRappDatEchCred;
    }

    public void setCrIsRappDatEchCred(Boolean crIsRappDatEchCred) {
        CrIsRappDatEchCred = crIsRappDatEchCred;
    }

    public String getCrModelTextRappEchRemb() {
        return CrModelTextRappEchRemb;
    }

    public void setCrModelTextRappEchRemb(String crModelTextRappEchRemb) {
        CrModelTextRappEchRemb = crModelTextRappEchRemb;
    }

    public String getCrNbreJrAvantDatEch() {
        return CrNbreJrAvantDatEch;
    }

    public void setCrNbreJrAvantDatEch(String crNbreJrAvantDatEch) {
        CrNbreJrAvantDatEch = crNbreJrAvantDatEch;
    }

    public String getCrNbreJrApreEchSiNHon() {
        return CrNbreJrApreEchSiNHon;
    }

    public void setCrNbreJrApreEchSiNHon(String crNbreJrApreEchSiNHon) {
        CrNbreJrApreEchSiNHon = crNbreJrApreEchSiNHon;
    }

    public String getCrUser() {
        return CrUser;
    }

    public void setCrUser(String crUser) {
        CrUser = crUser;
    }

    public String getCrDateHCree() {
        return CrDateHCree;
    }

    public void setCrDateHCree(String crDateHCree) {
        CrDateHCree = crDateHCree;
    }

    public String getCrUserModif() {
        return CrUserModif;
    }

    public void setCrUserModif(String crUserModif) {
        CrUserModif = crUserModif;
    }

    public String getCrDatHModif() {
        return CrDatHModif;
    }

    public void setCrDatHModif(String crDatHModif) {
        CrDatHModif = crDatHModif;
    }

    public String getCrCaisseId() {
        return CrCaisseId;
    }

    public void setCrCaisseId(String crCaisseId) {
        CrCaisseId = crCaisseId;
    }

    public String getCrGuichetId() {
        return CrGuichetId;
    }

    public void setCrGuichetId(String crGuichetId) {
        CrGuichetId = crGuichetId;
    }
}
