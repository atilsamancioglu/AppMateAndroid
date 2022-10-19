package com.atilsamancioglu.appmatedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.atilsamancioglu.appmatedemo.databinding.FragmentBuyBinding
import com.huawei.appmate.PurchaseClient
import com.huawei.appmate.callback.PurchaseResultListener
import com.huawei.appmate.callback.ReceivedDataListener
import com.huawei.appmate.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Error


class BuyFragment : Fragment() {

    private var _binding: FragmentBuyBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    var selectedProductId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBuyBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            selectedProductId = BuyFragmentArgs.fromBundle(it).productID

            PurchaseClient.instance.getProductsByProductIdList(listOf(selectedProductId), object : ReceivedDataListener<List<Product>, GenericError> {
                override fun onSucceeded(data: List<Product>) {
                    val product = data.first()
                    lifecycleScope.launch(context = Dispatchers.Main) {
                        binding.buyFragmentProductNameText.text= product.productLocales.first().productName
                        binding.buyFragmentDescriptionText.text= product.productLocales.first().productDesc
                    }
                }

                override fun onError(error: GenericError) {
                }
            })
        }

        binding.buyFragmentBuyButton.setOnClickListener {
            PurchaseClient.instance.purchase(
                activity = requireActivity(),
                purchaseRequest = PurchaseRequest(selectedProductId, ProductType.CONSUMABLE),
                listener = object : PurchaseResultListener<PurchaseResultInfo, GenericError> {
                    override fun onSuccess(data: PurchaseResultInfo) {
                        println("success")
                    }

                    override fun onError(error: GenericError) {
                    }

                    override fun onQueryPurchasesResponse(
                        p0: BillingResult,
                        p1: MutableList<Purchase>
                    ) {

                    }

                })
        }

    }

}