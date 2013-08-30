package com.zju.model;

/**
 * Constant values used throughout the application.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class Constants {

	private Constants() {
		// hide me
	}

	// ~ Static fields/initializers =============================================

	/**
	 * The name of the ResourceBundle used in this application
	 */
	public static final String BUNDLE_KEY = "ApplicationResources";

	/**
	 * File separator from System properties
	 */
	public static final String FILE_SEP = System.getProperty("file.separator");

	/**
	 * User home from System properties
	 */
	public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

	/**
	 * The name of the configuration hashmap stored in application scope.
	 */
	public static final String CONFIG = "appConfig";

	/**
	 * Session scope attribute that holds the locale set by the user. By setting this key to the same one that Struts
	 * uses, we get synchronization in Struts w/o having to do extra work or have two session-level variables.
	 */
	public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

	/**
	 * The request scope attribute under which an editable user form is stored
	 */
	public static final String USER_KEY = "userForm";

	/**
	 * The request scope attribute that holds the user list
	 */
	public static final String USER_LIST = "userList";

	/**
	 * The request scope attribute for indicating a newly-registered user
	 */
	public static final String REGISTERED = "registered";

	/**
	 * The name of the Administrator role, as specified in web.xml
	 */
	public static final String ADMIN_ROLE = "ROLE_ADMIN";

	/**
	 * The name of the User role, as specified in web.xml
	 */
	public static final String USER_ROLE = "ROLE_USER";

	/**
	 * The name of the user's role list, a request-scoped attribute when adding/editing a user.
	 */
	public static final String USER_ROLES = "userRoles";

	/**
	 * The name of the available roles list, a request-scoped attribute when adding/editing a user.
	 */
	public static final String AVAILABLE_ROLES = "availableRoles";

	/**
	 * The name of the CSS Theme setting.
	 */
	public static final String CSS_THEME = "csstheme";

	/**
	 * 列表页面每页显示的数量
	 */
	public static final Integer PAGE_SIZE = 15;

	/**
	 * 结果修改时的操作类型
	 */
	public static final String ADD = "添加";
	public static final String DELETE = "删除";
	public static final String EDIT = "修改";
	public static final String DRAG = "拖拽";

	/**
	 * 填充hi,lo,resultFlag的标记
	 */
	public static final Integer FILL_FLAG = 2;

	/**
	 * 修改testresult值的标记
	 */
	public static final Integer EDIT_FLAG = 3;

	/**
	 * 添加testResult整条数据的标记
	 */
	public static final Integer ADD_FLAG = 5;

	/**
	 * 删除标记
	 */
	public static final Integer DELETE_FLAG = 7;
	
	public static final Integer MANUAL_EDIT_FLAG = 33;

	/**
	 * 无结果
	 */
	public static final Integer STATUS_NORESULT = -1;
	/**
	 * 未审核
	 */
	public static final Integer STATUS_UNAUDIT = 0;
	/**
	 * 已通过
	 */
	public static final Integer STATUS_PASSED = 1;
	/**
	 * 未通过
	 */
	public static final Integer STATUS_UNPASS = 2;

}
