package com.android.msm.mylibrarysearchable;

import android.content.SearchRecentSuggestionsProvider;

public class SearchableProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.android.msm.mylibrarysearchable.SearchableProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;
    public SearchableProvider(){
        setupSuggestions( AUTHORITY, MODE );
    }
}
