package com.example.myshopeadmin.AllAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myshopeadmin.AllDataModel.womenProductsDataModel
import com.example.myshopeadmin.R

class WomenProductsAdapter(val womenProductsList: ArrayList<womenProductsDataModel>): RecyclerView.Adapter<WomenProductsAdapter.WomenProductsViewHolder>() {
    class WomenProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val womenImageView=itemView.findViewById<ImageView>(R.id.secondListImageView)
        val womenTittleTextView=itemView.findViewById<TextView>(R.id.secondListTittleTV)
        val womenDescriptionTextView=itemView.findViewById<TextView>(R.id.secondListTittleTV2)
        val womenDescription2TextView=itemView.findViewById<TextView>(R.id.secondListTittleTV3)
        val womenPriceTextView=itemView.findViewById<TextView>(R.id.secondListPriceTV)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WomenProductsViewHolder {
        val view=
            LayoutInflater.from(parent.context).inflate(R.layout.women_products_item_layout,parent,false)
        return WomenProductsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return womenProductsList.size
    }

    override fun onBindViewHolder(holder: WomenProductsViewHolder, position: Int) {
        val womenProducts=womenProductsList[position]

        Glide.with(holder.itemView.context).load(womenProducts.womenImage).into(holder.womenImageView)
        holder.womenTittleTextView.text=womenProducts.womenTittle
        holder.womenDescriptionTextView.text=womenProducts.womenDescription
        holder.womenDescription2TextView.text=womenProducts.womenDescription2
        holder.womenPriceTextView.text=womenProducts.womenProductPrice


    }
}