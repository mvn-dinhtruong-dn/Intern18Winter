package asiantech.internship.summer.viewpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.models.TimelinePagerItem;

public class FavouriteFragment extends Fragment {
    //private List<TimelinePagerItem> mTimelinePagers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Bundle bundle = this.getArguments();
        if (bundle != null) {
            mTimelinePagers = new ArrayList<>();
            mTimelinePagers = (List<TimelinePagerItem>) bundle.getSerializable("listFavourite");
        }*/
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        initTimeline(view);
        return view;
    }

    private void initTimeline(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavourite);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        /*if (getActivity() instanceof ViewPagerActivity) {
            ((ViewPagerActivity) getActivity()).setFavouriteAdapter(
                    new TimelinePagerAdapter(getContext(), mTimelinePagers));
            recyclerView.setAdapter(((ViewPagerActivity) getActivity()).getFavoriteAdapter());
        }*/
    }
}
