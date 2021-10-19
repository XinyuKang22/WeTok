package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;
import com.example.wetok.searchTree.Search;

import java.util.ArrayList;
import java.util.*;

/**
 * AddExp: it is extended from the abstract class Exp.
 *         This class is used to represent the expression of addition
 * @author Yuxin Hong
 * You are not required to implement any function inside this class.
 * Please do not change anything inside this class as well.
 */

public class AndExp extends Exp {
	
	private Exp term;
	private Exp exp;
	
	public AndExp(Exp term, Exp exp) {
		this.term = term;
		this.exp = exp;
	}

	public static List<Post> postIntersection(List<Post> a, List<Post> b) {
		List<Post> result = new ArrayList<>();
		for (int i = 0; i < a.size(); i++) {
			for (int j = 0; j < b.size(); j++) {
				if (a.get(i).equals(b.get(j))) {
					result.add(a.get(i));
				}
			}
		}
		return result;
	}

	@Override
	public String show() {
		return "(" + term.show() + " & " + exp.show() + ")";
	}

	@Override
	public List<Post> evaluate(Search s) {
		List<Post> result = postIntersection(term.evaluate(s), exp.evaluate(s));
		return result;
	}
}