package com.example.guess

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.guess.data.GameDatabase
import com.example.guess.data.Record
import kotlinx.android.synthetic.main.activity_record.*

// �Y�ό��냦��ən����ӛ����Ӳ�����ٌ��Y��ȡ���؂�
class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // ȡ��MaterialActivity��startActivityForResult()�����Y��(count��)
        // ����1����MaterialActivity�И˻`COUNTER������2�Ҳ����˻`COUNTERֵ�r���o����A�Oֵ
        val count = intent.getIntExtra("COUNTER", -1)

        // ���õ����Y��(count��)�@ʾ��activity_record.xml
        counter.setText(count.toString())

        // ȡ��activity_record��save���o�����°��o�����
        save.setOnClickListener{ view ->
            // �õ�nickname
            val nick = nickname.text.toString()
            // �����Y������������1�n�����Q�������������xml�n������2��ȡ����
            getSharedPreferences("guess", Context.MODE_PRIVATE)
                    .edit()     // �õ�SharedPreferences��݋����֮����܌����Y��
                    .putInt("REC_COUNTER", count)     // �����Y�ϣ�����1ֵ�ă������Q������2Ҫ������Y��
                    .putString("REC_NICKNAME", nick)
//                    .commit()   // commit()�Ǵ�ȡ�c����ͬ����һ���Y�ϴ������̈��Ќ���Ӳ�������гɹ���ʧ���ķ���ֵ
                    // �����Y�ϣ�apply()�Ǵ�ȡ�c���Ќٷ�ͬ�����Ȍ��Y�ϴ�ȡ��ӛ���w���و���(����Ӳ��)
                    // ׌����ĳ�ʽ�a����ӛ���w���޸�ǰ���Y�ϣ�Ч���^�ã��]�з���ֵ
                    .apply()

            //insert to Room�Y�ώ�
            // Room test
            // ��������һ���[�Έ��оwҪ��ʹ���߻���
            // �@�eҪ���ℓ��һ�����оw�톢�ӲŲ�������nͻ
            // ��lambda����еą�����Ψһ������һ�������Է���()������
            Thread(){
                // �_��ÿһ�������getInstanceȡ��singleton��һ��������
                GameDatabase.getInstance(this)?.recordDao()?.
                insert(Record(nick, count))
            }.start()

            // ��nickname����intent���
            val intent = Intent()
            // �����Y�ϣ�����1ֵ�Ę˻`���Q������2Ҫ������Y��
            intent.putExtra("Nick", nick)

            // �ڵڴˮ������setResult()�����ص�MaterialActivity��onActivityResult()
            // ����1ָ�Y��׃��������2�؂��Y��
            setResult(RESULT_OK, intent)

            // �Y����activity���ŕ���RESULT_OK��ǰ��
            finish()

        }
    }
}