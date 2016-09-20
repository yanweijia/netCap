package cn.yanweijia.test;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class test {
	public static void main(String[] args){
		NetworkInterface[] networkInterfaceArray = JpcapCaptor.getDeviceList();
		for(NetworkInterface networkInterface:networkInterfaceArray){
			System.out.println(networkInterface.description);
		}
	}
}
