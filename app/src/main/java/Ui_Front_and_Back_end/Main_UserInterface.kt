package Ui_Front_and_Back_end

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.recharge2mePlay.recharge2me.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class Main_UserInterface : AppCompatActivity() {


    private var backpressedTime : Long = 0
    private var backToast: Toast? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__user_interface)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_mainUi);
        val navController = findNavController(R.id.nhf_mainUi);

        bottomNavigationView.setupWithNavController(navController);


    }

    override fun onBackPressed() {

        if(backpressedTime + 2000 > System.currentTimeMillis())
        {
            backToast?.cancel()
            moveTaskToBack(true);
            finish()
            return
        }
        else{
            backToast = Toast.makeText(this,"Press again to exit!",Toast.LENGTH_SHORT)
            backToast?.show()
        }

        backpressedTime = System.currentTimeMillis()
    }
}