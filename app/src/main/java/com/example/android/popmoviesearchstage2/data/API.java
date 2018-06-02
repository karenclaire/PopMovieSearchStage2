package com.example.android.popmoviesearchstage2.data;

public class API {
    static public String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
    static public String API_KEY = " ";
    static public String IMAGE_URL = "http://image.tmdb.org/t/p/";
    static public String IMAGE_SIZE_185 = "w185";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String OVERVIEW = "overview";
    public static final String POSTER_PATH = "poster_path";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String VIDEO = "video";

    private static final String MOVIE_REQUEST_URL = "http://api.themoviedb.org/3/movie/popular?api_key=";
    private static final String MOVIE_TOP_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=";

    //Movie ID must be added
    private static  final String REVIEW_PATH ="http://api.themoviedb.org/3/movie/" + "" + "?&append_to_response=reviews";
    private static  final String TRAILER_PATH ="http://api.themoviedb.org/3/movie/" + "" + "?&append_to_response=trailers";

    public static final String YT_THUMB_URL = "http://img.youtube.com/vi/";


    public static String makeTrailerThumbnailURL(String thumbnailId) {
        return YT_THUMB_URL.concat(thumbnailId).concat("/hqdefault.jpg");
    }

}