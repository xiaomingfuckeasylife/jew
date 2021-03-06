package com.jew.test.model.base;

import com.jew.plugin.activeRecord.Model;
import com.jew.plugin.activeRecord.IBean;

/**
 * Generated by Jew, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseComment<M extends BaseComment<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}
	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return get("content");
	}
	public void setUsername(java.lang.String username) {
		set("username", username);
	}

	public java.lang.String getUsername() {
		return get("username");
	}
	public void setBlogId(java.lang.Integer blogId) {
		set("blog_id", blogId);
	}

	public java.lang.Integer getBlogId() {
		return get("blog_id");
	}
}
