package com.atilsamancioglu.appmatedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.atilsamancioglu.appmatedemo.databinding.RecyclerRowBinding
import com.huawei.appmate.model.Product

class ProductRecyclerAdapter(private val productList : List<Product>) : RecyclerView.Adapter<ProductRecyclerAdapter.ProductHolder>() {
    class ProductHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.binding.recyclerNameText.text = productList.get(position).productLocales.first().productName
        holder.binding.recyclerPriceText.text = productList.get(position).price

        holder.itemView.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToBuyFragment(productList.get(position).productId)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}
