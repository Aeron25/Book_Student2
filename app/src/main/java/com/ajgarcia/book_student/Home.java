package com.ajgarcia.book_student;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.imangazaliev.circlemenu.CircleMenu;
import com.imangazaliev.circlemenu.CircleMenuButton;

public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circleMenu);


        circleMenu.setOnItemClickListener(new CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()) {
                    case R.id.student:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showMessage("Student");
                                Intent std = new Intent(Home.this, student.class);
                                startActivity(std);
                            }
                        }, 1000);
                        break;
                    case R.id.book:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showMessage("Book");
                                Intent std = new Intent(Home.this, Book.class);
                                startActivity(std);
                            }
                        }, 1000);
                        break;
                    case R.id.borbook:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showMessage("Borrowed");
                                Intent std = new Intent(Home.this, Borrowed.class);
                                startActivity(std);
                            }
                        }, 1000);
                        break;

                }
            }
        });



        circleMenu.setStateUpdateListener(new CircleMenu.OnStateUpdateListener() {
            @Override
            public void onMenuExpanded() {
                Log.d("CircleMenuStatus", "Expanded");
            }

            @Override
            public void onMenuCollapsed() {
                Log.d("CircleMenuStatus", "Collapsed");
            }
        });
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
