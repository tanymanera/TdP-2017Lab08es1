package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static boolean isOneDistance(String word1, String word2) {
		int distance = 1;
		if(word1.length() != word2.length()) {
			return false;
		}
		for(int i=0; i < word1.length(); i++) {
			if(word1.charAt(i) != word2.charAt(i)) {
				distance--;
			}
		}
		if(distance == 0) {
			return true;
		} else {
		return false;
		}
	}

	public static List<String> getAllSimilarWords(List<String> parole, String parola, int numeroLettere){
		List<String> paroleSimili = new ArrayList<>();
		for(String p: parole) {
			if(isOneDistance(p, parola)) {
				paroleSimili.add(p);
			}
		}
		
		return paroleSimili;
	} 
	
	public static void main(String[] args) {
		System.out.println(isOneDistance("case", "casa"));
	}
}
