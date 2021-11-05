package gradient;

import jade.core.Agent;
import java.util.Scanner;
import jade.core.behaviours.OneShotBehaviour;


public class Gradient_Agent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyOneShotBehaviour());
  } 

  private class MyOneShotBehaviour extends OneShotBehaviour {

    public void action() {
      Scanner in = new Scanner(System.in);

	   //Entrada de valor n, cantidad de observaciones
	   System.out.println(""); 
	   int n = 0;
	   System.out.print("Please enter qty of observations: "); 
	   n = in.nextInt();
	   System.out.println("");
	   
	   //Entrada de valores de las observaciones
	   double obs[][] = new double[n][2];
	   for (int i = 0; i < n; i++){
	     System.out.print("Please enter X value of observation #" + (i+1) + ": "); 
	     obs[i][0] = in.nextDouble();
	     System.out.print("Please enter Y value of observation #" + (i+1) + ": "); 
	     obs[i][1] = in.nextDouble();     
	   }

	   //Asignando valores iniciales
	   double beta_0 = 0;
	   double beta_1 = 0;
	   double a = 0.0015;
	   double E = 100000;
	   double aux,aux3;
	   long aux2;
	   boolean flag = false;
	   int contador =0;
	   
	   //Ciclo iterativo hasta que encontremos un error cercano a 0
	   while (flag == false) {
		   //Calculando sumatorias de yi-B0+(B1*xi), (yi-B0+(B1*xi))^2 y xi*(yi-B0+(B1*xi)) y asignandolas a aux, aux2 y aux3
		   aux = 0; // yi-B0+(B1*xi)
		   aux2 = 0; // (yi-B0+(B1*xi))^2
		   aux3 = 0; // xi*(yi-B0+(B1*xi))
		   for (int i = 0 ; i < n; i++) {
			   aux += obs[i][1]+beta_0+(beta_1*obs[i][0]);
			   aux2 += Math.pow(obs[i][1]+beta_0+(beta_1*obs[i][0]),2);
			   aux3 += obs[i][0]*(obs[i][1]+beta_0+(beta_1*obs[i][0]));
		   }
		   
		   //Calculando E
		   E = (aux2)/9;
		   
		   //Si encontramos un error menor a 1
		   if (E < 2100) { //PARA ESTE DATASET, EL SISTEMA SOLO LLEGA HASTA UN ERROR MENOR A 2100
			   flag = true;
			   break;
		   }
		   
		   //Si no, modificamos valores de beta_0 y beta_1
		   else {
			   beta_0 += a*((-2*aux)/9);
			   beta_1 += a*((-2*aux3)/9);
		   }
		   contador++;
	   }
	   
	   System.out.println(""); 
	   System.out.println("Valor de E: "+ E); 
	   System.out.println("El modelo debe construirse con los siguientes valores:");
	   System.out.println("Beta 0: " + beta_0);
	   System.out.println("Beta 1: " + beta_1);
    }
    public int onEnd() {
      //myAgent.doDelete();   
      return super.onEnd();
    } 
  }
}