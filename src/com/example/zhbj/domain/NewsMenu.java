package com.example.zhbj.domain;

import java.util.ArrayList;

public class NewsMenu {
	public int retcode;
	public ArrayList<NewsMenuData> data;
	public ArrayList<Integer> extend;
	
	public class NewsMenuData{
		public int id;
		public String title;
		public int type;
		public ArrayList<NewsTabData> children;
		
		@Override
		public String toString() {
			return "NewsMenuData [title=" + title + ", children=" + children
					+ "]";
		}
	}
	
	public class NewsTabData{
		public int id;
		public String title;
		public int type;
		public String url;
		@Override
		public String toString() {
			return "NewsTabData [title=" + title + "]";
		}
	}

	@Override
	public String toString() {
		return "NewsMenu [data=" + data + "]";
	}
	
}
