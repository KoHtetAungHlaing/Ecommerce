package com.htetaunghlaing.mm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.htetaunghlaing.mm.adapter.CategoryAdapter
import com.htetaunghlaing.mm.model.Category
import com.htetaunghlaing.mm.services.ServiceBuilder
import com.htetaunghlaing.mm.services.WebService
import com.htetaunghlaing.mm.tOast.H
import com.htetaunghlaing.mm.tOast.H.Companion.USER_TOKEN
import com.htetaunghlaing.mm.tOast.H.Companion.l
import kotlinx.android.synthetic.main.activity_category.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        LoadAllCats()

        if (H.CheckUserAuth()) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        categoryRecycler.layoutManager = GridLayoutManager(this, 2)
    }

    private fun LoadAllCats() {
        val service = ServiceBuilder.buildService(WebService::class.java)
        val responseCats: Call<List<Category>> = service.getAllCat("Bearer $USER_TOKEN")

        responseCats.enqueue(object : Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                l(t.message!!)
            }

            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    val cats: List<Category> = response.body()!!
                    categoryRecycler.adapter = CategoryAdapter(this@CategoryActivity, cats)
                } else {
                    toast("Category Error")
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item:MenuItem=menu.findItem(R.id.cart)
        MenuItemCompat.setActionView(item,R.layout.my_cart)
        return super.onCreateOptionsMenu(menu)
    }
}
