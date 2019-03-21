package com.riders.testing.model;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class Cursor {
	
	public String resultCount;
	public ArrayList<Pages> pages;
	
	public String estimatedResultCount;
	public int currentPageIndex;
	public String moreResultsUrl;
	public String searchResultTime;

}
