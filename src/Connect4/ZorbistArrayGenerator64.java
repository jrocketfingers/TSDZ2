/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect4;

public class ZorbistArrayGenerator64 {

    public static void main(String [] args){

        String ident = "  ";

        System.out.println("{");
        for(int i = 0; i < 3; i++){

            System.out.println(ident + "{");

            for(int j = 0; j < 6; j++){
                System.out.print(ident + ident + "{");
                for(int k = 0; k < 7; k++){
                    System.out.print((long)(Math.random() * Long.MAX_VALUE));
                    if (k!=6) System.out.print(", ");
                }
                System.out.print("}");
                if (j!=5) System.out.println(",");
                else System.out.println("");
            }
            System.out.println(ident + "}");

            if (i!=2) System.out.println(ident + " ,");
            else System.out.println("");
        }
        System.out.println("}");
    }

}
