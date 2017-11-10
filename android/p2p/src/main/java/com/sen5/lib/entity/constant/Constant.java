package com.sen5.lib.entity.constant;

public class Constant {

	/**
	 * 盒子设备id字符串中的字串
	 */
	public static final String DID_SLIFE = "SLIFE";

    /**
     * 告知服务器的参数,默认的和SLIFE
     */
    public static final String P2P_PARAM_DEFAULT = "BIHLBDBIKAJKHJJJAPGJBMFNDGJOGPJIHLEPFKCIFCIBKPKFDFBPHHPBDCLNMMOAAFJOLBDHLNNAFGCBJFNNIIAAIMPMEBCKEPGDDKFJMOOO";
    public static final String P2P_PARAM_SLIFE = "ADHOAFAJPFMPCNNCBIHOBAFHDMNFGJJCHDAGFHCFEAIHOLKFDHADCLPBCJLLMMKBEIJCLDHGPJMLEMCDMGMMNOEIIGLHENDLEDCIHNBOMKKFFMCBBH";



    /**
	 * 读取数据的Channel ID，PPCS提供8个Channel，0~7，要与服务端的write channel对应
	 */
	public static final byte CHANNEL_READ = 2;
	/**
	 * 发送数据的Channel ID，PPCS提供8个Channel，0~7，要与服务端的read channel对应
	 */
	public static final byte CHANNEL_WRITE = 1;

    /**
     * 超时时间
      */
	public static final int TIME_OUT = 10*1000;


	/**********************Devices类型***************************/
	//Zigbee设备 ZHA：全拼ZigBee Home Automation ；ZLL:Zigbee Light Link；都是zigbee智能家居的标准
	public static final String ZLL_ACTION_LIGHT_1                         = "AC05E02100000";//ZLL灯
	public static final String ZLL_ACTION_LIGHT_2                         = "AC05E02200000";//ZLL灯
	public static final String ZHA_ZLL_ACTION_LIGHT                       = "A010402200000";//ZLL/ZHA灯
	public static final String ZHA_ZLL_ACTION_LIGHT1                      = "A010402100000";//ZLL灯

	public static final String ZHA_ZLL_ACTION_DIMMABLE_LIGHT              = "A010401010000";//ZLL灯
	public static final String ZHA_ZLL_ACTION_COLOUR_DIMMABLE_LIGHT       = "A010401020000";//ZLL灯
	public static final String ZHA_ZLL_ACTION_COLOUR_TEMPERATURE_LIGHT    = "A0104010C0000";//ZLL灯
	public static final String ZHA_ZLL_ACTION_EXTENDED_COLOUR_LIGHT       = "A0104010D0000";//ZLL灯




	public static final String ZHA_ACTION_LIGHT_SWITCH                    = "A010401030000";//ZHA开关
	public static final String ZHA_ACTION_DIMMER_SWITCH 				  = "A010401040000";//ZHA开关
	public static final String ZHA_ACTION_COLOUR_DIMMER_SWITCH            = "A010401050000";//ZHA开关
	public static final String ZHA_ACTION_ON_OFF_PLUG_IN_UNIT 			  = "A0104010A0000";//ZHA开关
	public static final String ZHA_ACTION_DIMMABLE_PLUG_IN_UNIT           = "A0104010B0000";//ZHA开关



	public static final String ZHA_ACTION_OUTLET_1                        = "A010400090000";//ZHA开关
	public static final String ZHA_ACTION_OUTLET_2                        = "A010400020000";//ZHA开关
	public static final String ZHA_ACTION_OUTLET_EU                       = "A010400510000";//ZHA开关——欧标
	public static final String ZHA_ACTION_RELAY                           = "A010401000000";//ZHA继电器
	public static final String ZHA_ACTION_SECURE_RC                       = "A010404020115";//ZHA安防遥控器
	public static final String ZHA_ACTION_SECURE_RC_NEW                   = "A010404010000";//ZHA安防遥控器  (新增)
	public static final String ZHA_ACTION_EMERGENCY_BUTTON                = "A01040402002C";//ZHA紧急按钮
	public static final String ZHA_ACTION_ALERTOR                         = "A010404030225";//ZHA报警器

	public static final String ZHA_SENSOR                                 = "A010404020000";//ZHA传感器
	public static final String ZHA_SENSOR_DOOR                            = "A010404020015";//ZHA门磁传感器
	public static final String ZHA_SENSOR_INFRARED                        = "A01040402000D";//ZHA红外传感器
	public static final String ZHA_SENSOR_SMOKE                           = "A010404020028";//ZHA烟雾传感器
	public static final String ZHA_SENSOR_COMBUSTIBLE_GAS                 = "A01040402002B";//ZHA易燃气体传感器
	public static final String ZHA_SENSOR_CO                              = "A010404028001";//ZHA一氧化碳传感器
	public static final String ZHA_SENSOR_SHOCK                           = "A01040402002D";//ZHA震动传感器
	public static final String ZHA_SENSOR_WATER                           = "A01040402002A";//ZHA水浸传感器
	public static final String ZHA_SENSOR_HUMITURE                        = "A010403020000";//ZHA温湿度传感器
	public static final String SENSOR_BOX_ALERTOR                         = "B000000000001";//盒子自带报警器

	//ZWave设备
	public static final String ZWAVE_ACTION_OUTLET                        = "CP04100100000";//插座

	public static final String ZWAVE_SENSOR_DOOR  	                      = "CA04070106070";//门磁传感器
	public static final String ZWAVE_SENSOR_CO  	                      = "CA04070102000";//一氧化碳传感器
	public static final String ZWAVE_SENSOR_WATER                         = "CA04070105070";//水浸传感器
	public static final String ZWAVE_SENSOR_SMOKE	                      = "CA04200101000";//烟雾传感器
	public static final String ZWAVE_SENSOR_INFRARED  	                  = "CA04070107000";//红外传感器
	public static final String ZWAVE_SENSOR_COMBUSTIBLE_GAS               = "CA04070112000";//可燃气体传感器
	public static final String ZWAVE_SENSOR_HUMITURE	                  = "CS04210101050";//温湿度传感器
	public static final String ZWAVE_MULTI_DEVICE	                      = "CM04070100000";//多功能设备

	//摄像头
	public static final String DEV_IP_CAMERA	                          = "B000000000002";//摄像头

	public static final String ZLL_ACTION_THERMOSTAT    				  = "A010403010000"; // 恒温器

	/************************************设备状态ID*******************************************/
	/****
	 * 目前有用的是1 ， 64 ， 67
	 * 灯15,16,17
     * 56-63特定设备的
	 */
	public static final int STATUS_ID_UNKNOWN                           = 0;//未知状态
	public static final int STATUS_ID_ON_OFF                            = 1;//开/关状态

	public static final int STATUS_ID_FEIBIT_SENSOR                     = 2;//feibit传感器状态
	public static final int STATUS_ID_FEIBIT_SENSOR_TEMPERATURE         = 3;//feibit传感器温度状态
	public static final int STATUS_ID_FEIBIT_SENSOR_HUMIDITY            = 4;//feibit传感器湿度状态
//	public static final int STATUS_ID_DEVICE_GROUP                      = 5;//device_group
	public static final int STATUS_ID_DEVICE_DID                        = 6;//摄像头的did

	public static final int STATUS_ID_DOOR_SENSOR                       = 7;//门磁传感器状态
	public static final int STATUS_ID_LUMINANCE                         = 8;//亮度状态
	public static final int STATUS_ID_HOME_SECURITY                     = 9;//家庭安全状态
	public static final int STATUS_ID_ZWAVE_HUMIDITY                    = 10;//zwave传感器湿度状态
	public static final int STATUS_ID_WATER_SENSOR                      = 11;//水浸状态
	public static final int STATUS_ID_CO_SENSOR                         = 12;//Co传感器状态
	public static final int STATUS_ID_SMOKE_SENSOR                      = 13;//烟雾状态
	public static final int STATUS_ID_COMBUSTIBLE_GAS_SENSOR            = 14;//易燃气体状态
	public static final int STATUS_ID_LIGHT_BRIGHTNESS                  = 15;//灯的亮度
	public static final int STATUS_ID_LIGHT_HUES                        = 16;//灯的色相
	public static final int STATUS_ID_LIGHT_SATURATION                  = 17;//灯饱和度


	public static final int ZB_STATUS_ID_LOCK                           = 51;//锁定状态 恒温器
	public static final int ZB_STATUS_ID_BATTERY_VALUE                  = 52;//电压值
	public static final int ZB_STATUS_ID_LOW_VOLTAGE                    = 53;//欠压状态
	public static final int ZB_STATUS_ID_LOW_VOLTAGE_RCU                = 54;//低电量：遥控器
	public static final int ZB_STATUS_ID_TIME                           = 55;//时间值
	public static final int ZB_STATUS_ID_LOCAL_TEMPERATURE              = 56;//当前温度
	public static final int ZB_STATUS_ID_SETUP_TEMPERATURE              = 57;//目标温度
	public static final int ZB_STATUS_ID_SYSTEM_MODE                    = 58;//系统模式
	public static final int ZB_STATUS_ID_DEVICE_MODE                    = 59;//设备工作模式
	public static final int ZB_STATUS_ID_FAN_SPEED                      = 60;//风速
	public static final int ZB_STATUS_ID_TEMPERATURE_VALUE              = 61;//温度值
	public static final int ZB_STATUS_ID_HUMIDITY_VALUE                 = 62;//湿度值
	public static final int ZB_STATUS_ID_BATTERY_VALUE_CTRL             = 63;//电压值
	public static final int ZB_STATUS_ID_BASIC                          = 64;//基础属性
	public static final int ZB_STATUS_ID_ZONE_TYPE                      = 65;//补救值
	public static final int ZB_STATUS_ID_ACE_RC_66                      = 66;//遥控器报警值: 在家撤防,布防,撤防

	public static final int ZB_SENSER_ONLINE_AND_STATUS                 = 67;//开机时 在线或


    public static final int ACTION_REFRIGERATION 	    = 3;//制冷
    public static final int ACTION_HEATING 	            = 4;//制热

    public static final int ACTION_WIND_AUTO 	        = 0;// 自动风速
    public static final int ACTION_WIND_ONE 	        = 1;// 风速1
    public static final int ACTION_WIND_TWO  	        = 2;// 风速2
    public static final int ACTION_WIND_THREE  	        = 3;// 风速3

    /************************************设备动作ID*******************************************/
	public static final int ACTION_ID_ON_OFF 	        = 1;//on/off
    public static final int ACTION_ID_LEVEL 	        = 2;//亮度
	public static final int ACTION_ID_HUES_SATURATION 	= 3;//色相和饱和度

	/************************************恒温器 设备动作ID*******************************************/
	public static final int ZB_ACTION_ID_LOCK	       		 = 4;	// 锁定状态  "lock_status":[0, 1]  1：设备锁住，用于恒温器的面板
	public static final int ZB_ACTION_ID_TIME	       		 = 5;	// 时间值
	public static final int ZB_ACTION_ID_SETUP_TEMPERATURE 	 = 6;	// 设置目标温度
	public static final int ZB_ACTION_ID_SYSTEM_MODE		 = 7;	// 系统模式 制冷制热
	public static final int ZB_ACTION_ID_POWER_MODE 		 = 8;	// 设备工作模式 power 电源开关
	public static final int ZB_ACTION_ID_FAN_SPEED           = 9;	// 风速
	public static final int ZB_ACTION_ID_ACE_RC_66   		 = 10;	// 遥控器报警值: 在家撤防,布防,撤防


	/******************************p2p通信*******************************/
	public static final String MSG_TYPE = "msg_type";

    public static final String IDENTITY_ID = "identity_id";

    /**
     * 消息体
     */
    public static final String MSG_BODY = "msg_body";

	/**
	 * 连接
	 */
	public static final int MSG_CONNECT = 99;

	/**
	 * 断开连接
	 */
	public static final int MSG_DISCONNECT = -99;


	// 手机端->盒子端msg_type
	//心跳包
	public static final int MSG_HEARTBEAT_PACKETS                     = 3;

	//1、设备操作
	public static final int MSG_LIST_DEVICE                           = 101;
	public static final int MSG_EDIT_DEVICE                           = 102;
	public static final int MSG_ADD_DEVICE 			                  = 103;
	public static final int MSG_DELETE_DEVICE                         = 104;
	public static final int MSG_CONTROL_DEVICE 			              = 105;
	public static final int MSG_REQUEST_SINGLE_DEVICE_STATUS          = 106;
	public static final int MSG_REQUEST_ALL_DEVICE_STATUS             = 107;

	//2、房间操作
	public static final int MSG_LIST_ROOM                             = 201;
	public static final int MSG_NEW_ROOM                              = 202;
	public static final int MSG_DELETE_ROOM                           = 203;
	public static final int MSG_EDIT_ROOM                             = 204;
	//3、场景操作
	public static final int MSG_LIST_SCENE                            = 301;
	public static final int MSG_NEW_SCENE                             = 302;
	public static final int MSG_DELETE_SCENE                          = 303;
	public static final int MSG_EDIT_SCENE                            = 304;
	public static final int MSG_APPLY_SCENE                           = 305;
	//4、模式
	public static final int MSG_LIST_MODE                             = 401;
	public static final int MSG_EDIT_MODE                             = 402;
	public static final int MSG_APPLY_MODE                            = 403;
	//5、收藏
	public static final int MSG_EDIT_FAVORITE                         = 501;
	public static final int MSG_LIST_FAVORITE                         = 502;
	//6、成员
	public static final int MSG_IDENTITY_VERIFY                       = 601; //验证身份
	public static final int MSG_LIST_USER                             = 602;
	public static final int MSG_ADD_USER                              = 603;
	public static final int MSG_DELETE_USER                           = 604;
	public static final int MSG_RENAME_USER                           = 608;
	//日志
	public static final int MSG_REQUEST_ALL_DEVICE_LOG                = 801;
	public static final int MSG_REQUEST_SINGLE_DEVICE_LOG             = 802;
	public static final int MSG_REQUEST_ALERTOR_LOG                   = 803;



	//盒子端-->手机端msg_type
    public static final int MSG_DATABASE_VERSION_RESPOND	          = 1;
	//心跳包
	public static final int MSG_HEARTBEAT_PACKETS_RESPOND             = 3;

	//十秒小视频
	public static final int MSG_TEN_SECOND_VIDEO                      =902;

	//1、设备
	public static final int MSG_LIST_DEVICE_RESPOND                   = 101;
	public static final int MSG_EDIT_DEVICE_RESPOND	                  = 102;
	public static final int MSG_ADD_DEVICE_RESPOND                    = 103;
	public static final int MSG_DELETE_DEVICE_RESPOND                 = 104;
	public static final int MSG_CONTROL_DEVICE_RESPOND                = 105;
	public static final int MSG_REPORT_DEVICE_STATUS_RESPOND          = 106;
	public static final int MSG_REQUEST_ALL_DEVICE_STATUS_RESPOND     = 107;
	//2、房间
	public static final int MSG_LIST_ROOM_RESPOND                     = 201;
	public static final int MSG_NEW_ROOM_RESPOND                      = 202;
	public static final int MSG_DELETE_ROOM_RESPOND		              = 203;
	public static final int MSG_EDIT_ROOM_RESPOND                     = 204;
    //3、场景
	public static final int MSG_LIST_SCENE_RESPOND                    = 301;
	public static final int MSG_NEW_SCENE_RESPOND                     = 302;
	public static final int MSG_DELETE_SCENE_RESPOND                  = 303;
	public static final int MSG_EDIT_SCENE_RESPOND                    = 304;
	public static final int MSG_APPLY_SCENE_RESPOND                   = 305;
	//4、模式
	public static final int MSG_LIST_MODE_RESPOND                     = 401;
	public static final int MSG_EDIT_MODE_RESPOND                     = 402;
	public static final int MSG_APPLY_MODE_RESPOND                    = 403;
	//5、收藏
	public static final int MSG_EDIT_FAVORITE_RESPOND                 = 501;
	public static final int MSG_LIST_FAVORITE_RESPOND                 = 502;
	//6、成员
	public static final int MSG_LIST_USER_RESPOND                     = 602;
	public static final int MSG_ADD_USER_RESPOND                      = 603;
	public static final int MSG_DELETE_USER_RESPOND                   = 604;
	public static final int MSG_REQUEST_ADD_USER                      = 605;
	public static final int MSG_IDENTITY_LEGAL_RESPOND                = 606;
	public static final int MSG_RENAME_USER_RESPOND                   = 608;

	public static final int MSG_JSON_ERROR_USER_RESPOND               = 613;
	public static final int MSG_ID_ERROR_RESPOND                   	  = 612;
	public static final int MSG_TV_CONNECTION_RESPOND                 = 611;
	public static final int MSG_MORE_USER_BUSY_RESPOND                = 610;
	public static final int MSG_SUPERUSER_NO_CONFIRM_RESPOND          = 609;


	public static final int MSG_IDENTITY_NOT_LEGAL_RESPOND            = 701;

	//日志
	public static final int MSG_REQUEST_ALL_DEVICE_LOG_RESPOND        = 801;
	public static final int MSG_REQUEST_SINGLE_DEVICE_LOG_RESPOND     = 802;
	public static final int MSG_REQUEST_ALERTOR_LOG_RESPOND           = 803;


	// 设防模式
	public static final int MODE_AWAY 	    = 1;	//离家模式
	public static final int MODE_STAY 	    = 2;	//在家模式
	public static final int MODE_DISARM 	= 3;	//撤防模式

	//设备类型
	public static final int MODE_SENSOR = 0;
	public static final int MODE_ACTION = 1;
	public static final int MODE_FULL = 2;

	//设备控制参数
	public static final int ACTION_OFF 	= 0;
	public static final int ACTION_ON 	= 1;

	//Home id类型
	public static final String HOME_TYPE_SLIFE = "SLIFE";
	public static final String HOME_TYPE_DDD = "DDD";




}
