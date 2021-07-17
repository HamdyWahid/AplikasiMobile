package com.example.aj.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.aj.MainActivity
import com.example.aj.R
import com.example.aj.app.ApiConfig
import com.example.aj.helper.SharedPref
import com.example.aj.model.ResponModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        s = SharedPref(this)

        btn_login.setOnClickListener {
            login()
        }

    }
    fun login() {
        if (edt_email.text.isEmpty()){
            edt_email.error = "Kolom Email Tidak Boleh Kosong"
            edt_email.requestFocus()
            return
        }else if (edt_password.text.isEmpty()){
            edt_password.error = "Password Tidak Boleh Kosong"
            edt_password.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(edt_email.text.toString(), edt_password.text.toString()).enqueue(object  : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "Error" + t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb.visibility = View.GONE
                val respon = response.body()!!
                if (response.body()!!.success == 1) {
                   s.setStatusLogin(true)
                    s.setUser(respon.user)
  //                  s.setString(s.nama, respon.user.name)
    //                s.setString(s.phone, respon.user.phone)
      //              s.setString(s.email, respon.user.email)
                    val intent = Intent(this@LoginActivity,  MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@LoginActivity, "Selamat Datang " + respon.user.name, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Error : " + respon.message, Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
}