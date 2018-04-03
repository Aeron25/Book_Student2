package com.ajgarcia.book_student;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Borrowed extends AppCompatActivity {


    DatabaseHelper myDB;
    EditText editStudent, editBook, editBorDate, editRetDate, editRemarks;
    Button btnAddData;
    Button btnviewAll;
    Spinner spinstud_id;
    Spinner spinbook_id;
    List<String> listid_forstud=new ArrayList<>();
    ArrayAdapter<String> for_studid;
    List<String> listid_forbook=new ArrayList<>();
    ArrayAdapter<String> for_bookid;
    String showstdtname;
    String showbkname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed);
        myDB = new DatabaseHelper(this);

        spinstud_id = (Spinner)findViewById(R.id.spinstud);
        editStudent = (EditText) findViewById(R.id.e_student);

        spinbook_id = (Spinner)findViewById(R.id.spinbook);
        editBook = (EditText) findViewById(R.id.e_book);

        editBorDate = (EditText) findViewById(R.id.e_bdate);
        editRetDate = (EditText) findViewById(R.id.e_rdate);
        editRemarks = (EditText) findViewById(R.id.e_remarks);


        btnAddData = (Button) findViewById(R.id.btnAdd);
        btnviewAll = (Button) findViewById(R.id.btnView);
        AddData();
        viewData();
        forstudid();
        dataforstudid();
        forbookid();
        dataforbookid();
    }

    public void forstudid(){
        spinstud_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                showstdtname=(adapterView.getItemAtPosition(position).toString());
                Detect();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void dataforstudid(){
        listid_forstud=myDB.student_id();
        for_studid=new ArrayAdapter<String>(Borrowed.this,android.R.layout.simple_spinner_dropdown_item,android.R.id.text1,listid_forstud);
        spinstud_id.setAdapter(for_studid);
    }
///---------------------------------------------------------------------------------------------------------------------------------------
    public void forbookid(){
        spinbook_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView2, View view, int position, long l) {
                showbkname=(adapterView2.getItemAtPosition(position).toString());
                Detect2();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView2) {

            }
        });
    }
    public void dataforbookid(){
        listid_forbook=myDB.book_id();
        for_bookid=new ArrayAdapter<String>(Borrowed.this,android.R.layout.simple_spinner_dropdown_item,android.R.id.text1,listid_forbook);
        spinbook_id.setAdapter(for_bookid);
    }

//---------------------------------------------------------------------------------------------------------------------------------------


    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean isInserted = myDB.insertBorrowed(spinstud_id.getSelectedItem().toString(),
                                editStudent.getText().toString(),
                                spinbook_id.getSelectedItem().toString(),
                                editBook.getText().toString(),
                                editBorDate.getText().toString(),
                                editRetDate.getText().toString(),
                                editRemarks.getText().toString());
                        if (isInserted == true){
                            Toast.makeText(Borrowed.this, "Added", Toast.LENGTH_LONG).show();
                        editBorDate.setText(null);
                        editRetDate.setText(null);
                        editRemarks.setText(null);
                        }
                        else
                            Toast.makeText(Borrowed.this, "Not Added", Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    public void viewData() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDB.viewBorrowed();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Student ID :" + res.getString(0) + "\n");
                            buffer.append("Student Name :" + res.getString(1) + "\n");
                            buffer.append("Book ID :" + res.getString(2) + "\n");
                            buffer.append("Book Name :" + res.getString(3) + "\n");
                            buffer.append("Borrowed Date :" + res.getString(4) + "\n");
                            buffer.append("Return Date :" + res.getString(5) + "\n");
                            buffer.append("Remarks :" + res.getString(6) + "\n\n");



                        }
                        //Show all data
                        showMessage("Borrowed Data", buffer.toString());
                    }
                }
        );
    }

    public void Detect() {

        try {
                        Cursor dec = myDB.viewSTname(showstdtname);
                        if (dec.getCount() == 0) {
                            showMessage("Error", "Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (dec.moveToNext()) {
                            buffer.append(dec.getColumnIndex("name"));
                                    editStudent.setText(dec.getString(0));

                        }
        }catch (Exception e){
            Toast.makeText(Borrowed.this,"INVALID!",Toast.LENGTH_SHORT).show();
        }

    }

    public void Detect2() {



        try {
            Cursor dec2 = myDB.viewBKname(showbkname);
            if (dec2.getCount() == 0) {
                showMessage("Error", "Nothing found");
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while (dec2.moveToNext()) {
                buffer.append(dec2.getColumnIndex("name"));
                editBook.setText(dec2.getString(0));

            }
        }catch (Exception e){
            Toast.makeText(Borrowed.this,"INVALID!",Toast.LENGTH_SHORT).show();
        }

    }





    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}


