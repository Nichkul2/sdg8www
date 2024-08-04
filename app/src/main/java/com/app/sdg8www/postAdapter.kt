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
class PostAdapter(context: Context, data: ArrayList<Post>) :
    ArrayAdapter<Post>(context, R.layout.post_list, data) {

    // mCaseList는 어댑터가 처리할 Item 객체 목록을 보관합니다.
    private val mCaseList: List<Post> = data
    private val mContext: Context = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val post = mCaseList[position]

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.post_list, parent, false)
        }

        val txUser = view!!.findViewById<TextView>(R.id.user_name)
        val txDate = view.findViewById<TextView>(R.id.date)
        val txContext = view.findViewById<TextView>(R.id.context)
        val txHeartCnt = view.findViewById<TextView>(R.id.heart_cnt)

        // 각 항목의 ID, 이름 및 설명을 설정합니다.
        txUser.text = post.author
        txDate.text = post.date
        txContext.text = post.content
        txHeartCnt.text = post.likeCount.toString()
        return view
    }

}