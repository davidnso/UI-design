package application;

public class storedData {
	static String FirstName;
	static String LastName;
	public void setFirst(String n) {
		FirstName = n; 
	}
	void setLast(String p) {
		LastName = p; 
	}
	public String getFirst() {
		
		return FirstName;
	}
	public String getLast() {
		return LastName;
	}

}
