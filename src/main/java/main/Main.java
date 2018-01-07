package main;

import security.PasswordEncrypter;
import dao.UserDAO;

import javax.swing.*;
import javax.transaction.SystemException;

import java.awt.*;

public class Main {

	public static void main(String[] args) throws IllegalStateException, SystemException {

		UserDAO userDao = new UserDAO();

		JFrame frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(350, 350);
		frame.setTitle("Fast Food");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(null, true);
		panel.setLayout(null);
		panel.setSize(300, 300);
		panel.setLocation(50, 50);

		JLabel usernameLabel = new JLabel("Please enter your username");
		usernameLabel.setSize(170, 20);
		usernameLabel.setLocation(20, 20);
		panel.add(usernameLabel);

		JTextField usernameTextField = new JTextField();
		usernameTextField.setSize(170, 20);
		usernameTextField.setLocation(20, 45);
		panel.add(usernameTextField);

		JLabel passwordLabel = new JLabel("Please enter your password");
		passwordLabel.setSize(170, 20);
		passwordLabel.setLocation(20, 80);
		panel.add(passwordLabel);

		JPasswordField passwordTextField = new JPasswordField();
		passwordTextField.setSize(170, 20);
		passwordTextField.setLocation(20, 105);
		panel.add(passwordTextField);

		JButton registerButton = new JButton("Register");
		registerButton.setSize(100, 20);
		registerButton.setLocation(20, 130);
		panel.add(registerButton);

		JButton loginButton = new JButton("Login");
		loginButton.setSize(80, 20);
		loginButton.setLocation(110, 130);
		panel.add(loginButton);

		JLabel successLabel = new JLabel("Action Succeeded!");
		successLabel.setForeground(Color.green);
		successLabel.setSize(170, 20);
		successLabel.setLocation(20, 0);
		successLabel.setVisible(false);
		panel.add(successLabel);

		JLabel errorsLabel = new JLabel("Action Failed! Make sure the input data is valid.");
		errorsLabel.setForeground(Color.red);
		errorsLabel.setSize(170, 20);
		errorsLabel.setLocation(20, 0);
		errorsLabel.setVisible(false);
		panel.add(errorsLabel);

		loginButton.addActionListener(e -> {

			String username = usernameTextField.getText();
			String password = String.valueOf(passwordTextField.getPassword());
			String dbPassword = PasswordEncrypter.encryptPassword(username, password);

			try {
				if (userDao.validate(username, dbPassword)) {
					successLabel.setVisible(true);
					errorsLabel.setVisible(false);
					// New frame to pop instead of the above instructions

				} else {
					errorsLabel.setVisible(true);
					successLabel.setVisible(false);

				}
			} catch (IllegalStateException e1) {
				System.out.println("illegal state expression on login buttn");
				e1.printStackTrace();
				
			}
		});

		registerButton.addActionListener(e -> {
			String username = usernameTextField.getText();
			String password = String.valueOf(passwordTextField.getPassword());
			String dbPassword = PasswordEncrypter.encryptPassword(username, password);

			try {
				if (userDao.hasUser(username)) {
					errorsLabel.setVisible(true);
					successLabel.setVisible(false);

				} else {
					try {
						userDao.addUser(username, dbPassword);
					} catch (IllegalStateException e1) {
						System.out.println("illegal state exception on regbutton");
						e1.printStackTrace();
					} catch (SystemException e1) {
						System.out.println("system exception on regbutton");
						e1.printStackTrace();
					}
					successLabel.setVisible(true);
					errorsLabel.setVisible(false);

				}
			} catch (IllegalStateException e1) {
				System.out.println("illegal state exception on regbutton");
				e1.printStackTrace();
			} catch (SystemException e1) {
				System.out.println("system exception on regbutton");
				e1.printStackTrace();
			}

		});

		frame.add(panel);
		frame.setVisible(true);

	}

}