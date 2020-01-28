package com.teamcs.pythondownloader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listview;
    String[] site_name,site_url;
    int[] site_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();
        listview = (ListView) findViewById(R.id.listview);
        site_name = new String[] {"Pornhub","xvideos","sex","XNXX","RED TUBE","X TUBE","Youtube"};
        site_url = new String[] {"https://pornhub.com","https://xvideos.com","https://sex.com","https://www.xnxx.com/","https://www.redtube.com/","https://www.xtube.com/","https://youtube.com"};
        site_image = new int[] {R.mipmap.ic_launcher,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher,R.mipmap.ic_launcher_round,R.mipmap.ic_launcher,R.mipmap.ic_launcher,R.mipmap.ic_launcher} ;


        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, site_name);
        listview.setAdapter(adapter);

        // ListView Item Click Listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // start next activity
                Intent web_view_intent = new Intent(getBaseContext(), webBrowser.class);
                web_view_intent.putExtra("url",site_url[position]);
                startActivity(web_view_intent);
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.simple_listview_layout_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.text1);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.listview_image);
            //Typeface tf= Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/pyidaungsu.ttf");
            //textView.setTypeface(tf);
            textView.setText(values[position]);
            //imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setImageResource(site_image[position]);
            return rowView;
        }
    }
}
