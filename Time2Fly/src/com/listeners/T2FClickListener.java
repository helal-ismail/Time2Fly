package com.listeners;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;

import com.shared.SharedLayouts;
import com.shared.SharedResources;
import com.ui.Home;
import com.ui.R;

public class T2FClickListener implements OnClickListener{
	
	Home HomeContext;
	
	public T2FClickListener(Context c) {
		HomeContext = (Home)c;
	}
	
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		
		// Side Tray Button
//		case R.id.side_tray:
//			View parent = (View)(SharedLayouts.drawer.getParent()).getParent().getParent();
//			if (parent.getVisibility() == View.GONE)
//				SharedResources.mAnimations.showRightPanel(parent, view);
//			else
//				SharedResources.mAnimations.hideRightPanel(parent, view);
//			break;

			
		// Search Button	
		case R.id.search_button:
			SharedResources.searchFilter = true;
			HomeContext.renderAfterSearch();
			break;
			
		// Back Button	
		case R.id.back:
			
			SharedLayouts.drawer2.setVisibility(View.GONE);
			SharedLayouts.drawer1.setVisibility(View.VISIBLE);
			
/*			ScrollView sv = (ScrollView) SharedLayouts.drawer.getParent();
			sv.setVisibility(View.VISIBLE);
*/			break;
			
			
		default:
			break;
		}
	}

}
