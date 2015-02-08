package com.mgr.parallaxlistitem.model;

import android.graphics.Bitmap;

public class ListItem {
	private final int id;
	private final int iconRes;
	private int type = TYPE_NORMAL;
	private Bitmap back;
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_PARALLAX = 1;

	public ListItem(int id, int iconRes) {
		this.id = id;
		this.iconRes = iconRes;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public Bitmap getBackgroundBitmap() {
		return back;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setBackgroundBitmap(Bitmap back) {
		this.back = back;
	}

	public int getIconResId() {
		return iconRes;
	}
}
