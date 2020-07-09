package com.example.poketlearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    EditText txtTitle;
    EditText txtDescription;
    EditText txtPrice;
    PoketLearning learning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FirebaseUtil.openFbReference("poketlearning");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        Intent intent = getIntent();
        PoketLearning learning = (PoketLearning) getIntent().getSerializableExtra("Learning");
        if (learning == null) {
            learning = new PoketLearning();
        }
        this.learning = learning;
        txtTitle.setText(learning.getTitle());
        txtDescription.setText(learning.getDescription());
        txtPrice.setText(learning.getPrice());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_menu:
                saveDeal();
                Toast.makeText(this,"Learning Subject Saved", Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;
            case R.id.delete_menu:
                deleteLearning();
                Toast.makeText(this, "Learning Subject Deleted", Toast.LENGTH_SHORT).show();
                backToList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    private void saveDeal() {
        learning.setTitle(txtTitle.getText().toString());
        learning.setDescription(txtDescription.getText().toString());
        learning.setPrice(txtPrice.getText().toString());
        if(learning.getId() == null) {
            mDatabaseReference.push().setValue(learning);
        } else {
            mDatabaseReference.child(learning.getId()).setValue(learning);
        }


    }

    private void deleteLearning() {
        if (learning == null) {
            Toast.makeText(this, "Please save the deal before deleting", Toast.LENGTH_SHORT).show();
            return;
        }
        mDatabaseReference.child(learning.getId()).removeValue();
    }

    private void backToList() {
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }

    private void clean() {
        txtTitle.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtTitle.requestFocus();
    }
}