package com.astix.Common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;


import com.astix.rajtraderssfasales.BuildConfig;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessDistributorDeletion;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutInterface.InterfaceServerSuccessRetailerFeedBackEntry;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblAllScoutingDistributorReturnedTableAfterServerSaving;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblAllScoutingDistributorReturnedTables;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblDistributorScoutingParameterData;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblDistributorScoutingRetailerFeedBackParameterForSavingDataToServer;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributor;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorBrandMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorCompetitorCompanyMstr;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorForDelete;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorRetailersFeedBackDetails;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblPotentialDistributorVehicleMasterList;
import com.astix.rajtraderssfasales.DistributorSocuting.ScoutingModels.TblServerEntryStatus;
import com.astix.rajtraderssfasales.ExecutionModelsData;
import com.astix.rajtraderssfasales.InterfaceRetrofit;
import com.astix.rajtraderssfasales.InterfaceRetrofitAttendanceMarking;
import com.astix.rajtraderssfasales.InterfaceRetrofitNewStoreNumberValidation;
import com.astix.rajtraderssfasales.InterfaceRetrofitOrderOnMain;
import com.astix.rajtraderssfasales.MultipleInterfaceForDayEndStatus;
import com.astix.rajtraderssfasales.MultipleInterfaceRetrofit;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblProductRelatedScheme;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeMstr;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsBucketDetails;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsProductMappingDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBenefitsValueDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBucketDetails;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabBucketProductMapping;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeSlabDetail;
import com.astix.rajtraderssfasales.SchemeTablesModelsSaving.TblSchemeStoreMapping;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.model.*;
import com.astix.rajtraderssfasales.rest.ApiClient;
import com.astix.rajtraderssfasales.rest.ApiInterface;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.astix.rajtraderssfasales.utils.IntentConstants;
import com.astix.rajtraderssfasales.utils.PreferenceManager;
import com.astix.rajtraderssfasales.utils.RandomString;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.allana.truetime.TimeUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class CommonFunction {


    public static Bitmap normalizeImageForUri(Context context, Uri uri) {
        Bitmap rotatedBitmap = null;

        try {

            ExifInterface exif = new ExifInterface(uri.getPath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            rotatedBitmap = rotateBitmap(bitmap, orientation);
            if (!bitmap.equals(rotatedBitmap)) {
                saveBitmapToFile(context, rotatedBitmap, uri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();

            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void saveBitmapToFile(Context context, Bitmap croppedImage, Uri saveUri) {
        if (saveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = context.getContentResolver().openOutputStream(saveUri);
                if (outputStream != null) {
                    croppedImage.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException e) {

            } finally {
                closeSilently(outputStream);
                croppedImage.recycle();
            }
        }
    }

    private static void closeSilently(@Nullable Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    public static void getAllMasterTableModelData(Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        CommonInfo.flgDrctslsIndrctSls = dbengine.fnGetflgDrctslsIndrctSlsForDSR(Integer.parseInt(CoverageNodeId), Integer.parseInt(CoverageNodeType));
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        PreferenceManager mPreferencesManager  = PreferenceManager.getInstance(context);
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        Data data = new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        String FCMToken= mPreferencesManager.getStringValue(IntentConstants.FCM_TOKEN, "");;
        if (FCMToken != null && !FCMToken.equals("")) {
            Log.e("TokenToServer", FCMToken);
            data.setRegistrationId( FCMToken);
        }
        else
        {
            data.setRegistrationId(  FirebaseInstanceId.getInstance().getToken());
        }
        //data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);
        data.setFlgAllRouteData(1);

        data.setInvoiceList(arrDistinctInvoiceNumbersNew);
        // data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<AllMasterTablesModel> call = apiService.Call_AllMasterData(data);
        call.enqueue(new Callback<AllMasterTablesModel>() {
            @Override
            public void onResponse(Call<AllMasterTablesModel> call, Response<AllMasterTablesModel> response) {
                if (response.code() == 200) {
                    AllMasterTablesModel allMasterTablesModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    System.out.println("Call All Data Start Here Step 1 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblDayStartAttendanceOptions() != null) {
                        dbengine.deletetblDayStartAttendanceOptions();
                        List<TblDayStartAttendanceOption> tblDayStartAttendanceOption = allMasterTablesModel.getTblDayStartAttendanceOptions();
                        if (tblDayStartAttendanceOption.size() > 0) {
                            dbengine.savetblDayStartAttendanceOptions(tblDayStartAttendanceOption);

//                            int AutoIdStore = 0;
//                            for (TblDayStartAttendanceOption dayStartAttendanceOption : tblDayStartAttendanceOption) {
//                                AutoIdStore = AutoIdStore++;
//                                dbengine.savetblDayStartAttendanceOptions(AutoIdStore, "" + dayStartAttendanceOption.getReasonId(), dayStartAttendanceOption.getReasonDescr(), dayStartAttendanceOption.getFlgToShowTextBox(), dayStartAttendanceOption.getFlgSOApplicable(), dayStartAttendanceOption.getFlgDSRApplicable(), dayStartAttendanceOption.getFlgNoVisitOption(), dayStartAttendanceOption.getSeqNo(), dayStartAttendanceOption.getFlgDelayedReason(), dayStartAttendanceOption.getFlgMarketVisit());
//                            }
                        } else {
                            blankTablearrayList.add("tblDayStartAttendanceOptions");
                        }
                    }
                    System.out.println("Call All Data End Here Step 1 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 2
                    System.out.println("Call All Data Start Here Step 2 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblRouteListMaster() != null) {
                        dbengine.Delete_tblRouteMasterMstr();
                        List<TblRouteListMaster> tblRouteListMaster = allMasterTablesModel.getTblRouteListMaster();
                        if (tblRouteListMaster.size() > 0) {
                            dbengine.saveRoutesInfo(tblRouteListMaster, fDate);

//                            int AutoIdStore = 0;
//                            for (TblRouteListMaster RouteListMaster : tblRouteListMaster) {
//                                dbengine.saveRoutesInfo("" + RouteListMaster.getID(), "" + RouteListMaster.getRouteType(), RouteListMaster.getDescr(), Integer.parseInt(RouteListMaster.getActive().toString()), Integer.parseInt(RouteListMaster.getActive().toString()), fDate);
//                            }
                        } else {
                            blankTablearrayList.add("tblRouteListMaster");
                        }
                    }




                    if (allMasterTablesModel.getTblProductSectorMapping() != null) {
                        dbengine.Delete_tblProductSectorMappingList();
                        List<TblProductSectorMapping> tblProductSectorMappingList = allMasterTablesModel.getTblProductSectorMapping();
                        if (tblProductSectorMappingList.size() > 0) {
                            dbengine.fnsavetblProductSectorMappingList(tblProductSectorMappingList);

                        } else {
                            blankTablearrayList.add("tblProductSectorMappingList");
                        }
                    }
                    System.out.println("Call All Data End Here Step 2 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 3 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblSalesPersonTodaysTarget() != null) {
                        CommonInfo.SalesPersonTodaysTargetMsg = "";
                        List<TblSalesPersonTodaysTarget> tblSalesPersonTodaysTarget = allMasterTablesModel.getTblSalesPersonTodaysTarget();
                        if (tblSalesPersonTodaysTarget.size() > 0) {
                            for (TblSalesPersonTodaysTarget SalesPersonTodaysTarget : tblSalesPersonTodaysTarget) {
                                CommonInfo.SalesPersonTodaysTargetMsg = SalesPersonTodaysTarget.getValueTarget();
                            }
                        }
                    }
                    System.out.println("Call All Data End Here Step 3 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 4 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    if (allMasterTablesModel.getTblIsDBRStockSubmitted() != null) {
                        dbengine.deletetblIsDBRStockSubmitted();
                        List<TblIsDBRStockSubmitted> tblIsDBRStockSubmitted = allMasterTablesModel.getTblIsDBRStockSubmitted();
                        if (tblIsDBRStockSubmitted.size() > 0) {
                            for (TblIsDBRStockSubmitted IsDBRStockSubmitted : tblIsDBRStockSubmitted) {
                                dbengine.savetblIsDBRStockSubmitted(IsDBRStockSubmitted.getIsDBRStockSubmitted());
                            }
                        }
                    }
                    System.out.println("Call All Data End Here Step 4 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 5 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 3
                    if (allMasterTablesModel.getTblBloodGroup() != null) {
                        dbengine.Delete_bloodGroupMstr();
                        List<TblBloodGroup> tblBloodGroup = allMasterTablesModel.getTblBloodGroup();

                        if (tblBloodGroup.size() > 0) {
                            dbengine.savetblBloodGroup(tblBloodGroup);
//                            for (TblBloodGroup BloodGroup : tblBloodGroup) {
//                                dbengine.savetblBloodGroup(BloodGroup.getBloddGroups());
//                            }
                        } else {
                            blankTablearrayList.add("tblBloodGroup");
                        }
                    }
                    System.out.println("Call All Data End Here Step 6 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 7 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblStoreSuggestedQtyMstrList() != null) {
                        dbengine.Delete_tblStoreSuggestedQtyMstrList();
                        List<TblStoreSuggestedQtyMstr> tblStoreSuggestedQtyMstrsList = allMasterTablesModel.getTblStoreSuggestedQtyMstrList();

                        if (tblStoreSuggestedQtyMstrsList.size() > 0) {
                            dbengine.saveTblStoreSuggestedQtyMstrList(tblStoreSuggestedQtyMstrsList);
                        } else {
                            blankTablearrayList.add("tblStoreSuggestedQtyMstrsList");
                        }
                    }
                    System.out.println("Call All Data End Here Step 7 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 8 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    if (allMasterTablesModel.getTblFocusbrandSKUMstrList() != null) {
                        dbengine.Delete_tblFocusbrandSKU();
                        List<TblFocusbrandSKU> tblFocusbrandSKUMstrList = allMasterTablesModel.getTblFocusbrandSKUMstrList();

                        if (tblFocusbrandSKUMstrList.size() > 0) {
                            dbengine.saveTblFocusbrandSKUMstrListt(tblFocusbrandSKUMstrList);
                        } else {
                            blankTablearrayList.add("tblFocusbrandSKU");
                        }
                    }
                    System.out.println("Call All Data End Here Step 8 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 9 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    if (allMasterTablesModel.getTblReplenishmentSKUList() != null) {
                        dbengine.Delete_tblReplenishmentSKU();
                        List<TblReplenishmentSKU> tblReplenishmentSKUList = allMasterTablesModel.getTblReplenishmentSKUList();

                        if (tblReplenishmentSKUList.size() > 0) {
                            dbengine.saveTblReplenishmentSKUMstrListt(tblReplenishmentSKUList);

                        } else {
                            blankTablearrayList.add("tblReplenishmentSKUList");
                        }
                    }

                    System.out.println("Call All Data End Here Step 9 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 10 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //TblStoreSuggestedQtyMstr
                    //table 4

                    if (allMasterTablesModel.getTblEducationQuali() != null) {
                        dbengine.Delete_tblEducationQuali();
                        List<TblEducationQuali> tblEducationQuali = allMasterTablesModel.getTblEducationQuali();

                        if (tblEducationQuali.size() > 0) {
                            dbengine.savetblEducationQuali(tblEducationQuali);
//                            for (TblEducationQuali EducationQuali : tblEducationQuali) {
//                                dbengine.savetblEducationQuali(EducationQuali.getQualification());
//                            }
                        } else {
                            blankTablearrayList.add("tblEducationQuali");
                        }
                    }
                    System.out.println("Call All Data End Here Step 10 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 11 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 5
                    if (allMasterTablesModel.getTblQuestIDForOutChannel() != null) {
                        dbengine.Delete_tblQuestIDForOutChannel();
                        List<TblQuestIDForOutChannel> tblQuestIDForOutChannel = allMasterTablesModel.getTblQuestIDForOutChannel();

                        if (tblQuestIDForOutChannel.size() > 0) {
                            dbengine.saveOutletChammetQstnIdGrpId(tblQuestIDForOutChannel);
//                            for (TblQuestIDForOutChannel QuestIDForOutChannel : tblQuestIDForOutChannel) {
//                                dbengine.saveOutletChammetQstnIdGrpId(QuestIDForOutChannel.getGrpQuestID(), QuestIDForOutChannel.getQuestID(), QuestIDForOutChannel.getOptionID(), QuestIDForOutChannel.getSectionCount());
//
//                            }

                        } else {
                            blankTablearrayList.add("tblQuestIDForOutChannel");
                        }
                    }
                    System.out.println("Call All Data End Here Step 11 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 12 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 6
                    if (allMasterTablesModel.getTblGetPDAQuestMstr() != null) {
                        dbengine.Delete_tblGetPDAQuestMstr();
                        List<TblGetPDAQuestMstr> tblGetPDAQuestMstr = allMasterTablesModel.getTblGetPDAQuestMstr();

                        if (tblGetPDAQuestMstr.size() > 0) {
                            dbengine.savetblQuestionMstrRetroFit(tblGetPDAQuestMstr);
                        } else {
                            blankTablearrayList.add("tblGetPDAQuestMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 11 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 12 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    if (allMasterTablesModel.getTblStoreCloseReasonMaster() != null) {
                        dbengine.deletetblStoreCloseReasonMaster();
                        List<TblStoreCloseReasonMaster> tblStoreCloseReasonMaster = allMasterTablesModel.getTblStoreCloseReasonMaster();

                        if (tblStoreCloseReasonMaster.size() > 0) {
                            dbengine.savetblStoreCloseReasonMaster(tblStoreCloseReasonMaster);

//                            for (TblStoreCloseReasonMaster StoreCloseReasonMaster : tblStoreCloseReasonMaster) {
//                                dbengine.savetblStoreCloseReasonMaster(StoreCloseReasonMaster.getCloseReasonID(), StoreCloseReasonMaster.getCloseReasonDescr());
//                            }

                        } else {
                            blankTablearrayList.add("tblStoreCloseReasonMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 12 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 13 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 7
                    if (allMasterTablesModel.getTblQuestIDForName() != null) {
                        dbengine.Delete_tblQuestIDForName();
                        List<TblQuestIDForName> tblQuestIDForName = allMasterTablesModel.getTblQuestIDForName();

                        if (tblQuestIDForName.size() > 0) {
                            dbengine.savetblQuestIDForName(tblQuestIDForName);
//                            for (TblQuestIDForName QuestIDForName : tblQuestIDForName) {
//                                dbengine.savetblQuestIDForName(QuestIDForName.getID(), QuestIDForName.getGrpQuestID(), QuestIDForName.getQuestID(), QuestIDForName.getQuestDesc());
//                            }

                        } else {
                            blankTablearrayList.add("tblQuestIDForName");
                        }
                    }
                    System.out.println("Call All Data End Here Step 13 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 14 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 8----------------
                    if (allMasterTablesModel.getTblGetPDAQuestGrpMapping() != null) {
                        dbengine.Delete_tblPDAQuestGrpMappingMstr();
                        List<TblGetPDAQuestGrpMapping> tblGetPDAQuestGrpMapping = allMasterTablesModel.getTblGetPDAQuestGrpMapping();

                        if (tblGetPDAQuestGrpMapping.size() > 0) {
                            dbengine.savetblPDAQuestGrpMappingMstr(tblGetPDAQuestGrpMapping);
                        } else {
                            blankTablearrayList.add("tblGetPDAQuestGrpMapping");
                        }
                    }
                    System.out.println("Call All Data End Here Step 14 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 15 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 9-------------------------------
                    if (allMasterTablesModel.getTblGetPDAQuestOptionMstr() != null) {
                        dbengine.Delete_tblOptionMstr();
                        List<TblGetPDAQuestOptionMstr> tblGetPDAQuestOptionMstr = allMasterTablesModel.getTblGetPDAQuestOptionMstr();

                        if (tblGetPDAQuestOptionMstr.size() > 0) {
                            dbengine.savetblOptionMstrRetrofit(tblGetPDAQuestOptionMstr);
                        } else {
                            blankTablearrayList.add("tblGetPDAQuestOptionMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 15 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 16 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 10-------------------------------
                    if (allMasterTablesModel.getTblGetPDAQuestionDependentMstr() != null) {
                        dbengine.Delete_tblQuestionDependentMstr();
                        List<TblGetPDAQuestionDependentMstr> tblGetPDAQuestionDependentMstr = allMasterTablesModel.getTblGetPDAQuestionDependentMstr();

                        if (tblGetPDAQuestionDependentMstr.size() > 0) {
                            dbengine.savetblQuestionDependentMstr(tblGetPDAQuestionDependentMstr);
//                            for (TblGetPDAQuestionDependentMstr GetPDAQuestionDependentMstr : tblGetPDAQuestionDependentMstr) {
//                                dbengine.savetblQuestionDependentMstr(GetPDAQuestionDependentMstr.getQuestID(), GetPDAQuestionDependentMstr.getOptID(), GetPDAQuestionDependentMstr.getDependentQuestID(), GetPDAQuestionDependentMstr.getGrpQuestID(), GetPDAQuestionDependentMstr.getGrpDepQuestID());
//                            }
                        } else {
                            blankTablearrayList.add("tblGetPDAQuestionDependentMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 16 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 17 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 11-------------------------------
                    if (allMasterTablesModel.getTblPDAQuestOptionDependentMstr() != null) {
                        dbengine.Delete_tblPDAQuestOptionDependentMstr();
                        List<TblPDAQuestOptionDependentMstr> tblPDAQuestOptionDependentMstr = allMasterTablesModel.getTblPDAQuestOptionDependentMstr();

                        if (tblPDAQuestOptionDependentMstr.size() > 0) {
                            dbengine.savetblPDAQuestOptionDependentMstr(tblPDAQuestOptionDependentMstr);
//                            for (TblPDAQuestOptionDependentMstr PDAQuestOptionDependentMstr : tblPDAQuestOptionDependentMstr) {
//                                dbengine.savetblPDAQuestOptionDependentMstr(PDAQuestOptionDependentMstr.getQstId(), PDAQuestOptionDependentMstr.getDepQstId(), PDAQuestOptionDependentMstr.getQstId(), PDAQuestOptionDependentMstr.getGrpDepQuestID());
//                            }
                        } else {
                            blankTablearrayList.add("tblPDAQuestOptionDependentMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 17 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 18 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 12-------------------------------
                    if (allMasterTablesModel.getTblPDAQuestOptionValuesDependentMstr() != null) {
                        dbengine.Delete_tblPDAQuestOptionValuesDependentMstr();
                        List<TblPDAQuestOptionValuesDependentMstr> tblPDAQuestOptionValuesDependentMstr = allMasterTablesModel.getTblPDAQuestOptionValuesDependentMstr();

                        if (tblPDAQuestOptionValuesDependentMstr.size() > 0) {
                            dbengine.savetblPDAQuestOptionValuesDependentMstr(tblPDAQuestOptionValuesDependentMstr);
//                            for (TblPDAQuestOptionValuesDependentMstr PDAQuestOptionValuesDependentMstr : tblPDAQuestOptionValuesDependentMstr) {
//                                dbengine.savetblPDAQuestOptionValuesDependentMstr(PDAQuestOptionValuesDependentMstr.getDepQstId(), PDAQuestOptionValuesDependentMstr.getDepOptID(), PDAQuestOptionValuesDependentMstr.getQuestId(), PDAQuestOptionValuesDependentMstr.getOptID(), PDAQuestOptionValuesDependentMstr.getOptDescr(), PDAQuestOptionValuesDependentMstr.getSequence(), PDAQuestOptionValuesDependentMstr.getGrpQuestID(), PDAQuestOptionValuesDependentMstr.getGrpDepQuestID());
//                            }
                        } else {
                            blankTablearrayList.add("tblPDAQuestOptionValuesDependentMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 18 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 19 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 13-------------------------------
                   /* if(allMasterTablesModel.getTblPDANotificationMaster()!=null) {
                        dbengine.Delete_tblNotificationMstr();
                        List<TblPDANotificationMaster> tblPDANotificationMaster = allMasterTablesModel.getTblPDANotificationMaster();

                        if (tblPDANotificationMaster.size() > 0) {
                            int SerialNo = 0;
                            for (TblPDANotificationMaster PDANotificationMaster : tblPDANotificationMaster) {
                                SerialNo = SerialNo++;
                                dbengine.inserttblNotificationMstr(SerialNo, token, PDANotificationMaster.getNotificationMessage(), PDANotificationMaster.getMsgSendingTime(), 0, 0, "0", 0, PDANotificationMaster.getMsgServerID());
                            }

                        } else {
                            blankTablearrayList.add("tblPDANotificationMaster");
                        }
                    }*/
                    //table 14-------------------------------
                    if (allMasterTablesModel.getTblUserName() != null) {
                        dbengine.delete_all_storeDetailTables();//deleting all tables related to
                        List<TblUserName> tblUserName = allMasterTablesModel.getTblUserName();

                        if (tblUserName.size() > 0) {
                            for (TblUserName UserName : tblUserName) {
                                dbengine.saveTblUserName(UserName.getUserName());
                            }
                        } else {
                            blankTablearrayList.add("tblUserName");
                        }
                    }
                    System.out.println("Call All Data End Here Step 19 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 20 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 15-------------------------------


                    //table 31-------------------------------
                    if (allMasterTablesModel.getTblAppMasterFlags() != null) {
                        dbengine.Delete_tblAppMasterFlags();
                        List<TblAppMasterFlags> tblAppMasterFlags = allMasterTablesModel.getTblAppMasterFlags();

                        if (tblAppMasterFlags.size() > 0) {
                            dbengine.saveAppMasterFlagsRetro(tblAppMasterFlags);
                            CommonInfo.hmapAppMasterFlags = dbengine.fnGetAppMasterFlags(CommonInfo.flgDrctslsIndrctSls);
                        } else {
                            blankTablearrayList.add("tblAppMasterFlags");
                        }
                    }
                    System.out.println("Call All Data End Here Step 20 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 21 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    if (allMasterTablesModel.getTblStoreCountDetails() != null) {
                        List<TblStoreCountDetails> tblStoreCountDetails = allMasterTablesModel.getTblStoreCountDetails();

                        if (tblStoreCountDetails.size() > 0) {
                            for (TblStoreCountDetails StoreCountDetails : tblStoreCountDetails) {
                                dbengine.saveTblStoreCountDetails("" + StoreCountDetails.getTotStoreAdded(), "" + StoreCountDetails.getTodayStoreAdded());
                            }
                        } else {
                            blankTablearrayList.add("tblStoreCountDetails");
                        }
                    }
                    System.out.println("Call All Data End Here Step 21 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 22 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 16-------------------------------
                    //already deleted above

                    if (allMasterTablesModel.getTblPreAddedStores() != null) {
                        List<TblPreAddedStores> tblPreAddedStores = allMasterTablesModel.getTblPreAddedStores();

                        HashMap<String, String> hmapPreAddedStoreIdSstat = new HashMap<String, String>();
                        hmapPreAddedStoreIdSstat = dbengine.checkForPreAddedStoreIdSstat();
                        if (tblPreAddedStores.size() > 0) {

                            dbengine.saveTblPreAddedStoresRetrofit(tblPreAddedStores, hmapPreAddedStoreIdSstat);
                        } else {
                            blankTablearrayList.add("tblPreAddedStores");
                        }


                        if (hmapPreAddedStoreIdSstat != null && hmapPreAddedStoreIdSstat.size() > 0) {
                            hmapPreAddedStoreIdSstat.clear();
                            hmapPreAddedStoreIdSstat = null;
                        }
                        //table 17-------------------------------
                        //already deleted above
                        List<TblPreAddedStoresDataDetails> tblPreAddedStoresDataDetails = allMasterTablesModel.getTblPreAddedStoresDataDetails();

                        if (tblPreAddedStoresDataDetails.size() > 0) {

                            dbengine.saveTblPreAddedStoresDataDetailsRetrofit(tblPreAddedStoresDataDetails);
                        } else {
                            blankTablearrayList.add("tblPreAddedStoresDataDetails");
                        }
                    }
                    System.out.println("Call All Data End Here Step 21 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 22 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));


                    //table 18-------------------------------
                    if (allMasterTablesModel.getTblStateCityMaster() != null) {
                        dbengine.deletetblStateCityMaster();
                        List<TblStateCityMaster> tblStateCityMaster = allMasterTablesModel.getTblStateCityMaster();

                        if (tblStateCityMaster.size() > 0) {
                            dbengine.fnsavetblStateCityMaster(tblStateCityMaster);
//                            for (TblStateCityMaster StateCityMaster : tblStateCityMaster) {
//                                dbengine.fnsavetblStateCityMaster("" + StateCityMaster.getStateID(), StateCityMaster.getState(), "" + StateCityMaster.getCityID(), StateCityMaster.getCity(), StateCityMaster.getCityDefault());
//                            }
                        } else {
                            blankTablearrayList.add("tblStateCityMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 22 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 23 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 19-------------------------------
                    if (allMasterTablesModel.getTblProductListMaster() != null) {
                        dbengine.Delete_tblProductList_for_refreshData();
                        List<TblProductListMaster> tblProductListMaster = allMasterTablesModel.getTblProductListMaster();

                        if (tblProductListMaster.size() > 0) {
                            dbengine.saveSOAPdataProductListRetrofit(tblProductListMaster);
                        } else {
                            blankTablearrayList.add("tblProductListMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 23 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 24 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 20-------------------------------
                    //deleted above
                    if (allMasterTablesModel.getTblProductSegementMap() != null) {
                        List<TblProductSegementMap> tblProductSegementMap = allMasterTablesModel.getTblProductSegementMap();

                        if (tblProductSegementMap.size() > 0) {

                            dbengine.saveProductSegementMapRetrofit(tblProductSegementMap);

                        } else {
                            blankTablearrayList.add("tblProductSegementMap");
                        }
                    }
                    System.out.println("Call All Data End Here Step 24 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 25 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));


                    //table 21-------------------------------
                    //deleted above
                    if (allMasterTablesModel.getTblPriceApplyType() != null) {
                        List<TblPriceApplyType> tblPriceApplyType = allMasterTablesModel.getTblPriceApplyType();

                        if (tblPriceApplyType.size() > 0) {
                            for (TblPriceApplyType priceApplyType : tblPriceApplyType) {
                                dbengine.savetblPriceApplyType(priceApplyType.getDiscountLevel(), priceApplyType.getCutoffvalue());
                            }

                        } else {
                            blankTablearrayList.add("tblPriceApplyType");
                        }
                    }
                    System.out.println("Call All Data End Here Step 25 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 26 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));


                    //table 22-------------------------------
                    //deleted above
                    if (allMasterTablesModel.getTblUOMMaster() != null) {
                        if (allMasterTablesModel.getTblUOMMaster().size() > 0) {
                            dbengine.insertUOMMstr(allMasterTablesModel.getTblUOMMaster());
                        } else {
                            blankTablearrayList.add("tblUOMMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 26 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 27 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 23-------------------------------
                    //deleted above
                    if (allMasterTablesModel.getTblUOMMapping() != null) {
                        List<TblUOMMapping> tblUOMMapping = allMasterTablesModel.getTblUOMMapping();

                        if (tblUOMMapping.size() > 0) {
                            for (TblUOMMapping UOMMapping : tblUOMMapping) {
                                dbengine.insertUOMMapping(UOMMapping.getNodeID(), UOMMapping.getNodeType(), UOMMapping.getBaseUOMID(), UOMMapping.getPackUOMID(), UOMMapping.getRelConversionUnits(), UOMMapping.getFlgVanLoading());
                            }

                        } else {
                            blankTablearrayList.add("tblUOMMapping");
                        }
                    }
                    System.out.println("Call All Data End Here Step 27 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 28 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 24-------------------------------
                    //deleted above
                    if (allMasterTablesModel.getTblManagerMstr() != null) {
                        dbengine.delete_tblManagerMstr();
                        List<TblManagerMstr> tblManagerMstr = allMasterTablesModel.getTblManagerMstr();

                        if (tblManagerMstr.size() > 0) {
                            for (TblManagerMstr ManagerMstr : tblManagerMstr) {
                                dbengine.savetblManagerMstr("" + ManagerMstr.getPersonID(), "" + ManagerMstr.getPersonType(), ManagerMstr.getPersonName(), "" + ManagerMstr.getManagerID(), "" + ManagerMstr.getManagerType(), ManagerMstr.getManagerName());
                            }

                        } else {
                            blankTablearrayList.add("tblManagerMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 28 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 29 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 25-------------------------------
                    //deleted above
                    if (allMasterTablesModel.getTblCategoryMaster() != null) {
                        dbengine.Delete_tblCategory_for_refreshData();
                        List<TblCategoryMaster> tblCategoryMaster = allMasterTablesModel.getTblCategoryMaster();

                        if (tblCategoryMaster.size() > 0) {
                            for (TblCategoryMaster CategoryMaster : tblCategoryMaster) {
                                dbengine.saveCategory(CategoryMaster.getNODEID(), CategoryMaster.getCATEGORY(), 0, CategoryMaster.getProductTypeNodeID(), CategoryMaster.getProductType(), CategoryMaster.getPrdClassOrdr(), CategoryMaster.getPrdTypeOrdr());
                            }

                        } else {
                            blankTablearrayList.add("tblCategoryMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 29 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 30 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 26-------------------------------
                    if (allMasterTablesModel.getTblBankMaster() != null) {
                        dbengine.deletetblBankMaster();
                        List<TblBankMaster> tblBankMaster = allMasterTablesModel.getTblBankMaster();

                        if (tblBankMaster.size() > 0) {

                            dbengine.savetblBankMaster(tblBankMaster);

                        } else {
                            blankTablearrayList.add("tblBankMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 30 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 31 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    if (allMasterTablesModel.getTblNoOrderReasonMaster() != null) {
                        dbengine.deletetblNoOrderReasonMaster();
                        List<TblNoOrderReasonMaster> tblNoOrderReasonMasterList = allMasterTablesModel.getTblNoOrderReasonMaster();

                        if (tblNoOrderReasonMasterList.size() > 0) {

                            dbengine.savetblNoOrderReasonMaser(tblNoOrderReasonMasterList);

                        } else {
                            blankTablearrayList.add("tblNoOrderReasonMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 31 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 32 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 27-------------------------------
                    //deleted above

                    if (allMasterTablesModel.getTblInstrumentMaster() != null) {
                        List<TblInstrumentMaster> tblInstrumentMaster = allMasterTablesModel.getTblInstrumentMaster();
                        dbengine.deleteTblInstrumentMaster();

                        if (tblInstrumentMaster.size() > 0) {
                            dbengine.savetblInstrumentMaster(tblInstrumentMaster);
//                            for (TblInstrumentMaster instrumentMaster : tblInstrumentMaster) {
//                                dbengine.savetblInstrumentMaster(instrumentMaster.getInstrumentModeId(), instrumentMaster.getInstrumentMode(), instrumentMaster.getInstrumentType());
//                            }

                        } else {
                            blankTablearrayList.add("tblInstrumentMaster");
                        }
                    }
                    System.out.println("Call All Data End Here Step 32 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 33 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 28-------------------------------


                    //table 29-------------------------------
                    if (allMasterTablesModel.getTblCycleID() != null) {
                        dbengine.deleteCompleteDataDistStock();

                        List<TblCycleID> tblCycleID = allMasterTablesModel.getTblCycleID();
                        if (CommonInfo.flgDrctslsIndrctSls == 1) {
                            if (tblCycleID.size() > 0) {
                                dbengine.insertCycleId(tblCycleID);
//                                for (TblCycleID CycleID : tblCycleID) {
//                                    dbengine.insertCycleId(CycleID.getCycleID(), CycleID.getCycStartTime(), CycleID.getCycleTime());
//                                }
                            } else {
                                blankTablearrayList.add("tblCycleID");
                            }
                        }
                    }
                    System.out.println("Call All Data End Here Step 32 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 33 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 30-------------------------------
                    //deleted above

                    if (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") != 0) {

                        System.out.println("Call All Data End Here Step 33 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        System.out.println("Call All Data Start Here Step 34 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        if (allMasterTablesModel.getTblStockUploadedStatus() != null) {
                            dbengine.Delete_tblStockUploadedStatus();

                            List<TblStockUploadedStatus> tblStockUploadedStatus = allMasterTablesModel.getTblStockUploadedStatus();

                            if (tblStockUploadedStatus.size() > 0) {
                                for (TblStockUploadedStatus StockUploadedStatus : tblStockUploadedStatus) {
                                    dbengine.inserttblStockUploadedStatus(StockUploadedStatus.getFlgStockTrans(), StockUploadedStatus.getVanLoadUnLoadCycID(), StockUploadedStatus.getCycleTime(), StockUploadedStatus.getStatusID(), StockUploadedStatus.getFlgDayEnd());
                                }

                            } else {
                                blankTablearrayList.add("tblStockUploadedStatus");
                            }
                        }
                        System.out.println("Call All Data End Here Step 34 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        System.out.println("Call All Data Start Here Step 35 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                        if (allMasterTablesModel.getTblVanStockOutFlg() != null) {
                            List<TblVanStockOutFlg> tblVanStockOutFlg = allMasterTablesModel.getTblVanStockOutFlg();

                            if (tblVanStockOutFlg.size() > 0) {
                                for (TblVanStockOutFlg VanStockOutFlg : tblVanStockOutFlg) {
                                    dbengine.insertStockOut(VanStockOutFlg.getFlgStockOutEntryDone());
                                }
                            } else {
                                blankTablearrayList.add("tblDistributorStockOutFlg");
                            }
                        }
                        System.out.println("Call All Data End Here Step 35 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        System.out.println("Call All Data Start Here Step 36 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                        if (allMasterTablesModel.getTblVanProductStock() != null) {
                            List<TblVanProductStock> tblVanProductStock = allMasterTablesModel.getTblVanProductStock();

                            if (tblVanProductStock.size() > 0) {
                                // if(CommonInfo.flgDrctslsIndrctSls==1) {
                                dbengine.insertDistributorStock(tblVanProductStock);
                                //  if(CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp")==1 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockCalculate")==1 ) {
                                int statusId = dbengine.confirmedStock();
                                if (statusId == 2) {
                                    dbengine.insertConfirmWArehouse(tblVanProductStock.get(0).getCustomer(), "1");
                                    dbengine.inserttblDayCheckIn(1);
                                }
                                // }
                                //  }

                            } else {
                                blankTablearrayList.add("tblVanProductStock");
                            }
                        }
                        System.out.println("Call All Data End Here Step 36 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        System.out.println("Call All Data Start Here Step 37 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    }

                    //deleted above
                    System.out.println("Call All Data End Here Step 37 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 38 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblVanIDOrderIDLeft() != null) {
                        List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft = allMasterTablesModel.getTblVanIDOrderIDLeft();

                        if (tblVanIDOrderIDLeft.size() > 0) {
                            for (TblVanIDOrderIDLeft DistributorIDOrderIDLeft : tblVanIDOrderIDLeft) {
                                dbengine.insertDistributorLeftOrderId(DistributorIDOrderIDLeft.getCustomer(), DistributorIDOrderIDLeft.getPDAOrderId(), DistributorIDOrderIDLeft.getFlgInvExists());
                            }
                        } else {
                            blankTablearrayList.add("tblVanIDOrderIDLeft");
                        }
                    }
                    System.out.println("Call All Data End Here Step 39 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 40 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 32-------------------------------
                    if (allMasterTablesModel.getTblInvoiceCaption() != null) {
                        dbengine.Delete_tblInvoiceCaption();
                        List<TblInvoiceCaption> tblInvoiceCaption = allMasterTablesModel.getTblInvoiceCaption();

                        if (tblInvoiceCaption.size() > 0) {
                            for (TblInvoiceCaption InvoiceCaption : tblInvoiceCaption) {
                                dbengine.savetblInvoiceCaption(InvoiceCaption.getInvPrefix(), InvoiceCaption.getVanIntialInvoiceIds(), InvoiceCaption.getInvSuffix());
                            }
                        } else {
                            blankTablearrayList.add("tblInvoiceCaption");
                        }
                    }
                    System.out.println("Call All Data End Here Step 40 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 41 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 33-------------------------------
                    if (allMasterTablesModel.getTblGetReturnsReasonForPDAMstr() != null) {
                        dbengine.Delete_tblGetReturnsReasonForPDAMstr();
                        List<TblGetReturnsReasonForPDAMstr> tblGetReturnsReasonForPDAMstr = allMasterTablesModel.getTblGetReturnsReasonForPDAMstr();

                        if (tblGetReturnsReasonForPDAMstr.size() > 0) {
                            for (TblGetReturnsReasonForPDAMstr GetReturnsReasonForPDAMstr : tblGetReturnsReasonForPDAMstr) {
                                dbengine.fnInsertTBLReturnRsn(GetReturnsReasonForPDAMstr.getStockStatusId(), GetReturnsReasonForPDAMstr.getStockStatus());
                            }
                        } else {
                            blankTablearrayList.add("tblGetReturnsReasonForPDAMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 41 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 42 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                   /* //table 34-------------------------------
                    if (allMasterTablesModel.getTblIsSchemeApplicable() != null) {
                        dbengine.Delete_tblGetReturnsReasonForPDAMstr();
                        List<TblIsSchemeApplicable> tblIsSchemeApplicable = allMasterTablesModel.getTblIsSchemeApplicable();

                        if (tblIsSchemeApplicable.size() > 0) {
                            for (TblIsSchemeApplicable IsSchemeApplicable : tblIsSchemeApplicable) {
                                dbengine.SavePDAIsSchemeApplicable(IsSchemeApplicable.getIsSchemeApplicable());
                            }
                        } else {
                            blankTablearrayList.add("tblIsSchemeApplicable");
                        }
                    }*/

                    //Scheme Related Tables Starts Here
                          /*  List<TblSchemeMaster> tblSchemeMasters = allMasterTablesModel.getTblSchememaster();

                            if (tblSchemeMasters != null && tblSchemeMasters.size() > 0) {
                                dbEngine.insertIntoTblSchemeMaster(tblSchemeMasters);
                            } else {
                                blankTablearrayList.add("tblSchemeMasters");
                            }
*/
                    if (allMasterTablesModel.getTblSchemeStoreMapping() != null && allMasterTablesModel.getTblSchemeStoreMapping().size() > 0) {
                        dbengine.Delete_tblStoreProductMap_for_refreshData();
                        List<TblSchemeStoreMapping> tblSchemeStoreMappings = allMasterTablesModel.getTblSchemeStoreMapping();

                        if (tblSchemeStoreMappings != null && tblSchemeStoreMappings.size() > 0) {
                            dbengine.savetblSchemeStoreMapping(tblSchemeStoreMappings);
                        } else {
                            blankTablearrayList.add("tblSchemeStoreMappings");
                        }

                        List<TblSchemeMstr> tblSchemeMstrList = allMasterTablesModel.getTblSchemeMstr();

                        if (tblSchemeMstrList != null && tblSchemeMstrList.size() > 0) {
                            dbengine.savetblSchemeMstr(tblSchemeMstrList);
                        } else {
                            blankTablearrayList.add("tblSchemeMstr");
                        }
                        System.out.println(TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT) + ": Common Function Step 17 Ends  Here");
                        List<TblSchemeSlabDetail> tblSchemeSlabDetailList = allMasterTablesModel.getTblSchemeSlabDetail();

                        if (tblSchemeSlabDetailList != null && tblSchemeSlabDetailList.size() > 0) {
                            dbengine.savetblSchemeSlabDetail(tblSchemeSlabDetailList);
                        } else {
                            blankTablearrayList.add("tblSchemeSlabDetail");
                        }

                        List<TblSchemeSlabBucketDetails> tblSchemeSlabBucketDetailsList = allMasterTablesModel.getTblSchemeSlabBucketDetails();
                        if (tblSchemeSlabBucketDetailsList != null && tblSchemeSlabBucketDetailsList.size() > 0) {
                            dbengine.savetblSchemeSlabBucketDetails(tblSchemeSlabBucketDetailsList);
                        } else {
                            blankTablearrayList.add("tblSchemeSlabBucketDetails");
                        }

                        List<TblSchemeSlabBucketProductMapping> tblSchemeSlabBucketProductMappingList = allMasterTablesModel.getTblSchemeSlabBucketProductMappings();
                        if (tblSchemeSlabBucketProductMappingList != null && tblSchemeSlabBucketProductMappingList.size() > 0) {
                            dbengine.savetblSchemeSlabBucketProductMapping(tblSchemeSlabBucketProductMappingList);
                        } else {
                            blankTablearrayList.add("tblSchemeSlabBucketProductMapping");
                        }


                        List<TblSchemeSlabBenefitsBucketDetails> tblSchemeSlabBenefitsBucketDetailsList = allMasterTablesModel.getTblSchemeSlabBenefitsBucketDetails();
                        if (tblSchemeSlabBenefitsBucketDetailsList != null && tblSchemeSlabBenefitsBucketDetailsList.size() > 0) {
                            dbengine.savetblSchemeSlabBenefitsBucketDetails(tblSchemeSlabBenefitsBucketDetailsList);
                        } else {
                            blankTablearrayList.add("tblSchemeSlabBenefitsBucketDetails");
                        }

                        List<TblSchemeSlabBenefitsProductMappingDetail> tblSchemeSlabBenefitsProductMappingDetailList = allMasterTablesModel.getTblSchemeSlabBenefitsProductMappingDetail();
                        if (tblSchemeSlabBenefitsProductMappingDetailList != null && tblSchemeSlabBenefitsProductMappingDetailList.size() > 0) {
                            dbengine.savetblSchemeSlabBenefitsProductMappingDetail(tblSchemeSlabBenefitsProductMappingDetailList);
                        } else {
                            blankTablearrayList.add("tblSchemeSlabBenefitsProductMappingDetail");
                        }

                        List<TblSchemeSlabBenefitsValueDetail> tblSchemeSlabBenefitsValueDetailList = allMasterTablesModel.getTblSchemeSlabBenefitsValueDetail();
                        if (tblSchemeSlabBenefitsValueDetailList != null && tblSchemeSlabBenefitsValueDetailList.size() > 0) {
                            dbengine.savetblSchemeSlabBenefitsValueDetail(tblSchemeSlabBenefitsValueDetailList);
                        } else {
                            blankTablearrayList.add("tblSchemeSlabBenefitsValueDetail");
                        }

                        List<TblProductRelatedScheme> tblProductRelatedSchemeList = allMasterTablesModel.getTblProductRelatedScheme();
                        if (tblProductRelatedSchemeList != null && tblProductRelatedSchemeList.size() > 0) {
                            dbengine.savetblProductRelatedScheme(tblProductRelatedSchemeList);
                        } else {
                            blankTablearrayList.add("tblProductRelatedScheme");
                        }


                              /*  List<TblProductADDONScheme> tblProductADDONSchemeList = allMasterTablesModel.getTblProductADDONScheme();
                                if (tblProductADDONSchemeList != null && tblProductADDONSchemeList.size() > 0) {
                                    dbEngine.savetblProductADDONScheme(tblProductADDONSchemeList);
                                } else {
                                    blankTablearrayList.add("tblProductADDONScheme");
                                }
*/
                    }
                    System.out.println("Call All Data End Here Step 42 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 43 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 35-------------------------------

                    //table 36-------------------------------
                    if (allMasterTablesModel.getTblSupplierMstrList() != null) {
                        HashMap<String, String> hmapSupplierMarkedForInActive = new HashMap<String, String>();
                        hmapSupplierMarkedForInActive = dbengine.checkSupplierMarkedForInActive();
                        dbengine.Delete_tblSupplierMstrList();

                        List<TblSupplierMstrList> tblSupplierMstrList = allMasterTablesModel.getTblSupplierMstrList();

                        if (tblSupplierMstrList.size() > 0) {
                            dbengine.saveSuplierMstrData(tblSupplierMstrList,hmapSupplierMarkedForInActive);
                           /* for (TblSupplierMstrList SupplierMstrList : tblSupplierMstrList) {
                                dbengine.saveSuplierMstrData(SupplierMstrList.getNodeID(), SupplierMstrList.getNodeType(), SupplierMstrList.getDescr(), SupplierMstrList.getLatCode(), SupplierMstrList.getLongCode(), SupplierMstrList.getFlgMapped(), SupplierMstrList.getAddress(), SupplierMstrList.getState(), SupplierMstrList.getCity(), SupplierMstrList.getPinCode(), SupplierMstrList.getPhoneNo(), SupplierMstrList.getTaxNumber(), SupplierMstrList.getEMailID(), SupplierMstrList.getFlgStockManage(), SupplierMstrList.getFlgDefault(),SupplierMstrList.getIsDiscountAllow(),SupplierMstrList.getIsDiscountApplicable());
                            }*/

                        } else {
                            blankTablearrayList.add("tblSupplierMstrList");
                        }
                    }
                    System.out.println("Call All Data End Here Step 43 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 44 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //Nitish--------------------------------------------------

                    //table 51-------------------------------
                    if (allMasterTablesModel.getTblLastOutstanding() != null) {
                        dbengine.Delete_tblLastOutstanding_for_refreshData();
                        //deleted above
                        List<TblLastOutstanding> tblLastOutstanding = allMasterTablesModel.getTblLastOutstanding();

                        if (tblLastOutstanding.size() > 0) {

                            dbengine.savetblLastOutstanding(tblLastOutstanding);

                        } else {
                            blankTablearrayList.add("tblLastOutstanding");
                        }
                    }
                    System.out.println("Call All Data End Here Step 44 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 45 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 50-------------------------------
                    if (allMasterTablesModel.getTblInvoiceLastVisitDetails() != null) {
                        List<TblInvoiceLastVisitDetails> tblInvoiceLastVisitDetails = allMasterTablesModel.getTblInvoiceLastVisitDetails();

                        if (tblInvoiceLastVisitDetails.size() > 0) {

                            dbengine.savetblInvoiceLastVisitDetails(tblInvoiceLastVisitDetails);

                        } else {
                            blankTablearrayList.add("tblInvoiceLastVisitDetails");
                        }
                    }
                    System.out.println("Call All Data End Here Step 45 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 46 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));


                    //table 49-------------------------------
                    if (allMasterTablesModel.getTblPDAGetExecutionSummary() != null) {
                        dbengine.deltblPDAGetExecutionSummary();
                        //deleted above
                        List<TblPDAGetExecutionSummary> tblPDAGetExecutionSummary = allMasterTablesModel.getTblPDAGetExecutionSummary();

                        if (tblPDAGetExecutionSummary.size() > 0) {

                            dbengine.inserttblForPDAGetExecutionSummary(tblPDAGetExecutionSummary);

                        } else {
                            blankTablearrayList.add("tblPDAGetExecutionSummary");
                        }
                    }
                    System.out.println("Call All Data End Here Step 46 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 47 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));


                    //table 48-------------------------------
                    if (allMasterTablesModel.getTblPDAGetLastOrderDetailsTotalValues() != null) {
                        dbengine.deletetblPDAGetLastOrderDetailsTotalValues();
                        //deleted above
                        List<TblPDAGetLastOrderDetailsTotalValues> tblPDAGetLastOrderDetailsTotalValues = allMasterTablesModel.getTblPDAGetLastOrderDetailsTotalValues();

                        if (tblPDAGetLastOrderDetailsTotalValues.size() > 0) {

                            dbengine.inserttblspForPDAGetLastOrderDetails_TotalValues(tblPDAGetLastOrderDetailsTotalValues);

                        } else {
                            blankTablearrayList.add("tblPDAGetLastOrderDetailsTotalValues");
                        }
                    }
                    System.out.println("Call All Data End Here Step 47 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 48 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));


                    //table 47-------------------------------
                    if (allMasterTablesModel.getTblPDAGetLastOrderDetails() != null) {
                        dbengine.deltblPDAGetLastOrderDetailsData();
                        //deleted above
                        List<TblPDAGetLastOrderDetails> tblPDAGetLastOrderDetails = allMasterTablesModel.getTblPDAGetLastOrderDetails();

                        if (tblPDAGetLastOrderDetails.size() > 0) {
                            dbengine.inserttblForPDAGetLastOrderDetails(tblPDAGetLastOrderDetails);
                        } else {
                            blankTablearrayList.add("tblPDAGetLastOrderDetails");
                        }
                    }
                    System.out.println("Call All Data End Here Step 48 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 49 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 46-------------------------------
                    if (allMasterTablesModel.getTblPDAGetLastVisitDetails() != null) {
                        dbengine.deletetblPDAGetLastVisitDetails();
                        //deleted above
                        List<TblPDAGetLastVisitDetails> tblPDAGetLastVisitDetails = allMasterTablesModel.getTblPDAGetLastVisitDetails();

                        if (tblPDAGetLastVisitDetails.size() > 0) {

                            dbengine.inserttblForPDAGetLastVisitDetails(tblPDAGetLastVisitDetails);

                        } else {
                            blankTablearrayList.add("tblPDAGetLastVisitDetails");
                        }
                    }
                    System.out.println("Call All Data End Here Step 49 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 50 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    //table 45-------------------------------
                    if (allMasterTablesModel.getTblPDAGetLastOrderDate() != null) {
                        dbengine.deletetblPDAGetLastOrderDateData();
                        //deleted above
                        List<TblPDAGetLastOrderDate> tblPDAGetLastOrderDate = allMasterTablesModel.getTblPDAGetLastOrderDate();

                        if (tblPDAGetLastOrderDate.size() > 0) {

                            dbengine.inserttblForPDAGetLastOrderDate(tblPDAGetLastOrderDate);

                        } else {
                            blankTablearrayList.add("tblPDAGetLastOrderDate");
                        }
                    }
                    System.out.println("Call All Data End Here Step 51 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 52 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 44-------------------------------
                    if (allMasterTablesModel.getTblPDAGetLastVisitDate() != null) {
                        dbengine.deletetblPDAGetLastVisitDate();
                        //deleted above
                        List<TblPDAGetLastVisitDate> tblPDAGetLastVisitDate = allMasterTablesModel.getTblPDAGetLastVisitDate();

                        if (tblPDAGetLastVisitDate.size() > 0) {

                            dbengine.inserttblForPDAGetLastVisitDate(tblPDAGetLastVisitDate);

                        } else {
                            blankTablearrayList.add("tblPDAGetLastVisitDate");
                        }
                    }
                    System.out.println("Call All Data End Here Step 52 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 53 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 43-------------------------------
                    if (allMasterTablesModel.getTblPDAGetLODQty() != null) {
                        dbengine.deletetblPDAGetLODQty();
                        //deleted above
                        List<TblPDAGetLODQty> tblPDAGetLODQty = allMasterTablesModel.getTblPDAGetLODQty();

                        if (tblPDAGetLODQty.size() > 0) {

                            dbengine.inserttblLODOnLastSalesSummary(tblPDAGetLODQty);

                        } else {
                            blankTablearrayList.add("tblPDAGetLODQty");
                        }
                    }
                    System.out.println("Call All Data End Here Step 53 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 54 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 42-------------------------------
                    if (allMasterTablesModel.getTblProductListLastVisitStockOrOrderMstr() != null) {
                        dbengine.deletetblProductListLastVisitStockOrOrderMstr();
                        //deleted above
                        List<TblProductListLastVisitStockOrOrderMstr> tblProductListLastVisitStockOrOrderMstr = allMasterTablesModel.getTblProductListLastVisitStockOrOrderMstr();

                        if (tblProductListLastVisitStockOrOrderMstr.size() > 0) {

                            dbengine.savetblProductListLastVisitStockOrOrderMstr(tblProductListLastVisitStockOrOrderMstr);

                        } else {
                            blankTablearrayList.add("tblProductListLastVisitStockOrOrderMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 53 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 54 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    dbengine.deletetblRouteChangeListMstr();
                    if (allMasterTablesModel.getTblRouteChangeListMstr() != null) {
                        List<TblRouteChangeListMstr> tblRouteChangeListMstr = allMasterTablesModel.getTblRouteChangeListMstr();

                        if (tblRouteChangeListMstr.size() > 0) {
                            for (TblRouteChangeListMstr tblRouteChangeListMstrDetails : tblRouteChangeListMstr) {
                                dbengine.inserttblRouteChangeListMstr(tblRouteChangeListMstrDetails.getReasonID(), tblRouteChangeListMstrDetails.getReasonDescr());
                            }

                        } else {
                            blankTablearrayList.add("tblRouteChangeListMstr");
                        }
                    } else {
                        dbengine.inserttblRouteChangeListMstr("1", "Demand from other route");
                        dbengine.inserttblRouteChangeListMstr("2", "Market closed");
                        dbengine.inserttblRouteChangeListMstr("-99", "Other");
                    }
                    System.out.println("Call All Data End Here Step 54 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 55 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 41-------------------------------
                    if (allMasterTablesModel.getTblStoreListMaster() != null) {
                        HashMap<String, String> hmapStoreIdSstat = new HashMap<String, String>();
                        hmapStoreIdSstat = dbengine.checkForStoreIdSstat();
                        HashMap<String, String> hmapflgOrderType = new HashMap<String, String>();
                        hmapflgOrderType = dbengine.checkForStoreflgOrderType();
                        HashMap<String, String> hmapStoreIdNewStore = new HashMap<String, String>();
                        hmapStoreIdNewStore = dbengine.checkForStoreIdIsNewStore();


                        HashMap<String, String> hmapStoreIdflgMapped = new HashMap<String, String>();
                        hmapStoreIdflgMapped = dbengine.checkForStoreIdFlgMapped();

                        HashMap<String, String> hmapStoreIdflgisDiscountApplicable = new HashMap<String, String>();
                        hmapStoreIdflgisDiscountApplicable = dbengine.checkForStoreIdIsDiscountApplicable();

                        HashMap<String, String> hmapStoreIdflgisDiscountAllowed = new HashMap<String, String>();
                        hmapStoreIdflgisDiscountAllowed = dbengine.checkForStoreIdIsDiscountAllowed();

                        HashMap<String, String> hmapStoreIdUpdatedContactNumber = new HashMap<String, String>();
                        hmapStoreIdUpdatedContactNumber = dbengine.checkForStoreIdUpdatedContactNumber();

                        //  dbengine.Delete_tblStore_for_refreshDataButNotNewStore();
                        //    dbengine.fndeleteStoreAddressMapDetailsMstr();

                        //deleted above
                        List<TblStoreListMaster> tblStoreListMaster = allMasterTablesModel.getTblStoreListMaster();

                        if (tblStoreListMaster.size() > 0) {
                            dbengine.saveSOAPdataStoreList(tblStoreListMaster, hmapStoreIdSstat, hmapflgOrderType, hmapStoreIdNewStore, hmapStoreIdflgMapped, hmapStoreIdUpdatedContactNumber,hmapStoreIdflgisDiscountApplicable, hmapStoreIdflgisDiscountAllowed);

                        } else {
                            blankTablearrayList.add("tblStoreListMaster");
                        }
                        if (hmapStoreIdSstat != null && hmapStoreIdSstat.size() > 0) {
                            hmapStoreIdSstat.clear();
                            hmapflgOrderType.clear();
                            hmapStoreIdNewStore.clear();
                            hmapStoreIdUpdatedContactNumber.clear();
                            hmapStoreIdflgMapped.clear();
                            hmapStoreIdSstat = null;
                            hmapflgOrderType = null;
                            hmapStoreIdNewStore = null;
                            hmapStoreIdUpdatedContactNumber = null;
                            hmapStoreIdflgMapped = null;

                        }
                    }
                    System.out.println("Call All Data End Here Step 54 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 55 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    HashMap<String, String> hmapStoreListForUpdateMstrList = new HashMap<String, String>();
                    hmapStoreListForUpdateMstrList = dbengine.fetchHmapStoreListForUpdateMstrList();
                    if (allMasterTablesModel.getTblStoreListForUpdateMstr() != null) {
                        //  dbengine.Delete_tblRouteMasterMstr();
                        List<TblStoreListForUpdateMstr> tblStoreListForUpdateMstrList = allMasterTablesModel.getTblStoreListForUpdateMstr();
                        if (tblStoreListForUpdateMstrList.size() > 0) {
                            dbengine.fnInsertOrUpdate_tblStoreListForUpdateMstr(tblStoreListForUpdateMstrList, hmapStoreListForUpdateMstrList);
                        } else {
                            blankTablearrayList.add("tblStoreListForUpdateMstr");
                        }
                    }
                    System.out.println("Call All Data End Here Step 54 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 55 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //table 40-------------------------------
                    if (allMasterTablesModel.getTblStoreListWithPaymentAddress() != null) {
                        //deleted above
                        List<TblStoreListWithPaymentAddress> tblStoreListWithPaymentAddress = allMasterTablesModel.getTblStoreListWithPaymentAddress();

                        if (tblStoreListWithPaymentAddress.size() > 0) {
                            dbengine.saveSOAPdataStoreListAddressMap(tblStoreListWithPaymentAddress);
                        } else {
                            blankTablearrayList.add("tblStoreListWithPaymentAddressMR");
                        }
                    }


                    //table 40-------------------------------

                    //deleted above
                    System.out.println("Call All Data End Here Step 55 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 56 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblStoreSomeProdQuotePriceMstr() != null) {
                        List<TblStoreSomeProdQuotePriceMstr> tblStoreSomeProdQuotePriceMstr = allMasterTablesModel.getTblStoreSomeProdQuotePriceMstr();

                        if (tblStoreSomeProdQuotePriceMstr.size() > 0) {

                            dbengine.insertMinDelQty(tblStoreSomeProdQuotePriceMstr);

                        } else {
                            blankTablearrayList.add("tblStoreSomeProdQuotePriceMstr");
                        }
                    }

                    //table 39-------------------------------

                    //deleted above
                    System.out.println("Call All Data End Here Step 56 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 57 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    if (allMasterTablesModel.getTblStoreLastDeliveryNoteNumber() != null) {
                        List<TblStoreLastDeliveryNoteNumber> tblStoreLastDeliveryNoteNumber = allMasterTablesModel.getTblStoreLastDeliveryNoteNumber();

                        if (tblStoreLastDeliveryNoteNumber.size() > 0) {

                            for (TblStoreLastDeliveryNoteNumber tblStoreLastDeliveryNoteNumberData : tblStoreLastDeliveryNoteNumber) {
                                int LastDeliveryNoteNumber = 0;
                                LastDeliveryNoteNumber = tblStoreLastDeliveryNoteNumberData.getLastDeliveryNoteNumber();
                                int valExistingDeliveryNoteNumber = 0;
                                valExistingDeliveryNoteNumber = dbengine.fnGettblStoreLastDeliveryNoteNumber();
                                if (valExistingDeliveryNoteNumber < LastDeliveryNoteNumber) {
                                    dbengine.Delete_tblStoreLastDeliveryNoteNumber();
                                    dbengine.savetblStoreLastDeliveryNoteNumber(LastDeliveryNoteNumber);
                                }
                            }


                        } else {
                            blankTablearrayList.add("tblStoreLastDeliveryNoteNumber");
                        }
                    }


                    System.out.println("Call All Data End Here Step 57 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 58 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    HashMap<String, String> hmapInvoiceOrderIDandStatus = new HashMap<String, String>();
                    hmapInvoiceOrderIDandStatus = dbengine.fetchHmapInvoiceOrderIDandStatus();
                    if (allMasterTablesModel.getTblPendingInvoices() != null) {
                        List<TblPendingInvoices> tblPendingInvoices = allMasterTablesModel.getTblPendingInvoices();

                        if (tblPendingInvoices.size() > 0) {

                            dbengine.inserttblPendingInvoices(tblPendingInvoices, hmapInvoiceOrderIDandStatus, imei);

                        } else {
                            blankTablearrayList.add("tblPendingInvoices");
                        }
                    }
                    System.out.println("Call All Data End Here Step 58 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 59 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblInvoiceExecutionProductList() != null) {

                        List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList = allMasterTablesModel.getTblInvoiceExecutionProductList();
                        dbengine.fnDeletetblInvoiceExecutionProductList();
                        if (tblInvoiceExecutionProductList.size() > 0) {

                            dbengine.inserttblInvoiceExecutionProductList(tblInvoiceExecutionProductList);

                        } else {
                            blankTablearrayList.add("tblInvoiceExecutionProductList");
                        }
                    }
                    System.out.println("Call All Data End Here Step 59 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 60 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblProductWiseInvoice() != null) {
                        List<TblProductWiseInvoice> tblProductWiseInvoice = allMasterTablesModel.getTblProductWiseInvoice();
                        if (tblProductWiseInvoice.size() > 0) {

                            dbengine.inserttblProductWiseInvoice(tblProductWiseInvoice, hmapInvoiceOrderIDandStatus);

                        } else {
                            blankTablearrayList.add("tblProductWiseInvoice");
                        }
                    }
                    System.out.println("Call All Data End Here Step 60 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 61 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    //nitishtable
                    boolean isDistributorStockSave = false;
                    if (flgCldFrm == 0) {
                        isDistributorStockSave = true;
                    } else if ((flgCldFrm == 1) && (CommonInfo.hmapAppMasterFlags.get("flgDBRStockCalculate") == 2)) {
                        isDistributorStockSave = true;
                    }
                    if (isDistributorStockSave) {
                        if (allMasterTablesModel.getTblDistributorIDOrderIDLeft() != null) {
                            dbengine.deleteDistStock();
                            List<TblDistributorIDOrderIDLeft> tblDistributorIDOrderIDLeft = allMasterTablesModel.getTblDistributorIDOrderIDLeft();
                            if (tblDistributorIDOrderIDLeft.size() > 0) {

                                for (TblDistributorIDOrderIDLeft tblDistributorIDOrderIDLeftData : tblDistributorIDOrderIDLeft) {
                                    dbengine.inserttblDistributorIDOrderIDLeft(tblDistributorIDOrderIDLeftData.getCustomer(), tblDistributorIDOrderIDLeftData.getPDAOrderId());
                                }


                            } else {
                                blankTablearrayList.add("tblDistributorIDOrderIDLeft");
                            }
                        }

                        System.out.println("Call All Data End Here Step 61 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        System.out.println("Call All Data Start Here Step 62 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        if (allMasterTablesModel.getTblDistributorProductStock() != null) {
                            List<TblDistributorProductStock> tblDistributorProductStock = allMasterTablesModel.getTblDistributorProductStock();
                            if (tblDistributorProductStock.size() > 0) {


                                dbengine.inserttblDistributorProductStock(tblDistributorProductStock);

                            } else {
                                blankTablearrayList.add("tblDistributorProductStock");
                            }
                        }
                        System.out.println("Call All Data End Here Step 62 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                        System.out.println("Call All Data Start Here Step 63 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    }

                    System.out.println("Call All Data End Here Step 63 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 64 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblReasonOrderCncl() != null) {
                        dbengine.fnDeleteUnWantedSubmitedInvoiceOrders();

                        hmapInvoiceOrderIDandStatus = null;

                        dbengine.delTblReasonOrderCncl();
                        List<TblReasonOrderCncl> tblReasonOrderCncl = allMasterTablesModel.getTblReasonOrderCncl();

                        if (tblReasonOrderCncl.size() > 0) {
                            for (TblReasonOrderCncl tblReasonOrderCnclModel : tblReasonOrderCncl) {
                                dbengine.insertReasonCanclOrder(tblReasonOrderCnclModel.getReasonCodeID(), tblReasonOrderCnclModel.getReasonDescr());
                            }
                        } else {
                            blankTablearrayList.add("tblReasonOrderCncl");
                        }
                    }


                /*    dbengine.deleteIncentivesTbles();


                    List<TblIncentiveMainMaster> tblIncentiveMaster=  allMasterTablesModel.getTblIncentiveMainMaster();

                    if(tblIncentiveMaster.size()>0){
                        for(TblIncentiveMainMaster tblIncentiveMasterModel:tblIncentiveMaster){
                            dbengine.savetblIncentiveMaster(tblIncentiveMasterModel.getIncId(),tblIncentiveMasterModel.getOutputType(),tblIncentiveMasterModel.getIncentiveName(),""+tblIncentiveMasterModel.getFlgAcheived(),""+tblIncentiveMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveMaster");
                    }

                    List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster=  allMasterTablesModel.getTblIncentiveSecondaryMaster();

                    if(tblIncentiveSecondaryMaster.size()>0){
                        for(TblIncentiveSecondaryMaster tblIncentiveSecondaryMasterModel:tblIncentiveSecondaryMaster){
                            dbengine.savetblIncentiveSeondaryMaster(tblIncentiveSecondaryMasterModel.getIncSlabId(),tblIncentiveSecondaryMasterModel.getIncId(), tblIncentiveSecondaryMasterModel.getOutputType(), tblIncentiveSecondaryMasterModel.getIncSlabName(),""+tblIncentiveSecondaryMasterModel.getFlgAcheived(),""+tblIncentiveSecondaryMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveSecondaryMaster");
                    }
                    Object tblIncentiveDetailsData=allMasterTablesModel.getTblIncentiveDetailsData();
                    ArrayList<HashMap<String,String>> tblIncentiveDetailsDataTable=(ArrayList<HashMap<String,String>>) tblIncentiveDetailsData;

                    if( tblIncentiveDetailsDataTable.size()>0) {
                        for(int i=0;i<10;i++)
                        {
                           String sdqeqwe= tblIncentiveDetailsDataTable.get(0).toString();
                        }
                    }


*/

                    System.out.println("Call All Data End Here Step 64 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 65 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblQuestionsSurvey() != null) {

                        dbengine.deleteSurveyTables();
                        List<TblQuestionsSurvey> tblQuestionsSurvey = allMasterTablesModel.getTblQuestionsSurvey();

                        if (tblQuestionsSurvey.size() > 0) {
                            for (TblQuestionsSurvey tblQuestionsSurveyModel : tblQuestionsSurvey) {
                                dbengine.fnsavetblQuestionsSurvey("" + tblQuestionsSurveyModel.getQstnID(), tblQuestionsSurveyModel.getQstnText(), "" + tblQuestionsSurveyModel.getFlgActive(), "" + tblQuestionsSurveyModel.getFlgOrder());
                            }
                        } else {
                            blankTablearrayList.add("tblQuestionsSurvey");
                        }
                    }

                    System.out.println("Call All Data End Here Step 65 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 66 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblOptionSurvey() != null) {
                        List<TblOptionSurvey> tblOptionSurvey = allMasterTablesModel.getTblOptionSurvey();

                        if (tblOptionSurvey.size() > 0) {
                            for (TblOptionSurvey tblOptionSurveyModel : tblOptionSurvey) {
                                dbengine.fnsavetblOptionSurvey("" + tblOptionSurveyModel.getQstnID(), tblOptionSurveyModel.getOptionText(), "" + tblOptionSurveyModel.getQstnID(), "" + tblOptionSurveyModel.getFlgaActive());
                            }
                        } else {
                            blankTablearrayList.add("tblOptionSurvey");
                        }
                    }

                    System.out.println("Call All Data End Here Step 66 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 67 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblForPDAGetPDAStoreSummary() != null) {
                        if (allMasterTablesModel.getTblForPDAGetPDAStoreSummary().size() > 0) {
                            dbengine.fnsavetblPDASummary(allMasterTablesModel.getTblForPDAGetPDAStoreSummary());
                        } else {
                            blankTablearrayList.add("tblForPDAGetPDAStoreSummary");
                        }
                    }

                    System.out.println("Call All Data End Here Step 67 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 68 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblStoreSummaryDetails() != null) {
                        if (allMasterTablesModel.getTblStoreSummaryDetails().size() > 0) {
                            dbengine.fnsavetblStoreSummaryDetails(allMasterTablesModel.getTblStoreSummaryDetails());
                        } else {
                            blankTablearrayList.add("tblStoreSummaryDetails");
                        }
                    }

                    System.out.println("Call All Data End Here Step 69 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 70 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblForPDAStoreVisitHistory() != null) {
                        if (allMasterTablesModel.getTblForPDAStoreVisitHistory().size() > 0) {
                            dbengine.fnSaveTblPDAStoreVisitHistory(allMasterTablesModel.getTblForPDAStoreVisitHistory());
                        } else {
                            blankTablearrayList.add("tblStoreSummary");
                        }
                    }

                    System.out.println("Call All Data End Here Step 70 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 71 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblForPDAStoreOrderHistory() != null) {
                        if (allMasterTablesModel.getTblForPDAStoreOrderHistory().size() > 0) {
                            dbengine.fnSaveTblPDAStoreOrderHistory(allMasterTablesModel.getTblForPDAStoreOrderHistory());
                        } else {
                            blankTablearrayList.add("tblOrderHistory");
                        }
                    }


                    if (allMasterTablesModel.getTblUOMTypeConverstionList() != null) {
                        if (allMasterTablesModel.getTblUOMTypeConverstionList().size() > 0) {
                            dbengine.fnSaveTblUOMTypeConverstionList(allMasterTablesModel.getTblUOMTypeConverstionList());
                        } else {
                            blankTablearrayList.add("tblUOMTypeConverstion");
                        }
                    }


                    System.out.println("Call All Data End Here Step 71 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 72 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblStoreTARSSummary() != null) {
                        if (allMasterTablesModel.getTblStoreTARSSummary().size() > 0) {
                            dbengine.fnSaveTblStoreTARSSummary(allMasterTablesModel.getTblStoreTARSSummary());
                        } else {
                            blankTablearrayList.add("tblStoreTARSSummary");
                        }
                    }

                    System.out.println("Call All Data End Here Step 72 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 73 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblProductLevelData() != null) {
                        if (allMasterTablesModel.getTblProductLevelData().size() > 0) {
                            dbengine.fnSaveTblProductLevelData(allMasterTablesModel.getTblProductLevelData());
                        } else {
                            blankTablearrayList.add("tblProductLevelData");
                        }
                    }

                    System.out.println("Call All Data End Here Step 72.1 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 73.1 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblPrdMstrTransactionUOMConfig() != null) {
                        if (allMasterTablesModel.getTblPrdMstrTransactionUOMConfig().size() > 0) {
                            dbengine.fnSaveTblPrdMstrTransactionUOMConfig(allMasterTablesModel.getTblPrdMstrTransactionUOMConfig());
                        } else {
                            blankTablearrayList.add("tblPrdMstrTransactionUOMConfig");
                        }
                    }



                    if (allMasterTablesModel.getTblAppliedOrderDiscountList() != null) {
                        if (allMasterTablesModel.getTblAppliedOrderDiscountList().size() > 0) {
                            dbengine.fnSaveTblAppliedOrderDiscount(allMasterTablesModel.getTblAppliedOrderDiscountList());
                        } else {
                            blankTablearrayList.add("tblAppliedOrderDiscountList");
                        }
                    }



                    System.out.println("Call All Data End Here Step 73 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 74 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    HashMap<String, String> hmapOrderIDPDAStoreIDs = dbengine.fnGetHmapOrderIDPDAStoreIDs();
                    HashMap<String, String> hmapOrderIDPDAProductIDs = dbengine.fnGetHmapOrderIDPDAProductIDs();
                    HashMap<String, String> hmapStoreIDVisitCodeAgainstTC = new HashMap<String, String>();
                    HashMap<String, String> hmapStoreIDOrderIDAgainstTC = new HashMap<String, String>();
                    dbengine.fnDeleteTCOrderTable();
                    if (allMasterTablesModel.getTblInvoiceHeader() != null) {
                        if (allMasterTablesModel.getTblInvoiceHeader().size() > 0) {
                            for (TblInvoiceHeaderServer tblInvoiceHeaderServer : allMasterTablesModel.getTblInvoiceHeader()) {

                                long syncTIMESTAMP = System.currentTimeMillis();
                                Date dateobj = new Date(syncTIMESTAMP);
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

                                RandomString cxz = new RandomString();
                                String randomString=cxz.nextString();
                                String VisitStartTS = df.format(new Date());
                                String imeinew = AppUtils.getToken(context);
                                String IMEIid = imeinew.substring(9);

                                String OrdID = "";
                                String OrderIDPDA = "NA";
                                if (tblInvoiceHeaderServer.getTmpInvoiceCodePDA() != null) {
                                    OrdID = tblInvoiceHeaderServer.getTmpInvoiceCodePDA();

                                }
                              //  OrderIDPDA = "Order" + "-" + tblInvoiceHeaderServer.getStoreID() + "-" + AppUtils.getToken(context) + "-" + OrdID;
                                OrderIDPDA = "Order" +  "-" + IMEIid + "-" + randomString + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim() + "-" + OrdID;

                                hmapStoreIDOrderIDAgainstTC.put(tblInvoiceHeaderServer.getStoreID(),OrderIDPDA);
                                String StoreVisitCode = "StoreVisit" + "-" + IMEIid + "-" + randomString + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim() + "-" + OrdID;
                                hmapStoreIDVisitCodeAgainstTC.put(tblInvoiceHeaderServer.getStoreID(),StoreVisitCode);
                                tblInvoiceHeaderServer.setTmpInvoiceCodePDA(OrderIDPDA);
                                tblInvoiceHeaderServer.setStoreVisitCode(StoreVisitCode);
                                String TeleCallingID = "0";
                                if (tblInvoiceHeaderServer.getTeleCallingID() != null)
                                    TeleCallingID = tblInvoiceHeaderServer.getTeleCallingID();

                                if (hmapOrderIDPDAStoreIDs!=null && hmapOrderIDPDAStoreIDs.size()>0 && !hmapOrderIDPDAStoreIDs.containsKey(OrderIDPDA)) {
                                    dbengine.saveStoreTempInvoiceTCOrder(tblInvoiceHeaderServer.getStoreVisitCode(), tblInvoiceHeaderServer.getTmpInvoiceCodePDA(), tblInvoiceHeaderServer.getStoreID(), tblInvoiceHeaderServer.getInvoiceDate(), tblInvoiceHeaderServer.getTotalBeforeTaxDis(), tblInvoiceHeaderServer.getTaxAmt(), tblInvoiceHeaderServer.getTotalDis(), tblInvoiceHeaderServer.getInvoiceVal(), tblInvoiceHeaderServer.getFreeTotal(), tblInvoiceHeaderServer.getInvAfterDis(), tblInvoiceHeaderServer.getAddDis(), tblInvoiceHeaderServer.getNoCoupon(), tblInvoiceHeaderServer.getTotalCoupunAmount(), tblInvoiceHeaderServer.getTransDate(), Integer.parseInt(tblInvoiceHeaderServer.getFlgInvoiceType()), tblInvoiceHeaderServer.getFlgWholeSellApplicable(), 1, 1, 0, tblInvoiceHeaderServer.getRouteNodeID(), tblInvoiceHeaderServer.getRouteNodeType(), TeleCallingID);
                                }
                            }
                        } else {
                            blankTablearrayList.add("TblInvoiceHeaderServer");
                        }
                    }

                    System.out.println("Call All Data End Here Step 74 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    System.out.println("Call All Data Start Here Step 75 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));
                    if (allMasterTablesModel.getTblInvoiceDetail() != null) {
                        if (allMasterTablesModel.getTblInvoiceDetail().size() > 0) {
                            for (TblInvoiceDetailsServer tblInvoiceDetailsServer : allMasterTablesModel.getTblInvoiceDetail()) {
                                String OrdID = "";
                                String OrderIDPDA = "NA";
                                String CartonID="0";
                                if (tblInvoiceDetailsServer.getTmpInvoiceCodePDA() != null) {
                                    OrdID = tblInvoiceDetailsServer.getTmpInvoiceCodePDA();

                                }
                                if (tblInvoiceDetailsServer.getCartonID() != null) {
                                    CartonID = tblInvoiceDetailsServer.getCartonID();

                                }
                                if(hmapStoreIDOrderIDAgainstTC!=null && hmapStoreIDOrderIDAgainstTC.size()>0  && hmapStoreIDOrderIDAgainstTC.containsKey(tblInvoiceDetailsServer.getStoreID()))
                                    OrderIDPDA=hmapStoreIDOrderIDAgainstTC.get(tblInvoiceDetailsServer.getStoreID());
                                    else
                                OrderIDPDA = "Order" + "-" + tblInvoiceDetailsServer.getStoreID() + "-" + AppUtils.getToken(context) + "-" + OrdID;


                                if (!hmapOrderIDPDAStoreIDs.containsKey(OrderIDPDA)) {

                                }
                                tblInvoiceDetailsServer.setTmpInvoiceCodePDA(OrderIDPDA);
                                String StoreVisitCode = "StoreVisit" + "-" + tblInvoiceDetailsServer.getStoreID() + "-" + AppUtils.getToken(context) + "-" + OrdID;


                                //  tblInvoiceDetailsServer.se(StoreVisitCode);
                                if (!hmapOrderIDPDAProductIDs.containsValue(OrderIDPDA)) {
                                    dbengine.fnsaveStoreTempOrderEntryDetailsTCOrder(tblInvoiceDetailsServer.getTmpInvoiceCodePDA(), tblInvoiceDetailsServer.getStoreID(), tblInvoiceDetailsServer.getCatID(), tblInvoiceDetailsServer.getProdID(), tblInvoiceDetailsServer.getProductPrice(), tblInvoiceDetailsServer.getTaxRate(), tblInvoiceDetailsServer.getFlgRuleTaxVal(), tblInvoiceDetailsServer.getOrderQty(), tblInvoiceDetailsServer.getUOMId(), tblInvoiceDetailsServer.getLineValBfrTxAftrDscnt(), tblInvoiceDetailsServer.getLineValAftrTxAftrDscnt(), tblInvoiceDetailsServer.getFreeQty(), tblInvoiceDetailsServer.getDisVal(), tblInvoiceDetailsServer.getSampleQuantity(), tblInvoiceDetailsServer.getProductShortName(), tblInvoiceDetailsServer.getTaxValue(), tblInvoiceDetailsServer.getOrderIDPDA(), tblInvoiceDetailsServer.getFlgIsQuoteRateApplied(), tblInvoiceDetailsServer.getFlgWholeSellApplicable(), tblInvoiceDetailsServer.getServingDBRId(), 1, tblInvoiceDetailsServer.getProductExtraOrder(), "" + tblInvoiceDetailsServer.getOrderVal(), tblInvoiceDetailsServer.getSuggestedQty(), tblInvoiceDetailsServer.getOrderQty(), 3, 1, tblInvoiceDetailsServer.getFlgUserEditedProduct(), tblInvoiceDetailsServer.getOrderQty(), "0",CartonID,0,tblInvoiceDetailsServer.getDiscperpcs());
                                } else {
                                    if (!hmapOrderIDPDAProductIDs.containsKey("" + tblInvoiceDetailsServer.getProdID())) {

                                        dbengine.fnsaveStoreTempOrderEntryDetailsTCOrder(tblInvoiceDetailsServer.getTmpInvoiceCodePDA(), tblInvoiceDetailsServer.getStoreID(), tblInvoiceDetailsServer.getCatID(), tblInvoiceDetailsServer.getProdID(), tblInvoiceDetailsServer.getProductPrice(), tblInvoiceDetailsServer.getTaxRate(), tblInvoiceDetailsServer.getFlgRuleTaxVal(), tblInvoiceDetailsServer.getOrderQty(), tblInvoiceDetailsServer.getUOMId(), tblInvoiceDetailsServer.getLineValBfrTxAftrDscnt(), tblInvoiceDetailsServer.getLineValAftrTxAftrDscnt(), tblInvoiceDetailsServer.getFreeQty(), tblInvoiceDetailsServer.getDisVal(), tblInvoiceDetailsServer.getSampleQuantity(), tblInvoiceDetailsServer.getProductShortName(), tblInvoiceDetailsServer.getTaxValue(), tblInvoiceDetailsServer.getOrderIDPDA(), tblInvoiceDetailsServer.getFlgIsQuoteRateApplied(), tblInvoiceDetailsServer.getFlgWholeSellApplicable(), tblInvoiceDetailsServer.getServingDBRId(), 1, tblInvoiceDetailsServer.getProductExtraOrder(), "" + tblInvoiceDetailsServer.getOrderVal(), tblInvoiceDetailsServer.getSuggestedQty(), tblInvoiceDetailsServer.getOrderQty(), 3, 1, tblInvoiceDetailsServer.getFlgUserEditedProduct(), tblInvoiceDetailsServer.getOrderQty(), "0",CartonID,0,tblInvoiceDetailsServer.getDiscperpcs());
                                    }
                                }
                            }
                        } else {
                            blankTablearrayList.add("TblInvoiceDetailsServer");
                        }
                    }

                    hmapStoreIDVisitCodeAgainstTC.clear();
                    hmapStoreIDOrderIDAgainstTC.clear();

                    dbengine.deleteTblBeatPlan();
                    // BeatPlansData beatPlansData = response.body();
                    List<TblBeatPlans> beatPlans = allMasterTablesModel.getTblBeatPlan();//.getTblBeatPlans();
                  //  if(beatPlans!=null && beatPlans.size()>0)
                    dbengine.insertIntoTblBeatPlan(beatPlans);

                    dbengine.deleteTblBeatWiseSpokeStoreVisitSchedule();

                    List<TblBeatWiseSpokeStoreVisitSchedule> tblBeatWiseSpokeStoreVisitSchedules = allMasterTablesModel.getTblBeatWiseSpokeStoreVisitSchedule();

                    if (tblBeatWiseSpokeStoreVisitSchedules != null && tblBeatWiseSpokeStoreVisitSchedules.size() > 0) {
                        dbengine.insertIntoTblBeatWiseSpokeStoreVisitSchedule(tblBeatWiseSpokeStoreVisitSchedules);
                    }
                    System.out.println("Call All Data End Here Step 75 :" + TimeUtils.getCurrentDeviceTime(TimeUtils.DATE_TIME_FORMAT));

                    dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllMasterTablesModel> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getAllExeutionData(Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Refreshing data..")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();


        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        CommonInfo.flgDrctslsIndrctSls = dbengine.fnGetflgDrctslsIndrctSlsForDSR(Integer.parseInt(CoverageNodeId), Integer.parseInt(CoverageNodeType));
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        Data data = new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);
        data.setFlgAllRouteData(1);

        data.setInvoiceList(arrDistinctInvoiceNumbersNew);
        // data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<ExecutionModelsData> call = apiService.Call_AllExecutionData(data);
        call.enqueue(new Callback<ExecutionModelsData>() {
            @Override
            public void onResponse(Call<ExecutionModelsData> call, Response<ExecutionModelsData> response) {
                if (response.code() == 200) {
                    ExecutionModelsData allMasterTablesModel = response.body();

                    HashMap<String, String> hmapInvoiceOrderIDandStatus = new HashMap<String, String>();
                    hmapInvoiceOrderIDandStatus = dbengine.fetchHmapInvoiceOrderIDandStatus();
                    if (allMasterTablesModel.getTblPendingInvoices() != null) {
                        List<TblPendingInvoices> tblPendingInvoices = allMasterTablesModel.getTblPendingInvoices();

                        if (tblPendingInvoices.size() > 0) {

                            dbengine.inserttblPendingInvoices(tblPendingInvoices, hmapInvoiceOrderIDandStatus, imei);

                        } else {
                            blankTablearrayList.add("tblPendingInvoices");
                        }
                    }


                    if (allMasterTablesModel.getTblInvoiceExecutionProductList() != null) {

                        List<TblInvoiceExecutionProductList> tblInvoiceExecutionProductList = allMasterTablesModel.getTblInvoiceExecutionProductList();
                        dbengine.fnDeletetblInvoiceExecutionProductList();
                        if (tblInvoiceExecutionProductList.size() > 0) {

                            dbengine.inserttblInvoiceExecutionProductList(tblInvoiceExecutionProductList);

                        } else {
                            blankTablearrayList.add("tblInvoiceExecutionProductList");
                        }
                    }


                    if (allMasterTablesModel.getTblProductWiseInvoice() != null) {
                        List<TblProductWiseInvoice> tblProductWiseInvoice = allMasterTablesModel.getTblProductWiseInvoice();
                        if (tblProductWiseInvoice.size() > 0) {

                            dbengine.inserttblProductWiseInvoice(tblProductWiseInvoice, hmapInvoiceOrderIDandStatus);

                        } else {
                            blankTablearrayList.add("tblProductWiseInvoice");
                        }
                    }




                   /* if(allMasterTablesModel.getTblReasonOrderCncl()!=null) {
                        dbengine.fnDeleteUnWantedSubmitedInvoiceOrders();

                        hmapInvoiceOrderIDandStatus = null;

                        dbengine.delTblReasonOrderCncl();
                        List<TblReasonOrderCncl> tblReasonOrderCncl = allMasterTablesModel.getTblReasonOrderCncl();

                        if (tblReasonOrderCncl.size() > 0) {
                            for (TblReasonOrderCncl tblReasonOrderCnclModel : tblReasonOrderCncl) {
                                dbengine.insertReasonCanclOrder(tblReasonOrderCnclModel.getReasonCodeID(), tblReasonOrderCnclModel.getReasonDescr());
                            }
                        } else {
                            blankTablearrayList.add("tblReasonOrderCncl");
                        }
                    }*/


                /*    dbengine.deleteIncentivesTbles();


                    List<TblIncentiveMainMaster> tblIncentiveMaster=  allMasterTablesModel.getTblIncentiveMainMaster();

                    if(tblIncentiveMaster.size()>0){
                        for(TblIncentiveMainMaster tblIncentiveMasterModel:tblIncentiveMaster){
                            dbengine.savetblIncentiveMaster(tblIncentiveMasterModel.getIncId(),tblIncentiveMasterModel.getOutputType(),tblIncentiveMasterModel.getIncentiveName(),""+tblIncentiveMasterModel.getFlgAcheived(),""+tblIncentiveMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveMaster");
                    }

                    List<TblIncentiveSecondaryMaster> tblIncentiveSecondaryMaster=  allMasterTablesModel.getTblIncentiveSecondaryMaster();

                    if(tblIncentiveSecondaryMaster.size()>0){
                        for(TblIncentiveSecondaryMaster tblIncentiveSecondaryMasterModel:tblIncentiveSecondaryMaster){
                            dbengine.savetblIncentiveSeondaryMaster(tblIncentiveSecondaryMasterModel.getIncSlabId(),tblIncentiveSecondaryMasterModel.getIncId(), tblIncentiveSecondaryMasterModel.getOutputType(), tblIncentiveSecondaryMasterModel.getIncSlabName(),""+tblIncentiveSecondaryMasterModel.getFlgAcheived(),""+tblIncentiveSecondaryMasterModel.getEarning());
                        }
                    }
                    else{
                        blankTablearrayList.add("tblIncentiveSecondaryMaster");
                    }
                    Object tblIncentiveDetailsData=allMasterTablesModel.getTblIncentiveDetailsData();
                    ArrayList<HashMap<String,String>> tblIncentiveDetailsDataTable=(ArrayList<HashMap<String,String>>) tblIncentiveDetailsData;

                    if( tblIncentiveDetailsDataTable.size()>0) {
                        for(int i=0;i<10;i++)
                        {
                           String sdqeqwe= tblIncentiveDetailsDataTable.get(0).toString();
                        }
                    }


*/
                    dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<ExecutionModelsData> call, Throwable t) {
                System.out.println();
                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();

                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllSummaryReportData(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryReportDay> call = apiService.Call_AllSummaryReportDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryReportDay>() {
            @Override
            public void onResponse(Call<AllSummaryReportDay> call, Response<AllSummaryReportDay> response) {
                if (response.code() == 200) {
                    AllSummaryReportDay allSummaryReportDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateAllSummaryDayDataTable();
                    List<TblAllSummaryDay> tblAllSummaryDay = allSummaryReportDayModel.getTblAllSummaryDay();
                    if (tblAllSummaryDay.size() > 0) {
                        dbengine.savetblAllSummaryDayAndMTD(tblAllSummaryDay);
                    } else {
                        blankTablearrayList.add("tblAllSummaryDay");
                    }
                    if(allSummaryReportDayModel.getTblLastRefreshTimeReport()!=null)
                    {
                        List<TblLastRefreshTimeReport> tblLastRefreshTimeReportList=allSummaryReportDayModel.getTblLastRefreshTimeReport();
                        if(tblLastRefreshTimeReportList!=null && tblLastRefreshTimeReportList.size()>0)
                        {
                            dbengine.savetblAllSummaryLastRefreshTimeReport(tblLastRefreshTimeReportList);
                        }
                    }

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryReportDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllSKUWiseMTDSummaryReport(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummarySKUWiseDay> call = apiService.Call_AllSummarySKUWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummarySKUWiseDay>() {
            @Override
            public void onResponse(Call<AllSummarySKUWiseDay> call, Response<AllSummarySKUWiseDay> response) {
                if (response.code() == 200) {
                    AllSummarySKUWiseDay allSummarySKUWiseDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateSKUDataTable();
                    List<TblSKUWiseDaySummary> tblSKUWiseDaySummary = allSummarySKUWiseDayModel.getTblSKUWiseDaySummary();
                    if (tblSKUWiseDaySummary.size() > 0) {
                        dbengine.savetblSKUWiseDaySummary(tblSKUWiseDaySummary);
                    } else {
                        blankTablearrayList.add("tblSKUWiseDaySummary");
                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummarySKUWiseDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllStoreWiseMTDSummaryReport(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryStoreWiseDay> call = apiService.Call_AllSummaryStoreWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryStoreWiseDay>() {
            @Override
            public void onResponse(Call<AllSummaryStoreWiseDay> call, Response<AllSummaryStoreWiseDay> response) {
                if (response.code() == 200) {
                    AllSummaryStoreWiseDay allSummaryStoreWiseDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateStoreWiseDataTable();
                    List<TblStoreWiseDaySummary> tblStoreWiseDaySummary = allSummaryStoreWiseDayModel.getTblStoreWiseDaySummary();
                    if (tblStoreWiseDaySummary.size() > 0) {
                        dbengine.savetblStoreWiseDaySummary(tblStoreWiseDaySummary);
                    } else {
                        blankTablearrayList.add("tblSKUWiseDaySummary");
                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryStoreWiseDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getAllStoreSKUWiseMTDSummaryReport(Context context, final String imei, String RegistrationID, String msgToShow) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);


        String PersonNodeIdAndNodeType = dbengine.fngetSalesPersonMstrData();

        int PersonNodeId = 0;

        int PersonNodeType = 0;
        if (!PersonNodeIdAndNodeType.equals("0^0")) {
            PersonNodeId = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
            PersonNodeType = Integer.parseInt(PersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
        }

        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsInfo reportsInfo = new ReportsInfo();
        reportsInfo.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsInfo.setIMEINo(imei);
        reportsInfo.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsInfo.setForDate(fDate);
        reportsInfo.setSalesmanNodeId(PersonNodeId);
        reportsInfo.setSalesmanNodeType(PersonNodeType);
        reportsInfo.setFlgDataScope(0);

        Call<AllSummaryStoreSKUWiseDay> call = apiService.Call_AllSummaryStoreSKUWiseMTDDay(reportsInfo);
        call.enqueue(new Callback<AllSummaryStoreSKUWiseDay>() {
            @Override
            public void onResponse(Call<AllSummaryStoreSKUWiseDay> call, Response<AllSummaryStoreSKUWiseDay> response) {
                if (response.code() == 200) {
                    AllSummaryStoreSKUWiseDay allSummaryStoreSKUWiseDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.truncateStoreAndSKUWiseDataTable();
                    List<TblStoreSKUWiseDaySummary> tblStoreSKUWiseDaySummary = allSummaryStoreSKUWiseDayModel.getTblStoreSKUWiseDaySummary();
                    if (tblStoreSKUWiseDaySummary.size() > 0) {
                        dbengine.savetblStoreSKUWiseDaySummary(tblStoreSKUWiseDaySummary);
                    } else {
                        blankTablearrayList.add("tblStoreSKUWiseDaySummary");
                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllSummaryStoreSKUWiseDay> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getAllTargetVsAcheivedData(Context context, final String imei, String RegistrationID, String msgToShow, int flgSelf, int CovNodeID, int CovNodeType, int SalesManNodeID, int SalesManNodeType) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
       /* String prsnCvrgId_NdTyp=  dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String  CoverageNodeId= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String   CoverageNodeType= prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];*/
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        Data data = new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setFlgAllRouteData(1);
        // data.setInvoiceList(null);
        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setFlgSelf(flgSelf);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);
        data.setCoverageAreaNodeId(CovNodeID);
        data.setCoverageAreaNodeType(CovNodeType);

        data.setSalesmanNodeID(SalesManNodeID);
        data.setSalesmanNodeType(SalesManNodeType);

        Call<AllTargetVsAchieved> call = apiService.Call_AllTargetVsAchieved(data);
        call.enqueue(new Callback<AllTargetVsAchieved>() {
            @Override
            public void onResponse(Call<AllTargetVsAchieved> call, Response<AllTargetVsAchieved> response) {
                if (response.code() == 200) {
                    AllTargetVsAchieved allTargetVsAchievedModel = response.body();


                    dbengine.truncatetblTargetVsAchievedSummary();

                    List<TblActualVsTargetReport> tblActualVsTargetReport = allTargetVsAchievedModel.getTblActualVsTargetReport();
                    if (tblActualVsTargetReport != null) {
                        if (tblActualVsTargetReport.size() > 0) {
                            dbengine.savetblTargetVsAchievedSummary(tblActualVsTargetReport);

                        } else {
                            blankTablearrayList.add("tblActualVsTargetReport");
                        }
                    }

                    //table 29-------------------------------

                    List<TblValueVolumeTarget> tblValueVolumeTarget = allTargetVsAchievedModel.getTblValueVolumeTarget();

                    if (tblValueVolumeTarget != null && tblValueVolumeTarget.size() > 0) {
                        dbengine.saveValueVolumeTarget(tblValueVolumeTarget);

                    } else {
                        blankTablearrayList.add("tblValueVolumeTarget");
                    }

                    List<TblActualVsTargetNote> tblActualVsTargetNote = allTargetVsAchievedModel.getTblActualVsTargetNote();
                    if (tblActualVsTargetNote != null) {
                        if (tblActualVsTargetNote.size() > 0) {
                            for (TblActualVsTargetNote tblActualVsTargetNoteData : tblActualVsTargetNote) {
                                dbengine.savetblTargetVsAchievedNote(tblActualVsTargetNoteData.getMsgToDisplay());
                            }

                        } else {
                            blankTablearrayList.add("tblActualVsTargetNote");
                        }
                    }


                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllTargetVsAchieved> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getAllAddedOutletSummaryReportModel(Context context, final String imei, String RegistrationID, String msgToShow, Integer flgDrillLevel) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();

        ReportsAddedOutletSummary reportsAddedOutletSummary = new ReportsAddedOutletSummary();
        reportsAddedOutletSummary.setApplicationTypeId(CommonInfo.Application_TypeID);
        reportsAddedOutletSummary.setIMEINo(imei);
        reportsAddedOutletSummary.setVersionId(CommonInfo.DATABASE_VERSIONID);
        reportsAddedOutletSummary.setFlgDrillLevel(flgDrillLevel);
        reportsAddedOutletSummary.setForDate(fDate);

        Call<AllAddedOutletSummaryReportModel> call = apiService.Call_AllPDAGetAddedOutletSummaryReport(reportsAddedOutletSummary);
        call.enqueue(new Callback<AllAddedOutletSummaryReportModel>() {
            @Override
            public void onResponse(Call<AllAddedOutletSummaryReportModel> call, Response<AllAddedOutletSummaryReportModel> response) {
                if (response.code() == 200) {
                    AllAddedOutletSummaryReportModel allAddedOutletSummaryReportModelModel = response.body();


                    dbengine.droptblDAGetAddedOutletSummaryReport();
                    dbengine.createtblDAGetAddedOutletSummaryReport();

                    List<TblDAGetAddedOutletSummaryReport> tblDAGetAddedOutletSummaryReport = allAddedOutletSummaryReportModelModel.getTblDAGetAddedOutletSummaryReport();

                    if (tblDAGetAddedOutletSummaryReport != null && tblDAGetAddedOutletSummaryReport.size() > 0) {
                        dbengine.savetblDAGetAddedOutletSummaryReport(tblDAGetAddedOutletSummaryReport);

                    } else {
                        blankTablearrayList.add("tblDAGetAddedOutletSummaryReport");
                    }

                    //table 29-------------------------------

                    List<TblDAGetAddedOutletSummaryOverallReport> tblDAGetAddedOutletSummaryOverallReport = allAddedOutletSummaryReportModelModel.getTblDAGetAddedOutletSummaryOverallReport();

                    if (tblDAGetAddedOutletSummaryOverallReport != null && tblDAGetAddedOutletSummaryOverallReport.size() > 0) {
                        dbengine.savetblDAGetAddedOutletSummaryOverallReport(tblDAGetAddedOutletSummaryOverallReport);

                    } else {
                        blankTablearrayList.add("tblDAGetAddedOutletSummaryOverallReport");
                    }
                    dbengine.fetchtblDAGetAddedOutletSummaryReport();
                    dbengine.fetchtblDAGetAddedOutletOverAllData();

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllAddedOutletSummaryReportModel> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getStockData(Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        //final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final MultipleInterfaceRetrofit multplinterfaceRetrofit = (MultipleInterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        Data data = new Data();
        data.setApplicationTypeId(CommonInfo.Application_TypeID);
        data.setIMEINo(imei);
        data.setVersionId(CommonInfo.DATABASE_VERSIONID);
        data.setRegistrationId(RegistrationID);
        data.setForDate(fDate);
        data.setFlgAllRouteData(1);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);

        data.setInvoiceList(arrDistinctInvoiceNumbersNew);

        data.setRouteNodeId(0);
        data.setRouteNodeType(0);
        data.setCoverageAreaNodeId(Integer.parseInt(CoverageNodeId));
        data.setCoverageAreaNodeType(Integer.parseInt(CoverageNodeType));

        Call<StockData> call = apiService.Call_StockData(data);
        call.enqueue(new Callback<StockData>() {
            @Override
            public void onResponse(Call<StockData> call, Response<StockData> response) {
                if (response.code() == 200) {
                    StockData stockData = response.body();
                    System.out.println("DATAENSERTEDSP");


                    //table 28-------------------------------
                    if (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") != 0) {
                        dbengine.Delete_tblStockUploadedStatus();
                        dbengine.deleteVanConfirmFlag();
                        List<TblStockUploadedStatus> tblStockUploadedStatus = stockData.getTblStockUploadedStatus();

                        if (tblStockUploadedStatus.size() > 0) {
                            for (TblStockUploadedStatus StockUploadedStatus : tblStockUploadedStatus) {
                                dbengine.inserttblStockUploadedStatus(StockUploadedStatus.getFlgStockTrans(), StockUploadedStatus.getVanLoadUnLoadCycID(), StockUploadedStatus.getCycleTime(), StockUploadedStatus.getStatusID(), StockUploadedStatus.getFlgDayEnd());

                            }

                        } else {
                            blankTablearrayList.add("tblStockUploadedStatus");
                        }

                        //table 29-------------------------------

                        dbengine.deleteCompleteDataDistStock();

                        List<TblCycleID> tblCycleID = stockData.getTblCycleID();
                        if (CommonInfo.flgDrctslsIndrctSls == 1) {
                            if (tblCycleID.size() > 0) {
                                dbengine.insertCycleId(tblCycleID);
//                                for (TblCycleID CycleID : tblCycleID) {
//                                    dbengine.insertCycleId(CycleID.getCycleID(), CycleID.getCycStartTime(), CycleID.getCycleTime());
//                                }
                            } else {
                                blankTablearrayList.add("tblCycleID");
                            }
                        }

                        //table 30-------------------------------
                        //deleted above
                        List<TblVanStockOutFlg> tblVanStockOutFlg = stockData.getTblVanStockOutFlg();

                        if (tblVanStockOutFlg.size() > 0) {
                            for (TblVanStockOutFlg VanStockOutFlg : tblVanStockOutFlg) {
                                dbengine.insertStockOut(VanStockOutFlg.getFlgStockOutEntryDone());
                            }
                        } else {
                            blankTablearrayList.add("tblVanStockOutFlg");
                        }
                        //table 31-------------------------------

                        List<TblVanProductStock> tblVanProductStock = stockData.getTblVanProductStock();

                        if (tblVanProductStock.size() > 0) {


                            //  if(CommonInfo.flgDrctslsIndrctSls==1) {
                            dbengine.insertDistributorStock(tblVanProductStock);
                            // if(CommonInfo.hmapAppMasterFlags.get("flgDBRStockInApp")==1 && CommonInfo.hmapAppMasterFlags.get("flgDBRStockCalculate")==1 ) {
                            int statusId = dbengine.confirmedStock();
                            if (statusId == 2) {
                                dbengine.insertConfirmWArehouse(tblVanProductStock.get(0).getCustomer(), "1");
                                dbengine.inserttblDayCheckIn(1);
                            }
                            // }
                            // }


                        } else {
                            blankTablearrayList.add("tblVanProductStock");
                        }

                        //deleted above
                        List<TblVanIDOrderIDLeft> tblVanIDOrderIDLeft = stockData.getTblVanIDOrderIDLeft();

                        if (tblVanIDOrderIDLeft.size() > 0) {
                            for (TblVanIDOrderIDLeft VanIDOrderIDLeft : tblVanIDOrderIDLeft) {
                                dbengine.insertDistributorLeftOrderId(VanIDOrderIDLeft.getCustomer(), VanIDOrderIDLeft.getPDAOrderId(), VanIDOrderIDLeft.getFlgInvExists());
                            }
                        } else {
                            blankTablearrayList.add("tblVanIDOrderIDLeft");
                        }

                    }
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    //  interfaceRetrofit.success();
                    multplinterfaceRetrofit.success(flgCldFrm);
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    //  interfaceRetrofit.failure();
                    multplinterfaceRetrofit.success(flgCldFrm);
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<StockData> call, Throwable t) {

                // dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                // interfaceRetrofit.failure();
                multplinterfaceRetrofit.success(flgCldFrm);
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getDistributorTodayStock(Context context, final String imei, final int customerNodeId, final int customerNodeType) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        DistributorTodaysStock data = new DistributorTodaysStock();

        data.setiMEINo(imei);
        data.setCustomerNodeId(customerNodeId);
        data.setCustomerNodeType(customerNodeType);
        data.setAppVersionNo(BuildConfig.VERSION_NAME);


        Call<DistributorStockData> call = apiService.Call_DistributorTodayStockData(data);
        call.enqueue(new Callback<DistributorStockData>() {
            @Override
            public void onResponse(Call<DistributorStockData> call, Response<DistributorStockData> response) {
                if (response.code() == 200) {
                    DistributorStockData stockData = response.body();
                    System.out.println("DATAENSERTEDSP");


                    List<TblDistributorDayReport> tblDistributorDayReport = stockData.getTblDistributorDayReport();

                    if (tblDistributorDayReport.size() > 0) {
                        for (TblDistributorDayReport tblDistributorDayReportData : tblDistributorDayReport) {
//   savetblDistributorDayReport(ProductNodeID, ProductNodeType, SKUName, FlvShortName,StockDate,CustomerNodeID,CustomerNodeType);
                            dbengine.savetblDistributorDayReport(tblDistributorDayReportData.getProductNodeID(), tblDistributorDayReportData.getProductNodeType(), tblDistributorDayReportData.getSKUName(), tblDistributorDayReportData.getFlvShortName(), tblDistributorDayReportData.getStockDate(), customerNodeId, customerNodeType);
                        }

                    } else {
                        blankTablearrayList.add("tblDistributorDayReport");
                    }


                    List<TblDistributorDayReportColumnsDesc> tblDistributorDayReportColumnsDesc = stockData.getTblDistributorDayReportColumnsDesc();

                    if (tblDistributorDayReportColumnsDesc.size() > 0) {
                        for (TblDistributorDayReportColumnsDesc tblDistributorDayReportColumnsDescData : tblDistributorDayReportColumnsDesc) {
                            //savetblDistributorDayReportColumnsDesc(DistDayReportCoumnName, DistDayReportColumnDisplayName,CustomerNodeID,CustomerNodeType);
                            dbengine.savetblDistributorDayReportColumnsDesc(tblDistributorDayReportColumnsDescData.getDistDayReportCoumnName(), tblDistributorDayReportColumnsDescData.getDistDayReportColumnDisplayName(), customerNodeId, customerNodeType);
                        }
                    } else {
                        blankTablearrayList.add("tblDistributorDayReportColumnsDesc");
                    }


                    //table 30-------------------------------
                    //deleted above
                    List<TblDistributorDayReportDisplayMessage> tblDistributorDayReportDisplayMessage = stockData.getTblDistributorDayReportDisplayMessage();

                    if (tblDistributorDayReportDisplayMessage.size() > 0) {
                        /*for(TblDistributorDayReportDisplayMessage tblDistributorDayReportDisplayMessageData:tblDistributorDayReportDisplayMessage){
                            dbengine.insertStockOut(DistributorStockOutFlg.getFlgStockOutEntryDone());
                        }*/
                    } else {
                        blankTablearrayList.add("tblDistributorDayReportDisplayMessage");
                    }

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<DistributorStockData> call, Throwable t) {

                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }


    public static void getGetServerTime(Context context, final String imei) {

        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        final InterfaceRetrofit interfaceRetrofit = (InterfaceRetrofit) context;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<TblCurrentServerDateTimeData> call = apiService.Call_TblCurrentServerDateTimeData();
        call.enqueue(new Callback<TblCurrentServerDateTimeData>() {
            @Override
            public void onResponse(Call<TblCurrentServerDateTimeData> call, Response<TblCurrentServerDateTimeData> response) {
                if (response.code() == 200) {
                    TblCurrentServerDateTimeData CurrentServerDateTimeData = response.body();
                    System.out.println("DATAENSERTEDSP");
                    List<TblServerTime> tblServerTime = CurrentServerDateTimeData.getTblServerTime();

                    if (tblServerTime.size() > 0) {
                        for (TblServerTime tblServerTimeData : tblServerTime) {
//   savetblDistributorDayReport(ProductNodeID, ProductNodeType, SKUName, FlvShortName,StockDate,CustomerNodeID,CustomerNodeType);
                            CommonInfo.crntServerTimecrntAttndncTime = tblServerTimeData.getServerTime();
                        }
                    }

                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.success();
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failure();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<TblCurrentServerDateTimeData> call, Throwable t) {

                //    dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failure();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void getStockUploadedStatus(final Context context, final String imei, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final MultipleInterfaceForDayEndStatus multipleInterfaceForDayEndStatus = (MultipleInterfaceForDayEndStatus) context;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setIMEINo(imei);
        statusInfo.setAppVersionNo(BuildConfig.VERSION_NAME);

        Call<StockUploadedStatus> call = apiService.Call_GetStockUploadStatus(statusInfo);
        call.enqueue(new Callback<StockUploadedStatus>() {
            @Override
            public void onResponse(Call<StockUploadedStatus> call, Response<StockUploadedStatus> response) {
                if (response.code() == 200) {
                    StockUploadedStatus stockUploadedStatus = response.body();
                    System.out.println("DATAENSERTEDSP");


                    //table 28-------------------------------
                    if (CommonInfo.hmapAppMasterFlags.get("flgVanStockInApp") != 0) {
                        dbengine.Delete_tblStockUploadedStatus();

                        List<TblStockUploadedStatus> tblStockUploadedStatus = stockUploadedStatus.getTblStockUploadedStatus();
                        int flgDayEnd = 0;
                        if (tblStockUploadedStatus.size() > 0) {
                            for (TblStockUploadedStatus StockUploadedStatus : tblStockUploadedStatus) {
                                flgDayEnd = StockUploadedStatus.getFlgDayEnd();
                                dbengine.inserttblStockUploadedStatus(StockUploadedStatus.getFlgStockTrans(), StockUploadedStatus.getVanLoadUnLoadCycID(), StockUploadedStatus.getCycleTime(), StockUploadedStatus.getStatusID(), StockUploadedStatus.getFlgDayEnd());

                            }

                        } else {
                            // blankTablearrayList.add("tblStockUploadedStatus");
                        }

                        SharedPreferences sharedPref = context.getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
                        SharedPreferences.Editor editorFinalSubmit = sharedPref.edit();
                        editorFinalSubmit.putInt("FinalSubmit", flgDayEnd);
                        editorFinalSubmit.commit();
                        multipleInterfaceForDayEndStatus.DayEndServerStatussuccess(flgCldFrm);
                    }


                } else {

                }
            }

            @Override
            public void onFailure(Call<StockUploadedStatus> call, Throwable t) {
                multipleInterfaceForDayEndStatus.DayEndServerStatusfailure(flgCldFrm);

            }
        });


    }


    public static void fnSaveRequestforOrdersOnMail(final Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm, String emailID, String OrderDate) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.RetrivingDataMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        final InterfaceRetrofitOrderOnMain interfaceRetrofit = (InterfaceRetrofitOrderOnMain) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        String prsnCvrgId_NdTyp = dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
        String CoverageNodeId = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0];
        String CoverageNodeType = prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1];
        CommonInfo.flgDrctslsIndrctSls = dbengine.fnGetflgDrctslsIndrctSlsForDSR(Integer.parseInt(CoverageNodeId), Integer.parseInt(CoverageNodeType));
        int FlgAllRoutesData = 1;
        String serverDateForSPref = dbengine.fnGetServerDate();
        ArrayList<InvoiceList> arrDistinctInvoiceNumbersNew = dbengine.getDistinctInvoiceNumbersNew();
        TblRequestForOrderOnMainData data = new TblRequestForOrderOnMainData();
        data.setPDACode(imei);
        data.setEMailID(emailID);
        data.setOrderDate(OrderDate);


        Call<TblAllOrderOnMailConfirmData> call = apiService.Call_fnSaveRequestforOrdersOnMail(data);
        call.enqueue(new Callback<TblAllOrderOnMailConfirmData>() {
            @Override
            public void onResponse(Call<TblAllOrderOnMailConfirmData> call, Response<TblAllOrderOnMailConfirmData> response) {
                if (response.code() == 200) {
                    TblAllOrderOnMailConfirmData allMasterTablesModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    if (allMasterTablesModel.getTblOrderConfirmFlagValidation() != null) {
                        List<TblOrderConfirmFlagValidation> tblDayStartAttendanceOption = allMasterTablesModel.getTblOrderConfirmFlagValidation();
                      /*  if (tblDayStartAttendanceOption.size() > 0) {

                            int AutoIdStore = 0;
                            for (TblDayStartAttendanceOption dayStartAttendanceOption : tblDayStartAttendanceOption) {
                                AutoIdStore = AutoIdStore++;
                                dbengine.savetblDayStartAttendanceOptions(AutoIdStore, "" + dayStartAttendanceOption.getReasonId(), dayStartAttendanceOption.getReasonDescr(), dayStartAttendanceOption.getFlgToShowTextBox(), dayStartAttendanceOption.getFlgSOApplicable(), dayStartAttendanceOption.getFlgDSRApplicable(), dayStartAttendanceOption.getFlgNoVisitOption(), dayStartAttendanceOption.getSeqNo(), dayStartAttendanceOption.getFlgDelayedReason(), dayStartAttendanceOption.getFlgMarketVisit());
                            }
                        } else {
                            blankTablearrayList.add("tblDayStartAttendanceOptions");
                        }*/
                    }


                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.successOrderOnMain(flgCldFrm, context);
                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failureOrderOnMain(flgCldFrm, context);
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<TblAllOrderOnMailConfirmData> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failureOrderOnMain(flgCldFrm, context);
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static void fnInsertDayStartAttendanceAPI(Context context, final String imei, String RegistrationID, String msgToShow, final int flgCldFrm) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
      /*  final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.MarkingAttendanceMsg))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();*/

        final InterfaceRetrofitAttendanceMarking interfaceRetrofit = (InterfaceRetrofitAttendanceMarking) context;
        final ArrayList blankTablearrayList = new ArrayList();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        TblAttandanceDetailsDataModel data = new TblAttandanceDetailsDataModel();
        ArrayList<TblAttandanceDetails> tblAttandanceDetails = dbengine.fnFilltblAttandanceDetailsRecord();
        ArrayList<TblLocationDetails> tblLocationDetails = dbengine.fnGetTblLocationDetailsForAttendance();

        data.setIMEINO(AppUtils.getToken(context));
        data.setTblAttandanceDetails(tblAttandanceDetails);
        data.setTblLocationDetails(tblLocationDetails);

        Gson gson = new GsonBuilder().create();
        JsonObject myCustomArray = gson.toJsonTree(data).getAsJsonObject();
        Log.d("AttendanceData", myCustomArray.toString());
        Call<AllAttendanceAPIInsertTablesModel> call = apiService.Call_MarkAttendanceAPI(data);
        call.enqueue(new Callback<AllAttendanceAPIInsertTablesModel>() {
            @Override
            public void onResponse(Call<AllAttendanceAPIInsertTablesModel> call, Response<AllAttendanceAPIInsertTablesModel> response) {
                if (response.code() == 200) {
                    AllAttendanceAPIInsertTablesModel allMasterTablesModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1
                    dbengine.deletetblDayStartAttendanceOptions();
                    if (allMasterTablesModel.getTblAttendanceReposes() != null) {

                        List<TblAttendanceRepose> tblAttendanceReposes = allMasterTablesModel.getTblAttendanceReposes();
                        if (tblAttendanceReposes != null && tblAttendanceReposes.size() > 0) {

                            if (tblAttendanceReposes.get(0).getFlgStatus() == 1) {
                                dbengine.fnSettblAttandanceDetails();
                             /*   if (progressHUD != null && progressHUD.isShowing())
                                    progressHUD.dismiss();*/
                                interfaceRetrofit.successAttendanceMarking(tblAttendanceReposes.get(0).getFlgStatus());

                            } else {
                               /* if (progressHUD != null && progressHUD.isShowing())
                                    progressHUD.dismiss();*/
                                interfaceRetrofit.successAttendanceMarking(0);
                            }

                        } else {
                            blankTablearrayList.add("tblAttendanceReposes");
                           /* if (progressHUD != null && progressHUD.isShowing())
                                progressHUD.dismiss();*/
                            interfaceRetrofit.successAttendanceMarking(0);
                        }
                    } else {
                       /* if (progressHUD != null && progressHUD.isShowing())
                            progressHUD.dismiss();*/
                        interfaceRetrofit.successAttendanceMarking(0);
                    }


                    // sendIntentToOtherActivityAfterAllDataFetched();

                } else {
                 /*   if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();*/
                    interfaceRetrofit.failureAttendanceMarking();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<AllAttendanceAPIInsertTablesModel> call, Throwable t) {

              /*  if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();*/
                interfaceRetrofit.failureAttendanceMarking();

            }
        });


    }



    public static void getNewStoreNumberValidation(Context context, final String imei, String RegistrationID, String msgToShow,String ContactNumber,String GSTNumber,String StoreID) {
        final AppDataSource dbengine = AppDataSource.getInstance(context);
        final KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.ValidatingMsgStore))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        final InterfaceRetrofitNewStoreNumberValidation interfaceRetrofit = (InterfaceRetrofitNewStoreNumberValidation) context;
        final ArrayList blankTablearrayList = new ArrayList();
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        final String fDate = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_FORMAT);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);




        TblStoreNumberValidationWhileAddStore reportsInfo = new TblStoreNumberValidationWhileAddStore();
        reportsInfo.setPDACode(AppUtils.getToken(context));
        reportsInfo.setContactNo(ContactNumber);
        reportsInfo.setGSTNumber(GSTNumber);
        reportsInfo.setStoreID(StoreID);


        Call<TblAllValidationNewStoreMobileNumber> call = apiService.StoreContactDuplicate(reportsInfo);
        call.enqueue(new Callback<TblAllValidationNewStoreMobileNumber>() {
            @Override
            public void onResponse(Call<TblAllValidationNewStoreMobileNumber> call, Response<TblAllValidationNewStoreMobileNumber> response) {
                if (response.code() == 200) {
                    TblAllValidationNewStoreMobileNumber allSummaryReportDayModel = response.body();
                    System.out.println("DATAENSERTEDSP");
                    //table 1

                    List<TblDuplicateContact> tblAllSummaryDay = allSummaryReportDayModel.getTblDuplicateContacts();



                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.successNewStoreNumberValidation(tblAllSummaryDay.get(0),""+ContactNumber);
                } else {
                    if (progressHUD != null && progressHUD.isShowing())
                        progressHUD.dismiss();
                    interfaceRetrofit.failureNewStoreNumberValidation();
                    // showAlertForError("Error while retreiving data from server");
                }
            }

            @Override
            public void onFailure(Call<TblAllValidationNewStoreMobileNumber> call, Throwable t) {
                System.out.println();
                if (progressHUD != null && progressHUD.isShowing())
                    progressHUD.dismiss();
                interfaceRetrofit.failureNewStoreNumberValidation();
                //   showAlertForError("Error while retreiving data from server");
            }
        });


    }

    public static class DateWiseTargetVsAchReportDownload extends AsyncTask {

        int flgCldFrm;

        @SuppressLint("StaticFieldLeak")
        private Activity context;
        private ProgressDialog mProgressDialog;
        private MultipleInterfaceRetrofit interfaceRetrofit;
        private int PersonNodeID=0;
        private int PersonNodeType=0;

        public DateWiseTargetVsAchReportDownload(Activity context, ProgressDialog mProgressDialog, MultipleInterfaceRetrofit interfaceRetrofit,int PersonNodeID,int PersonNodeType,int flgCldFrm) {
            this.context = context;
            this.mProgressDialog = mProgressDialog;
            this.interfaceRetrofit = interfaceRetrofit;
            this.flgCldFrm = flgCldFrm;
            this.PersonNodeID=PersonNodeID;
            this.PersonNodeType=PersonNodeType;

        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                AppDataSource dbengine = AppDataSource.getInstance(context);

                TblTargetVsAchSalesPersonReportParameterData reportsInfo = new TblTargetVsAchSalesPersonReportParameterData();
                reportsInfo.setPDACode(AppUtils.getToken(context));
                reportsInfo.setForDate( TimeUtils.getNetworkDate(context,TimeUtils.DATE_FORMAT));
                reportsInfo.setPersonNodeID(PersonNodeID);
                reportsInfo.setPersonNodeType(PersonNodeType);
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TblAllSaleManReportReturnedTablesTargetVsAch> call = apiService.SalesManReportTargetVsAch(reportsInfo);
                call.enqueue(new Callback<TblAllSaleManReportReturnedTablesTargetVsAch>() {
                    @Override
                    public void onResponse(Call<TblAllSaleManReportReturnedTablesTargetVsAch> call, Response<TblAllSaleManReportReturnedTablesTargetVsAch> response) {
                        if (response.code() == 200) {
                            TblAllSaleManReportReturnedTablesTargetVsAch allSummaryReportDayModel = response.body();

                            dbengine.delete_tblDayWiseActualVsTargetReportSalesMan();
                            List<TblDayWiseActualVsTargetReportSalesMan> tblDayWiseActualVsTargetReportSalesManList=  allSummaryReportDayModel.getTblDateWiseSalesManTargetVsAch();
                            if(tblDayWiseActualVsTargetReportSalesManList!=null && tblDayWiseActualVsTargetReportSalesManList.size()>0){

                                dbengine.insert_tblDayWiseActualVsTargetReportSalesMan(tblDayWiseActualVsTargetReportSalesManList);
                            }

                            dbengine.delete_tblDailyDateWiseSalesManTargetVsAch();
                            List<TblDailyDateWiseSalesManTargetVsAch> tblDailyDateWiseSalesManTargetVsAchList=  allSummaryReportDayModel.getTblDailyDateWiseSalesManTargetVsAchList();
                            if(tblDailyDateWiseSalesManTargetVsAchList!=null && tblDailyDateWiseSalesManTargetVsAchList.size()>0){

                                dbengine.insert_tblDailyDateWiseSalesManTargetVsAch(tblDailyDateWiseSalesManTargetVsAchList);
                            }
                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.success(flgCldFrm);
                                }
                            });
                        } else {

                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.failure(flgCldFrm);
                                }
                            });
                            // showAlertForError("Error while retreiving data from server");
                        }



                    }

                    @Override
                    public void onFailure(Call<TblAllSaleManReportReturnedTablesTargetVsAch> call, Throwable t) {
                        System.out.println();
                        context.runOnUiThread(() -> {
                            mProgressDialog.dismiss();
                            interfaceRetrofit.failure(flgCldFrm);
                        });

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
                context.runOnUiThread(() -> {
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure(flgCldFrm);
                });
            }
            return null;
        }
    }




    public static class GetScoutingDistributorListAndDetails extends AsyncTask {


        PreferenceManager mPreferenceManager;
        @SuppressLint("StaticFieldLeak")
        private Activity context;
        private ProgressDialog mProgressDialog;
        private MultipleInterfaceRetrofit interfaceRetrofit;
        private  int flgCldFrm;

        public GetScoutingDistributorListAndDetails(Activity context, ProgressDialog mProgressDialog, MultipleInterfaceRetrofit interfaceRetrofit,int flgCldFrm) {
            this.context = context;
            this.mProgressDialog = mProgressDialog;
            this.interfaceRetrofit = interfaceRetrofit;
            this.flgCldFrm = flgCldFrm;

            mPreferenceManager= PreferenceManager.getInstance(context);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                AppDataSource dbengine = AppDataSource.getInstance(context);

                TblDistributorScoutingParameterData reportsInfo = new TblDistributorScoutingParameterData();
                reportsInfo.setPDACode(AppUtils.getToken(context));


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TblAllScoutingDistributorReturnedTables> call = apiService.GetPDAGetPotentialDealerList(reportsInfo);
                call.enqueue(new Callback<TblAllScoutingDistributorReturnedTables>() {
                    @Override
                    public void onResponse(Call<TblAllScoutingDistributorReturnedTables> call, Response<TblAllScoutingDistributorReturnedTables> response) {
                        if (response.code() == 200) {
                            TblAllScoutingDistributorReturnedTables allSummaryReportDayModel = response.body();
                            System.out.println("DATAENSERTEDSP");
                            dbengine.delete_tblPotentialDistributorList();
                            List<TblPotentialDistributor> tblPotentialDistributorList=  allSummaryReportDayModel.getTblPotentialDistributorList();
                            if(tblPotentialDistributorList!=null && tblPotentialDistributorList.size()>0){

                                dbengine.insert_tblPotentialDistributorList(tblPotentialDistributorList);
                            }


                            dbengine.delete_tblCompetitorCompany();
                            List<TblPotentialDistributorCompetitorCompanyMstr> tblCompetitorCompanyList=  allSummaryReportDayModel.getTblCompetitorCompanyDetailsList();

                            if(tblCompetitorCompanyList!=null && tblCompetitorCompanyList.size()>0){

                                dbengine.insert_tblCompetitorCompany(tblCompetitorCompanyList);
                            }

                            dbengine.delete_tblCompetitorBrand();
                            List<TblPotentialDistributorCompetitorBrandMstr> tblCompetitorBrandList=  allSummaryReportDayModel.getTblCompetitorBrandDetailsList();
                            if(tblCompetitorBrandList!=null && tblCompetitorBrandList.size()>0){

                                dbengine.insert_tblCompetitorBrand(tblCompetitorBrandList);
                            }


                            dbengine.delete_tblCompetitorCompanyMstr();

                         /*   TblPotentialDistributorCompetitorCompanyMstr tblPotentialDistributorCompetitorCompanyMstrOther=new TblPotentialDistributorCompetitorCompanyMstr();
                            tblPotentialDistributorCompetitorCompanyMstrOther.setCompetitorCompanyID(99);
                            tblPotentialDistributorCompetitorCompanyMstrOther.setCompetitorCompany("Other");*/
                            List<TblPotentialDistributorCompetitorCompanyMstr> tblCompetitorCompanyMstrList=  allSummaryReportDayModel.getTblCompetitorCompanyMstr();
                            // tblCompetitorCompanyMstrList.add(tblPotentialDistributorCompetitorCompanyMstrOther);
                            if(tblCompetitorCompanyMstrList!=null && tblCompetitorCompanyMstrList.size()>0){

                                dbengine.insert_tblCompetitorCompanyMstr(tblCompetitorCompanyMstrList);
                            }


                            dbengine.delete_tblCompetitorBrandMstr();

                         /*   TblPotentialDistributorCompetitorBrandMstr tblPotentialDistributorCompetitorBrandMstrOther=new TblPotentialDistributorCompetitorBrandMstr();
                            tblPotentialDistributorCompetitorBrandMstrOther.setCompetitorBrandID(99);
                            tblPotentialDistributorCompetitorBrandMstrOther.setCompetitorBrand("Other");*/

                            List<TblPotentialDistributorCompetitorBrandMstr> tblCompetitorBrandMstrList=  allSummaryReportDayModel.getTblCompetitorBrandMstrList();
                            //  tblCompetitorBrandMstrList.add(tblPotentialDistributorCompetitorBrandMstrOther);
                            if(tblCompetitorBrandMstrList!=null && tblCompetitorBrandMstrList.size()>0){

                                dbengine.insert_tblCompetitorBrandMstr(tblCompetitorBrandMstrList);
                            }


                            dbengine.delete_tblVehicalType();
                            List<TblPotentialDistributorVehicleMasterList> tblVehicalTypeMstrList=  allSummaryReportDayModel.getTblVehicalTypeMstrList();
                            if(tblVehicalTypeMstrList!=null && tblVehicalTypeMstrList.size()>0){

                                dbengine.insert_tblVehicalType(tblVehicalTypeMstrList);
                            }


                            dbengine.delete_tblPotentialDistributorRetailersFeedBackDetails();
                            List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsList=  allSummaryReportDayModel.getTblPotentialDistributorRetailersFeedBackDetails();
                            if(tblPotentialDistributorRetailersFeedBackDetailsList!=null && tblPotentialDistributorRetailersFeedBackDetailsList.size()>0){

                                dbengine.insert_tblPotentialDistributorRetailersFeedBackDetails(tblPotentialDistributorRetailersFeedBackDetailsList);
                            }

                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.success(flgCldFrm);
                                }
                            });
                        } else {

                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.failure(flgCldFrm);
                                }
                            });

                        }



                    }

                    @Override
                    public void onFailure(Call<TblAllScoutingDistributorReturnedTables> call, Throwable t) {
                        System.out.println();
                        context.runOnUiThread(() -> {
                            mProgressDialog.dismiss();
                            interfaceRetrofit.failure(flgCldFrm);
                        });

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
                context.runOnUiThread(() -> {
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failure(flgCldFrm);
                });
            }
            return null;
        }
    }



    public static class SavePotentialDistributorData extends AsyncTask {


        PreferenceManager mPreferenceManager;
        @SuppressLint("StaticFieldLeak")
        private Activity context;
        private ProgressDialog mProgressDialog;
        private InterfaceServerSuccessEntry interfaceRetrofit;
        TblPotentialDistributor tblPotentialDistributor;
        private  int flgCldFrm;

        public SavePotentialDistributorData(Activity context, ProgressDialog mProgressDialog, InterfaceServerSuccessEntry interfaceRetrofit, int flgCldFrm, TblPotentialDistributor tblPotentialDistributor) {
            this.context = context;
            this.mProgressDialog = mProgressDialog;
            this.interfaceRetrofit = interfaceRetrofit;
            this.flgCldFrm = flgCldFrm;
            this.tblPotentialDistributor=tblPotentialDistributor;
            mPreferenceManager= PreferenceManager.getInstance(context);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
             /*  TblDistributorScoutingParameterForSavingDataToServer reportsInfo = new TblDistributorScoutingParameterForSavingDataToServer();
                reportsInfo.setTblPotentialDistributor(tblPotentialDistributor);*/



                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> call = apiService.SavePotentialDistributorData(tblPotentialDistributor);
                call.enqueue(new Callback<TblAllScoutingDistributorReturnedTableAfterServerSaving>() {
                    @Override
                    public void onResponse(Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> call, Response<TblAllScoutingDistributorReturnedTableAfterServerSaving> response) {
                        if (response.code() == 200) {
                            TblAllScoutingDistributorReturnedTableAfterServerSaving allSummaryReportDayModel = response.body();
                            System.out.println("DATAENSERTEDSP");

                            List<TblServerEntryStatus> tblServerEntryStatusList=  allSummaryReportDayModel.getTblServerEntryStatus();
                            if(tblServerEntryStatusList!=null && tblServerEntryStatusList.size()>0){


                            }



                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    if(mProgressDialog!=null && mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    interfaceRetrofit.successRecord(flgCldFrm,tblServerEntryStatusList);
                                }
                            });
                        } else {

                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    if(mProgressDialog!=null && mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();
                                    interfaceRetrofit.failureRecord(flgCldFrm);
                                }
                            });

                        }



                    }

                    @Override
                    public void onFailure(Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> call, Throwable t) {
                        System.out.println();
                        context.runOnUiThread(() -> {
                            if(mProgressDialog!=null && mProgressDialog.isShowing())
                                mProgressDialog.dismiss();
                            interfaceRetrofit.failureRecord(flgCldFrm);
                        });

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
                context.runOnUiThread(() -> {
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failureRecord(flgCldFrm);
                });
            }
            return null;
        }
    }


    public static class SavePotentialDistributorRetailerFeedBackData extends AsyncTask {


        PreferenceManager mPreferenceManager;
        @SuppressLint("StaticFieldLeak")
        private Activity context;
        private ProgressDialog mProgressDialog;
        private InterfaceServerSuccessRetailerFeedBackEntry interfaceRetrofit;
        List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving;
        private  int flgCldFrm;

        public SavePotentialDistributorRetailerFeedBackData(Activity context, ProgressDialog mProgressDialog, InterfaceServerSuccessRetailerFeedBackEntry interfaceRetrofit, int flgCldFrm,  List<TblPotentialDistributorRetailersFeedBackDetails> tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving) {
            this.context = context;
            this.mProgressDialog = mProgressDialog;
            this.interfaceRetrofit = interfaceRetrofit;
            this.flgCldFrm = flgCldFrm;
            this.tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving=tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving;
            mPreferenceManager= PreferenceManager.getInstance(context);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                TblDistributorScoutingRetailerFeedBackParameterForSavingDataToServer reportsInfo = new TblDistributorScoutingRetailerFeedBackParameterForSavingDataToServer();
                reportsInfo.setTblPotentialDistributorRetailersFeedBackDetailsListForServerSaving(tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving);


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> call = apiService.SavePotentialDistributorRetailerFeedBackData(reportsInfo);
                call.enqueue(new Callback<TblAllScoutingDistributorReturnedTableAfterServerSaving>() {
                    @Override
                    public void onResponse(Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> call, Response<TblAllScoutingDistributorReturnedTableAfterServerSaving> response) {
                        if (response.code() == 200) {
                            TblAllScoutingDistributorReturnedTableAfterServerSaving allSummaryReportDayModel = response.body();
                            System.out.println("DATAENSERTEDSP");

                            List<TblServerEntryStatus> tblServerEntryStatusList=  allSummaryReportDayModel.getTblServerEntryStatus();
                            if(tblServerEntryStatusList!=null && tblServerEntryStatusList.size()>0){


                            }



                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.successRetailerAddressFeebackRecord(flgCldFrm,tblServerEntryStatusList);
                                }
                            });
                        } else {

                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.failureRetailerAddressFeebackRecord(flgCldFrm);
                                }
                            });

                        }



                    }

                    @Override
                    public void onFailure(Call<TblAllScoutingDistributorReturnedTableAfterServerSaving> call, Throwable t) {
                        System.out.println();
                        context.runOnUiThread(() -> {
                            mProgressDialog.dismiss();
                            interfaceRetrofit.failureRetailerAddressFeebackRecord(flgCldFrm);
                        });

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
                context.runOnUiThread(() -> {
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failureRetailerAddressFeebackRecord(flgCldFrm);
                });
            }
            return null;
        }
    }


    public static class DeletePotentialDistributor extends AsyncTask {


        PreferenceManager mPreferenceManager;
        @SuppressLint("StaticFieldLeak")
        private Activity context;
        private ProgressDialog mProgressDialog;
        private InterfaceServerSuccessDistributorDeletion interfaceRetrofit;
        TblPotentialDistributor tblPotentialDistributorList;
        private  int flgCldFrm;

        public DeletePotentialDistributor(Activity context, ProgressDialog mProgressDialog, InterfaceServerSuccessDistributorDeletion interfaceRetrofit, int flgCldFrm,  TblPotentialDistributor tblPotentialDistributorList) {
            this.context = context;
            this.mProgressDialog = mProgressDialog;
            this.interfaceRetrofit = interfaceRetrofit;
            this.flgCldFrm = flgCldFrm;
            this.tblPotentialDistributorList=tblPotentialDistributorList;
            mPreferenceManager= PreferenceManager.getInstance(context);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                TblPotentialDistributorForDelete reportsInfo = new TblPotentialDistributorForDelete();
                reportsInfo.setPDACode(tblPotentialDistributorList.getPDACode());
                reportsInfo.setNodeID(tblPotentialDistributorList.getNodeID());
                reportsInfo.setNodeType(tblPotentialDistributorList.getNodeType());
                // reportsInfo.setTblPotentialDistributorRetailersFeedBackDetailsListForServerSaving(tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving);


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<Object> call = apiService.DeletePotentialDistributorData(reportsInfo);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 200) {

                          /*  List<TblServerEntryStatus> tblServerEntryStatusList=  allSummaryReportDayModel.getTblServerEntryStatus();
                            if(tblServerEntryStatusList!=null && tblServerEntryStatusList.size()>0){


                            }*/



                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.successRecordDelete(tblPotentialDistributorList);
                                }
                            });
                        } else {

                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.failureRecordDelete();
                                }
                            });

                        }



                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println();
                        context.runOnUiThread(() -> {
                            mProgressDialog.dismiss();
                            interfaceRetrofit.failureRecordDelete();
                        });

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
                context.runOnUiThread(() -> {
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failureRecordDelete();
                });
            }
            return null;
        }
    }

    public static class DeleteExistingDistributor extends AsyncTask {


        PreferenceManager mPreferenceManager;
        @SuppressLint("StaticFieldLeak")
        private Activity context;
        private ProgressDialog mProgressDialog;
        private InterfaceServerSuccessDistributorDeletion interfaceRetrofit;
        TblPotentialDistributor tblPotentialDistributorList;
        private  int flgCldFrm;

        public DeleteExistingDistributor(Activity context, ProgressDialog mProgressDialog, InterfaceServerSuccessDistributorDeletion interfaceRetrofit, int flgCldFrm,  TblPotentialDistributor tblPotentialDistributorList) {
            this.context = context;
            this.mProgressDialog = mProgressDialog;
            this.interfaceRetrofit = interfaceRetrofit;
            this.flgCldFrm = flgCldFrm;
            this.tblPotentialDistributorList=tblPotentialDistributorList;
            mPreferenceManager= PreferenceManager.getInstance(context);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                TblPotentialDistributorForDelete reportsInfo = new TblPotentialDistributorForDelete();
                reportsInfo.setPDACode(tblPotentialDistributorList.getPDACode());
                reportsInfo.setNodeID(tblPotentialDistributorList.getNodeID());
                reportsInfo.setNodeType(tblPotentialDistributorList.getNodeType());
                // reportsInfo.setTblPotentialDistributorRetailersFeedBackDetailsListForServerSaving(tblPotentialDistributorRetailersFeedBackDetailsListForServerSaving);


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<Object> call = apiService.DeleteDistributorData(reportsInfo);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        if (response.code() == 200) {

                          /*  List<TblServerEntryStatus> tblServerEntryStatusList=  allSummaryReportDayModel.getTblServerEntryStatus();
                            if(tblServerEntryStatusList!=null && tblServerEntryStatusList.size()>0){


                            }*/



                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.successRecordDelete(tblPotentialDistributorList);
                                }
                            });
                        } else {

                            context.runOnUiThread(() -> {
                                if (context != null && !context.isFinishing() && !context.isDestroyed()) {
                                    mProgressDialog.dismiss();
                                    interfaceRetrofit.failureRecordDelete();
                                }
                            });

                        }



                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println();
                        context.runOnUiThread(() -> {
                            mProgressDialog.dismiss();
                            interfaceRetrofit.failureRecordDelete();
                        });

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                Crashlytics.logException(e);
                context.runOnUiThread(() -> {
                    mProgressDialog.dismiss();
                    interfaceRetrofit.failureRecordDelete();
                });
            }
            return null;
        }
    }

}
