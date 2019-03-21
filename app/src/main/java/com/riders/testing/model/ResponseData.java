package com.riders.testing.model;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class ResponseData
{
	public ArrayList<Results> results;
	public Cursor cursor;
}
