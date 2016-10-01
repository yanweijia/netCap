package cn.yanweijia.utils;

import java.text.SimpleDateFormat;

import jpcap.packet.Packet;

public class Tools {
	
	/**
	 * byte[]转16进制字符串
	 * @author weijia
	 * @param bArray byte[]数组
	 * @return 对应的十六进制字符串
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	
	/**
	 * 获取系统当前时间,精确到毫秒数
	 * @return 格式为:yyyyMMddHHmmssSSS 如 201609221530555
	 */
	public static String getTime(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		java.util.Date date = new java.util.Date();
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 获取指定包的源地址和目的地址
	 * @param packet
	 * @return
	 */
	public static IntentAddr getAddr(Packet packet){
		IntentAddr intentAddr = new IntentAddr();
		String head = Tools.bytesToHexString(packet.header);
		//把MAC地址显示出来
		String dest = Tools.getMidString(head, 0, 12);
		String source = Tools.getMidString(head, 12, 24);
		
		intentAddr.destinationAddr = dest.substring(0,2)+":"+dest.substring(2, 4)+":"+dest.substring(4,6)+":"+dest.substring(6,8)+":"+dest.substring(8,10)+":"+dest.substring(10);
		intentAddr.sourceAddr = source.substring(0,2)+":"+source.substring(2,4)+":"+source.substring(4,6)+":"+source.substring(6,8)+":"+source.substring(8,10)+":"+source.substring(10);
		
		return intentAddr;
	}
	
	/**
	 * 获取指定字符串的从startIndex到endIndex的值
	 * @param str 给出的字符串
	 * @param startIndex 开始位置
	 * @param endIndex 结束位置,不包含
	 * @return 取到的子字符串
	 */
	public static String getMidString(String str,int startIndex,int endIndex){
		if(str==null)
			return null;
		if(startIndex<0 || endIndex>str.length())
			return null;
		return str.substring(startIndex, endIndex);
	}
	
	/**
	 * 将16进制数字格式化格式,如  0000变成  00 00 并且每16个空两个空格,每32个换行
	 * @param hex 未格式化的十六进制字串
	 * @return 格式化后的十六进制
	 */
	public static String hexFormat(String hex){
		String formatedHex = "";

		for(int i = 1 ; i <= hex.length() ; i++){
			formatedHex += hex.charAt(i-1);
			if(i % 32 == 0){
				formatedHex += "\n";
				continue;
			}
			if(i % 16 == 0){
				formatedHex += "    ";
				continue;
			}
			if(i % 2 == 0){
				formatedHex += " ";
				continue;
			}
		}
		
		return formatedHex;
	}
	
	/**
	 * 获取协议类型
	 * @param packet 协议包
	 * @return 协议类型
	 */
	public static String getProtocolType(Packet packet){
		return getProtocolType(Tools.bytesToHexString(packet.header));
	}
	
	/**
	 * 获取协议类型
	 * @param hexHeader 十六进制的协议头
	 * @return 协议类型
	 */
	public static String getProtocolType(String hexHeader){
		String protocol = hexHeader.substring(24,28);
		if(protocol.equals("0800")){
			protocol = hexHeader.substring(46,48);
			//System.out.println(protocol);
			if(protocol.equals("06"))
				return "TCP";
			if(protocol.equals("11"))
				return "UDP";
			
			return "IP";
		}
		if(protocol.equals("0600")){
			return "ARP";
		}
		
		return "";
	}
	
	/**
	 * 分析协议内容
	 * @param packet 协议包
	 * @return 协议分析结果,多行文本
	 */
	public static String analyzeProtocol(Packet packet){
		String protocolAnalyze = "";
		String protocolType = Tools.getProtocolType(packet);
		String hex = Tools.bytesToHexString(packet.header);
		if(protocolType.equals("TCP") ||  protocolType.equals("UDP")){
			protocolAnalyze+="版本Version:" + hex.charAt(28) + "\n";
			protocolAnalyze+="协议头长HeaderLength:" +(Integer.valueOf(hex.substring(29, 30), 16)*4)+"\n";
			protocolAnalyze+="包总长 TotalLength:" +(Integer.valueOf(hex.substring(32, 36),16))+"\n";
			protocolAnalyze+="源地址Source:"+Integer.valueOf(hex.substring(52, 54),16)+"."+
											Integer.valueOf(hex.substring(54, 56),16)+"."+
											Integer.valueOf(hex.substring(56, 58),16)+"."+
											Integer.valueOf(hex.substring(58,60),16)+"\n";
			
			protocolAnalyze+="目的地址Destination:"+Integer.valueOf(hex.substring(60, 62),16)+"."+
												Integer.valueOf(hex.substring(62, 64),16)+"."+
												Integer.valueOf(hex.substring(64, 66),16)+"."+
												Integer.valueOf(hex.substring(66, 68),16)+"\n";
			//源端口,目标端口
			protocolAnalyze+="源端口SourcePort:"+Integer.valueOf(hex.substring(68, 72),16)+"\n";
			protocolAnalyze+="目标端口DestinationPort:"+Integer.valueOf(hex.substring(72, 76),16)+"\n";
		}
		
		
		
		
		return protocolAnalyze;
	}
	
	
	/**
	 * 封装源地址和目的地址的类
	 * @author weijia
	 *
	 */
	public static class IntentAddr{
		/**源地址*/
		public String sourceAddr;
		/**目的地址*/
		public String destinationAddr;
	}
}
