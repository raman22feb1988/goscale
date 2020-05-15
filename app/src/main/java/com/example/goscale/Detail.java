package com.example.goscale;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Detail extends AppCompatActivity {
    String query;
    String text;
    int page;

    ArrayList<String> p;
    ArrayList<String> q;
    ArrayList<String> r;
    ArrayList<String> s;
    ArrayList<String> t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent1 = getIntent();
        Movie movie = intent1.getParcelableExtra("movie");
        p = intent1.getStringArrayListExtra("imdbId");
        q = intent1.getStringArrayListExtra("poster");
        r = intent1.getStringArrayListExtra("title");
        s = intent1.getStringArrayListExtra("year");
        t = intent1.getStringArrayListExtra("type");

        query = intent1.getStringExtra("query");
        text = intent1.getStringExtra("text");
        page = intent1.getIntExtra("page", 1);

        final ImageView i1 = findViewById(R.id.imageview2);
        TextView t1 = findViewById(R.id.textview4);
        TextView t2 = findViewById(R.id.textview5);
        TextView t3 = findViewById(R.id.textview6);
        TextView t4 = findViewById(R.id.textview7);
        Button b1 = findViewById(R.id.button4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Detail.this, Home.class);
                intent3.putStringArrayListExtra("imdbId", p);
                intent3.putStringArrayListExtra("poster", q);
                intent3.putStringArrayListExtra("title", r);
                intent3.putStringArrayListExtra("year", s);
                intent3.putStringArrayListExtra("type", t);

                intent3.putExtra("query", query);
                intent3.putExtra("text", text);
                intent3.putExtra("page", page);
                startActivity(intent3);
                finish();
            }
        });

        final String poster = movie.getPoster();
        final String title = movie.getTitle();
        final String year = movie.getYear();
        final String imdbId = movie.getImdbID();
        final String type = movie.getType();

        new ImageLoadTask(poster, i1).execute();
        t1.setText("Title: " + title);
        t2.setText("Year: " + year);
        t3.setText("Imdb Id: " + imdbId);
        t4.setText("Type: " + type);

        final ImageView i2 = findViewById(R.id.imageview4);
        final boolean[] bookmarked = new boolean[1];

        if(p.contains(imdbId)) {
            i2.setImageResource(R.drawable.black);
            bookmarked[0] = true;
        }
        else {
            i2.setImageResource(R.drawable.white);
            bookmarked[0] = false;
        }

        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bookmarked[0])
                {
                    bookmarked[0] = false;
                    i2.setImageResource(R.drawable.white);
                    int index = p.indexOf(imdbId);
                    p.remove(index);
                    q.remove(index);
                    r.remove(index);
                    s.remove(index);
                    t.remove(index);
                }
                else {
                    bookmarked[0] = true;
                    i2.setImageResource(R.drawable.black);
                    p.add(imdbId);
                    q.add(poster);
                    s.add(title);
                    r.add(year);
                    t.add(type);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        Intent intent2 = new Intent(Detail.this, Home.class);
        intent2.putStringArrayListExtra("imdbId", p);
        intent2.putStringArrayListExtra("poster", q);
        intent2.putStringArrayListExtra("title", r);
        intent2.putStringArrayListExtra("year", s);
        intent2.putStringArrayListExtra("type", t);

        intent2.putExtra("query", query);
        intent2.putExtra("text", text);
        intent2.putExtra("page", page);
        startActivity(intent2);
        finish();
    }
}