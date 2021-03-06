package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.model.Trailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.ACTION_VIEW;
import static com.example.android.popmoviesearchstage2.DetailsActivity.EXTRA_VIDEO;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName ();
    private static final String DEBUG_TAG = "DebugStuff";

    private List<Trailer> mTrailerList;
    private TrailerAdapterListener mTrailerAdapterListener;
    private Context mContext;
    private TextView trailerTextView;
    Trailer trailer;


    public interface TrailerAdapterListener {
        void onVideoClick(int position);
    }

    public TrailerAdapter(Context context, List<Trailer> trailerList) {
        this.mContext = context;
        this.mTrailerList = trailerList;

    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.d ( DEBUG_TAG, "TrailerAdapter onCreateViewHolder" );
        int layoutId = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from ( viewGroup.getContext () );

        View view = inflater.inflate ( layoutId, viewGroup, false );
        return new TrailerViewHolder ( view );

    }

   @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Log.d(DEBUG_TAG, "TrailerAdapter onBindViewHolder");

        Bundle bundle = new Bundle();
        trailer = mTrailerList.get(position);
        holder.trailerTextView.setText(trailer.getName());

    }

    public void loadTrailers(List<Trailer> trailerList, Context mContext) {
        Log.d ( DEBUG_TAG, "TrailerAdapter loadTrailers" );
        this.mContext = mContext;
        if (trailerList != null && !trailerList.isEmpty ()) {
            this.mTrailerList = trailerList;
            notifyDataSetChanged ();
        }
    }

    @Override
    public int getItemCount() {
        return (mTrailerList == null) ? 0 : mTrailerList.size();}



    public class TrailerViewHolder extends RecyclerView.ViewHolder {
           @BindView(R.id.trailer_name)
           TextView trailerTextView;
           @BindView(R.id.trailer_image)
           ImageView trailerImageView;
           View view;


           TrailerViewHolder(View view) {
               super ( view );
               ButterKnife.bind ( this, view );
               view.setOnClickListener ( new View.OnClickListener () {


                   // Set an item click listener on the RecyclerView, which sends an intent to a web browser to
                   //access chosen trailer
                   @Override
                   public void onClick(View view) {
                       Log.d ( DEBUG_TAG, "TrailerAdapter onClick" );

                       int position = getAdapterPosition ();
                       if (position != RecyclerView.NO_POSITION) {
                           //trailer trailerInformation = mTrailerList.get(position);
                           Context context = view.getContext();
                           String trailerId = mTrailerList.get ( position ).getKey ();
                           Intent intent = new Intent ( ACTION_VIEW, Uri.parse ( "https://www.youtube.com/watch?v=" + trailerId ) );
                           intent.putExtra ( EXTRA_VIDEO, trailerId );
                           context.startActivity ( intent );


                       }
                   }


               } );
           }
       }

   }






