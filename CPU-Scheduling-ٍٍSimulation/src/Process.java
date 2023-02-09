public class Process {
    public String name;
    public int arrival_time;
    public int burst_time;
    public int priority;
    public int ag_quantum;
    public int waiting_time = 0;
    public int turnaround_time = 0;
    public int execution_order;

    Process (String n,int a,int b,int p, int q)
    {
        name=n;
        arrival_time=a;
        burst_time=b;
        priority=p;
        ag_quantum=q;
    }
}
