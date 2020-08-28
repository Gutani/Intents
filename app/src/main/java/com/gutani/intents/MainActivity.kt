package com.gutani.intents

import android.app.SearchManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.telephony.SmsManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listView = ListView(this)
        setContentView(listView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.intent_actions))
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            openIntentAtPosition(position)
        }
    }

    private fun openIntentAtPosition(position:Int){
        val uri: Uri
        val intent: Intent
        when(position){
            0 -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("www.google.com.br")
                openIntent(intent)
            }
            1 -> {
                if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 0)
                }else{
                    callNumber()
                }
            }
            2 -> {
                uri = Uri.parse("geo:${10},${50}")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }
            3 -> {
                intent = Intent(Intent.ACTION_VIEW).putExtra("sms_body","default content").setType("vnd.android-dir/mms-sms")
                startActivity(intent)
//                val smsManager = SmsManager.getDefault()
//                smsManager.sendTextMessage("smsto:6334542035",null, "Mensagem do SMS", Intent().action, intent)
            }
            4 -> {
                intent = Intent().setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, "Compartilhando via Internet").setType("text/plain")
                openIntent(intent)
            }
            5 -> {
                intent = Intent(AlarmClock.ACTION_SET_ALARM).putExtra(AlarmClock.EXTRA_MESSAGE, "Study!!!").putExtra(AlarmClock.EXTRA_HOUR, 19).putExtra(AlarmClock.EXTRA_MINUTES,0).putExtra(AlarmClock.EXTRA_SKIP_UI, true).putExtra(AlarmClock.EXTRA_DAYS, arrayListOf(
                    Calendar.MONDAY,
                    Calendar.WEDNESDAY,
                    Calendar.THURSDAY,
                    Calendar.TUESDAY,
                    Calendar.SUNDAY,
                    Calendar.FRIDAY,
                    Calendar.SATURDAY
                ))
                openIntent(intent)
            }
            6 -> {
                intent = Intent(Intent.ACTION_SEARCH).putExtra(SearchManager.QUERY, "Gustavo Antunes")
                openIntent(intent)
            }
            7  -> {
                intent = Intent(Settings.ACTION_SETTINGS)
                openIntent(intent)
            }
            8 -> {
                intent = Intent("gustavo.antunes.CUSTOM_ACTION")
                openIntent(intent)
            }
            9 -> {
                uri = Uri.parse("produto://iphone/novo")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }
            else ->
                finish()
        }

    }

    private fun callNumber(){
        val uri = Uri.parse( "tel:62981512995")
        val intent = Intent(Intent.ACTION_CALL, uri)
        openIntent(intent)
    }

    private fun sendMessage(){
        val uri = Uri.parse("smsto:6334542035")
        val intent = Intent(Intent.ACTION_SENDTO, uri).putExtra("SMS Body", "Mensagem do SMS")
        openIntent(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.first() == PackageManager.PERMISSION_GRANTED){
            callNumber()
        }
    }

    private fun openIntent(intent:Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, R.string.error_intent, Toast.LENGTH_SHORT).show()
        }
    }
}