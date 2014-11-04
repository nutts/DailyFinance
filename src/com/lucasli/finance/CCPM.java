package com.lucasli.finance;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;



public class CCPM {

	/**
	 * @param args
	 * 而"成交量"是指当日开始交易至当时的累计总成交数量。
	 * 
	 */
	public static void main(String[] args) throws Exception {

		String urlstr = getXMLURL();
		System.out.println(urlstr);
		
		SAXBuilder saxBuilder = new SAXBuilder();
		
		Document doc = saxBuilder.build(new URL(urlstr));
		
		Element xmlfile = doc.getRootElement();
		
		//List<Element> dataEles = xmlfile.getChildren("data");
		
		StringBuilder sb = new StringBuilder("<table><tr><th>名称</th><th>到期日</th><th>成交量</th>" +
		"<th>比上交易日增减</th><th>持买单量</th><th>比上交易日增减</th><th>卖单量</th><th>比上交易日增减</th></tr>"
				);
		
		CCPList cList = getParty(xmlfile, "中信期货");
		
		System.out.println(cList);
		
		cList = getParty(xmlfile, "国泰君安");
		
		System.out.println(cList);
		
		//List<Element> dataEles = (List<Element>) XPath.selectNodes(xmlfile, "//data[shortname='中信期货    ']");
		//System.out.println("total size " + dataEles.size());
		//for(Element ele:dataEles){
//			if(ele.getChildText("shortname").trim().equals("中信期货") || ele.getChildText("shortname").trim().equals("国泰君安")){
//				Element dataEle = ele;
//				sb.append("<tr><td>" + ele.getChildText("shortname") + "</td>");
//				System.out.println("+++++++++++++++++" + ele.getChildText("shortname") + "++++++++++++++++++++++++++");
//				
//				System.out.println("期货： " + dataEle.getAttributeValue("Text"));
//				sb.append("<td>" + dataEle.getAttributeValue("text") + "</td>");
//				
//				System.out.println("value： " + dataEle.getAttributeValue("Value"));
//				System.out.println("tradingDay (成交日期)： " + dataEle.getChildText("tradingDay"));
//				
//				System.out.println("dataTypeId (0 成交量， 1 买单， 2 卖单)： " + dataEle.getChildText("dataTypeId"));
//				System.out.println("rank (名次)： " + dataEle.getChildText("rank"));
//				System.out.println("volume （持买单量或者卖单量）： " + dataEle.getChildText("volume"));
//				//XPath.selectSingleNode(ele, "dataTypeId)
//				System.out.println("varVolume （比上一日增减） " + dataEle.getChildText("varVolume"));
//				System.out.println("partyId （可能是代码）： " + dataEle.getChildText("partyid"));
//				System.out.println("productid： " + dataEle.getChildText("productid"));
//				
//			}else{
//				//System.out.println(ele.getChildText("shortname").trim());
//			}
			
			//System.out.println(ele.getValue());
	//	}

	}

//	private static Document parseXML(InputStream stream) throws Exception {
//		DocumentBuilderFactory objDocumentBuilderFactory = null;
//		DocumentBuilder objDocumentBuilder = null;
//		Document doc = null;
//		try {
//			objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
//			objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
//
//			doc = objDocumentBuilder.parse(stream);
//		} catch (Exception ex) {
//			throw ex;
//		}
//
//		return doc;
//	}
	
	public static String getQihuoDaily() throws Exception{
		String urlstr = getXMLURL();
		
		System.out.println(urlstr);
		
		SAXBuilder saxBuilder = new SAXBuilder();
		
		Document doc = saxBuilder.build(new URL(urlstr));
		
		Element xmlfile = doc.getRootElement();
		
		
		
		StringBuilder sb = new StringBuilder();
		
		CCPList cList = getParty(xmlfile, "中信期货");
		
		System.out.println(cList);
		sb.append(cList);
		
		cList = getParty(xmlfile, "国泰君安");
		
		sb.append(cList);
		
		
		cList = getParty(xmlfile, "摩根");
		
		sb.append(cList);
		
		cList = getParty(xmlfile, "瑞银");
		
		sb.append(cList);
		
		cList = getParty(xmlfile, "星展");
		
		sb.append(cList);
		
		cList = getParty(xmlfile, "法巴");
		
		sb.append(cList);
		
		cList = getParty(xmlfile, "瑞信");
		
		sb.append(cList);
		
		System.out.println(cList);
		return sb.toString();
	}
	
	public static String getXMLURL(){
		//String urlstr = "http://www.cffex.com.cn/fzjy/ccpm/201409/09/IF.xml";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		
		DateFormat dateFormdat = new SimpleDateFormat("yyyyMM/dd");
		String suffix = dateFormdat.format(cal.getTime());
		return "http://www.cffex.com.cn/fzjy/ccpm/" + suffix + "/IF.xml";
	}
	
	
	
	public static CCPList getParty(Element doc, String shortname) throws Exception{
		//List<Element> dataEles = (List<Element>) XPath.selectNodes(doc, "//data[shortname='" + shortname+ "    ']");
		
		CCPList cList = new CCPList();
		
		List<Element> dataEles = doc.getChildren("data");
		
		//System.out.println("total " + dataEles.size() + " elmeent found");
		
		for(Element e: dataEles){
			if(!e.getChildText("shortname").trim().contains(shortname))
				continue;
			
			String iid = e.getChildText("instrumentId");
			String type = e.getChildText("dataTypeId");
			CCPItem item = cList.getItem(shortname, iid);
			if(type.equals("0")){
				item.setTotalexchange(e.getChildText("volume"));
				item.setVartotalexchange(e.getChildText("varVolume"));
				
			}else if(type.equals("1")){
				item.setBuyvolume(e.getChildText("volume"));
				item.setVarbuy(e.getChildText("varVolume"));
			}else if(type.endsWith("2")){
				item.setSellvolume(e.getChildText("volume"));
				item.setVarsell(e.getChildText("varVolume"));
			}else{
				System.err.println("Fail to get type");
			}
		}
		
		return cList;
	}

}
