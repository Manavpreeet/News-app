package com.example.newstime.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newstime.MainActivity;
import com.example.newstime.News;
import com.example.newstime.R;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list, parent, false);
        }

        News currentItem = getItem(position);

        TextView titleView = (TextView) listItemView.findViewById(R.id.news_text);
        titleView.setText(currentItem.getTitle());

        TextView categoryView = (TextView) listItemView.findViewById(R.id.category_text_view);
        categoryView.setText(currentItem.getCategory());

        TextView authorView = (TextView) listItemView.findViewById(R.id.author_text_view);
        authorView.setText(currentItem.getAuthor());

        TextView dateView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateView.setText(currentItem.getDate());

        TextView descriptionView = (TextView) listItemView.findViewById(R.id.news_description);
        descriptionView.setText(currentItem.getDescription());

        ImageView thumbnailView = (ImageView) listItemView.findViewById(R.id.thumbnail);
//        thumbnailView(new MainActivity.ImageLoadTask(url, thumbnailView).execute());
        new MainActivity.ImageLoadTask(currentItem.getThumbnail(), thumbnailView).execute();

        return listItemView;
    }
}
