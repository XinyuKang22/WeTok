package com.example.wetok.parserAndTokenizer;

import com.example.wetok.bean.Post;
import com.example.wetok.searchTree.Search;

import java.util.List;

/**
 * Abstract class Exp to represent expressions
 * @author Yuxin Hong
 *
 * You are not required to implement any function inside this class.
 * Please do not change anything inside this class as well.
 */
public abstract class Exp {
	public abstract String show();
	public abstract List<Post> evaluate(Search s);
}
