package com.lucasli.finance;

public class CCPItem {

	public String shortname;
	public String instrumentId;
	public String tradingDay;
	public int totalexchange; //成交量；
	public int vartotalexchange; //成交量变动；
	public int buyvolume; //买入量；
	public int varbuy;
	public int sellvolume;
	public int varsell;
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public String getTradingDay() {
		return tradingDay;
	}
	public void setTradingDay(String tradingDay) {
		this.tradingDay = tradingDay;
	}
	public int getTotalexchange() {
		return totalexchange;
	}
	public void setTotalexchange(String totalexchange) {
		this.totalexchange = Integer.parseInt(totalexchange);
	}
	public int getVartotalexchange() {
		return vartotalexchange;
	}
	public void setVartotalexchange(String vartotalexchange) {
		this.vartotalexchange = Integer.parseInt(vartotalexchange);
	}
	public int getBuyvolume() {
		return buyvolume;
	}
	public void setBuyvolume(String buyvolume) {
		this.buyvolume = Integer.parseInt(buyvolume);
	}
	public int getVarbuy() {
		return varbuy;
	}
	public void setVarbuy(String varbuy) {
		this.varbuy = Integer.parseInt(varbuy);
	}
	public int getSellvolume() {
		return sellvolume;
	}
	public void setSellvolume(String sellvolume) {
		this.sellvolume = Integer.parseInt(sellvolume);
	}
	public int getVarsell() {
		return varsell;
	}
	public void setVarsell(String varsell) {
		this.varsell = Integer.parseInt(varsell);
	}
	
	public String toString(){
		int netVolume = this.getBuyvolume() - this.getSellvolume();
		int netvarVolume = this.getVarbuy() - this.getVarsell();
		
		String netVolumeStr = netVolume > 0? "<font color='green'>看多</font>":"<font color='red'>看空</font>";
		String netVarVolumeStr = netvarVolume > 0? "<font color='green'>看多</font>":"<font color='red'>看空</font>";
		
		return this.getShortname() + "-" + this.getInstrumentId().trim() + " 成交量:" + this.getTotalexchange() 
				+ ",比昨日增减" + this.getVartotalexchange() + ",持买单量:" + this.getBuyvolume() + ",比昨日增减:" + this.getVarbuy() + 
				",持卖单量：" + this.getSellvolume() + ",比昨日增减：" + this.getVarsell() + "净持仓:" + netVolume + netVolumeStr +
				"(负数表示看空), 净变动：" + (netvarVolume) + netVarVolumeStr
				+ "<br>";
	}

}


