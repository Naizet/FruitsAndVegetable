package com.bishe.FV.entity;

public class ViewPager {
	private int iconResId;
	private String intro;
	
	public ViewPager(int iconResId, String intro) {
		this.iconResId=iconResId;
		this.intro=intro;
	}
	public int getIconResId() {
		return iconResId;
	}
	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	

}
