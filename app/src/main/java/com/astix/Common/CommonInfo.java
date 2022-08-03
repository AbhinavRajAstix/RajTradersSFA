package com.astix.Common;

import android.net.Uri;

import com.astix.rajtraderssfasales.BuildConfig;

import java.io.File;
import java.util.LinkedHashMap;

public class CommonInfo {
    //firebaseID astix8  pass: astix1234
    public static String APP_TOKEN_PREFS_NAME = "tokenPrefs";
    public static String APP_TOKEN_PREFS_KEY = "token";
    public static String APP_TOKEN_PREFS_KEYTest = "token";


//Contains 0=NoApplicale and 1=Applicable

    //Live 196 server
    public static final String LastTrackPreference = "RajTradersLastTrackPrefrence";
    public static String ContactNumber = "";
    public static String ActiveRouteSM = "0";
    public static int AnyVisit = 0;
    public static String AppLatLngJsonFile = "RajTradersOrderSFALatLngJson";

    public static int Application_TypeID = 2;
    public static final String AttandancePreference = "RajTradersOrderAttandancePreference";
    public static int CoverageAreaNodeID = 0;
    public static int CoverageAreaNodeType = 0;
    public static String DATABASE_NAME = "AppDataSource";
    public static int DATABASE_VERSIONID = BuildConfig.VERSION_CODE;
    public static String AppVersionID = BuildConfig.VERSION_NAME;
    public static int flgDrctslsIndrctSls = 2;
    public static int DayStartClick = 0;
    public static int DistanceRange = 3000;
    public static final String DistributorCheckInXMLFolder = "RajTradersOrderDistributorCheckInXMLDev";
    public static final String DistributorMapXMLFolder = "RajTradersOrderDistributorMapXMLDev";
    public static final String DistributorStockXMLFolder = "RajTradersOrderDistributorStockXMLDev";
    //public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_RajTradersDistributionDevelopment/Default.aspx";
    public static String FinalLatLngJsonFile = "RajTradersOrderSFAFinalLatLngJsonDev";
    public static int FlgDSRSO = 0;
    //public static String ImageSyncPath = "http://103.20.212.194/ReadXML_RajTradersImagesDevelopment/Default.aspx";
    public static String ImagesFolder = "RajTradersOrderSFAImagesDev";
    public static String ImagesFolderServer = ".RajTradersOrderSFAImagesServerDev";
    //public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_RajTradersInvoiceDevelopment/DefaultGT.aspx";
    public static String InvoiceXMLFolder = "RajTradersOrderInvoiceXmlDev";
    //public static String OrderSyncPath = "http://103.20.212.194/ReadXML_RajTradersDevelopment/DefaultGTSFA.aspx";
    public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_RajTradersDev/DefaultSODistributorMappingGT.aspx";
    public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForRajTradersDev/default.aspx";
    public static String OrderXMLFolder = "RajTradersOrderSFAXmlDev";
    public static final String Preference = "RajTradersOrderPrefrence";
    public static final String CycleOrDayEndPreference = "CycleOrDayEndPreference";
    public static String SalesPersonTodaysTargetMsg = "";
    public static String SalesQuoteId = "BLANK";
    public static int SalesmanNodeId = 0;
    public static int SalesmanNodeType = 0;
    public static String TextFileFolder = "RajTradersOrderTextFile";
    public static String VersionDownloadAPKName = "RajTradersOrderSales.apk";
    //public static String VersionDownloadAPKName = "RajTradersOrderSales_Dev.apk";
    public static String VersionDownloadPath = "http://www.rajsuperwhite.in/downloads/";
    public static String WebServicePath = "http://103.20.212.67/Raj_PDADataService_Android10_live/GTService.asmx";
    public static String WebStockInUrl = "http://www.rajsuperwhite.in/manageorder/frmstockin.aspx";
    public static String WebStockOutUrl = "http://www.rajsuperwhite.in/manageorder/frmStockTransferToVanDetail_PDA.aspx";
    public static String clickedTagPhoto_savedInstance = null;
    public static String fileContent = "";
    public static int flgAllRoutesData = 1;
    public static int flgDataScope = 0;
    public static String globalValueOfPaymentStage = "0_0_0";
    public static File imageF_savedInstance = null;
    public static String imageName_savedInstance = null;
    public static String token = "";
    public static int VanLoadedUnloaded = 0;
    public static String newQuottionID = "NULL";
    public static String prcID = "NULL";
    public static String quatationFlag = "";
    public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
    public static Uri uriSavedImage_savedInstance = null;
    public static String TextFileName = "RajTradersAllDetails";
    public static String TextFileArrayName = "AllDetails";


    public static final String COMMON_SYNC_PATH_URL = "http://103.107.67.196/RajSM_PDAFileReceivingApp_Live/Default.aspx?FileType=";

    public static String ClientFileNameOrderSync = "XML_Files";
    public static String ClientFileNameImageSyncPath = "IMAGE_ImageFiles";
    public static String ClientFileNameInvoiceSyncPath = "XML_InvoiceFile_GT";
    public static String ClientFileNameDistributorSyncPath = "XML_DistributionFile_GT";
    public static String ClientFileNameDistributorMapPath = "XML_DistributionMap_GT";

    public static final String Invoice_Database_Assistant_DB_NAME = "XMLInvoiceFile";
    public static final String Database_Assistant_Distributor_Entry_DB_NAME = "DistributorDataFile";
    public static final String Database_Assistant_DB_NAME = "DBRajTraders";

    public static final String BASE_URL = "http://www.rajsuperwhite.in/Raj_PDADataAPI_Live/";
//    public static final String BASE_URL = "http://103.20.212.67/Allana_PDADataAPI_Test/";
    public static String RegistrationID = "NotGettingFromServer";
    public static String crntServerTimecrntAttndncTime = "";
    public static Integer flgCollDefControl = 0;
    public static Double CollectionPer = 0.00;
    public static LinkedHashMap<String, Integer> hmapAppMasterFlags = new LinkedHashMap<String, Integer>();//Filled from All Button Activicty On onCreate Method
    public static int flgLangChangeReuired = 0;
    public static String VALIDATION_CODE = "validation_coe";
    public static final String MTAS_ORDER_WEB_URL = "http://www.rajsuperwhite.in/pda/mTas.aspx?pdacode=";
    public static int maxAllowedPhotos=2;

    public static  String PURCHASE_ORDER_WEB_URL = "http://www.rajsuperwhite.in/frmLoginPDA.aspx?IMEI=";
    public static  String WHATSAPP_ENROLL_WEB_URL = "http://www.rajsuperwhite.in/PDA/frmWhatsppRegistration.aspx?sid=";
    public static  LinkedHashMap<String,String> hmapStoreLocationDetails=new LinkedHashMap<String,String>();
     public static  String WEB_Daily_Working_Report = "http://www.rajsuperwhite.in/Mobile/frmDayTrackerReport_Mobile.aspx?imei=";
    public static String TextFileFolderCrashDetails = "RajTradersCrashDetails";

    //Stagging
   /* public static final String LastTrackPreference = "RajTradersLastTrackPrefrence";
    public static String ContactNumber = "";
    public static String ActiveRouteSM = "0";
    public static int AnyVisit = 0;
    public static String AppLatLngJsonFile = "RajTradersOrderSFALatLngJson";

    public static int Application_TypeID = 2;
    public static final String AttandancePreference = "RajTradersOrderAttandancePreference";
    public static int CoverageAreaNodeID = 0;
    public static int CoverageAreaNodeType = 0;
    public static String DATABASE_NAME = "AppDataSource";
    public static int DATABASE_VERSIONID = BuildConfig.VERSION_CODE;
    public static String AppVersionID = BuildConfig.VERSION_NAME;
    public static int flgDrctslsIndrctSls = 2;
    public static int DayStartClick = 0;
    public static int DistanceRange = 3000;
    public static final String DistributorCheckInXMLFolder = "RajTradersOrderDistributorCheckInXMLStagging";
    public static final String DistributorMapXMLFolder = "RajTradersOrderDistributorMapXMLStagging";
    public static final String DistributorStockXMLFolder = "RajTradersOrderDistributorStockXMLStagging";
    //public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_RajTradersDistributionDevelopment/Default.aspx";
    public static String FinalLatLngJsonFile = "RajTradersOrderSFAFinalLatLngJsonStagging";
    public static int FlgDSRSO = 0;
    //public static String ImageSyncPath = "http://103.20.212.194/ReadXML_RajTradersImagesDevelopment/Default.aspx";
    public static String ImagesFolder = "RajTradersOrderSFAImagesStagging";
    public static String ImagesFolderServer = ".RajTradersOrderSFAImagesServerStagging";
    //public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_RajTradersInvoiceDevelopment/DefaultGT.aspx";
    public static String InvoiceXMLFolder = "RajTradersOrderInvoiceXmlStagging";
    //public static String OrderSyncPath = "http://103.20.212.194/ReadXML_RajTradersDevelopment/DefaultGTSFA.aspx";
    public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_RajTradersDev/DefaultSODistributorMappingGT.aspx";
    public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForRajTradersDev/default.aspx";
    public static String OrderXMLFolder = "RajTradersOrderSFAXmlStagging";
    public static final String Preference = "RajTradersOrderPrefrence";
    public static final String CycleOrDayEndPreference = "CycleOrDayEndPreference";
    public static String SalesPersonTodaysTargetMsg = "";
    public static String SalesQuoteId = "BLANK";
    public static int SalesmanNodeId = 0;
    public static int SalesmanNodeType = 0;
    public static String TextFileFolder = "RajTradersOrderTextFile";
    public static String VersionDownloadAPKName = "RajTradersOrderSales_UAT.apk";
    //public static String VersionDownloadAPKName = "RajTradersOrderSales_Dev.apk";
    public static String VersionDownloadPath = "http://103.107.67.196/downloads/";
    public static String WebServicePath = "http://103.20.212.194/WebServiceAndroidAllanaDevelopment/Service.asmx";
    public static String WebStockInUrl = "http://103.20.212.194/Allanadev/manageorder/frmstockin.aspx";
    public static String WebStockOutUrl = "http://103.20.212.194/Allanadev/manageorder/frmStockTransferToVanDetail_PDA.aspx";
    public static String clickedTagPhoto_savedInstance = null;
    public static String fileContent = "";
    public static int flgAllRoutesData = 1;
    public static int flgDataScope = 0;
    public static String globalValueOfPaymentStage = "0_0_0";
    public static File imageF_savedInstance = null;
    public static String imageName_savedInstance = null;
    public static String token = "";
    public static int VanLoadedUnloaded = 0;
    public static String newQuottionID = "NULL";
    public static String prcID = "NULL";
    public static String quatationFlag = "";
    public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
    public static Uri uriSavedImage_savedInstance = null;
    public static String TextFileName = "RajTradersAllDetails";
    public static String TextFileArrayName = "AllDetails";


    public static final String COMMON_SYNC_PATH_URL = "http://103.107.67.196/RajSM_PDAFileReceivingApp_UAT/Default.aspx?FileType=";

    public static String ClientFileNameOrderSync = "XML_Files";
    public static String ClientFileNameImageSyncPath = "IMAGE_ImageFiles";
    public static String ClientFileNameInvoiceSyncPath = "XML_InvoiceFile_GT";
    public static String ClientFileNameDistributorSyncPath = "XML_DistributionFile_GT";
    public static String ClientFileNameDistributorMapPath = "XML_DistributionMap_GT";

    public static final String Invoice_Database_Assistant_DB_NAME = "XMLInvoiceFile";
    public static final String Database_Assistant_Distributor_Entry_DB_NAME = "DistributorDataFile";
    public static final String Database_Assistant_DB_NAME = "DBRajTraders";

    public static final String BASE_URL = "http://103.107.67.196/Raj_PDADataAPI_UAT/";
    //    public static final String BASE_URL = "http://103.20.212.67/Allana_PDADataAPI_Test/";
    public static String RegistrationID = "NotGettingFromServer";
    public static String crntServerTimecrntAttndncTime = "";
    public static Integer flgCollDefControl = 0;
    public static Double CollectionPer = 0.00;
    public static LinkedHashMap<String, Integer> hmapAppMasterFlags = new LinkedHashMap<String, Integer>();//Filled from All Button Activicty On onCreate Method
    public static int flgLangChangeReuired = 0;
    public static String VALIDATION_CODE = "validation_code";
    public static final String MTAS_ORDER_WEB_URL = "http://103.107.67.196/RajTradersTars_UAT/pda/mTas.aspx?pdacode=";
    public static int maxAllowedPhotos=2;

    public static  String PURCHASE_ORDER_WEB_URL = "http://103.107.67.196/RajTradersTars_UAT/frmLoginPDA.aspx?IMEI=";
    public static  String WHATSAPP_ENROLL_WEB_URL = "http://103.107.67.196/RajTradersTars_UAT/PDA/frmWhatsppRegistration.aspx?sid=";
    public static  String WEB_Daily_Working_Report = "http://103.107.67.196/RajTradersTars_UAT/Mobile/frmDayTrackerReport_Mobile.aspx?imei=";
    public static  LinkedHashMap<String,String> hmapStoreLocationDetails=new LinkedHashMap<String,String>();
    public static  LinkedHashMap<String,String> hmapChildVisitLatLong=new LinkedHashMap<String,String>();
    public static String TextFileFolderCrashDetails = "RajTradersCrashDetails";*/

    //ASM Demo DB
    /*public static final String LastTrackPreference = "RajTradersLastTrackPrefrence";
    public static String ContactNumber = "";
    public static String ActiveRouteSM = "0";
    public static int AnyVisit = 0;
    public static String AppLatLngJsonFile = "RajTradersOrderSFALatLngJson";

    public static int Application_TypeID = 2;
    public static final String AttandancePreference = "RajTradersOrderAttandancePreference";
    public static int CoverageAreaNodeID = 0;
    public static int CoverageAreaNodeType = 0;
    public static String DATABASE_NAME = "AppDataSource";
    public static int DATABASE_VERSIONID = BuildConfig.VERSION_CODE;
    public static String AppVersionID = BuildConfig.VERSION_NAME;
    public static int flgDrctslsIndrctSls = 2;
    public static int DayStartClick = 0;
    public static int DistanceRange = 3000;
    public static final String DistributorCheckInXMLFolder = "RajTradersOrderDistributorCheckInXMLStagging";
    public static final String DistributorMapXMLFolder = "RajTradersOrderDistributorMapXMLStagging";
    public static final String DistributorStockXMLFolder = "RajTradersOrderDistributorStockXMLStagging";
    //public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_RajTradersDistributionDevelopment/Default.aspx";
    public static String FinalLatLngJsonFile = "RajTradersOrderSFAFinalLatLngJsonStagging";
    public static int FlgDSRSO = 0;
    //public static String ImageSyncPath = "http://103.20.212.194/ReadXML_RajTradersImagesDevelopment/Default.aspx";
    public static String ImagesFolder = "RajTradersOrderSFAImagesStagging";
    public static String ImagesFolderServer = ".RajTradersOrderSFAImagesServerStagging";
    //public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_RajTradersInvoiceDevelopment/DefaultGT.aspx";
    public static String InvoiceXMLFolder = "RajTradersOrderInvoiceXmlStagging";
    //public static String OrderSyncPath = "http://103.20.212.194/ReadXML_RajTradersDevelopment/DefaultGTSFA.aspx";
    public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_RajTradersDev/DefaultSODistributorMappingGT.aspx";
    public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForRajTradersDev/default.aspx";
    public static String OrderXMLFolder = "RajTradersOrderSFAXmlStagging";
    public static final String Preference = "RajTradersOrderPrefrence";
    public static final String CycleOrDayEndPreference = "CycleOrDayEndPreference";
    public static String SalesPersonTodaysTargetMsg = "";
    public static String SalesQuoteId = "BLANK";
    public static int SalesmanNodeId = 0;
    public static int SalesmanNodeType = 0;
    public static String TextFileFolder = "RajTradersOrderTextFile";
    public static String VersionDownloadAPKName = "RajTradersOrderSales_Demo.apk";
    //public static String VersionDownloadAPKName = "RajTradersOrderSales_Dev.apk";
    public static String VersionDownloadPath = "http://103.20.212.67/downloads/";
    public static String WebServicePath = "http://103.20.212.194/WebServiceAndroidAllanaDevelopment/Service.asmx";
    public static String WebStockInUrl = "http://103.20.212.194/Allanadev/manageorder/frmstockin.aspx";
    public static String WebStockOutUrl = "http://103.20.212.194/Allanadev/manageorder/frmStockTransferToVanDetail_PDA.aspx";
    public static String clickedTagPhoto_savedInstance = null;
    public static String fileContent = "";
    public static int flgAllRoutesData = 1;
    public static int flgDataScope = 0;
    public static String globalValueOfPaymentStage = "0_0_0";
    public static File imageF_savedInstance = null;
    public static String imageName_savedInstance = null;
    public static String token = "";
    public static int VanLoadedUnloaded = 0;
    public static String newQuottionID = "NULL";
    public static String prcID = "NULL";
    public static String quatationFlag = "";
    public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
    public static Uri uriSavedImage_savedInstance = null;
    public static String TextFileName = "RajTradersAllDetails";
    public static String TextFileArrayName = "AllDetails";


    public static final String COMMON_SYNC_PATH_URL = "http://103.20.212.67/RajSM_PDAFileReceivingApp_ASMDemo/Default.aspx?FileType=";

    public static String ClientFileNameOrderSync = "XML_Files";
    public static String ClientFileNameImageSyncPath = "IMAGE_ImageFiles";
    public static String ClientFileNameInvoiceSyncPath = "XML_InvoiceFile_GT";
    public static String ClientFileNameDistributorSyncPath = "XML_DistributionFile_GT";
    public static String ClientFileNameDistributorMapPath = "XML_DistributionMap_GT";

    public static final String Invoice_Database_Assistant_DB_NAME = "XMLInvoiceFile";
    public static final String Database_Assistant_Distributor_Entry_DB_NAME = "DistributorDataFile";
    public static final String Database_Assistant_DB_NAME = "DBRajTraders";

    public static final String BASE_URL = "http://103.20.212.67/Raj_PDADataAPI_ASMDemo/";
    //    public static final String BASE_URL = "http://103.20.212.67/Allana_PDADataAPI_Test/";
    public static String RegistrationID = "NotGettingFromServer";
    public static String crntServerTimecrntAttndncTime = "";
    public static Integer flgCollDefControl = 0;
    public static Double CollectionPer = 0.00;
    public static LinkedHashMap<String, Integer> hmapAppMasterFlags = new LinkedHashMap<String, Integer>();//Filled from All Button Activicty On onCreate Method
    public static int flgLangChangeReuired = 0;
    public static String VALIDATION_CODE = "validation_code";
    public static final String MTAS_ORDER_WEB_URL = "http://103.20.212.67/RajTradersTars_Staging/pda/mTas.aspx?pdacode=";
    public static int maxAllowedPhotos=2;

    public static  String PURCHASE_ORDER_WEB_URL = "http://103.20.212.67/RajTradersTars_Staging/frmLoginPDA.aspx?IMEI=";
    public static  String WHATSAPP_ENROLL_WEB_URL = "http://103.20.212.67/RajTradersTars_Staging/PDA/frmWhatsppRegistration.aspx?sid=";
    public static  String WEB_Daily_Working_Report = "http://103.20.212.67/RajTradersTars_Staging/Mobile/frmDayTrackerReport_Mobile.aspx?imei=";
    public static  LinkedHashMap<String,String> hmapStoreLocationDetails=new LinkedHashMap<String,String>();
    public static  LinkedHashMap<String,String> hmapChildVisitLatLong=new LinkedHashMap<String,String>();
*/
    //Dev
    /*public static final String LastTrackPreference = "RajTradersLastTrackPrefrence";
    public static String ContactNumber = "";
    public static String ActiveRouteSM = "0";
    public static int AnyVisit = 0;
    public static String AppLatLngJsonFile = "RajTradersOrderSFALatLngJson";

    public static int Application_TypeID = 2;
    public static final String AttandancePreference = "RajTradersOrderAttandancePreference";
    public static int CoverageAreaNodeID = 0;
    public static int CoverageAreaNodeType = 0;
    public static String DATABASE_NAME = "AppDataSource";
    public static int DATABASE_VERSIONID = BuildConfig.VERSION_CODE;
    public static String AppVersionID = BuildConfig.VERSION_NAME;
    public static int flgDrctslsIndrctSls = 2;
    public static int DayStartClick = 0;
    public static int DistanceRange = 3000;
    public static final String DistributorCheckInXMLFolder = "RajTradersOrderDistributorCheckInXMLDev";
    public static final String DistributorMapXMLFolder = "RajTradersOrderDistributorMapXMLDev";
    public static final String DistributorStockXMLFolder = "RajTradersOrderDistributorStockXMLDev";
    //public static String DistributorSyncPath = "http://103.20.212.194/ReadXML_RajTradersDistributionDevelopment/Default.aspx";
    public static String FinalLatLngJsonFile = "RajTradersOrderSFAFinalLatLngJsonDev";
    public static int FlgDSRSO = 0;
    //public static String ImageSyncPath = "http://103.20.212.194/ReadXML_RajTradersImagesDevelopment/Default.aspx";
    public static String ImagesFolder = "RajTradersOrderSFAImagesDev";
    public static String ImagesFolderServer = ".RajTradersOrderSFAImagesServerDev";
    //public static String InvoiceSyncPath = "http://103.20.212.194/ReadXML_RajTradersInvoiceDevelopment/DefaultGT.aspx";
    public static String InvoiceXMLFolder = "RajTradersOrderInvoiceXmlDev";
    //public static String OrderSyncPath = "http://103.20.212.194/ReadXML_RajTradersDevelopment/DefaultGTSFA.aspx";
    public static String OrderSyncPathDistributorMap = "http://103.20.212.194/ReadXML_RajTradersDev/DefaultSODistributorMappingGT.aspx";
    public static String OrderTextSyncPath = "http://103.20.212.194/ReadTxtFileForRajTradersDev/default.aspx";
    public static String OrderXMLFolder = "RajTradersOrderSFAXmlDev";
    public static final String Preference = "RajTradersOrderPrefrence";
    public static final String CycleOrDayEndPreference = "CycleOrDayEndPreference";
    public static String SalesPersonTodaysTargetMsg = "";
    public static String SalesQuoteId = "BLANK";
    public static int SalesmanNodeId = 0;
    public static int SalesmanNodeType = 0;
    public static String TextFileFolder = "RajTradersOrderTextFile";
    public static String VersionDownloadAPKName = "RajTradersOrderSales_Test.apk";
    //public static String VersionDownloadAPKName = "RajTradersOrderSales_Dev.apk";
    public static String VersionDownloadPath = "http://103.20.212.67/downloads/";
    public static String WebServicePath = "http://103.20.212.194/WebServiceAndroidAllanaDevelopment/Service.asmx";
    public static String WebStockInUrl = "http://103.20.212.194/Allanadev/manageorder/frmstockin.aspx";
    public static String WebStockOutUrl = "http://103.20.212.194/Allanadev/manageorder/frmStockTransferToVanDetail_PDA.aspx";
    public static String clickedTagPhoto_savedInstance = null;
    public static String fileContent = "";
    public static int flgAllRoutesData = 1;
    public static int flgDataScope = 0;
    public static String globalValueOfPaymentStage = "0_0_0";
    public static File imageF_savedInstance = null;
    public static String imageName_savedInstance = null;
    public static String token = "";
    public static int VanLoadedUnloaded = 0;
    public static String newQuottionID = "NULL";
    public static String prcID = "NULL";
    public static String quatationFlag = "";
    public static String sPrefVanLoadedUnloaded = "VanLoadedUnloaded";
    public static Uri uriSavedImage_savedInstance = null;
    public static String TextFileName = "RajTradersAllDetails";
    public static String TextFileArrayName = "AllDetails";


    public static final String COMMON_SYNC_PATH_URL = "http://103.20.212.67/RajSM_PDAFileReceivingApp_Dev/Default.aspx?FileType=";

    public static String ClientFileNameOrderSync = "XML_Files";
    public static String ClientFileNameImageSyncPath = "IMAGE_ImageFiles";
    public static String ClientFileNameInvoiceSyncPath = "XML_InvoiceFile_GT";
    public static String ClientFileNameDistributorSyncPath = "XML_DistributionFile_GT";
    public static String ClientFileNameDistributorMapPath = "XML_DistributionMap_GT";

    public static final String Invoice_Database_Assistant_DB_NAME = "XMLInvoiceFile";
    public static final String Database_Assistant_Distributor_Entry_DB_NAME = "DistributorDataFile";
    public static final String Database_Assistant_DB_NAME = "DBRajTraders";

    public static final String BASE_URL = "http://103.20.212.67/Raj_PDADataAPI_Dev/";
//    public static final String BASE_URL = "http://103.20.212.67/Allana_PDADataAPI_Test/";
    public static String RegistrationID = "NotGettingFromServer";
    public static String crntServerTimecrntAttndncTime = "";
    public static Integer flgCollDefControl = 0;
    public static Double CollectionPer = 0.00;
    public static LinkedHashMap<String, Integer> hmapAppMasterFlags = new LinkedHashMap<String, Integer>();//Filled from All Button Activicty On onCreate Method
    public static int flgLangChangeReuired = 0;
    public static String VALIDATION_CODE = "validation_code";
    public static final String MTAS_ORDER_WEB_URL = "http://103.20.212.67/RajTradersTars_Dev/pda/mTas.aspx?pdacode=";
    public static int maxAllowedPhotos=2;

    public static  String PURCHASE_ORDER_WEB_URL = "http://103.20.212.67/RajTradersTars_Dev/frmLoginPDA.aspx?IMEI=";
    public static  String WHATSAPP_ENROLL_WEB_URL = "http://103.20.212.67/RajTradersTars_Dev/PDA/frmWhatsppRegistration.aspx?sid=";*/
//Contains 0=NoApplicale and 1=Applicable



					/*hmapAppMasterFlags.put("flgDistributorCheckIn", cursor.getInt(0));
                    hmapAppMasterFlags.put("flgDBRStockInApp", cursor.getInt(1));
                    hmapAppMasterFlags.put("flgDBRStockEdit", cursor.getInt(2));
                    hmapAppMasterFlags.put("flgDBRStockCalculate", cursor.getInt(3));
                    hmapAppMasterFlags.put("flgDBRStockControl", cursor.getInt(4));
                    hmapAppMasterFlags.put("flgCollRequired", cursor.getInt(5));   //0=Not To be mapped Again,1=Can Map Distributor
                    hmapAppMasterFlags.put("flgCollReqOrdr", cursor.getInt(6));
                    hmapAppMasterFlags.put("flgCollTab", cursor.getInt(7));
                    hmapAppMasterFlags.put("flgCollDefControl", cursor.getInt(8));
                    hmapAppMasterFlags.put("flgCollControlRule", cursor.getInt(9));
                    hmapAppMasterFlags.put("flgSchemeAvailable", cursor.getInt(10));
                    hmapAppMasterFlags.put("flgSchemeAllowEntry", cursor.getInt(11));
                    hmapAppMasterFlags.put("flgSchemeAllowEdit", cursor.getInt(12));
                    hmapAppMasterFlags.put("flgQuotationIsAvailable", cursor.getInt(13));
                    hmapAppMasterFlags.put("flgExecutionIsAvailable", cursor.getInt(14));
                    hmapAppMasterFlags.put("flgExecutionPhotoCompulsory", cursor.getInt(15));
                    hmapAppMasterFlags.put("flgTargetShowatStart", cursor.getInt(16));
                    hmapAppMasterFlags.put("flgIncentiveShowtStart", cursor.getInt(17));
                    hmapAppMasterFlags.put("flgInvoicePrint", cursor.getInt(18));
                    hmapAppMasterFlags.put("flgShowPOSM", cursor.getInt(19));
                    hmapAppMasterFlags.put("flgVisitStartOutstandingDetails", cursor.getInt(20));
                    hmapAppMasterFlags.put("flgVisitStartSchemeDetails", cursor.getInt(21));
                    hmapAppMasterFlags.put("flgStoreDetailsEdit", cursor.getInt(22));
                    hmapAppMasterFlags.put("flgShowDeliveryAddressButtonOnOrder", cursor.getInt(23));
                    hmapAppMasterFlags.put("flgShowManagerOnStoreList", cursor.getInt(24));
                    hmapAppMasterFlags.put("flgRptTargetVsAchived", cursor.getInt(25));
                    hmapAppMasterFlags.put("flgVanSockManage", cursor.getInt(26));
                    hmapAppMasterFlags.put("flgVanSockManage", cursor.getInt(27));*/
}