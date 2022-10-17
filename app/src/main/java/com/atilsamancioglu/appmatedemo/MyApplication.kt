package com.atilsamancioglu.appmatedemo

import android.app.Application
import com.huawei.appmate.PurchaseClient

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        PurchaseClient.getInstance(this, "rMeKYzCsRUCYQsx8P9UzpA")
    }
}
