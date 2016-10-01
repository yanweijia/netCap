package cn.yanweijia.ui;

import java.awt.BorderLayout;
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
	private JTextArea textArea_head;
	private JLabel label_protocolType,label_protocolAnalyze;
	private JScrollPane scrollPane;


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
		textArea_head = new JTextArea();
		textArea_head.setFont(new Font("Monospaced", Font.BOLD, 18));
		textArea_head.setRows(10);
		JScrollPane scrollpaneHead = new JScrollPane();
		scrollpaneHead.setViewportView(textArea_head);
		panelSouth.add(scrollpaneHead, BorderLayout.NORTH);
		label_protocolType = new JLabel("协议类型:");
		contentPane.add(label_protocolType, BorderLayout.NORTH);
		
		label_protocolAnalyze = new JLabel("协议分析:<br>");
		label_protocolAnalyze.setFont(new Font("Tahoma", Font.PLAIN, 18));
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(label_protocolAnalyze);
		contentPane.add(scrollPane, BorderLayout.WEST);
		
		textArea_head.append(Tools.hexFormat(Tools.bytesToHexString(packet.header)));
		
		init(packet);
		setVisible(true);
	}
	private void init(Packet packet){
		label_protocolType.setText("协议类型:" + Tools.getProtocolType(packet));
		String protocolAnalyze = Tools.analyzeProtocol(packet);
		protocolAnalyze="<html>" + protocolAnalyze.replaceAll("\n", "<br/>") + "</html>";
		label_protocolAnalyze.setText(protocolAnalyze);
	}

}
