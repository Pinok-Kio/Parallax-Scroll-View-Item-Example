package com.mgr.parallaxlistitem.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.mgr.parallaxlistitem.R;
import com.mgr.parallaxlistitem.interfaces.OnScrollListener;
import com.mgr.parallaxlistitem.model.ListItem;
import com.mgr.parallaxlistitem.view.ParallaxListItemView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParallaxListAdapter extends RecyclerView.Adapter<ParallaxListAdapter.Holder> {
	private final List<ListItem> items;
	private final Collection<OnScrollListener> listeners = new ArrayList<>();
	private int currentDy;
	private int listViewHeight;

	public ParallaxListAdapter(List<ListItem> items) {
		this.items = items;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
		Holder holder;
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parallax_item, viewGroup, false);
		holder = new Holder(view);
		holder.text = (TextView) view.findViewById(R.id.item_id);
		holder.icon = (ImageView) view.findViewById(R.id.item_icon);
		return holder;
	}

	@Override
	public void onViewAttachedToWindow(final Holder holder) {
		super.onViewAttachedToWindow(holder);
		int position = holder.getPosition();
		ListItem item = items.get(position);

		if (item.getType() == ListItem.TYPE_PARALLAX && item.getBackgroundBitmap() != null) {
			if (!listeners.contains((OnScrollListener) holder.itemView)) {
				listeners.add((OnScrollListener) holder.itemView);
			}
			if (((ParallaxListItemView) holder.itemView).isFirstStart) {
				((ParallaxListItemView) holder.itemView).isFirstStart = false;
				final ViewTreeObserver viewTreeObserver = holder.itemView.getViewTreeObserver();
				if (viewTreeObserver.isAlive()) {
					viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
						@Override
						public void onGlobalLayout() {
							viewTreeObserver.removeOnGlobalLayoutListener(this);
							((ParallaxListItemView) holder.itemView).setListHeight(listViewHeight);
							((ParallaxListItemView) holder.itemView).setInitialTopPosition(holder.itemView.getTop());
							ParallaxListItemView.viewHeight = holder.itemView.getHeight();
						}
					});
				}
			}
		}
	}

	@Override
	public void onViewDetachedFromWindow(Holder holder) {
		super.onViewDetachedFromWindow(holder);
		int position = holder.getPosition();
		ListItem item = items.get(position);
		if (item.getType() == ListItem.TYPE_PARALLAX) {
			listeners.remove((OnScrollListener) holder.itemView);
		}
	}

	@Override
	public void onBindViewHolder(Holder holder, int i) {
		ListItem item = items.get(i);

		if (item.getType() == ListItem.TYPE_NORMAL) {
			holder.text.setText("Item Position: " + item.getId());
			holder.text.setTextColor(Color.BLACK);
			holder.text.setShadowLayer(0, 0, 0, Color.BLACK);
			((ParallaxListItemView) holder.itemView).setBitmap(null, 0);
			holder.icon.setImageResource(item.getIconResId());
			holder.itemView.setBackgroundColor(Color.WHITE);
			holder.itemView.setWillNotDraw(true);
		} else {
			holder.text.setTextColor(Color.WHITE);
			holder.icon.setImageResource(item.getIconResId());
			if (item.getBackgroundBitmap() == null) {
				holder.text.setText("I'm a ParallaXXX! W/O Background");
				holder.itemView.setBackgroundColor(Color.RED);
				((ParallaxListItemView) holder.itemView).setBitmap(null, 0);
				holder.itemView.setWillNotDraw(true);
				holder.text.setShadowLayer(0, 0, 0, Color.BLACK);
			} else {
				holder.text.setText("I'm a ParallaXXX!");
				holder.itemView.setWillNotDraw(false);
				holder.text.setShadowLayer(8, 2, 4, Color.BLACK);
				((ParallaxListItemView) holder.itemView).setListHeight(listViewHeight);
				((ParallaxListItemView) holder.itemView).setBitmap(item.getBackgroundBitmap(), currentDy <= 0 ? 0 : listViewHeight);
			}
		}
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public void onScroll(int dY) {
		for (OnScrollListener l : listeners) {
			l.onScroll(dY);
		}
		currentDy = dY;
	}

	public void setListViewHeight(int height) {
		listViewHeight = height;
	}

	class Holder extends RecyclerView.ViewHolder {
		private TextView text;
		private ImageView icon;

		public Holder(View itemView) {
			super(itemView);
		}
	}
}
