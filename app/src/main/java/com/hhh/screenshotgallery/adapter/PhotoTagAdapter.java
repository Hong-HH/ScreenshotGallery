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
import com.hhh.screenshotgallery.model.PhotoTag;
import com.hhh.screenshotgallery.model.Tag;
import com.hhh.screenshotgallery.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PhotoTagAdapter extends RecyclerView.Adapter<PhotoTagAdapter.ViewHolder> {
    // 리사이클러뷰에서, 클릭이벤트 처리할때는 아래 코드를 그냥 카피해서 사용
    public interface OnItemClickListener{
        void onItemClick(int index);
        void onDeleteClick(int index);
    }

    PhotoAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(PhotoAdapter.OnItemClickListener listener){
        this.listener = listener;
    }
    //////////////////////////////////////////////////////////////////
    Context context;
    List<PhotoTag> photoTagList;

    public PhotoTagAdapter(Context context, List<PhotoTag> photoTagList) {
        this.context = context;
        this.photoTagList = photoTagList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle;
        private TextView txtContent;
        private TextView txtTag;
        private TextView txtDate;
        private CardView cardView;
        private ImageView imgDelete;
        private ImageView imgPhoto;
        private List<Tag> tag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtDate =itemView.findViewById(R.id.txtDate);
            txtTag = itemView.findViewById(R.id.txtTag);
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
    public PhotoTagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_row, parent, false);
        return new PhotoTagAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoTagAdapter.ViewHolder holder, int position) {
        // 자바의 리스트에 들어있는 데이터와, 화면을 연결시키는 역할.
        PhotoTag photoTag = photoTagList.get(position);
        holder.txtTitle.setText(photoTag.getTitle());
        holder.txtContent.setText(photoTag.getContent());
        holder.txtDate.setText( photoTag.getCreated_at() );

        // todo 이거 recycler view 안에 recycler view 넣는 법 나중에 검색
        // 현재는 그냥 쭉 이어붙이기로 가는 중
        List<Tag> tagList_1 = photoTag.getTag();
//        ArrayList<String> stringList = new ArrayList<String>();
        StringBuffer sb = new StringBuffer ();
        for (Tag tag1 : tagList_1) {
            String keyword =  tag1.getTag();
            sb.append("#");
            sb.append(keyword);
            sb.append(" ");
        }
        String concat = sb.toString();
        holder.txtTag.setText(concat);


        Glide.with(context).load(Utils.IMAGE_URL+photoTag.getPhoto_url())
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return photoTagList.size();
    }
}
