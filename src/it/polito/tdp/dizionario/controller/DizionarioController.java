package it.polito.tdp.dizionario.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionario.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioController {

	private Model model;
	private List<String> paroleAdiacenti;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;
	@FXML
	private TextArea txtResult;
	@FXML
	private TextField inputNumeroLettere;
	@FXML
	private TextField inputParola;
	@FXML
	private Button btnGeneraGrafo;
	@FXML
	private Button btnTrovaVicini;
	@FXML
	private Button btnTuttiVicini;
	@FXML
	private Button btnTrovaGradoMax;

	@FXML
	void doReset(ActionEvent event) {
		inputNumeroLettere.clear();
		inputParola.clear();
		txtResult.clear();
		btnGeneraGrafo.setDisable(true);
		btnTrovaGradoMax.setDisable(true);
		btnTrovaVicini.setDisable(true);
		btnTuttiVicini.setDisable(true);
		inputParola.setDisable(true);
	}

	@FXML
	void AttivateButtons(ActionEvent event) {
		event.getEventType();
//		System.out.println(event.getEventType().getName());
		btnGeneraGrafo.setDisable(false);

	}

	@FXML
	void TrovaViciniOn(ActionEvent event) {
		event.getEventType();
		btnTrovaVicini.setDisable(false);
		btnTuttiVicini.setDisable(false);
	}

	@FXML
	void doTuttiVicini(ActionEvent event) {
		try {
			txtResult.setText(model.listTuttiVicini(inputParola.getText()).toString());
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doGeneraGrafo(ActionEvent event) {

		try {
			int numeroLettere = Integer.parseInt(inputNumeroLettere.getText());
			paroleAdiacenti = model.createGraph(numeroLettere);
			txtResult.setText(paroleAdiacenti.toString());
			btnTrovaGradoMax.setDisable(false);
			inputParola.setDisable(false);

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaGradoMax(ActionEvent event) {

		try {
			txtResult.setText(model.findMaxDegree());

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void doTrovaVicini(ActionEvent event) {

		try {
			String parolaInserita = inputParola.getText();
			List<String> paroleAdiacenti = model.displayNeighbours(parolaInserita);
			txtResult.setText(paroleAdiacenti.toString());

		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}
	}

	@FXML
	void initialize() {
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputNumeroLettere != null : "fx:id=\"inputNumeroLettere\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert inputParola != null : "fx:id=\"inputParola\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaTutti\" was not injected: check your FXML file 'Dizionario.fxml'.";
		assert btnTuttiVicini != null : "fx:id=\"btnTuttiVicini\" was not injected: check your FXML file 'Dizionario.fxml'.";
		btnGeneraGrafo.setDisable(true);
		btnTrovaGradoMax.setDisable(true);
		btnTrovaVicini.setDisable(true);
		btnTuttiVicini.setDisable(true);
		inputParola.setDisable(true);
	}

	public void setModel(Model model) {
		this.model = model;

	}
}