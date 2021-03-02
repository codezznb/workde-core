package cn.workde.core.validator;

/**
 * @author chenchen
 * @date 2020/06/17
 */
public interface RegexpConstant {

	/**
	 * 电话号码 -> XXX-XXXXXXX"、"XXXX-XXXXXXXX"
	 */
	String REX_MOBILE = "^(\\d{2,4}-?)?\\d{7,8}$";

	/**
	 * 中文和数字
	 */
	String REX_CHINESE_NUMBER = "^[\\u4E00-\\u9FA50-9]+$";

	/**
	 * 中文，英文，数字，标点符号 -> 不允许@#&<>*特殊字符
	 */
	String REX_SPECIAL_CHARACTERS = "([\\u4E00-\\u9FA5A-Za-z0-9]|[,]|[，]|[ ]|[\\\"]|[”]|[“]|[？]|[?]|[!]|[！]|[、]|[。]|[\\\\s]|[《]|[》]|[：]|[:]|[‘]|[’]|[；])*";

	/**
	 * 数字和字母
	 */
	String REX_NUMBER_LETTER = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$";

}
