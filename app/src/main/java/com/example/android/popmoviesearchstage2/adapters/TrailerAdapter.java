package com.example.android.popmoviesearchstage2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmoviesearchstage2.DetailsActivity;
import com.example.android.popmoviesearchstage2.R;
import com.example.android.popmoviesearchstage2.data.API;
import com.example.android.popmoviesearchstage2.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.popmoviesearchstage2.DetailsActivity.EXTRA_VIDEO;
import static com.example.android.popmoviesearchstage2.DetailsActivity.VIDEO_PATH;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> mTrailerList;
    private TrailerAdapterListener mTrailerAdapterListener;
    private Context mContext;
    private TextView trailerTextView;
    Trailer trailer;



    public interface TrailerAdapterListener {
        void onVideoClick(int position);
    }

    public TrailerAdapter(Context context, List<Trailer> trailerList, TrailerAdapterListener trailerAdapterListener) {
        this.mContext = context;
        this.mTrailerList = trailerList;
        this.mTrailerAdapterListener = trailerAdapterListener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();
        int layoutId = R.layout.trailer_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        TrailerViewHolder holder;

        View view = inflater.inflate(layoutId, parent, false);
        return holder = new TrailerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        Bundle bundle = new Bundle();
        trailer = mTrailerList.get(position);
        holder.trailerTextView.setText(trailer.getName());
        holder.setOnClickListener(mTrailerAdapterListener, position);



        String trailerThumbnail = API.makeTrailerThumbnailURL((mTrailerList.get(position).getKey()));
        //String trailerThumbnail = "https://img.youtube.com/vi/" + mTrailerList.get(position).getKey() + "/0.jpg");
        ImageView imageView = holder.itemView.findViewById(R.id.trailer_image);
        Picasso.with(mContext)
                .load(trailerThumbnail)
                .placeholder(R.drawable.ic_ondemand_video_black_24dp)
                .into(imageView);

        if (mTrailerList != null && mTrailerList.size() > 0) {
            bundle.putParcelableArrayList(EXTRA_VIDEO, new ArrayList<>(mTrailerList));
        }
    }

    @Override
    public int getItemCount() {
        return (mTrailerList == null) ? 0 : mTrailerList.size();}

       /** public void addAll(ArrayList<Trailer> trailer) {
            mTrailerList.addAll(trailer);
            notifyDataSetChanged();
    }**/


    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trailer_name)
        TextView trailerTextView;
        View view;


        TrailerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener((View.OnClickListener) this);
        }

        // Set an item click listener on the RecyclerView, which sends an intent to a web browser to
        //access chosen trailer
        @Override
        public void onClick(View view) {
            Trailer trailerInformation = new Trailer(trailer.getId(), trailer.getKey(),
                    trailer.getName(), trailer.getSite(),trailer.getTrailerPath());

            Intent intent = new Intent(mContext, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_VIDEO, trailerInformation);
            intent.putExtra(DetailsActivity.EXTRA_VIDEO_SITE, VIDEO_PATH + trailer.getTrailerPath());
            mContext.startActivity(intent);

    }

    /**
     * Retrieve a {@link Trailer} item from the adapter list based on a given position.
     */
    public Trailer get(int position) {return mTrailerList.get(position);
    }

    private void setTrailerName(String name) {
        trailerTextView.setText(name);
    }
    public List<Trailer> getTrailerList() {return mTrailerList; }


    //Set an onClickListener for the TrailerAdapter
    private void setOnClickListener(TrailerAdapter.TrailerAdapterListener mTrailerAdapterListener, final int position) {
        view.setOnClickListener(v -> mTrailerAdapterListener.onVideoClick(position));
    }


   }
}




