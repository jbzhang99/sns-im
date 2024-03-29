

package com.inga.model.proto;

public final class SentBodyProto {
  private SentBodyProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ModelOrBuilder extends
      // @@protoc_insertion_point(interface_extends:com.w2cx.sns.sdk.server.model.proto.Model)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string key = 1;</code>
     */
    String getKey();
    /**
     * <code>string key = 1;</code>
     */
    com.google.protobuf.ByteString
        getKeyBytes();

    /**
     * <code>int64 timestamp = 2;</code>
     */
    long getTimestamp();

    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */
    int getDataCount();
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */
    boolean containsData(
            String key);
    /**
     * Use {@link #getDataMap()} instead.
     */
    @Deprecated
    java.util.Map<String, String>
    getData();
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */
    java.util.Map<String, String>
    getDataMap();
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */

    String getDataOrDefault(
            String key,
            String defaultValue);
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */

    String getDataOrThrow(
            String key);
  }
  /**
   * Protobuf type {@code com.w2cx.sns.sdk.server.model.proto.Model}
   */
  public  static final class Model extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:com.w2cx.sns.sdk.server.model.proto.Model)
      ModelOrBuilder {
    // Use Model.newBuilder() to construct.
    private Model(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Model() {
      key_ = "";
      timestamp_ = 0L;
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Model(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              String s = input.readStringRequireUtf8();

              key_ = s;
              break;
            }
            case 16: {

              timestamp_ = input.readInt64();
              break;
            }
            case 26: {
              if (!((mutable_bitField0_ & 0x00000004) == 0x00000004)) {
                data_ = com.google.protobuf.MapField.newMapField(
                    DataDefaultEntryHolder.defaultEntry);
                mutable_bitField0_ |= 0x00000004;
              }
              com.google.protobuf.MapEntry<String, String>
              data__ = input.readMessage(
                  DataDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
              data_.getMutableMap().put(
                  data__.getKey(), data__.getValue());
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return SentBodyProto.internal_static_com_w2cx_sns_sdk_server_model_proto_Model_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 3:
          return internalGetData();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return SentBodyProto.internal_static_com_w2cx_sns_sdk_server_model_proto_Model_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Model.class, Builder.class);
    }

    @SuppressWarnings("unused")
	private int bitField0_;
    public static final int KEY_FIELD_NUMBER = 1;
    private volatile Object key_;
    /**
     * <code>string key = 1;</code>
     */
    public String getKey() {
      Object ref = key_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        key_ = s;
        return s;
      }
    }
    /**
     * <code>string key = 1;</code>
     */
    public com.google.protobuf.ByteString
        getKeyBytes() {
      Object ref = key_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        key_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TIMESTAMP_FIELD_NUMBER = 2;
    private long timestamp_;
    /**
     * <code>int64 timestamp = 2;</code>
     */
    public long getTimestamp() {
      return timestamp_;
    }

    public static final int DATA_FIELD_NUMBER = 3;
    private static final class DataDefaultEntryHolder {
      static final com.google.protobuf.MapEntry<
          String, String> defaultEntry =
              com.google.protobuf.MapEntry
              .<String, String>newDefaultInstance(
                  SentBodyProto.internal_static_com_w2cx_sns_sdk_server_model_proto_Model_DataEntry_descriptor,
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "",
                  com.google.protobuf.WireFormat.FieldType.STRING,
                  "");
    }
    private com.google.protobuf.MapField<
        String, String> data_;
    private com.google.protobuf.MapField<String, String>
    internalGetData() {
      if (data_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            DataDefaultEntryHolder.defaultEntry);
      }
      return data_;
    }

    public int getDataCount() {
      return internalGetData().getMap().size();
    }
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */

    public boolean containsData(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      return internalGetData().getMap().containsKey(key);
    }
    /**
     * Use {@link #getDataMap()} instead.
     */
    @Deprecated
    public java.util.Map<String, String> getData() {
      return getDataMap();
    }
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */

    public java.util.Map<String, String> getDataMap() {
      return internalGetData().getMap();
    }
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */

    public String getDataOrDefault(
        String key,
        String defaultValue) {
      if (key == null) { throw new NullPointerException(); }
      java.util.Map<String, String> map =
          internalGetData().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, string&gt; data = 3;</code>
     */

    public String getDataOrThrow(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      java.util.Map<String, String> map =
          internalGetData().getMap();
      if (!map.containsKey(key)) {
        throw new IllegalArgumentException();
      }
      return map.get(key);
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getKeyBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, key_);
      }
      if (timestamp_ != 0L) {
        output.writeInt64(2, timestamp_);
      }
      com.google.protobuf.GeneratedMessageV3
        .serializeStringMapTo(
          output,
          internalGetData(),
          DataDefaultEntryHolder.defaultEntry,
          3);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getKeyBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, key_);
      }
      if (timestamp_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(2, timestamp_);
      }
      for (java.util.Map.Entry<String, String> entry
           : internalGetData().getMap().entrySet()) {
        com.google.protobuf.MapEntry<String, String>
        data__ = DataDefaultEntryHolder.defaultEntry.newBuilderForType()
            .setKey(entry.getKey())
            .setValue(entry.getValue())
            .build();
        size += com.google.protobuf.CodedOutputStream
            .computeMessageSize(3, data__);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Model)) {
        return super.equals(obj);
      }
      Model other = (Model) obj;

      boolean result = true;
      result = result && getKey()
          .equals(other.getKey());
      result = result && (getTimestamp()
          == other.getTimestamp());
      result = result && internalGetData().equals(
          other.internalGetData());
      return result;
    }

    @SuppressWarnings("unchecked")
	@Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + KEY_FIELD_NUMBER;
      hash = (53 * hash) + getKey().hashCode();
      hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTimestamp());
      if (!internalGetData().getMap().isEmpty()) {
        hash = (37 * hash) + DATA_FIELD_NUMBER;
        hash = (53 * hash) + internalGetData().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Model parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Model parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Model parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Model parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Model parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Model parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Model parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Model parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Model parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Model parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Model parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Model parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Model prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.w2cx.sns.sdk.server.model.proto.Model}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:com.w2cx.sns.sdk.server.model.proto.Model)
        ModelOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return SentBodyProto.internal_static_com_w2cx_sns_sdk_server_model_proto_Model_descriptor;
      }

      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMapField(
          int number) {
        switch (number) {
          case 3:
            return internalGetData();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      @SuppressWarnings({"rawtypes"})
      protected com.google.protobuf.MapField internalGetMutableMapField(
          int number) {
        switch (number) {
          case 3:
            return internalGetMutableData();
          default:
            throw new RuntimeException(
                "Invalid map field number: " + number);
        }
      }
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return SentBodyProto.internal_static_com_w2cx_sns_sdk_server_model_proto_Model_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Model.class, Builder.class);
      }

      // Construct using com.w2cx.sns.sdk.server.model.proto.SentBodyProto.Model.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        key_ = "";

        timestamp_ = 0L;

        internalGetMutableData().clear();
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return SentBodyProto.internal_static_com_w2cx_sns_sdk_server_model_proto_Model_descriptor;
      }

      public Model getDefaultInstanceForType() {
        return Model.getDefaultInstance();
      }

      public Model build() {
        Model result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @SuppressWarnings("unused")
	public Model buildPartial() {
        Model result = new Model(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        result.key_ = key_;
        result.timestamp_ = timestamp_;
        result.data_ = internalGetData();
        result.data_.makeImmutable();
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Model) {
          return mergeFrom((Model)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Model other) {
        if (other == Model.getDefaultInstance()) return this;
        if (!other.getKey().isEmpty()) {
          key_ = other.key_;
          onChanged();
        }
        if (other.getTimestamp() != 0L) {
          setTimestamp(other.getTimestamp());
        }
        internalGetMutableData().mergeFrom(
            other.internalGetData());
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Model parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Model) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private Object key_ = "";
      /**
       * <code>string key = 1;</code>
       */
      public String getKey() {
        Object ref = key_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          key_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string key = 1;</code>
       */
      public com.google.protobuf.ByteString
          getKeyBytes() {
        Object ref = key_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          key_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string key = 1;</code>
       */
      public Builder setKey(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        key_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string key = 1;</code>
       */
      public Builder clearKey() {
        
        key_ = getDefaultInstance().getKey();
        onChanged();
        return this;
      }
      /**
       * <code>string key = 1;</code>
       */
      public Builder setKeyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        key_ = value;
        onChanged();
        return this;
      }

      private long timestamp_ ;
      /**
       * <code>int64 timestamp = 2;</code>
       */
      public long getTimestamp() {
        return timestamp_;
      }
      /**
       * <code>int64 timestamp = 2;</code>
       */
      public Builder setTimestamp(long value) {
        
        timestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 timestamp = 2;</code>
       */
      public Builder clearTimestamp() {
        
        timestamp_ = 0L;
        onChanged();
        return this;
      }

      private com.google.protobuf.MapField<
          String, String> data_;
      private com.google.protobuf.MapField<String, String>
      internalGetData() {
        if (data_ == null) {
          return com.google.protobuf.MapField.emptyMapField(
              DataDefaultEntryHolder.defaultEntry);
        }
        return data_;
      }
      private com.google.protobuf.MapField<String, String>
      internalGetMutableData() {
        onChanged();;
        if (data_ == null) {
          data_ = com.google.protobuf.MapField.newMapField(
              DataDefaultEntryHolder.defaultEntry);
        }
        if (!data_.isMutable()) {
          data_ = data_.copy();
        }
        return data_;
      }

      public int getDataCount() {
        return internalGetData().getMap().size();
      }
      /**
       * <code>map&lt;string, string&gt; data = 3;</code>
       */

      public boolean containsData(
          String key) {
        if (key == null) { throw new NullPointerException(); }
        return internalGetData().getMap().containsKey(key);
      }
      /**
       * Use {@link #getDataMap()} instead.
       */
      @Deprecated
      public java.util.Map<String, String> getData() {
        return getDataMap();
      }
      /**
       * <code>map&lt;string, string&gt; data = 3;</code>
       */

      public java.util.Map<String, String> getDataMap() {
        return internalGetData().getMap();
      }
      /**
       * <code>map&lt;string, string&gt; data = 3;</code>
       */

      public String getDataOrDefault(
          String key,
          String defaultValue) {
        if (key == null) { throw new NullPointerException(); }
        java.util.Map<String, String> map =
            internalGetData().getMap();
        return map.containsKey(key) ? map.get(key) : defaultValue;
      }
      /**
       * <code>map&lt;string, string&gt; data = 3;</code>
       */

      public String getDataOrThrow(
          String key) {
        if (key == null) { throw new NullPointerException(); }
        java.util.Map<String, String> map =
            internalGetData().getMap();
        if (!map.containsKey(key)) {
          throw new IllegalArgumentException();
        }
        return map.get(key);
      }

      public Builder clearData() {
        internalGetMutableData().getMutableMap()
            .clear();
        return this;
      }
      /**
       * <code>map&lt;string, string&gt; data = 3;</code>
       */

      public Builder removeData(
          String key) {
        if (key == null) { throw new NullPointerException(); }
        internalGetMutableData().getMutableMap()
            .remove(key);
        return this;
      }
      /**
       * Use alternate mutation accessors instead.
       */
      @Deprecated
      public java.util.Map<String, String>
      getMutableData() {
        return internalGetMutableData().getMutableMap();
      }
      /**
       * <code>map&lt;string, string&gt; data = 3;</code>
       */
      public Builder putData(
          String key,
          String value) {
        if (key == null) { throw new NullPointerException(); }
        if (value == null) { throw new NullPointerException(); }
        internalGetMutableData().getMutableMap()
            .put(key, value);
        return this;
      }
      /**
       * <code>map&lt;string, string&gt; data = 3;</code>
       */

      public Builder putAllData(
          java.util.Map<String, String> values) {
        internalGetMutableData().getMutableMap()
            .putAll(values);
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:com.w2cx.sns.sdk.server.model.proto.Model)
    }

    // @@protoc_insertion_point(class_scope:com.w2cx.sns.sdk.server.model.proto.Model)
    private static final Model DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Model();
    }

    public static Model getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Model>
        PARSER = new com.google.protobuf.AbstractParser<Model>() {
      public Model parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Model(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Model> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Model> getParserForType() {
      return PARSER;
    }

    public Model getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_w2cx_sns_sdk_server_model_proto_Model_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_w2cx_sns_sdk_server_model_proto_Model_fieldAccessorTable;
  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_w2cx_sns_sdk_server_model_proto_Model_DataEntry_descriptor;
  @SuppressWarnings("unused")
private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_w2cx_sns_sdk_server_model_proto_Model_DataEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\016SentBody.proto\022#com.w2cx.sns.sdk.serve" +
      "r.model.proto\"\230\001\n\005Model\022\013\n\003key\030\001 \001(\t\022\021\n\t" +
      "timestamp\030\002 \001(\003\022B\n\004data\030\003 \003(\01324.com.w2cx" +
      ".sns.sdk.server.model.proto.Model.DataEn" +
      "try\032+\n\tDataEntry\022\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002" +
      " \001(\t:\0028\001B\017B\rSentBodyProtob\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_w2cx_sns_sdk_server_model_proto_Model_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_w2cx_sns_sdk_server_model_proto_Model_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_w2cx_sns_sdk_server_model_proto_Model_descriptor,
        new String[] { "Key", "Timestamp", "Data", });
    internal_static_com_w2cx_sns_sdk_server_model_proto_Model_DataEntry_descriptor =
      internal_static_com_w2cx_sns_sdk_server_model_proto_Model_descriptor.getNestedTypes().get(0);
    internal_static_com_w2cx_sns_sdk_server_model_proto_Model_DataEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_w2cx_sns_sdk_server_model_proto_Model_DataEntry_descriptor,
        new String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
