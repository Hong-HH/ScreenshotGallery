package com.hhh.screenshotgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hhh.screenshotgallery.R;
import com.hhh.screenshotgallery.model.Photo;
import com.hhh.screenshotgallery.utils.Utils;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    // 리사이클러뷰에서, 클릭이벤트 처리할때는 아래 코드를 그냥 카피해서 사용
    public interface OnItemClickListener{
        void onItemClick(int index);
        void onDeleteClick(int index);
    }

    OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    //////////////////////////////////////////////////////////////////

    Context context;
    List<Photo> photoList;

    public PhotoAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtContent;
        private TextView txtDate;
        private CardView cardView;
        private ImageView imgDelete;
        private ImageView imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtDate =itemView.findViewById(R.id.txtDate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 아래 코드는 카피 앤 페이스트해서 사용!
                    int index = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(index);
                    }
                    ///////////////////////////////////
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    if(listener != null){
                        listener.onDeleteClick(index);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 자바의 리스트에 들어있는 데이터와, 화면을 연결시키는 역할.
        Photo photo = photoList.get(position);
        holder.txtTitle.setText(photo.getTitle());
        holder.txtContent.setText(photo.getContent());
        holder.txtDate.setText( photo.getCreated_at() );

        Glide.with(context).load(Utils.IMAGE_URL+photo.getPhoto_url())
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
