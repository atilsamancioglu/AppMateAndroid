package com.atilsamancioglu.appmatedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.huawei.appmate.PurchaseClient
import com.huawei.appmate.callback.ReceivedDataListener
import com.huawei.appmate.model.GenericError
import com.huawei.appmate.model.Product

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}