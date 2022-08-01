package com.astix.rajtraderssfasales.mTASModule
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Environment
import android.os.Handler
import android.provider.CallLog
import android.telephony.TelephonyManager
import android.util.Log
import com.astix.Common.CommonInfo
import java.text.SimpleDateFormat
import java.util.*

/*

class ServiceReceiver : BroadcastReceiver() {
    var telephony: TelephonyManager? = null
    lateinit var context: Context
    private lateinit var dbAdapterKenya: DBAdapterKenya
    private var phoneStateListenerRegisteredFlg: Int = 0
    private lateinit var callDisconnectedDateTime : String;
//    private lateinit var number : String;

    override fun onReceive(context: Context, intent: Intent) {
        this@ServiceReceiver.context = context
        dbAdapterKenya = DBAdapterKenya(context)
        */
/*telephony!!.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                super.onCallStateChanged(state, incomingNumber)
                println("incomingNumber : $incomingNumber")
                number = incomingNumber;
            }
        }, PhoneStateListener.LISTEN_CALL_STATE)*//*

        */
/*   val phoneListener = MyPhoneStateListener(context)
           if(phoneStateListenerRegisteredFlg == 0){
               telephony = context
                   .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
               telephony!!.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE)
               phoneStateListenerRegisteredFlg = 1
           }*//*

        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        var number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
        Log.d("MPR", "number [$number]")
        */
/*  if (state == null) {
              //Outgoing call
              number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
              Log.i("tag", "Outgoing number : $number")
          }*//*

        if (number == null)
            return
        if (TelephonyManager.EXTRA_STATE_RINGING == state) {
            Log.d("MPR", "Its Ringing [$number]")
        }
        if (TelephonyManager.EXTRA_STATE_IDLE == state && CommonInfo.ContactNumber == number) {
            Log.d("MPR", "Its Idle")
//            Toast.makeText(context, "Its Idle", Toast.LENGTH_SHORT).show()

            offHookCalled = false
            if (!disconnected) {
                disconnected = true
                val tblMTASCall = dbAdapterKenya.getDebugState(CommonInfo.CurrentTime)
                tblMTASCall.state = 3
                tblMTASCall.debugState = tblMTASCall.debugState + ",IDLE"
                tblMTASCall.callEndedDateTime = TimeUtils.getNetworkDateTime(context, TimeUtils.DATE_TIME_FORMAT)

                Log.e("tblMTASCallLogs", tblMTASCall.toString())
                dbAdapterKenya.updateCallLogs(tblMTASCall)
            } else {
                return
            }

            if (CommonInfo.ContactNumber == number) {

                try {
                    Handler().postDelayed({
                        if (CommonInfo.FlgRecording == 1)
                            AppUtils.stopCallRecord()

                        getCallDetails()
                    }, 1000)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    Crashlytics.logException(ex)
//                    Toast.makeText(context,"call logs issues", Toast.LENGTH_SHORT).show()
                }

                disconnected = false
            }
        }
        if (TelephonyManager.EXTRA_STATE_OFFHOOK == state && CommonInfo.ContactNumber == number) {
            Log.d("MPR", "Its OffHook")
//            Toast.makeText(context, "Its OffHook", Toast.LENGTH_SHORT).show()
            if (!offHookCalled) {
                offHookCalled = true
                val tblMTASCall = dbAdapterKenya.getDebugState(CommonInfo.CurrentTime)
                tblMTASCall.state = 2
                tblMTASCall.debugState = tblMTASCall.debugState + ",offHook"

                Log.e("tblMTASCallLogs", tblMTASCall.toString())
                dbAdapterKenya.updateCallLogs(tblMTASCall)
                println("not return")
            } else {
                println("return")
                return
            }

            if (CommonInfo.ContactNumber == number && offHookCalled) {

                val intent: Intent = Intent(context, MTASWebActivity::class.java)
//                Toast.makeText(context,"Swapping",Toast.LENGTH_SHORT).show()
                //                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }

    private fun getCallDetails() {
        try {
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
//                   Toast.makeText(context, "$sb", Toast.LENGTH_SHORT).show()
                    Log.d("Call Logs", "$sb")
                    saveCallLogsToDB(phNumber, callDuration, dateTime, callPickedTiming)
                    break
                }
            }
            cur.close()
        } catch (ex: java.lang.Exception) {
            Crashlytics.logException(ex)
            resetFlags()
        } finally {

        }
    }

    companion object {
        var phoneRinging = false
        var offHookCalled = false
        var disconnected = false
    }

    private fun resetFlags() {
        CommonInfo.ContactNumber = ""
        CommonInfo.FlgRecording = 0
        CommonInfo.TeleCallingIdForRecording = 0
        CommonInfo.StoreIdForRecording = "NA"
        CommonInfo.CurrentRecordFileName = ""
        CommonInfo.CurrentTime = "NA"
    }

    private fun saveCallLogsToDB(phoneNumber: String, callDuration: String, dateTime: String, callPickedDateTime: String) {
        try {
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
                            flgIsExisting = 1
                            fileName = audioItems.name
                            break
                        }
                    }
                }
            }

            var flgRecording = CommonInfo.FlgRecording

            if (CommonInfo.FlgRecording == 1) {
                flgRecording = flgIsExisting
            }

            val tblMTASCallLogs1 = dbAdapterKenya.getDebugState(CommonInfo.CurrentTime)
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
                    "${tblMTASCallLogs1.debugState}, done",
                    CommonInfo.CurrentTime,
                    tblMTASCallLogs1.callEndedDateTime

            )
//            Toast.makeText(context, tblMTASCallLogs.toString(), Toast.LENGTH_SHORT).show()
            Log.e("tblMTASCallLogs", tblMTASCallLogs.toString())
            dbAdapterKenya.updateCallLogs(tblMTASCallLogs)

            bgTasker().execute()
        } catch (ex: Exception) {
            Crashlytics.logException(ex)
        } finally {
            resetFlags()
        }

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


            dbAdapterKenya.deleteMTASXmlDataTable("3")
            dbAdapterKenya.open()
            dbAdapterKenya.savetbl_MTASXMLfiles(newfullFileName, "3", "0")
            dbAdapterKenya.close()
//            CommonInfo.CurrentRecordFileName = ""
        } catch (ex: java.lang.Exception) {
//            Toast.makeText(context,"Syncing issues",Toast.LENGTH_SHORT).show()
            Crashlytics.logException(ex)
            ex.printStackTrace()
        }
    }

    inner class bgTasker : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void): Void? {
            try {
                syncNow()
            } catch (e: Exception) {
//                Toast.makeText(context,"bg tasker issues",Toast.LENGTH_SHORT).show()
                Crashlytics.logException(e)
                val esdsd = e.message
            } finally {
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            try {
                if (!AppUtils.isMyServiceRunning(UploadRecordingsService::class.java, context))
                    context.startService(Intent(context, UploadRecordingsService::class.java))
            }catch (ex : Exception){
                Crashlytics.logException(ex)
            }

        }
    }

    fun onDestroy() {
//        telephony!!.listen(null, PhoneStateListener.LISTEN_NONE)
        AppUtils.stopCallRecord()
    }
}
*/
