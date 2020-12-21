package com.etone.project.utils;

import com.etone.project.core.utils.DateUtil;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class SetValueToEntityCsv implements SetValueToEntity {
    
    private String getValue(Object cell, Map<String, String> regularMap) {
        
        if(cell == null || cell.toString().trim().isEmpty()) {
            return null;
        }
        
        String value = cell.toString();

        if (regularMap != null && !regularMap.isEmpty()) {
            Set<String> keySet = regularMap.keySet();

            for (String key : keySet) {
                value = value.replace(key, regularMap.get(key));
            }
        }
        
        if(value == null || value.trim().isEmpty()) {
            return null;
        }
        
        return value;
    }

    @Override
    public void setboolean(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null || value.trim().equals("")) {
                return;
            }
            
            Boolean valBool = null;
            if (value.toUpperCase().equals("TRUE")) {
                valBool = true;
            } else {
                if (value.toUpperCase().equals("FALSE")) {
                    valBool = false;
                }
            }
            method.invoke(entity, valBool.booleanValue());
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setBoolean(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            Boolean valBool = null;
            if (value.toUpperCase().equals("TRUE")) {
                valBool = true;
            } else {
                if (value.toUpperCase().equals("FALSE")) {
                    valBool = false;
                }
            }
            method.invoke(entity, valBool);
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setString(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);
            method.invoke(entity, value);
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setint(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            Integer valInt = Integer.parseInt(value.trim());
            method.invoke(entity, valInt.intValue());
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setInteger(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            Integer valInt = Integer.parseInt(value.trim());
            method.invoke(entity, valInt);
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void setlong(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            Long valLong = Long.parseLong(value.trim());
            method.invoke(entity, valLong.longValue());
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void setLong(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            Long valLong = Long.parseLong(value.trim());
            method.invoke(entity, valLong);
        } catch (Exception ex) {
//            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, 
//                    "cell:" + cell != null? cell.toString() : "null,msg:" + ex.getMessage());
        }
    }

    @Override
    public void setfloat(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            
            Float f = Float.parseFloat(value);
            method.invoke(entity, f.floatValue());
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setFloat(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            
            Float f = Float.parseFloat(value);
            method.invoke(entity, f);
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setdouble(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            Double d = Double.parseDouble(value);
            
            method.invoke(entity, d.doubleValue());
        } catch (Exception ex) {
//            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setDouble(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            
            Double d = Double.parseDouble(value);
            method.invoke(entity, d);
        } catch (Exception ex) {
//            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setDate(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            Date date = DateUtil.parseStringToDate(this.getValue(cell, regularMap));
            if (date != null) {
                method.invoke(entity, date);
            }

        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setTimestamp(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);
            if(value == null) {
                return;
            }
            Date date = DateUtil.parseStringToDate(this.getValue(cell, regularMap));
            if (date != null) {
                Timestamp date1 = new Timestamp(date.getTime());
                method.invoke(entity, date1);

            }
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setBigDecimal(Method method, Object cell, Object entity, Map<String, String> regularMap) {
        try {
            String value = this.getValue(cell, regularMap);

            if(value == null) {
                return;
            }
            
            BigDecimal bigDecimal = new BigDecimal(value);
            
            method.invoke(entity, bigDecimal);
        } catch (Exception ex) {
            Logger.getLogger(SetValueToEntityCsv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
