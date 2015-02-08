package com.mgr.parallaxlistitem.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mgr.parallaxlistitem.R;
import com.mgr.parallaxlistitem.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class ListDataGenerator {

	public static List<ListItem> generateCollection(Context context, int count) {
		List<ListItem> result = new ArrayList<>(count);
		Bitmap backAbstract = BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_test);
		Bitmap backStones = BitmapFactory.decodeResource(context.getResources(), R.mipmap.image_test_stones);
		int step = 3;
		int currentIndex = 0;
		for (int i = 0; i < count; i++) {
			ListItem item;
			if (i == currentIndex) {
				item = new ListItem(i, R.drawable.ic_action_settings);
				currentIndex += step;
				item.setType(ListItem.TYPE_PARALLAX);
				if(currentIndex % 2 == 0) {
					item.setBackgroundBitmap(backAbstract);
				}else{
					item.setBackgroundBitmap(backStones);
				}
			} else {
				item = new ListItem(i, R.drawable.ic_action_settings_dark);
			}
			result.add(item);
		}
		return result;
	}
}
