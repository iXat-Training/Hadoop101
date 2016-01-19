/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.ixat.avro.model;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Stock extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Stock\",\"namespace\":\"com.ixat.avro.model\",\"fields\":[{\"name\":\"symbol\",\"type\":\"string\"},{\"name\":\"trade_date\",\"type\":\"string\"},{\"name\":\"open_price\",\"type\":\"double\"},{\"name\":\"close_price\",\"type\":\"double\"},{\"name\":\"hi_price\",\"type\":\"double\"},{\"name\":\"low_price\",\"type\":\"double\"},{\"name\":\"trade_volume\",\"type\":\"long\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public java.lang.CharSequence symbol;
  @Deprecated public java.lang.CharSequence trade_date;
  @Deprecated public double open_price;
  @Deprecated public double close_price;
  @Deprecated public double hi_price;
  @Deprecated public double low_price;
  @Deprecated public long trade_volume;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public Stock() {}

  /**
   * All-args constructor.
   */
  public Stock(java.lang.CharSequence symbol, java.lang.CharSequence trade_date, java.lang.Double open_price, java.lang.Double close_price, java.lang.Double hi_price, java.lang.Double low_price, java.lang.Long trade_volume) {
    this.symbol = symbol;
    this.trade_date = trade_date;
    this.open_price = open_price;
    this.close_price = close_price;
    this.hi_price = hi_price;
    this.low_price = low_price;
    this.trade_volume = trade_volume;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return symbol;
    case 1: return trade_date;
    case 2: return open_price;
    case 3: return close_price;
    case 4: return hi_price;
    case 5: return low_price;
    case 6: return trade_volume;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: symbol = (java.lang.CharSequence)value$; break;
    case 1: trade_date = (java.lang.CharSequence)value$; break;
    case 2: open_price = (java.lang.Double)value$; break;
    case 3: close_price = (java.lang.Double)value$; break;
    case 4: hi_price = (java.lang.Double)value$; break;
    case 5: low_price = (java.lang.Double)value$; break;
    case 6: trade_volume = (java.lang.Long)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'symbol' field.
   */
  public java.lang.CharSequence getSymbol() {
    return symbol;
  }

  /**
   * Sets the value of the 'symbol' field.
   * @param value the value to set.
   */
  public void setSymbol(java.lang.CharSequence value) {
    this.symbol = value;
  }

  /**
   * Gets the value of the 'trade_date' field.
   */
  public java.lang.CharSequence getTradeDate() {
    return trade_date;
  }

  /**
   * Sets the value of the 'trade_date' field.
   * @param value the value to set.
   */
  public void setTradeDate(java.lang.CharSequence value) {
    this.trade_date = value;
  }

  /**
   * Gets the value of the 'open_price' field.
   */
  public java.lang.Double getOpenPrice() {
    return open_price;
  }

  /**
   * Sets the value of the 'open_price' field.
   * @param value the value to set.
   */
  public void setOpenPrice(java.lang.Double value) {
    this.open_price = value;
  }

  /**
   * Gets the value of the 'close_price' field.
   */
  public java.lang.Double getClosePrice() {
    return close_price;
  }

  /**
   * Sets the value of the 'close_price' field.
   * @param value the value to set.
   */
  public void setClosePrice(java.lang.Double value) {
    this.close_price = value;
  }

  /**
   * Gets the value of the 'hi_price' field.
   */
  public java.lang.Double getHiPrice() {
    return hi_price;
  }

  /**
   * Sets the value of the 'hi_price' field.
   * @param value the value to set.
   */
  public void setHiPrice(java.lang.Double value) {
    this.hi_price = value;
  }

  /**
   * Gets the value of the 'low_price' field.
   */
  public java.lang.Double getLowPrice() {
    return low_price;
  }

  /**
   * Sets the value of the 'low_price' field.
   * @param value the value to set.
   */
  public void setLowPrice(java.lang.Double value) {
    this.low_price = value;
  }

  /**
   * Gets the value of the 'trade_volume' field.
   */
  public java.lang.Long getTradeVolume() {
    return trade_volume;
  }

  /**
   * Sets the value of the 'trade_volume' field.
   * @param value the value to set.
   */
  public void setTradeVolume(java.lang.Long value) {
    this.trade_volume = value;
  }

  /** Creates a new Stock RecordBuilder */
  public static com.ixat.avro.model.Stock.Builder newBuilder() {
    return new com.ixat.avro.model.Stock.Builder();
  }
  
  /** Creates a new Stock RecordBuilder by copying an existing Builder */
  public static com.ixat.avro.model.Stock.Builder newBuilder(com.ixat.avro.model.Stock.Builder other) {
    return new com.ixat.avro.model.Stock.Builder(other);
  }
  
  /** Creates a new Stock RecordBuilder by copying an existing Stock instance */
  public static com.ixat.avro.model.Stock.Builder newBuilder(com.ixat.avro.model.Stock other) {
    return new com.ixat.avro.model.Stock.Builder(other);
  }
  
  /**
   * RecordBuilder for Stock instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Stock>
    implements org.apache.avro.data.RecordBuilder<Stock> {

    private java.lang.CharSequence symbol;
    private java.lang.CharSequence trade_date;
    private double open_price;
    private double close_price;
    private double hi_price;
    private double low_price;
    private long trade_volume;

    /** Creates a new Builder */
    private Builder() {
      super(com.ixat.avro.model.Stock.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.ixat.avro.model.Stock.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.symbol)) {
        this.symbol = data().deepCopy(fields()[0].schema(), other.symbol);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.trade_date)) {
        this.trade_date = data().deepCopy(fields()[1].schema(), other.trade_date);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.open_price)) {
        this.open_price = data().deepCopy(fields()[2].schema(), other.open_price);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.close_price)) {
        this.close_price = data().deepCopy(fields()[3].schema(), other.close_price);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.hi_price)) {
        this.hi_price = data().deepCopy(fields()[4].schema(), other.hi_price);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.low_price)) {
        this.low_price = data().deepCopy(fields()[5].schema(), other.low_price);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.trade_volume)) {
        this.trade_volume = data().deepCopy(fields()[6].schema(), other.trade_volume);
        fieldSetFlags()[6] = true;
      }
    }
    
    /** Creates a Builder by copying an existing Stock instance */
    private Builder(com.ixat.avro.model.Stock other) {
            super(com.ixat.avro.model.Stock.SCHEMA$);
      if (isValidValue(fields()[0], other.symbol)) {
        this.symbol = data().deepCopy(fields()[0].schema(), other.symbol);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.trade_date)) {
        this.trade_date = data().deepCopy(fields()[1].schema(), other.trade_date);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.open_price)) {
        this.open_price = data().deepCopy(fields()[2].schema(), other.open_price);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.close_price)) {
        this.close_price = data().deepCopy(fields()[3].schema(), other.close_price);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.hi_price)) {
        this.hi_price = data().deepCopy(fields()[4].schema(), other.hi_price);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.low_price)) {
        this.low_price = data().deepCopy(fields()[5].schema(), other.low_price);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.trade_volume)) {
        this.trade_volume = data().deepCopy(fields()[6].schema(), other.trade_volume);
        fieldSetFlags()[6] = true;
      }
    }

    /** Gets the value of the 'symbol' field */
    public java.lang.CharSequence getSymbol() {
      return symbol;
    }
    
    /** Sets the value of the 'symbol' field */
    public com.ixat.avro.model.Stock.Builder setSymbol(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.symbol = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'symbol' field has been set */
    public boolean hasSymbol() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'symbol' field */
    public com.ixat.avro.model.Stock.Builder clearSymbol() {
      symbol = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'trade_date' field */
    public java.lang.CharSequence getTradeDate() {
      return trade_date;
    }
    
    /** Sets the value of the 'trade_date' field */
    public com.ixat.avro.model.Stock.Builder setTradeDate(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.trade_date = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'trade_date' field has been set */
    public boolean hasTradeDate() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'trade_date' field */
    public com.ixat.avro.model.Stock.Builder clearTradeDate() {
      trade_date = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'open_price' field */
    public java.lang.Double getOpenPrice() {
      return open_price;
    }
    
    /** Sets the value of the 'open_price' field */
    public com.ixat.avro.model.Stock.Builder setOpenPrice(double value) {
      validate(fields()[2], value);
      this.open_price = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'open_price' field has been set */
    public boolean hasOpenPrice() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'open_price' field */
    public com.ixat.avro.model.Stock.Builder clearOpenPrice() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'close_price' field */
    public java.lang.Double getClosePrice() {
      return close_price;
    }
    
    /** Sets the value of the 'close_price' field */
    public com.ixat.avro.model.Stock.Builder setClosePrice(double value) {
      validate(fields()[3], value);
      this.close_price = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'close_price' field has been set */
    public boolean hasClosePrice() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'close_price' field */
    public com.ixat.avro.model.Stock.Builder clearClosePrice() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'hi_price' field */
    public java.lang.Double getHiPrice() {
      return hi_price;
    }
    
    /** Sets the value of the 'hi_price' field */
    public com.ixat.avro.model.Stock.Builder setHiPrice(double value) {
      validate(fields()[4], value);
      this.hi_price = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'hi_price' field has been set */
    public boolean hasHiPrice() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'hi_price' field */
    public com.ixat.avro.model.Stock.Builder clearHiPrice() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'low_price' field */
    public java.lang.Double getLowPrice() {
      return low_price;
    }
    
    /** Sets the value of the 'low_price' field */
    public com.ixat.avro.model.Stock.Builder setLowPrice(double value) {
      validate(fields()[5], value);
      this.low_price = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'low_price' field has been set */
    public boolean hasLowPrice() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'low_price' field */
    public com.ixat.avro.model.Stock.Builder clearLowPrice() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'trade_volume' field */
    public java.lang.Long getTradeVolume() {
      return trade_volume;
    }
    
    /** Sets the value of the 'trade_volume' field */
    public com.ixat.avro.model.Stock.Builder setTradeVolume(long value) {
      validate(fields()[6], value);
      this.trade_volume = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'trade_volume' field has been set */
    public boolean hasTradeVolume() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'trade_volume' field */
    public com.ixat.avro.model.Stock.Builder clearTradeVolume() {
      fieldSetFlags()[6] = false;
      return this;
    }

    @Override
    public Stock build() {
      try {
        Stock record = new Stock();
        record.symbol = fieldSetFlags()[0] ? this.symbol : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.trade_date = fieldSetFlags()[1] ? this.trade_date : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.open_price = fieldSetFlags()[2] ? this.open_price : (java.lang.Double) defaultValue(fields()[2]);
        record.close_price = fieldSetFlags()[3] ? this.close_price : (java.lang.Double) defaultValue(fields()[3]);
        record.hi_price = fieldSetFlags()[4] ? this.hi_price : (java.lang.Double) defaultValue(fields()[4]);
        record.low_price = fieldSetFlags()[5] ? this.low_price : (java.lang.Double) defaultValue(fields()[5]);
        record.trade_volume = fieldSetFlags()[6] ? this.trade_volume : (java.lang.Long) defaultValue(fields()[6]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}