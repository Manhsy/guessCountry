package com.example.myquizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.lang.reflect.Type

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {
    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgressBar: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null
    private var btnSubmit: Button? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null

    private var numOfCorrect: Int = 0
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        userName = intent.getStringExtra(Constants.USER_NAME).toString()
        progressBar = findViewById(R.id.progressBar)
        tvProgressBar = findViewById(R.id.tvProgressBar)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)
        btnSubmit = findViewById(R.id.btn_submit)
        tvOptionOne = findViewById(R.id.optionOne)
        tvOptionTwo = findViewById(R.id.optionTwo)
        tvOptionThree = findViewById(R.id.optionThree)
        tvOptionFour = findViewById(R.id.optionFour)

        //set on click listener to each choice
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)

        btnSubmit?.setOnClickListener(this)

        mQuestionsList = Constants.getQuestions()

        setQuestion()

    }

    private fun setQuestion() {

        val question: Question = mQuestionsList!![mCurrentPosition - 1]

        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgressBar?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question

        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour

        defaultOptionsView()

        if(mCurrentPosition == mQuestionsList!!.size){
            btnSubmit?.text = "Finish"
        }else{
            btnSubmit?.text = "Submit"
        }

    }
    //set the border of the unselected options
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()

        tvOptionOne?.let{
            options.add(0, it)
        }
        tvOptionTwo?.let{
            options.add(1, it)
        }
        tvOptionThree?.let{
            options.add(2, it)
        }
        tvOptionFour?.let{
            options.add(3, it)
        }

        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectedOptionView(tv: TextView, selectedOption: Int){
        //make all the choices have unselected styling
        defaultOptionsView()

        mSelectedOptionPosition = selectedOption
        //set the styling for the option that was selected
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(view: View?) {
        when(view?.id){

            //once an option is selected
            R.id.optionOne -> {
                tvOptionOne?.let{
                    selectedOptionView(it, 1)
                }
            }
            R.id.optionTwo -> {
                tvOptionTwo?.let{
                    selectedOptionView(it, 2)
                }
            }
            R.id.optionThree -> {
                tvOptionThree?.let{
                    selectedOptionView(it, 3)
                }
            }
            R.id.optionFour -> {
                tvOptionFour?.let{
                    selectedOptionView(it, 4)
                }
            }

            //once the submit button is selected
            R.id.btn_submit->{
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition++
                    when{
                        mCurrentPosition <= mQuestionsList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            val intent: Intent = Intent(this, Result::class.java)
                            intent.putExtra(Constants.USER_NAME, userName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, numOfCorrect.toString())
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size.toString())
                            startActivity(intent) //moves the screen to the quiz... activity
                            finish() //close the current activity
                        }
                    }
                }else{
                    val question = mQuestionsList?.get(mCurrentPosition - 1)

                    answerView(mSelectedOptionPosition, R.drawable.incorrect_option_border_bg)
                    answerView(question!!.correctAnswer, R.drawable.correct_option_border_bg)

                    if(question!!.correctAnswer == mSelectedOptionPosition){
                        numOfCorrect+=1
                    }

                    if(mCurrentPosition == mQuestionsList!!.size){
                        btnSubmit?.text = "Finish"
                    }else{
                        btnSubmit?.text = "Go to the next question"
                    }
                    mSelectedOptionPosition = 0
                }
            }
        }
    }
    private fun answerView (answer: Int, drawableView: Int){
        when (answer){
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(this, drawableView)
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(this, drawableView)
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            }
            4 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }
}