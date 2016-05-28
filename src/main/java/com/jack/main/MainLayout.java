package com.jack.main;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainLayout {
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("RemoteClose");
		Container container = frame.getContentPane();
		JPanel panel = new JPanel();
		container.add(panel);
		panel.setLayout(new FlowLayout());
		JButton button = new JButton("Start");
		panel.add(button);
		
		//set Listener
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					initSocket();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		});
		
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				System.exit(0);
			}
		});
		
		frame.setSize(300, 100);
		frame.setVisible(true);
	}
	
	private static final void initSocket() throws IOException {
		ServerSocket serverSocket = new ServerSocket(6789);
		Socket socket = serverSocket.accept();
		InputStream inputStream = socket.getInputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length=inputStream.read(buffer))!=-1) {
			String cmd = new String(buffer, 0, length);
			if (cmd.equals("shutdown")) {
				Runtime.getRuntime().exec("shutdown -s -f -t 3");
			}
		}
		serverSocket.close();
	}
}
