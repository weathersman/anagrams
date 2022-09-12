/*
Author: Christopher Weathers
Created: August 2022
Purpose: This is a simple multi-word anagram finder that makes use of the SingleWordAnagramFinder to find anagrams of 
each of the words in the white-space delimited input string.  It displays the results as a set of the anagram sets of 
each word.
License: Free for use, but please give algorithm credit to author.
*/
import java.util.*;

public class MultiWordAnagramFinder {

    private SingleWordAnagramFinder swf;

    public static void main(String[] args){
        
        String words = "angle dogs elbow";
        
        if(args.length >= 1){ 
            for(int i = 0; i < args.length; i++){
                words = args[i] + " ";
            }
        }
        
        MultiWordAnagramFinder mwf = new MultiWordAnagramFinder();
        mwf.findAnagrams(words.toLowerCase());
    }
    
    public MultiWordAnagramFinder(){
        this.swf = new SingleWordAnagramFinder();
    }

    protected void findAnagrams(String words){
        String [] wordList = words.split("\\s+");
        HashMap<String, List<String>> anagrams = new HashMap<String,List<String>>();
        
        for(int i = 0; i < wordList.length; i++){
            this.swf.findSingleWordAnagrams(wordList[i]);
            anagrams.put(wordList[i], this.swf.getMatches());
            this.swf.clearMatches();
        }
        
        System.out.println(anagrams);
        
    }
}
