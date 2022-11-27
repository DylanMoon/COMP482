public class Tests {

    public static void main(String[] args) {
        var arr = new int[100][1000];
        var count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = count++;
            }
        }
        /*for (int[] ints : arr) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.printf("%3d ", ints[j]);
            }
            System.out.println();
        }*/
        var startTime = System.nanoTime();
        var curr = -1;
//        while(curr <= count){
//            System.out.println(Project3.BinarySearch2d(curr++, arr));
//            Project3.BinarySearch2d(curr++, arr);
//        }
        System.out.println(Project3.BinarySearch2d(0, arr));
        var stopTime = System.nanoTime();

        System.out.println("elapsed: " + (stopTime - startTime) / 1000000 + "ms");

    }
}
