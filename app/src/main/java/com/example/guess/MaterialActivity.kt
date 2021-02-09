package com.example.guess

import android.content.Context
import android.content.Intent
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
    val secretNumber = SecretNumber()
    val TAG = MaterialActivity::class.java.simpleName

    // 產生：生成activity物件時，onCreate()被執行
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate:")
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

        Log.d(TAG, "onCreate: " + secretNumber.secret);

        // 讀取已儲存的檔案，引數要與RecordActivity中的一致
        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
                .getInt("REC_COUNTER", -1)  // 沒讀到值時-1
        val nickname = getSharedPreferences("guess", Context.MODE_PRIVATE)
                .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: $count / $nickname")
    }

    // 利用control+o開啟覆寫，輸入覆寫生命週期函數關鍵字
    // 開始：出現第一個畫面前，onStart()被執行
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ");
    }

    // 繼續：出現第一個畫面後，onResume()被執行。開始有畫面與user互動
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ");
    }

    // 中斷：按鈕按下去後，要轉換下一個畫面前，OnPause()被執行。
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ");
    }

    // 停止：跳轉第二畫面後，原畫面onStop()被執行
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ");
    }

    // 重啟：在第二畫面返回前一畫面，按鈕按下去後，返回的畫面還沒出現前，OnRestart()被執行。
    // 回到onStart()
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ");
    }

    // 消滅：物件清除後，onDestroy()被執行，回到桌面
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ");
    }

    fun check(view: View){
        // 從使用者得到的輸入數字
//        val n = number.text.toString().toInt()
//        viewModel.guess(n)
        val n = number.text.toString().toInt()
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
            // 事件處理-跳轉到另一頁面：當使用者猜對時，按下確認鍵後跳轉到另一頁面；若沒猜對不反應動作
            .setPositiveButton(getString(R.string.ok), { dialog, which ->
                if(diff == 0){
                    // 回應使用者動作：intent類別與使用者互動產生變化，如按下按鈕啟動相機、轉換畫面、跳出功能…等等
                    // 參數1從哪個Activity轉換到目標Activity；參數2指定目標Activity
                    val intent = Intent(this, RecordActivity::class.java)
                    // 傳遞原本畫面資料到目標畫面：將原本畫面的count加到intent物件中存入
                    // 參數1給標籤；參數2要加的資料
                    intent.putExtra("COUNTER", secretNumber.count)
                    // 幫忙將intent物件送到Android系統，讓系統把Activity產生出來
                    startActivity(intent)
                }
            })
            .show()

    }
}