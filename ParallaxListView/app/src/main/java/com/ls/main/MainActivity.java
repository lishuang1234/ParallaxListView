package com.ls.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ls.view.ParallaxListView;


public class MainActivity extends ActionBarActivity {
    private ParallaxListView listView;
    private ViewGroup mHeaderViewRoot;
    private View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ParallaxListView) findViewById(R.id.parallaxListView);
        mHeaderViewRoot = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.listview_header,
                null);
        mHeaderView = mHeaderViewRoot.findViewById(R.id.ig_header);
        listView.addHeaderView(mHeaderViewRoot);
        listView.setHeaderView(mHeaderView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, new String[]{"First Item",
                "Second Item", "Third Item", "Fifth Item", "Sixth Item", "Seventh Item",
                "Eighth Item", "Ninth Item", "Tenth Item", "....."});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("item  " + id);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            listView.setMaxHeightZoom(500);
        }
    }
}
