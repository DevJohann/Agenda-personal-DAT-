package co.edu.unbosque.model;

/**
 * This interface contains all the CRUD operations and some other utilities used
 * when working with the file.
 * 
 * @author Johann Toncon
 *
 */
public interface PersonDAO {

	public boolean insert(Person person);

	public void delete(int id);

	public Person get(int id);

	public void update(int id, String option, String newValue);

	public void readFile();

	public void loadFile();
}
