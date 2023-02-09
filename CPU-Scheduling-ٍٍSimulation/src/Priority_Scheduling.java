public class Priority_Scheduling {
    private Process[] processes;
    private int context_switching;
    private int CurrentTime=0; // for count the time from 0 to the current time
    private int order_number=0; // for count the number of execution for each process 
    private String nameOfProcess[]; // for save  the name of processes
    private int burstTimeOfProcess[]; // for save the fact of burst time for each processes
    Priority_Scheduling (Process[] p ,int c) // constructor
    {
        processes = new Process[p.length];
        for (int i = 0;i <p.length;i++)
        {
            processes[i] = new Process(p[i].name,p[i].arrival_time,p[i].burst_time,p[i].priority,p[i].ag_quantum);
        }
        context_switching=c;
        Scheduling();
    }

    private void Scheduling ()
    {
        set_Name_andTime();
      while (All_Processes_Is_Finish(processes)==false) //check all process terminate or not
      { 
        
             Process avails []= new Process[NmbOfAvailableProcesses()] ;
             avails = ArrOfAvailableProcesses();
             int currentIndex=MaxPriority(avails);// index of process that is executed
             int nextIndex= MaxPriority(avails);// index of process that is executed after the past process is outed
            // while(ckeck_if_procoss_hasaApriorityThanNowProcess(avails, avails[i].priority)==false)
              while(currentIndex == nextIndex) // for check if process is out of the system, or it is has a large priority, not put this process out the system but still executeted
              {
                avails[currentIndex].burst_time -=1;
                if(avails[currentIndex].burst_time==0) // calculate and set the turnaround time and the waiting time in each process in system
                {
                    order_number++;
                    avails[currentIndex].execution_order = order_number;
                    avails[currentIndex].waiting_time = ((CurrentTime+1)-searchin_bursttimeArray(avails[currentIndex].name)) - avails[currentIndex].arrival_time;
                    avails[currentIndex].turnaround_time =avails[currentIndex].waiting_time+searchin_bursttimeArray(avails[currentIndex].name);

                }


                CurrentTime++;

                nextIndex = MaxPriority(avails);
                if(All_Processes_Is_Finish(processes)==false)
                    break;
                if(CurrentTime%10==0)//for get rid of starvation , for example at 10 second at totel or current time , calling function Aging for makimg all processes execute although small priotity processes
                Aging(avails);
              }


      }

      
    }

    private void set_Name_andTime() // for the name  in array nameOfProcess[] and burst time in array burstTimeOfProcess[]
    {
        nameOfProcess = new String[processes.length];
        burstTimeOfProcess = new int [processes.length];
        for(int i=0;i<processes.length;i++)
        {
            nameOfProcess[i] = processes[i].name;
            burstTimeOfProcess[i] = processes[i].burst_time;
        }
    }
    private int searchin_bursttimeArray(String n) // for return the true value of the processes before it is executed
    {
        for(int i=0;i<nameOfProcess.length;i++)
        {
            if(nameOfProcess[i]==n)
                return burstTimeOfProcess[i];
        }
        return 1;
    }
    private double CalculateAverageOfWaitingTime ()
    {
        double sum =0;
        for (int i = 0 ;i < processes.length ;i++)
        {
            sum += processes[i].waiting_time;
        }

        return sum/processes.length;
    }
    private double CalculateAverageOfTurnaroundTime ()
    {
        double sum =0;
        for (int i = 0 ;i < processes.length ;i++)
        {
            sum += processes[i].turnaround_time;
        }
        return sum/processes.length;
    }
    private Boolean All_Processes_Is_Finish(Process[] p) // check all process terminate or not
    {
        int numberOfProcess =p.length;
        for(int i=0;i<numberOfProcess;i++)
        {
            if(p[i].burst_time!=0)
                return false;
        }
        return true;
    }

    public int NmbOfAvailableProcesses() // count and return number of processes that is arriver time is less than or equal the current or total time
    {
        int cnt=0;
        for(int i=0;i<processes.length;i++)
        {
            if(processes[i].arrival_time <=CurrentTime)
                cnt++;
        }
        return cnt;
    }
    public Process[] ArrOfAvailableProcesses() //return array of processes that is arriver time is less than or equal the current or total time
    {
        Process [] availArr = new Process[NmbOfAvailableProcesses()];
        int j=0;
        for(int i=0;i<processes.length;i++)
        {
            if(processes[i].arrival_time <=CurrentTime)
            {
                availArr [j] = processes[i];
                j++;
            }

        }
        return availArr;
    }
    private int MaxPriority(Process [] av) // return the index of process that has a max priority
    {
        Process flag;
        for(int i=0;i<av.length;i++)  // sorting the available array
        {
            for(int j=0;j<av.length;j++)
            {
                if(av[j].priority > av[i].priority)
                {
                    flag = av[j];
                    av[j] =av[i];
                    av[i] = flag;
                }
            }
        }
        int i;
        for(i=0;i<av.length;i++)
        {
            if(av[i].burst_time <=0) // for checking the process if it terminate return the process that after it to execute
                continue;
            else
                return i;
        }
        return i;



    }

    //  aging function for starvation
    private void Aging(Process ava [] ) // for guarantee that all process is executed although that's min priorty by increase the priority of processes that it's priority small
    {
        int size = (1/4) *(ava.length)  ; // for example increase 25% of the available array that is priority is small
        int cnt=1;
        for(int i = (ava.length)-1;cnt<size;i--)
        {
            ava[i].priority -=4; // for example increase the priority of min process priority by 4
            cnt++;
        }

    }
    public void Print()
    {
        System.out.println("Program Output scheduling : ");
        for (int i = 0 ;i < processes.length ;i++)
        {
            System.out.println("process name is " + processes[i].name);
            System.out.println("process execution order is " + processes[i].execution_order);
            System.out.println("process waiting time is " + processes[i].waiting_time);
            System.out.println("process turnaround time is " + processes[i].turnaround_time);
            System.out.println("-------------------------------------------------------------");
        }
        System.out.println("average of waiting time is " + CalculateAverageOfWaitingTime());
        System.out.println("average of turnaround time is " + CalculateAverageOfTurnaroundTime());
        System.out.println("--------------------end priority scheduling--------------------");
    }

}
