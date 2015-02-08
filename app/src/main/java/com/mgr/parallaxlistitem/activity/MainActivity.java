package com.mgr.parallaxlistitem.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import com.mgr.parallaxlistitem.R;
import com.mgr.parallaxlistitem.adapter.ParallaxListAdapter;
import com.mgr.parallaxlistitem.model.ListItem;
import com.mgr.parallaxlistitem.utils.ListDataGenerator;

import java.util.List;


public class MainActivity extends ActionBarActivity {
	private RecyclerView recyclerView;
	private static final int LIST_ITEMS_COUNT = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		List<ListItem> items = ListDataGenerator.generateCollection(this, LIST_ITEMS_COUNT);

		recyclerView = (RecyclerView) findViewById(R.id.list_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		final ParallaxListAdapter adapter = new ParallaxListAdapter(items);
		recyclerView.setAdapter(adapter);
		recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
				super.getItemOffsets(outRect, view, parent, state);
				int space = 10;
				outRect.left = space;
				outRect.right = space;
				outRect.bottom = space;

				// Add top margin only for the first item to avoid double space between items
				if(parent.getChildPosition(view) == 0) {
					outRect.top = space;
				}
			}
		});
		recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				adapter.onScroll(dy);
			}
		});
		obtainRecycleViewHeight(adapter);
	}

	private void obtainRecycleViewHeight(final ParallaxListAdapter adapter){
		ViewTreeObserver viewTreeObserver = recyclerView.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					adapter.setListViewHeight(recyclerView.getBottom());
				}
			});
		}
	}
}
