/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // TODO
		
		// 0. if one item or less in list, return that list
		if (list.size() <= 1) {
			return list;
		}
		
		// 1. split list in half
		int i = list.size() / 2;
		
		List<T> half1 = list.subList(0, i);
		List<T> half2 = list.subList(i, list.size());
		
		// 2. sort each half
		half1 = mergeSort(half1, comparator);
		half2 = mergeSort(half2, comparator);

		List<T> newList = new ArrayList<T>();
		int i1 = 0;
		int i2 = 0;
		
		// 3. merge both halves into newList
		// exit when go thru all items in one half
		while (i1 < half1.size() && i2 < half2.size()) {
			int c = comparator.compare(half1.get(i1), half2.get(i2));
			
			// item in half1 is smaller or equal to item in half2
			if (c <= 0) {
				newList.add(half1.get(i1));
				i1++;
			}
			else {
				newList.add(half2.get(i2));
				i2++;
			}
		}
		
		// add rest of list, exit when go through both halves
		while (i1 < half1.size() || i2 < half2.size()) {
			if (i1 < half1.size()) {
				newList.add(half1.get(i1));
				i1++;
			}
			else {
				newList.add(half2.get(i2));
				i2++;
			}
		}
        return newList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // TODO
		
		PriorityQueue<T> queue = new PriorityQueue<T>();
		
		for (T item : list) {
			queue.offer(item);
		}
		
		for (int i = 0; i < list.size(); i++) {
			list.set(i, queue.poll());
		}
	}
	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // TODO
		
		PriorityQueue<T> queue = new PriorityQueue<T>();
		
		for (T item : list) {
			// add if queue not full
			if (queue.size() < k) {
				queue.add(item);
			}
			// add if item is bigger than smallest item in queue
			else if (comparator.compare(item, queue.peek()) > 0){
				queue.remove();
				queue.add(item);
			}
		}
		
		List<T> newList = new ArrayList<T>();
		
		while (!queue.isEmpty()) {
			newList.add(queue.poll());
		}
		
        return newList;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
