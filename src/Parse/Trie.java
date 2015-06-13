/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Parse;

/**
 *
 * @author trevor.witjes
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Trie implements Serializable{
   private TrieNode root;
   
   /**
    * Constructor
    */
   public Trie() {
      root = new TrieNode();
   }
   
   /**
    * Adds a word to the Trie
    * @param word
    */
   public void addWord(String word) {
      root.addWord(word.toLowerCase());
   }
   
   /**
    * Get the words in the Trie with the given prefix
    * @param prefix
    * @return a List containing String objects containing the words in
    *         the Trie with the given prefix.
    */
   public List getWords(String prefix) {
        //Find the node which represents the last letter of the prefix
        List temp = new ArrayList();
        temp.addAll(getWordsRecursive(prefix, root));
        TrieNode lastNode = root;
        for (int i=0; i<prefix.length(); i++) {
            lastNode = lastNode.getNode(prefix.charAt(i));
      
            //If no node matches, then no words exist, return empty list
            if (lastNode == null) i = 500;   
        }
      
        if (lastNode != null) temp.addAll(lastNode.getWords());   //Return the words which eminate from the last node
        
        Set<String> hs = new HashSet<>();   // remove duplicates
        hs.addAll(temp);
        temp.clear();
        temp.addAll(hs);
        return temp;
   }
   
   private List getWordsRecursive (String prefix, TrieNode prevNode){
        
        List temp = new ArrayList();
        TrieNode lastNode;
        
        for (int j=0; j<30; j++){
            lastNode = prevNode.getNodefromIndex(j);                                // get nodes A-Z under prevNode
            if (lastNode != null){  // this doesn't work. only works if letter exists at that point 
                temp.addAll(getWordsRecursive(prefix, lastNode));                             // call getWordsRecursive for each A-Z
                for (int i=0; i<prefix.length(); i++) {                                 // go down the list, matching prefix
                    lastNode = lastNode.getNode(prefix.charAt(i));
                    if (lastNode == null) i = 500;          
                }
                if (lastNode != null){
                    temp.addAll(lastNode.getWords());
                }
            }
        }

        return temp;
   }
   
}