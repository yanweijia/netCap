package cn.yanweijia.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartupWindow extends JFrame {
	//默认序列化ID
	private static final long serialVersionUID = 8122641499996033401L;
	private JPanel contentPane;
	private JComboBox comboBox;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartupWindow frame = new StartupWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartupWindow() {
		setTitle("请选择网卡");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 451, 194);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox();
		comboBox.setBounds(138, 39, 259, 20);
		contentPane.add(comboBox);
		//初始化网卡信息并添加进下拉框中
		NetworkInterface[] networkInterfaceArray = JpcapCaptor.getDeviceList();
		for(NetworkInterface networkInterface:networkInterfaceArray){
			//System.out.println(networkInterface.description);
			comboBox.addItem(networkInterface.description);
//			System.out.println(networkInterface.addresses[1].address.getHostAddress());	//基本上地址0为ipv6地址,地址1一般为ipv4地址
//			System.out.println(networkInterface.datalink_description);	//网络类型,以太网（Ethernet）、无线LAN网（wireless LAN）、令牌环网(token ring)等
//			System.out.println(networkInterface.datalink_name); //具体网络类型,如EN10MB,Ethernet等
//			System.out.println(networkInterface.loopback);	//是否本地回环地址 127.0.0.1(本机地址)
		}
		
		JLabel lblNewLabel = new JLabel("选择网卡");
		lblNewLabel.setBounds(30, 42, 72, 14);
		contentPane.add(lblNewLabel);
		
		JButton btn_ok = new JButton("确定");
		btn_ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//选择网卡
				int index = comboBox.getSelectedIndex();
				JOptionPane.showMessageDialog(StartupWindow.this, "You have selected index :  "+index);
		
			}
		});
		btn_ok.setBounds(43, 87, 141, 40);
		contentPane.add(btn_ok);
		
		JButton btn_cancel = new JButton("取消");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//关闭窗口
				StartupWindow.this.dispose();
			}
		});
		btn_cancel.setBounds(256, 88, 141, 38);
		contentPane.add(btn_cancel);
	}
}
