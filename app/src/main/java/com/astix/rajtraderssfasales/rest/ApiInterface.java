package com.astix.rajtraderssfasales.rest;



import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblAllScoutingDistributorReturnedTableAfterServerSaving;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblAllScoutingDistributorReturnedTables;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblDistributorScoutingParameterData;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblDistributorScoutingRetailerFeedBackParameterForSavingDataToServer;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributor;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorForDelete;
import com.astix.rajtraderssfasales.ExecutionModelsData;
import com.astix.rajtraderssfasales.SignUpModule.TblAllValidationData;
import com.astix.rajtraderssfasales.SignUpModule.TblContactNum;
import com.astix.rajtraderssfasales.model.AllAddedOutletSummaryReportModel;
import com.astix.rajtraderssfasales.model.AllAttendanceAPIInsertTablesModel;
import com.astix.rajtraderssfasales.model.AllMasterTablesModel;
import com.astix.rajtraderssfasales.model.AllSummaryReportDay;
import com.astix.rajtraderssfasales.model.AllSummarySKUWiseDay;
import com.astix.rajtraderssfasales.model.AllSummaryStoreSKUWiseDay;
import com.astix.rajtraderssfasales.model.AllSummaryStoreWiseDay;
import com.astix.rajtraderssfasales.model.AllTargetVsAchieved;
import com.astix.rajtraderssfasales.model.ConfirmVanStock;
import com.astix.rajtraderssfasales.model.Data;
import com.astix.rajtraderssfasales.model.DistributorStockData;
import com.astix.rajtraderssfasales.model.DistributorTodaysStock;
import com.astix.rajtraderssfasales.model.IMEIVersionDetails;
import com.astix.rajtraderssfasales.model.IMEIVersionParentModel;
import com.astix.rajtraderssfasales.model.PDAConfirmVanStockModel;
import com.astix.rajtraderssfasales.model.PersonInfo;
import com.astix.rajtraderssfasales.model.RegistrationValidation;
import com.astix.rajtraderssfasales.model.ReportsAddedOutletSummary;
import com.astix.rajtraderssfasales.model.ReportsInfo;
import com.astix.rajtraderssfasales.model.StatusInfo;
import com.astix.rajtraderssfasales.model.StockData;
import com.astix.rajtraderssfasales.model.StockUploadedStatus;
import com.astix.rajtraderssfasales.model.TblAllOrderOnMailConfirmData;
import com.astix.rajtraderssfasales.model.TblAllSaleManReportReturnedTablesTargetVsAch;
import com.astix.rajtraderssfasales.model.TblAllValidationNewStoreMobileNumber;
import com.astix.rajtraderssfasales.model.TblAllValidationRouteData;
import com.astix.rajtraderssfasales.model.TblAllValidationStoreContactData;
import com.astix.rajtraderssfasales.model.TblAttandanceDetailsDataModel;
import com.astix.rajtraderssfasales.model.TblCurrentServerDateTimeData;
import com.astix.rajtraderssfasales.model.TblPDAVanDayEndDetResult;
import com.astix.rajtraderssfasales.model.TblRequestForOrderOnMainData;
import com.astix.rajtraderssfasales.model.TblRouteValidation;
import com.astix.rajtraderssfasales.model.TblSaveVanStockRequestResult;
import com.astix.rajtraderssfasales.model.TblStoreContactNum;
import com.astix.rajtraderssfasales.model.TblStoreNumberValidationWhileAddStore;
import com.astix.rajtraderssfasales.model.TblTargetVsAchSalesPersonReportParameterData;
import com.astix.rajtraderssfasales.model.VanDayEnd;
import com.astix.rajtraderssfasales.model.VanStockRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {



    @POST("Home/PersonValidation")
    Call<IMEIVersionParentModel> Call_IMEIVersionDetailStatus(@Body IMEIVersionDetails IMEIVersionDetails);

    @POST("Home/AllData")
    Call<AllMasterTablesModel> Call_AllMasterData(@Body Data data);

    @POST("Home/getinvoicelist")
    Call<ExecutionModelsData> Call_AllExecutionData(@Body Data data);

    @POST("Home/GetAllDaySummary")
    Call<AllSummaryReportDay> Call_AllSummaryReportDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetSKUWiseDaySummary")
    Call<AllSummarySKUWiseDay> Call_AllSummarySKUWiseDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetSKUWiseMTDSummary")
    Call<AllSummarySKUWiseDay> Call_AllSummarySKUWiseMTDDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreWiseDaySummary")
    Call<AllSummaryStoreWiseDay> Call_AllSummaryStoreWiseDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreWiseMTDSummary")
    Call<AllSummaryStoreWiseDay> Call_AllSummaryStoreWiseMTDDay(@Body ReportsInfo reportsInfo);

    @POST("Home/GetStoreSKUWiseDaySummary")
    Call<AllSummaryStoreSKUWiseDay> Call_AllSummaryStoreSKUWiseDay(@Body ReportsInfo reportsInfo);


    @POST("Home/GetStoreSKUWiseMTDSummary")
    Call<AllSummaryStoreSKUWiseDay> Call_AllSummaryStoreSKUWiseMTDDay(@Body ReportsInfo reportsInfo);


    @POST("Home/GetTargetVsAchieved")
    Call<AllTargetVsAchieved> Call_AllTargetVsAchieved(@Body Data data);


    @POST("Home/GetPDAGetAddedOutletSummaryReport")
    Call<AllAddedOutletSummaryReportModel> Call_AllPDAGetAddedOutletSummaryReport(@Body ReportsAddedOutletSummary reportsAddedOutletSummary);

    @POST("Home/GetPersonDetail")
    Call<RegistrationValidation> Call_GetRegistrationDetails(@Body PersonInfo personInfo);


    @POST("Home/SaveVanStockRequest")
    Call<TblSaveVanStockRequestResult> Call_SaveVanStockRequest(@Body VanStockRequest VanStockRequest);

    @POST("Home/PDAConfirmVanStock")
    Call<PDAConfirmVanStockModel> Call_PDAConfirmVanStock(@Body ConfirmVanStock ConfirmVanStock);

    @POST("Home/PDAVanDayEnd")
    Call<TblPDAVanDayEndDetResult> Call_PDAVanDayEnd(@Body VanDayEnd VanDayEnd);

    @POST("Home/StockMaster")
    Call<StockData> Call_StockData(@Body Data data);

    @POST("Home/GetDistributorTodaysStock")
    Call<DistributorStockData> Call_DistributorTodayStockData(@Body DistributorTodaysStock data);

    @POST("Home/GetServerTime")
    Call<TblCurrentServerDateTimeData> Call_TblCurrentServerDateTimeData();

    @POST("Home/GetStockUploadStatus")
    Call<StockUploadedStatus> Call_GetStockUploadStatus(@Body StatusInfo statusInfo);


    @POST("Home/SendSMS")
    Call<ResponseBody> sendOtp(@Query("Mobile")String mobile, @Query("Msg")String OTP);

    @POST("Home/UserValidation")
    Call<TblAllValidationData> userValidate(@Body TblContactNum tblContactNum);



    @POST("Home/fnRequestforOrdersOnMail")
    Call<TblAllOrderOnMailConfirmData> Call_fnSaveRequestforOrdersOnMail(@Body TblRequestForOrderOnMainData tblRequestForOrderOnMainData);


    @POST("Home/Call_MarkAttendanceAPI")
    Call<AllAttendanceAPIInsertTablesModel> Call_MarkAttendanceAPI(@Body TblAttandanceDetailsDataModel data);

    @POST("Home/UserValidationStoreContactDetails")
    Call<TblAllValidationStoreContactData> userValidateStoreContact(@Body TblStoreContactNum tblStoreContactNum);


    @POST("Home/PDARouteChangeApproval")
    Call<TblAllValidationRouteData> PDARouteChangeApproval(@Body TblRouteValidation tblRouteValidation);

    @POST("Home/StoreContactDuplicate")
    Call<TblAllValidationNewStoreMobileNumber> StoreContactDuplicate(@Body TblStoreNumberValidationWhileAddStore tblStoreNumberValidationWhileAddStore);

    @POST("Home/GetSpTargetVsAchievement_New")
    Call<TblAllSaleManReportReturnedTablesTargetVsAch> SalesManReportTargetVsAch(@Body TblTargetVsAchSalesPersonReportParameterData tblTargetVsAchSalesPersonReportParameterData);



    @POST("Home/GetPDAGetPotentialDealerList")
    Call<TblAllScoutingDistributorReturnedTables> GetPDAGetPotentialDealerList(@Body TblDistributorScoutingParameterData tblDistributorScoutingParameterData);

    @POST("Home/SavePotentialDistributorData")
    Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> SavePotentialDistributorData(@Body TblPotentialDistributor reportsInfo);


    @POST("Home/SavePotentialDistributor_RetailerFeedbackData")
    Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> SavePotentialDistributorRetailerFeedBackData(@Body TblDistributorScoutingRetailerFeedBackParameterForSavingDataToServer reportsInfo);


    @POST("Home/DeletePotentialDistributorData")
    Call<Object> DeletePotentialDistributorData(@Body TblPotentialDistributorForDelete reportsInfo);

    @POST("Home/DeleteDistributorData")
    Call<Object> DeleteDistributorData(@Body TblPotentialDistributorForDelete reportsInfo);
}
