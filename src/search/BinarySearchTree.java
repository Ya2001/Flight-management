package search;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Locale;

public class BinarySearchTree<T extends Comparable<T>> {

	private class BSTNode { // private class to hold a tree node

		T value;
		private BSTNode leftChild; // left subtree
		private BSTNode rightChild; // right subtree

		public BSTNode(T value) {
			this.value = value;
			leftChild = null;
			rightChild = null;
		}

		public BSTNode getLeftChild() {
			return leftChild;
		}

		public BSTNode getRightChild() {
			return rightChild;
		}

		public void setLeftChild(BSTNode n) {
			leftChild = n;
		}

		public void setRightChild(BSTNode n) {
			rightChild = n;
		}

		public T getValue() {
			return value;
		}

		// inserting nodes
		public void insert(BSTNode n) {
			// insert somewhere into the left subtree
			if (this.getValue().compareTo(n.getValue()) > 0) {
				if (this.getLeftChild() == null) {
					// add node n as left child
					this.setLeftChild(n);
				} else {
					// otherwise recurse down the left subtree
					this.getLeftChild().insert(n);
				}
			}

			// insert somewhere into the right subtree
			else if (this.getValue().compareTo(n.getValue()) < 0)  {
				if (this.getRightChild() == null) {
					// add node n as right child
					this.setRightChild(n);
				} else {
					// otherwise recurse down the right subtree
					this.getRightChild().insert(n);
				}
			}

			// a duplicate which we don't allow (could also raise exception)
			else {
				return;
			}
		}

		// lookup a node
		public boolean exists(String origin, String destination, Calendar date) {
			int hashSearch = origin.hashCode() * destination.hashCode() + date.hashCode();
			
			// lookup somewhere into the left subtree
			if (hashSearch < this.getValue().hashCode()) {
				if (this.getLeftChild() == null) {
					// value v cannot be in the tree
					return false;
				} else {
					// otherwise recurse down the left subtree
					return this.getLeftChild().exists(origin, destination, date);
				}
			} else if (hashSearch > this.getValue().hashCode()) {
				if (this.getRightChild() == null) {
					// value v cannot be in the tree
					return false;
				} else {
					// otherwise recurse down the right subtree
					return this.getRightChild().exists(origin, destination, date);
				}
			}

			// otherwise this.value == v
			return true;
		}
		
		public T search(String origin, String destination, Calendar date) {
			int hashSearch = origin.hashCode() * destination.hashCode() + date.hashCode();
			
			// lookup somewhere into the left subtree
			if (hashSearch < this.getValue().hashCode()) {
				if (this.getLeftChild() == null) {
					// value v cannot be in the tree
					return null;
				} else {
					// otherwise recurse down the left subtree
					return this.getLeftChild().search(origin, destination, date);
				}
			} else if (hashSearch > this.getValue().hashCode()) {
				if (this.getRightChild() == null) {
					// value v cannot be in the tree
					return null;
				} else {
					// otherwise recurse down the right subtree
					return this.getRightChild().search(origin, destination, date);
				}
			}

			// otherwise this.value == v
			return this.getValue();
		}

		// useful for the delete method
		public BSTNode getLargestValueNode() {
			// descend down the right subtree until we get the largest value.
			// i.e. until we cannot continue to go down the right subtree
			if (this.getRightChild() == null) {
				return this;
			} else {
				return this.getRightChild().getLargestValueNode();
			}
		}

		// deleting a node with the given value
		public BSTNode delete(T v) {
			// this is not the node we want to remove
			if (!this.getValue().equals(v)) {
				// go right?
				if (this.getValue().compareTo(v) < 0 && this.getRightChild() != null) {
					// we're going to update our right child reference
					this.rightChild = this.getRightChild().delete(v);
					return this;
				}

				// go left?
				else if (this.getValue().compareTo(v) > 0 && this.getLeftChild() != null) {
					// we're going to update our left child reference
					this.leftChild = this.getLeftChild().delete(v);
					return this;
				}

				// we are trying to delete a non-existent value
				else {
					return this;
				}
			}

			// this is the node we want to remove
			else {
				// difficult case: does it have two children?
				if (this.getLeftChild() != null && this.getRightChild() != null) {

					// step 1: find the largest value in the left subtree
					BSTNode largestLeft = this.getLeftChild().getLargestValueNode();

					// step 2: set this node's value to the value of the largest left node value
					this.value = largestLeft.getValue();

					// step 3: delete the largest node on left then set Node returned as left child.
					this.leftChild = this.getLeftChild().delete(largestLeft.getValue());

					return this;
				}

				// the easy cases

				// has only left subtree
				else if (this.getLeftChild() != null) {
					// return to the parent node what this node's left child is
					return this.getLeftChild();
				}

				// has only right subtree
				else if (this.getRightChild() != null) {
					// return to the parent node what this node's right child is
					return this.getRightChild();
				}

				// has no subtrees (is a leaf node)
				else {
					// System.out.println("Deleting Node with value " + this.value);
					return null;
				}
			}
		}

		public int numberOfNodes() {
			int valueToReturn = 1; // this is 1 to count this node
			if (leftChild != null)
				valueToReturn += leftChild.numberOfNodes(); // add the nodes in the left subtree
			if (rightChild != null)
				valueToReturn += rightChild.numberOfNodes(); // add the nodes in the right subtree
			return valueToReturn; // return the number of nodes
		}
		

		
		public void inOrderTraversal(LinkedList<T> dl) {
			if (leftChild != null) {
				this.leftChild.inOrderTraversal(dl);
			}
			dl.add(this.value);
			if (rightChild != null) {
				this.rightChild.inOrderTraversal(dl);
			}
		}
	}

	private BSTNode rootNode = null;

	public void insert(T v) {
		if (rootNode == null) {
			rootNode = new BSTNode(v);
		} else {
			rootNode.insert(new BSTNode(v));
		}
	}

	public void delete(T v) {
		if (rootNode != null) {
			rootNode = rootNode.delete(v);
		}
	}

	public boolean exists(String origin, String destination, Calendar date) {
		if (rootNode != null) {
			return rootNode.exists(origin, destination, date);
		} else {
			return false;
		}
	}
	
	public T search(String origin, String destination, Calendar date) {
		if (rootNode != null) {
			return rootNode.search(origin, destination, date);
		} else {
			return null;
		}
	}

	public boolean isEmpty() {
		return (rootNode == null);
	}

	public int size() {
		if (rootNode == null) {
			return 0;
		} else {
			return rootNode.numberOfNodes();
		}
	}
	
	public LinkedList<T> inOrderTraversal() {
		LinkedList<T> dl = new LinkedList<>();
		if (rootNode != null) {
			rootNode.inOrderTraversal(dl);
			return dl;
		} else {
			return dl; // how you handle this is up to you. An exception might be a better option
		}
	}
}
