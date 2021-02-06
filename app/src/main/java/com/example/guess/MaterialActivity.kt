package com.example.guess

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {

    // 關鍵字lateinit指此變數晚一點才會給初始值，解決null問題
    private lateinit var viewModel: GuessViewModel

    val TAG = MaterialActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(findViewById(R.id.toolbar))

        // ViewModelProvider類別可呼叫ViewModel的類別；this指Activity，生出ViewModel給Activity使用
        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)

        // 呼叫要觀察的LiveData變數counter，若觀察到值有變動就執行data
        // this指Activity，參數2實作Observe(interface)，data指ViewModel的counter變動
        viewModel.counter.observe(this, Observer { data ->
            // 指layout裡的id：counter更改內容
            counter.setText(data.toString())
        })

        // 呼叫觀察LiveData變數result，若觀察到值有變動就執行Observer的result
        viewModel.result.observe(this, Observer { result ->
            var message = when (result){
                GameResult.BIGGER -> "Bigger"   // 改變提示框文字
                GameResult.SMALLER -> "Smaller"
                GameResult.NUMBER_RIGHT -> "Yes! You got it"
            }

            // 產生提示框
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show()
        })

        // 浮動元件：FloatingActionButton類別
        // layout>activity_material.xml>ComponentTree>fab>id；.setOnClickListener設定按下後的動作(lambda)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            // 產生詢問對話框：詢問使用者是否要重新玩
            AlertDialog.Builder(this)
                .setTitle("Replay game")
                .setMessage("Are you sure?")
                // 正向按鈕-ok，lambda參數2指按下按鈕的反應
                .setPositiveButton(getString(R.string.ok), { dialog, which ->
                    // 重置
                    viewModel.reset()
                    // 輸入字串框也要重置
                    number.setText("")
                })
                // 中立按鈕
                .setNeutralButton("Cancel", null)
                .show()

            viewModel.reset()
        }

    }

    fun check(view: View){
        // 從使用者得到的輸入數字
        val n = number.text.toString().toInt()
        viewModel.guess(n)
        /*val n = number.text.toString().toInt()
        println("number: $n")

        Log.d(TAG, "number:" + n)

        val diff = secretNumber.validate(n)
        var message = getString(R.string.yes_you_got_it)
        if(diff < 0){
            message = getString(R.string.bigger)
        }else if(diff > 0){
            message = getString(R.string.smaller)
        }

        // 按下ok(猜數字)顯示目前猜了幾次的次數
        counter.setText(secretNumber.count.toString())

//        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()*/

    }
}