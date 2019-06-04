package com.ego.pojo;

import java.io.Serializable;

import com.ego.pojo.TbItem;

public class TbItemChild extends TbItem implements Serializable{
	private String [] images;
	private Boolean enough;


	public Boolean getEnough() {
		return enough;
	}

	public void setEnough(Boolean enough) {
		this.enough = enough;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
	

}
