package com.example.zhbj.domain;

import java.util.ArrayList;

public class NewsTabBean {

	public NewsTab data;
	
	public class NewsTab{
		public String more;
		public ArrayList<NewsData> news;
		public ArrayList<TopNews> topnews;
	}
	
	public class NewsData{
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
	}
	
	public class TopNews{
		public String id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;
	}
}
