package com.astix.rajtraderssfasales.merchandizing;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.astix.Common.CommonInfo;

import com.astix.rajtraderssfasales.ActualVisitStock;
import com.astix.rajtraderssfasales.BaseActivity;
import com.astix.rajtraderssfasales.DeletePic;
import com.astix.rajtraderssfasales.ExpandableHeightGridView;
import com.astix.rajtraderssfasales.InterfaceContactUpdate;
import com.astix.rajtraderssfasales.LastVisitDetails;
import com.astix.rajtraderssfasales.ProductEntryForm;
import com.astix.rajtraderssfasales.R;
import com.astix.rajtraderssfasales.StoreEditActivityContactLocation;
import com.astix.rajtraderssfasales.camera.CameraPreview;
import com.astix.rajtraderssfasales.database.AppDataSource;
import com.astix.rajtraderssfasales.sync.DatabaseAssistant;
import com.astix.rajtraderssfasales.utils.AppUtils;
import com.crashlytics.android.Crashlytics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.astix.rajtraderssfasales.camera.CameraUtils.findFrontFacingCamera;
import static com.astix.rajtraderssfasales.camera.CameraUtils.hasCamera;


public class PicAfterMerchandising extends BaseActivity implements DeletePic {//, InterfaceContactUpdate
    public  Context ctx;

    View viewContactSection;
    LinearLayout ll_ContactUpdateSection;
    TextView tvContactNoCorrection;
    Button btnEditContact;




    ImageView ivBack;
    TextView txt_header;
    public String storeID = "0";
    public String selStoreName;
    public String date;
    public String pickerDate;
    public String imei;
    String storeVisitCode = "";

    private int flgIsPicsAllowed = 1;
    String storePics = "";
    @BindView(R.id.picsAllowedRG)
    RadioGroup picsAllowedRG;
    @BindView(R.id.commentsET)
    EditText commentsET;



    private final LinkedHashMap<String, Integer> hmapCtgry_Imageposition = new LinkedHashMap<>();
    private final LinkedHashMap<String, ArrayList<String>> hmapCtgryPhotoSection = new LinkedHashMap<>();
    private final LinkedHashMap<String, ImageAdapter> hmapImageAdapter = new LinkedHashMap<>();
    private final LinkedHashMap<String, Integer> hmapCtgrySign_Imageposition = new LinkedHashMap<>();
    private final LinkedHashMap<String, ArrayList<String>> hmapCtgrySignPhotoSection = new LinkedHashMap<>();
    private final LinkedHashMap<String, ImageAdapter> hmapSignImageAdapter = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> hmapPhotoDetailsForSaving = new LinkedHashMap<>();
    public int maxAllowedPhotos = CommonInfo.maxAllowedPhotos;
    public String CustomerNodeType = "180";
    public int cntAfterMerchandisingPhotoTaken = 0;
    public DatabaseAssistant DASFA = null;
    public int flgType = 0;
    public String Spoke_ID = "SpokeID";
    public String clickedTagPhoto = "AfterMerchandising";
    public String clickedTagSignPhoto = "SignAfterMerchandising";
    public String StoreIDForImg = "0";
    public String selectedSpokeName = "NA";
    ProgressDialog pDialog2;
    boolean flgListEmptyRack = false;
    String formattedDate = "";

    @BindView(R.id.btn_clickPhoto)
    Button btn_clickPhoto;
    @BindView(R.id.btn_clickSignPhoto)
    Button btn_clickSignPhoto;
    @BindView(R.id.storeName_txt)
    TextView mTxtStoreName;
    @BindView(R.id.recycler_view_ClickedPhotos)
    ExpandableHeightGridView recycler_view_ClickedPhotos;
    @BindView(R.id.recycler_view_ClickedSignPhotos)
    ExpandableHeightGridView recycler_view_ClickedSignPhotos;
    ImageAdapter adapterImage_sign = null;
    ArrayList<String> list_SignImgName;
    ImageAdapter adapterImage = null;
    ArrayList<String> list_ImgName;
    String SpokeVisitCode = "";
    AppDataSource mDataSource;
    private int picAddPosition = 0;
    private int removePicPositin = 0;
    private float mDist = 0;
    private Activity mActivity;
    private ArrayList<Object> arrSignImageData;
    private String imageName;
    private Uri uriSavedImage;
    private ImageView flashImage;
    private Button capture, cancelCam;
    private ArrayList<Object> arrImageData;
    private LinearLayout cameraPreview;
    private CameraPreview mPreview;
    private Camera mCamera;
    private Button switchCamera;
    private boolean isLighOn = false;
    private Camera.PictureCallback mPicture;
    private Dialog dialog;

    View.OnClickListener captrureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            cancelCam.setEnabled(false);
            flashImage.setEnabled(false);
            if (cameraPreview != null) {
                cameraPreview.setEnabled(false);
            }

            if (mCamera != null) {
                mCamera.takePicture(null, null, mPicture);
            } else {
                dialog.dismiss();
            }
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    };
    private int flgStoreActivity;

    private  File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", CommonInfo.ImagesFolder);

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMMdd_HHmmss.SSS", Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + AppUtils.getToken(PicAfterMerchandising.this) + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public void onDestroy() {
        super.onDestroy();

    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
        *//*    Intent fireBackDetPg=new Intent(PicAfterMerchandising.this, DisplayItemPics.class);
            fireBackDetPg.putExtra("storeID", storeID);
            fireBackDetPg.putExtra("SN", selStoreName);
            fireBackDetPg.putExtra("bck", 1);
            fireBackDetPg.putExtra("imei", imei);
            fireBackDetPg.putExtra("userdate", date);
            fireBackDetPg.putExtra("pickerDate", pickerDate);
            fireBackDetPg.putExtra("flgOrderType", 1);
            //fireBackDetPg.putExtra("rID", routeID);
            startActivity(fireBackDetPg);
            finish();*//*
            Intent nxtP4 = new Intent(PicAfterMerchandising.this, ActualVisitStock.class);
            nxtP4.putExtra("storeID", storeID);
            nxtP4.putExtra("SN", selStoreName);
            nxtP4.putExtra("imei", imei);
            nxtP4.putExtra("StoreVisitCode", storeVisitCode);
            nxtP4.putExtra("token", imei);
            nxtP4.putExtra("userdate", date);
            nxtP4.putExtra("pickerDate", pickerDate);
            nxtP4.putExtra("flgOrderType", 1);
            startActivity(nxtP4);
            finish();
        }
        return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clck_pics);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);
        ctx=this;
        mDataSource = AppDataSource.getInstance(this);
        DASFA = new DatabaseAssistant(this);

        imei = AppUtils.getToken(ctx);
        mActivity = this;
       /* setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getIntentExtras();
        toolbar.setTitle("After Merchandising");*/
        getIntentExtras();

        txt_header=findViewById(R.id.txt_header);
        txt_header.setText("After Merchandising");


        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nxtP4 = new Intent(PicAfterMerchandising.this, ActualVisitStock.class);
                nxtP4.putExtra("storeID", storeID);
                nxtP4.putExtra("SN", selStoreName);
                nxtP4.putExtra("imei", imei);
                nxtP4.putExtra("StoreVisitCode", storeVisitCode);
                nxtP4.putExtra("token", imei);
                nxtP4.putExtra("userdate", date);
                nxtP4.putExtra("pickerDate", pickerDate);
                nxtP4.putExtra("flgOrderType", 1);
                startActivity(nxtP4);
                finish();
            }
        });



        adapterImage = new ImageAdapter(this);

        arrImageData = new ArrayList<>();

        storeVisitCode = getStoreVisitCode();
        storePics = mDataSource.fnGetStoreNoPicsFlag(storeVisitCode);
        adapterImage_sign = new ImageAdapter(this);

        arrSignImageData = new ArrayList<>();

        recycler_view_ClickedPhotos.setAdapter(adapterImage);
        recycler_view_ClickedPhotos.setExpanded(true);
        hmapImageAdapter.put(clickedTagPhoto, adapterImage);


        recycler_view_ClickedSignPhotos.setAdapter(adapterImage_sign);
        recycler_view_ClickedSignPhotos.setExpanded(true);
        hmapSignImageAdapter.put(clickedTagSignPhoto, adapterImage_sign);

//        storeVisitCode = getStoreVisitCode();
        btn_clickPhoto.setText("Take Photo After Merchandising*");

        btn_clickSignPhoto.setVisibility(View.GONE);
        recycler_view_ClickedSignPhotos.setVisibility(View.GONE);
        initView();
        fetchPhotoDetails();
        clickImage();

        fetchSignPhotoDetails();
        clickSignImage();
    }

    void clickImage() {
        /*String selectedTextID="00";

        selectedTextID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
        */
        if (list_ImgName == null || list_ImgName.size() == 0) {
            adapterImage = new ImageAdapter(PicAfterMerchandising.this);
            recycler_view_ClickedPhotos.setAdapter(adapterImage);

            hmapImageAdapter.put(clickedTagPhoto, adapterImage);
        }
    }

    void clickSignImage() {
        /*String selectedTextID="00";

        selectedTextID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
        */
        if (list_SignImgName == null || list_SignImgName.size() == 0) {
            adapterImage_sign = new ImageAdapter(PicAfterMerchandising.this);
            recycler_view_ClickedSignPhotos.setAdapter(adapterImage_sign);
            hmapSignImageAdapter.put(clickedTagSignPhoto, adapterImage_sign);
        }
    }

    void fetchSignPhotoDetails() {
        if (list_SignImgName != null && list_SignImgName.size() > 0) {
            adapterImage_sign = new ImageAdapter(PicAfterMerchandising.this);
            // clickedTagPhoto = "Rack";

            int picPosition = 0;
            for (int i = 0; i < list_SignImgName.size(); i++) {
                String imgName = list_SignImgName.get(i);
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imgName;
                File fImageShow = new File(file_dj_path);
                if (fImageShow.exists()) {
                    Bitmap bmp = decodeSampledBitmapFromFile(fImageShow.getAbsolutePath(), 80, 80);
                    adapterImage_sign.add(i, bmp, imgName + "^" + clickedTagPhoto);

                    hmapCtgrySign_Imageposition.put(clickedTagSignPhoto, picPosition);
                    picPosition++;
                }
            }
            recycler_view_ClickedSignPhotos.setAdapter(adapterImage_sign);

            hmapSignImageAdapter.put(clickedTagSignPhoto, adapterImage_sign);


            hmapCtgrySignPhotoSection.put(clickedTagSignPhoto, list_SignImgName);


        }


    }

    void fetchPhotoDetails() {
        if (list_ImgName != null && list_ImgName.size() > 0) {
            adapterImage = new ImageAdapter(PicAfterMerchandising.this);
            // clickedTagPhoto = "Rack";

            int picPosition = 0;
            for (int i = 0; i < list_ImgName.size(); i++) {
                String imgName = list_ImgName.get(i);
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imgName;
                File fImageShow = new File(file_dj_path);
                if (fImageShow.exists()) {
                    Bitmap bmp = decodeSampledBitmapFromFile(fImageShow.getAbsolutePath(), 80, 80);
                    adapterImage.add(i, bmp, imgName + "^" + clickedTagPhoto);
                    cntAfterMerchandisingPhotoTaken = cntAfterMerchandisingPhotoTaken + 1;
                    hmapCtgry_Imageposition.put(clickedTagPhoto, picPosition);
                    picPosition++;
                }
            }
            recycler_view_ClickedPhotos.setAdapter(adapterImage);

            hmapImageAdapter.put(clickedTagPhoto, adapterImage);

            hmapCtgryPhotoSection.put(clickedTagPhoto, list_ImgName);

        }


    }

    public void openCustomCamara(int flgWhichButtonCliked) {
        String section_name = "";
        int imageType = 0;
        int numImages = 0;
        if (flgWhichButtonCliked == 1) {
            if (hmapCtgryPhotoSection != null && hmapCtgryPhotoSection.size() != 0) {
                numImages = hmapCtgryPhotoSection.get(clickedTagPhoto).size();
                section_name = "Raj's shelves after merchandising Photos";
                imageType = 4;

            }
        } else if (flgWhichButtonCliked == 2) {
            if (hmapCtgrySignPhotoSection != null && hmapCtgrySignPhotoSection.size() != 0) {
                numImages = hmapCtgrySignPhotoSection.get(clickedTagSignPhoto).size();
                section_name = "Sign Board Photos";
                imageType = 19;

            }
        }

        if (AppUtils.fnValidateMaxAllowedPhotosTakenCount(mActivity, imageType, flgWhichButtonCliked, numImages, maxAllowedPhotos) == true) {
            AppUtils.fnValidateMaxAllowedPhotosMsg(mActivity, section_name, imageType, maxAllowedPhotos);
        } else {
            openCamera(flgWhichButtonCliked);//Second Parameter is CollectionID
        }


    }

    private void openCamera(int flgWhichButtonCliked) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (flgWhichButtonCliked == 1) {//Rack
            arrImageData.clear();
        } else if (flgWhichButtonCliked == 2) {
            arrSignImageData.clear();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dialog = new Dialog(PicAfterMerchandising.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //dialog.setTitle("Calculation");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_cameraview);
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();

        parms.height = ViewGroup.LayoutParams.MATCH_PARENT;
        parms.width = ViewGroup.LayoutParams.MATCH_PARENT;
        cameraPreview = dialog.findViewById(R.id.camera_preview);
        switchCamera = dialog.findViewById(R.id.btSwitchCamera);
        if (mCamera != null) {
            mPreview = new CameraPreview(PicAfterMerchandising.this, mCamera);
            cameraPreview.addView(mPreview);
        }
        //onResume code
        if (!hasCamera(PicAfterMerchandising.this)) {
            Toast toast = Toast.makeText(PicAfterMerchandising.this, getText(R.string.txtNoCamera), Toast.LENGTH_LONG);
            toast.show();
        }
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(PicAfterMerchandising.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                switchCamera.setVisibility(View.GONE);
            }

            //mCamera = Camera.open(findBackFacingCamera());


            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
			/*if(mCamera==null){
				mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
			}*/

            mPreview = new CameraPreview(this, mCamera);
            cameraPreview.addView(mPreview);
            boolean isParameterSet = false;
            try {
                Camera.Parameters params = mCamera.getParameters();


                List<Camera.Size> sizes = params.getSupportedPictureSizes();
                Camera.Size size = sizes.get(0);
                //Camera.Size size1 = sizes.get(0);
                for (int i = 0; i < sizes.size(); i++) {

                    if (sizes.get(i).width > size.width)
                        size = sizes.get(i);
                }

                //System.out.println(size.width + "mm" + size.height);

                params.setPictureSize(size.width, size.height);
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                //	params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);

                //	params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                isLighOn = false;
                int minExpCom = params.getMinExposureCompensation();
                int maxExpCom = params.getMaxExposureCompensation();

                if (maxExpCom > 4 && minExpCom < 4) {
                    params.setExposureCompensation(4);
                } else {
                    params.setExposureCompensation(0);
                }

                params.setAutoExposureLock(false);
                params.setAutoWhiteBalanceLock(false);
                //String supportedIsoValues = params.get("iso-values");
                // String newVAlue = params.get("iso");
                //  params.set("iso","1600");
                params.setColorEffect("none");
                params.set("scene-mode", "auto");
                params.setPictureFormat(ImageFormat.JPEG);
                params.setJpegQuality(70);
                params.setRotation(90);

                mCamera.setParameters(params);
                isParameterSet = true;
            } catch (Exception e) {

            }
            if (!isParameterSet) {
                Camera.Parameters params2 = mCamera.getParameters();
                params2.setPictureFormat(ImageFormat.JPEG);
                params2.setJpegQuality(70);
                params2.setRotation(90);

                mCamera.setParameters(params2);
            }

            setCameraDisplayOrientation(PicAfterMerchandising.this, Camera.CameraInfo.CAMERA_FACING_BACK, mCamera);
            mPicture = getPictureCallback(flgWhichButtonCliked);
            mPreview.refreshCamera(mCamera);
        }

        capture = dialog.findViewById(R.id.button_capture);

        flashImage = dialog.findViewById(R.id.flashImage);
        flashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLighOn) {
                    // turn off flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }

                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(params);
                    flashImage.setImageResource(R.drawable.flash_off);
                    isLighOn = false;
                } else {
                    // turn on flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }

                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

                    flashImage.setImageResource(R.drawable.flash_on);
                    mCamera.setParameters(params);

                    isLighOn = true;
                }
            }
        });

        final Button cancleCamera = dialog.findViewById(R.id.cancleCamera);
        cancelCam = cancleCamera;
        cancleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                v.setEnabled(false);
                capture.setEnabled(false);
                cameraPreview.setEnabled(false);
                flashImage.setEnabled(false);

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;
                releaseCameraAndPreview();
                dialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        capture.setOnClickListener(captrureListener);

        cameraPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Get the pointer ID
                Camera.Parameters params = mCamera.getParameters();
                int action = event.getAction();

                if (event.getPointerCount() > 1) {
                    // handle multi-touch events
                    if (action == MotionEvent.ACTION_POINTER_DOWN) {
                        mDist = getFingerSpacing(event);
                    } else if (action == MotionEvent.ACTION_MOVE
                            && params.isZoomSupported()) {
                        mCamera.cancelAutoFocus();
                        handleZoom(event, params);
                    }
                } else {
                    // handle single touch events
                    if (action == MotionEvent.ACTION_UP) {
                        handleFocus(event, params);
                    }
                }
                return true;
            }
        });

        dialog.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private Camera.PictureCallback getPictureCallback(final int flgWhichButtonCliked) {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //make a new picture file
                File pictureFile = getOutputMediaFile();

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;

                if (pictureFile == null) {
                    return;
                }
                try {
                    //write the file
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    if (flgWhichButtonCliked == 1) {
                        arrImageData.add(0, pictureFile);
                        arrImageData.add(1, pictureFile.getName());
                    } else if (flgWhichButtonCliked == 2) {
                        arrSignImageData.add(0, pictureFile);
                        arrSignImageData.add(1, pictureFile.getName());
                    }

                    dialog.dismiss();
                    if (pictureFile != null) {
                        File file = pictureFile;
                        System.out.println("File +++" + pictureFile);
                        imageName = pictureFile.getName();
                        Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 80, 80);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        uriSavedImage = Uri.fromFile(pictureFile);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        byte[] byteArray = stream.toByteArray();

                        // Convert ByteArray to Bitmap::\
                        //
                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date dateobj = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                        String clkdTime = df.format(dateobj);
                        //	String valueOfKey=imagButtonTag+"~"+tempId+"~"+file.getAbsolutePath()+"~"+clkdTime+"~"+"2";
//                        String valueOfKey = clickedTagPhoto + "~" + AddNewStore_DynamicSectionWise.selStoreID + "~" + uriSavedImage.toString() + "~" + clkdTime + "~" + "1";
                        //   helperDb.insertImageInfo(tempId,imagButtonTag, imageName, file.getAbsolutePath(), 2);
                        if (flgWhichButtonCliked == 1) {
                            String valueOfKey = clickedTagPhoto + "~" + StoreIDForImg + "~" + uriSavedImage.toString() + "~" + clkdTime + "~" + "1";

                            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            setSavedImageToScrollView(bitmap, imageName, valueOfKey, clickedTagPhoto, flgWhichButtonCliked, 4);
                            System.out.println("merch data..." + imageName + "~~" + valueOfKey + "~~" + clickedTagPhoto);
                        } else if (flgWhichButtonCliked == 2) {
                            String valueOfKey = clickedTagSignPhoto + "~" + StoreIDForImg + "~" + uriSavedImage.toString() + "~" + clkdTime + "~" + "1";

                            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            setSavedImageToScrollView(bitmap, imageName, valueOfKey, clickedTagSignPhoto, flgWhichButtonCliked, 19);
                            System.out.println("merch data..." + imageName + "~~" + valueOfKey + "~~" + clickedTagSignPhoto);
                        }

                    }


                } catch (FileNotFoundException e) {
                    Crashlytics.logException(e);
                } catch (IOException e) {
                    Crashlytics.logException(e);
                }

                //refresh camera to continue preview--------------------------------------------------------------
                //	mPreview.refreshCamera(mCamera);
                //if want to release camera
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }
            }
        };
        return picture;
    }

    private void setSavedImageToScrollView(Bitmap bitmap, String imageName, String valueOfKey, String clickedTagPhotonew, int flgWhichButtonCliked, int imageType) {

        if (flgWhichButtonCliked == 1) {//Rack
            if (hmapCtgry_Imageposition != null && hmapCtgry_Imageposition.size() > 0) {
                if (hmapCtgry_Imageposition.containsKey(clickedTagPhotonew)) {
                    picAddPosition = hmapCtgry_Imageposition.get(clickedTagPhotonew);
                    if (picAddPosition == -1) {
                        flgListEmptyRack = false;
                        picAddPosition = 0;
                    }
                } else {
                    picAddPosition = 0;
                }
            } else {
                picAddPosition = 0;
            }

            removePicPositin = picAddPosition;
            ArrayList<String> listClkdPic = new ArrayList<String>();
            if (hmapCtgryPhotoSection != null && hmapCtgryPhotoSection.containsKey(clickedTagPhotonew)) {
                listClkdPic = hmapCtgryPhotoSection.get(clickedTagPhotonew);
            }

            listClkdPic.add(imageName);
            hmapCtgryPhotoSection.put(clickedTagPhotonew, listClkdPic);
            ImageAdapter adapterImage = hmapImageAdapter.get(clickedTagPhotonew);
            adapterImage.add(picAddPosition, bitmap, imageName + "^" + clickedTagPhotonew);
            System.out.println("REMOVE AND PIC ADD..." + removePicPositin + "__" + picAddPosition);
            System.out.println("Picture Adapter" + picAddPosition);
            picAddPosition++;
            hmapCtgry_Imageposition.put(clickedTagPhotonew, picAddPosition);

            String photoPath = valueOfKey.split(Pattern.quote("~"))[2];
            String clickedDateTime = valueOfKey.split(Pattern.quote("~"))[3];
            cntAfterMerchandisingPhotoTaken = cntAfterMerchandisingPhotoTaken + 1;
            //key- imagName
            //value- businessId^CatID^TypeID^PhotoPath^ClikcedDatetime^PhotoTypeFlag^StackNo

            hmapPhotoDetailsForSaving.put(imageName, clickedTagPhotonew + "~" + photoPath + "~" + clickedDateTime + "~" + imageType);
            System.out.println("Hmap Photo..." + imageName + "^" + clickedTagPhotonew + "^" + photoPath + "^" + clickedDateTime);
        } else if (flgWhichButtonCliked == 2) {
            if (hmapCtgrySign_Imageposition != null && hmapCtgrySign_Imageposition.size() > 0) {
                if (hmapCtgrySign_Imageposition.containsKey(clickedTagPhotonew)) {
                    picAddPosition = hmapCtgrySign_Imageposition.get(clickedTagPhotonew);
                    if (picAddPosition == -1) {
                        flgListEmptyRack = false;
                        picAddPosition = 0;
                    }
                } else {
                    picAddPosition = 0;
                }
            } else {
                picAddPosition = 0;
            }

            removePicPositin = picAddPosition;
            ArrayList<String> listClkdPic = new ArrayList<String>();
            if (hmapCtgrySignPhotoSection != null && hmapCtgrySignPhotoSection.containsKey(clickedTagPhotonew)) {
                listClkdPic = hmapCtgrySignPhotoSection.get(clickedTagPhotonew);
            }

            listClkdPic.add(imageName);
            hmapCtgrySignPhotoSection.put(clickedTagPhotonew, listClkdPic);
            ImageAdapter adapterImage = hmapSignImageAdapter.get(clickedTagPhotonew);
            adapterImage.add(picAddPosition, bitmap, imageName + "^" + clickedTagPhotonew);
            System.out.println("REMOVE AND PIC ADD..." + removePicPositin + "__" + picAddPosition);
            System.out.println("Picture Adapter" + picAddPosition);
            picAddPosition++;
            hmapCtgrySign_Imageposition.put(clickedTagPhotonew, picAddPosition);

            String photoPath = valueOfKey.split(Pattern.quote("~"))[2];
            String clickedDateTime = valueOfKey.split(Pattern.quote("~"))[3];

            //key- imagName
            //value- businessId^CatID^TypeID^PhotoPath^ClikcedDatetime^PhotoTypeFlag^StackNo

            hmapPhotoDetailsForSaving.put(imageName, clickedTagPhotonew + "~" + photoPath + "~" + clickedDateTime + "~" + imageType);
            System.out.println("Hmap Photo..." + imageName + "^" + clickedTagPhotonew + "^" + photoPath + "^" + clickedDateTime);
        }


    }

    private void handleZoom(MotionEvent event, Camera.Parameters params) {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();
        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            // zoom in
            if (zoom < maxZoom)
                zoom++;
        } else if (newDist < mDist) {
            // zoom out
            if (zoom > 0)
                zoom--;
        }
        mDist = newDist;
        params.setZoom(zoom);
        mCamera.setParameters(params);
    }

    public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null
                && supportedFocusModes
                .contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }

    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void releaseCameraAndPreview() {
        //cameraPreview = null;
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


    @OnClick(R.id.btn_clickPhoto)
    public void onCameraRackClick() {

        // clickedTagPhotoRack="Gowdown";
        /*if(hmapCtgryPhotoSectionRack.get("Store").size()<4)
            openCustomCamara(1);*/
        /*if(hmapCtgryPhotoSection.size()==0)
            openCustomCamara(1);
        else if(  hmapCtgryPhotoSection.get(clickedTagPhoto).size()<2)
        {
            openCustomCamara(1);
        }
        else
        {
            AppUtils.fnValidateMaxAllowedPhotos(mActivity);
        }*/
        openCustomCamara(1);
        // openCustomCamara(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(StoreEditActivityContactLocation.flgCallActivityContactLocation==1)
        {
            inflateUpdateContactSection();
            StoreEditActivityContactLocation.flgCallActivityContactLocation=0;
        }
    }
    void inflateUpdateContactSectionLayout()
    {
        viewContactSection=getLayoutInflater().inflate(R.layout.layout_update_contact_frame, null);

        tvContactNoCorrection= (TextView) viewContactSection.findViewById(R.id.tvContactNoCorrection);
        btnEditContact= (Button) viewContactSection.findViewById(R.id.btnEditContact);

        ll_ContactUpdateSection.addView(viewContactSection);





    }
    void inflateUpdateContactSection()
    {


        tvContactNoCorrection= (TextView) viewContactSection.findViewById(R.id.tvContactNoCorrection);
        btnEditContact= (Button) viewContactSection.findViewById(R.id.btnEditContact);




        if(mDataSource.fnCheckStoreIntblStoreListForUpdateMstr(storeID)==1)
        {
            viewContactSection.setVisibility(View.VISIBLE);
            if(mDataSource.fnCheckStoreMappedInStoreListForUpdateMstr(storeID)==1)
            {
                viewContactSection.setVisibility(View.GONE);
                tvContactNoCorrection.setText("Contact Number Verified");
                tvContactNoCorrection.setTextColor(Color.parseColor("#74C543"));
            }

            btnEditContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ready4GetLoc = new Intent(PicAfterMerchandising.this, StoreEditActivityContactLocation.class);
                    ready4GetLoc.putExtra("storeID", storeID);
                    startActivity(ready4GetLoc);
                    //finish();
                }
            });
        }
        else
        {
            viewContactSection.setVisibility(View.GONE);
        }


    }


   /* @Override
    public void fnCallUpdateLocation() {
        inflateUpdateContactSection();
    }*/

    public void initView() {

        ll_ContactUpdateSection=findViewById(R.id.ll_ContactUpdateSection);
        inflateUpdateContactSectionLayout();
        inflateUpdateContactSection();

        if (!storePics.split(Pattern.quote("^"))[1].equals("NA")) {
            commentsET.setText(storePics.split(Pattern.quote("^"))[1]);
        }

        flgIsPicsAllowed = Integer.parseInt(storePics.split(Pattern.quote("^"))[0]);
        if (flgIsPicsAllowed == 1) {
            picsAllowedRG.check(R.id.yesRB);
            commentsET.setVisibility(View.GONE);
            recycler_view_ClickedSignPhotos.setVisibility(View.VISIBLE);
            btn_clickPhoto.setEnabled(true);
            //makeViewsMandate();
        } else {
            picsAllowedRG.check(R.id.noRB);
            commentsET.setVisibility(View.VISIBLE);
            recycler_view_ClickedSignPhotos.setVisibility(View.GONE);
            btn_clickPhoto.setEnabled(false);
            // makeViewsMandate();
        }
        picsAllowedRG.setEnabled(false);
        picsAllowedRG.findViewById(R.id.yesRB).setEnabled(false);
        picsAllowedRG.findViewById(R.id.noRB).setEnabled(false);
        commentsET.setEnabled(false);



        picsAllowedRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.yesRB) {
                    flgIsPicsAllowed = 1;
                    commentsET.setVisibility(View.GONE);
                    recycler_view_ClickedSignPhotos.setVisibility(View.VISIBLE);
                    btn_clickPhoto.setEnabled(true);
                    // makeViewsMandate();
                } else {
                    flgIsPicsAllowed = 0;
                    commentsET.setVisibility(View.VISIBLE);
                    recycler_view_ClickedSignPhotos.setVisibility(View.VISIBLE);
                    btn_clickPhoto.setEnabled(false);
                    // makeViewsMandate();
                }
            }
        });




        list_ImgName = mDataSource.getImagesAgainstStore(storeID, flgType, 4);
        if (list_ImgName != null && list_ImgName.size() > 0) {
            adapterImage = new ImageAdapter(PicAfterMerchandising.this);
            //expandableHeightGridView.setAdapter(adapterImage);
            clickedTagPhoto = "AfterMerchandising";
            hmapImageAdapter.put(clickedTagPhoto, adapterImage);
            fetchPhotoDetails();
        }

        list_SignImgName = mDataSource.getImagesAgainstStore(storeID, flgType, 19);
        if (list_SignImgName != null && list_SignImgName.size() > 0) {
            adapterImage_sign = new ImageAdapter(PicAfterMerchandising.this);
            //expandableHeightGridView.setAdapter(adapterImage);
            clickedTagSignPhoto = "SignBeforeMerchandising";
            hmapSignImageAdapter.put(clickedTagSignPhoto, adapterImage_sign);
            fetchSignPhotoDetails();
        }
    }

    private void getIntentExtras() {


        Bundle bundle = getIntent().getExtras();


        selStoreName = bundle.getString("SN");
        storeID = bundle.getString("storeID");
        imei = bundle.getString("imei");
        date = bundle.getString("userdate");
        pickerDate= bundle.getString("pickerDate");
        mTxtStoreName.setText("Abhinav Sir");

        flgType=2;



    }

    public String getStoreVisitCode() {

        String IMEINo = AppUtils.getToken(this);

        storeVisitCode = mDataSource.fnGetStoreVisitCode( storeID);
        if (storeVisitCode.equals("NA")) {
            storeVisitCode = genStoreVisitCode();
            String Comments = "NA";
           /* locationRetreivingGlobal = new LocationRetreivingGlobal();
            locationRetreivingGlobal.locationRetrievingAndDistanceCalculating(PicBeforeMerchandising.this, true, true, 20, 0);
*/

        }


        return storeVisitCode;
    }
    public String genStoreVisitCode() {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String VisitStartTS = df.format(dateobj);
        String cxz;
        cxz = UUID.randomUUID().toString();


        StringTokenizer tokens = new StringTokenizer(cxz, "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        //String IMEIid = CommonInfo.imei.substring(9);
        cxz = "StoreVisitCode" + "-" + cxz + "-" + VisitStartTS.replace(" ", "").replace(":", "").trim();


        return cxz;

    }


    public void fnCallSavingDatails() {
        // storeVisitCode=getStoreVisitCode();

        fnSaveImagesInDatabase();
        //   mDataSource.insertIntoTblCompetitorDetails(competitorData);


    }

    @OnClick(R.id.bt_Next)
    public void saveData(View view) {
        if (fnValidatImageRackPhotoRakenorNot() == false) {
            showAlertSingleButtonInfo("Please take Shelves Photo after merchandising");
            return;
        }
       /* if (fnValidatImageRackPhotoRakenorNot() == false) {
            showAlertSingleButtonInfo("Please click the Rack Photo");
            return;
        }*/

      /*  Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
        formattedDate=dateFormat.format(date);
        mDataSource.insertintotblLastPDAActivityDeatils("Store "+ "("+tblSpokeProfile.getSpokename()+ ")" +" " +" Visited at",formattedDate,"3",tblSpokeProfile.getSpokeNodeID());
*/
        fnCallSavingDatails();


    }


    @Override
    public void getProductPhotoDetail(String productIdTag) {

    }

    @Override
    public void delPic(Bitmap bmp, String imageNameToDel) {
        String imageNameToDelVal = imageNameToDel.split(Pattern.quote("^"))[0];
        String tagPhoto = imageNameToDel.split(Pattern.quote("^"))[1];
        if (hmapCtgry_Imageposition.containsKey(tagPhoto))
            picAddPosition = hmapCtgry_Imageposition.get(tagPhoto);

        else if (hmapCtgrySign_Imageposition.containsKey(tagPhoto))
            picAddPosition = hmapCtgrySign_Imageposition.get(tagPhoto);


        if (picAddPosition > 1) {
            removePicPositin = picAddPosition - 1;
        } else {
            removePicPositin = picAddPosition;
        }

        removePicPositin = removePicPositin - 1;
        picAddPosition = picAddPosition - 1;
        System.out.println("REMOVE AND PIC ADD DEL..." + removePicPositin + "__" + picAddPosition);

        if (hmapCtgry_Imageposition.containsKey(tagPhoto))
            hmapCtgry_Imageposition.put(tagPhoto, picAddPosition);

        else if (hmapCtgrySign_Imageposition.containsKey(tagPhoto))
            hmapCtgrySign_Imageposition.put(tagPhoto, picAddPosition);


        ArrayList<String> listClkdPic = new ArrayList<String>();
        if (hmapCtgryPhotoSection != null && hmapCtgryPhotoSection.containsKey(tagPhoto)) {
            listClkdPic = hmapCtgryPhotoSection.get(tagPhoto);
            if (listClkdPic.contains(imageNameToDelVal)) {
                listClkdPic.remove(imageNameToDelVal);
                ImageAdapter adapterImage = hmapImageAdapter.get(tagPhoto);
                adapterImage.remove(bmp);
                hmapCtgryPhotoSection.put(tagPhoto, listClkdPic);
                if (listClkdPic.size() < 1) {
                    hmapCtgryPhotoSection.remove(tagPhoto);
                }
            }
            if (cntAfterMerchandisingPhotoTaken > 0)
                cntAfterMerchandisingPhotoTaken = cntAfterMerchandisingPhotoTaken - 1;
            if (listClkdPic.size() == 0) {
                flgListEmptyRack = true;
            }
        } else if (hmapCtgrySignPhotoSection != null && hmapCtgrySignPhotoSection.containsKey(tagPhoto)) {
            listClkdPic = hmapCtgrySignPhotoSection.get(tagPhoto);
            if (listClkdPic.contains(imageNameToDelVal)) {
                listClkdPic.remove(imageNameToDelVal);
                ImageAdapter adapterImage = hmapSignImageAdapter.get(tagPhoto);
                adapterImage.remove(bmp);
                hmapCtgrySignPhotoSection.put(tagPhoto, listClkdPic);
                if (listClkdPic.size() < 1) {
                    hmapCtgrySignPhotoSection.remove(tagPhoto);
                }
            }
            if (listClkdPic.size() == 0) {
                flgListEmptyRack = true;
            }
        }


        hmapPhotoDetailsForSaving.remove(imageNameToDelVal);
        //  String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageNameToDel;
        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" + imageNameToDelVal;
        mDataSource.validateAndDelPic(1, imageNameToDelVal, "0", StoreIDForImg);
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                callBroadCast();
            } else {
            }
        }
    }

    private void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
    }



    public void fnSaveImagesInDatabase() {
        if (hmapPhotoDetailsForSaving != null && hmapPhotoDetailsForSaving.size() > 0) {
            for (Map.Entry<String, String> entry : hmapPhotoDetailsForSaving.entrySet()) {//clickedTagPhoto+"~"+photoPath+"~"+clickedDateTime+"~"+"1"
                String photoName = entry.getKey();
                String photoPath = entry.getValue().split(Pattern.quote("~"))[1];
                String clickedDateTime = entry.getValue().split(Pattern.quote("~"))[2];
                int ImageType = Integer.parseInt(entry.getValue().split(Pattern.quote("~"))[3]);

                mDataSource.insertImagesAgainstStore(storeID, clickedDateTime, photoName, photoPath, 1, ImageType, flgType,storeVisitCode);
                // System.out.println("SAVING..."+storeID+"-"+clickedDateTime+"-"+photoName+"-"+photoPath);
            }
        }
        mDataSource.fnUpdate_tblStoreVisitMaster(storeVisitCode, commentsET.getText().toString().trim(), flgIsPicsAllowed);
        Intent nxtP4 = new Intent(PicAfterMerchandising.this, ProductEntryForm.class);
        //Intent nxtP4 = new Intent(LastVisitDetails.this,ProductOrderFilterSearch_RecycleView.class);
        nxtP4.putExtra("storeID", storeID);
        nxtP4.putExtra("SN", selStoreName);
        nxtP4.putExtra("imei", imei);
        nxtP4.putExtra("token", imei);
        nxtP4.putExtra("userdate", date);
        nxtP4.putExtra("pickerDate", pickerDate);
        nxtP4.putExtra("flgOrderType", 1);
        startActivity(nxtP4);
        finish();

    }

    public Boolean fnValidatImageRackPhotoRakenorNot() {
     /*   Boolean flgImageRackPhotoTakeOrNot = false;
        cntAfterMerchandisingPhotoTaken = 0;
        if (hmapCtgryPhotoSection != null && (hmapCtgryPhotoSection.size() > 0)) {
            flgImageRackPhotoTakeOrNot = true;
            cntAfterMerchandisingPhotoTaken = 1;

        }
        return flgImageRackPhotoTakeOrNot;*/



        Boolean flgImageRackPhotoTakeOrNot = true;
        cntAfterMerchandisingPhotoTaken=0;
        int i = 1;
        int flgcolImageValidation = 0;
        String colNameToValidate = "";
        if (flgIsPicsAllowed == 0 && TextUtils.isEmpty(commentsET.getText().toString().trim())) {
            flgcolImageValidation = 1;
            if (colNameToValidate.equals(""))
                colNameToValidate = (i++) + ". Please enter reason.";
            else
                colNameToValidate = colNameToValidate + "\n" + (i++) + ". Please enter reason.";

//            showAlertSingleButtonInfo("Please enter reason.");
            flgImageRackPhotoTakeOrNot = true; //for making it madatory make it true and then write the code for validation
        }

        if(flgImageRackPhotoTakeOrNot==true) {
            if (flgIsPicsAllowed == 1 && hmapCtgryPhotoSection != null && (hmapCtgryPhotoSection.size() > 0)) {
                flgImageRackPhotoTakeOrNot = true;
                cntAfterMerchandisingPhotoTaken = 1;
            }
            else if (flgIsPicsAllowed == 0)
            {
                flgImageRackPhotoTakeOrNot = true;
                cntAfterMerchandisingPhotoTaken = 0;
            }
            else {
                flgImageRackPhotoTakeOrNot = false;
                cntAfterMerchandisingPhotoTaken = 0;
            }
        }
        return flgImageRackPhotoTakeOrNot;
    }



}
