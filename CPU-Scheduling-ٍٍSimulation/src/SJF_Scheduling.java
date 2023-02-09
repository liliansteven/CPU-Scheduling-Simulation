public class SJF_Scheduling {
    private Process[] processes;
    private int context_switching;

    SJF_Scheduling (Process[] p ,int c)
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
        int time =0;
        boolean enter_process_to_processor = false;
        boolean process_come = false;
        int p_with_shortest_burst_time = -1;
        int exe_order = 0;
        AddBurstTimeToTurnaroundTime();
        while (!CheckFinished())
        {
            // choose process
            // if no process run now
            if(p_with_shortest_burst_time == -1)
            {
                for (int t = 0 ;t <= time ;t++)
                {
                    for (int i = 0 ;i < processes.length ;i++)
                    {
                        if(p_with_shortest_burst_time == -1 && processes[i].burst_time>0 && processes[i].arrival_time <=t)
                        {
                            p_with_shortest_burst_time = i;
                            enter_process_to_processor = true;
                            process_come  = true;
                        }else if (p_with_shortest_burst_time != -1 && processes[i].burst_time <processes[p_with_shortest_burst_time].burst_time && processes[i].arrival_time <=t && processes[i].burst_time>0)
                        {
                            p_with_shortest_burst_time = i;
                            enter_process_to_processor = true;
                            process_come  = true;
                        }
                    }
                }
            } else
            {
                // check if process that run now is the smallest process come
                for (int t = 0 ;t <= time ;t++)
                {
                    for (int i = 0 ;i < processes.length ;i++)
                    {
                        if(processes[i].burst_time>0 && processes[i].arrival_time <=t && processes[i].burst_time <processes[p_with_shortest_burst_time].burst_time)
                        {
                            p_with_shortest_burst_time = i;
                            enter_process_to_processor = true;
                        }
                    }
                }
            }
            if(process_come)
            {
                // add switch time to waiting time for processes if old process switched by another
                if (enter_process_to_processor)
                {
                    time += context_switching;
                    AddContextTime(time);
                    enter_process_to_processor = false;
                }
                // processor finish one burst for chosen process
                processes[p_with_shortest_burst_time].burst_time -= 1;
                time++;
                // add waiting time for other processes
                AddWaitingTime(p_with_shortest_burst_time,time);
                if(processes[p_with_shortest_burst_time].burst_time == 0)
                {
                    exe_order++;
                    processes[p_with_shortest_burst_time].execution_order = exe_order;
                    p_with_shortest_burst_time=-1;
                    process_come=false;
                }
            }
        }
        AddWaitingTimeToTurnaroundTime();
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
    private void AddWaitingTime (int process_run_now, int time_of_now)
    {
        for (int i = 0 ;i < processes.length ;i++)
        {
            if (process_run_now != i && processes[i].arrival_time < time_of_now && processes[i].burst_time != 0)
            {
                processes[i].waiting_time +=1;
            }
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
        int sum =0;
        for (int i = 0 ;i < processes.length ;i++)
        {
            sum += processes[i].waiting_time;
        }
        return sum/processes.length;
    }
    private double CalculateAverageOfTurnaroundTime ()
    {
        int sum =0;
        for (int i = 0 ;i < processes.length ;i++)
        {
            sum += processes[i].turnaround_time;
        }
        return sum/processes.length;
    }
    public void Print()
    {
        System.out.println("by sjf scheduling---------->");
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
        System.out.println("--------------------end sjf scheduling--------------------");
    }
}
