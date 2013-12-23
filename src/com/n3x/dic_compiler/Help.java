package com.n3x.dic_compiler;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.Canvas;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.SystemColor;
import javax.swing.JEditorPane;
import javax.swing.UIManager;
import javax.swing.JSeparator;

import com.sun.awt.AWTUtilities;


public class Help extends JFrame{

	private static int x,y;
	private JPanel contentPane;
	private final JButton btnOk = new JButton("OK");
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	public static boolean rep = true, isRunning=false;

	/**
	 * Create the frame.
	 */
	public Help() {
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(800, 100, 393, 513);
		AWTUtilities.setWindowOpacity(this, 0.0f);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRunning){
					Main.main.tempFile.delete();
					isRunning=false;
					Main.main.stopped=true;
					Main.main.fadeOut(Main.help,0.8f, 10);
					rep = false;
					Main.main.clip.stop();
					Main.main.clip.flush();
					Main.main.clip.close();
				}
			}
		});
		btnOk.setBounds(135, 479, 119, 29);
		contentPane.add(btnOk);
		
		JLabel lblNx = new JLabel("N\u2211X");
		lblNx.setForeground(new Color(30, 144, 255));
		lblNx.setFont(new Font("Iowan Old Style", Font.BOLD, 35));
		lblNx.setBounds(151, 6, 81, 39);
		contentPane.add(lblNx);
		
		lblNewLabel = new JLabel("\u041D\u0435\u0434\u043A\u043E \u041D\u0438\u043A\u043E\u043B\u043E\u0432");
		lblNewLabel.setForeground(new Color(255, 102, 0));
		lblNewLabel.setFont(new Font("Lithos Pro", Font.PLAIN, 8));
		lblNewLabel.setBounds(161, 47, 60, 16);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("v. 1.0.0");
		lblNewLabel_1.setForeground(SystemColor.windowBorder);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 8));
		lblNewLabel_1.setBounds(356, 497, 31, 16);
		contentPane.add(lblNewLabel_1);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		editorPane.setEditable(false);
		editorPane.setFocusable(false);
		editorPane.setContentType("text/html\n");
		editorPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
		editorPane.setText("\u0426\u0435\u043B\u0442\u0430 \u043D\u0430 \u0442\u0430\u0437\u0438 \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u0430 \u0435 \u0441\u044A\u0437\u0434\u0430\u0432\u0430\u043D\u0435\u0442\u043E \u043D\u0430 \u0440\u0435\u0447\u043D\u0438\u043A\u043E\u0432\u0438 \u0444\u0430\u0439\u043B\u043E\u0432\u0435\n\u0432\u044A\u0432 \u0444\u043E\u0440\u043C\u0430\u0442 \u041D\u0435\u043C\u0441\u043A\u0438 - \u0411\u044A\u043B\u0433\u0430\u0440\u0441\u043A\u0438, \u043C\u0430\u043A\u0430\u0440 \u0447\u0435 \u0440\u0430\u0431\u043E\u0442\u0438 \u0437\u0430 \u0432\u0441\u0435\u043A\u0438 \u0435\u0437\u0438\u043A.\n\u0422\u0435\u0437\u0438 \u0444\u0430\u0439\u043B\u043E\u0432\u0435 \u0441\u0435 \u0440\u0430\u0437\u0447\u0438\u0442\u0430\u0442 \u0438 \u0437\u0430\u0440\u0435\u0436\u0434\u0430\u0442 \u043E\u0442 'German Dictionary'\n\u043F\u0440\u043E\u0433\u0440\u0430\u043C\u0430\u0442\u0430 \u0437\u0430 Android\u2122. \u0412\u0441\u0438\u0447\u043A\u0430\u0442\u0430 \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F \u043D\u0430 \u0444\u0430\u0439\u043B\u044A\u0442 \u0435\n\u0441\u044A\u0445\u0440\u0430\u043D\u0435\u043D\u0430 \u0432 .dat \u0444\u0430\u0439\u043B \u0441 \u0438\u043C\u0435\u0442\u043E:<br>\nwords-'\u0434\u0430\u0442\u0430 \u0438 \u0447\u0430\u0441 \u043D\u0430 \u0441\u044A\u0437\u0434\u0430\u0432\u0430\u043D\u0435'-'\u0441\u043B\u0443\u0447\u0430\u0439\u043D\u0430 5 \u0441\u0438\u043C\u0432\u043E\u043B\u043D\u0430 \u043D\u0438\u0448\u043A\u0430'\n<br><br>\n\u0418\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F\u0442\u0430 \u0437\u0430 \u043E\u0442\u0434\u0435\u043B\u043D\u0438\u0442\u0435 \u0434\u0443\u043C\u0438 \u0435 \u0448\u0438\u0444\u0440\u043E\u0432\u0430\u043D\u0430 \u0438\u0437\u043F\u043E\u043B\u0437\u0432\u0430\u0439\u043A\u0438\nAES \u0435\u043D\u043A\u0440\u0438\u043F\u0446\u0438\u044F \u0438 \u0441\u043B\u0443\u0447\u0430\u0439\u043D\u0430 6 \u0441\u0438\u043C\u0432\u043E\u043B\u043D\u0430 \u043F\u0430\u0440\u043E\u043B\u0430 \u0433\u0435\u043D\u0435\u0440\u0438\u0440\u0430\u043D\u0430 \u043F\u0440\u0438\n\u0437\u0430\u043F\u0438\u0441\u0432\u0430\u043D\u0435\u0442\u043E \u043D\u0430 \u0444\u0430\u0439\u043B\u044A\u0442, \u0437\u0430 \u0434\u0430 \u0437\u0430\u0449\u0438\u0442\u0438 \u0434\u0430\u043D\u043D\u0438\u0442\u0435 \u043D\u0430 \u043F\u043E\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043B\u044F.\n\u041F\u0440\u0435\u043F\u043E\u0440\u044A\u0447\u0430\u043D\u043E \u0435 \u043F\u043E\u0442\u0440\u0435\u0431\u0438\u0442\u0435\u043B\u044F\u0442 \u0434\u0430 \u0441\u0438 \u0437\u0430\u043F\u0438\u0448\u0435 \u043F\u0430\u0440\u043E\u043B\u0430\u0442\u0430 \u043F\u043E\u0440\u0430\u0434\u0438\n\u0444\u0430\u043A\u0442\u0430, \u0447\u0435 \u0441\u043B\u0443\u0447\u0430\u0439\u043D\u0438 \u043F\u0430\u0440\u043E\u043B\u0438 \u043B\u0435\u0441\u043D\u043E \u0441\u0435 \u0437\u0430\u0431\u0440\u0430\u0432\u044F\u0442 \u0438 \u0431\u0435\u0437 \u043D\u0435\u044F \u0434\u0430\u043D\u043D\u0438\u0442\u0435\n\u043D\u0430 \u0444\u0430\u0439\u043B\u044A\u0442 \u043D\u0435 \u043C\u043E\u0433\u0430\u0442 \u0434\u0430 \u0441\u0435 \u0440\u0430\u0437\u0447\u0435\u0442\u0430\u0442 \u043E\u0442 \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u0430\u0442\u0430.\n<br><br>\n\u041F\u0440\u043E\u0433\u0440\u0430\u043C\u0430\u0442\u0430 \u0441\u044A\u0449\u043E \u043F\u043E\u0437\u0432\u043E\u043B\u044F\u0432\u0430 \u0437\u0430\u0440\u0435\u0436\u0434\u0430\u043D\u0435\u0442\u043E \u043D\u0430 \u0440\u0435\u0447\u043D\u0438\u043A\u043E\u0432\u0438 \u0444\u0430\u0439\u043B\u043E\u0432\u0435 \u043E\u0442\n\u0438\u043D\u0442\u0435\u0440\u043D\u0435\u0442\u0430, \u0441\u0430\u043C\u043E \u0447\u0440\u0435\u0437 \u0414\u0418\u0420\u0415\u041A\u0422\u0415\u041D \u043B\u0438\u043D\u043A \u043A\u044A\u043C \u0444\u0430\u0439\u043B\u044A\u0442.<br>\n\u041F\u0440\u0438\u043C\u0435\u0440 : http://www.mysite.com/words-xxx.xxx.xxx-jd9V7.dat\n\u041C\u043E\u043B\u044F \u0443\u0432\u0435\u0440\u0435\u0442\u0435 \u0441\u0435, \u0447\u0435 \u043B\u0438\u043D\u043A\u044A\u0442 \u0437\u0430\u0432\u044A\u0440\u0448\u0432\u0430 \u0441 .dat, \u0442\u043E\u0432\u0430 \u043C\u043E\u0436\u0435 \u0434\u0430 \u043E\u0437\u043D\u0430\u0447\u0430\u0432\u0430,\n\u0447\u0435 \u0435 \u0434\u0438\u0440\u0435\u043A\u0442\u0435\u043D.\n<br><br>\nSource \u043A\u043E\u0434\u044A\u0442 \u0437\u0430 \u0432\u0435\u0440\u0441\u0438\u044F 1.0.0 \u043D\u0430 \u0442\u0430\u0437\u0438 \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u0430 \u043A\u0430\u043A\u0442\u043E \u0438 \u043C\u0443\u0437\u0438\u043A\u0430\u0442\u0430\n\u0438\u0437\u043F\u043E\u043B\u0437\u0432\u0430\u043D\u0430 \u0432 \u0442\u043E\u0432\u0430 \u043C\u0435\u043D\u044E \u043C\u043E\u0433\u0430\u0442\u0430 \u0434\u0430 \u0431\u044A\u0434\u0430\u0442 \u043D\u0430\u043C\u0435\u0440\u0435\u043D\u0438 \u043D\u0430 \u0430\u0434\u0440\u0435\u0441\u044A\u0442:\n<a href=\"https://github.com/N3X5?tab=repositories\">https://github.com/N3X5?tab=repositories</a><br>\n\u041C\u043E\u043B\u044F \u0438\u0437\u0432\u0438\u043D\u0435\u0442\u0435 \u0443\u0436\u0430\u0441\u043D\u043E\u0442\u043E \u043C\u0438 \u043E\u0440\u0433\u0430\u043D\u0438\u0437\u0438\u0440\u0430\u043D\u0435 \u043D\u0430 \u043A\u043E\u0434\u0430 \u0438 \u0444\u0430\u043A\u0442\u0430, \u0447\u0435\n\u0438\u0437\u0432\u044A\u0440\u0448\u0432\u0430\u043C \u043D\u0435\u0449\u043E \u043A\u043E\u0435\u0442\u043E \u043C\u043E\u0436\u0435 \u0434\u0430 \u0441\u0435 \u043D\u0430\u043F\u0440\u0430\u0432\u0438 \u0441 400 \u043B\u0438\u043D\u0438\u0438 \u043A\u043E\u0434 \u0441\n700.");
		editorPane.setBackground(UIManager.getColor("Button.background"));
		editorPane.setBounds(6, 70, 381, 404);
		editorPane.addHyperlinkListener(new HyperlinkListener() {
		    public void hyperlinkUpdate(HyperlinkEvent e) {
		        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		        	if(Desktop.isDesktopSupported()) {
							try {
								Desktop.getDesktop().browse(e.getURL().toURI());
							} catch (IOException e1) {}
							catch (URISyntaxException e1) {}
		        	}
		        }
		    }
		});
		contentPane.add(editorPane);
		
		editorPane.addMouseListener(new MouseAdapter() {  
			public void mousePressed(MouseEvent e) {  
			if(!e.isMetaDown()){  
			x = e.getX();  
			y = e.getY();  
			}  
			}  
			});  
		editorPane.addMouseMotionListener(new MouseMotionAdapter() {  
			public void mouseDragged(MouseEvent e) {  
			if(!e.isMetaDown()){  
			Point p = getLocation();  
			setLocation(p.x + e.getX() - x,  
			p.y + e.getY() - y);  
			}  
			}  
			});
		
		addMouseListener(new MouseAdapter() {  
			public void mousePressed(MouseEvent e) {  
			if(!e.isMetaDown()){  
			x = e.getX();  
			y = e.getY();  
			}  
			}  
			});  
		addMouseMotionListener(new MouseMotionAdapter() {  
			public void mouseDragged(MouseEvent e) {  
			if(!e.isMetaDown()){  
			Point p = getLocation();  
			setLocation(p.x + e.getX() - x,  
			p.y + e.getY() - y);  
			}  
			}  
			});
	}
	
}
