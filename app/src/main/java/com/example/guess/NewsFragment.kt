package com.example.guess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

// 最新消息的Fragment
// Fragment：區塊、片段，排解activity工作，讓一個activity中有許多fragment，各自有各自的生命週期
class NewsFragment : Fragment(){
    // 讓fragment只會永遠有一個，而不會每次呼叫都產生一個
    companion object{
        // 關鍵字by=讓instance不要一開始呼叫NewsFragment時就生出來，是當instance被呼叫時在執行lazy內動作
        val instance: NewsFragment by lazy {
            NewsFragment()
        }
    }

    override fun onCreateView(
        // 將xml資料充氣成View物件
        inflater: LayoutInflater,
        // 所在地方的容器
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 參數1指充氣目標xml，參數2指定秀出的layout之id，參數3指這fragment_news產生時是否要直接連接到container身上
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
}