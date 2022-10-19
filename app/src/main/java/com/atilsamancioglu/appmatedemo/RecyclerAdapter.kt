package com.atilsamancioglu.appmatedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.atilsamancioglu.appmatedemo.databinding.RecyclerRowBinding
import com.huawei.appmate.model.Product

class RecyclerAdapter(val productList : ArrayList<Product>) : RecyclerView.Adapter<RecyclerAdapter.LandmarkHolder>() {
    class LandmarkHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LandmarkHolder(binding)
    }

    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {
        holder.binding.recyclerTextView.text = productList.get(position).productLocales.first().productName
        holder.itemView.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToBuyFragment(productList.get(position).productId)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}
