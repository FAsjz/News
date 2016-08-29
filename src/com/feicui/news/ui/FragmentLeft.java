package com.feicui.news.ui;

import com.feicui.news.MainActivity;
import com.feicui.news.R;
import com.feicui.news.UserActivity;
import com.feicui.news.R.color;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FragmentLeft extends Fragment implements OnClickListener{
	private RelativeLayout[] rl = new RelativeLayout[5];
	private MainActivity activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_menu_left, null);
		rl[0] = (RelativeLayout) v.findViewById(R.id.fragment_menu_rl1);
		rl[1] = (RelativeLayout) v.findViewById(R.id.fragment_menu_rl2);
		rl[2] = (RelativeLayout) v.findViewById(R.id.fragment_menu_rl3);
		rl[3] = (RelativeLayout) v.findViewById(R.id.fragment_menu_rl4);
		rl[4] = (RelativeLayout) v.findViewById(R.id.fragment_menu_rl5);
		for (RelativeLayout rls : rl) {
			rls.setOnClickListener(this);
		}
	return v;
}
	
	@Override
	public void onClick(View v) {
		activity = (MainActivity)getActivity();
//		v.setBackgroundColor(color.azure);
	
		switch (v.getId()) {
		case R.id.fragment_menu_rl1:
			activity.showNewsContent();
			break;
		case R.id.fragment_menu_rl2:
			activity.showFragmentContent();
			break;
		case R.id.fragment_menu_rl3:
			getActivity().startActivity(new Intent(getActivity(),UserActivity.class));
			break;
		case R.id.fragment_menu_rl4:
//			activity.showNewsContent();
			break;
		case R.id.fragment_menu_rl5:
//			activity.showNewsContent();
			break;
		}
	}
	
	
	
	
	
}
