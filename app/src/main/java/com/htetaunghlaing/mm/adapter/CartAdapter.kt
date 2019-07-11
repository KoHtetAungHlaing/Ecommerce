package com.htetaunghlaing.mm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htetaunghlaing.mm.R
import com.htetaunghlaing.mm.SingleProductActivity
import com.htetaunghlaing.mm.model.CartProduct
import com.htetaunghlaing.mm.model.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_product_row.view.*
import org.jetbrains.anko.toast

class CartAdapter(val context: Context, val pRoduct: List<CartProduct>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_row, parent, false))
    }

    override fun getItemCount(): Int {
        return pRoduct.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pro = pRoduct[position]
        holder.itemView.tvProductTitle.text="${pro.name} (${pro.count})"
        holder.itemView.tvProductPrice.text= pro.price.toString()
        Picasso.get().load(pro.image).into(holder.itemView.imgProductSingle)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}