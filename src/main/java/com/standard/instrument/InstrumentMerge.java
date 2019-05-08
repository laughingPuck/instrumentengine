package com.standard.instrument;

import com.standard.instrument.engine.Engine;
import com.standard.instrument.engine.InstrumentDetailRule;
import com.standard.instrument.engine.InstrumentMapRule;
import com.standard.instrument.engine.RuleType;
import com.standard.instrument.vo.Instrument;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstrumentMerge {
    public static void main(String args[]) {
        /**
         * args[0] for type, args[1] for code
         */
        Instrument instrumentLME = new Instrument("PB_03_2018", "", "15-03-2018",
                "17-03-2018", "LME_PB", "Lead 13 March 2018", "", "LME");

        Instrument instrumentPRIME = new Instrument("PRIME_PB_03_2018", "PB_03_2018", "14-03-2018", "18-03-2018",
                "LME_PB", "Lead 13 March 2018", "FALSE", "PRIME");

        List<Instrument> instrumentList = new ArrayList<Instrument>();
        instrumentList.add(instrumentLME);
        instrumentList.add(instrumentPRIME);

        InstrumentMapRule instrumentMapRule = new InstrumentMapRule();
        instrumentMapRule.setColumn("exchangeCode");
        instrumentMapRule.setMappingColumn("code");
        instrumentMapRule.setInstrumentType("LME");

        InstrumentDetailRule lastTradingDateRule = new InstrumentDetailRule();
        lastTradingDateRule.setColumn("lastTradingDate");
        lastTradingDateRule.setRuleType(RuleType.OVERRIDE);

        InstrumentDetailRule deliveryDateRule = new InstrumentDetailRule();
        deliveryDateRule.setColumn("deliveryDate");
        deliveryDateRule.setRuleType(RuleType.OVERRIDE);

        InstrumentDetailRule marketRule = new InstrumentDetailRule();
        marketRule.setColumn("market");
        marketRule.setRuleType(RuleType.TRIM);

        List<InstrumentDetailRule> instrumentDetailRuleList = new ArrayList<InstrumentDetailRule>();
        instrumentDetailRuleList.add(lastTradingDateRule);
        instrumentDetailRuleList.add(deliveryDateRule);
        instrumentDetailRuleList.add(marketRule);


        List<InstrumentDetailRule> instrumentDetailRuleList1 = new ArrayList<InstrumentDetailRule>();

        InstrumentDetailRule tradeAbleRule = new InstrumentDetailRule();
        tradeAbleRule.setColumn("tradeAble");
        tradeAbleRule.setRuleType(RuleType.DEFAULT);
        tradeAbleRule.setColumnValue("TRUE");

        instrumentDetailRuleList1.add(marketRule);
        instrumentDetailRuleList1.add(tradeAbleRule);

        Engine lmeEngine = new Engine();

        lmeEngine.setInstruments(instrumentList);
        lmeEngine.setInstrumentDetailRules(instrumentDetailRuleList1);


        Engine primeEngine = new Engine();

        primeEngine.setInstruments(instrumentList);
        primeEngine.setInstrumentMapRule(instrumentMapRule);
        primeEngine.setInstrumentDetailRules(instrumentDetailRuleList);

        try {
            System.out.println(lmeEngine.execute(args[0], args[1]));
            System.out.println(primeEngine.execute(args[0], args[1]));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
