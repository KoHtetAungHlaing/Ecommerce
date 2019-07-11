package com.htetaunghlaing.mm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.htetaunghlaing.mm.adapter.CartAdapter
import com.htetaunghlaing.mm.model.CartProduct
import com.htetaunghlaing.mm.model.ErrorMessage
import com.htetaunghlaing.mm.model.Products
import com.htetaunghlaing.mm.services.ServiceBuilder
import com.htetaunghlaing.mm.services.WebService
import com.htetaunghlaing.mm.tOast.H
import com.htetaunghlaing.mm.tOast.H.Companion.USER_TOKEN
import com.htetaunghlaing.mm.tOast.H.Companion.l
import kotlinx.android.synthetic.main.activity_my_cart.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCartActivity : AppCompatActivity() {
    var cartCount: TextView? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)

        supportActionBar?.title = "Cart Items"
        val cart_keys = H.getAllKey()
        getCartItems(cart_keys)
        cartRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.bill_out) {
            BillOut()
        }else if(item.itemId==R.id.upload_product){
            startActivity(Intent(this@MyCartActivity,UploadProductActivity::class.java))

        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCartItems(cart_keys: String) {
        val services: WebService = ServiceBuilder.buildService(WebService::class.java)
        val responseCartProduct: Call<List<CartProduct>> = services.getCartPreviewItems("Bearer $USER_TOKEN", cart_keys)

        responseCartProduct.enqueue(object : Callback<List<CartProduct>> {
            override fun onFailure(call: Call<List<CartProduct>>, t: Throwable) {
                H.l(t.message!!)
            }

            override fun onResponse(call: Call<List<CartProduct>>, response: Response<List<CartProduct>>) {
                if (response.isSuccessful) {
                    val products: List<CartProduct> = response.body()!!
                    cartRecycler.adapter = CartAdapter(this@MyCartActivity, products)
                } else {
                    H.l("Something went wrong!")
                }
            }

        })
    }
}

private fun BillOut(){
    val cart_keys = H.getAllKey()

    val servic: WebService = ServiceBuilder.buildService(WebService::class.java)
    val responseCart: Call<ErrorMessage> = servic.BillOutOrder("Bearer $USER_TOKEN", cart_keys)
    responseCart.enqueue(object : Callback<ErrorMessage>{
        override fun onFailure(call: Call<ErrorMessage>, t: Throwable) {
            H.l(t.message.toString())
        }

        override fun onResponse(call: Call<ErrorMessage>, response: Response<ErrorMessage>) {
            if(response.isSuccessful){
                val message:ErrorMessage= response.body()!!
                Log.i("me",message.msg)
                H.clearCart()
            }else{
                H.l("Something wrong!")
            }
        }

    })
}
