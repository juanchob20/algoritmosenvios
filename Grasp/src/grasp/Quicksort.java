package grasp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Software
 */
public class Quicksort {
 
  
  private ArrayList<Vuelo>  numbers;
  private int number;

  public void sort(ArrayList<Vuelo> values) {
    // Check for empty or null array
    if (values ==null || values.isEmpty()){
      return;
    }
    this.numbers = values;
    number = values.size();
    quicksort(0, number - 1);
  }

  private void quicksort(int low, int high) {
    int i = low, j = high;
    // Get the pivot element from the middle of the list
    double pivot = numbers.get(low + (high-low)/2).getCostoPorPaquete();

    // Divide into two lists
    while (i <= j) {
      // If the current value from the left list is smaller then the pivot
      // element then get the next element from the left list
      while (numbers.get(i).getCostoPorPaquete() < pivot) {
        i++;
      }
      // If the current value from the right list is larger then the pivot
      // element then get the next element from the right list
      while (numbers.get(j).getCostoPorPaquete() > pivot) {
        j--;
      }

      // If we have found a values in the left list which is larger then
      // the pivot element and if we have found a value in the right list
      // which is smaller then the pivot element then we exchange the
      // values.
      // As we are done we can increase i and j
      if (i <= j) {
        exchange(i, j);
        i++;
        j--;
      }
    }
    // Recursion
    if (low < j)
      quicksort(low, j);
    if (i < high)
      quicksort(i, high);
  }

  private void exchange(int i, int j) {
    Collections.swap(numbers, i, j);
    
  }
} 

