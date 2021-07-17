package com.example.aj.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.aj.MainActivity
import com.example.aj.R
import com.example.aj.adapter.AdapterProduk
import com.example.aj.adapter.AdapterSlider
import com.example.aj.app.ApiConfig
import com.example.aj.model.Produk
import com.example.aj.model.ResponModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    lateinit var vpslider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvProdukTerlaris: RecyclerView
    lateinit var rvNonObat: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        getProduk()

        return view
    }

    fun displayProduk(){
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.slider7)
        arrSlider.add(R.drawable.slider8)
        arrSlider.add(R.drawable.slider9)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpslider.adapter = adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager2 = LinearLayoutManager(activity)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManager3 = LinearLayoutManager(activity)
        layoutManager3.orientation = LinearLayoutManager.HORIZONTAL

        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManager

        rvProdukTerlaris.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProdukTerlaris.layoutManager = layoutManager2
/*
        rvNonObat.adapter = AdapterProduk(requireActivity(), listProduk)
        rvNonObat.layoutManager = layoutManager3*/
    }

    private var listProduk:ArrayList<Produk> = ArrayList()
    fun getProduk(){
        ApiConfig.instanceRetrofit.getProduk().enqueue(object  : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }
            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1){
                    listProduk = res.produks
                    displayProduk()
                }
            }

        })
    }

    fun init(view: View){
        vpslider = view.findViewById(R.id.vp_slider)
        rvProduk = view.findViewById(R.id.rv_produk)
        rvProdukTerlaris = view.findViewById(R.id.rv_produkTerlaris)
       /* rvNonObat = view.findViewById(R.id.rv_nonobat)*/
    }

/*    val arrProduk: ArrayList<Produk>get() {
        val arr = ArrayList<Produk>()
        val p1 = Produk()
        p1.name = "Promag"
        p1.harga = "10.000"
        p1.gambar = R.drawable.promag

        val p2 = Produk()
        p2.nama = "Bodrex"
        p2.harga = "5.000"
        p2.gambar = R.drawable.bodrek

        val p3 = Produk()
        p3.nama = "Herocyn"
        p3.harga = "25.000"
        p3.gambar = R.drawable.herocyn

        arr.add(p1)
        arr.add(p2)
        arr.add(p3)

        return arr
    }

    val arrProdukTerlaris: ArrayList<Produk>get() {
        val arr = ArrayList<Produk>()
        val p1 = Produk()
        p1.nama = "Promag"
        p1.harga = "10.000"
        p1.gambar = R.drawable.promag

        val p2 = Produk()
        p2.nama = "Bodrex"
        p2.harga = "5.000"
        p2.gambar = R.drawable.bodrek

        val p3 = Produk()
        p3.nama = "Herocyn"
        p3.harga = "25.000"
        p3.gambar = R.drawable.herocyn

        arr.add(p1)
        arr.add(p2)
        arr.add(p3)

        return arr
    }

    val arrNonObat: ArrayList<Produk>get() {
        val arr = ArrayList<Produk>()
        val p1 = Produk()
        p1.nama = "Promag"
        p1.harga = "10.000"
        p1.gambar = R.drawable.promag

        val p2 = Produk()
        p2.nama = "Bodrex"
        p2.harga = "5.000"
        p2.gambar = R.drawable.bodrek

        val p3 = Produk()
        p3.nama = "Herocyn"
        p3.harga = "25.000"
        p3.gambar = R.drawable.herocyn

        arr.add(p1)
        arr.add(p2)
        arr.add(p3)

        return arr
    }*/

}