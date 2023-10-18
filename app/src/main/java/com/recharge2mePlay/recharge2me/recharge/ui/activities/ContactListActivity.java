package com.recharge2mePlay.recharge2me.recharge.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
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

import com.recharge2mePlay.recharge2me.recharge.models.ContactType;
import com.recharge2mePlay.recharge2me.recharge.ui.adapters.ContactListAdapter;

import com.recharge2mePlay.recharge2me.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactListActivity extends AppCompatActivity {


    RecyclerView rv_contactList;
    ContactListAdapter contacatList_adpter;

    ArrayList<ContactType> contacts = new ArrayList<ContactType>();

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

        rv_contactList.addOnItemTouchListener(new ContactListAdapter.contactRecyclerTouchListener(this, rv_contactList,
                new ContactListAdapter.onContactClick() {
            @Override
            public void onContactCardClick(View view, String number) {
                setNumber(number);
            }
        }));
    }

    // It will return the contacts List
    private ArrayList<ContactType> getContacts(){

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        ArrayList<ContactType> contactsList = new ArrayList<ContactType>();


        while(cursor.moveToNext()){

            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            ContactType contact = new ContactType(name, number);
            contactsList.add(contact);

        }

        return contactsList;
    }

    // It will set the data on RecyclerView
    private void setDataonRecyclerView(ArrayList<ContactType> contactsList){

        Collections.sort(contactsList, new Comparator<ContactType>() {
            @Override
            public int compare(ContactType contact_type, ContactType t1) {
                return contact_type.getName().compareTo(t1.getName());
            }
        });

        rv_contactList = findViewById(R.id.rv_contactList);
        contacatList_adpter = new ContactListAdapter(contactsList, this);
        rv_contactList.setAdapter(contacatList_adpter);
        rv_contactList.setLayoutManager(new LinearLayoutManager(this));
    }

    // This will set the Filter Contact List on RecyclerView
    private void setFilteredListOnRecyclerView(String text, ArrayList<ContactType> list){

        ArrayList<ContactType> filteredList = new ArrayList<>();

        if(list != null){
            for(ContactType c : list){
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