package Ui_Front_and_Back_end;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recharge2mePlay.recharge2me.R;

import java.util.Map;

import LogInSignIn_Entry.DataTypes.CreateAccount_userDetails;
import LogInSignIn_Entry.DataTypes.User_googleAndOwn;
import custom_Loading_Dialog.LoadingDialog;

import static android.content.ContentValues.TAG;

public class Ui_Profile extends Fragment {

    View view;

    TextView tv_profile_userName,
             tv_profile_userNumber,
             tv_profile_userEmail,
             tv_profile_userRewards;

    LoadingDialog loadingDialog;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    public Ui_Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ui__profile, container, false);

        tv_profile_userName = view.findViewById(R.id.tv_profile_userName);
        tv_profile_userNumber = view.findViewById(R.id.tv_profile_userNumber);
        tv_profile_userEmail = view.findViewById(R.id.tv_profile_userEmail);
        tv_profile_userRewards = view.findViewById(R.id.tv_profile_userRewards);

        loadingDialog = new LoadingDialog((Main_UserInterface) requireActivity());

        firebaseAuth = firebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        getProfileData();

        return view;
    }

    public void getProfileData(){

        loadingDialog.startLoading();
        DocumentReference docRef = db.collection("USERS").document(firebaseAuth.getUid());

        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Log.d("Data fect: ", "DATA Fetch: " + documentSnapshot.getData());

                User_googleAndOwn data = documentSnapshot.toObject(User_googleAndOwn.class);
                CreateAccount_userDetails userDetails = data.getUser_details();

                tv_profile_userName.setText(userDetails.getName());
                tv_profile_userNumber.setText("+91 " + userDetails.getNumber());
                tv_profile_userEmail.setText(userDetails.getEmail());
                tv_profile_userRewards.setText("₹ " + userDetails.getRewards());

                loadingDialog.stopLoading();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingDialog.stopLoading();
                Toast.makeText((Main_UserInterface) requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}