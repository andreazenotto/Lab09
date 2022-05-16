package it.polito.tdp.borders.model;

public class Country {
	
	private String stateAbb;
	private int ccode;
	private String stateNme;
	
	public Country(String stateAbb, int ccode, String stateNme) {
		super();
		this.stateAbb = stateAbb;
		this.ccode = ccode;
		this.stateNme = stateNme;
	}
	
	public String getStateAbb() {
		return stateAbb;
	}
	public void setStateAbb(String stateAbb) {
		this.stateAbb = stateAbb;
	}
	public int getCCode() {
		return ccode;
	}
	public void setCCode(int cCode) {
		ccode = cCode;
	}
	public String getStateNme() {
		return stateNme;
	}
	public void setStateNme(String stateNme) {
		this.stateNme = stateNme;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ccode;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (ccode != other.ccode)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.stateNme;
	}
	

}
