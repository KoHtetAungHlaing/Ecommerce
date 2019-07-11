package com.htetaunghlaing.mm.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.htetaunghlaing.mm.R
import com.htetaunghlaing.mm.SingleCategoryProductActivity
import com.htetaunghlaing.mm.model.Category
import kotlinx.android.synthetic.main.category_row_item.view.*

class CategoryAdapter(val context: Context, val cat: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_item, parent, false))
    }

    override fun getItemCount(): Int {
        return cat.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cats = cat[position]
        holder.itemView.tvCategory.text = cats.name

        holder.itemView.tvCategory.setOnClickListener {
            Log.d("me",cats.id.toString())
            val intent = Intent(context, SingleCategoryProductActivity::class.java)
            intent.putExtra("cat_id",cats.id.toString())
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}