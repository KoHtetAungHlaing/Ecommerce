package com.htetaunghlaing.mm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.htetaunghlaing.mm.adapter.ProductAdapter
import com.htetaunghlaing.mm.model.Products
import com.htetaunghlaing.mm.services.ServiceBuilder
import com.htetaunghlaing.mm.services.WebService
import com.htetaunghlaing.mm.tOast.H
import com.htetaunghlaing.mm.tOast.H.Companion.USER_TOKEN
import com.htetaunghlaing.mm.tOast.H.Companion.l
import kotlinx.android.synthetic.main.activity_single_category_product.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleCategoryProductActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_category_product)


     /*   if (H.CheckUserAuth()) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }*/

        var bundle: Bundle = intent.extras!!
        var catId = bundle.getString("cat_id")
        toast(catId.toString())
        loadAllProductsOfACategory(catId!!)
        singleCatProductRecycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun loadAllProductsOfACategory(catId: String) {
        val service = ServiceBuilder.buildService(WebService::class.java)
        val responseProduct: Call<Products> = service.getProductOfACategory("Bearer $USER_TOKEN", catId)

        responseProduct.enqueue(object : Callback<Products> {
            override fun onFailure(call: Call<Products>, t: Throwable) {
                l(t.message!!)

            }

            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.isSuccessful) {
                    val prod: Products = response.body()!!
                    val prods=prod.products
                    singleCatProductRecycler.adapter=ProductAdapter(this@SingleCategoryProductActivity,prods)

                } else {
                    toast("OKOKOKOKOKOKOK")
                }
            }

        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item: MenuItem =menu.findItem(R.id.cart)
        MenuItemCompat.setActionView(item,R.layout.my_cart)
        return super.onCreateOptionsMenu(menu)
    }
}
