package co.edu.unbosque.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;

import co.edu.unbosque.model.Person;
import co.edu.unbosque.persistence.FileMngr;
import co.edu.unbosque.view.MainView;

/**
 * The class MainController is used as the main brain of the application,
 * manages button triggers and calls their actions thanks to the ActionListener
 * interface.
 * 
 * @author Johann Toncon
 *
 */
public class MainController implements ActionListener {

	/**
	 * A reference to the MainView class, so it can be deployed.
	 */
	private MainView view;

	/**
	 * A reference to the File Manager class, so it can be deployed, and use the
	 * CRUD operations.
	 */
	private FileMngr fileAdmin;

	public MainController() {
		execute();
		setListeners();
	}

	/**
	 * A method used to instantiate the outer classes.
	 */
	public void execute() {
		view = new MainView();
		fileAdmin = new FileMngr(new File("src/data/database.dat"));
		view.setVisible(true);
	}

	/**
	 * Retrieves the buttons from view class, and sets up them ActionCommands and
	 * ActionListeners
	 */
	public void setListeners() {
		view.getUploadBtn().setActionCommand("insert");
		view.getUploadBtn().addActionListener(this);

		view.getDeleteBtn().setActionCommand("delete");
		view.getDeleteBtn().addActionListener(this);

		view.getGetBtn().setActionCommand("get");
		view.getGetBtn().addActionListener(this);

		view.getModifyBtn().setActionCommand("modify");
		view.getModifyBtn().addActionListener(this);

		view.getGetAllBtn().setActionCommand("get all");
		view.getGetAllBtn().addActionListener(this);
	}

	/**
	 * Implemented method of ActionListener, here the application manages the button
	 * actions
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "insert":
			String name = JOptionPane.showInputDialog("Enter your contact's name");
			if (name == null) {
				JOptionPane.showMessageDialog(null, "Error: You have to give a value", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!name.matches("[a-zA-Z]+")) {
				JOptionPane.showMessageDialog(null, "Error: Interesting name... Enter only letters", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String phoneNum = JOptionPane.showInputDialog("Enter his phone number");
			if (phoneNum == null) {
				JOptionPane.showMessageDialog(null, "Error: You have to give a value", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!phoneNum.matches("^[0-9]+$")) {
				JOptionPane.showMessageDialog(null, "Error: The phone NUMBER should be a number", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String email = JOptionPane.showInputDialog("Enter his email");
			if (email == null) {
				JOptionPane.showMessageDialog(null, "Error: You have to give a value", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
				JOptionPane.showMessageDialog(null, "Error: Invalid mail format", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id for this contact (Unique)"));
				boolean res = fileAdmin.insert(new Person(name, phoneNum, email, id));
				if (res == false) {
					JOptionPane.showMessageDialog(null, "Error: The ID must be unique, that one already exists",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Error: The ID must be a number", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "delete":
			try {
				int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the target ID to delete"));
				fileAdmin.delete(id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Error: The ID must be a number", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "get":
			try {
				int targetId = Integer.parseInt(JOptionPane.showInputDialog("Enter the id to search"));
				Person target = fileAdmin.get(targetId);
				view.clearArea();
				view.writeToArea("Name: " + target.getName() + "\nPhone: " + target.getPhoneNum() + "\nEmail: "
						+ target.getEmail() + "\nID: " + target.getId() + "\n");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Error: The ID must be a number", "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "The contact doesnt exist", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "modify":
			try {
				int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the target ID to modify"));

				String option = JOptionPane
						.showInputDialog("Select an attribute to modify\n1. Name\n2. Phone\n3. Email\n4. ID");
				if (option == null) {
					JOptionPane.showMessageDialog(null, "Error: You must give a value", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!option.matches("[0-9]")) {
					JOptionPane.showMessageDialog(null, "Error: Use only one number to select", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				String newValue = JOptionPane.showInputDialog("Enter the new value");
				if (newValue == null) {
					JOptionPane.showMessageDialog(null, "Error: You must give a value", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Checking formats
				switch (option) {
				case "1":
					if (!newValue.matches("[a-zA-Z]+")) {
						JOptionPane.showMessageDialog(null, "Error: Interesting name... Enter only letters", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						fileAdmin.update(id, option, newValue);
					}
					break;
				case "2":
					if (!newValue.matches("[0-9]+")) {
						JOptionPane.showMessageDialog(null, "Error: The phone NUMBER should be a number", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						fileAdmin.update(id, option, newValue);
					}
					break;
				case "3":
					if (!newValue.matches("^[\\\\w-\\\\.]+@([\\\\w-]+\\\\.)+[\\\\w-]{2,4}$")) {
						JOptionPane.showMessageDialog(null, "Error: Invalid mail format", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						fileAdmin.update(id, option, newValue);
					}
					break;
				case "4":
					try {
						if (!newValue.matches("[0-9]+")) {
							JOptionPane.showMessageDialog(null, "Error: ID must be a number", "Error",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						Person res = fileAdmin.get(Integer.parseInt(newValue));
						if (res != null) {
							JOptionPane.showMessageDialog(null, "Error: The ID must be unique, that one already exists",
									"Error", JOptionPane.ERROR_MESSAGE);
						} else {
							fileAdmin.update(id, option, newValue);
						}

					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Error: The ID must be a number", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					break;
				default:
					JOptionPane.showMessageDialog(null, "Error: No such option", "Error", JOptionPane.ERROR_MESSAGE);

					break;
				}
			} catch (NumberFormatException ex) {

			}
			break;
		case "get all":
			view.clearArea();
			for (Person p : fileAdmin.getData()) {
				view.writeToArea("Name: " + p.getName() + "\nPhone: " + p.getPhoneNum() + "\nEmail: " + p.getEmail()
						+ "\nID: " + p.getId() + "\n");
			}
			break;
		default:
			System.out.println("Error 1");
			break;
		}
	}

	public MainView getView() {
		return view;
	}

	public void setView(MainView view) {
		this.view = view;
	}

	public FileMngr getFileAdmin() {
		return fileAdmin;
	}

	public void setFileAdmin(FileMngr fileAdmin) {
		this.fileAdmin = fileAdmin;
	}

}
