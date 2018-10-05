package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza che differiscono per un solo carattere
	 */
	public List<String> getAllSimilarWords(String parola, int numeroLettere) {
		
		List<String> result = new ArrayList<>();
		List<String> wildcards = withWildcard(parola);
		
		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola " +
				"WHERE LENGTH(nome) = ? AND nome LIKE ? " +
				"AND nome != ?";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			st.setString(3, parola);
			for(String wild: wildcards) {
				st.setString(2, wild);
				ResultSet res = st.executeQuery();
				while(res.next()) {
					result.add(res.getString("nome"));
				}
				res.close();
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		return result;
	}

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int numeroLettere) {

		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		PreparedStatement st;

		try {

			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			ResultSet res = st.executeQuery();

			List<String> parole = new ArrayList<String>();

			while (res.next())
				parole.add(res.getString("nome"));
			res.close();
			conn.close();

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	/**
	 * Da stringa data crea tutte le stringhe
	 * con wild char "."
	 */
	private List<String> withWildcard(String parola) {
		StringBuilder wordWithWildCard = new StringBuilder(parola);
		List<String> wildCards = new ArrayList<>();
		for(int i = 0; i < parola.length(); i++) {
			char chr = parola.charAt(i);
			wordWithWildCard.setCharAt(i, '_');
			wildCards.add(wordWithWildCard.toString());
			wordWithWildCard.setCharAt(i, chr);			
		}
		
		return wildCards;
		
	}

}
