/**
 * Created By: Comwave Project Team
 * Created Date: 2012-11-28 上午11:40:14
 */
package com.ybase.stock.domain;

/**
 * @author Tom Yang
 * @version 1.0
 */
public class Stock {
	
	public static final int FIRST_TYPE = 1;

	public static final int SECOND_TYPE = 2;

	public static final int NONE = 0;

	public static final int RECORD_FIELD_NUM = 33;

	/** 主键 */
	private Integer id;

	/** 股票名称 */
	private String stockName;

	/** 今日开盘价 */
	private Float todayOpen;

	/** 昨日开盘价 */
	private Float yesterdayClose;

	/** 当前价格 */
	private Float spotPrice;

	/** 今日最高价格 */
	private Float dayHighPrice;

	/** 今日最低价格 */
	private Float dayLowPrice;

	/** 竞买价，即“买一”报价 */
	private Float buyPrice;

	/** 竞卖价，即“卖一”报价 */
	private Float racePrice;

	/** 成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百 */
	private Long shareTraceNum;

	/** 成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值 除以一万 */
	private Long shareTraceAmount;

	/** “买一”申请股 */
	private Long firstBuyerApplyFor;

	/** “买一”报价 */
	private Float firstBuyerQuote;

	/** “买二”申请股 */
	private Long secondBuyerApplyFor;

	/** “买二”报价 */
	private Float secondBuyerQuote;

	/** “买三”申请股 */
	private Long thirdBuyerApplyFor;

	/** “买三”报价 */
	private Float thirdBuyerQuote;

	/** “买四”申请股 */
	private Long fourthBuyerApplyFor;

	/** “买四”申请股 */
	private Float fourthBuyerQuote;

	/** “买五”申请股 */
	private Long fivethBuyerApplyFor;

	/** “买五”申请股 */
	private Float fivethBuyerQuote;

	/** “卖一”申报股 */
	private Long firstRacerApplyFor;

	/** “卖一”报价 */
	private Float firstRacerQuote;

	/** “卖二”申报股 */
	private Long secondRacerApplyFor;

	/** “卖二”报价 */
	private Float secondRacerQuote;

	/** “卖三”申报股 */
	private Long thirdRacerApplyFor;

	/** “卖三”报价 */
	private Float thirdRacerQuote;

	/** “卖四”申报股 */
	private Long fourthRacerApplyFor;

	/** “卖四”报价 */
	private Float fourthRacerQuote;

	/** “卖五”申报股 */
	private Long fivethRacerApplyFor;

	/** “卖五”报价 */
	private Float fivethRacerQuote;

	/** 日期 */
	private String date_;

	/** 时间 */
	private String time_;

	/** 其他 */
	private String memo;

	/** setter,getter 方法 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public Float getTodayOpen() {
		return todayOpen;
	}

	public void setTodayOpen(Float todayOpen) {
		this.todayOpen = todayOpen;
	}

	public Float getYesterdayClose() {
		return yesterdayClose;
	}

	public void setYesterdayClose(Float yesterdayClose) {
		this.yesterdayClose = yesterdayClose;
	}

	public Float getSpotPrice() {
		return spotPrice;
	}

	public void setSpotPrice(Float spotPrice) {
		this.spotPrice = spotPrice;
	}

	public Float getDayHighPrice() {
		return dayHighPrice;
	}

	public void setDayHighPrice(Float dayHighPrice) {
		this.dayHighPrice = dayHighPrice;
	}

	public Float getDayLowPrice() {
		return dayLowPrice;
	}

	public void setDayLowPrice(Float dayLowPrice) {
		this.dayLowPrice = dayLowPrice;
	}

	public Float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Float getRacePrice() {
		return racePrice;
	}

	public void setRacePrice(Float racePrice) {
		this.racePrice = racePrice;
	}

	public Long getShareTraceNum() {
		return shareTraceNum;
	}

	public void setShareTraceNum(Long shareTraceNum) {
		this.shareTraceNum = shareTraceNum;
	}

	public Long getShareTraceAmount() {
		return shareTraceAmount;
	}

	public void setShareTraceAmount(Long shareTraceAmount) {
		this.shareTraceAmount = shareTraceAmount;
	}

	public Long getFirstBuyerApplyFor() {
		return firstBuyerApplyFor;
	}

	public void setFirstBuyerApplyFor(Long firstBuyerApplyFor) {
		this.firstBuyerApplyFor = firstBuyerApplyFor;
	}

	public Float getFirstBuyerQuote() {
		return firstBuyerQuote;
	}

	public void setFirstBuyerQuote(Float firstBuyerQuote) {
		this.firstBuyerQuote = firstBuyerQuote;
	}

	public Long getSecondBuyerApplyFor() {
		return secondBuyerApplyFor;
	}

	public void setSecondBuyerApplyFor(Long secondBuyerApplyFor) {
		this.secondBuyerApplyFor = secondBuyerApplyFor;
	}

	public Float getSecondBuyerQuote() {
		return secondBuyerQuote;
	}

	public void setSecondBuyerQuote(Float secondBuyerQuote) {
		this.secondBuyerQuote = secondBuyerQuote;
	}

	public Long getThirdBuyerApplyFor() {
		return thirdBuyerApplyFor;
	}

	public void setThirdBuyerApplyFor(Long thirdBuyerApplyFor) {
		this.thirdBuyerApplyFor = thirdBuyerApplyFor;
	}

	public Float getThirdBuyerQuote() {
		return thirdBuyerQuote;
	}

	public void setThirdBuyerQuote(Float thirdBuyerQuote) {
		this.thirdBuyerQuote = thirdBuyerQuote;
	}

	public Long getFourthBuyerApplyFor() {
		return fourthBuyerApplyFor;
	}

	public void setFourthBuyerApplyFor(Long fourthBuyerApplyFor) {
		this.fourthBuyerApplyFor = fourthBuyerApplyFor;
	}

	public Float getFourthBuyerQuote() {
		return fourthBuyerQuote;
	}

	public void setFourthBuyerQuote(Float fourthBuyerQuote) {
		this.fourthBuyerQuote = fourthBuyerQuote;
	}

	public Long getFivethBuyerApplyFor() {
		return fivethBuyerApplyFor;
	}

	public void setFivethBuyerApplyFor(Long fivethBuyerApplyFor) {
		this.fivethBuyerApplyFor = fivethBuyerApplyFor;
	}

	public Float getFivethBuyerQuote() {
		return fivethBuyerQuote;
	}

	public void setFivethBuyerQuote(Float fivethBuyerQuote) {
		this.fivethBuyerQuote = fivethBuyerQuote;
	}

	public Long getFirstRacerApplyFor() {
		return firstRacerApplyFor;
	}

	public void setFirstRacerApplyFor(Long firstRacerApplyFor) {
		this.firstRacerApplyFor = firstRacerApplyFor;
	}

	public Float getFirstRacerQuote() {
		return firstRacerQuote;
	}

	public void setFirstRacerQuote(Float firstRacerQuote) {
		this.firstRacerQuote = firstRacerQuote;
	}

	public Long getSecondRacerApplyFor() {
		return secondRacerApplyFor;
	}

	public void setSecondRacerApplyFor(Long secondRacerApplyFor) {
		this.secondRacerApplyFor = secondRacerApplyFor;
	}

	public Float getSecondRacerQuote() {
		return secondRacerQuote;
	}

	public void setSecondRacerQuote(Float secondRacerQuote) {
		this.secondRacerQuote = secondRacerQuote;
	}

	public Long getThirdRacerApplyFor() {
		return thirdRacerApplyFor;
	}

	public void setThirdRacerApplyFor(Long thirdRacerApplyFor) {
		this.thirdRacerApplyFor = thirdRacerApplyFor;
	}

	public Float getThirdRacerQuote() {
		return thirdRacerQuote;
	}

	public void setThirdRacerQuote(Float thirdRacerQuote) {
		this.thirdRacerQuote = thirdRacerQuote;
	}

	public Long getFourthRacerApplyFor() {
		return fourthRacerApplyFor;
	}

	public void setFourthRacerApplyFor(Long fourthRacerApplyFor) {
		this.fourthRacerApplyFor = fourthRacerApplyFor;
	}

	public Float getFourthRacerQuote() {
		return fourthRacerQuote;
	}

	public void setFourthRacerQuote(Float fourthRacerQuote) {
		this.fourthRacerQuote = fourthRacerQuote;
	}

	public Long getFivethRacerApplyFor() {
		return fivethRacerApplyFor;
	}

	public void setFivethRacerApplyFor(Long fivethRacerApplyFor) {
		this.fivethRacerApplyFor = fivethRacerApplyFor;
	}

	public Float getFivethRacerQuote() {
		return fivethRacerQuote;
	}

	public void setFivethRacerQuote(Float fivethRacerQuote) {
		this.fivethRacerQuote = fivethRacerQuote;
	}

	public String getDate_() {
		return date_;
	}

	public void setDate_(String date_) {
		this.date_ = date_;
	}

	public String getTime_() {
		return time_;
	}

	public void setTime_(String time_) {
		this.time_ = time_;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
