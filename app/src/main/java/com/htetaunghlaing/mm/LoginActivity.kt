package com.htetaunghlaing.mm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.htetaunghlaing.mm.model.Token
import com.htetaunghlaing.mm.services.ServiceBuilder
import com.htetaunghlaing.mm.services.WebService
import com.htetaunghlaing.mm.tOast.H.Companion.USER_TOKEN
import com.htetaunghlaing.mm.tOast.H.Companion.l
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = "login"

        tvLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email = let_email.text.toString()
            val password = letpassword.text.toString()
            LoginUser(email, password)
        }
        btnCancel.setOnClickListener {
            let_email.text = null
            letpassword.text = null
        }
    }

    private fun LoginUser(email: String, password: String) {
        val service: WebService = ServiceBuilder.buildService(WebService::class.java)
        val responceLogin: Call<Token> = service.loginUser(email, password)

        responceLogin.enqueue(object : Callback<Token> {
            override fun onFailure(call: Call<Token>, t: Throwable) {
                l(t.message!!)
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                val token: Token = response.body()!!
                USER_TOKEN = token.token
                Log.d("message",token.token)
                val intent= Intent(this@LoginActivity,CategoryActivity::class.java)
                startActivity(intent)

            }
        })
    }
}
