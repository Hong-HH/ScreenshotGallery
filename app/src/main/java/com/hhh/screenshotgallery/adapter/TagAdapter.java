package com.hhh.screenshotgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hhh.screenshotgallery.R;
import com.hhh.screenshotgallery.model.Tag;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{
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
    List<Tag> tagList;

    public TagAdapter(Context context, List<Tag> tagList) {
        this.context = context;
        this.tagList = tagList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTag;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTag = itemView.findViewById(R.id.txtTag);
            cardView = itemView.findViewById(R.id.tagCardView);

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

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_row, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tag tag = tagList.get(position);
        holder.txtTag.setText( tag.getTag() );
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }
}
