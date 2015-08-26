package com.braffa.sellem.hbn;

public interface IBaseDao {

	public Object create();

	public void delete();

	public int getCount();

	public Object read();

	public Object readAll();

	public Object update();
	
	public Object search();
	
	public Object remove();

}
