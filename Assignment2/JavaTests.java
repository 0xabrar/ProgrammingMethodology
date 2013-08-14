/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author abrar
 */


import acm.program.*;

public class JavaTests extends ConsoleProgram {
  public static void main(String[] args) {
      int[] questions = new int[3];
      questions[0] = 0;
      questions[1] = 1;
      questions[2] = 2;
      int question = 2;
     question = CheckQuestion(questions, question);
     System.out.println("Final: " + question);
  }
 
  
     private static int CheckQuestion (int[] questions, int questionNumber) { 
       
         while (true) {
             questionNumber = (int)Math.random() * questions.length;
             int checks = 0;
             
             for (int i = 0; i < questions.length; i++) {
                 if (questionNumber != questions[i]) checks++;
             } 
             
             if (checks == questions.length - 1) break;

         }
         return questionNumber;
        

       
    }
     
}
  