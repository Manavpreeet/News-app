package com.example.newstime.Query;

import android.text.TextUtils;
import android.util.Log;

import com.example.newstime.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {}

    public static ArrayList<News> extractData(String newsJson) {
        if(TextUtils.isEmpty(newsJson)) return null;

        ArrayList<News> news = new ArrayList<>();

        try{
            JSONObject jsonRootObject = new JSONObject(newsJson);
            JSONArray results = jsonRootObject.optJSONArray("articles");

            for(int i=0; i<results.length(); i++){
                JSONObject resultData = results.getJSONObject(i);
                JSONObject source = resultData.getJSONObject("source");
                String title = resultData.getString("title");
                String category = source.getString("name");
                String url = resultData.getString("url");
                String date = resultData.getString("publishedAt");
                String author = resultData.getString("author");
                String urlThumbnail = resultData.getString("urlToImage");
                String description = resultData.getString("description");
                if (author != null) {
                    author = "by " + author;
                }else{
                    author = "No Author ..";
                }

                news.add(new News(title, url, date, category, author, urlThumbnail, description));
            }

        } catch (JSONException e) {
            Log.e("QuerUtlis","Problem parsing the data", e);
        }
        return news;
    }

    private static URL creatUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error while creating url ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStram(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseMessage());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem while retrieving JSON response", e);
        }finally {
            if(urlConnection != null)
                urlConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }
        return jsonResponse;
    }

    public static String readFromStram(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String data = reader.readLine();

            while(data != null){
                output.append(data);
                data = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<News> fetchData(String requestUrl){
        URL url = creatUrl(requestUrl);
        String jsonResponse = null;

        try{
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stram.", e);
        }

        ArrayList<News> news = extractData(jsonResponse);
        return news;
    }
}
