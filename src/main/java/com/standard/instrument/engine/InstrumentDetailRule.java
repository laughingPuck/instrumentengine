package com.standard.instrument.engine;

public class InstrumentDetailRule {

    private String column; //delivery_date

    private RuleType ruleType; //override or do nothing or other rules etc...

    private String columnValue;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public void setRuleType(RuleType ruleType) {
        this.ruleType = ruleType;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}
