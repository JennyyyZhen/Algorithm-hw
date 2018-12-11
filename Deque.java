//package coursera;

import java.util.Iterator;
import java.util.LinkedList;

public class Deque<Item> implements Iterable<Item> {
	   private Node<Item> head;
	   private Node<Item> tail;
	   private int size;
	   private class Node<Item>{
		   Item val;
		   Node<Item> prev;
		   Node<Item> next;
		   Node(Node<Item> prev,Node<Item> next,Item val){
			this.val=val;
			this.prev=prev;
			this.next=next;
		   }  
	   }
	
	   public Deque() {
		   this.head=null;
		   this.tail=null;
		   this.size=0;
	   }                           // construct an empty deque
	
	   public boolean isEmpty() {
		   return this.size==0; // is the deque empty?
	   }                
	
	   public int size() {
		   return this.size;  // return the number of items on the deque
	   }                       
	
	   public void addFirst(Item item) {
		   if(item==null) {
			throw new java.lang.IllegalArgumentException();
		   }
		   // add the item to the front
		   if(size==0) {
			Node<Item> n= new Node<>(null,null,item);
			head=n;
			tail=n;
		   }
		   else {
			 Node<Item> n= new Node<>(null,head,item); 
			 head.prev=n;
			 head=n;
		   }
			 size++;
	   }         
	
	   public void addLast(Item item) {
		   if(item==null) {
			 throw new java.lang.IllegalArgumentException();
		   }
		   if(size==0) {// add the item to the end
			 Node<Item> n= new Node<>(null,null,item);
			 head=n;
			 tail=n;
		   }
		   else {
			 Node<Item> n= new Node<>(tail,null,item);
			 tail.next=n;
			 tail=n;
		   }
		   size++;
	   }        
	
	   public Item removeFirst() {
		   if(this.isEmpty()) {
			   throw new java.util.NoSuchElementException();
		   } 
		   Node<Item> temp=head;
		   if(size==1) {
			   head=null;
			   tail=null;
		   }
		   else {
		   head= head.next;
		   head.prev=null;
		   }
		   size--;
		   return temp.val;   
	   }                // remove and return the item from the front
	
	   public Item removeLast() {
		   if(this.isEmpty()) {
			   throw new java.util.NoSuchElementException();
		   }
		   Node<Item> temp=tail;
		   if(size==1) {
			   head=null;
			   tail=null;
		   }
		   else {
		   tail=tail.prev;
		   tail.next=null;
		   }
		   size--;
		   return  temp.val;
	   }                 // remove and return the item from the end
	
	   public Iterator<Item> iterator(){
		   return new Iterator<Item>() {
			 Node<Item> curr= head;
			@Override
			public boolean hasNext() {
				if(curr==null) return false;
				else return true;
			}

			@Override
			public Item next() {
				if(!this.hasNext())
					throw new java.util.NoSuchElementException();
				Node<Item> temp=curr;
				curr=curr.next;
				return temp.val;
			}
			public void remove() {
				throw new java.lang.UnsupportedOperationException();
			}
			   
		   };// return an iterator over items in order from front to end;
	   }         
	}
