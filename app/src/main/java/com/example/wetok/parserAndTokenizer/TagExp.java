package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * LitExp: it is extended from the abstract class Exp,
 * 		   This class is used to represented the expression of 32-bit unsigned integer
 *
 * You are not required to implement any function inside this class.
 * Please do not change any thing inside this class as well.
 */

public class TagExp extends Exp {
	
	private String value;

	public TagExp(String value) {
		this.value = value;
	}

	@Override
	public String show() {
		return value.toString();
	}

	@Override
	public List<Post> evaluate() {
		List<Post> result = new ArrayList<>();
		return result;
	} //TODO: TAG EVALUATION
}
