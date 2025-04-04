package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;

public class SynsetHyponymReader {
    private static HashMap<String, ArrayList<String>> SynsetIDtoWords = new HashMap<>();
    private static HashMap<String, ArrayList<String>> adjacencyList = new HashMap<>();
    private static HashMap<String, ArrayList<String>> WordtoID = new HashMap<>();
    public static HashMap<String, ArrayList<String>> getWordtoID() {return WordtoID;}

    public static HashMap<String, ArrayList<String>> getAdjacencyList() {return adjacencyList;}

    public static HashMap<String, ArrayList<String>> getSynsetIDWords() {return SynsetIDtoWords;}

    public SynsetHyponymReader(String synsetFileName, String hyponymFileName) {
        In synsets = new In(synsetFileName);
        In hyponyms = new In(hyponymFileName);
        while (!synsets.isEmpty()) {
            // SYNSET ID TO WORDS
            String rows = synsets.readLine();
            String[] commaSplit = rows.split(",");
            String synsetID = commaSplit[0];
            String[] word = commaSplit[1].split("\\s");
            SynsetIDtoWords.put(synsetID, new ArrayList<String>());
            for (int i = 0; i < word.length; i++) {
                SynsetIDtoWords.get(synsetID).add(word[i]);
            }
            /*WORD ID*/
            ArrayList<String> wordValues = new ArrayList<>();
            for (int i = 0; i < word.length; i++) {
                wordValues.add(word[i]);
            }
            for (int i = 0; i < wordValues.size(); i++) {
                if (!WordtoID.containsKey(wordValues.get(i))) {
                    WordtoID.put(wordValues.get(i), new ArrayList<String>());
                }
                WordtoID.get(wordValues.get(i)).add(synsetID);
            }
        }
        while (!hyponyms.isEmpty()) {
            String rows = hyponyms.readLine();
            String[] hypContent = rows.split(",");
            String parent = hypContent[0];
            for (int i = 0; i < hypContent.length; i++) {
                if (!adjacencyList.containsKey(hypContent[i])) {
                    adjacencyList.put(hypContent[i], new ArrayList<>());
                }
            }
            for (int i = 1; i < hypContent.length; i++) {
                adjacencyList.get(parent).add(hypContent[i]);
            }
        }
    }
}

//want to see if key(parent) already exists in Adjacency List, then values
//adjacency.get(parent), then append to that

//inside for loop, check if hypContent[i] already exists in adjacency list, if it's not there
// map to an AJList.put(hypoContent[i], new ArrayList)
