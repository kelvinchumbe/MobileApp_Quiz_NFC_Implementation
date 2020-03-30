package com.example.mobileapp_quiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationForm extends Fragment {
    private final String mypreferences = "Registration";
    private final String name = "Name";
    private final String email = "Email";
    private final String id = "Id";
    private final String gender = "Gender";
    private final String department = "Department";
    private final String session = "Session";
    private final String password = "Password";

    private EditText name_edit, password_edit, email_edit, id_edit;
    private Spinner department_edit, session_edit;
    private RadioGroup gender_edit;
    private String checked_gender;

    private SharedPreferences sharedPreferences;

    Database mydb;


    public RegistrationForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mydb = new Database(getActivity());

        Button submitbtn;
        Button savebtn;
        final Button readbtn;
        Button deletebtn;
        Button updatebtn;

        final View view = inflater.inflate(R.layout.fragment_registration_form, container, false);
//        submitbtn = view.findViewById(R.id.addbtn);
        savebtn = view.findViewById(R.id.savebtn);
        readbtn = view.findViewById(R.id.readbtn);
        deletebtn = view.findViewById(R.id.deletebtn);
        updatebtn = view.findViewById(R.id.updatebtn);

        name_edit = view.findViewById(R.id.name);
        password_edit = view.findViewById(R.id.password);
        email_edit = view.findViewById(R.id.email);
        id_edit = view.findViewById(R.id.student_id);
        gender_edit = view.findViewById(R.id.gender);
//        department_edit = view.findViewById(R.id.department);
//        session_edit = view.findViewById(R.id.session);


        gender_edit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked_button = view.findViewById(checkedId);
                checked_gender = checked_button.getText().toString();
            }
        });


        sharedPreferences = this.getActivity().getSharedPreferences(mypreferences, Context.MODE_PRIVATE);
//        getData();

//        submitbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addEntry();
//
//                if(!addEntry()){
//                    Toast toast = Toast.makeText(getActivity(), "Unable to Save", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//                else{
//                    Intent intent = new Intent(getActivity(), Submitted.class);
//                    startActivity(intent);
//                }
//
//            }
//        });

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.insertData(name_edit.getText().toString(), password_edit.getText().toString(), email_edit.getText().toString());
                Toast toast = Toast.makeText(getActivity(), "Record Saved", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor results = mydb.readData();
                if(results.getCount() == 0){
                    create_alertDialog("Error", "No data in DB");
                    return;
                }
                StringBuffer sb = new StringBuffer();
                while (results.moveToNext()){
                    sb.append("RegNo: " +  results.getString(0) + " \n");
                    sb.append("Name: " +  results.getString(1) + " \n");
                    sb.append("Password: " +  results.getString(2) + " \n");
                    sb.append("Email: " +  results.getString(3) + " \n\n");
                }

                create_alertDialog("Student Records", sb.toString());
            }

        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = Integer.parseInt(id_edit.getText().toString());
                mydb.delete(id);
                Toast toast = Toast.makeText(getActivity(), "Record Deleted", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = Integer.parseInt(id_edit.getText().toString());
                mydb.update(id, name_edit.getText().toString(), password_edit.getText().toString(), email_edit.getText().toString());
                Toast toast = Toast.makeText(getActivity(), "Record Updated", Toast.LENGTH_SHORT);
                toast.show();
            }
        });



        getData();

        // Inflate the layout for this fragment
        return view;
    }

    public boolean addEntry(){
        // Save entries to shared preference
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean saved = true;

        if(name_edit.getText().toString().isEmpty()){
            name_edit.setError("Enter Your Name!");
            saved = false;
        }
        else{
            editor.putString(name, name_edit.getText().toString());
        }

        if(email_edit.getText().toString().isEmpty()){
            email_edit.setError("Enter Your Email!");
            saved = false;
        }
        else{
            editor.putString(email, email_edit.getText().toString());
        }

        if(password_edit.getText().toString().isEmpty()){
            password_edit.setError("Enter Your Password.!");
            saved = false;
        }
        else{
            editor.putString(password, password_edit.getText().toString());
        }


        editor.commit();

        return saved;
    }

    public void getData(){
        // Gets name and email and displays them on the respective textviews/editviews
            String savedname = sharedPreferences.getString(name,"");
            String savedemail = sharedPreferences.getString(email,"");
            String savedpassword = sharedPreferences.getString(password,"");

            name_edit.setText(savedname);
            email_edit.setText(savedemail);
            password_edit.setText(savedpassword);

    }

    public void create_alertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.show();
    }

}
