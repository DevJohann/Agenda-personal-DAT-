package co.edu.unbosque.view;

import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This is the main and only UI class, stores and sets up all the swing
 * elements.
 * 
 * @author Johann Toncon
 *
 */
public class MainView extends JFrame {

	private JLabel mainTitle;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton uploadBtn;
	private JButton deleteBtn;
	private JButton getBtn;
	private JButton modifyBtn;
	private JButton getAllBtn;

	/**
	 * The constructor is used to create instances of the class. Is also used to set
	 * up the JFrame properties such as size, layouts, and so on.
	 */
	public MainView() {

		mainTitle = new JLabel("Personal agenda");
		mainTitle.setBounds(150, 20, 120, 20);
		add(mainTitle);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(30, 70, 350, 300);
		add(scrollPane);

		uploadBtn = new JButton("Add");
		uploadBtn.setBounds(30, 390, 80, 22);
		uploadBtn.setFocusable(false);
		add(uploadBtn);

		deleteBtn = new JButton("Delete");
		deleteBtn.setBounds(120, 390, 80, 22);
		deleteBtn.setFocusable(false);
		add(deleteBtn);

		getBtn = new JButton("Search");
		getBtn.setBounds(210, 390, 80, 22);
		getBtn.setFocusable(false);
		add(getBtn);

		modifyBtn = new JButton("Modify");
		modifyBtn.setBounds(300, 390, 80, 22);
		modifyBtn.setFocusable(false);
		add(modifyBtn);

		getAllBtn = new JButton("See all");
		getAllBtn.setBounds(30, 30, 80, 22);
		getAllBtn.setFocusable(false);
		add(getAllBtn);

		setLayout(null);
		setResizable(false);
		setBounds(new Rectangle(420, 480));
		setLocationRelativeTo(null);
		setTitle("Your personal agenda");
	}

	/**
	 * This mehtod is used to append new info to the Text Area and then, update its
	 * graphics
	 * 
	 * @param value
	 */
	public void writeToArea(String value) {
		textArea.append(value);
		textArea.update(textArea.getGraphics());
	}

	/**
	 * This method sets the content of the Text Area to void
	 */
	public void clearArea() {
		textArea.setText("");
	}

	public JLabel getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(JLabel mainTitle) {
		this.mainTitle = mainTitle;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JButton getUploadBtn() {
		return uploadBtn;
	}

	public void setUploadBtn(JButton uploadBtn) {
		this.uploadBtn = uploadBtn;
	}

	public JButton getDeleteBtn() {
		return deleteBtn;
	}

	public void setDeleteBtn(JButton deleteBtn) {
		this.deleteBtn = deleteBtn;
	}

	public JButton getGetBtn() {
		return getBtn;
	}

	public void setGetBtn(JButton getBtn) {
		this.getBtn = getBtn;
	}

	public JButton getModifyBtn() {
		return modifyBtn;
	}

	public void setModifyBtn(JButton modifyBtn) {
		this.modifyBtn = modifyBtn;
	}

	public JButton getGetAllBtn() {
		return getAllBtn;
	}

	public void setGetAllBtn(JButton getAllBtn) {
		this.getAllBtn = getAllBtn;
	}

}
