package rahkan.amin.tictactoegame.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.alert
import rahkan.amin.tictactoegame.R
import java.util.*

class GameActivity : AppCompatActivity() {

    var game_type: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_game)
        initfont()

        game_type = intent.getIntExtra("game_type", 0)
        Log.e("game_type", "" + game_type)
    }

    companion object {

        fun newIntent(context: Context, type: Int): Intent {
            var intent = Intent(context, GameActivity::class.java)
            intent.putExtra("game_type", type)

            return intent
        }

    }

    var typeface: Typeface? = null
    private fun initfont() {
        typeface = Typeface.createFromAsset(applicationContext.assets, "fonts/adamwarrenpro-bold.ttf")
    }

    protected fun btnClick(view: View) {

        var btnSelect = view as Button
        var index: Int = 0
        when (view.id) {
            R.id.btnCel1 -> index = 1
            R.id.btnCel2 -> index = 2
            R.id.btnCel3 -> index = 3
            R.id.btnCel4 -> index = 4
            R.id.btnCel5 -> index = 5
            R.id.btnCel6 -> index = 6
            R.id.btnCel7 -> index = 7
            R.id.btnCel8 -> index = 8
            R.id.btnCel9 -> index = 9
        }

        playGame(btnSelect, index)
    }


    var player = 1
    var player1Movments = ArrayList<Int>()
    var player2Movments = ArrayList<Int>()
    private fun playGame(btnSelected: Button, index: Int) {

        btnSelected.isEnabled = false
        btnSelected.textSize = 25F
        btnSelected.setTypeface(typeface, Typeface.BOLD)

        if (player == 1) {
            player = 2
            btnSelected.setBackgroundColor(Color.RED)
            btnSelected.text = "X"
            player1Movments.add(index)


            if (checkWinner() != -1)
                showEndDialog(winner)
            else {
                if (game_type == 1)
                    Handler().postDelayed({ AI_Play() }, 1000)
            }

        } else {
            player = 1
            btnSelected.setBackgroundColor(Color.GREEN)
            btnSelected.text = "O"
            player2Movments.add(index)
            if (checkWinner() != -1)
                showEndDialog(winner)
        }

//        checkWinner()

    }


    private fun AI_Play() {
        var emptyMovments = ArrayList<Int>()

        for (item in 1..9) {
            if (!(player1Movments.contains(item) || player2Movments.contains(item))) {
                emptyMovments.add(item)
            }
        }

        if (emptyMovments.size >= 1) {
            var random = Random()
            var cellIndex = random.nextInt(emptyMovments.size - 0) + 0

            var btnSelect: Button?

            when (emptyMovments[cellIndex]) {
                1 -> btnSelect = btnCel1
                2 -> btnSelect = btnCel2
                3 -> btnSelect = btnCel3
                4 -> btnSelect = btnCel4
                5 -> btnSelect = btnCel5
                6 -> btnSelect = btnCel6
                7 -> btnSelect = btnCel7
                8 -> btnSelect = btnCel8
                9 -> btnSelect = btnCel9
                else -> {
                    btnSelect = btnCel1
                }
            }

            playGame(btnSelect, emptyMovments[cellIndex])
        } else {

            alert("no winner ") {
                positiveButton("Again", onClicked = {
                    resetGame()
                })
                negativeButton("Finish", onClicked = {
                    finish()
                })
            }.show()

        }
    }

    var winner = -1
    private fun checkWinner(): Int {


        if ((player1Movments.contains(1) && player1Movments.contains(2) && player1Movments.contains(3)) ||
                (player1Movments.contains(4) && player1Movments.contains(5) && player1Movments.contains(6)) ||
                (player1Movments.contains(7) && player1Movments.contains(8) && player1Movments.contains(9)) ||
                (player1Movments.contains(1) && player1Movments.contains(4) && player1Movments.contains(7)) ||
                (player1Movments.contains(2) && player1Movments.contains(5) && player1Movments.contains(8)) ||
                (player1Movments.contains(3) && player1Movments.contains(6) && player1Movments.contains(9)) ||
                (player1Movments.contains(1) && player1Movments.contains(5) && player1Movments.contains(9)) ||
                (player1Movments.contains(3) && player1Movments.contains(5) && player1Movments.contains(7))) {
            winner = 1
        }

        if ((player2Movments.contains(1) && player2Movments.contains(2) && player2Movments.contains(3)) ||
                (player2Movments.contains(4) && player2Movments.contains(5) && player2Movments.contains(6)) ||
                (player2Movments.contains(7) && player2Movments.contains(8) && player2Movments.contains(9)) ||
                (player2Movments.contains(1) && player2Movments.contains(4) && player2Movments.contains(7)) ||
                (player2Movments.contains(2) && player2Movments.contains(5) && player2Movments.contains(8)) ||
                (player2Movments.contains(3) && player2Movments.contains(6) && player2Movments.contains(9)) ||
                (player2Movments.contains(1) && player2Movments.contains(5) && player2Movments.contains(9)) ||
                (player2Movments.contains(3) && player2Movments.contains(5) && player2Movments.contains(7))) {
            winner = 2

        }

//

        return winner

//            when (winner) {
//                1 -> showEndDialog(winner)
//                2 -> showEndDialog(winner)
//            }


    }

    private fun showEndDialog(winner: Int) {
        alert("game finished ...player $winner is winner ") {
            title = "Game Over ... "
            positiveButton("Again", onClicked = {

                resetGame()
            })
            negativeButton("finish ", onClicked =
            {
                finish()
            })
        }.show()
    }

    private fun resetGame() {

        btnCel1.setBackgroundColor(Color.WHITE)
        btnCel2.setBackgroundColor(Color.WHITE)
        btnCel3.setBackgroundColor(Color.WHITE)
        btnCel4.setBackgroundColor(Color.WHITE)
        btnCel5.setBackgroundColor(Color.WHITE)
        btnCel6.setBackgroundColor(Color.WHITE)
        btnCel7.setBackgroundColor(Color.WHITE)
        btnCel8.setBackgroundColor(Color.WHITE)
        btnCel9.setBackgroundColor(Color.WHITE)

        btnCel1.text = ""
        btnCel2.text = ""
        btnCel3.text = ""
        btnCel4.text = ""
        btnCel5.text = ""
        btnCel6.text = ""
        btnCel7.text = ""
        btnCel8.text = ""
        btnCel9.text = ""


        btnCel1.isEnabled = true
        btnCel2.isEnabled = true
        btnCel3.isEnabled = true
        btnCel4.isEnabled = true
        btnCel5.isEnabled = true
        btnCel6.isEnabled = true
        btnCel7.isEnabled = true
        btnCel8.isEnabled = true
        btnCel9.isEnabled = true
        winner = -1
        player = 1
        player1Movments.clear()
        player2Movments.clear()
    }
}
