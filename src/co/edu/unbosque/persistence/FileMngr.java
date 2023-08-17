package co.edu.unbosque.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import co.edu.unbosque.model.Person;
import co.edu.unbosque.model.PersonDAO;

/**
 * This is the main model class, contains all the implementations of the CRUD
 * operations. The communication between the user and the file is made with
 * FileInput/OutputStrem and ObjectInput/OutputStream.
 * 
 * @author Johann Toncon
 *
 */
public class FileMngr implements PersonDAO {

	/**
	 * Looks like the persistence object, but it's not. This is used as an
	 * intermediary between user and the DAT file. When the user saves a contact, it
	 * is stored here and then is saved into the DAT file. When the user want to
	 * search a contact, it is retrieved here, and then shown to the user.
	 */
	private ArrayList<Person> data;

	/**
	 * This is the pointer to the DAT file
	 */
	private File f;

	/**
	 * The constructor will check if the file exists, if not, it creates it.
	 * 
	 * @param file
	 */
	public FileMngr(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
				this.f = file;
				this.readFile();
			} catch (IOException e) {

			}
		} else {
			this.f = file;
		}
	}

	// CRUD OPERATIONS

	/**
	 * The implementation of the Insert (CREATE) method, first, it checks that the
	 * new ID is not duplicated, if not, it opens a communication channel to the DAT
	 * file and send the data atribute with the new contact.
	 * 
	 * @param person
	 * @return Boolean
	 */
	@Override
	public boolean insert(Person person) {
		try {
			if (this.get(person.getId()) != null) { // ID is repeated //Bc it does this.get, the array is updated
				return false; // Cant add
			} else {
				// Add the new person to local arraylist
				data.add(person);

				// Write the data to DAT file
				FileOutputStream FOS = new FileOutputStream(f);
				ObjectOutputStream OOS = new ObjectOutputStream(FOS);

				OOS.writeObject(data);
				JOptionPane.showMessageDialog(null, "OK");
				OOS.close();
				FOS.close();
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * First, it retrieves the data in DAT file, then, search the target contact, if
	 * it exists, is removed from the arraylist and then is loaded up again to the
	 * DAT file.
	 */
	@Override
	public void delete(int id) {
		// Update array
		this.readFile();

		// Search target
		Person target = null;
		for (Person p : data) {
			if (p.getId() == id) {
				target = p;
				int confirm = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to delete " + p.getName() + "?");
				if (confirm == 0) {
					data.remove(p);
					JOptionPane.showMessageDialog(null, "DELETED");
					break;
				}
				break;
			}
		}
		// If not found
		if (target == null) {
			JOptionPane.showMessageDialog(null, "Contact not found");
			return;
		}

		// Reload DAT file
		this.loadFile();
	}

	/**
	 * Simply retrieves the data in DAT file, and then search the target contact, it
	 * will return it if found.
	 * 
	 * @return Person
	 */
	@Override
	public Person get(int id) {

		this.readFile(); // Update the arraylist
		Person target = null;
		for (Person p : data) { // Search in the arraylist for the target ID
			if (p.getId() == id) {
				target = p;
				break;
			}
		}
		return target; // Return either null or the found object
	}

	/**
	 * First, it retrieve the data from DAT file, then searh the target, and last,
	 * check which option the user want to change, and sets the new value.
	 * 
	 * @param id
	 * @param option
	 * @param newValue
	 */
	@Override
	public void update(int id, String option, String newValue) {

		// Update arraylist
		this.readFile();

		// Retrieve target object
		Person target = null;
		for (Person p : data) {
			if (p.getId() == id) {
				target = p;
			}
		}
		// If not found
		if (target == null) {
			JOptionPane.showMessageDialog(null, "Contact doesnt exist");
			return;
		}

		switch (option) {
		case "1":
			// Changing name
			target.setName(newValue);
			JOptionPane.showMessageDialog(null, "OK");
			break;
		case "2":
			// Changing phone number
			target.setPhoneNum(newValue);
			JOptionPane.showMessageDialog(null, "OK");
			break;
		case "3":
			// Changing email
			target.setEmail(newValue);
			JOptionPane.showMessageDialog(null, "OK");
			break;
		case "4":
			// Changing id
			target.setId(Integer.parseInt(newValue));
			JOptionPane.showMessageDialog(null, "OK");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Error: Invalid option", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		}

		// Load changes
		this.loadFile();
	}

	/**
	 * This method is used to update with the latest data the "data" attribute, so
	 * other methods can work with it.
	 */
	@Override
	public void readFile() {
		data = new ArrayList<Person>();
		if (f.length() != 0) { // DAT file has something inside
			try {
				FileInputStream FIS = new FileInputStream(f);
				ObjectInputStream OIS = new ObjectInputStream(FIS);
				data = (ArrayList<Person>) OIS.readObject();
				OIS.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // If not, the array will be empty
	}

	/**
	 * This method is used to update the data stored in the DAT file with new
	 * modifications.
	 */
	@Override
	public void loadFile() {
		try {
			FileOutputStream FOS = new FileOutputStream(f);
			ObjectOutputStream OOS = new ObjectOutputStream(FOS);
			OOS.writeObject(data);
			OOS.close();
		} catch (IOException ex) {
			System.out.println("error");
		}
	}

	public ArrayList<Person> getData() {
		this.readFile();
		return data;
	}

	public void setData(ArrayList<Person> data) {
		this.data = data;
	}

}
