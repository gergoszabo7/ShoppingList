package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertItemActivity extends AppCompatActivity {

    EditText itemname,quantity;
    DatabaseReference ref;
    Item item;
    Button btnadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);
        itemname = (EditText)findViewById(R.id.productnameedit);
        quantity = (EditText)findViewById(R.id.quantityedit);
        btnadd = (Button)findViewById(R.id.addbutton);
        ref= FirebaseDatabase.getInstance().getReference().child("Item");
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = new Item();
                String name = itemname.getText().toString().trim();
                int qnty = Integer.parseInt(quantity.getText().toString().trim());
                item.setName(name);
                item.setQuantity(qnty);
                ref.push().setValue(item);
                Toast.makeText(InsertItemActivity.this,"Item added to shoplist",Toast.LENGTH_LONG).show();
                switchActivity();
            }
        });
    }

    public void switchActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}