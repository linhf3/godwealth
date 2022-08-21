package com.godwealth.datastructuresandalgorithms.datastructure;

import java.io.*;

/**
 * 稀疏数组
 *
 */
public class SparseArray {
    //例子
//    public static void main(String[] args){
//        //先创建一个原始的二维数组 11 * 11
//        //0：表示没有棋子，1表示黑子，2表示蓝子
//        int[][] chessArr1 = new int[11][11];
//        chessArr1[1][2] = 1;
//        chessArr1[2][3] = 2;
//        chessArr1[4][5] = 2;
//        //输出
//        int sum = 0;
//        System.out.println("------原始的二维数组------");
//        for (int[] row:chessArr1){
//            for (int item:row){
//                System.out.printf("%d\t",item);
//                //转换为稀疏数组
//                //先遍历二维数组，得到非零数据的个数
//                if(item!= 0){
//                    sum++;
//                }
//            }
//            System.out.println();
//        }
//        System.out.println("-------有效值-------");
//        System.out.println(sum);
//        //创建对应的稀疏数组
//        int[][] sparseArray = new int[sum+1][3];
//        //给稀疏数组赋值
//        sparseArray[0][0] = 11;
//        sparseArray[0][1] = 11;
//        sparseArray[0][2] = sum;
//
//        //遍历二维数组将非0的值存到稀疏数组里
//        int count = 1;
//        System.out.println("------得到的稀疏数组------");
//        for(int i = 0;i<chessArr1.length;i++){
//            for (int j = 0; j<chessArr1[0].length;j++){
//                if (chessArr1[i][j]!=0)
//                {
//                    sparseArray[count][0] = i;
//                    sparseArray[count][1] = j;
//                    sparseArray[count][2] = chessArr1[i][j];
//                    count++;
//                }
//            }
//        }
//        for (int[] row:sparseArray){
//            for (int item:row){
//                System.out.printf("%d\t",item);
//            }
//            System.out.println();
//        }
//        //将稀疏数组恢复成二维数组
//        /*
//        1.首先读取稀疏数组的第一行，创建原始二维数组
//        2.在读取稀疏数组后几行的数据，并赋给二维数组
//         */
//        int r = sparseArray[0][0];
//        int c = sparseArray[0][1];
//        sum = sparseArray[0][2];
//        int[][] chessArray2 = new int[r][c];
//        /*for (int[] row:chessArray2){
//            for (int item:row){
//                System.out.printf("%d\t",item);
//
//            }
//            System.out.println();
//        }*/
//        for (int i = 1;i<sparseArray.length;i++){
//            r = sparseArray[i][0];
//            c = sparseArray[i][1];
//            int data = sparseArray[i][2];
//            chessArray2[r][c] = data;
//
//        }
//        System.out.println("-------得到的新二维数组--------");
//        for (int[] row:chessArray2){
//            for (int item:row){
//                System.out.printf("%d\t",item);
//            }
//            System.out.println();
//        }
//
//    }

    public static void main(String[] args) throws IOException, IOException {
        //先创建一个原始的二维数组 11 * 11
        //0：表示没有棋子，1表示黑子，2表示蓝子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;
        //输出
        int sum = 0;
        System.out.println("------原始的二维数组------");
        for (int[] row:chessArr1){
            for (int item:row){
                System.out.printf("%d\t",item);
                //转换为稀疏数组
                //先遍历二维数组，得到非零数据的个数
                if(item!= 0){
                    sum++;
                }
            }
            System.out.println();
        }
        System.out.println("-------有效值-------");
        System.out.println(sum);
        //创建对应的稀疏数组
        int[][] sparseArray = new int[sum+1][3];
        //给稀疏数组赋值
        sparseArray[0][0] = chessArr1[0].length;
        sparseArray[0][1] = chessArr1[0].length;
        sparseArray[0][2] = sum;

        //遍历二维数组将非0的值存到稀疏数组里
        int count = 1;
        System.out.println("------得到的稀疏数组------");
        for(int i = 0;i<chessArr1.length;i++){
            for (int j = 0; j<chessArr1[0].length;j++){
                if (chessArr1[i][j]!=0)
                {
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArr1[i][j];
                    count++;
                }
            }
        }
        System.out.println("把稀疏数组保存到文件中.....");
        FileOutputStream fileOutputStream = new FileOutputStream(new File("chess.text"));

        for (int i = 0; i< sparseArray.length;i++){
            for (int j = 0;j< sparseArray[0].length;j++){
                int a = sparseArray[i][j];
                if(j == 2){
                    fileOutputStream.write((String.valueOf(a)).getBytes());
                }else {
                    fileOutputStream.write((String.valueOf(a)+",").getBytes());
                }
            }
            fileOutputStream.write("\n".getBytes());
        }
        System.out.println("-----读取文件中的稀疏数组并恢复为原来的数组-------");

        BufferedReader bufferedReader = new BufferedReader(new FileReader("chess.text"));
        String line = null;
        int c = 0;
        String row = null;
        String col = null;

        int[][] chessArray2 = null;
        while((line = bufferedReader.readLine())!= null){
            c++;
            if(c==1){//稀疏矩阵的第一行
                String[] array = line.split(",");//以，为分割读取文件
                row = array[0];
                col = array[1];
                chessArray2 = new int[Integer.parseInt(row)][Integer.parseInt(col)];
            }else {
                String[] array = line.split(",");
                String hang = array[0];
                String lie = array[1];
                String data = array[2];
                chessArray2[Integer.parseInt(hang)][Integer.parseInt(lie)] = Integer.parseInt(data);
            }
        }
        for (int[] r:chessArray2){
            for (int item:r){
                System.out.printf("%d\t",item);
            }
            System.out.println();
        }
    }
}
