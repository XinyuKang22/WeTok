package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;
import com.example.wetok.searchTree.Search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * TagExp: it is extended from the abstract class Exp,
 * 		   This class is used to represented the expression of 32-bit unsigned integer
 * @author Yuxin Hong
 * @author Xinyue Hu
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
	public List<Post> evaluate(Search s) {
		List<Post> result = new ArrayList<>();
		result.addAll(s.search(value));
		System.out.println("tag is: " + value + "; result = " + result);
		return result;
	}
}
