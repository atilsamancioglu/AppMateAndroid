package com.atilsamancioglu.appmatedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huawei.appmate.PurchaseClient
import com.huawei.appmate.callback.ReceivedDataListener
import com.huawei.appmate.model.GenericError
import com.huawei.appmate.model.Product

class FirstFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PurchaseClient.instance.getProducts(object :
            ReceivedDataListener<List<Product>, GenericError> {

            override fun onSucceeded(data: List<Product>) {
                println(data)
            }

            override fun onError(error: GenericError) {
                println(error.errorMessage)
            }


        })
    }
}