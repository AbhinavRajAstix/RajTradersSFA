package com.astix.rajtraderssfasales.mTASModule

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Environment
import android.os.Handler
import android.provider.CallLog
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import com.astix.Common.CommonInfo
import com.crashlytics.android.Crashlytics
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/*

class MyPhoneStateListener(var context: Context) : PhoneStateListener() {
    private var dbAdapterKenya: DBAdapterKenya = DBAdapterKenya(context)

    override fun onCallStateChanged(state: Int, incomingNumber: String) {
        when (state) {
            TelephonyManager.CALL_STATE_IDLE -> {
                Log.d("DEBUG", "IDLE")

                offHookCalled = false
                if (!disconnected) {
                    val tblMTASCall = dbAdapterKenya.getDebugState(CommonInfo.CurrentTime)
                    tblMTASCall.state = 3
                    tblMTASCall.debugState = tblMTASCall.debugState+",IDLE"

                    Log.e("tblMTASCallLogs", tblMTASCall.toString())
                    dbAdapterKenya.updateCallLogs(tblMTASCall)
                    disconnected = true
                } else {
                    return
                }

                if (CommonInfo.ContactNumber.isNotEmpty()) {

                    try {
                        Handler().postDelayed({
                            if (CommonInfo.FlgRecording == 1)
                            AppUtils.stopCallRecord()

                            getCallDetails()
                        }, 1000)
                    }catch (ex : Exception){
                        Toast.makeText(context,"call logs issues",Toast.LENGTH_SHORT).show()
                    }

                    disconnected = false
                }
            }

            TelephonyManager.CALL_STATE_OFFHOOK -> {
                Log.d("DEBUG", "OFFHOOK")
                if (!offHookCalled) {
                    val tblMTASCall = dbAdapterKenya.getDebugState(CommonInfo.CurrentTime)
                    tblMTASCall.state = 2
                    tblMTASCall.debugState = tblMTASCall.debugState+",offHook"

                    Log.e("tblMTASCallLogs", tblMTASCall.toString())
                    dbAdapterKenya.updateCallLogs(tblMTASCall)
                    offHookCalled = true
                    println("not return")
                } else {
                    println("return")
                    return
                }

                if (CommonInfo.ContactNumber.isNotEmpty() && offHookCalled) {
                   */
/* Handler().postDelayed({

                    }, 1000)*//*

                    val intent: Intent = Intent(context, MTASWebActivity::class.java)
                    Toast.makeText(context,"Swapping",Toast.LENGTH_SHORT).show()
                    //                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
            TelephonyManager.CALL_STATE_RINGING -> {
                Log.d("DEBUG", "RINGING")
                phoneRinging = true
            }
        }
    }

    private fun getCallDetails() {
        val sb = StringBuffer()
        val cur = context.contentResolver.query(
                CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC limit 10;"
        )

        val number = cur!!.getColumnIndex(CallLog.Calls.NUMBER)
        val duration = cur.getColumnIndex(CallLog.Calls.DURATION)
        val dateTime = cur.getColumnIndex(CallLog.Calls.DATE)
        sb.append("Call Details : \n")
        while (cur.moveToNext()) {
            val phNumber = cur.getString(number)
            val callDuration = cur.getString(duration)
            var dateTime = cur.getString(dateTime)
            dateTime = AppUtils.convertMillisIntoDateTime(dateTime)
            val dir: String? = null
            sb.append("\nPhone Number:--- $phNumber \nCall duration in sec :--- $callDuration ")
            sb.append("\n----------------------------------")
            var callPickedTiming = if (callDuration != "0") {
                AppUtils.getCallPickDateTime(callDuration, context)
            } else {
                "NA"
            }
            sb.append("\ncallPickedTiming:--- $callPickedTiming \ndateTime :--- $dateTime ")
            if (phNumber == CommonInfo.ContactNumber) {
                Toast.makeText(context,"$sb",Toast.LENGTH_SHORT).show()
                Log.d("Call Logs", "$sb")
                saveCallLogsToDB(phNumber, callDuration, dateTime, callPickedTiming)
                break
            }
        }
        cur.close()

    }

    fun syncNow() {
        val Outstat = 3
        val syncTIMESTAMP = System.currentTimeMillis()
        val dateobj = Date(syncTIMESTAMP)
        val df = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH)
        val StampEndsTime = df.format(dateobj)

        dbAdapterKenya.open()
        val presentRoute: String = dbAdapterKenya.GetActiveRouteID()
        dbAdapterKenya.close()

        val df1 = SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss", Locale.ENGLISH)
        val newfullFileName = Utils.getToken(context) + "." + presentRoute + "." + df1.format(dateobj)
        try {
            val OrderXMLFolder = File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder)
            if (!OrderXMLFolder.exists()) {
                OrderXMLFolder.mkdirs()
            }
            val routeID: String = dbAdapterKenya.GetActiveRouteIDSunil()

            val DASFA = MTASDatabaseAssistant(context)
            DASFA.open()
            DASFA.export(CommonInfo.DATABASE_NAME, newfullFileName, routeID)
            DASFA.close()


            dbAdapterKenya.deleteMTASXmlDataTable( "3");
            dbAdapterKenya.open()
            dbAdapterKenya.savetbl_MTASXMLfiles(newfullFileName, "3", "0")
            dbAdapterKenya.close()
//            CommonInfo.CurrentRecordFileName = ""
        } catch (ex: java.lang.Exception) {
            Toast.makeText(context,"Syncing issues",Toast.LENGTH_SHORT).show()
            Crashlytics.logException(ex)
            ex.printStackTrace()
        }
    }

    private fun saveCallLogsToDB(phoneNumber: String, callDuration: String, dateTime: String, callPickedDateTime: String) {
        val flgCallPicked = if (callDuration != "0") {
            1
        } else {
            0
        }
        var fileName = "NA"
        var flgIsExisting: Int = 0*/
/*= dbAdapterKenya.checkWhetherFileExistingInTbl(recordingFiles[0].name);*//*

        if (CommonInfo.FlgRecording == 1) {
            val path = "${Environment.getExternalStorageDirectory().path}/${CommonInfo.CallRecordingFolder}"
            val recordingFileDir: File = File(path)
            val recordingFiles = recordingFileDir.listFiles()
            if (recordingFiles != null) {
                for (audioItems in recordingFiles) {
                    if (audioItems.name.split("_").toTypedArray()[0] == CommonInfo.CurrentRecordFileName) {
                        flgIsExisting = 1;
                        fileName = audioItems.name
                        break
                    }
                }
            }
        }

        var flgRecording = CommonInfo.FlgRecording

        if (CommonInfo.FlgRecording == 1){
            flgRecording = flgIsExisting
        }
        val debugState = dbAdapterKenya.getDebugState(CommonInfo.CurrentTime);
        val tblMTASCallLogs = TblMTASCallLogs(
                phoneNumber,
                dateTime,
                callDuration,
                callPickedDateTime,
                flgCallPicked,
                fileName,
                Utils.getToken(context),
                flgRecording,
                CommonInfo.StoreIdForRecording,
                3,
                CommonInfo.TeleCallingIdForRecording,
                4,
                "${debugState.debugState}, done",
                CommonInfo.CurrentTime,
                debugState.callEndedDateTime
        )
        Toast.makeText(context,tblMTASCallLogs.toString(),Toast.LENGTH_SHORT).show()
        Log.e("tblMTASCallLogs", tblMTASCallLogs.toString())
        dbAdapterKenya.updateCallLogs(tblMTASCallLogs)

        CommonInfo.ContactNumber = ""
        CommonInfo.FlgRecording = 0
        CommonInfo.TeleCallingIdForRecording = 0
        CommonInfo.StoreIdForRecording = "NA"
        CommonInfo.CurrentRecordFileName = ""
        bgTasker().execute()
    }

    companion object {
        var phoneRinging = false
        var offHookCalled = false
        var disconnected = false
    }

    inner class bgTasker : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            try {
                syncNow()
            } catch (e: Exception) {
                Toast.makeText(context,"bg tasker issues",Toast.LENGTH_SHORT).show()
                Crashlytics.logException(e)
                val esdsd = e.message
            } finally {
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            if (!AppUtils.isMyServiceRunning(UploadRecordingsService::class.java, context))
                context.startService(Intent(context, UploadRecordingsService::class.java))
        }
    }

}*/
