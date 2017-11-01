package com.panyangbo.distributedAsura;

import com.panyangbo.distributedAsura.config.ShardInfo4Asura;
import com.panyangbo.distributedAsura.shard.ShardHandler;
import com.panyangbo.distributedAsura.shard.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panyangbo panyangbo
 * @create 2017/6/26
 */


public class ShardPools4Asura extends Sharded<ShardHandler,ShardInfo4Asura> {
    public ShardPools4Asura(List<ShardInfo4Asura> shards, boolean isHashing) {
        super(shards, isHashing);
    }

    /*public static void quickSort(int[] array){
        if(array != null){
            quickSort(array, 0, array.length-1);
        }
    }

    private static void quickSort(int[] array,int beg,int end){
        if(beg >= end || array == null)
            return;
        int p = partition(array, beg, end);
        quickSort(array, beg, p-1);
        quickSort(array, p+1, end);
    }

    private static int partition(int[] array,int beg,int end){
        int last = array[end];
        int i = beg -1;
        for (int j = beg; j <= end-1; j++) {
            if(array[j] <= last){
                i++;
                if(i != j){
                    array[i] = array[i]^array[j];
                    array[j] = array[i]^array[j];
                    array[i] = array[i]^array[j];
                }
            }
        }
        if((i+1) != end){
            array[i+1] = array[i+1]^array[end];
            array[end] = array[i+1]^array[end];
            array[i+1] = array[i+1]^array[end];
        }
        return i+1;
    }*/

    public static void main(String[] args) {

        int[] array ={3,2,1,4,5,6,7,3,10,99,12,35,75,16};
        int [] result = mergeSort(array);
        for (int i = 0; i <result.length ; i++) {
            System.out.println(result[i]);
        }

        ArrayList a = new ArrayList();

    }

    private static int[] mergeSort(int[] array) {
        int[] result = new int[array.length];
        mergeSort(array,0,array.length-1,result);
        return result;
    }

    private static void mergeSort(int[] array, int low, int high,int[] result) {
        if ( high - low <= 6 ){
            for (int i = low ; i <= high ; i++){
                for (int j = i ; j > low && array[j-1]>array[j]; j--){
                    array[j-1] = array[j-1]^array[j];
                    array[j] = array[j-1]^array[j];
                    array[j-1] = array[j-1]^array[j];
                }
            }
            return;
        }
        int destLow  = low;
        int destHigh = high;
        int mid = (low + high) >>> 1;
        mergeSort(array,low,mid,result);
        mergeSort(array,mid+1,high,result);
        merge(array,destLow,mid,destHigh,result);
    }

    private static void merge(int[] array, int low, int mid, int high,int[] result) {
        int l = low;
        int m = mid+1;
        while (l<=mid && m<=high){
            if (array[l]<array[m]){
                result[low++] = array[l++];
            }else{
                result[low++] = array[m++];
            }
        }
        while (l<=mid){
            result[low++] = array[l++];
        }
        while (m<=high){
            result[low++] = array[m++];
        }
    }
}
