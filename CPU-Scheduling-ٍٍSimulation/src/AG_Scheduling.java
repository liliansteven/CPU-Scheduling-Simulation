import java.util.LinkedList;
import java.util.Queue;

public class AG_Scheduling {
    private Process[] processes;

    private int quarter_of_quantum;
    private int half_of_quantum;
    private int context_switching;
    boolean process_come = false;
    private Queue<Integer> process_number_that_arrived = new LinkedList();
    private int time =0;

    AG_Scheduling (Process[] p ,int c)
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
        boolean enter_process_to_processor = false;
        boolean first = true;
        int p_run_now = -1;
        int exe_order = 0;
        AddBurstTimeToTurnaroundTime();
        /*System.out.println("start scheduling");*/
        while (!CheckFinished())
        {
            // choose process
            if(!process_come)
            {
                // update ready queue
                UpdateQueue(time,time);
            }
            // check if it is first to add context switching time
            if (first)
            {
                // remove process from ready queue
                p_run_now = process_number_that_arrived.remove();
                // flag to add context switching time
                enter_process_to_processor = true;
                // next process not first
                first = false;
            } else
            {
                // check if next process is old process
                if (process_number_that_arrived.peek() == p_run_now)
                {
                    // remove process from ready queue
                    p_run_now = process_number_that_arrived.remove();
                }else
                {
                    // remove process from ready queue
                    p_run_now = process_number_that_arrived.remove();
                    // flag to add context switching time
                    enter_process_to_processor = true;
                }
            }
            // add switch time to waiting time for processes if old process switched by another
            if (enter_process_to_processor && context_switching != 0)
            {
                // update time
                time += context_switching;
                // add waiting time to arrived processes
                addContextTime(time);
                // remove flag
                enter_process_to_processor = false;
                // update ready queue
                UpdateQueue(time-context_switching+1,time);
            }
            // set value for quarter_of_quantum and half_of_quantum
            UpdateQuantum(p_run_now);
            PrintProcessDataBeforeRun(p_run_now);
            // run process quarter time of quantum
            int time_taken = MinisQuarterQuantum(p_run_now);
            // update time
            time += time_taken;
            // add waiting time to arrived processes
            addWaitingTime(p_run_now,time,time_taken);
            // update ready queue
            UpdateQueue(time-time_taken+1,time);
            // check if it is the greatest priority
            if (IndexOfMaxPriority(p_run_now) == p_run_now && processes[p_run_now].burst_time > 0)
            {
                // run process quarter time of quantum
                time_taken = MinisQuarterQuantum(p_run_now);
                // update time
                time += time_taken;
                // add waiting time to arrived processes
                addWaitingTime(p_run_now,time,time_taken);
                // update ready queue
                UpdateQueue(time-time_taken+1,time);
                // time will process taken in processor if it is still shortest job
                int time_will_taken = half_of_quantum;
                // real time that process taken in processor
                time_taken = 0;
                // check if it is the shortest job and processor not tack time greater than half of quantum
                while (IndexOfMinBurstTime(p_run_now) == p_run_now && time_taken < time_will_taken && processes[p_run_now].burst_time > 0)
                {
                    // update the time taken
                    time_taken++;
                    // run process
                    processes[p_run_now].burst_time--;
                    // update time
                    time ++;
                    // add waiting time to arrived processes
                    addWaitingTime(p_run_now,time,1);
                    // update ready queue
                    UpdateQueue(time-time_taken+1,time);
                }
                // check if process not finish
                if(processes[p_run_now].burst_time > 0)
                {
                    if (time_taken+1 == time_will_taken)
                    {
                        PrintProcessDataThatRunCaseI(p_run_now);
                        // case i : add process to the end of queue and increases quantum by 2
                        processes[p_run_now].ag_quantum += 2;
                        // add process to the end of queue
                        process_number_that_arrived.add(p_run_now);
                    } else if (time_taken == 0)
                    {
                        // case ii : add process to the end of queue and increases quantum by remaining_quantum/2
                        PrintProcessDataThatRunCaseII(p_run_now);
                        double remaining_quantum = processes[p_run_now].ag_quantum - (2*quarter_of_quantum);
                        double half_of_remaining_quantum = remaining_quantum / 2;
                        if(half_of_remaining_quantum % 1 != 0)
                        {
                            double fraction = half_of_remaining_quantum%1;
                            half_of_remaining_quantum = half_of_remaining_quantum-fraction+1;
                        }
                        processes[p_run_now].ag_quantum += half_of_remaining_quantum;
                        process_number_that_arrived.add(p_run_now);
                    }
                } else
                {
                    exe_order++;
                    // set execution order
                    processes[p_run_now].execution_order = exe_order;
                }
            } else
            {
                if(processes[p_run_now].burst_time > 0)
                {
                    // case iii : add process to the end of queue and increases quantum by remaining quantum
                    PrintProcessDataThatRunCaseIII(p_run_now);
                    int remaining_quantum = processes[p_run_now].ag_quantum - quarter_of_quantum;
                    processes[p_run_now].ag_quantum += remaining_quantum;
                    process_number_that_arrived.add(p_run_now);
                } else
                {
                    exe_order++;
                    // set execution order
                    processes[p_run_now].execution_order = exe_order;
                }
            }
        }
        AddWaitingTimeToTurnaroundTime();
    }
    private void addContextTime (int time_of_now)
    {
        for (int i = 0 ;i < processes.length ;i++)
        {
            if (processes[i].arrival_time < time_of_now && processes[i].burst_time != 0)
            {
                if(processes[i].arrival_time > (time_of_now-context_switching))
                {
                    processes[i].waiting_time += time_of_now-processes[i].arrival_time;
                } else
                {
                    processes[i].waiting_time += context_switching;
                }
            }
        }
    }
    private void addWaitingTime (int process_run_now, int time_of_now ,int time_taken)
    {
        for (int i = 0 ;i < processes.length ;i++)
        {
            if (process_run_now != i && processes[i].arrival_time < time_of_now && processes[i].burst_time != 0)
            {
                if(processes[i].arrival_time > (time_of_now-time_taken))
                {
                    processes[i].waiting_time += time_of_now-processes[i].arrival_time;
                } else
                {
                    processes[i].waiting_time += time_taken;
                }
            }
        }
    }
    private void AddBurstTimeToTurnaroundTime ()
    {
        for (int i = 0 ;i < processes.length ;i++)
        {
            processes[i].turnaround_time += processes[i].burst_time;
        }
    }
    private void AddWaitingTimeToTurnaroundTime ()
    {
        for (int i = 0 ;i < processes.length ;i++)
        {
            processes[i].turnaround_time += processes[i].waiting_time;
        }
    }
    private int MinisQuarterQuantum (int run_now)
    {
        if(processes[run_now].burst_time >= quarter_of_quantum)
        {
            processes[run_now].burst_time -= quarter_of_quantum;
            return quarter_of_quantum;
        } else
        {
            int time_taken = processes[run_now].burst_time;
            processes[run_now].burst_time = 0;
            return time_taken;
        }
    }
    private int IndexOfMaxPriority(int index) // return the index of process that has a max priority
    {
        int index_of_max_p = index;
        int p = processes[index].priority;
        for (int i=0; i < processes.length;i++)
        {
            if(p > processes[i].priority && processes[i].arrival_time <= time && processes[i].burst_time != 0)
            {
                index_of_max_p = i;
                p = processes[i].priority;
            }
        }
        return index_of_max_p;
    }
    private int IndexOfMinBurstTime(int index) // return the index of process that has a max priority
    {
        int index_of_min_brust_time= index;
        int b = processes[index].burst_time;
        for (int i=0; i < processes.length;i++)
        {
            if(b > processes[i].burst_time && processes[i].arrival_time <= time && processes[i].burst_time != 0)
            {
                index_of_min_brust_time = i;
                b = processes[i].burst_time;
            }
        }
        return index_of_min_brust_time;
    }
    private boolean CheckFinished ()
    {
        int num_of_finished_process =0;
        for (int i = 0 ;i < processes.length ;i++)
        {
            if (processes[i].burst_time == 0)
            {
                num_of_finished_process++;
            }
        }
        return num_of_finished_process == processes.length;
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
    private void UpdateQueue (int start_time,int end_time)
    {
        for (int time = start_time; time <= end_time; time++)
        {
            for (int i = 0 ;i < processes.length ;i++)
            {
                if(processes[i].arrival_time == time && processes[i].burst_time > 0 )
                {
                    process_number_that_arrived.add(i);
                    process_come = true;
                }
            }
        }
    }
    private int Ceil (int p_num,double d)
    {
        double q = processes[p_num].ag_quantum/d;
        if(q % 1 != 0)
        {
            q = (q - (q%1)) + 1;
        }
        return (int) q;
    }
    private void UpdateQuantum (int p_num)
    {
        quarter_of_quantum = Ceil(p_num,4);
        half_of_quantum = Ceil(p_num,2);
    }
    private void PrintProcessDataBeforeRun (int p_num)
    {
        System.out.println("Data for process "+processes[p_num].name+" : ( before run in processor and increase quantum time )");
        System.out.println("burst time ="+processes[p_num].burst_time);
        System.out.println("quantum time ="+processes[p_num].ag_quantum);
    }
    private void PrintProcessDataThatRunCaseI (int p_num)
    {
        System.out.println("Data for process "+processes[p_num].name+" : ( after run in processor ,Case I)");
        System.out.println("burst time ="+processes[p_num].burst_time);
        System.out.println("quantum time = "+processes[p_num].ag_quantum +" + 2 = "+(processes[p_num].ag_quantum+2) );
    }
    private void PrintProcessDataThatRunCaseII (int p_num)
    {
        System.out.println("Data for process "+processes[p_num].name+" : ( after run in processor ,Case II)");
        System.out.println("burst time ="+processes[p_num].burst_time);
        System.out.println("quantum time = "+processes[p_num].ag_quantum +" + ceil(remaining_quantum / 2) = ");
        double remaining_quantum = processes[p_num].ag_quantum - (2*quarter_of_quantum);
        double half_of_remaining_quantum = remaining_quantum / 2;
        if(half_of_remaining_quantum % 1 != 0)
        {
            double fraction = half_of_remaining_quantum%1;
            half_of_remaining_quantum = half_of_remaining_quantum-fraction+1;
        }
        System.out.println("quantum time = "+processes[p_num].ag_quantum +" + "+half_of_remaining_quantum+" = "+(processes[p_num].ag_quantum+2) );
    }
    private void PrintProcessDataThatRunCaseIII (int p_num)
    {
        System.out.println("Data for process "+processes[p_num].name+" : ( after run in processor ,Case III)");
        System.out.println("burst time ="+processes[p_num].burst_time);
        System.out.println("quantum time = "+processes[p_num].ag_quantum +" + ceil(remaining_quantum / 2) = ");
        int remaining_quantum = processes[p_num].ag_quantum - quarter_of_quantum;
        System.out.println("quantum time = "+processes[p_num].ag_quantum +" + "+remaining_quantum+" = "+(processes[p_num].ag_quantum+2) );
    }
    public void Print()
    {
        System.out.println("Program Output AG scheduling : ");
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
        System.out.println("--------------------end AG scheduling--------------------");
    }
}
