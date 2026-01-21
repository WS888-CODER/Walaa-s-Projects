import java.util.*;


/*------------Process Class & Data Structures (Handled by Hatoun)------------*/

//This part defines the Process class, which stores attributes of each process.
class Process {
   int id, arrivalTime, burstTime, remainingTime, completionTime, waitingTime, turnaroundTime;

// Constructor to initialize a new process

   public Process(int id, int arrivalTime, int burstTime) {
      this.id = id;
      this.arrivalTime = arrivalTime;
      this.burstTime = burstTime;
      this.remainingTime = burstTime;  // Initially, remaining time is equal to burst time
   }
}


public class ProcessScheduler1 {
   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
   
      // Asking for the number of processes
      System.out.print("Number of processes= ");
      int n = scanner.nextInt();
      List<Process> processes = new ArrayList<>();
   
   // Taking input for arrival time and burst time for each process
      System.out.println("Arrival times and burst times as follows:");
      for (int i = 0; i < n; i++) {
         System.out.print("P" + (i + 1) + " Arrival time = ");
         int arrivalTime = scanner.nextInt();
         System.out.print("P" + (i + 1) + " Burst time = ");
         int burstTime = scanner.nextInt();
         processes.add(new Process(i + 1, arrivalTime, burstTime));
      }
   
      // Execute the scheduling simulation
      simulateScheduling(processes);
      scanner.close();
   }

/*------------Simulation of Event-Driven Scheduling (Handled by Hatoun)------------*/


// Function to simulate the SRTF scheduling with context switching
   public static void simulateScheduling(List<Process> processes) {
      int time = 0, completed = 0, totalWaitingTime = 0, totalTurnaroundTime = 0;
      int contextSwitchTime = 1; // Context switching time
      float cpuUtilization;
      Process currentProcess = null; // Track the currently executing process
   
   
   // Gantt chart representation
      List<String> ganttChart = new ArrayList<>();
      int startTime = 0;
      String previousProcess = "";
   
      System.out.println("\nScheduling Algorithm: Shortest remaining time first");
      System.out.println("Context Switch: " + contextSwitchTime + " ms");
   
   
/*------------Preemptive SJF Algorithm with FCFS Tie-Breaker (Handled by Rama)------------*/
//The scheduling loop selects the process with the shortest remaining time.


   // Scheduling loop: Runs until all processes are completed
      while (completed < processes.size()) {
         Process shortest = null;
      
         // Find the process with the shortest remaining time
         for (Process p : processes) {
            if (p.arrivalTime <= time && p.remainingTime > 0) {
               // If no process is selected yet OR a shorter remaining time is found
               // OR if tie in remaining time, apply FCFS (earlier arrival gets priority)
               if (shortest == null || p.remainingTime < shortest.remainingTime ||
                  (p.remainingTime == shortest.remainingTime && p.arrivalTime < shortest.arrivalTime)) {
                  shortest = p;
               }
            }
         }
      
      // If no process is ready to execute, increment time and continue
         if (shortest == null) { 
            time++;
            continue;
         }

/*------------Context Switching & Gantt Chart (Handled by Walaa)------------*/
//Whenever a new process is selected, a context switch occurs.

// Handle context switching: If a different process is selected
if (currentProcess != null && currentProcess != shortest) {
    if (startTime < time) {  // Ensure no duplicate time slots
        ganttChart.add(startTime + "-" + time + " P" + currentProcess.id);
    }

   // Add context switch to Gantt chart
    ganttChart.add(time + "-" + (time + contextSwitchTime) + " CS");
    time += contextSwitchTime; // Add context switch time
    startTime = time;
}
             
        // Start executing the current process
         currentProcess = shortest;
         previousProcess = "P" + shortest.id;
         shortest.remainingTime--;  // Reduce remaining execution time
         time++;
      
/*------------Performance Metrics Calculation (Handled by Maha)------------*/  
  
        // If the process finishes execution
         if (shortest.remainingTime == 0) { 
            completed++;
            shortest.completionTime = time;
            shortest.turnaroundTime = shortest.completionTime - shortest.arrivalTime;
            shortest.waitingTime = shortest.turnaroundTime - shortest.burstTime;
            totalTurnaroundTime += shortest.turnaroundTime;
            totalWaitingTime += shortest.waitingTime;
         
            // Save the final execution segment in the Gantt chart
            ganttChart.add(startTime + "-" + time + " P" + shortest.id);
            startTime = time;
         }
      }
   
      // Calculate CPU utilization (excluding context switch time)
      cpuUtilization = (float) (time - contextSwitchTime * (ganttChart.size() / 2)) / time * 100;
   
      // Print scheduling results
      printResults(processes, ganttChart, totalTurnaroundTime, totalWaitingTime, cpuUtilization);
   }

/*------------Output & Visualization (Handled by Maha)------------*/  

   // Function to display the results including Gantt Chart and performance metrics
   public static void printResults(List<Process> processes, List<String> ganttChart, int totalTurnaroundTime, int totalWaitingTime, float cpuUtilization) {
      System.out.println("\nTime\tProcess/CS");
      
      
      // Print the Gantt chart representation
      for (String step : ganttChart) {
         System.out.println(step);
      }
   
   
   // Display performance metrics
      System.out.println("\nPerformance Metrics");
      System.out.printf("Average Turnaround Time: %.2f\n", (float) totalTurnaroundTime / processes.size());
      System.out.printf("Average Waiting Time: %.2f\n", (float) totalWaitingTime / processes.size());
      System.out.printf("CPU Utilization: %.2f%%\n", cpuUtilization);
   }
}
