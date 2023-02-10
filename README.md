## Description of the Assignment : Write a java program to simulate the following schedulers 

- Before going into the following types of Scheduling, there are some sources will help you to understand more than what is in the assignment :
1. [jenny's playlist in Operating Systems](https://www.youtube.com/playlist?list=PLdo5W4Nhv31a5ucW_S1K3-x6ztBRD-PNa)
2. [CPU Scheduling | Chapter-5 | Operating System , Neso Academy's playlist](https://www.youtube.com/playlist?list=PLBlnK6fEyqRitWSE_AyyySWfhRgyA-rHk)
3. [Article : CPU Scheduling in Operating Systems](https://www.geeksforgeeks.org/cpu-scheduling-in-operating-systems/)
### **(1) preemptive Shortest- Job First (SJF) Scheduling with context switching.**
![alt text](https://media.geeksforgeeks.org/wp-content/uploads/20221109131350/SJF.png)
#### Some sources that will help in understanding (SJF) Scheduling :

1. [All you need to know about Shortest Job First (SJF) Scheduling ](https://www.guru99.com/shortest-job-first-sjf-scheduling.html)

2. [Shortest Remaining Time First (Preemptive SJF) Scheduling Algorithm](https://www.geeksforgeeks.org/shortest-remaining-time-first-preemptive-sjf-scheduling-algorithm/)

3. [Shortest Job First – Preemptive Scheduling with Example (SJF)](https://prepinsta.com/operating-systems/shortest-job-first-scheduling-preemptive-example/#:~:text=Shortest%20Job%20First%20%E2%80%93%20Preemptive%20Scheduling%20Algorithm%20is%20an%20algorithm%20in,job%20with%20shorter%20burst%20time.)

- Videos
1. [SJF with processes having CPU and IO Time | CPU Scheduling Algorithm in OS](https://www.youtube.com/watch?v=0l_BLaA6TcI)

2. [Shortest Job First(SJF) Scheduling Algorithm with example | Operating System](https://www.youtube.com/watch?v=pYO-FAg-TpQ)

3. [Scheduling Algorithms - Shortest Job First (SJF)](https://www.youtube.com/watch?v=t0g9b3SJECg)

3. [Shortest Job First Scheduling (Solved Problem 1)](https://www.youtube.com/watch?v=lpM14aWgl3Q)

4. [Preemptive Shortest Job First (SRTF) - CPU Scheduling](https://www.youtube.com/watch?v=h-e7QtjfmkI)

### **(2) Round Robin (RR) with context switching.** 
![alt text](https://www.gatevidyalay.com/wp-content/uploads/2018/10/Round-Robin-Scheduling.png)

#### Some sources that will help in understanding (RR) Scheduling :
1. [What is Round Robin Scheduling in OS?](https://www.scaler.com/topics/round-robin-scheduling-in-os/)

2. [Program for Round Robin Scheduling for the same Arrival time](https://www.geeksforgeeks.org/program-for-round-robin-scheduling-for-the-same-arrival-time/)

3. [Round Robin Scheduling with different arrival times](https://www.geeksforgeeks.org/round-robin-scheduling-with-different-arrival-times/)

4. [Selfish Round Robin CPU Scheduling](https://www.geeksforgeeks.org/selfish-round-robin-cpu-scheduling/)

- Videos : 
1. [Round Robin(RR) CPU Scheduling Algorithm in OS with example](https://www.youtube.com/watch?v=-jFGYDfWkXI)

2. [Round Robin(RR) Scheduling example with advantages and drawbacks | Operating Systems](https://www.youtube.com/watch?v=chjRXx7WvM0)

3. [Round Robin (RR) Process Scheduling Algorithm with Example](https://www.youtube.com/watch?v=MlPIDniDRYY)

4. [Scheduling Algorithms - Round Robin Scheduling](https://www.youtube.com/watch?v=YzBBJYfwdi8)

5. [Round Robin Scheduling (Turnaround Time & Waiting Time)](https://www.youtube.com/watch?v=7TpxxTNrcTg)

6. [Round Robin Scheduling - Solved Problem (Part 1)](https://www.youtube.com/watch?v=QlCmgBOMjlI)

7. [Round Robin Scheduling - Solved Problem (Part 2)](https://www.youtube.com/watch?v=wioTortHb_g)

#### **(3) preemptive Priority Scheduling (with the solving of starvation problem).**
![alt text](https://cstaleem.com/wp-content/uploads/2020/05/Priority-Based-preemptive.jpg)

#### Some sources that will help in understanding Starvation and Aging problem && Priority Scheduling :
1. [Starvation and Aging in Operating Systems](https://www.geeksforgeeks.org/starvation-and-aging-in-operating-systems/)

2. [What Is Starvation In OS? Understand The Definition, Causes, Solution](https://unstop.com/blog/starvation-in-os)

3. [What is Starvation and Aging?](https://afteracademy.com/blog/what-is-starvation-and-aging/)

4. [Preemptive Priority CPU Scheduling Algorithm](https://www.geeksforgeeks.org/preemptive-priority-cpu-scheduling-algortithm/)

5. [Priority Based CPU Scheduling Algorithm ](https://chiraggoyaliit.medium.com/different-cpu-scheduling-algorithms-in-operating-systems-79a221a9e595)

- Videos : 
1. [Preemptive Priority Scheduling Algorithm in OS with example | Operating System](https://www.youtube.com/watch?v=23h3lkHNL_s)

2. [Starvation and Aging in Priority Scheduling | Operating Systems](https://www.youtube.com/watch?v=01DiVzZbRjY)

3. [Non Preemptive Priority Scheduling Algorithm with example | Operating System](https://www.youtube.com/watch?v=5xYvFN9OrZs&t=3s)

**(4) AG Scheduling :**
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
