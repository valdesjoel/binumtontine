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
    private String CrBaseTxInter;
    private String CrIsTxIntNeg;
    private String CrNbreAvalDmde;
    private String CrNbreMinAvalExig;
    private String CrTxCouvCrAval;
    private String CrIsTxCouvAvalOblig;
    private String CrIsCautionMorAvalAcc;
    private String CrIsGarBloqCptOblig;
    private String CrIsGarCptEAVOn;
    private String CrIsGarCptEATOn;
    private String CrIsGarCptEAPOn;
    private String CrMtMaxSansAval;
    private String CrIsAvalSansCredOn;
    private String CrIsTxGarMemObl;
    private String CrTauxGarMemb;
    private String CrIsPersMorAvalOn;
    private String CrIsCouvPartSOn;
    private String CrTxCouvPSOblig;
    private String CrIsAffCollCredOn;
    private String CrNbreAnAncMinCred;
    private String CrNbAnAncNeg;
    private String CrMtPlafondMax;
    private String CrIsMtPlafCredLeve;
    private String CrIsGarMatExige;
    private String CrIsFraisEtudDossOn;
    private String CrNatFrEtudDoss;
    private String CrValTxFrEtudDoss;
    private String CrBaseTxFrEtudDoss;
    private String CrIsFraisDeblocCredOn;
    private String CrNatFraisDeblocCred;
    private String CrValTxFraisDeblocCred;
    private String CrBaseTxFraisDeblocCred;
    private String CrIsFraisDecaissCredOn;
    private String CrNatFraisDecaissCred;
    private String CrValTxFraisDecaissCred;
    private String CrBaseFraisDecaissCred;
    private String CrIsFraisEtudByDAV;
    private String CrIsFraisDeblocByDAV;
    private String CrIsFraisDecaissByDAV;
    private String CrIsModDecaissByObjet;
    private String CrIsDeblocTransfDAVOn;
    private String CrIsMtPlafByObjet;
    private String CrModeRemb;
    private String CrIsCptEATRemCredOn;
    private String CrIsCptEAPRemCredOn;
    private String CrIsInterOffSiCapRembAnt;
    private String CrTxInterEchNHon;
    private String CrBaseInterEchNHon;
    private String CrPlanningRembCred;
    private String CrIsRappDatEchCred;
    private String CrModelTextRappEchRemb;
    private String CrNbreJrAvantDatEch;
    private String CrNbreJrApreEchSiNHon;
    private String CrUser;
    private String CrDateHCree;
    private String CrUserModif;
    private String CrDatHModif;
    private String CrCaisseId;
    private String CrGuichetId;
    private String CrIsTxIntDegressif;
    private String CrModeCalcInteret;
    private String CrPeriodCalcInteret;
    private String CrNbreJrDelaiGrace;
    private String CrIsDelaiGraceNegoc;
    private String CrIsJoursOuvresOnly;
    private String CrIsTxInteretJrOn;
    private String CrNatureJrTxIntJr;
    private String CrNatureTxPenRet;
    private String CrValTxPenRet;
    private String CrBaseTxPenRet;
    private String CrPeriodNatureTxPenRet;
    private String CrIsTxPenRetardOn;
    private String CrNatureJrTxPenRet;
    private String CrNatureTxInt_IntRetCred;
    private String CrTauxInt_IntRetCred;
    private String CrBasexInt_IntRetCred;
    private String CrPeriod_IntRetCred;
    private String CrIsTxIntJrOn_IntRetCred;
    private String CrNatJrTxIntJr_IntRetCred;
    private String CrIsCpteEAVOnRembCred;
    private String CrIsCpteCourantOnRembCred;
    private String CrIsIntRetCreditOn;

    private String CrIsSoldPenRetObligSiNewEchCred;
    private String CrIsSoldIntRetObligSiNewEchCred;
    private String CrIsTauxInteretAnOn;
    private String CrNatureTxIntAn;
    private String CrTauxValTxIntAn;
    private String CrBaseTxIntAn;

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
    public static final String KEY_CREDIT_Base_TxInter = "CrBaseTxInter";
    public static final String KEY_CrIsTauxInteretAnOn = "CrIsTauxInteretAnOn";
    public static final String KEY_CrNatureTxIntAn = "CrNatureTxIntAn";
    public static final String KEY_CrTauxValTxIntAn = "CrTauxValTxIntAn";
    public static final String KEY_CrBaseTxIntAn = "CrBaseTxIntAn";
    public static final String KEY_CrIsSoldPenRetObligSiNewEchCred = "CrIsSoldPenRetObligSiNewEchCred";
    public static final String KEY_CrIsSoldIntRetObligSiNewEchCred = "CrIsSoldIntRetObligSiNewEchCred";
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
    public static final String KEY_CrIsTxIntDegressif ="CrIsTxIntDegressif";
    public static final String KEY_CrModeCalcInteret ="CrModeCalcInteret";
    public static final String KEY_CrPeriodCalcInteret ="CrPeriodCalcInteret";
    public static final String KEY_CrNbreJrDelaiGrace ="CrNbreJrDelaiGrace";
    public static final String KEY_CrIsDelaiGraceNegoc ="CrIsDelaiGraceNegoc";
    public static final String KEY_CrIsJoursOuvresOnly ="CrIsJoursOuvresOnly";
    public static final String KEY_CrIsTxInteretJrOn ="CrIsTxInteretJrOn";
    public static final String KEY_CrNatureJrTxIntJr ="CrNatureJrTxIntJr";
    public static final String KEY_CrNatureTxPenRet ="CrNatureTxPenRet";
    public static final String KEY_CrValTxPenRet ="CrValTxPenRet";
    public static final String KEY_CrBaseTxPenRet ="CrBaseTxPenRet";
    public static final String KEY_CrPeriodNatureTxPenRet ="CrPeriodNatureTxPenRet";
    public static final String KEY_CrIsTxPenRetardOn ="CrIsTxPenRetardOn";
    public static final String KEY_CrNatureJrTxPenRet ="CrNatureJrTxPenRet";
    public static final String KEY_CrNatureTxInt_IntRetCred ="CrNatureTxInt_IntRetCred";
    public static final String KEY_CrTauxInt_IntRetCred ="CrTauxInt_IntRetCred";
    public static final String KEY_CrBasexInt_IntRetCred ="CrBasexInt_IntRetCred";
    public static final String KEY_CrPeriod_IntRetCred ="CrPeriod_IntRetCred";
    public static final String KEY_CrIsTxIntJrOn_IntRetCred ="CrIsTxIntJrOn_IntRetCred";
    public static final String KEY_CrNatJrTxIntJr_IntRetCred ="CrNatJrTxIntJr_IntRetCred";
    public static final String KEY_CrIsCpteEAVOnRembCred ="CrIsCpteEAVOnRembCred";
    public static final String KEY_CrIsCpteCourantOnRembCred ="CrIsCpteCourantOnRembCred";
    public static final String KEY_CrIsIntRetCreditOn ="CrIsIntRetCreditOn";
// JSON Parameters END


    public Credit() {
    }

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
     * @param crIsTxIntDegressif
     * @param crModeCalcInteret
     * @param crPeriodCalcInteret
     */
    public Credit(String crCode, String crLibelle, String crDureeMin, String crDureeMax, String crNaturePas,
                  String crNbreUPas, String crTypTxInter, String crValTxInter, String crBaseTxInter, String crIsTxIntNeg,
                  String crNbreAvalDmde, String crNbreMinAvalExig, String crTxCouvCrAval, String crIsTxCouvAvalOblig,
                  String crIsCautionMorAvalAcc, String crIsGarBloqCptOblig, String crIsGarCptEAVOn, String crIsGarCptEATOn,
                  String crIsGarCptEAPOn, String crMtMaxSansAval, String crIsAvalSansCredOn, String crIsTxGarMemObl,
                  String crTauxGarMemb, String crIsPersMorAvalOn, String crIsCouvPartSOn, String crTxCouvPSOblig,
                  String crIsAffCollCredOn, String crNbreAnAncMinCred, String crNbAnAncNeg, String crMtPlafondMax,
                  String crIsMtPlafCredLeve, String crIsGarMatExige, String crIsFraisEtudDossOn, String crNatFrEtudDoss,
                  String crValTxFrEtudDoss, String crBaseTxFrEtudDoss, String crIsFraisDeblocCredOn, String crNatFraisDeblocCred,
                  String crValTxFraisDeblocCred, String crBaseTxFraisDeblocCred, String crIsFraisDecaissCredOn,
                  String crNatFraisDecaissCred, String crValTxFraisDecaissCred, String crBaseFraisDecaissCred,
                  String crIsFraisEtudByDAV, String crIsFraisDeblocByDAV, String crIsFraisDecaissByDAV,
                  String crIsModDecaissByObjet, String crIsDeblocTransfDAVOn, String crIsMtPlafByObjet,
                  String crModeRemb, String crIsCptEATRemCredOn, String crIsCptEAPRemCredOn, String crIsInterOffSiCapRembAnt,
                  String crTxInterEchNHon, String crBaseInterEchNHon, String crPlanningRembCred, String crIsRappDatEchCred,
                  String crModelTextRappEchRemb, String crNbreJrAvantDatEch, String crNbreJrApreEchSiNHon,
                  String crUser, String crDateHCree, String crUserModif, String crDatHModif, String crCaisseId, String crGuichetId,
                  String crIsTxIntDegressif, String crModeCalcInteret, String crPeriodCalcInteret, String crNbreJrDelaiGrace,
                  String crIsDelaiGraceNegoc, String crIsJoursOuvresOnly, String crIsTxInteretJrOn, String crNatureJrTxIntJr,
                  String crNatureTxPenRet, String crValTxPenRet, String crBaseTxPenRet, String crPeriodNatureTxPenRet, String crIsTxPenRetardOn,
                  String crNatureJrTxPenRet, String crNatureTxInt_IntRetCred, String crTauxInt_IntRetCred, String crBasexInt_IntRetCred,
                  String crPeriod_IntRetCred, String crIsTxIntJrOn_IntRetCred, String crNatJrTxIntJr_IntRetCred, String crIsCpteEAVOnRembCred,
                  String crIsCpteCourantOnRembCred, String crIsIntRetCreditOn) {
        CrCode = crCode;
        CrLibelle = crLibelle;
        CrDureeMin = crDureeMin;
        CrDureeMax = crDureeMax;
        CrNaturePas = crNaturePas;
        CrNbreUPas = crNbreUPas;
        CrTypTxInter = crTypTxInter;
        CrValTxInter = crValTxInter;
        CrBaseTxInter = crBaseTxInter;
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
        CrIsTxIntDegressif = crIsTxIntDegressif;
        CrModeCalcInteret = crModeCalcInteret;
        CrPeriodCalcInteret = crPeriodCalcInteret;
        CrNbreJrDelaiGrace = crNbreJrDelaiGrace;
        CrIsDelaiGraceNegoc = crIsDelaiGraceNegoc;
        CrIsJoursOuvresOnly = crIsJoursOuvresOnly;
        CrIsTxInteretJrOn = crIsTxInteretJrOn;
        CrNatureJrTxIntJr = crNatureJrTxIntJr;
        CrNatureTxPenRet = crNatureTxPenRet;
        CrValTxPenRet = crValTxPenRet;
        CrBaseTxPenRet = crBaseTxPenRet;
        CrPeriodNatureTxPenRet = crPeriodNatureTxPenRet;
        CrIsTxPenRetardOn = crIsTxPenRetardOn;
        CrNatureJrTxPenRet = crNatureJrTxPenRet;
        CrNatureTxInt_IntRetCred = crNatureTxInt_IntRetCred;
        CrTauxInt_IntRetCred = crTauxInt_IntRetCred;
        CrBasexInt_IntRetCred = crBasexInt_IntRetCred;
        CrPeriod_IntRetCred = crPeriod_IntRetCred;
        CrIsTxIntJrOn_IntRetCred = crIsTxIntJrOn_IntRetCred;
        CrNatJrTxIntJr_IntRetCred = crNatJrTxIntJr_IntRetCred;
        CrIsCpteEAVOnRembCred = crIsCpteEAVOnRembCred;
        CrIsCpteCourantOnRembCred = crIsCpteCourantOnRembCred;
        CrIsIntRetCreditOn = crIsIntRetCreditOn;
    }

    public Credit(String crNumero, String crCode, String crLibelle, String crDureeMin, String crDureeMax, String crNaturePas,
                  String crNbreUPas, String crTypTxInter, String crValTxInter,String crBaseTxInter, String crIsTxIntNeg, String crNbreAvalDmde,
                  String crNbreMinAvalExig, String crTxCouvCrAval, String crIsTxCouvAvalOblig, String crIsCautionMorAvalAcc,
                  String crIsGarBloqCptOblig, String crIsGarCptEAVOn, String crIsGarCptEATOn, String crIsGarCptEAPOn,
                  String crMtMaxSansAval, String crIsAvalSansCredOn, String crIsTxGarMemObl, String crTauxGarMemb,
                  String crIsPersMorAvalOn, String crIsCouvPartSOn, String crTxCouvPSOblig, String crIsAffCollCredOn,
                  String crNbreAnAncMinCred, String crNbAnAncNeg, String crMtPlafondMax, String crIsMtPlafCredLeve,
                  String crIsGarMatExige, String crIsFraisEtudDossOn, String crNatFrEtudDoss, String crValTxFrEtudDoss,
                  String crBaseTxFrEtudDoss, String crIsFraisDeblocCredOn, String crNatFraisDeblocCred, String crValTxFraisDeblocCred,
                  String crBaseTxFraisDeblocCred, String crIsFraisDecaissCredOn, String crNatFraisDecaissCred,
                  String crValTxFraisDecaissCred, String crBaseFraisDecaissCred, String crIsFraisEtudByDAV,
                  String crIsFraisDeblocByDAV, String crIsFraisDecaissByDAV, String crIsModDecaissByObjet,
                  String crIsDeblocTransfDAVOn, String crIsMtPlafByObjet, String crModeRemb, String crIsCptEATRemCredOn,
                  String crIsCptEAPRemCredOn, String crIsInterOffSiCapRembAnt, String crTxInterEchNHon, String crBaseInterEchNHon,
                  String crPlanningRembCred, String crIsRappDatEchCred, String crModelTextRappEchRemb, String crNbreJrAvantDatEch,
                  String crNbreJrApreEchSiNHon,
                  String crUser, String crDateHCree, String crUserModif, String crDatHModif, String crCaisseId, String crGuichetId, String crIsTxIntDegressif,
                  String crModeCalcInteret, String crPeriodCalcInteret, String crNbreJrDelaiGrace,String crIsDelaiGraceNegoc,
                  String crIsJoursOuvresOnly, String crIsTxInteretJrOn, String crNatureJrTxIntJr, String crNatureTxPenRet,
                  String crValTxPenRet, String crBaseTxPenRet, String crPeriodNatureTxPenRet, String crIsTxPenRetardOn, String crNatureJrTxPenRet,
                  String crNatureTxInt_IntRetCred, String crTauxInt_IntRetCred, String crBasexInt_IntRetCred, String crPeriod_IntRetCred,
                  String crIsTxIntJrOn_IntRetCred, String crNatJrTxIntJr_IntRetCred) {
        CrNumero = crNumero;
        CrCode = crCode;
        CrLibelle = crLibelle;
        CrDureeMin = crDureeMin;
        CrDureeMax = crDureeMax;
        CrNaturePas = crNaturePas;
        CrNbreUPas = crNbreUPas;
        CrTypTxInter = crTypTxInter;
        CrValTxInter = crValTxInter;
        CrBaseTxInter = crBaseTxInter;
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
        CrIsTxIntDegressif = crIsTxIntDegressif;
        CrModeCalcInteret = crModeCalcInteret;
        CrPeriodCalcInteret = crPeriodCalcInteret;
        CrNbreJrDelaiGrace = crNbreJrDelaiGrace;
        CrIsDelaiGraceNegoc = crIsDelaiGraceNegoc;
        CrIsJoursOuvresOnly = crIsJoursOuvresOnly;
        CrIsTxInteretJrOn = crIsTxInteretJrOn;
        CrNatureJrTxIntJr = crNatureJrTxIntJr;
        CrNatureTxPenRet = crNatureTxPenRet;
        CrValTxPenRet = crValTxPenRet;
        CrBaseTxPenRet = crBaseTxPenRet;
        CrPeriodNatureTxPenRet = crPeriodNatureTxPenRet;
        CrIsTxPenRetardOn = crIsTxPenRetardOn;
        CrNatureJrTxPenRet = crNatureJrTxPenRet;
        CrNatureTxInt_IntRetCred = crNatureTxInt_IntRetCred;
        CrTauxInt_IntRetCred = crTauxInt_IntRetCred;
        CrBasexInt_IntRetCred = crBasexInt_IntRetCred;
        CrPeriod_IntRetCred = crPeriod_IntRetCred;
        CrIsTxIntJrOn_IntRetCred = crIsTxIntJrOn_IntRetCred;
        CrNatJrTxIntJr_IntRetCred = crNatJrTxIntJr_IntRetCred;
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

    public String getCrBaseTxInter() {
        return CrBaseTxInter;
    }

    public void setCrBaseTxInter(String crBaseTxInter) {
        CrBaseTxInter = crBaseTxInter;
    }

    public String getCrIsDelaiGraceNegoc() {
        return CrIsDelaiGraceNegoc;
    }

    public void setCrIsDelaiGraceNegoc(String crIsDelaiGraceNegoc) {
        CrIsDelaiGraceNegoc = crIsDelaiGraceNegoc;
    }

    public String getCrIsJoursOuvresOnly() {
        return CrIsJoursOuvresOnly;
    }

    public void setCrIsJoursOuvresOnly(String crIsJoursOuvresOnly) {
        CrIsJoursOuvresOnly = crIsJoursOuvresOnly;
    }

    public String getCrIsTxIntNeg() {
        return CrIsTxIntNeg;
    }

    public void setCrIsTxIntNeg(String crIsTxIntNeg) {
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

    public String getCrIsTxCouvAvalOblig() {
        return CrIsTxCouvAvalOblig;
    }

    public void setCrIsTxCouvAvalOblig(String crIsTxCouvAvalOblig) {
        CrIsTxCouvAvalOblig = crIsTxCouvAvalOblig;
    }

    public String getCrIsCautionMorAvalAcc() {
        return CrIsCautionMorAvalAcc;
    }

    public void setCrIsCautionMorAvalAcc(String crIsCautionMorAvalAcc) {
        CrIsCautionMorAvalAcc = crIsCautionMorAvalAcc;
    }

    public String getCrIsGarBloqCptOblig() {
        return CrIsGarBloqCptOblig;
    }

    public void setCrIsGarBloqCptOblig(String crIsGarBloqCptOblig) {
        CrIsGarBloqCptOblig = crIsGarBloqCptOblig;
    }

    public String getCrIsGarCptEAVOn() {
        return CrIsGarCptEAVOn;
    }

    public void setCrIsGarCptEAVOn(String crIsGarCptEAVOn) {
        CrIsGarCptEAVOn = crIsGarCptEAVOn;
    }

    public String getCrIsGarCptEATOn() {
        return CrIsGarCptEATOn;
    }

    public void setCrIsGarCptEATOn(String crIsGarCptEATOn) {
        CrIsGarCptEATOn = crIsGarCptEATOn;
    }

    public String getCrIsGarCptEAPOn() {
        return CrIsGarCptEAPOn;
    }

    public void setCrIsGarCptEAPOn(String crIsGarCptEAPOn) {
        CrIsGarCptEAPOn = crIsGarCptEAPOn;
    }

    public String getCrMtMaxSansAval() {
        return CrMtMaxSansAval;
    }

    public void setCrMtMaxSansAval(String crMtMaxSansAval) {
        CrMtMaxSansAval = crMtMaxSansAval;
    }

    public String getCrIsAvalSansCredOn() {
        return CrIsAvalSansCredOn;
    }

    public void setCrIsAvalSansCredOn(String crIsAvalSansCredOn) {
        CrIsAvalSansCredOn = crIsAvalSansCredOn;
    }

    public String getCrIsTxGarMemObl() {
        return CrIsTxGarMemObl;
    }

    public void setCrIsTxGarMemObl(String crIsTxGarMemObl) {
        CrIsTxGarMemObl = crIsTxGarMemObl;
    }

    public String getCrTauxGarMemb() {
        return CrTauxGarMemb;
    }

    public void setCrTauxGarMemb(String crTauxGarMemb) {
        CrTauxGarMemb = crTauxGarMemb;
    }

    public String getCrIsPersMorAvalOn() {
        return CrIsPersMorAvalOn;
    }

    public void setCrIsPersMorAvalOn(String crIsPersMorAvalOn) {
        CrIsPersMorAvalOn = crIsPersMorAvalOn;
    }

    public String getCrIsCouvPartSOn() {
        return CrIsCouvPartSOn;
    }

    public void setCrIsCouvPartSOn(String crIsCouvPartSOn) {
        CrIsCouvPartSOn = crIsCouvPartSOn;
    }

    public String getCrTxCouvPSOblig() {
        return CrTxCouvPSOblig;
    }

    public void setCrTxCouvPSOblig(String crTxCouvPSOblig) {
        CrTxCouvPSOblig = crTxCouvPSOblig;
    }

    public String getCrIsAffCollCredOn() {
        return CrIsAffCollCredOn;
    }

    public void setCrIsAffCollCredOn(String crIsAffCollCredOn) {
        CrIsAffCollCredOn = crIsAffCollCredOn;
    }

    public String getCrNbreAnAncMinCred() {
        return CrNbreAnAncMinCred;
    }

    public void setCrNbreAnAncMinCred(String crNbreAnAncMinCred) {
        CrNbreAnAncMinCred = crNbreAnAncMinCred;
    }

    public String getCrNbAnAncNeg() {
        return CrNbAnAncNeg;
    }

    public void setCrNbAnAncNeg(String crNbAnAncNeg) {
        CrNbAnAncNeg = crNbAnAncNeg;
    }

    public String getCrMtPlafondMax() {
        return CrMtPlafondMax;
    }

    public void setCrMtPlafondMax(String crMtPlafondMax) {
        CrMtPlafondMax = crMtPlafondMax;
    }

    public String getCrIsMtPlafCredLeve() {
        return CrIsMtPlafCredLeve;
    }

    public void setCrIsMtPlafCredLeve(String crIsMtPlafCredLeve) {
        CrIsMtPlafCredLeve = crIsMtPlafCredLeve;
    }

    public String getCrIsGarMatExige() {
        return CrIsGarMatExige;
    }

    public void setCrIsGarMatExige(String crIsGarMatExige) {
        CrIsGarMatExige = crIsGarMatExige;
    }

    public String getCrIsFraisEtudDossOn() {
        return CrIsFraisEtudDossOn;
    }

    public void setCrIsFraisEtudDossOn(String crIsFraisEtudDossOn) {
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

    public String getCrIsFraisDeblocCredOn() {
        return CrIsFraisDeblocCredOn;
    }

    public void setCrIsFraisDeblocCredOn(String crIsFraisDeblocCredOn) {
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

    public String getCrIsFraisDecaissCredOn() {
        return CrIsFraisDecaissCredOn;
    }

    public void setCrIsFraisDecaissCredOn(String crIsFraisDecaissCredOn) {
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

    public String getCrIsFraisEtudByDAV() {
        return CrIsFraisEtudByDAV;
    }

    public void setCrIsFraisEtudByDAV(String crIsFraisEtudByDAV) {
        CrIsFraisEtudByDAV = crIsFraisEtudByDAV;
    }

    public String getCrIsFraisDeblocByDAV() {
        return CrIsFraisDeblocByDAV;
    }

    public void setCrIsFraisDeblocByDAV(String crIsFraisDeblocByDAV) {
        CrIsFraisDeblocByDAV = crIsFraisDeblocByDAV;
    }

    public String getCrIsFraisDecaissByDAV() {
        return CrIsFraisDecaissByDAV;
    }

    public void setCrIsFraisDecaissByDAV(String crIsFraisDecaissByDAV) {
        CrIsFraisDecaissByDAV = crIsFraisDecaissByDAV;
    }

    public String getCrIsModDecaissByObjet() {
        return CrIsModDecaissByObjet;
    }

    public void setCrIsModDecaissByObjet(String crIsModDecaissByObjet) {
        CrIsModDecaissByObjet = crIsModDecaissByObjet;
    }

    public String getCrIsDeblocTransfDAVOn() {
        return CrIsDeblocTransfDAVOn;
    }

    public void setCrIsDeblocTransfDAVOn(String crIsDeblocTransfDAVOn) {
        CrIsDeblocTransfDAVOn = crIsDeblocTransfDAVOn;
    }

    public String getCrIsMtPlafByObjet() {
        return CrIsMtPlafByObjet;
    }

    public void setCrIsMtPlafByObjet(String crIsMtPlafByObjet) {
        CrIsMtPlafByObjet = crIsMtPlafByObjet;
    }

    public String getCrModeRemb() {
        return CrModeRemb;
    }

    public void setCrModeRemb(String crModeRemb) {
        CrModeRemb = crModeRemb;
    }

    public String getCrIsCptEATRemCredOn() {
        return CrIsCptEATRemCredOn;
    }

    public void setCrIsCptEATRemCredOn(String crIsCptEATRemCredOn) {
        CrIsCptEATRemCredOn = crIsCptEATRemCredOn;
    }

    public String getCrIsCptEAPRemCredOn() {
        return CrIsCptEAPRemCredOn;
    }

    public void setCrIsCptEAPRemCredOn(String crIsCptEAPRemCredOn) {
        CrIsCptEAPRemCredOn = crIsCptEAPRemCredOn;
    }

    public String getCrIsInterOffSiCapRembAnt() {
        return CrIsInterOffSiCapRembAnt;
    }

    public void setCrIsInterOffSiCapRembAnt(String crIsInterOffSiCapRembAnt) {
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

    public String getCrIsRappDatEchCred() {
        return CrIsRappDatEchCred;
    }

    public void setCrIsRappDatEchCred(String crIsRappDatEchCred) {
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

    public String getCrIsTxIntDegressif() {
        return CrIsTxIntDegressif;
    }

    public void setCrIsTxIntDegressif(String crIsTxIntDegressif) {
        CrIsTxIntDegressif = crIsTxIntDegressif;
    }

    public String getCrModeCalcInteret() {
        return CrModeCalcInteret;
    }

    public void setCrModeCalcInteret(String crModeCalcInteret) {
        CrModeCalcInteret = crModeCalcInteret;
    }

    public String getCrPeriodCalcInteret() {
        return CrPeriodCalcInteret;
    }

    public void setCrPeriodCalcInteret(String crPeriodCalcInteret) {
        CrPeriodCalcInteret = crPeriodCalcInteret;
    }

    public String getCrNbreJrDelaiGrace() {
        return CrNbreJrDelaiGrace;
    }

    public void setCrNbreJrDelaiGrace(String crNbreJrDelaiGrace) {
        CrNbreJrDelaiGrace = crNbreJrDelaiGrace;
    }

    public String getCrIsTxInteretJrOn() {
        return CrIsTxInteretJrOn;
    }

    public void setCrIsTxInteretJrOn(String crIsTxInteretJrOn) {
        CrIsTxInteretJrOn = crIsTxInteretJrOn;
    }

    public String getCrNatureJrTxIntJr() {
        return CrNatureJrTxIntJr;
    }

    public void setCrNatureJrTxIntJr(String crNatureJrTxIntJr) {
        CrNatureJrTxIntJr = crNatureJrTxIntJr;
    }

    public String getCrNatureTxPenRet() {
        return CrNatureTxPenRet;
    }

    public void setCrNatureTxPenRet(String crNatureTxPenRet) {
        CrNatureTxPenRet = crNatureTxPenRet;
    }

    public String getCrValTxPenRet() {
        return CrValTxPenRet;
    }

    public void setCrValTxPenRet(String crValTxPenRet) {
        CrValTxPenRet = crValTxPenRet;
    }

    public String getCrBaseTxPenRet() {
        return CrBaseTxPenRet;
    }

    public void setCrBaseTxPenRet(String crBaseTxPenRet) {
        CrBaseTxPenRet = crBaseTxPenRet;
    }

    public String getCrPeriodNatureTxPenRet() {
        return CrPeriodNatureTxPenRet;
    }

    public void setCrPeriodNatureTxPenRet(String crPeriodNatureTxPenRet) {
        CrPeriodNatureTxPenRet = crPeriodNatureTxPenRet;
    }

    public String getCrIsTxPenRetardOn() {
        return CrIsTxPenRetardOn;
    }

    public void setCrIsTxPenRetardOn(String crIsTxPenRetardOn) {
        CrIsTxPenRetardOn = crIsTxPenRetardOn;
    }

    public String getCrNatureJrTxPenRet() {
        return CrNatureJrTxPenRet;
    }

    public void setCrNatureJrTxPenRet(String crNatureJrTxPenRet) {
        CrNatureJrTxPenRet = crNatureJrTxPenRet;
    }

    public String getCrNatureTxInt_IntRetCred() {
        return CrNatureTxInt_IntRetCred;
    }

    public void setCrNatureTxInt_IntRetCred(String crNatureTxInt_IntRetCred) {
        CrNatureTxInt_IntRetCred = crNatureTxInt_IntRetCred;
    }

    public String getCrTauxInt_IntRetCred() {
        return CrTauxInt_IntRetCred;
    }

    public void setCrTauxInt_IntRetCred(String crTauxInt_IntRetCred) {
        CrTauxInt_IntRetCred = crTauxInt_IntRetCred;
    }

    public String getCrBasexInt_IntRetCred() {
        return CrBasexInt_IntRetCred;
    }

    public void setCrBasexInt_IntRetCred(String crBasexInt_IntRetCred) {
        CrBasexInt_IntRetCred = crBasexInt_IntRetCred;
    }

    public String getCrPeriod_IntRetCred() {
        return CrPeriod_IntRetCred;
    }

    public void setCrPeriod_IntRetCred(String crPeriod_IntRetCred) {
        CrPeriod_IntRetCred = crPeriod_IntRetCred;
    }

    public String getCrIsTxIntJrOn_IntRetCred() {
        return CrIsTxIntJrOn_IntRetCred;
    }

    public void setCrIsTxIntJrOn_IntRetCred(String crIsTxIntJrOn_IntRetCred) {
        CrIsTxIntJrOn_IntRetCred = crIsTxIntJrOn_IntRetCred;
    }

    public String getCrNatJrTxIntJr_IntRetCred() {
        return CrNatJrTxIntJr_IntRetCred;
    }

    public void setCrNatJrTxIntJr_IntRetCred(String crNatJrTxIntJr_IntRetCred) {
        CrNatJrTxIntJr_IntRetCred = crNatJrTxIntJr_IntRetCred;
    }

    public String getCrIsCpteEAVOnRembCred() {
        return CrIsCpteEAVOnRembCred;
    }

    public void setCrIsCpteEAVOnRembCred(String crIsCpteEAVOnRembCred) {
        CrIsCpteEAVOnRembCred = crIsCpteEAVOnRembCred;
    }

    public String getCrIsCpteCourantOnRembCred() {
        return CrIsCpteCourantOnRembCred;
    }

    public void setCrIsCpteCourantOnRembCred(String crIsCpteCourantOnRembCred) {
        CrIsCpteCourantOnRembCred = crIsCpteCourantOnRembCred;
    }

    public String getCrIsIntRetCreditOn() {
        return CrIsIntRetCreditOn;
    }

    public void setCrIsIntRetCreditOn(String crIsIntRetCreditOn) {
        CrIsIntRetCreditOn = crIsIntRetCreditOn;
    }

    public String getCrIsSoldPenRetObligSiNewEchCred() {
        return CrIsSoldPenRetObligSiNewEchCred;
    }

    public void setCrIsSoldPenRetObligSiNewEchCred(String crIsSoldPenRetObligSiNewEchCred) {
        CrIsSoldPenRetObligSiNewEchCred = crIsSoldPenRetObligSiNewEchCred;
    }

    public String getCrIsSoldIntRetObligSiNewEchCred() {
        return CrIsSoldIntRetObligSiNewEchCred;
    }

    public void setCrIsSoldIntRetObligSiNewEchCred(String crIsSoldIntRetObligSiNewEchCred) {
        CrIsSoldIntRetObligSiNewEchCred = crIsSoldIntRetObligSiNewEchCred;
    }

    public String getCrIsTauxInteretAnOn() {
        return CrIsTauxInteretAnOn;
    }

    public void setCrIsTauxInteretAnOn(String crIsTauxInteretAnOn) {
        CrIsTauxInteretAnOn = crIsTauxInteretAnOn;
    }

    public String getCrNatureTxIntAn() {
        return CrNatureTxIntAn;
    }

    public void setCrNatureTxIntAn(String crNatureTxIntAn) {
        CrNatureTxIntAn = crNatureTxIntAn;
    }

    public String getCrTauxValTxIntAn() {
        return CrTauxValTxIntAn;
    }

    public void setCrTauxValTxIntAn(String crTauxValTxIntAn) {
        CrTauxValTxIntAn = crTauxValTxIntAn;
    }

    public String getCrBaseTxIntAn() {
        return CrBaseTxIntAn;
    }

    public void setCrBaseTxIntAn(String crBaseTxIntAn) {
        CrBaseTxIntAn = crBaseTxIntAn;
    }
}
