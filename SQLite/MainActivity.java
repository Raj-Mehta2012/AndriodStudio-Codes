import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etText;
    Button btAdd;
    ListView listView;

    DatabaseHelper databaseHelper;
    ArrayList arrayList;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign variables
        etText=findViewById(R.id.et_text);
        btAdd=findViewById(R.id.bt_add);
        listView=findViewById(R.id.list_view);

        //init databasehelper to Main activity
        databaseHelper =new DatabaseHelper(MainActivity.this);

        //add Dbase to Arraylist
        arrayList=databaseHelper.getAllText();

        //init array adapter
        arrayAdapter =new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);

        //set array adap to listview
        listView.setAdapter(arrayAdapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text from Edit Text
                String text=etText.getText().toString();

                if(!text.isEmpty()){
                    if(databaseHelper.addText(text)){
                        etText.setText("");  //Clears EditText

                        //display toast
                        Toast.makeText(getApplicationContext(),"Data Inserted ...",Toast.LENGTH_SHORT).show();

                        //clear arraylist
                        arrayList.clear();
                        arrayList.addAll(databaseHelper.getAllText());

                        //refresh listview data
                        arrayAdapter.notifyDataSetChanged();
                        listView.invalidateViews();  //is used to tell the ListView to invalidate all its child item views (redraw them).
                        listView.refreshDrawableState();

                    }
                }
            }
        });
    }
}


