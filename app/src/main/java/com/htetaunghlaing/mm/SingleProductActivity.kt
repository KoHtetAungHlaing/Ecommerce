package com.htetaunghlaing.mm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import com.htetaunghlaing.mm.model.Product
import com.htetaunghlaing.mm.tOast.H
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_single_product.*

class SingleProductActivity : AppCompatActivity() {

    var cartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_product)

        val product = intent.getParcelableExtra<Product>("product")

        productTitle.text = product.name
        Picasso.get().load(product.image).into(productImage)
        productPrice.text = product.price.toString()
        productDescription.text = product.description

        imgAddToCart.setOnClickListener {
            H.addToCart(product.price)
            cartCount?.text = H.getCartCount().toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item: MenuItem = menu.findItem(R.id.cart)
        MenuItemCompat.setActionView(item, R.layout.my_cart)

        val cartView: View = MenuItemCompat.getActionView(item)
        cartCount = cartView.findViewById(R.id.cart_count)
        val cartImage: ImageView = cartView.findViewById(R.id.cart_view)
        cartCount?.text = H.getCartCount().toString()
        cartImage.setOnClickListener {
            startActivity(Intent(this@SingleProductActivity, MyCartActivity::class.java))
        }
        return super.onCreateOptionsMenu(menu)
    }
}
