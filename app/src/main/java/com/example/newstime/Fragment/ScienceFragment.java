package com.example.newstime.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.newstime.Adapter.NewsAdapter;
import com.example.newstime.News;
import com.example.newstime.Query.QueryUtils;
import com.example.newstime.R;

import java.util.ArrayList;

public class ScienceFragment extends Fragment {

    private NewsAdapter mAdapter;
    private static String REQUEST_URL = "https://newsapi.org/v2/everything?q=science&apiKey=525c5ea00bf74a0aab295ba1a1155746";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_item, container, false);

        mAdapter = new NewsAdapter(getContext(), new ArrayList<News>());
        ListView listView = (ListView) view.findViewById(R.id.list);

        listView.setAdapter(mAdapter);

        if (mAdapter == null && mAdapter.isEmpty())
            view = inflater.inflate(R.layout.no_content_view, container, false);
        else {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News currentNews = mAdapter.getItem(position);
                    Uri newUri = Uri.parse(currentNews.getUrl());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newUri);
                    startActivity(websiteIntent);
                }
            });

            ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {

                ScienceNewsAsyncTask task = new ScienceNewsAsyncTask();
                task.execute(REQUEST_URL);
            } else {
                view = inflater.inflate(R.layout.no_connection_view, container, false);
            }
        }
        return view;
    }

    private class ScienceNewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(String... urls) {
            if( urls.length < 1 || urls[0] == null)
                return null;

            ArrayList<News> result = QueryUtils.fetchData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<News> data) {
            mAdapter.clear();
            if(data != null && !data.isEmpty())
                mAdapter.addAll(data);

        }
    }
}