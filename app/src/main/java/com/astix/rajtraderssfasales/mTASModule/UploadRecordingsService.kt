package com.astix.rajtraderssfasales.mTASModule

import android.app.Service
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.*
import android.util.Log
import com.astix.Common.CommonInfo
import com.crashlytics.android.Crashlytics
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL


/*
class UploadRecordingsService : Service() {

    inner class GetFilesAndStartUploadingAsync() : AsyncTask<Void, Void, Void>() {
        val path = "${Environment.getExternalStorageDirectory().path}/${CommonInfo.CallRecordingFolder}"
        private val recordingFileDir = File(path)
        private val recordingFiles = recordingFileDir.listFiles()
        var conn: HttpURLConnection? = null
        val boundary = "*****"
        var dos: DataOutputStream? = null
        val lineEnd = "\r\n"
        val twoHyphens = "--"
        var bytesRead: Int = 0
        var bytesAvailable: Int = 0
        var bufferSize: Int = 0
        lateinit var buffer: ByteArray
        var serverResponseCode = 0

        val maxBufferSize = 1 * 1024 * 1024


        override fun doInBackground(vararg params: Void?): Void? {
            if (AppUtils.isOnline(this@UploadRecordingsService)) {
                val dbengine = DBAdapterKenya(this@UploadRecordingsService)
                var xmlFileName: String? = null
                val xmlForWeb = arrayOfNulls<String>(1)
                try {
                    val xmlfileNames: String = dbengine.fnGetMTASXMLFile("3")
                    var index = 0
                    if (xmlfileNames != "") {
                        val xmlfileArray = xmlfileNames.split("^").toTypedArray()
                        for (i in xmlfileArray.indices) {
                            println("index$index")
                            xmlFileName = xmlfileArray[i]


//
                            //String newzipfile = Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + xmlFileName + ".zip";
                            //  xmlForWeb[0]=         Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + xmlFileName + ".xml";
                            val newzipfile = Environment.getExternalStorageDirectory().toString() + "/" + CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".zip"
                            xmlForWeb[0] = Environment.getExternalStorageDirectory().toString() + "/" + CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".xml"
                            SyncMaster.zip(xmlForWeb, newzipfile)
                            var conn: HttpURLConnection? = null
                            var dos: DataOutputStream? = null
                            val lineEnd = "\r\n"
                            val twoHyphens = "--"
                            val boundary = "*****"
                            var bytesRead: Int
                            var bytesAvailable: Int
                            var bufferSize: Int
                            var buffer: ByteArray
                            val maxBufferSize = 1 * 1024 * 1024
                            val file2send = File(newzipfile)
                            var file_size = 0
                            file_size = try {
                                file2send.length().toString().toInt() //FOR KB
                                // file_size = Integer.parseInt(String.valueOf(file2send.length() / 1024));// For MB
                                // file_size = Integer.parseInt(String.valueOf(file2send.length() / 1048576));// For GB
                                // file_size = Integer.parseInt(String.valueOf(file2send.length() / 1073741824));// For TB
                            } catch (ex: Exception) {
//                                Toast.makeText(this@UploadRecordingsService,"uploading xml issues",Toast.LENGTH_SHORT).show()
                                0
                            }
                            // It is for Testing Purpose
                            //String urlString = "http://115.124.126.184/ReadXML_PragaSFAForTest/default.aspx?CLIENTFILENAME=" + zipFileName;

                            // It is for Live Purpose
                            // String urlString = "http://115.124.126.184/ReadXML_PragaSFA/default.aspx?CLIENTFILENAME=" + zipFileName;


//						 String urlString = CommonInfo.OrderSyncPath.trim() + "?CLIENTFILENAME=" + xmlFileName+".xml";
                            if (file_size == 0) {
                                serverResponseCode = 200
                                if (serverResponseCode == 200) {
                                    dbengine.deleteMTASXmlTable("3")
                                    */
/*dbengine.upDatetbl_allAnswermstr("3");
		                     dbengine.upDatetbl_DynamcDataAnswer("3");*//*
deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".xml")
                                    deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".zip")
                                    println("ShivamDELETE$xmlFileName")
                                }
                            } else {
                                val urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim { it <= ' ' } + CommonInfo.ClientFileNameOrderSync + "&CLIENTFILENAME=" + xmlFileName + ".xml"


                                // open a URL connection to the Servlet
                                val fileInputStream = FileInputStream(file2send)
                                val url = URL(urlString)

                                // Open a HTTP  connection to  the URL
                                conn = url.openConnection() as HttpURLConnection
                                conn!!.doInput = true // Allow Inputs
                                conn!!.doOutput = true // Allow Outputs
                                conn!!.useCaches = false // Don't use a Cached Copy
                                conn!!.requestMethod = "POST"
                                conn!!.setRequestProperty("Connection", "Keep-Alive")
                                conn!!.setRequestProperty("ENCTYPE", "multipart/form-data")
                                conn!!.setRequestProperty("Content-Type", "multipart/form-data;boundary=$boundary")
                                conn!!.setRequestProperty("zipFileName", xmlFileName)
                                dos = DataOutputStream(conn!!.outputStream)
                                dos.writeBytes(twoHyphens + boundary + lineEnd)
                                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                        + xmlFileName + "\"" + lineEnd)
                                dos.writeBytes(lineEnd)

                                // create a buffer of  maximum size
                                bytesAvailable = fileInputStream.available()
                                bufferSize = Math.min(bytesAvailable, maxBufferSize)
                                buffer = ByteArray(bufferSize)

                                // read file and write it into form...
                                bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                                while (bytesRead > 0) {
                                    dos.write(buffer, 0, bufferSize)
                                    bytesAvailable = fileInputStream.available()
                                    bufferSize = Math.min(bytesAvailable, maxBufferSize)
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                                }

                                // send multipart form data necesssary after file data...
                                dos.writeBytes(lineEnd)
                                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)

                                // Responses from the server (code and message)
                                serverResponseCode = conn!!.responseCode
                                val serverResponseMessage = conn!!.responseMessage
                                Log.i("uploadFile", "HTTP Response is : "
                                        + serverResponseMessage + ": " + serverResponseCode)
                                if (serverResponseCode == 200) {
                                    dbengine.deleteMTASXmlTable("3")
                                    */
/*dbengine.upDatetbl_allAnswermstr("3");
		                     dbengine.upDatetbl_DynamcDataAnswer("3");*//*

                                    Handler(Looper.getMainLooper()).post {
                                        deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".xml")
                                        deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".zip")
                                    }
                                    println("ShivamDELETE$xmlFileName")
                                }


                                //close the streams //
                                fileInputStream.close()
                                dos.flush()
                                dos.close()
                                index++
                            }
                        }
                    } else {
                        serverResponseCode = 200
                    }
                } catch (e: Exception) {
//                    Toast.makeText(this@UploadRecordingsService,"uploading xml issues 2",Toast.LENGTH_SHORT).show()
                    serverResponseCode = -1
                    e.printStackTrace()
                }



                if (recordingFiles != null) {
                    for (item in recordingFiles) {
                        val fileToSend = File(item.absolutePath)
                        var file_size = 0
                        file_size = fileToSend.length().toString().toInt() //FOR KB
                        try {

                            if (file_size == 0) {

                            } else {
                                val urlString = CommonInfo.COMMON_SYNC_PATH_URL.trim { it <= ' ' } + CommonInfo.ClientFileNameCallRecordsSyncPath + "&CLIENTFILENAME=" + item.name


                                // open a URL connection to the Servlet
                                val fileInputStream = FileInputStream(fileToSend)
                                val url = URL(urlString)

                                // Open a HTTP  connection to  the URL
                                conn = url.openConnection() as HttpURLConnection
                                conn!!.doInput = true // Allow Inputs

                                conn!!.doOutput = true // Allow Outputs

                                conn!!.useCaches = false // Don't use a Cached Copy

                                conn!!.requestMethod = "POST"
                                conn!!.setRequestProperty("Connection", "Keep-Alive")
                                conn!!.setRequestProperty("ENCTYPE", "multipart/form-data")
                                conn!!.setRequestProperty("Content-Type", "multipart/form-data;boundary=$boundary")
                                conn!!.setRequestProperty("audioFileName", item.name)

                                dos = DataOutputStream(conn!!.outputStream)

                                dos!!.writeBytes(twoHyphens + boundary + lineEnd)
                                dos!!.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                        + item.name + "\"" + lineEnd)

                                dos!!.writeBytes(lineEnd)

                                // create a buffer of  maximum size

                                // create a buffer of  maximum size
                                bytesAvailable = fileInputStream.available()

                                bufferSize = Math.min(bytesAvailable, maxBufferSize)
                                buffer = ByteArray(bufferSize)

                                // read file and write it into form...

                                // read file and write it into form...
                                bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                                while (bytesRead > 0) {
                                    dos!!.write(buffer, 0, bufferSize)
                                    bytesAvailable = fileInputStream.available()
                                    bufferSize = Math.min(bytesAvailable, maxBufferSize)
                                    bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                                }

                                // send multipart form data necesssary after file data...

                                // send multipart form data necesssary after file data...
                                dos!!.writeBytes(lineEnd)
                                dos!!.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)

                                // Responses from the server (code and message)

                                // Responses from the server (code and message)
                                serverResponseCode = conn!!.responseCode
                                val serverResponseMessage = conn!!.responseMessage
                                */
/*Handler(Looper.getMainLooper()).post {
                                    Toast.makeText(this@UploadRecordingsService, "HTTP Response is : $serverResponseMessage: $serverResponseCode", Toast.LENGTH_SHORT)
                                }*//*


                                Log.i("uploadFile", "HTTP Response is : "
                                        + serverResponseMessage + ": " + serverResponseCode)

                                if (serverResponseCode == 200) {
                                    */
/*dbengine.upDatetbl_allAnswermstr("3");
                                 dbengine.upDatetbl_DynamcDataAnswer("3");*//*


                                    Handler(Looper.getMainLooper()).post {
                                        deleteViewdXml(CommonInfo.CallRecordingFolder + "/" + item.name)
                                    }
                                    println("ShivamDELETE${item.name}")
                                }


                                //close the streams //


                                //close the streams //
                                fileInputStream.close()
                                dos!!.flush()
                                dos!!.close()
                            }

                        }catch (ex : java.lang.Exception){
//                            Toast.makeText(this@UploadRecordingsService,"Audio uploading issues",Toast.LENGTH_SHORT).show()
                            Crashlytics.logException(ex)
                        }
                    }
                }
            }
            return null
        }
    }

    private fun deleteViewdXml(file_dj_path: String) {
        val dir = Environment.getExternalStorageDirectory()
        val fdelete = File(dir, file_dj_path)
        // File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
//                Toast.makeText(this,"file Deleted :$file_dj_path",Toast.LENGTH_SHORT)
                Log.e("-->", "file Deleted :$file_dj_path")
                callBroadCast()
            } else {
//                Toast.makeText(this,"file not Deleted :$file_dj_path",Toast.LENGTH_SHORT)
                Log.e("-->", "file not Deleted :$file_dj_path")
            }
        }
    }

    fun callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14")
            MediaScannerConnection.scanFile(this, arrayOf(Environment.getExternalStorageDirectory().toString()), null) { path, uri -> }
        } else {
            Log.e("-->", " < 14")
            sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())))
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        GetFilesAndStartUploadingAsync().execute()
        startCountDown()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (countDownTimer != null)
            countDownTimer.cancel()
    }
    private lateinit var countDownTimer : CountDownTimer
    private fun startCountDown() {
        countDownTimer = object : CountDownTimer(300*/
/*1800000*//*
, 1000) {
            override fun onTick(millisUntilFinished: Long) {
//                Log.e("CountDownTimer","$millisUntilFinished")
            }

            override fun onFinish() {

                GetFilesAndStartUploadingAsync().execute()
                startCountDown()
            }
        }.start()
    }
}*/
