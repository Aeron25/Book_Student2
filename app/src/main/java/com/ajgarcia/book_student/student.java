package com.ajgarcia.book_student;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class student extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editStdID, editName, editCourse, editSex;
    Button btnAddData;
    Button btnviewAll;
    Button btnDeleteData;
    Button btnUpdate;
    Button B_Back;
    Spinner spinner;
    String semesterr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        myDB = new DatabaseHelper(this);


        editStdID = (EditText) findViewById(R.id.e_book);
        editName = (EditText) findViewById(R.id.e_name);
        editCourse = (EditText) findViewById(R.id.e_author);
        editSex = (EditText) findViewById(R.id.e_pub);

        spinner = (Spinner)findViewById(R.id.e_spinner);

        btnAddData = (Button) findViewById(R.id.btnAdd);
        btnviewAll = (Button) findViewById(R.id.btnView);
        btnDeleteData = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);


        AddData();
        viewData();
        UpdateData();
        DeleteData();


        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(student.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.semester));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);


    }



    public void DeleteData() {
        btnDeleteData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDB.deleteStudent(editStdID.getText().toString());
                        if (deletedRows > 0){
                            Toast.makeText(student.this, "Deleted!", Toast.LENGTH_LONG).show();
                        editStdID.setText(null);
                        }
                        else
                            Toast.makeText(student.this, "Not Deleted!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        semesterr = spinner.getSelectedItem().toString();
                        boolean isUpdate = myDB.updateStudent(editStdID.getText().toString(),
                                editName.getText().toString(),
                                editCourse.getText().toString(),
                                editSex.getText().toString(),
                                semesterr);
                        if (isUpdate == true){
                            Toast.makeText(student.this, "Updated!", Toast.LENGTH_LONG).show();
                            editStdID.setText(null);
                            editName.setText(null);
                            editCourse.setText(null);
                            editSex.setText(null);
                        }
                        else
                            Toast.makeText(student.this, "Not Updated!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        semesterr = spinner.getSelectedItem().toString();
                        boolean isInserted = myDB.insertStudent(editStdID.getText().toString(),
                                editName.getText().toString(),
                                editCourse.getText().toString(),
                                editSex.getText().toString(),
                                semesterr);
                        if (isInserted == true){
                            Toast.makeText(student.this, "Added", Toast.LENGTH_LONG).show();
                        editStdID.setText(null);
                        editName.setText(null);
                        editCourse.setText(null);
                        editSex.setText(null);
                    }
                        else
                            Toast.makeText(student.this, "Not Added", Toast.LENGTH_LONG).show();
                    }
                }

        );

    }

    public void viewData() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDB.viewStudent();
                        if (res.getCount() == 0) {
                            // show message
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Student Id :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Course :" + res.getString(2) + "\n");
                            buffer.append("Sex :" + res.getString(3) + "\n");
                            buffer.append("Semester :" + res.getString(4) + "\n\n");



                        }
                        //Show all data
                        showMessage("Student Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}


