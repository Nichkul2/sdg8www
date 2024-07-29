package com.app.sdg8www

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ItemAdapter(context: Context, data: ArrayList<Item>) :
    ArrayAdapter<Item?>(context, R.layout.item_list, data as List<Item?>) {
    var mContext: Context
    private val mCaseList: List<Item>

    init {
        mCaseList = data
        mContext = context
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        val itemCase = mCaseList[position]
        val inflater = LayoutInflater.from(context)
        view = inflater.inflate(R.layout.item_list, parent, false)
        val txId = view.findViewById<TextView>(R.id.tx_id)
        val txName = view.findViewById<TextView>(R.id.tx_name)
        val txAddress = view.findViewById<TextView>(R.id.tx_description)
        txId.text = itemCase.id
        txName.text = itemCase.name
        txAddress.text = itemCase.description
        view.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("name", itemCase.name)
            intent.putExtra("description", itemCase.description)
            mContext.startActivity(intent)
            (mContext as Activity).finish()

        })
        return view
    }

}