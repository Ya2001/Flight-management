package search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Trie {

	private TrieNode rootNode = null;
	static private final char ROOT_NODE_CHAR = '*'; // A dummy char to represent the Root Node

	// This is the class that represents a node in the trie
	private class TrieNode {
		// the character contained at this node
		private char value;
		// the subtrees that stem from this node
		private TrieNode[] subnodes;
		// We need to know if this node represents the end point of a a valid word
		private boolean isValidEnd = false;

		public TrieNode(char c) {
			value = c;
			isValidEnd = false;
			subnodes = new TrieNode[26];
			// initialise each node to null. 26 nodes for 26 letters
			for (int i = 0; i < 26; i++) {
				subnodes[i] = null;
			}
		}

		public void addWord(String s) {
			// 97 is 'a' in ASCII //Where is this character based in the array
			int positionOfNextNode = ((int) s.codePointAt(0)) - 97;

			// add a new node for this value
			if (subnodes[positionOfNextNode] == null) {
				subnodes[positionOfNextNode] = new TrieNode(s.charAt(0));
			}

			// if this is the last character, and we don't need to add a node
			// then set the end point to be valid
			if (s.length() == 1) {
				subnodes[positionOfNextNode].isValidEnd = true;
			} else {
				// add the substring from 1 on to that node
				subnodes[positionOfNextNode].addWord(s.substring(1));
			}
		}

		public TrieNode deleteWord(String s) {
			// similar to deleting a linked list, we rebuild the Trie as we return.

			// this is the last char
			if (s.length() == 0) {
				isValidEnd = false;
			} else {
				// 97 is 'a' in ASCII
				int positionOfNextNode = ((int) s.codePointAt(0)) - 97;

				// we don't have the word at all
				if (subnodes[positionOfNextNode] == null) {
					return this;
				}

				// there are still more characters
				else {
					subnodes[positionOfNextNode] = subnodes[positionOfNextNode].deleteWord(s.substring(1));
				}
			}

			// As a final step. can we delete the nodes
			//
			// remember. we can only delete a node at this point if it is not a valid end
			// point and it has no subtrees
			// otherwise we need to leave it alone.
			if (!isValidEnd) {
				boolean canDeleteNode = true;
				for (int i = 0; i < subnodes.length; i++) {
					if (subnodes[i] != null)
						canDeleteNode = false;
				}

				// if we can remove this node then return false
				if (canDeleteNode)
					return null;
				else
					return this;
			}

			return this;
		}

		public boolean containsWord(String s) {
			// 97 is 'a' in ASCII
			int positionOfNextNode = ((int) s.codePointAt(0)) - 97;

			// we don't have the word
			if (subnodes[positionOfNextNode] == null) {
				return false;
			}

			// there are still more characters
			else {

				if (s.length() == 1) {
					return subnodes[positionOfNextNode].isValidEnd;
				}

				// keep going recursively on the substring
				else {
					return subnodes[positionOfNextNode].containsWord(s.substring(1));
				}
			}
		}

		// very similar to containsWord method
		public boolean areStringsWithPrefix(String s) {
			// 97 is 'a' in ASCII
			int positionOfNextNode = ((int) s.codePointAt(0)) - 97;

			// check if we don't have the word
			if (subnodes[positionOfNextNode] == null) {
				return false;
			}

			// there are still more characters
			else {
				// are we at the last character of the prefix string?
				if (s.length()== 1) {
					return true;
				}

				// we're not at the end of the prefix string
				else {
					return subnodes[positionOfNextNode].areStringsWithPrefix(s.substring(1));
				}
			}
		}

		// "b" is a prefix of "boxed"
		// "bo" is a prefix of "boxed"
		// "box" is a prefix of "boxed"
		// "ba" is not a prefix of "boxed"
		// "a" is not a prefix of "boxed"
		public ArrayList<String> wordsWithPrefix(String s) {
			// 97 is 'a' in ASCII
			int positionOfNextNode = ((int) s.toLowerCase().codePointAt(0)) - 97;
			
			String prefixString = "";
			if (value == ROOT_NODE_CHAR) {
				prefixString = "";
			} else {
				prefixString = "" + value;
			}
			
			if (subnodes[positionOfNextNode] == null) {
				return new ArrayList();
			}
			else {
				if (s.length() == 1) {
					ArrayList<String> al = new ArrayList<String>();
					for (String suffix : subnodes[positionOfNextNode].returnAllStrings()) {
						al.add(prefixString + suffix);
					}
					return al;
				}
				else {
					return subnodes[positionOfNextNode].wordsWithPrefix(s.substring(1));
				}
			}
		}
		
		public ArrayList<String> returnAllStrings() {
			ArrayList al = new ArrayList();

			// if this is the root node, then we don't want to add that character on
			String prefixString = "";
			if (value == ROOT_NODE_CHAR) {
				prefixString = "";
			} else {
				prefixString = "" + value;
			}
			if (isValidEnd) { // if this is a valid end point we need to add the char we store as a string
				al.add(prefixString);
			}

			// Find all the substrings and add them on
			for (int i = 0; i < subnodes.length; i++) {
				if (subnodes[i] != null) {
					// there be substrings
					ArrayList tempAL = subnodes[i].returnAllStrings();
					Iterator it = tempAL.iterator();
					while (it.hasNext()) {
						al.add(prefixString + it.next()); // add our prefix onto each suffix
					}
				}
			}
			return al;
		}

		// similar to returnAllStrings (but simpler)
		public int countAllStrings() {
			int numberOfStrings = 0;

			// we've found the end of a string
			if (isValidEnd) {
				numberOfStrings += 1;
			}

			// look for more strings going from A-Z through the subnodes
			for (int i = 0; i < subnodes.length; i++) {
				// if there are substrings...
				if (subnodes[i] != null) {
					int val = subnodes[i].countAllStrings();
					numberOfStrings+=val;
				}
			}
			return numberOfStrings;
		}
	} // End of TrieNode Class

	// Returns true if there are words in the trie with the given prefix string
	public boolean areWordsWithPrefix(String str) {
		if (rootNode == null) {
			return false;
		} else {
			return rootNode.areStringsWithPrefix(str);
		}
	}
	
	public ArrayList<String> wordsWithPrefix(String str) {
		if (rootNode == null) {
			return new ArrayList<>();
		}
		else {
			return rootNode.wordsWithPrefix(str);
		}
	}

	// Returns the number of words in the trie
	public int countAllWords() {
		if (rootNode == null) {
			return 0;
		} else {
			return rootNode.countAllStrings();
		}
	}

	// Prints all of the words in the Trie to the console
	public void printAllWords() {
		if (rootNode == null) {
			return;
		} else {
			ArrayList al = rootNode.returnAllStrings();

			Iterator it = al.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		}
	}

	public void insertString(String s) {
		if (rootNode == null) {
			rootNode = new TrieNode(ROOT_NODE_CHAR);
		}
		rootNode.addWord(s.toLowerCase());
	}

	public boolean containsString(String s) {
		return rootNode.containsWord(s.toLowerCase());
	}

	public void deleteString(String s) {
		rootNode.deleteWord(s.toLowerCase());
	}

}
