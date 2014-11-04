package com.lucasli.finance;

import java.util.ArrayList;

public class CCPList {

	ArrayList<CCPItem> result = null;
	
	public CCPList(){
		
	}
	
	public void addItem(CCPItem i){
		result.add(i);
	}
	
	
	public CCPItem getItem(String shortname, String instrument){
		
		if(result != null){
			for(CCPItem i : result){
				if(shortname.equals(i.getShortname()) && instrument.equals(i.getInstrumentId()))
					return i;
			}
		}
		
		//if not, create new one;
		CCPItem newone = new CCPItem();
		newone.setShortname(shortname);
		newone.setInstrumentId(instrument);
		if(result == null){
			result = new ArrayList<CCPItem>();
		}
		result.add(newone);
		return newone;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		if(result == null){
			return "No Data";
		}
		
		for(CCPItem i : result){
			sb.append(i.toString());
		}
		
		return sb.toString();
	}

}
