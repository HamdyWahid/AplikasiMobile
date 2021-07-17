package com.example.aj.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aj.R
import com.example.aj.adapter.AdapterProdukTransaksi
import com.example.aj.adapter.AdapterRiwayat
import com.example.aj.app.ApiConfig
import com.example.aj.helper.Helper
import com.example.aj.model.DetailTransaksi
import com.example.aj.model.ResponModel
import com.example.aj.model.Transaksi
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTransaksiActivity : AppCompatActivity() {

    var transaksi = Transaksi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        Helper().setToolbar(this, toolbar, "Detail Transaksi")

        val json = intent.getStringExtra("transaksi")
        transaksi = Gson().fromJson(json, Transaksi::class.java)

        setData(transaksi)
        displayProduk(transaksi.details)
        mainButton()
    }

    fun mainButton(){
        btn_batal.setOnClickListener {
            batalTransaksi()
        }
    }

    fun batalTransaksi(){
        ApiConfig.instanceRetrofit.batalchekout(transaksi.id).enqueue(object  : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1){
                    Toast.makeText(this@DetailTransaksiActivity, "Transaksi di-Batalkan", Toast.LENGTH_SHORT).show()
                    onBackPressed()
 //                   displayRiwayat(res.transaksis)
                }
            }
        })
    }

    fun setData(t: Transaksi) {
        tv_status.text = t.status
        tv_tgl.text = t.created_at
        tv_penerima.text = t.name+" - "+t.phone
        tv_alamat.text = t.detail_lokasi
        tv_kodeUnik.text = Helper().gantiRupiah(t.kode_unik)
        tv_totalBelanja.text = Helper().gantiRupiah(t.total_harga)
        tv_ongkir.text = Helper().gantiRupiah(t.ongkir)
        tv_total.text = Helper().gantiRupiah(t.total_transfer)

        if (t.status !="MENUNGGU") div_footer.visibility = View.GONE

        var color = getColor(R.color.menungu)
        if (t.status == "SELESAI") color = getColor(R.color.selesai)
        else if (t.status == "BATAL") color = getColor(R.color.batal)

        tv_status.setTextColor(color)
    }

    fun displayProduk(transaksis: ArrayList<DetailTransaksi>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_produk.adapter = AdapterProdukTransaksi(transaksis)
        rv_produk.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}