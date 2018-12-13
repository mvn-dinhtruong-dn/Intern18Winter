package asiantech.internship.summer.recycler_view;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.recycler_view.model.TimelineItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TimelineViewHolder> {
    private List<TimelineItem> mItems;
    private OnItemListener mListener;


    RecyclerViewAdapter(List<TimelineItem> listUsers, OnItemListener listener) {
        this.mItems = listUsers;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new TimelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    interface OnItemListener {
        void onClickLike(int position);
    }

    class TimelineViewHolder extends RecyclerView.ViewHolder {
        private static final String LIKE_SINGULAR = "like";
        private static final String LIKE_PLURAL = "likes";
        private ImageView mImgAvatar;
        private TextView mUsername;
        private ImageView mImgPictures;
        private TextView mCountLike;
        private ImageButton mImgBtnLike;
        private TextView mComment;

        private TimelineViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mUsername = itemView.findViewById(R.id.tvUsername);
            mImgPictures = itemView.findViewById(R.id.imgPicture);
            mCountLike = itemView.findViewById(R.id.tvCountLike);
            mImgBtnLike = itemView.findViewById(R.id.imgBtnLike);
            mComment = itemView.findViewById(R.id.tvComment);
            this.setIsRecyclable(false);
            mImgBtnLike.setOnClickListener(view -> mListener.onClickLike(getAdapterPosition()));
        }

        @SuppressLint("SetTextI18n")
        private void bindView(int position) {
            TimelineItem item = mItems.get(position);
            if (item != null) {
                mImgAvatar.setImageResource(item.getAvatar());
                mUsername.setText(item.getUsername());
                mImgPictures.setImageResource(item.getPicture());
                mComment.setText(item.getComment());
                if (mItems.get(position).getCountLike() < 2) {
                    mCountLike.setText(item.getCountLike() + " " + LIKE_SINGULAR);
                } else {
                    mCountLike.setText(item.getCountLike() + " " + LIKE_PLURAL);
                }
            }
        }
    }
}
