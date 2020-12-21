//设置窗口语言
var ne_order_setup_win_title = "设置";
var ok = "确定";
var cancel = "取消";
var close = "关闭";
var ne_setup = "设置";

var stream_sortFilterTab_title = "显示";
var stream_paramsSetting_title = "格式";

var time_format = "时间格式:";
var eg_date_time_format = "日期时间: 2013-06-26 10:20:00.000";
var eg_seconds_time_format = "相对于第一条消息的秒数: 1.123";
var eg_relative_time_format = "相对于前一条消息的秒数: 1.123";

var spc_format = "信令码格式:";
var eg_spc_dec_format = "十进制";
var eg_spc_dashed_format = "14 位(3-8-3) / 24 位(8-8-8)";
var eg_spc_hex_format = "十六进制";

//Begin Add by shenhaitao KF47876 for DTS2013121109833
var signplanewintitle = "信令消息流程图";
var userplanewintitle = "用户面消息详情";
//End Add by shenhaitao KF47876 for DTS2013121109833

var callflowdiagram = "流程图";
var callsummary = "会话信息";
var summary_info = "会话信息：";
var messageList = "消息列表";

var label_invalid_time = "参数 ‘starttime’ 无效";
var label_invalid_ngnid = "参数 ‘ngn_cdrid’ 无效";
var label_ngnid_notfound = "单据不存在";

var label_invalid_request = "请求无效";
var label_color_tab = "颜色";
var label_protocol_column = "协议";
var label_normal_color_column = "正常颜色";
var label_error_color_column = "异常颜色";
var label_warning = "警告";
var label_color_reset_normal_question = "确定要恢复到默认？";
var label_color_reset_error_question = "确定要恢复到默认？";
var label_color_toall__normal_question = "确定要应用到所有协议？";
var label_color_toall_error_question = "确定要应用到所有协议？";
var label_color_resetall_question = "确定要全部恢复到默认？";
var label_choosecolor_tip = "选择颜色";
var label_color_reset_tip = "恢复到默认";
var label_color_toall_tip = "应用到所有协议";
var label_color_resetall_tip = "全部恢复到默认";
var label_color_resetall = "全部恢复默认";

var label_messages = "消息列表";
var label_detail_resolve = '消息详情';
var label_message_name = "消息名称";
var label_timestamp = "时间戳";
var label_link = "链路";
var label_ipsrc = "源IP";
var label_ipdst = "目的IP";
var label_linkopc = "源信令点";
var label_linkdpc = "目的信令点";
var label_slc = "链路选择码";
var label_decodetext = "消息详情";
var label_first = "第一条";
var label_previous = "前一条";
var label_next = "后一条";
var label_last = "最后一条";

//VAS CDR
var label_vas_cdr = 'VAS CDR';
var label_reload_tdr = 'Reload TDR';
var label_reload_notify_sms_tdr = 'Reload Notify SMS TDR';
var label_mbanking_tdr = 'MBanking TDR';

//业务轨迹语言
var label_day = '天';//d
var label_second = '秒';//s
var label_hour = '小时';//h
var label_minute = '分';//m

var label_handoverfail = '切换失败';//Handover failure
var label_caller = '主叫';//Calling
var label_called = '被叫';//Called

var label_no_connect = '未接通';//Call Not Connected
var label_connect_delay_long = '接通时延过长';//Connection Delay Tool Long
var label_connect_delay = '接续时长';//Connection Delay
var label_calltime = '通话时长';//Call Duration

var label_num = '序号';//SN
var label_name = '名称';//Name
var label_interface = '接口';//Interface
var label_value = '值';//Value

var label_up_rxlev = '上行电平';//Uplink-Level
var label_dw_rxlev = '下行电平';//Downlink-Level
var label_up_rxqual = '上行质量';//Uplink-Quality
var label_dw_rxqual = '下行质量';//Downlink-Quality
var label_dtxu = 'DTXU';//DTXD
var label_dtxd = 'DTXD';//DTXU
var label_dtx = 'DTX';//DTX

var label_location = '正在定位...';//Locating...
var label_location_fail = '定位失败';//Locating failed
var label_abis_message_list = 'Abis消息列表';//Abis Messages
var label_event_list = '事件列表';//Events
var label_common_message_list = '通用消息列表';//Common Messages
var label_caller_track = '主叫轨迹';//Calling Track
var label_called_track = '被叫轨迹';//Called Track
var label_call_track = '呼叫轨迹';//Call Track

var label_caller_information = '主叫信息';//Calling Information
var label_called_information = '被叫信息';//Called Information
var label_call_information = '呼叫信息';//Call Information
var label_measurement_report = '测量报告';//Measurement Report

var label_continue_uplink_lower_signal = '连续上行弱电平';//Continuous Weak Uplink-Level
var label_continue_downlink_lower_signal = '连续下行弱电平';//Continuous Weak Downlink-Level
var label_continue_uplink_lower_quality = '连续上行质差';//Continuous Bad Uplink-Quality
var label_continue_downlink_lower_quality = '连续下行质差';//Continuous Bad Downlink-Quality
var label_continue_uplink_interference = '连续上行干扰';//Continuous Uplink Interference
var label_continue_downlink_interference = '连续下行干扰';//Continuous Downlink Interference
var label_uplink_lower_signal_percent = '上行弱电平比率';//Weak Uplink-Level Rate
var label_downlink_lower_signal_percent = '下行弱电平比率';//Weak Downlink-Level Rate
var label_uplink_lower_quality_percent = '上行质差比率';//Bad Uplink-Quality Rate
var label_downlink_lower_quality_percent = '下行质差比率';//Bad Downlink-Quality Rate
var label_uplink_interference_percent = '上行干扰比率';//Uplink Interference Rate
var label_downlink_interference_percent = '下行干扰比率';//Downlink Interference Rate
var label_uplink_downlink_imbalance_percent = '上下行不平衡比率';//Uplink-Downlink Imbalance Rate

var label_bsic = 'BSIC';//BSIC
var label_bcch = 'BCCH';//BCCH
var label_tei = 'TEI';//TEI
var label_channel = '信道';//Channel
var label_ta = 'TA'; //TA
var label_yes = '是'; //Yes
var label_no = '否'; //No
var label_half_speed = "半速率";//Half Rate
var label_bs_power = "BTS功率";//BTS Power
var label_ms_power = "MS功率";//MS Power
var label_cell = "小区";//Cell
var label_speed = '速度';//Rate
var label_mps = '米/秒';//m/s
var label_arfcn = "ARFCN";//ARFCN
var label_rxlev = "电平";//Level

var label_with = '有';//Yes
var label_without = '没有';//No
var label_root_site = '室分';//Indoor
var label_outer_site = '宏站';//Macro
var label_cell_name = '小区名称';//Cell Name
var label_lon = '经度';//Longitude
var label_lat = '纬度';//Latitude
var label_bs_type = '基站类型';//BTS Type
var label_repeatern = '直放站';//Repeater
var label_vender = '设备厂家';//Vender
var label_cell_ant_height = '天线挂高';//Antenna Height
var label_direction_angle = '方向角';//Direction Angle
var label_down_tilt = '下倾角';//Tilt Angle
var label_electric_tilt = '电子下倾角';//Electric Tilt Angle
var label_mechanical_tilt = '机械下倾角';//Mechanical Tilt Angle
var label_band = '频段';//Frequency Band
var label_tch = 'TCH频点';//TCH Frequency
var label_conf = '载频数';//Number of Frequencies
var label_cro = '重选偏移(CRO)';//CRO
var label_accmin = 'MS最小接入电平(ACCMIN)';//MS Minimum Rx Level (ACCMIN)
var label_crh = '重选滞后(CRH)';//CRH
var label_rlinkt = '无线链路超时';//Radio-Link Timed Out
var label_hop = '是否跳频';//Frequency hopping
var label_cell_reselectn = '重选邻区频点';//Neighboring Cell Frequency for Reselection
var label_hon = '切换邻区频点';//Neighboring Cell Frequency for Handover
var label_t3212 = '周期位置更新定时器(T3212)';//Periodic Location Update Timer (T3212)

var label_scell = 'S';//S
var label_ncell = 'N';//N

var label_imsi = 'IMSI';//IMSI
var label_msisdn = 'MSISDN';//MSISDN
var label_starttime = '开始时间';//Start Time
var label_handover_times = '切换次数';//Handover Times
var label_used_cells = '占用小区';//Serving Cells
var label_avg_uplink_lev = '上行平均接收电平';//Average Uplink-Level
var label_avg_downlink_lev = '下行平均接收电平';//Average Downlink-Level
var label_avg_uplink_quality = '上行平均接收质量';//Average Uplink-Quality
var label_avg_downlink_quality = '下行平均接收质量';//Average Downlink-Quality
var label_avg_ta = '平均TA';//Average TA
var label_half_speed_percent = '半速率比率';//Half-Rate Rate
var label_lost_call = '掉话';//Call Drop

var label_getrawsignfailed = "无法获取原始信令";
var label_errorcode = "错误码";

var label_show_ip_spc = "显示信令点/IP地址";
var show_format = "显示格式:";
var save = "保存设置";

var _ParameterError = "查询参数异常";