package com.hexin.user.utils;


import java.util.Stack;

/**
 * 
 * ByteUtil
 *
 * @description 字节工具
 */
public class ByteUtil
{
	//将字节数组转换为short类型，即统计字符串长度
	public static short bytes2Short2(byte[] b) {
		short i = (short) (((b[1] & 0xff) << 8) | b[0] & 0xff);
		return i;
	}
	//将字节数组转换为16进制字符串
	public static String BinaryToHexString(byte[] bytes) {
		String hexStr = "0123456789ABCDEF";
		String result = "";
		String hex = "";
		for (byte b : bytes) {
			hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
			hex += String.valueOf(hexStr.charAt(b & 0x0F));
			result += hex + " ";
		}
		return result;
	}
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		// toUpperCase将字符串中的所有字符转换为大写
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		// toCharArray将此字符串转换为一个新的字符数组。
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	//charToByte返回在指定字符的第一个发生的字符串中的索引，即返回匹配字符
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}



	/**
	 * @Title:hexString2String
	 * @Description:16进制字符串转字符串
	 * @param src
	 *            16进制字符串
	 * @return 字节数组
	 * @throws
	 */
	public static String hexString2String(String src) {
		String temp = "";
		for (int i = 0; i < src.length() / 2; i++) {
			temp = temp
					+ (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),
					16).byteValue();
		}
		return temp;
	}

	public static void main(String[] args){
		System.out.println(hexString2String("0808"));
		//System.out.println(hexStringToString(hexStr.replace(" ","")));
		System.out.println("12345".substring(2,5));
		int[] ints = new int[3];
		System.out.println(ints.length);
		Stack<Double> stack = new Stack<>();
		stack.size();

	}

}
