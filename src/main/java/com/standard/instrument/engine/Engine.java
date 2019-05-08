package com.standard.instrument.engine;

import com.standard.instrument.vo.Instrument;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static java.lang.Class.forName;

public class Engine {

    private InstrumentMapRule instrumentMapRule;

    private List<InstrumentDetailRule> instrumentDetailRules;

    private List<Instrument> instruments;

    public InstrumentMapRule getInstrumentMapRule() {
        return instrumentMapRule;
    }

    public void setInstrumentMapRule(InstrumentMapRule instrumentMapRule) {
        this.instrumentMapRule = instrumentMapRule;
    }

    public List<InstrumentDetailRule> getInstrumentDetailRules() {
        return instrumentDetailRules;
    }

    public void setInstrumentDetailRules(List<InstrumentDetailRule> instrumentDetailRules) {
        this.instrumentDetailRules = instrumentDetailRules;
    }

    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public Instrument execute(String type, String code) throws ClassNotFoundException, IntrospectionException, IllegalAccessException, InvocationTargetException {
        Instrument instrument = findInstrument(type, code);
        if (instrument == null) {
            System.out.println("No mapping instrument source found! Please check source file/input");
            return null;
            //throw new InstrumentException();
        }
        Instrument mapInstrument = findMapInstrument(instrument, instrumentMapRule);

        for (InstrumentDetailRule instrumentDetailRule : instrumentDetailRules) {
            Class clazz = forName("com.standard.instrument.vo.Instrument");
            PropertyDescriptor pd = new PropertyDescriptor(instrumentDetailRule.getColumn(), clazz);
            Method get = pd.getReadMethod();
            Method set = pd.getWriteMethod();
            if (RuleType.OVERRIDE.equals(instrumentDetailRule.getRuleType())) {
                if (mapInstrument == null) {
                    continue;
                }
                String value = (String) get.invoke(mapInstrument);
                set.invoke(instrument, value);
            } else if (RuleType.TRIM.equals(instrumentDetailRule.getRuleType())) {
                String value = (String) get.invoke(instrument);
                set.invoke(instrument, value.split("_")[1]);
            } else if (RuleType.DEFAULT.equals(instrumentDetailRule.getRuleType())) {
                set.invoke(instrument, instrumentDetailRule.getColumnValue());
            }
            /*switch (instrumentDetailRule.getRuleType()) {
                case OVERRIDE:
                    if (mapInstrument == null) {
                        break;
                    }
                    Class clazz = forName("com.standard.instrument.vo.Instrument");
                    PropertyDescriptor pd = new PropertyDescriptor(instrumentDetailRule.getColumn(), clazz);
                    Method get = pd.getReadMethod();
                    Method set = pd.getWriteMethod();
                    String value = (String) get.invoke(mapInstrument);
                    set.invoke(instrument, value);
                    break;
                case TRIM:
                    break;
                default:
                    break;
            }*/
        }
        return instrument;
    }

    private Instrument findInstrument(String type, String code) {
        for (Instrument instrument : instruments) {
            if ((code.equals(instrument.getCode()) || code.equals(instrument.getExchangeCode())) && type.equals(instrument.getType())) {
                return instrument;
            }
        }
        return null;
    }

    private Instrument findMapInstrument(Instrument instrument, InstrumentMapRule instrumentMapRule) throws ClassNotFoundException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        if (instrumentMapRule == null) return null;
        Class clazz = forName("com.standard.instrument.vo.Instrument");
        PropertyDescriptor pd = new PropertyDescriptor(instrumentMapRule.getColumn(), clazz);
        Method get = pd.getReadMethod();
        String sourceValue = (String) get.invoke(instrument);
        return findMapInstrument(instrumentMapRule.getMappingColumn(), sourceValue, instrumentMapRule.getInstrumentType());
    }

    private Instrument findMapInstrument(String mappingColumn, String sourceValue, String type) throws ClassNotFoundException, IntrospectionException, InvocationTargetException, IllegalAccessException {
        for (Instrument instrument : instruments) {
            if (!instrument.getType().equals(type)) {
                continue;
            }
            Class clazz = forName("com.standard.instrument.vo.Instrument");
            PropertyDescriptor pd = new PropertyDescriptor(mappingColumn, clazz);
            Method get = pd.getReadMethod();
            String targetValue = (String) get.invoke(instrument);
            if (sourceValue.equals(targetValue)) {
                return instrument;
            }
        }
        return null;
    }
}
