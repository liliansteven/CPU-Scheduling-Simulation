/*
20200404
20200138
20200131
20200548
20200218
 */

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main (String[] args)
    {
        int number_of_p;
        int rr_quantum;
        int context_switching;
        String p_name;
        int p_arrival_time;
        int p_burst_time;
        int p_priority;
        int p_quantum;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of processes :");
        number_of_p = scanner.nextInt();
        System.out.println("Enter the round robin Time quantum : ");
        rr_quantum = scanner.nextInt();
        System.out.println("Enter the context switching :");
        context_switching = scanner.nextInt();

        Process[] processes = new Process[number_of_p];
        for (int i = 0; i < number_of_p; i++) {
			System.out.println("Enter process " + (i + 1) + " name.");
			p_name = scanner.next();
			System.out.println("Enter arrival Time of process " + (i + 1) + ".");
			p_arrival_time = scanner.nextInt();
			System.out.println("Enter burst Time of process " + (i + 1) + ".");
			p_burst_time= scanner.nextInt();
			System.out.println("Enter quantum Time of process " + (i + 1) + ".");
			p_quantum = scanner.nextInt();
			System.out.println("Enter priority of process " + (i + 1) + ".");
            p_priority = scanner.nextInt();
			System.out.print("\n");

			processes[i] = new Process(p_name, p_arrival_time, p_burst_time, p_priority , p_quantum);
		}
        int choice = 0;
        System.out.println("Choice one of the following schedulers:");
        System.out.println("1. SJF_Scheduling");
        System.out.println("2. RR_Scheduling");
        System.out.println("3. Priority_Scheduling");
        System.out.println("4. AG_Scheduling");
        choice = scanner.nextInt();
        if(choice == 1){
            SJF_Scheduling sjf = new SJF_Scheduling (processes ,context_switching);
            sjf.Print();
        }
        if(choice == 2){
            RR_Scheduling rr = new RR_Scheduling(processes ,rr_quantum ,context_switching);
            rr.Print();
        }
        if(choice == 3){
            Priority_Scheduling p = new Priority_Scheduling(processes ,context_switching);
            p.Print();
        }
        if(choice == 4){
            AG_Scheduling ag = new AG_Scheduling(processes ,context_switching);
            ag.Print();
        }
    }
}