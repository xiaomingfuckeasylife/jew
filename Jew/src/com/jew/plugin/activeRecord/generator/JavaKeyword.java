package com.jew.plugin.activeRecord.generator;

import java.util.HashSet;
import java.util.Set;

public class JavaKeyword {
	
	private String[] keywordArray = {
			"abstract",
			"assert",
			"boolean",
			"break",
			"byte",
			"case",
			"catch",
			"char",
			"class",
			"const",
			"continue",
			"default",
			"do",
			"double",
			"else",
			"enum",
			"extends",
			"final",
			"finally",
			"float",
			"for",
			"goto",
			"if",
			"implements",
			"import",
			"instanceof",
			"int",
			"interface",
			"long",
			"native",
			"new",
			"package",
			"private",
			"protected",
			"public",
			"return",
			"strictfp",
			"short",
			"static",
			"super",
			"switch",
			"synchronized",
			"this",
			"throw",
			"throws",
			"transient",
			"try",
			"void",
			"volatile",
			"while"
		};
	
	private Set<String> keywords = init();
			
	public Set<String> init(){
		Set<String> keys = new HashSet<String>();
		for(int i=0;i<keywordArray.length;i++){
			keys.add(keywordArray[i]);
		}
		return keys;
	}
	
	public void addKeywords(String keywords){
		if(keywords != null){
			this.keywords.add(keywords);
		}
	}
	
	public boolean isKeywords(String keywords){
		return this.keywords.contains(keywords);
	}
}
