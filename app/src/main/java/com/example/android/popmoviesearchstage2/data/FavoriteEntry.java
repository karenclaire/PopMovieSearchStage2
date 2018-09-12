package com.example.android.popmoviesearchstage2.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite")
public class FavoriteEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Double voteAverage;
    private String posterPath;
    private String overview;
    private String releaseDate;


    public FavoriteEntry (int id, String overview, Double voteAverage, String releaseDate, String posterPath){
        this.id = id;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    /**public static String getYear(String dateString) {
        Calendar calendar = Calendar.getInstance ();
        SimpleDateFormat parser = new SimpleDateFormat ( "yyyy-MM-dd" );
        Date date = null;
        try {
            date = parser.parse ( dateString );
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        calendar.setTime ( date );
        return String.valueOf ( calendar.get ( Calendar.YEAR ) );
    }**/

}



