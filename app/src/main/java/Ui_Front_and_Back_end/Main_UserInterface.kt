package Ui_Front_and_Back_end

import android.os.Bundle
import android.view.*
import android.view.View.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.recharge2mePlay.recharge2me.R
import kotlinx.android.synthetic.main.activity_main__user_interface.view.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Main_UserInterface : AppCompatActivity(), MenuItem.OnMenuItemClickListener {


    private var backpressedTime : Long = 0
    private var backToast: Toast? = null;

    private lateinit var navController: NavController
    private lateinit var nav_drawer: NavigationView
    private lateinit var nav_icon: ImageView

    private lateinit var lL_tell_aFreind: LinearLayout
    private lateinit var lL_helpAndSupport: LinearLayout
    private lateinit var listner: NavController.OnDestinationChangedListener

    // Initegers
    private var flag:Int = 0
    private var onBackPressedFlag: Int = 1;
    private var onTouchFlag: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__user_interface)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_mainUi);
        navController = findNavController(R.id.nhf_mainUi);

        // Navigation view
        nav_icon = findViewById<ImageView>(R.id.iv_navIcon_mainUi)
        nav_drawer = findViewById<NavigationView>(R.id.nav_drawer)

        // LinearLayout
        lL_tell_aFreind = nav_drawer.findViewById(R.id.lL_tell_aFreind);
        lL_helpAndSupport = nav_drawer.findViewById(R.id.lL__helpAndSupport);

        nav_drawer.visibility = GONE

        if(flag == 0){
            nav_drawer.animate().translationXBy(-100f)
            flag = 1;
        }

        // OnClickListner
        nav_icon.setOnClickListener{

            lifecycleScope.launch {
                appyAnimtionOnNavIcon()
            }
            applyOpenAnimation()
        }

        nav_drawer.getHeaderView(0).setOnClickListener {
            Toast.makeText(this, "Header", Toast.LENGTH_SHORT).show()
        }

        // TODO: add this lines for set user profile Image in Header
//        val iv: ImageView = nav_drawer.getHeaderView(0).findViewById(R.id.iv_navHeader_profileImage)
//        iv.setImageResource(R.drawable.man)

        lL_tell_aFreind.setOnClickListener{
            Toast.makeText(this, "Tell a Freind", Toast.LENGTH_SHORT).show()
        }
        lL_helpAndSupport.setOnClickListener {
            Toast.makeText(this, "Help and Support", Toast.LENGTH_SHORT).show()
        }

        bottomNavigationView.setupWithNavController(navController);
        nav_drawer.setupWithNavController(navController)

        // It will listen the destinations and animate the drawer
        listner = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.ui_Profile){
                if(nav_drawer.isVisible){
                        lifecycleScope.launch {
                            appyCloseAnimation()
                        }
                }
            }
            else if(destination.id == R.id.ui_Home){
                if(nav_drawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
            }
            else if(destination.id == R.id.ui_Transactions){
                if(nav_drawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
            }
            else if(destination.id == R.id.settings){
                if(nav_drawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
            }
        }

        // handle signOut click
        var item: MenuItem = nav_drawer.menu.findItem(R.id.drawer_signOut)
        item.setOnMenuItemClickListener(this)

    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listner)
    }
    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listner)
    }

//Animation
    // Animate navIcon
    private suspend fun appyAnimtionOnNavIcon(){
        nav_icon.apply {
            animate().
                alpha(0f)
                .duration = 100L

            delay(200)

            animate()
                .alpha(1f)
                .duration = 100L
        }
    }
    // Animation on navDrawer
    private fun applyOpenAnimation(){
        nav_drawer.apply {
            alpha = 0f
            visibility = View.VISIBLE

            animate()
                    .alpha(1f)
                    .setDuration(200L)
                    .translationXBy(+100f)
                    .setListener(null)
        }
    }
    private suspend fun appyCloseAnimation(){
        nav_drawer.apply {
            animate()
                    .alpha(0f)
                    .setDuration(200L)
                    .translationXBy(-100f)
                    .setListener(null)

            delay(200)

            visibility = View.INVISIBLE
//            alpha = 1f
            if(onBackPressedFlag == 0)
                onBackPressedFlag = 1
            if(onTouchFlag == 0)
                onTouchFlag = 1
        }
    }

    // onBackPressd and onTouch events
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(nav_drawer.isVisible){
            if(onTouchFlag == 1){
                onTouchFlag = 0
                lifecycleScope.launch {
                    appyCloseAnimation()
                }
            }
        }
        return super.onTouchEvent(event)
    }
    override fun onBackPressed() {

        if(!nav_drawer.isVisible){
            if(backpressedTime + 2000 > System.currentTimeMillis())
            {
                backToast?.cancel()
                moveTaskToBack(true);
                finish()
                return
            }
            else{
                backToast = Toast.makeText(this, "Press again to exit!", Toast.LENGTH_SHORT)
                backToast?.show()
            }

            backpressedTime = System.currentTimeMillis()
        }

        if(nav_drawer.isVisible){
            if(onBackPressedFlag == 1){
                onBackPressedFlag = 0
                lifecycleScope.launch {
                    appyCloseAnimation()
                }
            }
        }
    }

    // When signOut clicked
    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        Toast.makeText(this, "signOut", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            appyCloseAnimation()
        }
        return true
    }
}


