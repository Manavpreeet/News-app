package com.example.newstime;

public class News {
    private String mTitle, mDate, mCategory, mAuthor, mUrl, mThumbnail, mDescription;

    public News(String title,String url, String date, String category, String author, String thumbnail, String description){
        mTitle = title;
        mDate = date;
        mCategory = category;
        mAuthor = author;
        mUrl = url;
        mThumbnail = thumbnail;
        mDescription = description;
    }

    public String getTitle(){ return mTitle; }
    public String getDate(){ return mDate; }
    public String getCategory(){ return mCategory; }
    public String getAuthor(){ return mAuthor; }
    public String getUrl(){ return mUrl; }
    public String getThumbnail(){ return mThumbnail; }
    public String getDescription(){ return mDescription; }

}
