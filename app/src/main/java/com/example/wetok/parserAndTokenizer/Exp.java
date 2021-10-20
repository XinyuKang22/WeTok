package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;
import com.example.wetok.searchTree.Search;

import java.util.List;

/**
 * Abstract class Exp to represent expressions
 * @author Yuxin Hong
 */
public abstract class Exp {
	public abstract String show();
	public abstract List<Post> evaluate(Search s);
}
