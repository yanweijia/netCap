package cn.yanweijia.ui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {
	private NetworkInterface networkInterface;	//网卡接口
	private JPanel contentPane;
	private JLabel label_info;	//显示网卡信息的标签
	private boolean isCapturing = false;	//是否正在抓包
	private JPanel panel;
	private JTextArea textArea;
	private JpcapCaptor captor;	//用于与网络设备进行连接
	
	/**
	 * @author weijia
	 * @param index 网卡编号
	 */
	public MainWindow(int index) {
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		if(index<0 || index>JpcapCaptor.getDeviceList().length){
			JOptionPane.showMessageDialog(null, "网卡信息获取出错!");
			return;
		}
		//获取网卡
		networkInterface = JpcapCaptor.getDeviceList()[index];
		
		
		JButton btn_cap = new JButton("开始抓包");
		btn_cap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isCapturing==false){
					btn_cap.setText("停止抓包");
					isCapturing = true;
					//TODO:抓包线程
					new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								captor = JpcapCaptor.openDevice(networkInterface, Integer.MAX_VALUE, false, 20);
								while(isCapturing == true){
									Packet packet = captor.getPacket();
									if(packet!=null)	//如果抓包的数据不为空
										textArea.append("\n"+packet);
									
									
								}
								System.out.println("停止抓包~~~");
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}).start();;
					
					
					
				}else{
					btn_cap.setText("开始抓包");
					isCapturing = false;
				}
				
				
			}
		});
		btn_cap.setBounds(837, 11, 124, 79);
		contentPane.add(btn_cap);
		
		label_info = new JLabel("网卡信息:");
		label_info.setBounds(10, 11, 817, 79);
		contentPane.add(label_info);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 101, 963, 531);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);	//textArea嵌套在ScrollPane里面放在面板中央
		
		
		setResizable(false);
		setTitle("抓包");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 999, 682);
		//获取网卡信息并显示
		init();
	}
	/**
	 * 初始化,获取网卡信息
	 */
	private void init(){
		String name = networkInterface.description;	//网卡名称
		String datalink_description = networkInterface.datalink_description;	//网络类型
		String datalink_name = networkInterface.datalink_name;	//具体网络类型
		String addr = "";	//网卡对应地址
		NetworkInterfaceAddress[] address = networkInterface.addresses;
		for(int i = 0 ; i < address.length ; i++){
			addr += "地址" + i + ":" + address[i].address;
		}
		addr = addr.replaceAll("/", "");
		String content = "<html>网卡名称:" + name + "<br>网络类型:" + datalink_description + "<br>具体网络类型:" + datalink_name + "<br>网卡地址:" + addr + "</html>";
		label_info.setText(content);
	}
	
	
	
	public static void main(String[] args){
		new MainWindow(0);
	}
}
