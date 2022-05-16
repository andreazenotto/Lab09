
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private ComboBox<Country> cmbCounty;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.txtAnno.getText().isBlank()) {
    		this.txtResult.setText("Errore: devi inserire un anno");
    		return;
    	}
    	try {
    		int anno = Integer.parseInt(this.txtAnno.getText());
    		if(anno<1816 || anno>2016) {
    			this.txtResult.setText("Errore: devi inserire un anno compreso tra il 1816 ed il 2016");
    			return;
    		}
    		this.model.creaGrafo(anno);
    		this.txtResult.setText(this.model.descriviGrafo());
    	} catch(Exception e) {
    		e.printStackTrace();    		
    	}
    }

    @FXML
    void doStatiRaggiungibili(ActionEvent event) {
    	this.txtResult.clear();
    	if(this.txtAnno.getText().isBlank()) {
    		this.txtResult.setText("Errore: devi inserire un anno");
    		return;
    	}
    	try {
    		int anno = Integer.parseInt(this.txtAnno.getText());
    		if(anno<1816 || anno>2016) {
    			this.txtResult.setText("Errore: devi inserire un anno compreso tra il 1816 ed il 2016");
    			return;
    		}
    		if(this.cmbCounty.getValue()==null) {
    			this.txtResult.setText("Errore: devi scegliere un paese di partenza");
    			return;
    		}    			
    	    Set<Country> vertici = model.creaGrafo(anno);
    	    if(!vertici.contains(this.cmbCounty.getValue())) {
    	    	this.txtResult.setText("Errore: paese non esistente nel " + anno);
    			return;
    	    }
    	    String stringa = "I paesi raggiungibili da " + this.cmbCounty.getValue() + " nel " + anno + " sono:\n";
    		for(Country c: this.model.trovaCountriesRaggiungibili(this.cmbCounty.getValue())) {
    			stringa += c + "\n";
    		}
    		this.txtResult.setText(stringa);
    	} catch(Exception e) {
    		e.printStackTrace();    		
    	}
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert cmbCounty != null : "fx:id=\"cmbCounty\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbCounty.getItems().clear();
    	this.cmbCounty.getItems().setAll(this.model.getCountries());
    }
}
