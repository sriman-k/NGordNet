package ngordnet.main;
import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    class HyponymOccurence implements Comparable<HyponymOccurence> {
        private String word;
        private Double occurences;

        @Override
        public int compareTo(HyponymOccurence other) {
            if (other.occurences == this.occurences) {
                return other.word.compareTo(this.word);
            }
            return (int) (other.occurences - this.occurences);
        }
        public HyponymOccurence(String word, double occurences) {
            this.word = word;
            this.occurences = occurences;
        }
    }
    private NGramMap nGramMap;
    private SynsetHyponymReader wordnet;
    public HyponymsHandler(SynsetHyponymReader wnet, NGramMap nmap) {
        wordnet = wnet;
        nGramMap = nmap;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        HashSet<String> res = new HashSet<>();
        if (words.isEmpty()) {
            return words.toString();
        } else if (words.size() ==  1 && q.k() == 0) {
            res.addAll(hyponyms(words.get(0)));
        } else if (words.size() > 1 && q.k() == 0) {
            res.addAll(listHypoynms(words));
        } else if (q.k() > 0) {
            res.addAll(kHyponyms(words, q.startYear(), q.endYear(), q.k()));
        }
        HashSet<String> rv = new HashSet<>(res);
        ArrayList<String> sorted = new ArrayList<>(rv);
        Collections.sort(sorted);
        return sorted.toString();
    }
    public void basicDfs(String id, HashSet<String> visited, HashSet<String> output) {
        HashMap<String, ArrayList<String>> adjacencyList = SynsetHyponymReader.getAdjacencyList();
        ArrayList<String> neighbors = adjacencyList.get(id);
        visited.add(id);
        output.addAll(SynsetHyponymReader.getSynsetIDWords().get(id));
        if (neighbors != null) {
            for (String neighbor: neighbors) {
                if (!visited.contains(neighbor)) {
                    basicDfs(neighbor, visited, output);
                }
            }
        }
    }
    public ArrayList<String> hyponyms(String source) {
        if (!SynsetHyponymReader.getWordtoID().containsKey(source)) {
            ArrayList<String> b = new ArrayList<>();
            return b;
        }
        HashSet<String> visited = new HashSet<>();
        HashSet<String> output = new HashSet<>();
        for (String id: SynsetHyponymReader.getWordtoID().get(source)) {
            basicDfs(id, visited, output);
        }
        HashSet<String> rv = new HashSet<>(output);
        ArrayList<String> end = new ArrayList<>(rv);
        return end;
    }

    public ArrayList<String> listHypoynms(List<String> source) {
        ArrayList<String> returnList = new ArrayList<>();
        returnList.addAll(hyponyms(source.get(0)));
        for (int i = 1; i < source.size(); i++) {
            returnList.retainAll(hyponyms(source.get(i)));
        }
        return returnList;
    }

    public ArrayList<String> kHyponyms(List<String> source, int startYear, int endYear, int k) {
        ArrayList<String> hyponyms = listHypoynms(source);
        PriorityQueue<HyponymOccurence> pq = new PriorityQueue<HyponymOccurence>();
        for (String word: hyponyms) {
            TimeSeries cH = nGramMap.countHistory(word, startYear, endYear);
            Collection<Double> values = cH.values();
            double total = 0;
            for (double value: values) {
                total += value;
            }
            if (total != 0) {
                HyponymOccurence hypOccur = new HyponymOccurence(word, total);
                pq.add(hypOccur);
            }
        }
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < k; i++) {
            if (pq.size() > 0) {
                result.add(pq.poll().word);
            }
        }
        Collections.sort(result);
        return result;
    }
}
