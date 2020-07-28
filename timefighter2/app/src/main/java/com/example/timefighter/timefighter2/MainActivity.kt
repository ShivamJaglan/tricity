package com.example.timefighter.timefighter2

import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    internal var gamestarted=false
    internal lateinit var countDownTimer: CountDownTimer
    internal val initialCountDown: Long=60000
    internal val countDowninterval: Long=1000
//    internal lateinit var initialtimeleft:
    internal var score=0
    internal lateinit var tapmebutton: Button
    internal lateinit var gamescoretextview: TextView
    internal lateinit var timelefttextview: TextView
    internal var timeLeftOnTimer: Long=60000
    companion object{
        private val TAG= MainActivity::class.java.simpleName
        private const val SCORE_KEY="SCORE_KEY"
        private const val TIME_LEFT_KEY="TIME_LEFT_KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tapmebutton=findViewById(R.id.tapmebutton)
        gamescoretextview=findViewById(R.id.gamescoretextview)
//        gamescoretextview.text=getString(R.string.yourscore,score)
        timelefttextview=findViewById(R.id.timelefttextview)
        tapmebutton.setOnClickListener {view ->
            val bounceAnimation=AnimationUtils.loadAnimation(this,R.anim.bounce)
            view.startAnimation(bounceAnimation)

        incrementScore()

        }
       if(savedInstanceState!=null)
       {
           score=savedInstanceState.getInt(SCORE_KEY)
           timeLeftOnTimer=savedInstanceState.getLong(TIME_LEFT_KEY)
           restoreGame()
       }
        else{
           resetgame()
       }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.actionAbout)
        {
            showInfo()
        }
        return true
    }
    private fun showInfo()
    {
        val dialogTitle=getString(R.string.aboutTitle,BuildConfig.VERSION_NAME)
        val dialogMessage=getString(R.string.aboutMesssage)
        val builder=AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()



    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SCORE_KEY,score)
        outState.putLong(TIME_LEFT_KEY,timeLeftOnTimer)
        countDownTimer.cancel()
    }

    private fun incrementScore() {
    if (!gamestarted)
    {
        startgame()
    }
        score=score+1
        val newscore=getString(R.string.yourscore,score)
        gamescoretextview.text=newscore
        val blinkanimation= AnimationUtils.loadAnimation(this,R.anim.blink)
        gamescoretextview.startAnimation(blinkanimation)
    }
    private fun startgame()
    {
        countDownTimer.start()
        gamestarted=true
    }
    private fun resetgame(){
        score=0;
        gamescoretextview.text=getString(R.string.yourscore,score)
        val initialTimeLeft=initialCountDown/countDowninterval
        timelefttextview.text=getString(R.string.timeleft,initialTimeLeft)
        countDownTimer= object:CountDownTimer(initialCountDown,countDowninterval) {
            override fun onTick(millisunitilfinished: Long) {
                timeLeftOnTimer=millisunitilfinished
               val tiiime= millisunitilfinished/1000
                timelefttextview.text=getString(R.string.timeleft,tiiime)
            }
            override fun onFinish()
            {
                endgame()
            }
        }
        gamestarted=false


    }
    private fun restoreGame()
    {
        gamescoretextview.text=getString(R.string.yourscore,score)
        val restoredTime=timeLeftOnTimer/1000
        timelefttextview.text=getString(R.string.timeleft,restoredTime)
        countDownTimer=object:CountDownTimer(timeLeftOnTimer,countDowninterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer=millisUntilFinished
                val timeLeft=millisUntilFinished/1000
                timelefttextview.text=getString(R.string.timeleft,timeLeft)


            }

            override fun onFinish() {
                endgame()
            }
        }
countDownTimer.start()
        gamestarted=true;
    }
    private fun endgame()
    {
        Toast.makeText(this,getString(R.string.gameOverMessage,score), Toast.LENGTH_LONG).show()
        resetgame()
    }
}