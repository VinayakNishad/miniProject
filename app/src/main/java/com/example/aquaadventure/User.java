package com.example.aquaadventure;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText code,name,credits,marks;
    Button btnIn,btnDis,btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        code=findViewById(R.id.edtCode);
        name=findViewById(R.id.edtName);
        credits=findViewById(R.id.edtCredits);
        marks=findViewById(R.id.edtMarks);

        btnIn=findViewById(R.id.btnIn);
        btnDis=findViewById(R.id.btnDis);
        btnDel=findViewById(R.id.btnDel);

        DBHelper db=new DBHelper(MainActivity.this);

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Subcode=code.getText().toString();
                String Subname=name.getText().toString();
                String Subcredits=credits.getText().toString();
                String Submarks=marks.getText().toString();

                Boolean result =db.InsertU(Subcode,Subname,Subcredits,Submarks);
                if(result){
                    Toast.makeText(MainActivity.this," data inserted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "no data inserted", Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cur= db.Display();
                if(cur.getCount()>0){
                    StringBuffer buffer=new StringBuffer();
                    while(cur.moveToNext()){
                        buffer.append(cur.getString(0)+"\n");
                        buffer.append(cur.getString(1)+"\n");
                        buffer.append(cur.getString(2)+"\n");
                        buffer.append(cur.getString(3)+"\n \n");
                    }
                    Toast.makeText(MainActivity.this, buffer.toString(), Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Subcode=code.getText().toString();
                Boolean result=db.deleteU(Subcode);

                if(result){
                    Toast.makeText(MainActivity.this," data deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "no data deleted", Toast.LENGTH_SHORT).show();
                }


            }
        });








    }
}