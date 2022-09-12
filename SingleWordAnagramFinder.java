/*
Author: Christopher Weathers
Created: August 2022
Purpose: This is a non-recursive and non-permutation based anagram solver for single words.  Java implementations of
recursive methods of anagram problems are limited by stack overflow issues for longer words.  This algorithm also avoids
the need to find all permutations of a word, and instead, there are only n-1 permutations needed where n = word length.
License: Free for use, but please give algorithm credit to author.
*/

import java.io.*;
import java.util.*;

public class SingleWordAnagramFinder {

    private static final String WORDFILE = "words.txt";
    private HashMap<Character, List<String>> mapDictionary;
    private Set<String> matches;


    public static void main(String[] args){
        String sample = "angel";
        if(args.length > 0){
            sample = args[0];
        }
        SingleWordAnagramFinder swf = new SingleWordAnagramFinder();
        swf.findSingleWordAnagrams(sample.toLowerCase());
        swf.displayMatches();
    }

    public SingleWordAnagramFinder(){
        this.mapDictionary = new HashMap<Character, List<String>>();
        this.matches = new HashSet<String>();
        this.slurpMapDictionary();
    }
    
    protected void findSingleWordAnagrams(String inputWord){
        int wordLength = inputWord.length();
        findMatches(inputWord);
        int j = 1;
        for(int i = 0; i < wordLength-1; i++){
            String temp = swapPositionsInString(inputWord, 0, j);
            findMatches(temp);
            j++;
        }
    }    
    
    private void findMatches(String inputWord){
        try {
            Character c = new Character(inputWord.charAt(0));
            List<String> wordList = this.mapDictionary.get(c);
            for (String word : wordList) {
                if (inputWord.length() == word.length() && canUnscramble(inputWord, word)) {
                    this.matches.add(word);
                }
            }
        }catch(Exception e){
            System.out.println("Failed on " + inputWord + ", Exception: " + e);
            System.exit(1);
        }
    }
    
    private boolean canUnscramble(String candidateWord, String targetWord){
        List<Character> letterPool = new ArrayList<>();
        for (char ch: candidateWord.toCharArray()) {
            letterPool.add(ch);
        }
        
        for(int i = 0; i < targetWord.length(); i++){
            Character tmp = targetWord.charAt(i);
            if(! letterPool.contains(tmp)){
                return false;
            }else{
                 letterPool.remove(tmp);
            }
        }
        return true;
    }

    private static String swapPositionsInString(String a, int i, int j) {
        char[] b = a.toCharArray();  
        char ch = b[i];  
        b[i] = b[j];  
        b[j] = ch;  
        return String.valueOf(b);  
    }
    
    private void displayMatches(){ System.out.println(this.matches.toString()); }
    
    protected List<String> getMatches(){ 
        List<String> matches = new ArrayList<String>();
        for(String tmp : this.matches){
            matches.add(tmp);
        }
        
        return matches;
    }
    
    private void slurpMapDictionary() {
      try {
            File file = new File(WORDFILE);    
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr); 
    
            String line;  
            while((line = br.readLine()) != null) {  
                Character firstLetter = new Character(line.charAt(0));
                if(mapDictionary.containsKey(firstLetter)){
                    mapDictionary.get(firstLetter).add(line);
                }else{
                    List<String> wordList = new ArrayList<String>();
                    wordList.add(line);
                    mapDictionary.put(firstLetter,wordList);
                }
            }  
            fr.close();
        }catch (IOException e){
                System.out.println(e.getMessage());
        }
    }
    
    protected void clearMatches(){ this.matches.clear(); }
}
