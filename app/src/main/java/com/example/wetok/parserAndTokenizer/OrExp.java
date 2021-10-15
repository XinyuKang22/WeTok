package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * SubExp: it is extended from the abstract class Exp,
 * 		    This class is used to represent the expression of subtraction
 *
 * Please do not change anything else.
 */

public class OrExp extends Exp {
	
	private Exp term;
	private Exp exp;
	
	public OrExp(Exp term, Exp exp) {
		this.term = term;
		this.exp = exp;
	}

	@Override
	public String show() {
		return "(" + term.show() + " - " + exp.show() + ")";
	}

	@Override
	public List<Post> evaluate() {
		List<Post> result = new ArrayList<>(term.evaluate());
		result.addAll(exp.evaluate());
		return result;
	}
}
