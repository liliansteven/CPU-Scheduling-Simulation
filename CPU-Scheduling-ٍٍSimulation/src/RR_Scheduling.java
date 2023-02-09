import java.util.LinkedList;
import java.util.Queue;

public class RR_Scheduling {
    private Process[] processes;
    private int quantum;
    private int context_switching;
    boolean process_come = false;
    private Queue<Integer> process_number_that_arrived = new LinkedList();

    RR_Scheduling (Process[] p ,int q ,int c)
    {
        processes = new Process[p.length];
        for (int i = 0;i <p.length;i++)
        {
            processes[i] = new Process(p[i].name,p[i].arrival_time,p[i].burst_time,p[i].priority,p[i].ag_quantum);
        }
        quantum=q;
        context_switching=c;
        Scheduling();
    }
    private void Scheduling ()
    {
        int time =0;
        boolean enter_process_to_processor = false;
        boolean first = true;
        int p_run_now = 0;
        int exe_order = 0;
        /*System.out.println("start scheduling");*/
        AddBurstTimeToTurnaroundTime();
        while (!CheckFinished())
        {
            /*System.out.println("at time " + time);*/
            // choose process
            if(!process_come)
            {
                UpdateQueue(time,time);
            } else
            {
                if (first)
                {
                    p_run_now = process_number_that_arrived.remove();
                    enter_process_to_processor = true;
                    first = false;
                }else
                {
                    if (process_number_that_arrived.peek() == p_run_now)
                    {
                        p_run_now = process_number_that_arrived.remove();
                    }else
                    {
                        p_run_now = process_number_that_arrived.remove();
                        enter_process_to_processor = true;
                    }
                }
                // add switch time to waiting time for processes if old process switched by another
                if (enter_process_to_processor)
                {
                    /*System.out.println("add context time");*/
                    time += context_switching;
                    AddContextTime(time);
                    enter_process_to_processor = false;
                    UpdateQueue(time-context_switching+1,time);
                }
                // processor take quantum time ot less
                int time_taken = MinisBurstTime(p_run_now);
                /*System.out.println("burst time for " +(p_run_now+1)+" process is " + processes[p_run_now].burst_time);*/
                time += time_taken;
                // add waiting time for other processes
                /*System.out.println("add waiting time");*/
                AddWaitingTime(p_run_now,time,time_taken);
                UpdateQueue(time-time_taken+1,time);
                if(processes[p_run_now].burst_time ==0)
                {
                    exe_order++;
                    processes[p_run_now].execution_order = exe_order;
                    if (process_number_that_arrived.isEmpty())
                    {
                        process_come = false;
                    }
                } else
                {
                    process_number_that_arrived.add(p_run_now);
                }
            }
        }
        AddWaitingTimeToTurnaroundTime();
        /*System.out.println("finish at time " + time);*/
    }
    private void AddContextTime (int time_of_now)
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
    private void AddWaitingTime (int process_run_now, int time_of_now ,int time_taken)
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
    private int MinisBurstTime (int run_now)
    {
        if(processes[run_now].burst_time >= quantum)
        {
            processes[run_now].burst_time -= quantum;
            return quantum;
        } else
        {
            int time_taken = processes[run_now].burst_time;
            processes[run_now].burst_time = 0;
            return time_taken;
        }
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
    public void Print()
    {
        System.out.println("Program Output for rr scheduling : ");
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
        System.out.println("--------------------end rr scheduling--------------------");
    }
}
