package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;
import com.example.wetok.searchTree.Search;
import com.example.wetok.view.SearchActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * LitExp: it is extended from the abstract class Exp,
 * 		   This class is used to represented the expression of 32-bit unsigned integer
 *
 * @author Yuxin Hong
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
	public List<Post> evaluate(Search s) {
		List<Post> result = new ArrayList<>();

		if(SearchActivity.tags.contains(value)){
			result.addAll(s.search(value));
		}

		return result;
	}
}
