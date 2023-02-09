## Description the overall idea of ​​the assignment 
### Write a java program to simulate the following schedulers 

1. **preemptive Shortest- Job First (SJF) Scheduling with context switching**
2. **Round Robin (RR) with context switching** 
3. **preemptive Priority Scheduling (with the solving of starvation problem)**

4. **AG Scheduling :**
- Each process is provided a static time to execute called quantum.

- Once a process is executed for given time period, it’s called FCFS till the
finishing of (ceil(52%)) of its Quantum time then it’s converted tenon
preemptive Priority till the finishing of the next(ceil(52%)), after that it’s
converted to preemptive Shortest- Job First (SJF).

#### **We have 3 scenarios of the running process in AG Scheduler :**
1. The running process used all its quantum time and it still have job to
do (add this process to the end of the queue, then increases its Quantum time by Two .

2. The running process was execute as non-preemptive Priority and
didn’t use all its quantum time based on another process converted 
from ready to running (add this process to the end of the queue, and
then increase its Quantum time by ceil(the remaining Quantum
time/2) ).

3. The running process was execute as preemptive Shortest- Job First
(SJF) and didn’t use all its quantum time based on another process
converted from ready to running (add this process to the end of the
queue, and then increase its Quantum time by the remaining
Quantum time).

4. The running process didn’t use all of its quantum time because it’s no
longer need that time and the job was completed (set it’s quantum time to zero).
