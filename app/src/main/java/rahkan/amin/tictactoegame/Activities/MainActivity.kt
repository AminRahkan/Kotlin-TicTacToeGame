package rahkan.amin.tictactoegame.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import rahkan.amin.tictactoegame.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    protected fun goToPlay(view: View) {
        when (view.id) {
            R.id.btnMenu1 -> openGameActivity(1) //2 player
            R.id.btnMenu2 -> openGameActivity(2) //AI player
        }
    }

    private fun openGameActivity(type: Int) {
        var intent = GameActivity.newIntent(applicationContext, type)
        startActivity(intent)
    }
}
