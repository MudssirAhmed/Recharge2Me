package com.recharge2mePlay.recharge2me.home.ui.activities

import Global.custom_Loading_Dialog.CustomToast
import Global.custom_Loading_Dialog.LoadingDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.recharge2mePlay.recharge2me.R
import com.recharge2mePlay.recharge2me.onboard.models.User_googleAndOwn
import com.recharge2mePlay.recharge2me.onboard.ui.activities.EntryActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeActivity : AppCompatActivity(), MenuItem.OnMenuItemClickListener {


    private var backpressedTime : Long = 0
    private var backToast: Toast? = null;

    private lateinit var navController: NavController
    private lateinit var nav_drawer: NavigationView
    private lateinit var nav_icon: ImageView

    private lateinit var lL_tell_aFreind: LinearLayout
//    private lateinit var lL_helpAndSupport: LinearLayout
    private lateinit var lL_feedBack: LinearLayout
    private lateinit var lL_policies: LinearLayout



    private lateinit var listner: NavController.OnDestinationChangedListener

    // Custom
    private lateinit var toast: CustomToast
    private lateinit var loadingDialog: LoadingDialog

    // Initegers
    private var flag:Int = 0
    private var onBackPressedFlag: Int = 1;
    private var onTouchFlag: Int = 1

    // Strings
    private var set: String = ""
    private var setP: String = ""
    private var trx: String = ""

    // Firebase
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bnv_mainUi);
        navController = findNavController(R.id.nhf_mainUi);

        // Navigation view
        nav_icon = findViewById<ImageView>(R.id.iv_navIcon_mainUi)
        nav_drawer = findViewById<NavigationView>(R.id.nav_drawer)

        // LinearLayout
        lL_feedBack = nav_drawer.findViewById(R.id.lL_navDrawer_feedback);
        lL_tell_aFreind = nav_drawer.findViewById(R.id.lL_tell_aFreind);
//        lL_helpAndSupport = nav_drawer.findViewById(R.id.lL__helpAndSupport);
        lL_policies = nav_drawer.findViewById(R.id.lL_policies);

        // custom
        toast = CustomToast(this)
        loadingDialog = LoadingDialog(this)

        // Firebase
         db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        nav_drawer.visibility = GONE

        if(flag == 0){
            nav_drawer.animate().translationXBy(-100f)
            flag = 1;
        }

        // OnClickListner
        nav_icon.setOnClickListener{
            lifecycleScope.launch {
                setDataOnNavDrawer()
                appyAnimtionOnNavIcon()
            }
            applyOpenAnimation()
        }


        nav_drawer.getHeaderView(0).setOnClickListener {
            Toast.makeText(this, "Header", Toast.LENGTH_SHORT).show()
        }
        lL_feedBack.setOnClickListener {
            giveFeedBack()
        }
        lL_tell_aFreind.setOnClickListener{
            getLinkFromFirebase()
        }
//        lL_helpAndSupport.setOnClickListener {
//            helpAndSupport()
//        }
        lL_policies.setOnClickListener {
            val intent: Intent = Intent(this, PolicyActivity::class.java)
            intent.putExtra("Details", "fromMainUi")
            startActivity(intent)
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
                bottomNavigationView.visibility = VISIBLE
                nav_icon.visibility = VISIBLE
                set = "Profile"
            }
            else if(destination.id == R.id.ui_Home){
                if(nav_drawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                bottomNavigationView.visibility = VISIBLE
                nav_icon.visibility = VISIBLE
                set = "Home"
                trx = "Home"
            }
            else if(destination.id == R.id.ui_Transactions){
                if(nav_drawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                bottomNavigationView.visibility = VISIBLE
                nav_icon.visibility = VISIBLE
                trx = "Transactions"
            }
            else if(destination.id == R.id.settings){
                if(nav_drawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                bottomNavigationView.visibility = GONE
                nav_icon.visibility = GONE
                if(set == "Home"){
                    set = "Go_Home"
                }
                else if(set == "Profile"){
                    set = "Go_Profile"
                }

            }
            else if(destination.id == R.id.transactionDetails){
                if(nav_drawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                bottomNavigationView.visibility = GONE
                nav_icon.visibility = GONE

                if(trx.equals("Home")){
                    trx = "Go_Home"
                }
                else if(trx.equals("Transactions")){
                    trx = "Go_Transactions"
                }
            }
        }

        // handle signOut click
        var item: MenuItem = nav_drawer.menu.findItem(R.id.drawer_signOut)
        item.setOnMenuItemClickListener(this)

        lifecycleScope.launch() {
            setDataOnNavDrawer()
        }

    }

    // When signOut clicked
    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        lifecycleScope.launch {
            appyCloseAnimation()
            signOut()
        }
        return true
    }

    private fun signOut() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val acct = GoogleSignIn.getLastSignedInAccount(this)

        if (acct != null) { // means user signIn with Google

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            // This code clears which account is connected to the app. To sign in again, the user must choose their account again.
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this) { // It will go back on LogIn-SignIn Page.
                        gotoLogninSignUi()
                    }
        }
        else {
            val user: FirebaseUser? = mAuth.getCurrentUser()

            if (user != null) { // means user will signIn using firebaseAuth
                mAuth.signOut()
                gotoLogninSignUi()
            }
        }
    } // End of signOut method;

    private fun gotoLogninSignUi() {
        val intent = Intent(this, EntryActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "You are Logged Out...", Toast.LENGTH_SHORT).show()
    }


    private fun giveFeedBack() {
        val appPackageName: String = this.getPackageName() // getPackageName() from Context or Activity object
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }    // Give Feedback
    private fun getLinkFromFirebase() {
        lL_tell_aFreind.isEnabled = false
        lL_tell_aFreind.isClickable = false
        Toast.makeText(this, "mail us for any Querry!", Toast.LENGTH_SHORT).show()
        try {
            val docRef: DocumentReference = db.collection("ScreenDialog").document("playStore")
            docRef.get().addOnSuccessListener { documentSnapshot ->
                try {
                    val link = documentSnapshot.getString("Link")
                    if (link != null) {
                        onShareClicked(link)
                    }
                } catch (e: Exception) {
                    Log.i("expInside", e.message ?: "Error! ")
                    onShareClicked("null")
                }
            }.addOnFailureListener {
                Log.i("expFail", "Error! ")
                onShareClicked("null") }
        }
        catch (e: java.lang.Exception) {
            Log.i("expMain", e.message ?: "Error! ")
            onShareClicked("null")
        }
    } // Tell a freind
    private fun onShareClicked(link: String) {
        Log.i("link", "link: " + link)

        lL_tell_aFreind.isEnabled = true
        lL_tell_aFreind.isClickable = true

        var link:String = link

        if (link.equals("null")) {
            link = "https://play.google.com/store/apps/details?id=com.recharge2mePlay.recharge2me"
        }

        val uri = Uri.parse(link)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link.toString())
        intent.putExtra(Intent.EXTRA_TITLE, "Recharge2me")

        startActivity(Intent.createChooser(intent, "Share Link"))
    }    // play store link
    private fun helpAndSupport() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "recharge2me.help@gmail.com", null))
        this.startActivity(Intent.createChooser(emailIntent, null))
    } // Help and support


    fun setDataOnNavDrawer(){

        val docRef = db.collection("USERS").document(auth.uid.toString())

        try {
            docRef.get()
                    .addOnSuccessListener { documentSnapshot ->

                        val city = documentSnapshot.toObject<User_googleAndOwn>()
                        val user = city?.user_details
                        val Name: String = user?.name ?:  "R2M Demo"
                        val Reward: String = user?.rewards ?: "0"

                        val tv_navHeader_name  = nav_drawer.getHeaderView(0).findViewById<TextView>(R.id.tv_navHeader_name);
                        val tv_navHeader_reward  = nav_drawer.getHeaderView(0).findViewById<TextView>(R.id.tv_navHeader_reward);

                        tv_navHeader_name.text = Name
                        tv_navHeader_reward.text = "₹ " + Reward + " rewards"
                    }
                    .addOnFailureListener { exception ->
                        toast.showToast("Error! " + exception.message)
                    }
        }
        catch (e: Exception){
            toast.showToast("Error! " + e.message)
        }

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

        if (event != null) {
            if(event.action == MotionEvent.ACTION_UP){
                if(nav_drawer.isVisible){
                    if(onTouchFlag == 1){
                        onTouchFlag = 0
                        lifecycleScope.launch {
                            appyCloseAnimation()
                        }
                    }
                }
            }
            else if(event.action == MotionEvent.ACTION_DOWN){

                if(nav_drawer.isVisible){
                    if(onTouchFlag == 1){
                        onTouchFlag = 0
                        lifecycleScope.launch {
                            appyCloseAnimation()
                        }
                    }
                }
            }
        }

        return super.onTouchEvent(event)
    }
    override fun onBackPressed() {

        if(!nav_drawer.isVisible && (set.equals("Home") || set.equals("Profile"))
                                && (trx.equals("Home") || trx.equals("Transactions"))   ){
            if(nav_icon.isVisible){
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
        }

        if(nav_drawer.isVisible){
            if(onBackPressedFlag == 1){
                onBackPressedFlag = 0
                lifecycleScope.launch {
                    appyCloseAnimation()
                }
            }
        }

        // Hondeling back button
        if(set == "Go_Home"){
            nhf_mainUi.findNavController().navigate(R.id.action_settings_to_ui_Home)
        }
        else if(set == "Go_Profile"){
            nhf_mainUi.findNavController().navigate(R.id.action_settings_to_ui_Profile)
        }
        else if(trx == "Go_Home"){
            nhf_mainUi.findNavController().navigate(R.id.action_transactionDetails_to_ui_Home)
        }
        else if(trx == "Go_Transactions"){
            nhf_mainUi.findNavController().navigate(R.id.action_transactionDetails_to_ui_Transactions)
        }

    }





}



