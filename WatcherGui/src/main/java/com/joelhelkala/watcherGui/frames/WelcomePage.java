package com.joelhelkala.watcherGui.frames;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.joelhelkala.watcherGui.Nodes.Nodes;
import com.joelhelkala.watcherGui.Nodes.Node.Node;
import com.joelhelkala.watcherGui.User.User;
import com.joelhelkala.watcherGui.User.Roles.Role;
import com.joelhelkala.watcherGui.frames.dialogs.Dialog;
import com.joelhelkala.watcherGui.frames.subframes.AdminFrame;
import com.joelhelkala.watcherGui.frames.subframes.Histograms;
import com.joelhelkala.watcherGui.frames.subframes.NodeDataFrame;
import com.joelhelkala.watcherGui.frames.subframes.NodeSettingsFrame;
import com.joelhelkala.watcherGui.frames.subframes.SettingsFrame;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;

/*
 * Main page when the user logs in
 */
public class WelcomePage implements MouseListener, ActionListener {
		
	private static final int width = 1200, height = 750;
	private static final int topPanelHeight = height/10;
	private static final int leftPanelWidth = width/5;
	private static final int bottomPanelHeight = height/11;
	
	private static final int pollInterval = 30; // Poll the backend every interval in seconds
	
	private static final Color gray = new Color(45, 45, 45);
	private static final Color lightgray = new Color(73, 73, 73);
	private static final Color darkwhite = new Color(96, 96, 96);
	private static final Color dark = new Color(33, 33, 33);
	
	private final CardLayout cl = new CardLayout();
	private static JComboBox comboBox = new JComboBox();
	
	private JPanel stagePanel;
	private final JLabel nodeDataLabel 		= new JLabel("Node data");
	private final JLabel nodeSettingsLabel 	= new JLabel("Node settings");
	private final JLabel friendsLabel 		= new JLabel("Histograms");
	private final JLabel adminLabel 		= new JLabel("Admin");
	private final JLabel helpLabel 			= new JLabel("Help");
	private final JLabel settingsLabel 		= new JLabel("Settings");
	private final JLabel logoutLabel 		= new JLabel("Logout");
	private static JFrame frame;
	
	private static JLabel chosen_label;
	private static Timer timer;
	
	private static NodeDataFrame nodeDataFrame = new NodeDataFrame(leftPanelWidth, topPanelHeight, width-leftPanelWidth, height-topPanelHeight-bottomPanelHeight);
	private static NodeSettingsFrame nodeSettingFrame = new NodeSettingsFrame(width-leftPanelWidth, height-topPanelHeight-bottomPanelHeight);
	private static Histograms histogramFrame = new Histograms();
	
	public WelcomePage(){		
		frame = new JFrame();
		frame.getContentPane().setBackground( Color.MAGENTA );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		
		// TOP PANEL
		JPanel topPanel = new JPanel();
		topPanel.setBackground(dark);
		topPanel.setBounds(0,0,width, topPanelHeight);
		topPanel.setLayout(null);
		frame.getContentPane().add(topPanel);
		
		updateComboBox();
		
		comboBox.addActionListener(this);
		comboBox.setBounds(500, 10, 200, 50);
		topPanel.add(comboBox);
		
		JLabel userLabel = new JLabel("Hello, " + User.getName());
		userLabel.setBounds(1000, 32, 100, 16);
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		userLabel.setForeground(Color.WHITE);
		topPanel.add(userLabel);
		JLabel iconLabel = new JLabel();
		iconLabel.setBounds(20,10,50,50);
		ImageIcon image = new ImageIcon("src/main/resources/images/lightbulb_5.png");
		iconLabel.setIcon(LoginPage.resizeIcon(image, 50, 50));
		topPanel.add(iconLabel);
		JLabel titleLabel = new JLabel("WATCHER");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 21));
		titleLabel.setForeground(Color.yellow);
		titleLabel.setBounds(99, 12, 100, 50);
		topPanel.add(titleLabel);
		
		// LEFT PANEL
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(dark);
		leftPanel.setBounds(0, topPanelHeight, leftPanelWidth, height);
		
		frame.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		nodeDataLabel.setOpaque(true);
		nodeDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nodeDataLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 16));
		nodeDataLabel.setBounds(0, 33, 240, 53);
		nodeDataLabel.setForeground(Color.WHITE);
		nodeDataLabel.addMouseListener(this);
		nodeDataLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		// Set as the chosen section
		nodeDataLabel.setBackground(gray);
		chosen_label = nodeDataLabel;
		leftPanel.add(nodeDataLabel);
		
		nodeSettingsLabel.setOpaque(true);
		nodeSettingsLabel.setBackground(dark);
		nodeSettingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nodeSettingsLabel.setForeground(Color.WHITE);
		nodeSettingsLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 16));
		nodeSettingsLabel.setBounds(0, 86, 240, 53);
		nodeSettingsLabel.addMouseListener(this);
		nodeSettingsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leftPanel.add(nodeSettingsLabel);
		
		friendsLabel.setOpaque(true);
		friendsLabel.setBackground(dark);
		friendsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		friendsLabel.setForeground(Color.WHITE);
		friendsLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 16));
		friendsLabel.setBounds(0, 139, 240, 53);
		friendsLabel.addMouseListener(this);
		friendsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leftPanel.add(friendsLabel);
		
		helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		helpLabel.setForeground(Color.WHITE);
		helpLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 16));
		helpLabel.setBounds(20, 615, 98, 16);
		helpLabel.addMouseListener(this);
		helpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leftPanel.add(helpLabel);
		
		settingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		settingsLabel.setForeground(Color.WHITE);
		settingsLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 16));
		settingsLabel.setBounds(123, 615, 98, 16);
		settingsLabel.addMouseListener(this);
		settingsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		leftPanel.add(settingsLabel);
		
		// BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(gray);
		bottomPanel.setBounds(leftPanelWidth, height-height/11, width, height/11);

		logoutLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		logoutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		logoutLabel.setBounds(884, 6, 48, 20);
		logoutLabel.setForeground(Color.WHITE);
		logoutLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 16));
		logoutLabel.addMouseListener(this);
		bottomPanel.setLayout(null);
		logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bottomPanel.add(logoutLabel);
		frame.getContentPane().add(bottomPanel);
		
		// MAIN PANEL
		// Add frames to a Cardlayout panel which can be used to swap between panels
		// This is used for navigationbar on the left
		stagePanel = new JPanel();
		stagePanel.setBounds(leftPanelWidth, topPanelHeight, width-leftPanelWidth, height-topPanelHeight-bottomPanelHeight);
		stagePanel.setBackground(Color.red);
		
		//JPanel friendsFrame = new FriendsFrame(leftPanelWidth, topPanelHeight, width-leftPanelWidth, height-topPanelHeight-bottomPanelHeight);
		JPanel settingsFrame = new SettingsFrame();

		stagePanel.setLayout(cl);
		stagePanel.add(nodeDataFrame, "data");
		stagePanel.add(nodeSettingFrame, "setting");
		stagePanel.add(histogramFrame, "friends");
		stagePanel.add(settingsFrame, "settings");
		
		if(User.getRole() == Role.ADMIN) {
			AddAdminTab(leftPanel);
			stagePanel.add(new AdminFrame(), "admin");
		}
		cl.show(stagePanel, "data");
		frame.add(stagePanel);
		
		frame.setVisible(true);
		PollData();
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	// Add a admin navigationtab to given panel
	private void AddAdminTab(JPanel panel) {
		adminLabel.setOpaque(true);
		adminLabel.setBackground(dark);
		adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
		adminLabel.setForeground(Color.WHITE);
		adminLabel.setFont(new Font("Apple SD Gothic Neo", Font.PLAIN, 16));
		adminLabel.setBounds(0, 192, 240, 53);
		adminLabel.addMouseListener(this);
		adminLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		panel.add(adminLabel);
	}
	
	/*
	 * Polls the backend for new data every given interval
	 */
	private static void PollData() {
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        	int timeRemaining = pollInterval;
            public void run() {
            	timeRemaining--;

                if (timeRemaining < 0) {
                    timer.cancel();
                	updateComboBoxAndPoll((String)comboBox.getSelectedItem());
                }
            }
        }, 0, 1000);
	}

	/*
	 * If mouse is clicked on top of left navigationbar items, 
	 * then swap the scene. If clicked is already active, then do nothing.
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == nodeSettingsLabel && arg0.getSource() != chosen_label) {
			cl.show(stagePanel, "setting");
			setActiveScene(nodeSettingsLabel);
		} else if(arg0.getSource() == nodeDataLabel && arg0.getSource() != chosen_label) {
			cl.show(stagePanel, "data");
			setActiveScene(nodeDataLabel);
		} else if(arg0.getSource() == friendsLabel && arg0.getSource() != chosen_label) {
			cl.show(stagePanel, "friends");
			setActiveScene(friendsLabel);
		} else if(arg0.getSource() == helpLabel) {
			Dialog.showHelpDialog();
		} else if(arg0.getSource() == settingsLabel) {
			cl.show(stagePanel, "settings");
			setActiveScene(settingsLabel);
		} else if(arg0.getSource() == adminLabel) {
			cl.show(stagePanel, "admin");
			setActiveScene(adminLabel);
		}
	}
	
	
	/*
	 * Sets a navbar item as chosen and changes its colors accordingly
	 */
	private void setActiveScene(JLabel chosen) {
		chosen_label.setBackground(dark);
		
		chosen.setBackground(gray);
		chosen_label = chosen;
	}
	
	// Changes the main scene to given scene
	// This is prompted when left navi bar items are clicked
	private void changeScene(JPanel sceneholder, JPanel new_scene) {
		sceneholder.removeAll();
		sceneholder = new_scene;
		frame.getContentPane().add(sceneholder);
		frame.validate();
		frame.repaint();
		frame.setVisible(true);
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		Object target = arg0.getSource();
		((JComponent) target).setBackground(darkwhite);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		Object target = arg0.getSource();
		if(target == chosen_label) {
			((JComponent) target).setBackground(gray);
			return;
		}
		((JComponent) target).setBackground(dark);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if(arg0.getSource() == logoutLabel) {
			handleLogout();
		}
		
	}
	
	// Logs the user out and clears user data
	// Closes current window and opens loginpage
	private void handleLogout() {
		int result = Dialog.yesNoDialog("Are you sure you want to logout", "LOGOUT");
		
		if(result == JOptionPane.YES_OPTION) {
			User.clear();
			timer.cancel();
			frame.dispose();
			LoginPage login = new LoginPage();
		}
	}
	
	/*
	 * Opens a dialog that asks if the user wants to save
	 */
	public static boolean handleSave() {
		String message = "Are you sure you want to save?";
		String title = "SAVE?";
		int result = Dialog.yesNoDialog(message, title);
		
		return result == JOptionPane.YES_OPTION;
	}
	
	/*
	 * Request updated nodes from the server and 
	 * update combobox with these
	 */
	public static void updateComboBox() {
		Nodes.updateNodes();
		List<String> node_names = new ArrayList<String>();
		
		for(Node n : Nodes.getAll()) {
			node_names.add(n.getLocation());
		}
		
		comboBox.setModel( new DefaultComboBoxModel(node_names.toArray()) );
		updateDataByName((String)comboBox.getSelectedItem());
	}
	
	private static void updateComboBoxAndPoll(String comboboxName) {
		Nodes.updateNodes();
		List<String> node_names = new ArrayList<String>();
		
		for(Node n : Nodes.getAll()) {
			node_names.add(n.getLocation());
		}
		
		comboBox.setModel( new DefaultComboBoxModel(node_names.toArray()) );
		comboBox.setSelectedItem(comboboxName);
		updateDataByName((String)comboBox.getSelectedItem());
		PollData();
	}
	
	/*
	 * Request updated data from node with given name
	 * update data tables with nodes values
	 */
	private static void updateDataByName(String node_name) {
		Node node = Nodes.findByLocation(node_name);
		
		if(node == null) return;
		
		node.updateData(HttpRequests.getNodeData(node.getId()));
		nodeDataFrame.updateData(node);
		nodeSettingFrame.updateInformation(node);
		histogramFrame.updateData(node);
	}
	
	/*
	 * This is prompted when user selects a item from node combobox
	 * Find the node object based on name and then fetch the data for that node from the server
	 * This data will be displayed on the data page
	 */
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		String node_name = ((JComboBox)e.getSource()).getSelectedItem().toString();
		updateDataByName(node_name);
	}

	public static void TimedOutSession() {
		String message = "\"The session has timed out.\"\n"
		        + "You will be logged out...";
		String title = "TIMEOUT!";
		JOptionPane.showConfirmDialog(frame, message, title,
	               JOptionPane.ERROR_MESSAGE);
		User.clear();
		timer.cancel();
		frame.dispose();
		LoginPage login = new LoginPage();		
	}
}