package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 0
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0
    private var mUserName: String? = null
    private var mCorrectAnswers: Int = 0

    private var progressBar: ProgressBar? = null
    private var textProgress: TextView? = null
    private var textQuestion: TextView? = null
    private var imageQuestion: ImageView? = null
    private var submitButton: Button? = null

    private var textOptionOne: TextView? = null
    private var textOptionTwo: TextView? = null
    private var textOptionThree: TextView? = null
    private var textOptionFour: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        progressBar = findViewById(R.id.barProgress)
        textProgress = findViewById(R.id.textProgress)
        textQuestion = findViewById(R.id.text_question)
        imageQuestion = findViewById(R.id.id_image)
        submitButton = findViewById(R.id.btn_submit)
        mUserName = intent.getStringExtra(Constants.userName)

        textOptionOne = findViewById(R.id.option_one)
        textOptionTwo = findViewById(R.id.option_two)
        textOptionThree = findViewById(R.id.option_three)
        textOptionFour = findViewById(R.id.option_four)

        mQuestionsList = Constants.getQuestion()

        textOptionOne?.setOnClickListener(this)
        textOptionTwo?.setOnClickListener(this)
        textOptionThree?.setOnClickListener(this)
        textOptionFour?.setOnClickListener(this)
        submitButton?.setOnClickListener(this)

        setQuestion()
    }

    private fun setQuestion()
    {
        defaultOptionsView()
        val question: Question = mQuestionsList!![mCurrentPosition]
        progressBar?.progress = mCurrentPosition + 1
        val textProgressString = "${mCurrentPosition + 1}/${mQuestionsList!!.size}"
        textProgress?.text = textProgressString
        imageQuestion?.setImageResource(question.image)
        textQuestion?.text = question.question
        textOptionOne?.text = question.optionOne
        textOptionTwo?.text = question.optionTwo
        textOptionThree?.text = question.optionThree
        textOptionFour?.text = question.optionFour

        val textFinish = "FINISH"
        val textSubmit = "SUBMIT"

        if(mCurrentPosition == mQuestionsList!!.size)
        {
            submitButton?.text = textFinish
        }
        else
        {
            submitButton?.text = textSubmit
        }

    }

    private fun defaultOptionsView()
    {
        val options = ArrayList<TextView>()
        textOptionOne?.let {
            options.add(0, it)
        }
        textOptionTwo?.let {
            options.add(1, it)
        }
        textOptionThree?.let {
            options.add(2, it)
        }
        textOptionFour?.let {
            options.add(3, it)
        }

        for(option in options)
        {
            option.setTextColor(Color.parseColor("#7a8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }

    }

    private fun selectedOption(view: TextView, selectedOptionNum: Int)
    {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum

        view.setTextColor(Color.parseColor("#363a43"))
        view.setTypeface(view.typeface, Typeface.BOLD)
        view.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(v: View?) {

        val textFinish = "FINISH"
        val textNextQuestion = "GO TO NEXT QUESTION"

        when(v?.id){
            R.id.option_one -> { textOptionOne?.let { selectedOption(it, 1) }}
            R.id.option_two -> { textOptionTwo?.let { selectedOption(it, 2) }}
            R.id.option_three -> { textOptionThree?.let { selectedOption(it, 3) }}
            R.id.option_four -> { textOptionFour?.let { selectedOption(it, 4) }}
            R.id.btn_submit -> {
                if(mSelectedOptionPosition == 0)
                {
                    mCurrentPosition++

                    when
                    {
                        mCurrentPosition < mQuestionsList!!.size -> {setQuestion()}
                        else -> {
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.userName, mUserName)
                            intent.putExtra(Constants.correctAnswers, mCorrectAnswers)
                            intent.putExtra(Constants.totalQuestions, mQuestionsList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }

                }
                else
                {
                    val question = mQuestionsList?.get(mCurrentPosition)
                    if(mSelectedOptionPosition != question!!.correctAnswer)
                    {
                        answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    }
                    else
                    {
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionsList!!.size - 1)
                    {
                        submitButton?.text = textFinish
                    }
                    else
                    {
                        submitButton?.text = textNextQuestion
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int)
    {
        when(answer){
            1 -> textOptionOne?.background = ContextCompat.getDrawable(
                this@QuizQuestionActivity,
                drawableView
            )
            2 -> textOptionTwo?.background = ContextCompat.getDrawable(
                this@QuizQuestionActivity,
                drawableView
            )
            3 -> textOptionThree?.background = ContextCompat.getDrawable(
                this@QuizQuestionActivity,
                drawableView
            )
            4 -> textOptionFour?.background = ContextCompat.getDrawable(
                this@QuizQuestionActivity,
                drawableView
            )
        }
    }
}