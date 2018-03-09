import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.function.UnaryOperator;

public class Transform {
    static double[] array;
    static Semaphore sem = new Semaphore(0);

    static void initArray(int size){
        array = new double[size];
        for(int i=0;i<size;i++){
            array[i]= Math.random()*size/(i+1);
        }
        //System.out.print(array[1]);
    }

    public static void main(String[] args) throws InterruptedException {
        initArray(100000000);
        int cnt=4;
        UnaryOperator<Double> unary= i->Math.sin(i);
        parallelMean(cnt, unary);
    }

    static class MeanCalc extends Thread{
        private final int start;
        private final int end;
        UnaryOperator<Double> unary;

        MeanCalc(int start, int end, UnaryOperator<Double> unary){
            this.start = start;
            this.end=end;
            this.unary=unary;
        }
        public void run(){
            for (int i=start; i<end; i++)
            {
                array[i]=unary.apply(array[i]);
            }
            sem.release();
        }
    }

    static void parallelMean(int cnt, UnaryOperator<Double> unary) throws InterruptedException {
        MeanCalc threads[]=new MeanCalc[cnt];
        int size=array.length/cnt;
        for(int i=0; i<cnt; i++)
        {
            threads[i]=new MeanCalc(i*size,(i+1)*size, unary);

        }
        double t1 = System.nanoTime()/1e6;

        for(int i=0; i<cnt; i++)
        {
            threads[i].start();
        }
        sem.acquire(cnt);
        double t2 = System.nanoTime()/1e6;

        System.out.printf(Locale.US,"size = %d cnt=%d >  t2-t1=%f\n",
                array.length,
                cnt,
                t2-t1);
        //System.out.print(array[1]);
    }


}

