package com.joelhelkala.watcherGui.frames;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

import javax.swing.*;
import javax.swing.border.Border;

import org.json.JSONObject;

import com.joelhelkala.watcherGui.Colors.Colors;
import com.joelhelkala.watcherGui.User.User;
import com.joelhelkala.watcherGui.frames.panels.LoginPanel;
import com.joelhelkala.watcherGui.frames.panels.RegisterPanel;
import com.joelhelkala.watcherGui.httpRequests.HttpRequests;

/*
 * Login page which is displayed when the application is ran
 */
public class LoginPage implements ActionListener, MouseListener {
	private JFrame frame;
	private JButton newButton;

	private JLabel messageLabel;
	private JLabel retryLabel;
	private JPanel bottomPanel;
	private JLabel imageLabel;
	private ImageIcon lightImageOff = new ImageIcon("src/main/resources/images/lightbulb_1.png");
	private ImageIcon lightImageWarming = new ImageIcon("src/main/resources/images/lightbulb_2.png");
	private ImageIcon lightImageWarm = new ImageIcon("src/main/resources/images/lightbulb_3.png");
	private ImageIcon lightImageHotter = new ImageIcon("src/main/resources/images/lightbulb_4.png");
	private ImageIcon lightImageHot = new ImageIcon("src/main/resources/images/lightbulb_5.png");
	
	private final CardLayout cl = new CardLayout();
	private JPanel stagePanel;
	
	private boolean loginSwitch = true;
	private JButton actionButton;
	
	private LoginPanel loginPanel;
	private RegisterPanel registerPanel;
	
	private final int width = 800, height = 420;
	private final int[] retry_intervals = {5, 15, 30, 60, 180};
	private int retry_count = 0;
	
	// Constructor
	public LoginPage(){
		frame = new JFrame();
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width,height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		
		newButton = new JButton("Sign Up");
		actionButton = new JButton("Login");
		
		messageLabel = new JLabel();
		retryLabel = new JLabel();
		bottomPanel = new JPanel();
		imageLabel = new JLabel();
		
		messageLabel.setBounds(width/2-50, height-29*4, 250, 35);
		messageLabel.setFont(new Font(null, Font.ITALIC, 15));
		messageLabel.setForeground(Colors.errorText);
		retryLabel.setBounds(width/2, height-29*3, 250, 35);
		retryLabel.setFont(new Font(null, Font.ITALIC, 15));
		retryLabel.setForeground(Colors.errorText);
		
		imageLabel.setBackground(Color.black);
		imageLabel.setBounds(80,30, width, height-100);
		
		newButton.setBounds(width/4-117/2, height-29*3, 117, 29);
		newButton.setBorder(new RoundedBorder(10));
		newButton.setForeground(Colors.buttonColor);
		newButton.addActionListener(this);
		newButton.addMouseListener(this);
		newButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		actionButton.setBounds(width-180, height-29*3, 117, 29);
		actionButton.setBorder(new RoundedBorder(10));
		actionButton.setForeground(Colors.buttonColor);
		actionButton.addActionListener(this);
		actionButton.addMouseListener(this);
		actionButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		frame.getRootPane().setDefaultButton(actionButton);
		
		bottomPanel.setBackground(Color.black);
		bottomPanel.setBounds(0,0,width, height);
		bottomPanel.setLayout(null);
		
		setLightBulb();
		
		bottomPanel.add(newButton);
		bottomPanel.add(imageLabel);
		bottomPanel.add(actionButton);		
		bottomPanel.add(retryLabel);
		bottomPanel.add(messageLabel);
	
		frame.add(bottomPanel);
		
		stagePanel = new JPanel();
		stagePanel.setBounds(width / 2, 60, width/2-30, height-180);
		stagePanel.setLayout(cl);
		loginPanel = new LoginPanel();
		registerPanel = new RegisterPanel();
		stagePanel.add(loginPanel, "1");
		stagePanel.add(registerPanel, "2");
		cl.show(stagePanel, "1");
		
		bottomPanel.add(stagePanel);
		frame.setVisible(true);
		CheckBackendStatus();
	}
	
	// Sets a lightbulb image on login window
	private void setLightBulb() {
		imageLabel.setIcon(resizeIcon(lightImageOff, 255, 343));
	}
	
	// Sets the lightbulb image on
	private void setLightBulbOn() {
		imageLabel.setIcon(resizeIcon(lightImageHot, 255, 343));
	}
	
	// Resize the given image to given width and height
	protected static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
	    Image img = icon.getImage();  
	    Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	}
	
	// Sends a ping to backend to check connection
	private void CheckBackendStatus() {
		try {
			if(!HttpRequests.PingRequest()) {
				messageLabel.setText("Cannot connect to server");
				LoopConnect();
			} else {
				messageLabel.setText(null);
				retryLabel.setText(null);
				setLightBulbOn();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * When button is pressed with actionlistener attached
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newButton) {
			if(loginSwitch) {
				RegisterStage();
			} else {
				LoginStage();
			}
		} else if (e.getSource() == actionButton) {
			boolean success = loginSwitch ? handleLogin() : handleRegister();
		}	
	}
	
	private void RegisterStage() {
		cl.show(stagePanel, "2");
		newButton.setText("Sign In");
		actionButton.setText("Register");
		loginSwitch = !loginSwitch;
	}
	
	private void LoginStage() {
		cl.show(stagePanel, "1");
		newButton.setText("Sign Up");
		actionButton.setText("Login");
		loginSwitch = !loginSwitch;
	}
	
	/*
	 * Sends login information to the server
	 * if the credentials are valid, save the user information and token
	 * and open welcomepage
	 */
	private boolean handleLogin() {
		Map<String, String> credentials = loginPanel.getCredentials();
		if(credentials.get("email").equals("") || credentials.get("password").equals("")) return false;
		
		boolean valid = HttpRequests.LoginRequest(credentials.get("email"), credentials.get("password"));
		if (valid) {
			frame.dispose();
			new WelcomePage();
		} else {						
			messageLabel.setText("Invalid username or password");
			loginPanel.ClearFields();
		}
		return valid;
	}
	
	/*
	 * Sends the registration info to the server
	 */
	private boolean handleRegister() {
		if (registerPanel.validateForm()) {
			Map<String, String> userInfo = registerPanel.getUserInfo();
			JSONObject response = HttpRequests.RegisterUser(userInfo.get("name"), userInfo.get("email"), 
					userInfo.get("password"));
			if(response != null) {
				String message = response.getString("message");
				String title = "User registration";
				int icon = JOptionPane.INFORMATION_MESSAGE;
				if(response.getInt("status") < 300) {
					JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
					LoginStage();					
				} else JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
			} else JOptionPane.showMessageDialog(frame, "Something went wrong", "ERROR!", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	/*
	 * Tries to connect to backend in a loop
	 * with an increasing timer
	 */
	private void LoopConnect() {
		final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
        	int i = retry_intervals[retry_count];

            public void run() {
                retryLabel.setText("Retry in: " + i);
                i--;

                if (i < 0) {
                    timer.cancel();
                    if(retry_count < retry_intervals.length-1) retry_count++;
                    CheckBackendStatus();
                }
            }
        }, 0, 1000);
	}
	
	// Make a button have rounded edges 
	// https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
	private static class RoundedBorder implements Border {
	    private int radius;

	    RoundedBorder(int radius) {
	        this.radius = radius;
	    }

	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
	    }

	    public boolean isBorderOpaque() {
	        return true;
	    }

	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	    }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Object target = e.getSource();
		((JComponent) target).setForeground(Color.yellow);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Object target = e.getSource();
		((JComponent) target).setForeground(Color.orange);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

