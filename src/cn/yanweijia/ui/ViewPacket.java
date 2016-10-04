package cn.yanweijia.ui;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import cn.yanweijia.utils.Tools;
import jpcap.packet.Packet;
import java.awt.Font;
import javax.swing.JLabel;

public class ViewPacket extends JFrame {

	private static final long serialVersionUID = 4456987364192802009L;
	private JPanel contentPane;
	private JPanel panelSouth;
	private JTextArea textArea_hex;
	private JLabel label_protocolType,label_protocolAnalyze;	//协议类型,协议分析
	private JScrollPane scrollPane;	//放协议hex数据TextArea_hex内容
	private JLabel label_img;	//放协议图片的label


	public ViewPacket(Packet packet) {
		setTitle("ViewPacket");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 774, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new BorderLayout(0, 0));
		textArea_hex = new JTextArea();
		textArea_hex.setFont(new Font("Monospaced", Font.BOLD, 18));
		textArea_hex.setRows(10);
		JScrollPane scrollpaneHead = new JScrollPane();
		scrollpaneHead.setViewportView(textArea_hex);
		panelSouth.add(scrollpaneHead, BorderLayout.NORTH);
		label_protocolType = new JLabel("协议类型:");
		contentPane.add(label_protocolType, BorderLayout.NORTH);
		
		label_protocolAnalyze = new JLabel("协议分析:<br>");
		label_protocolAnalyze.setFont(new Font("Tahoma", Font.PLAIN, 18));
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(label_protocolAnalyze);
		contentPane.add(scrollPane, BorderLayout.WEST);
		
		label_img = new JLabel("协议图片:");
		contentPane.add(label_img, BorderLayout.CENTER);
		
		textArea_hex.append(Tools.hexFormat(Tools.bytesToHexString(packet.header)+Tools.bytesToHexString(packet.data)));
		init(packet);
		setVisible(true);
	}
	private void init(Packet packet){
		//协议类型
		String protocolType = Tools.getProtocolType(packet);
		label_protocolType.setText("协议类型:" + protocolType);
		String protocolAnalyze = Tools.analyzeProtocol(packet);
		protocolAnalyze="<html>" + protocolAnalyze.replaceAll("\n", "<br/>") + "</html>";
		label_protocolAnalyze.setText(protocolAnalyze);


		//设置协议分析的图片
		String img = null;
		if(protocolType.equals("TCP") || protocolType.equals("ICMP"))
			img = "img_tcp.jpg";
		if(protocolType.equals("UDP") || protocolType.equals("OICQ"))
			img = "img_udp.jpg";
		if(protocolType.equals("ARP"))
			img = "img_arp.jpg";
		String path = System.getProperty("user.dir") + "\\" + img;
		//System.out.println(path);
		Icon icon=new ImageIcon(path);
		label_img.setIcon(icon);
	}

}
