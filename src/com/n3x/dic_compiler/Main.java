package com.n3x.dic_compiler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JTextField;

import org.apache.commons.io.IOUtils;

import com.apple.eio.FileManager;
import com.sun.awt.AWTUtilities;


public class Main extends JFrame implements Runnable {

	private JPanel contentPane;
    private JTable table = null;
    private DefaultTableModel dtm = null;
    private String[] wordTypes = { "Съществително", "Прилагателно", "Глагол"}, genderTypes = {"der", "das", "die"};
    private JComboBox genderList = new JComboBox(genderTypes), typeList = new JComboBox(wordTypes);
    private String userKey, chosenDir= "";
	private File chosenFile= null;
    private final JFileChooser fileChooser = new JFileChooser(), fileChooser2 = new JFileChooser();
    private JTextField textField;
    public static Main main = new Main();
    public static Help help = new Help();
    public Clip clip;
    private String os = System.getProperty("os.name");
    public File tempFile = null;
    public boolean stopped = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(os.contains("Linux")||os.contains("Windows"))
			setBounds(100, 100, 640, 369);
		else
			setBounds(100, 100, 640, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		setTitle("Компилатор на Речници -- Немски език");
		
	    Object rowData[][] = {{ "----", "der", "----", "----", "Съществително"}};
		    Object columnNames[] = { "Дума", "Род", "Множественно число", "Превод", "Тип" };
		    
		    dtm = new DefaultTableModel(rowData, columnNames);
		    table = new JTable(dtm);
		    table.putClientProperty("terminateEditOnFocusGained", Boolean.TRUE);
		    table.setGridColor(Color.LIGHT_GRAY);
		    table.getTableHeader().setReorderingAllowed(false);
		    table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		    	{
		    	public void valueChanged(ListSelectionEvent e)
		    		{
		    			if(table.getSelectedRow()!=-1)
		    			if(dtm.getValueAt(table.getSelectedRow(),4)!="Съществително"){
		    				genderList.setEnabled(false);
		    			}
		    			else{
		    				genderList.setEnabled(true);
		    			}
		    		}

		    	});
		    contentPane.setLayout(null);

		    
		    JScrollPane scrollPane = new JScrollPane(table);
		    scrollPane.setBounds(5, 5, 630, 238);
		    scrollPane.setFocusable(true);
		    contentPane.add(scrollPane);
		    
		    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    
		    genderList.addFocusListener(new FocusAdapter(){
		    	public void focusGained(FocusEvent e){
	    			if(dtm.getValueAt(table.getSelectedRow(),4)!="Съществително"){  
	    				genderList.setEnabled(false);
	    			}
	    			else{
	    				genderList.setEnabled(true);
	    			}
		    	}
		    });
		    
			TableColumn typeWord = table.getColumnModel().getColumn(4);
			TableColumn gender = table.getColumnModel().getColumn(1);
			genderList.setEnabled(true);
			genderList.setSelectedIndex(0);
			genderList.setEnabled(false);
			gender.setCellEditor(new DefaultCellEditor(genderList));
			typeList.setSelectedIndex(0);
			typeWord.setCellEditor(new DefaultCellEditor(typeList));
			
		    
		    JPopupMenu popupMenu = new JPopupMenu();
	        JMenuItem deleteItem = new JMenuItem("Изтрий Дума");
	        JMenuItem addItem = new JMenuItem("Добави Дума");
	        
	        addItem.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	String rowData[][]  = {{"----","der","----","----","Съществително"}};
	            	Vector<String> v = new Vector<String>();
	            	v.add(rowData[0][0]);
	            	v.add(rowData[0][1]);
	            	v.add(rowData[0][2]);
	            	v.add(rowData[0][3]);
	            	v.add(rowData[0][4]);
	            	dtm.addRow(v);
	            }
	        });
	        
	        deleteItem.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	int i = table.getSelectedRow();
	            	dtm.removeRow(i);
	            }
	        });
	        
	        
	        popupMenu.add(deleteItem);
	        popupMenu.add(addItem);
	        table.setComponentPopupMenu(popupMenu);
	        scrollPane.setComponentPopupMenu(popupMenu);
	        
	        JLabel label = new JLabel("Запази таблицата в:");
	        if(os.contains("Windows")||os.contains("Linux"))
	        	label.setForeground(Color.BLUE);
	        else
	        	label.setForeground(UIManager.getColor("Button.light"));
	        label.setFont(new Font("Lucida Grande", Font.BOLD, 13));
	        label.setHorizontalAlignment(SwingConstants.CENTER);
	        label.setBounds(228, 255, 153, 16);
	        contentPane.add(label);
	        
	        textField = new JTextField();
	        textField.setBounds(149, 283, 315, 20);
	        contentPane.add(textField);
	        textField.setColumns(10);
	        textField.setEditable(false);
	        
	        fileChooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser2.setAcceptAllFileFilterUsed(false);
	        fileChooser2.setFileFilter(new FileNameExtensionFilter("Data Files","dat"));
	        
	        JButton btnNewButton = new JButton("...");
	        btnNewButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		fileChooser.showOpenDialog(contentPane);
	        		if(fileChooser.getSelectedFile()!=null)
	        			chosenDir = fileChooser.getSelectedFile().toString();
	        		if(!chosenDir.endsWith("/")&&!chosenDir.endsWith("\\")){
	        			String os = System.getProperty("os.name");
	        			if(os.contains("Windows"))
	        				chosenDir = chosenDir+"\\";
	        			else
	        				chosenDir = chosenDir+"/";
	        		}
	        		textField.setText(chosenDir);
	        	}
	        });
	        btnNewButton.setBounds(464, 283, 34, 21);
	        contentPane.add(btnNewButton);
	        
		    MenuBar menuBar=new MenuBar();
		    Menu file = new Menu("Файл");
		    MenuItem item = new MenuItem("Отвори речников файл...");
		    MenuItem item2 = new MenuItem("Запази сегашна таблица");
		    MenuItem item3 = new MenuItem("Помощ");
		    MenuItem item4 = new MenuItem("Отвори речников файл от URL...");
		    item2.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		try{
	        			saveTable();
	        			if(!chosenDir.isEmpty())
	        			JOptionPane.showMessageDialog(contentPane, 
	        			"Таблицата е запазена в папката: "+chosenDir+"\n"+
	        			"Вашата парола за файлът е: "+userKey+"\n" +
	        			"Моля ви запишете паролата някъде, поради факта, че случайни пароли лесно се забравят, а\n" +
	        			"без паролата данните във файла не могат да бъдат разчетени.");
	        			userKey = "";
	        		}catch(Exception e1){}
	        	}
	        });
		    
		    item4.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e){
		    	    int reply1 = JOptionPane.showConfirmDialog(contentPane, "Моля запазете таблицата си преди да продължите,\n" +
		    	    "зареждането на нова таблица изтрива предишната.","Важно",JOptionPane.OK_CANCEL_OPTION); 
		    	    if(reply1==2)
		    	    	return;
		    		String reply = JOptionPane.showInputDialog(contentPane, "Моля въведете директен URL към речниковия файл:", "Въведете URL", JOptionPane.PLAIN_MESSAGE);
		    		if(reply==null)
		    			return;
		    		while((!reply.startsWith("http://")||!reply.startsWith("https://"))&&!reply.endsWith(".dat"))
		    			reply = JOptionPane.showInputDialog(contentPane, "Въвели сте невалиден URL. Моля пробвайте пак: (Адреса трябва да завършва с .dat)", "Въведете URL", JOptionPane.PLAIN_MESSAGE);
		    		URL webFile = null;
		    		Scanner reader = null;
		    		try{
		    			webFile = new URL(reply);
		    		}catch(Exception e2){}
		    		try {
						reader = new Scanner(webFile.openStream());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(contentPane, "Програмата не успя да разчете файлът, моля проверете URL адреса който въведохте");
						return;
					}
		    		
		    		String password = null;
		    	    JPasswordField passwordField = new JPasswordField();
		    	    Object[] obj = {"Моля въведете паролата: (ТРЯБВА ДА Е ДЪЛГА 6 СИМВОЛА)\n", passwordField};
		    	    Object[] obj2 = {"Паролата ви НЕ е дълга 6 символа, пробвайте отново:", passwordField};
		    	    Object stringArray[] = {"OK","Cancel"};
		    	    if (JOptionPane.showOptionDialog(contentPane, obj, "Въведете Парола",
		    	    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION)
		    	    password = String.valueOf(passwordField.getPassword());
		    		if(password==null)
		    			return;
		    	    while(password.length()!=6){
		    	    	System.out.println(password.length());
			    	    if (JOptionPane.showOptionDialog(contentPane, obj2, "Въведете Парола",
					    	    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj2) == JOptionPane.YES_OPTION)
					    	    password = String.valueOf(passwordField.getPassword());
		    	    }
		    		userKey = password;
		    		
		    		try{
		    			while(true){
		    				String line = reader.nextLine();
		    				if(line.startsWith("$word: ")){
		    					String wordVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[wordVal.length];
		    				      int x=0;
		    				      for(String s: wordVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Програмата не успя да разшифрова файлът, моля проверете паролата си.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 0);
		    				}
		    				else if(line.startsWith("$gender")){
		    					String genderVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[genderVal.length];
		    				      int x=0;
		    				      for(String s: genderVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    			            	break;
		    				      }
		    				      else if(decrypt(userKey+"0000000000", cipher).equals("null"))
		    				    	  table.setValueAt("der", table.getRowCount()-1, 1);
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 1);
		    				}
		    				else if(line.startsWith("$plural")){
		    					String pluralVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[pluralVal.length];
		    				      int x=0;
		    				      for(String s: pluralVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 2);
		    				}
		    				else if(line.startsWith("$translation")){
		    					String translationVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[translationVal.length];
		    				      int x=0;
		    				      for(String s: translationVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 3);
		    				}
		    				else if(line.startsWith("$type")){
		    					String typeVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[typeVal.length];
		    				      int x=0;
		    				      for(String s: typeVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 4);
		    				}
		    				else if(line.startsWith("@break")){

		    						Vector<String> v2 = new Vector<String>();
		    		            	String rowData[][]  = {{"----","der","----","----","Съществително"}};
		    		            	v2.add("----");
		    		            	v2.add("der");
		    		            	v2.add("----"); 
		    		            	v2.add("----");
		    		            	v2.add("Съществително");
		    		            	dtm.addRow(v2);
		    						
		    				}
		    			}
		    		}catch(Exception e3){}
		    	}
		    });
		    
		    item.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		int returnVal = fileChooser2.showOpenDialog(contentPane);
		    		if(returnVal==0){
		    		chosenFile = fileChooser2.getSelectedFile();
		    	    int reply = JOptionPane.showConfirmDialog(contentPane, "Моля запазете таблицата си преди да продължите,\n" +
		    	    "зареждането на нова таблица изтрива предишната.","Важно",JOptionPane.OK_CANCEL_OPTION); 
		    	    if(reply==2)
		    	    	return;
		    	    String password = null;
		    	    JPasswordField passwordField = new JPasswordField();
		    	    Object[] obj = {"Моля въведете паролата: (ТРЯБВА ДА Е ДЪЛГА 6 СИМВОЛА)\n", passwordField};
		    	    Object[] obj2 = {"Паролата ви НЕ е дълга 6 символа, пробвайте отново:", passwordField};
		    	    Object stringArray[] = {"OK","Cancel"};
		    	    if (JOptionPane.showOptionDialog(contentPane, obj, "Въведете Парола",
		    	    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj) == JOptionPane.YES_OPTION)
		    	    password = String.valueOf(passwordField.getPassword());
		    		if(password==null)
		    			return;
		    	    while(password.length()!=6){
		    	    	System.out.println(password.length());
			    	    if (JOptionPane.showOptionDialog(contentPane, obj2, "Въведете Парола",
					    	    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, obj2) == JOptionPane.YES_OPTION)
					    	    password = String.valueOf(passwordField.getPassword());
		    	    }
		    		userKey = password;
		    		if(chosenFile.exists())
		    		try{
		    			final int count = table.getRowCount();
		    		   for(int x=count-1;x>0;x--){
		    				   dtm.removeRow(x);
		    		   }
		    		   
		    			BufferedReader bf = new BufferedReader(new FileReader(chosenFile));
		    			String line="";
		    			while((line=bf.readLine())!= null){
		    				if(line.startsWith("$word: ")){
		    					String wordVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[wordVal.length];
		    				      int x=0;
		    				      for(String s: wordVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 0);
		    				}
		    				else if(line.startsWith("$gender")){
		    					String genderVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[genderVal.length];
		    				      int x=0;
		    				      for(String s: genderVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    			            	break;
		    				      }
		    				      else if(decrypt(userKey+"0000000000", cipher).equals("null"))
		    				    	  table.setValueAt("der", table.getRowCount()-1, 1);
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 1);
		    				}
		    				else if(line.startsWith("$plural")){
		    					String pluralVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[pluralVal.length];
		    				      int x=0;
		    				      for(String s: pluralVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 2);
		    				}
		    				else if(line.startsWith("$translation")){
		    					String translationVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[translationVal.length];
		    				      int x=0;
		    				      for(String s: translationVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 3);
		    				}
		    				else if(line.startsWith("$type")){
		    					String typeVal[] = line.split(" ")[1].split(":");
		    				      byte cipher[] = new byte[typeVal.length];
		    				      int x=0;
		    				      for(String s: typeVal){
		    				    	  cipher[x]=Byte.parseByte(s);
		    				    	  x++;
		    				      }
		    				      if(decrypt(userKey+"0000000000", cipher)=="FAIL"){
		    				    	  	dtm.removeRow(0);
		    							Vector<String> v1 = new Vector<String>();
		    			            	v1.add("----");
		    			            	v1.add("der");
		    			            	v1.add("----"); 
		    			            	v1.add("----");
		    			            	v1.add("Съществително");
		    			            	dtm.addRow(v1);
		    					    	JOptionPane.showMessageDialog(new Main().contentPane, "Failed to decrypt file, please check your password.");
		    				    	  break;
		    				      }
		    				      else
		    				    	  table.setValueAt(decrypt(userKey+"0000000000", cipher), table.getRowCount()-1, 4);
		    				}
		    				else if(line.startsWith("@break")){

		    						Vector<String> v2 = new Vector<String>();
		    		            	String rowData[][]  = {{"----","der","----","----","Съществително"}};
		    		            	v2.add("----");
		    		            	v2.add("der");
		    		            	v2.add("----"); 
		    		            	v2.add("----");
		    		            	v2.add("Съществително");
		    		            	dtm.addRow(v2);
		    						
		    				}
		    			}
		    		}catch(Exception e2){}
		    	}
		    	}
		    });
		    
		    item3.addActionListener(new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		if(!help.isRunning){
		    			stopped = false;
		    			main.fadeIn(Main.help, 0.8f, 10);
		    			help.rep = true;
		    			help.isRunning=true;
		    			new Thread(main).start();
		    		}
		    	}
		    });
		    
		    file.add(item);
		    file.add(item4);
		    file.addSeparator();
		    file.add(item2);
		    file.addSeparator();
		    file.add(item3);
		    menuBar.add(file);
		    setMenuBar(menuBar); 
	}
	
	public void saveTable()throws Exception
	{
	BufferedWriter bfw = null;
	if(chosenDir.isEmpty()){
		JOptionPane.showMessageDialog(contentPane, "Моля изберете папка в която да бъде запазена таблицата.");
		return;
	}
	else
	  bfw = new BufferedWriter(new FileWriter(chosenDir+genName()));
	
	bfw.write("#DO NOT DELETE ANYTHING IN THIS FILE. YOU WILL DAMAGE THE CONTENTS.\n" +
			"#All variable values are encrypted, attempts to recover their information will likely be futile.\n\n");
	
	  StringBuilder str = new StringBuilder();
	  StringBuilder str1;
	  StringBuilder str2;
	  StringBuilder str3;
	  StringBuilder str4;
	  StringBuilder str5;
	  userKey = getRandStr(6);
	  String key = userKey+"0000000000";
	  try{
	  byte[] nullGenderCipher = encrypt(key,"null");
      for(byte b: nullGenderCipher){
    	  str.append(b+":");
      }
      str.deleteCharAt(str.length()-1);
	  }catch(Exception e){e.printStackTrace();}
	  
	  for(int i=0;i<table.getRowCount();i++){
		  str1 = new StringBuilder();
		  str2 = new StringBuilder();
		  str3 = new StringBuilder();
		  str4 = new StringBuilder();
		  str5 = new StringBuilder();
		  
		  byte[] wordCipher = encrypt(key,(String)(table.getValueAt(i, 0)));
	      for(byte b: wordCipher){
	    	  str1.append(b+":");
	      }
	      str1.deleteCharAt(str1.length()-1);
		  
		  byte[] genderCipher = new byte[1000];
		  if(dtm.getValueAt(i,4)=="Съществително")
			  genderCipher = encrypt(key,(String)(table.getValueAt(i, 1)));
	      for(byte b: genderCipher){
	    	  str2.append(b+":");
	      }
	      str2.deleteCharAt(str2.length()-1);
	      
		  byte[] pluralCipher = encrypt(key,(String)(table.getValueAt(i, 2)));
	      for(byte b: pluralCipher){
	    	  str3.append(b+":");
	      }
	      str3.deleteCharAt(str3.length()-1);
		  
		  byte[] translationCipher = encrypt(key,(String)(table.getValueAt(i, 3)));
	      for(byte b: translationCipher){
	    	  str4.append(b+":");
	      }
	      str4.deleteCharAt(str4.length()-1);
		  
		  byte[] typeCipher = encrypt(key,(String)(table.getValueAt(i, 4)));
	      for(byte b: typeCipher){
	    	  str5.append(b+":");
	      }
	      str5.deleteCharAt(str5.length()-1);
		  
		  bfw.write("$word: "+str1+"\n");
		  if(dtm.getValueAt(i,4)!="Съществително")
			  bfw.write("$gender: "+str.toString()+"\n");
		  else
			  bfw.write("$gender: "+str2.toString()+"\n");
		  bfw.write("$plural: "+str3.toString()+"\n");
		  bfw.write("$translation: "+str4.toString()+"\n");
		  bfw.write("$type: "+str5.toString());
		  if(i+1!=table.getRowCount())
		  	bfw.write("\n\n"+"@break"+"\n\n");
	  }
	  bfw.close();
	}
	String genName(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
		Date date = new Date();
		
	  	Random r = new Random();
	  	StringBuilder str = new StringBuilder();
	  	str.append("words-"+dateFormat.format(date)+"-");
	  	char c[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	  	int x;
	  	for(int y=0;y<5;y++){
	  		x= r.nextInt(62);
	  		str.append(c[x]);
	  	}
	  	str.append(".dat");
	  	return str.toString();
	}
	
	String getRandStr(int length){
	  	Random r = new Random();
	  	StringBuilder str = new StringBuilder();
	  	char c[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	  	int x;
	  	for(int y=0;y<length;y++){
	  		x= r.nextInt(62);
	  		str.append(c[x]);
	  	}
	  	return str.toString();
	}
	
	  public static byte[] encrypt(String key, String value)
		      throws GeneralSecurityException {

		    byte[] raw = key.getBytes();
		    if (raw.length != 16) {
		      throw new IllegalArgumentException("Invalid key size.");
		    }

		    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, skeySpec,
		        new IvParameterSpec(new byte[16]));
		    return cipher.doFinal(value.getBytes());
		  }

		  public static String decrypt(String key, byte[] encrypted)
		      throws GeneralSecurityException {

		    byte[] raw = key.getBytes();
		    if (raw.length != 16) {
		      throw new IllegalArgumentException("Invalid key size.");
		    }
		    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, skeySpec,
		        new IvParameterSpec(new byte[16]));
		    byte[] original = null;
		    try{
		    	original = cipher.doFinal(encrypted);
		    }catch(Exception e){
		    	return "FAIL";
		    }

		    return new String(original);
		  }
		  
			public void fadeOut(JFrame window,float currentOpacity,long sleepTime){
				for(float f=currentOpacity;f>=0.02f;f-=0.01f){
					AWTUtilities.setWindowOpacity(window, f);
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e1) {}
				}
			}
			
			public void fadeIn(JFrame window,float peakOpacity,long sleepTime){
				for(float f=0.01f;f<=peakOpacity;f+=0.01f){
					AWTUtilities.setWindowOpacity(window, f);
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {}
				}
			}
			
			@Override
			public void run() {
				try{
				InputStream in = Main.class.getResourceAsStream("music.wav");
				tempFile = File.createTempFile("tempfile", ".wav");
				tempFile.deleteOnExit();  
				FileOutputStream out = new FileOutputStream(tempFile);
			    byte[] buffer = new byte[1024];
			    int bytesRead;
			    while ((bytesRead = in.read(buffer)) != -1)
			    {
			        out.write(buffer, 0, bytesRead);
			    }
		        clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
		        AudioInputStream audioStream = AudioSystem.getAudioInputStream(tempFile);
		        clip.open(audioStream);
		        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		        gainControl.setValue(-5.0f);
		        clip.loop(0);
				}catch(Exception e){e.printStackTrace();}
			}
			
			/*Only needed in OS X,
			 * due to apple clip size restrictions.
			 */
		       void play(String filename){

		           int total, totalToRead, numBytesRead, numBytesToRead;
		           byte[] buffer;
		           AudioFormat     wav;
		           TargetDataLine  line;
		           SourceDataLine  lineIn;
		           DataLine.Info   info;
		           File            file;
		           FileInputStream fis;

		           wav = new AudioFormat(44100, 16, 2, true, false);
		           info = new DataLine.Info(SourceDataLine.class, wav);


		           buffer = new byte[1024*333];
		           numBytesToRead = 1024*333;
		           total=0;
		           stopped = false;

		           if (!AudioSystem.isLineSupported(info)) {
		               System.out.print("no support for " + wav.toString() );
		           }
		           try {
		               // Obtain and open the line.
		               lineIn = (SourceDataLine) AudioSystem.getLine(info);
		               lineIn.open(wav);
		               lineIn.start();
		               fis = new FileInputStream(file = new File(filename));
		               totalToRead = fis.available();



		               while (total < totalToRead && !stopped){
		                   numBytesRead = fis.read(buffer, 0, numBytesToRead);
		                   if (numBytesRead == -1) break;
		                   total += numBytesRead;
		                   lineIn.write(buffer, 0, numBytesRead);
		               }

		           } catch (LineUnavailableException ex) {
		               ex.printStackTrace();
		           } catch (FileNotFoundException nofile) {
		               nofile.printStackTrace();
		           } catch (IOException io) {
		               io.printStackTrace();
		           }
		   }

}
