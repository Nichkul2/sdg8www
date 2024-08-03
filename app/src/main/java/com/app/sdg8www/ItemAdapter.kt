package com.app.sdg8www

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ItemAdapter(context: Context, data: ArrayList<String>) :
    ArrayAdapter<String?>(context, R.layout.item_list, data as List<String?>) {
    var mContext: Context
    private val mCaseList: List<String>

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
        txId.text = (position+1).toString()
        txName.text = itemCase
        if ("Teacher/Educator" == itemCase){
            txAddress.text = "Provide education and training to develop a skilled workforce that contributes to economic growth and sustainable development"
        } else if ("Business Analyst" == itemCase){
            txAddress.text = "Analyze business processes and recommend improvements to drive economic growth and job creation"
        } else if ("Software Developer" == itemCase){
            txAddress.text = "Design, develop, and maintain software applications that enhance business productivity and promote economic growth"
        } else if ("Human Resources Manager" == itemCase){
            txAddress.text = "Oversee recruitment and employee development programs to ensure fair and productive employment practices"
        } else if ("Sustainability Consultant" == itemCase){
            txAddress.text = "Advise organizations on sustainable practices that promote economic growth while minimizing environmental impact"
        } else if ("Social Worker" == itemCase){
            txAddress.text = "Support individuals and communities in overcoming barriers to employment and economic participation"
        }

        view.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("name", itemCase)
            intent.putExtra("description", txAddress.text.toString())
            mContext.startActivity(intent)
            (mContext as Activity).finish()

        })
        return view
    }

}
