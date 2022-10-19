package com.atilsamancioglu.appmatedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.atilsamancioglu.appmatedemo.databinding.FragmentFirstBinding
import com.huawei.appmate.PurchaseClient
import com.huawei.appmate.callback.ReceivedDataListener
import com.huawei.appmate.model.GenericError
import com.huawei.appmate.model.Product
import com.huawei.appmate.model.PurchaseInfo

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    var adapter : RecyclerAdapter? = null
    var currentPurchased : PurchaseInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        PurchaseClient.instance.getProducts(object :
            ReceivedDataListener<List<Product>, GenericError> {

            override fun onSucceeded(data: List<Product>) {
                println(data)

                adapter = RecyclerAdapter(ArrayList(data))
                binding.recyclerView.adapter = adapter
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            }

            override fun onError(error: GenericError) {
                println(error.errorMessage)
            }


        })

        getPurchasesAndDisplay()


        binding.firstFragmentConsumeButton.setOnClickListener {
            currentPurchased?.let {
                PurchaseClient.instance.consumePurchase(it.purchaseToken,
                    object : ReceivedDataListener<String, GenericError> {
                        override fun onSucceeded(data: String) {
                            println("on succeed" + data)
                        }

                        override fun onError(error: GenericError) {
                        }
                    }
                )

            }
        }
    }

    private fun getPurchasesAndDisplay() {
        PurchaseClient.instance.getPurchases(
            object : ReceivedDataListener<List<PurchaseInfo>, GenericError> {
                override fun onSucceeded(data: List<PurchaseInfo>) {
                    currentPurchased = data.first()
                    val productIds = data.map { data -> data.productId }
                    PurchaseClient.instance.getProductsByProductIdList(productIds, object : ReceivedDataListener<List<Product>, GenericError> {
                        override fun onSucceeded(data: List<Product>) {
                            val product = data.first()
                            binding.firstFragmentPurchasedProductText.text = product.productLocales.first().productName
                        }

                        override fun onError(error: GenericError) {
                        }
                    })
                }

                override fun onError(error: GenericError) {
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}