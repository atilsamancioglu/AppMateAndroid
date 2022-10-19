package com.atilsamancioglu.appmatedemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.huawei.appmate.PurchaseClient
import com.huawei.appmate.callback.ReceivedDataListener
import com.huawei.appmate.model.GenericError
import com.huawei.appmate.model.Product


class MyViewModel : ViewModel() {

    val myProducts = MutableLiveData<List<Product>>()

    init {
        getData()
    }

    private fun getData() {
        PurchaseClient.instance.getProducts(object :
            ReceivedDataListener<List<Product>, GenericError> {

            override fun onSucceeded(data: List<Product>) {
                println(data)

                myProducts.value = data

            }

            override fun onError(error: GenericError) {
                println(error.errorMessage)
            }


        })
    }

}