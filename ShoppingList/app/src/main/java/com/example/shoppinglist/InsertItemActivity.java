package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertItemActivity extends AppCompatActivity {

    EditText itemname,quantity;
    DatabaseReference ref;
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_item);
    }

    public void addItem(View view) {
        itemname = (EditText)findViewById(R.id.productnameedit);
        quantity = (EditText)findViewById(R.id.quantityedit);
        ref= FirebaseDatabase.getInstance().getReference().child("Item");
        item = new Item();
        String name = itemname.getText().toString().trim();
        int qnty = Integer.parseInt(quantity.getText().toString().trim());
        item.setName(name);
        item.setQuantity(qnty);
        ref.push().setValue(item);
        Toast.makeText(InsertItemActivity.this,"Item added to shoplist",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}