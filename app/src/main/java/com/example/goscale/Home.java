package com.example.goscale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AppCompatActivity implements AsyncResponse {
    RecyclerView r1;
    RecyclerView r2;
    EditText e1;
    EditText e2;
    Button b2;
    Button b3;
    int page;
    int totalResults;
    String text;
    String query;
    Bookmark bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        r1 = findViewById(R.id.recyclerview1);
        r2 = findViewById(R.id.recyclerview2);

        e1 = findViewById(R.id.edittext1);
        e2 = findViewById(R.id.edittext2);
        Button b1 = findViewById(R.id.button1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = (e1.getText().toString());
                String url = "https://www.omdbapi.com/?s=" + (query.length() == 0 ? "friends" : query) + "&page=1&apikey=7ee18c5e";

                page = 1;
                e2.setText(Integer.toString(page));

                Viewdata v2 = new Viewdata();
                v2.delegate = Home.this;
                v2.setId(1);
                v2.execute("GET", url);
            }
        });

        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    page = Integer.parseInt((e2.getText()).toString());
                } catch(NumberFormatException e)
                {
                    page = 1;
                }

                String url = "https://www.omdbapi.com/?s=" + (query.length() == 0 ? "friends" : query) + "&page=" + page + "&apikey=7ee18c5e";

                Viewdata v5 = new Viewdata();
                v5.delegate = Home.this;
                v5.setId(1);
                v5.execute("GET", url);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Intent intent2 = getIntent();

        query = intent2.getStringExtra("query");
        text = intent2.getStringExtra("text");
        if(query == null) {
            bookmark = new Bookmark();
            SharedPreferences pref = getApplicationContext().getSharedPreferences("AppData", 0);
            String p = pref.getString("imdbId", null);
            String q = pref.getString("poster", null);
            String r = pref.getString("title", null);
            String s = pref.getString("year", null);
            String t = pref.getString("type", null);

            if(p == null) {
                bookmark.setImdbId(new ArrayList<String>());
            } else if(p.equals("[]")) {
                bookmark.setImdbId(new ArrayList<String>());
            } else {
                bookmark.setImdbId(new ArrayList<>(Arrays.asList((p.substring(1, p.length() - 1)).split(", "))));
            }

            if(q == null) {
                bookmark.setPoster(new ArrayList<String>());
            } else if(q.equals("[]")) {
                bookmark.setPoster(new ArrayList<String>());
            } else {
                bookmark.setPoster(new ArrayList<>(Arrays.asList((q.substring(1, q.length() - 1)).split(", "))));
            }

            if(r == null) {
                bookmark.setTitle(new ArrayList<String>());
            } else if(r.equals("[]")) {
                bookmark.setTitle(new ArrayList<String>());
            } else {
                bookmark.setTitle(new ArrayList<>(Arrays.asList((r.substring(1, r.length() - 1)).split(", "))));
            }

            if(s == null) {
                bookmark.setYear(new ArrayList<String>());
            } else if(s.equals("[]")) {
                bookmark.setYear(new ArrayList<String>());
            } else {
                bookmark.setYear(new ArrayList<>(Arrays.asList((s.substring(1, s.length() - 1)).split(", "))));
            }

            if(t == null) {
                bookmark.setType(new ArrayList<String>());
            } else if(t.equals("[]")) {
                bookmark.setType(new ArrayList<String>());
            } else {
                bookmark.setType(new ArrayList<>(Arrays.asList((t.substring(1, t.length() - 1)).split(", "))));
            }
        }
        else {
            bookmark = new Bookmark();
            bookmark.setImdbId(intent2.getStringArrayListExtra("imdbId"));
            bookmark.setPoster(intent2.getStringArrayListExtra("poster"));
            bookmark.setTitle(intent2.getStringArrayListExtra("title"));
            bookmark.setYear(intent2.getStringArrayListExtra("year"));
            bookmark.setType(intent2.getStringArrayListExtra("type"));
        }
        if(query == null || query.length() == 0) {
            query = "friends";
        }
        page = intent2.getIntExtra("page", 1);
        String website = "https://www.omdbapi.com/?s=" + query + "&page=" + Integer.toString(page) + "&apikey=7ee18c5e";

        List<String> v = bookmark.getImdbId();
        List<String> w = bookmark.getPoster();
        List<String> x = bookmark.getTitle();
        List<String> y = bookmark.getYear();
        List<String> z = bookmark.getType();

        List<Movie> movieList = new ArrayList<>();
        TextView t1 = findViewById(R.id.textview10);

        if(v.size() == 0)
        {
            t1.setVisibility(View.INVISIBLE);
        }
        else
        {
            t1.setVisibility(View.VISIBLE);
        }

        for(int u = 0; u < v.size(); u++) {
            Movie m = new Movie();
            m.setImdbID(v.get(u));
            m.setPoster(w.get(u));
            m.setTitle(x.get(u));
            m.setYear(y.get(u));
            m.setType(z.get(u));
            movieList.add(m);
        }

        r2.setHasFixedSize(true);
        r2.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));

        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(Home.this, R.layout.bookmark, movieList, query, (e1.getText()).toString(), page, bookmark);
        r2.setAdapter(bookmarkAdapter);

        Viewdata v1 = new Viewdata();
        v1.delegate = this;
        v1.setId(1);
        v1.execute("GET", website);
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

    @Override
    public void onBackPressed() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("AppData", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("imdbId", (bookmark.getImdbId()).toString());
        editor.putString("poster", (bookmark.getPoster()).toString());
        editor.putString("title", (bookmark.getTitle()).toString());
        editor.putString("year", (bookmark.getYear()).toString());
        editor.putString("type", (bookmark.getType()).toString());
        editor.commit();

        super.onBackPressed();
    }

    @Override
    public void processFinish(String output, int viewdata) {
        try {
            final List<Movie> movieList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject(output);
            JSONArray movies = jsonObject.getJSONArray("Search");
            totalResults = jsonObject.getInt("totalResults");

            if(text != null) {
                e1.setText(text);
                text = null;
                e2.setText(Integer.toString(page));
            }

            b2 = findViewById(R.id.button2);
            b3 = findViewById(R.id.button3);

            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page -= 1;
                    e2.setText(Integer.toString(page));

                    String url = "https://www.omdbapi.com/?s=" + (query.length() == 0 ? "friends" : query) + "&page=" + Integer.toString(page) + "&apikey=7ee18c5e";

                    Viewdata v3 = new Viewdata();
                    v3.delegate = Home.this;
                    v3.setId(1);
                    v3.execute("GET", url);
                }
            });

            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page += 1;
                    e2.setText(Integer.toString(page));

                    String url = "https://www.omdbapi.com/?s=" + (query.length() == 0 ? "friends" : query) + "&page=" + Integer.toString(page) + "&apikey=7ee18c5e";

                    Viewdata v4 = new Viewdata();
                    v4.delegate = Home.this;
                    v4.setId(1);
                    v4.execute("GET", url);
                }
            });

            for(int i = 0; i < movies.length(); i++)
            {
                Movie a = new Movie();
                JSONObject movie = movies.getJSONObject(i);

                a.setPoster(movie.getString("Poster"));
                a.setTitle(movie.getString("Title"));
                a.setYear(movie.getString("Year"));
                a.setImdbID(movie.getString("imdbID"));
                a.setType(movie.getString("Type"));

                movieList.add(a);
            }

            r1.setHasFixedSize(true);
            r1.setLayoutManager(new GridLayoutManager(Home.this, 2));

            MovieAdapter movieAdapter = new MovieAdapter(Home.this, R.layout.movie, movieList, query, (e1.getText()).toString(), page, bookmark);
            r1.setAdapter(movieAdapter);

            if(page <= 1) {
                b2.setEnabled(false);
            }
            else {
                b2.setEnabled(true);
            }

            if(page >= (totalResults + 9) / 10)
            {
                b3.setEnabled(false);
            }
            else {
                b3.setEnabled(true);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}