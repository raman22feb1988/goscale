package com.example.goscale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context con;
    int _resource;
    List<Movie> lival;

    String query;
    String text;
    int page;
    Bookmark bookmark;

    View listItem;
    View thingView;
    ImageView ima;
    TextView tx1;
    TextView tx2;

    public MovieAdapter(Context context, int resource, List<Movie> li, String query, String text, int page, Bookmark bookmark) {
        // TODO Auto-generated constructor stub
        con = context;
        _resource = resource;
        lival = li;

        this.query = query;
        this.text = text;
        this.page = page;
        this.bookmark = bookmark;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);

            thingView = itemView;

            ima = itemView.findViewById(R.id.imageview1);
            tx1 = itemView.findViewById(R.id.textview1);
            tx2 = itemView.findViewById(R.id.textview2);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        listItem = layoutInflater.inflate(_resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = lival.get(position);

        final ImageView i1 = thingView.findViewById(R.id.imageview3);

        final String poster = movie.getPoster();
        final String title = movie.getTitle();
        final String year = movie.getYear();
        final String imdbId = movie.getImdbID();
        final String type = movie.getType();

        new ImageLoadTask(poster, ima).execute();
        tx1.setText(title);
        tx2.setText(year);

        final boolean[] bookmarked = new boolean[1];

        if((bookmark.getImdbId()).contains(imdbId)) {
            i1.setImageResource(R.drawable.black);
            bookmarked[0] = true;
        }
        else {
            i1.setImageResource(R.drawable.white);
            bookmarked[0] = false;
        }

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bookmarked[0])
                {
                    bookmarked[0] = false;
                    i1.setImageResource(R.drawable.white);
                    int index = (bookmark.getImdbId()).indexOf(imdbId);
                    (bookmark.getImdbId()).remove(index);
                    (bookmark.getPoster()).remove(index);
                    (bookmark.getTitle()).remove(index);
                    (bookmark.getYear()).remove(index);
                    (bookmark.getType()).remove(index);
                }
                else {
                    bookmarked[0] = true;
                    i1.setImageResource(R.drawable.black);
                    (bookmark.getImdbId()).add(imdbId);
                    (bookmark.getPoster()).add(poster);
                    (bookmark.getTitle()).add(title);
                    (bookmark.getYear()).add(year);
                    (bookmark.getType()).add(type);
                }

                Intent intent4 = new Intent(con, Home.class);
                intent4.putStringArrayListExtra("imdbId", bookmark.getImdbId());
                intent4.putStringArrayListExtra("poster", bookmark.getPoster());
                intent4.putStringArrayListExtra("title", bookmark.getTitle());
                intent4.putStringArrayListExtra("year", bookmark.getYear());
                intent4.putStringArrayListExtra("type", bookmark.getType());

                intent4.putExtra("query", query);
                intent4.putExtra("text", text);
                intent4.putExtra("page", page);
                con.startActivity(intent4);
                ((Activity) con).finish();
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(con, Detail.class);

                Bundle bundle = new Bundle();
                bundle.putParcelable("movie", movie);
                intent1.putExtras(bundle);

                intent1.putStringArrayListExtra("imdbId", bookmark.getImdbId());
                intent1.putStringArrayListExtra("poster", bookmark.getPoster());
                intent1.putStringArrayListExtra("title", bookmark.getTitle());
                intent1.putStringArrayListExtra("year", bookmark.getYear());
                intent1.putStringArrayListExtra("type", bookmark.getType());

                intent1.putExtra("query", query);
                intent1.putExtra("text", text);
                intent1.putExtra("page", page);

                con.startActivity(intent1);
                ((Activity) con).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lival.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}