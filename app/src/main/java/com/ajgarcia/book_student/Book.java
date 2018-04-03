package com.ajgarcia.book_student;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Book extends AppCompatActivity {

    DatabaseHelper myDB;
    EditText editBookID, editName, editAuthor, editPub;
    Button btnAddData;
    Button btnviewAll;
    Button btnDeleteData;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        myDB = new DatabaseHelper(this);

        editBookID = (EditText) findViewById(R.id.e_book);
        editName = (EditText) findViewById(R.id.e_name);
        editAuthor = (EditText) findViewById(R.id.e_author);
        editPub = (EditText) findViewById(R.id.e_pub);


        btnAddData = (Button) findViewById(R.id.btnAdd);
        btnviewAll = (Button) findViewById(R.id.btnView);
        btnDeleteData = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();


    }



    public void DeleteData() {
        btnDeleteData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deletedRows = myDB.deleteBook(editBookID.getText().toString());
                        if (deletedRows > 0) {
                            Toast.makeText(Book.this, "Deleted!", Toast.LENGTH_LONG).show();
                            editBookID.setText(null);
                        }
                        else
                            Toast.makeText(Book.this, "Not Deleted!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDB.updateBook(editBookID.getText().toString(),
                                editName.getText().toString(),
                                editAuthor.getText().toString(),
                                editPub.getText().toString());
                        if (isUpdate == true) {
                            Toast.makeText(Book.this, "Updated!", Toast.LENGTH_LONG).show();
                            editBookID.setText(null);
                            editName.setText(null);
                            editAuthor.setText(null);
                            editPub.setText(null);
                        }
                        else
                            Toast.makeText(Book.this, "Not Updated!", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = myDB.insertBook(editBookID.getText().toString(),
                                editName.getText().toString(),
                                editAuthor.getText().toString(),
                                editPub.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(Book.this, "Inserted", Toast.LENGTH_LONG).show();
                            editBookID.setText(null);
                            editName.setText(null);
                            editAuthor.setText(null);
                            editPub.setText(null);
                        }
                        else
                            Toast.makeText(Book.this, "Not Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDB.viewBook();
                        if (res.getCount() == 0) {

                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("BookId :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Author :" + res.getString(2) + "\n");
                            buffer.append("Published Date :" + res.getString(3) + "\n\n");
                        }

                        showMessage("Book Data", buffer.toString());
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


