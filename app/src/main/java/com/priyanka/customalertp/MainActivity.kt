package com.priyanka.customalertp

import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.priyanka.alertsp.CusomAlertsP

class MainActivity : AppCompatActivity() {
    var b1: Button? = null
    var b2: Button? = null
    var b3: Button? = null
    var b4: Button? = null
    var b5: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        b1 = findViewById(R.id.Sucess)
        b2 = findViewById(R.id.Error)
        b3 = findViewById(R.id.Warrning)
        b4 = findViewById(R.id.Question)
        b5 = findViewById(R.id.Progress)
        b1?.setOnClickListener(View.OnClickListener { view: View? ->
            val cusomAlertsP =
                CusomAlertsP(this@MainActivity, CusomAlertsP.SUCCESS_TYPE)
            cusomAlertsP.setTitleText("Success")
            val message = "Thank you very much for your valuable feedback."
            if (message != null && !message.isEmpty()) {
                cusomAlertsP.setContentText(message)
            }
            cusomAlertsP.setConfirmButtonText("OK")
            cusomAlertsP.setConfirmClickListener(CusomAlertsP.onCusomAlertsPClickListener { sweetAlertDialog: CusomAlertsP -> sweetAlertDialog.dismissWithAnimation() })
            cusomAlertsP.show()
        })
        b2?.setOnClickListener(View.OnClickListener { view: View? ->
            val cusomAlertsP =
                CusomAlertsP(this@MainActivity, CusomAlertsP.ERROR_TYPE)
            cusomAlertsP.setTitleText("Error")
            val message = "Incorrect Password"
            if (message != null && !message.isEmpty()) {
                cusomAlertsP.setContentText(message)
            }
            cusomAlertsP.setConfirmButtonText("OK")
            cusomAlertsP.setConfirmClickListener(CusomAlertsP.onCusomAlertsPClickListener { customAlertsP: CusomAlertsP -> customAlertsP.dismissWithAnimation() })
            cusomAlertsP.show()
        })

        b3?.setOnClickListener(View.OnClickListener {
            val customAlertsP = 
                CusomAlertsP(this@MainActivity,CusomAlertsP.WARNING_TYPE)
            customAlertsP.setTitleText("Warrning!")
            val message = "this should not to be done!!!!"
            customAlertsP.setContentText(message)
            customAlertsP.setConfirmButtonText("OK")
            customAlertsP.setCancelButtonText("CANCEL")
            customAlertsP.setCancelClickListener { customAlertsP : CusomAlertsP -> customAlertsP.dismissWithAnimation() }
            customAlertsP.setConfirmClickListener { customAlertsP : CusomAlertsP -> customAlertsP.dismissWithAnimation() }
            customAlertsP.show()
        })

        b4?.setOnClickListener(View.OnClickListener {
            val cusomAlertsP = CusomAlertsP(this@MainActivity,CusomAlertsP.TYPE_QUESTION)
            cusomAlertsP.setTitleText("Wana save world??")
            cusomAlertsP.setContentText("Save Planet save world")
            cusomAlertsP.setCancelClickListener { cusomAlertsP : CusomAlertsP -> cusomAlertsP.dismissWithAnimation() }
            cusomAlertsP.setConfirmClickListener { customAlertsP : CusomAlertsP -> customAlertsP.dismissWithAnimation() }
            cusomAlertsP.show()
        })

        b5?.setOnClickListener(View.OnClickListener {
            val cusomAlertsP = CusomAlertsP(this@MainActivity,CusomAlertsP.TYPE_LOADING)
            cusomAlertsP.setTitleText("Loading")
            cusomAlertsP.show()
        })
    }
}