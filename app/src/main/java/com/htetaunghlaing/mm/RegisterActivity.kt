package com.htetaunghlaing.mm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.htetaunghlaing.mm.model.Category
import com.htetaunghlaing.mm.model.Token
import com.htetaunghlaing.mm.services.ServiceBuilder
import com.htetaunghlaing.mm.services.WebService
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.title = "Register"

        btnRegister.setOnClickListener {
            val username = ret_username.text.toString()
            val email = ret_email.text.toString()
            val password = retpassword.text.toString()
        }
        btnRCancel.setOnClickListener {
            ret_username.text = null
            ret_email.text = null
            retpassword.text = null
        }
        tvRegister.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)

        }
    }

}