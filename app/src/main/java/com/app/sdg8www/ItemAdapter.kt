package com.app.sdg8www

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

// ItemAdapter는 Item 객체의 목록을 표시하기 위해 ArrayAdapter를 확장합니다.
class ItemAdapter(context: Context, data: ArrayList<Item>) :
    ArrayAdapter<Item>(context, R.layout.item_list, data) {

    // mCaseList는 어댑터가 처리할 Item 객체 목록을 보관합니다.
    private val mCaseList: List<Item> = data
    private val mContext: Context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val item = mCaseList[position]

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_list, parent, false)
        }

        val txId = view!!.findViewById<TextView>(R.id.tx_id)
        val txName = view.findViewById<TextView>(R.id.tx_name)
        val txAddress = view.findViewById<TextView>(R.id.tx_description)

        // 각 항목의 ID, 이름 및 설명을 설정합니다.
        txId.text = (position + 1).toString()
        txName.text = item.name
        txAddress.text = item.description

        // 항목을 클릭하면 DetailActivity로 이동합니다.
        view.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("name", item.name)
            intent.putExtra("description", item.description)
            intent.putExtra("id", item.id)
            mContext.startActivity(intent)
            (mContext as Activity).finish()
        }
        return view
    }
}
