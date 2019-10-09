package com.admin.server.utils;

import javax.servlet.http.HttpSession;

import com.admin.server.bean.PbUser;

public class GetUserInfoUtil {
	
	public static PbUser getUserInfo(HttpSession session) {
		return (PbUser) session.getAttribute("user");
	}
}
