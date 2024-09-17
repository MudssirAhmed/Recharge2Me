package com.recharge2mePlay.recharge2me.home.ui.activities

import com.recharge2mePlay.recharge2me.utils.dialogs.CustomToast
import com.recharge2mePlay.recharge2me.utils.dialogs.LoadingDialog
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
import com.recharge2mePlay.recharge2me.databinding.ActivityHomeBinding
import com.recharge2mePlay.recharge2me.onboard.models.User_googleAndOwn
import com.recharge2mePlay.recharge2me.onboard.ui.activities.EntryActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), MenuItem.OnMenuItemClickListener {

    // Binding
    private lateinit var mBinding: ActivityHomeBinding

    private var backpressedTime : Long = 0
    private var backToast: Toast? = null;

    private lateinit var navController: NavController
    private lateinit var listner: NavController.OnDestinationChangedListener
    private lateinit var toast: CustomToast
    private lateinit var loadingDialog: LoadingDialog
    private var flag:Int = 0
    private var onBackPressedFlag: Int = 1;
    private var onTouchFlag: Int = 1
    private var set: String = ""
    private var setP: String = ""
    private var trx: String = ""
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val bottomNavigationView = mBinding.bnvMainUi
        navController = findNavController(R.id.nhf_mainUi);
        toast = CustomToast(this)
        loadingDialog = LoadingDialog(this)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        setData()
        setEventListeners()
    }

    private fun setData() {
        mBinding.navDrawer.visibility = GONE
        if(flag == 0) {
            mBinding.navDrawer.animate().translationXBy(-100f)
            flag = 1;
        }
        mBinding.bnvMainUi.setupWithNavController(navController);
        mBinding.navDrawer.setupWithNavController(navController)
        listner = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.ui_Profile){
                if(mBinding.navDrawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                mBinding.bnvMainUi.visibility = VISIBLE
                mBinding.ivNavIconMainUi.visibility = VISIBLE
                set = "Profile"
            }
            else if(destination.id == R.id.ui_Home){
                if(mBinding.navDrawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                mBinding.bnvMainUi.visibility = VISIBLE
                mBinding.ivNavIconMainUi.visibility = VISIBLE
                set = "Home"
                trx = "Home"
            }
            else if(destination.id == R.id.ui_Transactions){
                if(mBinding.navDrawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                mBinding.bnvMainUi.visibility = VISIBLE
                mBinding.ivNavIconMainUi.visibility = VISIBLE
                trx = "Transactions"
            }
            else if(destination.id == R.id.settings){
                if(mBinding.navDrawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                mBinding.bnvMainUi.visibility = GONE
                mBinding.ivNavIconMainUi.visibility = GONE
                if(set == "Home"){
                    set = "Go_Home"
                }
                else if(set == "Profile"){
                    set = "Go_Profile"
                }
            }
            else if(destination.id == R.id.transactionDetails){
                if(mBinding.navDrawer.isVisible){
                    lifecycleScope.launch {
                        appyCloseAnimation()
                    }
                }
                mBinding.bnvMainUi.visibility = GONE
                mBinding.ivNavIconMainUi.visibility = GONE

                if(trx.equals("Home")){
                    trx = "Go_Home"
                }
                else if(trx.equals("Transactions")){
                    trx = "Go_Transactions"
                }
            }
        }
        val item: MenuItem = mBinding.navDrawer.menu.findItem(R.id.drawer_signOut)
        item.setOnMenuItemClickListener(this)
        lifecycleScope.launch() {
            setDataOnNavDrawer()
        }
    }

    private fun setEventListeners() {
        mBinding.ivNavIconMainUi.setOnClickListener {
            lifecycleScope.launch {
                setDataOnNavDrawer()
                appyAnimtionOnNavIcon()
            }
            applyOpenAnimation()
        }
        mBinding.navDrawer.getHeaderView(0).setOnClickListener {
            Toast.makeText(this, "Header", Toast.LENGTH_SHORT).show()
        }
        mBinding.lLNavDrawerFeedback.setOnClickListener {
            giveFeedBack()
        }
        mBinding.lLTellAFreind.setOnClickListener{
            getLinkFromFirebase()
        }
//        lL_helpAndSupport.setOnClickListener {
//            helpAndSupport()
//        }
        mBinding.lLPolicies.setOnClickListener {
            val intent: Intent = Intent(this, PolicyActivity::class.java)
            intent.putExtra("Details", "fromMainUi")
            startActivity(intent)
        }
        mBinding.ivSettings.setOnClickListener {

        }
    }

    // When signOut clicked
    override fun onMenuItemClick(item: MenuItem): Boolean {
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
    }

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
    }
    
    private fun getLinkFromFirebase() {
        mBinding.lLTellAFreind.isEnabled = false
        mBinding.lLTellAFreind.isClickable = false
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
    }
    
    private fun onShareClicked(link: String) {
        Log.i("link", "link: " + link)

        mBinding.lLTellAFreind.isEnabled = true
        mBinding.lLTellAFreind.isClickable = true

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
    }
    
    private fun helpAndSupport() {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "recharge2me.help@gmail.com", null))
        this.startActivity(Intent.createChooser(emailIntent, null))
    }
    
    fun setDataOnNavDrawer(){

        val docRef = db.collection("USERS").document(auth.uid.toString())

        try {
            docRef.get()
                    .addOnSuccessListener { documentSnapshot ->

                        val city = documentSnapshot.toObject<User_googleAndOwn>()
                        val user = city?.user_details
                        val Name: String = user?.name ?:  "R2M Demo"
                        val Reward: String = user?.rewards ?: "0"

                        val tv_navHeader_name  = mBinding.navDrawer.getHeaderView(0).findViewById<TextView>(R.id.tv_navHeader_name);
                        val tv_navHeader_reward  = mBinding.navDrawer.getHeaderView(0).findViewById<TextView>(R.id.tv_navHeader_reward);

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

    private suspend fun appyAnimtionOnNavIcon(){
        mBinding.ivNavIconMainUi.apply {
            animate().
                alpha(0f)
                .duration = 100L

            delay(200)

            animate()
                .alpha(1f)
                .duration = 100L
        }
    }
    
    private fun applyOpenAnimation(){
        mBinding.navDrawer.apply {
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
        mBinding.navDrawer.apply {
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event != null) {
            if(event.action == MotionEvent.ACTION_UP){
                if(mBinding.navDrawer.isVisible){
                    if(onTouchFlag == 1){
                        onTouchFlag = 0
                        lifecycleScope.launch {
                            appyCloseAnimation()
                        }
                    }
                }
            }
            else if(event.action == MotionEvent.ACTION_DOWN){

                if(mBinding.navDrawer.isVisible){
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

        if(!mBinding.navDrawer.isVisible && (set.equals("Home") || set.equals("Profile"))
                                && (trx.equals("Home") || trx.equals("Transactions"))   ){
            if(mBinding.ivNavIconMainUi.isVisible){
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

        if(mBinding.navDrawer.isVisible){
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



