package com.example.guess

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guess.data.EventResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_function.view.*
import java.net.URL

// 主功能設計
class MainActivity : AppCompatActivity() {
    // 詢問相機使用權的判定值
    private val REQUEST_CODE_CAMERA: Int = 100

    // 得到MainActivity的字串，讓Log除錯用
    val TAG = MainActivity::class.java.simpleName

    //顯示項目
    val functions = listOf<String>(
            "Camera",
            "Guess game",
            "Record list",
            "Download coupons",
            "News",
            "Snooker",
            "Maps")

    // mainthread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread {
            val data = URL("http://api.snooker.org/?t=5&s=2020").readText()

            // 利用Gson()快速解析json資料
            val result = Gson().fromJson(data, EventResult::class.java)
            // 得到所有json資料
            result.forEach {
                Log.d(TAG, "onCreate: $it")
            }
        }.start()

        //RecyclerView：當user上下滑動元件離開畫面時，該元件會先被儲存等待之後被取出
        //早期是用丟棄方式
        //設定版面功能表：LinearLayout清單流水式，可以上下滑動
        recycler.layoutManager = LinearLayoutManager(this)
        // 是否固定大小
        recycler.setHasFixedSize(true)
        // adapter連接器，實現一個或多個系統不同部分之間的連接，獲得協同處理
        recycler.adapter = FunctionAdapter()

        //spinner微調器自製下拉選單 - 顯示固定個數的資料
        val colors = arrayOf("Red", "Green", "Blue")
        // 參數2指每一列資料的layout(這裡使用android內建的layout，可在SDK找到)，參數3.4指資料來源
        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, colors)
        // 下拉效果 - 選單變寬
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        // 設定到spinner的adapter
        spinner.adapter = adapter
        // 點擊後抓到資料，需override兩種方法：沒有點擊時，已經點擊時
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            // 參數1=AdapterView該對應的資料，參數2=點擊的TextView，參數4=id值
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                Log.d(TAG, "onItemSelected: ${colors[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    //配置畫面的holder，必須繼承RecyclerView.Adapter來做畫面功能
    //內部類別，只能給MainActivity用
    inner class FunctionAdapter(): RecyclerView.Adapter<FunctionHolder>(){

        //設定資料顯示畫面
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_function, parent, false)
            val holder = FunctionHolder(view)
            return holder
        }

        // 當有資料時會自動被呼叫，設定資料位置；position：資料在第幾列
        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            holder.nameText.text = functions.get(position)
            // 當user按下功能鍵，傳入被按下的position
            holder.itemView.setOnClickListener {
                functionClicked(position)
            }
        }

        // 裡面有多少筆資料，來自functions
        override fun getItemCount(): Int {
            return functions.size
        }
    }

    // 判斷功能鍵，按下按鈕跳到對應的該畫面
    private fun functionClicked(position: Int) {
        when(position){
            0 -> {
                //取得使用相機的權限
                val permission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                // 判斷從使用者得到的選擇，0=允許，已被內建在PackageManager.PERMISSION_GRANTED
                if(permission == PackageManager.PERMISSION_GRANTED){
                    takePhoto()
                }else{
                    //android預設危險權限對話框，由requestPermissions取得
                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
                }
            }
            1 -> startActivity(Intent(this, MaterialActivity::class.java))
            2 -> startActivity(Intent(this, RecordListActivity::class.java))
            5 -> startActivity(Intent(this, SnookerActivity::class.java))
            else -> return
        }
    }

    // 當按下android預設危險權限對話框的其一選項後，會自動執行
    override fun onRequestPermissionsResult(
            // 對應REQUEST_CODE_CAMERA的值
            requestCode: Int,
            // 警示字串
            permissions: Array<out String>,
            // 使用者按下選項的結果
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //判斷requestCode的工作碼是否相同
        if(requestCode == REQUEST_CODE_CAMERA){
            // 判斷使用者輸入結果是否一致(系統會記錄第一次放在[0]，之前會自動登入)
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhoto()
            }
        }
    }

    //開啟相機
    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }
    // 暫存被滑出功能表，定點在row_function.xml>name
    class FunctionHolder(view:View): RecyclerView.ViewHolder(view){
        var nameText: TextView = view.name
    }

    // 當Activity要出現的時候，會去要一個Menu表單物件
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // menu物件的充氣機，可以將xml設計圖檔變成menu物件，參數2要充氣到哪一個menu物件
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //當使用者去選擇Menu表單物件的時候，就會跑到此方法
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 使用者按了圖示action_cache
        if(item.itemId == R.id.action_cache){
            Log.d(TAG, "Cache selected")
        }
        return super.onOptionsItemSelected(item)
    }
}