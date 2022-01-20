package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Result : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        var tvName: TextView? = findViewById(R.id.tv_name)
        var tvScore: TextView? = findViewById(R.id.tv_score)
        var btnFinish: Button? = findViewById(R.id.btn_finish)

        tvName?.text = intent.getStringExtra(Constants.USER_NAME)
        tvScore?.text = "Your score is ${intent.getStringExtra(Constants.CORRECT_ANSWERS)} out of ${intent.getStringExtra(Constants.TOTAL_QUESTIONS)}"

        btnFinish?.setOnClickListener{
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Constants.USER_NAME, "user_name")
            intent.putExtra(Constants.CORRECT_ANSWERS, "correct_answer")
            intent.putExtra(Constants.TOTAL_QUESTIONS, "total_questions")
            startActivity(intent) //moves the screen to the quiz... activity
            finish() //close the current activity
        }

    }
}