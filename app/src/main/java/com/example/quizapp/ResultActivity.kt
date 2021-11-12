package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val userName: TextView = findViewById(R.id.username_text)
        val score: TextView = findViewById(R.id.score_text)
        val finishButton: Button = findViewById(R.id.finish_button)

        userName.text = intent.getStringExtra(Constants.userName)
        score.text = intent.getStringExtra(Constants.correctAnswers)
        val totalQuestions = intent.getIntExtra(Constants.totalQuestions, 0)
        val correctAnswers = intent.getIntExtra(Constants.correctAnswers, 0)

        score.text = "Your score is $correctAnswers out of $totalQuestions"

        finishButton.setOnClickListener {
            finish()
        }
    }
}