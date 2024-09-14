package com.ambitious.parampara.Fragment;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ambitious.parampara.Adapter.E_store_Adapter;
import com.ambitious.parampara.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {

    View view;
    private RecyclerView rec_e_book;
    private RecyclerView.Adapter adapter=null;
    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book, container, false);
        findID();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        rec_e_book.setHasFixedSize(false);
        rec_e_book.setLayoutManager(mLayoutManager);
        rec_e_book.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
        rec_e_book.setItemAnimator(new DefaultItemAnimator());
        adapter = new E_store_Adapter(getActivity());
        rec_e_book.setAdapter(adapter);

        return view;
    }

    private void findID() {
        rec_e_book=view.findViewById(R.id.rec_e_book);

    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column; //spacing / spanCount; // spacing - column  ((1f / spanCount) * spacing)
                outRect.right = (column + 1);   //spacing / spanCount; // (column + 1)  ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column;   //spacing / spanCount; // column  ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1);  //spacing / spanCount; // spacing - (column + 1)  ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
