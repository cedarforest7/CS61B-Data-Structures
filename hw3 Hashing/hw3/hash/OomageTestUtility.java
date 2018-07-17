package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int N = oomages.size();
        int[] oNum = new int[M];        //store the num of Oomages each bucket has
        for (Oomage o : oomages) {
            oNum[bucketNum(o, M)]++;
        }
        for (int n : oNum) {
            if (n < N/50 || n > N/2.5) {
                return false;
            }
        }
        return true;
    }
    //converte an Oomage’s hashCode to a bucket number
    private static int bucketNum(Oomage o, int M) {            //M buckets
        return (o.hashCode() & 0x7FFFFFFF) % M;
    }
}
