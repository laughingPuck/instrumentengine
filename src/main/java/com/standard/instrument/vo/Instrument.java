package com.standard.instrument.vo;

import java.io.Serializable;

public class Instrument implements Serializable {

    private static final long serialVersionUID = -8456930061468898341L;

    private String code;

    private String exchangeCode;

    private String lastTradingDate;

    private String deliveryDate;

    private String market;

    private String label;

    private String tradeAble;

    private String type;

    public Instrument() {

    }

    public Instrument(String code, String exchangeCode, String lastTradingDate, String deliveryDate, String market, String label, String tradeAble, String type) {
        this.code = code;
        this.exchangeCode = exchangeCode;
        this.lastTradingDate = lastTradingDate;
        this.deliveryDate = deliveryDate;
        this.market = market;
        this.label = label;
        this.tradeAble = tradeAble;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getLastTradingDate() {
        return lastTradingDate;
    }

    public void setLastTradingDate(String lastTradingDate) {
        this.lastTradingDate = lastTradingDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTradeAble() {
        return tradeAble;
    }

    public void setTradeAble(String tradeAble) {
        this.tradeAble = tradeAble;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Instrument{" +
                "lastTradingDate='" + lastTradingDate + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", market='" + market + '\'' +
                ", label='" + label + '\'' +
                ", tradeAble='" + tradeAble + '\'' +
                '}';
    }
}
