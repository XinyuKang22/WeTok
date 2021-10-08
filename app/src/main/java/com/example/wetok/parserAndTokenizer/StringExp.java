package com.example.wetok.parserAndTokenizer;

/**
 * LitExp: it is extended from the abstract class Exp,
 * 		   This class is used to represented the expression of 32-bit unsigned integer
 *
 * You are not required to implement any function inside this class.
 * Please do not change any thing inside this class as well.
 */

public class StringExp extends Exp {
	
	private String value;

	public StringExp(String value) {
		this.value = value;
	}

	@Override
	public String show() {
		return value.toString();
	}

	@Override
	public Boolean evaluate() {
		return false;
//		return value;
	} //TODO: 改变返回数据类型
}
