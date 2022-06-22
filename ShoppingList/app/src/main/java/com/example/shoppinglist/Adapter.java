package com.example.shoppinglist;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Item> list;

    public interface OnClickListener{
        void onDeleteClick(int position);
    }

    public Adapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = list.get(position);
        holder.itemname.setText(item.getName());
        holder.amount.setText(item.getQuantity());
        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(item.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemname,amount;
        ImageButton delbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.itemname);
            amount = itemView.findViewById(R.id.amount);
            delbtn = itemView.findViewById(R.id.delbtn);
        }
    }

    public void deleteItem(String name){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query itemQuery = ref.child("Item").orderByChild("name").equalTo(name);

        itemQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnap: dataSnapshot.getChildren()) {
                    itemSnap.getRef().removeValue();
                    //IntentSneaker sneaker = new IntentSneaker();
                    //sneaker.switchActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public static class IntentSneaker extends AppCompatActivity {
        public void switchActivity(){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
