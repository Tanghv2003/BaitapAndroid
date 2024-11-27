package vn.edu.hust.studentman

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

import android.content.Intent
import android.widget.EditText
import android.widget.Button

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student)


        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val name = findViewById<EditText>(R.id.newName).text.toString()
            val mssv = findViewById<EditText>(R.id.newMSSV).text.toString()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("name", name)
                resultIntent.putExtra("mssv", mssv)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}