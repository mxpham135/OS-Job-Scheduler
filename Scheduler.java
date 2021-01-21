import java.util.*; 

public class Scheduler {

	public static void main(String[] args) {
		
		System.out.println("\t\t\t-----Process Scheduler-----");
		System.out.println("This program would simulating four job schedulers of the Operating Systems:");
		System.out.println("First Come First Served, Shortest Job First, Round-Robin with Time Slice 2 & 5.\n");
		
		int job1[] = new int [30];
		/*int job1[] = {7,5,9,3,2};							// simple test case
		
		System.out.println("Test Case 1 (with 5 jobs)");
		generatedPrint(job1,5);						
		FCFS(job1, 5);							
		SJF(job1, 5);
		RoundRobin(job1, 5, 2);
		RoundRobin(job1, 5, 5);
		*/
		
		int jobLength = 5;
		int testCase = 1;
		while (testCase <= 6) {
			System.out.println("Test Case " + testCase + " with " + jobLength + " jobs");
			generatedPrint(job1,jobLength);					// generated and print jobs for test cases
			FCFS(job1, jobLength);							// call FCFS
			SJF(job1, jobLength);                           // call SJF
			RoundRobin(job1, jobLength, 2);                 // call Round Robin with 2 time slice
			RoundRobin(job1, jobLength, 5);                 // call Round Robin with 5 time slice
			jobLength += 5;                                 // add 5 jobs
			testCase++;
		}	
	}
	
	static void generatedPrint(int arr[], int size){
		Random random = new Random();
		
        System.out.print("\tJob #:   ");                	// print out job #
		for (int i = 0; i < size; i++) 
			System.out.printf("%5d",i+1);
		
        System.out.print("\n\tRun Time:");              	// generated/print random # for job length
		for (int i = 0; i < size; i++) {
			arr[i] = random.nextInt(20);
			System.out.printf("%5d",arr[i]);
		}
		System.out.println("\n");
	} 
	
	static void FCFS(int arr[], int size){  
		int sum = 0;
		double average = 0;
		int endTime[] = new int [size];						// an array to store the end time
		
		System.out.print("    First Come First Served");
		System.out.print("\n\tJob #:   ");					// print out Job # 
		for (int i = 0; i < size; i++) 
			System.out.printf("%5d",i+1);	
		
		System.out.print("\n\tRun Time:");					// print Job # run time
		for (int i = 0; i < size; i++) 
			System.out.printf("%5d",arr[i]);	
		
		System.out.print("\n\tEnd Time:");
		for (int i = 0; i < size; i++) {			
			sum += arr[i]; 									// calculate end time
			endTime[i] = sum;								// store end time in an array
			average += endTime[i];							// calculate the average turn around time
			System.out.printf("%5d",endTime[i]);	
		}	
		
		System.out.print("\n\tAverage Turnaround Time: " + average/size + "\n");
	}
	
	static void SJF(int arr[], int size){  
		int job[] = new int [size];							// store job #
		int runTime[] = new int [size];						// store run Time from input array
		int endTime[] = new int [size];						// store end time
		
		int temp1, temp2;
		int sum = 0;
		double average = 0;
		
		runTime = Arrays.copyOf(arr,size);					// copy input array
	
		for (int i = 0; i < size; i++) 						// copy job#
			job[i] = (i+1);
			
		System.out.println();
		for (int i = 1; i < size; i++) 						// sort input array
			for (int j = i; j > 0; j--) 
				if (runTime[j] < runTime [j - 1]) {			// swap array
					temp1 = runTime[j];	
					runTime[j] = runTime[j - 1];
					runTime[j - 1] = temp1;
					
					temp2 = job[j];							// swap job #
					job[j] = job[j - 1];
					job[j - 1] = temp2;
			    }
		
		System.out.print("    Shorest Job First");
		System.out.print("\n\tJob #:   ");					// print out Job # order
		for (int i = 0; i < size; i++) 
			System.out.printf("%5d",job[i]);
			
		System.out.print("\n\tRun Time:");					// print out shortest rum time first
		for (int i = 0; i < size; i++) 
			System.out.printf("%5d",runTime[i]);
			
		System.out.print("\n\tEnd Time:");
		for (int i = 0; i < size; i++) {					// store end time in temp array
			sum += runTime[i]; 						
			endTime[i] = sum;
			System.out.printf("%5d",endTime[i]);	
			average += endTime[i];							// calculate the average turn around time
		}	
		
		System.out.println("\n\tAverage Turnaround Time: " + average/size + "\n");
	}
	
	static void RoundRobin(int arr[], int size, int timeSlice){  
		int sum = 0, hold = 0;
		double average = 0;
		int endTime[] = new int [1000];						// an array to store the end time
		int job[] = new int [1000];							// hold job #
		
		int remainder[] = new int [size];					// to hold remainder runtime
		remainder = Arrays.copyOf(arr,size);		
		
		int max = 0;
		for (int i = 0; i < size; i++)						// calculate the last burst values
			max += remainder[i];
		
		System.out.print("    Round-Robin with Time Slice = " + timeSlice);
		System.out.print("\n\tTimeLeft:");
		int j = 0;
		while (hold < max){									// while haven't reach last burst values
			for (int i = 0; i < size; i++) {				// cut down runtime by timeSlice values
				if (remainder[i] > timeSlice) {
					remainder[i] -= timeSlice;
					sum += timeSlice;
					endTime[j] += sum;
					System.out.printf("%5d",remainder[i]);
				}	
				else if (remainder[i] > 0) {				// cut down runtime by remaining runtime values
					sum += remainder[i];
					remainder[i] = 0;
					hold = sum;								// check when hit last values
					average += hold;
					endTime[j] += sum;
					System.out.printf("%5d",remainder[i]);
				}
				job[j] = (i+1);								// store job #
				if (endTime[j] != 0) 				
					j++;	
			}
		}	
		
		System.out.print("\n\tJob #:   ");					// print out Job # order
		int i = 0;
		while (endTime[i] != 0) {
			System.out.printf("%5d",job[i]);
			i++;
		}
		
		System.out.print("\n\tEnd Time:");
		i = 0;												// print out end time
		while (endTime[i] != 0) {
			System.out.printf("%5d",endTime[i]);
			i++;
		}
		
		System.out.println("\n\tAverage Turnaround Time: " + average/size + "\n");
	}
}
