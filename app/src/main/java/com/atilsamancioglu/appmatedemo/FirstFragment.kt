package com.atilsamancioglu.appmatedemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.atilsamancioglu.appmatedemo.databinding.FragmentFirstBinding
import com.huawei.appmate.PurchaseClient
import com.huawei.appmate.callback.ReceivedDataListener
import com.huawei.appmate.model.GenericError
import com.huawei.appmate.model.Product
import com.huawei.appmate.model.PurchaseInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
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

    override fun onResume() {
        super.onResume()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productList.layoutManager = LinearLayoutManager(requireContext())


        PurchaseClient.instance.getProducts(object :
            ReceivedDataListener<List<Product>, GenericError> {

            override fun onSucceeded(data: List<Product>) {
                lifecycleScope.launch(context = Dispatchers.Main) {
                    val adapter = ProductRecyclerAdapter(data)
                    binding.productList.adapter = adapter
                }
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
                            binding.firstFragmentPurchasedProductText.text = ""
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
                    println("get purchase çalıştı"+data)
                    currentPurchased = data.first()
                    val productIds = data.map { data -> data.productId }
                    PurchaseClient.instance.getProductsByProductIdList(productIds, object : ReceivedDataListener<List<Product>, GenericError> {
                        override fun onSucceeded(data: List<Product>) {
                            val product = data.first()
                            lifecycleScope.launch(context = Dispatchers.Main) {
                                binding.firstFragmentPurchasedProductText.text = product.productLocales.first().productName
                            }
                        }

                        override fun onError(error: GenericError) {
                            println(error.errorMessage)
                        }
                    })
                }

                override fun onError(error: GenericError) {
                    println(error.errorMessage)

                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}