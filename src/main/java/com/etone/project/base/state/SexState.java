package com.etone.project.base.state;

/**
 * 性别标识 枚举类型.
 *
 * @author <a href="mailto:88453013@qq.com">Guojian</a>
 * @version $$Revision: 140218 $$
 * @date 2014-02-18 
 */
public enum SexState {
	/** 女(0) */
	girl(0, "女"),
	/** 男(1) */
	boy(1, "男"),
	/** 保密(2) */
	secrecy(2, "保密");

	/**
	 * 值 Integer型
	 */
	private final Integer value;
	/**
	 * 描述 String型
	 */
	private final String description;

	SexState(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 获取值
	 * @return value
	 */
	public Integer getValue() {
		return value;
	}

	/**
     * 获取描述信息
     * @return description
     */
	public String getDescription() {
		return description;
	}

	public static SexState getSexState(Integer value) {
		if (null == value)
			return null;
		for (SexState _enum : SexState.values()) {
			if (value.equals(_enum.getValue()))
				return _enum;
		}
		return null;
	}
	
	public static SexState getSexState(String description) {
		if (null == description)
			return null;
		for (SexState _enum : SexState.values()) {
			if (description.equals(_enum.getDescription()))
				return _enum;
		}
		return null;
	}

}