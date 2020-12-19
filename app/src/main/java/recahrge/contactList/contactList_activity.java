package recahrge.contactList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import recahrge.DataTypes.contactList_dataType.contact_type;
import recahrge.myAdapters.ContacatList_Adpter;

import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class contactList_activity extends AppCompatActivity {


    RecyclerView rv_contactList;
    ContacatList_Adpter contacatList_adpter;

    ArrayList<contact_type> contacts = new ArrayList<contact_type>();

    EditText et_contactList_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        et_contactList_search = findViewById(R.id.et_contactList_search);


        // Search in Contact list by Name or Number
        et_contactList_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setFilteredListOnRecyclerView(editable.toString(), contacts);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS}, 1);
        }
        else{
            contacts = getContacts();
            setDataonRecyclerView(contacts);
        }

        rv_contactList.addOnItemTouchListener(new ContacatList_Adpter.contactRecyclerTouchListener(this, rv_contactList,
                new ContacatList_Adpter.onContactClick() {
            @Override
            public void onContactCardClick(View view, String number) {
                setNumber(number);
            }
        }));
    }

    // It will return the contacts List
    private ArrayList<contact_type> getContacts(){

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        ArrayList<contact_type> contactsList = new ArrayList<contact_type>();


        while(cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contact_type  contact = new contact_type(name, number);
            contactsList.add(contact);

        }

        return contactsList;
    }

    // It will set the data on RecyclerView
    private void setDataonRecyclerView(ArrayList<contact_type> contactsList){

        Collections.sort(contactsList, new Comparator<contact_type>() {
            @Override
            public int compare(contact_type contact_type, contact_type t1) {
                return contact_type.getName().compareTo(t1.getName());
            }
        });

        rv_contactList = findViewById(R.id.rv_contactList);
        contacatList_adpter = new ContacatList_Adpter(contactsList, this);
        rv_contactList.setAdapter(contacatList_adpter);
        rv_contactList.setLayoutManager(new LinearLayoutManager(this));
    }

    // This will set the Filter Contact List on RecyclerView
    private void setFilteredListOnRecyclerView(String text, ArrayList<contact_type> list){

        ArrayList<contact_type> filteredList = new ArrayList<>();

        if(list != null){
            for(contact_type c : list){
                if(c.getName().toLowerCase().contains(text.toLowerCase()) || c.getNumber().toLowerCase().contains(text.toLowerCase())){
                    filteredList.add(c);
                }
            }
            contacatList_adpter.setFilterList(filteredList);
        }

    }

    // it will ask the Reed permission for contacts to user
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if(grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                getContacts();
            }
        }
    }

    public void setNumber(String number){
        Intent intent = new Intent();
        intent.putExtra("number", number);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}