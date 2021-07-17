package com.example.aj.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aj.R
import com.example.aj.helper.Helper
import com.example.aj.model.Bank
import com.example.aj.model.rajaongkir.Costs
import com.example.aj.model.rajaongkir.Result
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class AdapterBank(var data: ArrayList<Bank>, var listener : Listeners) : RecyclerView.Adapter<AdapterBank.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama = view.findViewById<TextView>(R.id.tv_nama)
        val layout = view.findViewById<LinearLayout>(R.id.layout)
        val image = view.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val a = data[position]
        holder.tvNama.text = a.nama
        holder.image.setImageResource(a.image)
        holder.layout.setOnClickListener{
            listener.onClicked(a, holder.adapterPosition)
        }
    }

    interface Listeners {
        fun onClicked(data: Bank, index: Int)
    }

}