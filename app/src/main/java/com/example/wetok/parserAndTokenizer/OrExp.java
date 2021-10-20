package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;
import com.example.wetok.searchTree.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * OrExp: it is extended from the abstract class Exp,
 * 		    This class is used to represent the expression of subtraction
 * @author Yuxin Hong
 */

public class OrExp extends Exp {
	
	private Exp term;
	private Exp exp;
	
	public OrExp(Exp term, Exp exp) {
		this.term = term;
		this.exp = exp;
	}

	public static List<Post> postUnion(List<Post> a, List<Post> b) {
		List<Post> result = new ArrayList<>();
		result.addAll(a);
		for (Post p : b) {
			if (!a.contains(p)) result.add(p);
		}
		return result;
	}

	@Override
	public String show() {
		return "(" + term.show() + " | " + exp.show() + ")";
	}

	@Override
	public List<Post> evaluate(Search s) {
		List<Post> result = postUnion(term.evaluate(s), exp.evaluate(s));
		return result;
	}
}
