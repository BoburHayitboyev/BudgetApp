package com.example.budgettrackerapp

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.budgettrackerapp.databinding.ItemaBudgetBinding

class BudgetAdapter(var data: List<Budget>,var onClick: OnItemClickListener) :
    RecyclerView.Adapter<BudgetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemaBudgetBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.title.text = data[position].title
        holder.binding.sana.text = data[position].sana

        if (data[position].imageID == ("minus")){
            holder.binding.money.text = "-"+data[position].price+" so'm"
            holder.binding.imgLogo.setImageResource(R.drawable.ic_doira_red)
            holder.binding.money.setTextColor(Color.RED)
        }else{
            holder.binding.money.text = data[position].price+" so'm"
            holder.binding.imgLogo.setImageResource(R.drawable.ic_doira)
        }

        holder.binding.root.setOnClickListener {
            onClick.onCLick(budget = data[position])
        }


        Log.d("BudgetAdapter_ish",data[position].title)
    }


    inner class ViewHolder(val binding: ItemaBudgetBinding) :
        RecyclerView.ViewHolder(binding.root)

}