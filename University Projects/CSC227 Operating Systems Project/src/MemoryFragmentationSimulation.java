// ===================================================================
// Hatoun - MemoryBlock Design & Memory Initialization
// ===================================================================


import java.util.*;

class MemoryBlock {
    // Represents one memory block
    int size;
    int startAddress;
    int endAddress;
    boolean isAllocated;
    String processID;
    int internalFragmentation;
    
    // Constructor to initialize memory block details
    public MemoryBlock(int size, int startAddress) {
        this.size = size;
        this.startAddress = startAddress;
        this.endAddress = startAddress + size - 1;
        this.isAllocated = false;
        this.processID = "Null";
        this.internalFragmentation = 0;
    }
}

public class MemoryFragmentationSimulation {
    static ArrayList<MemoryBlock> memory = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int strategy = 1; // 1=first-fit, 2=best-fit, 3=worst-fit
    static boolean initialReportPrinted = false;
    
    
    
    // ===================================================================
    // Hatoun & Maha - Main Program Menu
    // This handles user interaction: allocation, deallocation, reporting
    // ===================================================================
    public static void main(String[] args) {
        initializeMemory();
        while (true) {
            System.out.println("\n1) Allocates memory blocks");
            System.out.println("2) De-allocates memory blocks");
            System.out.println("3) Print report about the current state of memory and internal Fragmentation");
            System.out.println("4) Exit");
            System.out.println("============================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: allocateMemory(); break;
                case 2: deallocateMemory(); break;
                case 3: printReport(); break;
                case 4: System.exit(0);
                default: System.out.println("Invalid choice.");
            }
        }
    }


    // ===================================================================
    // Hatoun - Memory Setup Logic
    // This method initializes memory blocks by reading their sizes
    // and assigning start/end addresses.
    // ===================================================================
    static void initializeMemory() {
        System.out.print("Enter the total number of blocks: ");
        int M = scanner.nextInt();
        System.out.print("Enter the size of each block in KB: ");
        int startAddress = 0;
        for (int i = 0; i < M; i++) {
            int size = scanner.nextInt();
            memory.add(new MemoryBlock(size, startAddress));
            startAddress += size;
        }
        System.out.print("Enter allocation strategy (1=First-Fit, 2=Best-Fit, 3=Worst-Fit): ");
        strategy = scanner.nextInt();
        printInitialReport();
    }
    
    
    
    // ===================================================================
    // Rama - Allocation Logic
    // This method finds a suitable memory block and assigns it to a process
    // based on the selected strategy (first, best, worst fit)
    // ===================================================================
    static void allocateMemory() {
        System.out.print("Enter the process ID and size of process: ");
        String pid = scanner.next();
        int size = scanner.nextInt();

        MemoryBlock selected = null;
        for (MemoryBlock block : memory) {
            if (!block.isAllocated && block.size >= size) {
                if (strategy == 1 && selected == null) {
                    selected = block; // First-Fit
                    break;
                } else if (strategy == 2 && (selected == null || block.size < selected.size)) {
                    selected = block; // Best-Fit
                } else if (strategy == 3 && (selected == null || block.size > selected.size)) {
                    selected = block; // Worst-Fit
                }
            }
        }

        if (selected != null) {
            selected.isAllocated = true;
            selected.processID = pid;
            selected.internalFragmentation = selected.size - size;
            System.out.println(pid + " allocated at address " + selected.startAddress + ", internal fragmentation: " + selected.internalFragmentation);
        } else {
            System.out.println("Allocation failed: no suitable block found.");
        }
    }
    
    
    // ===================================================================
    // Walaa - Deallocation Logic
    // This method releases a block assigned to a process
    // by searching for the process ID and marking the block as free
    // ===================================================================
    static void deallocateMemory() {
        System.out.print("Enter the process ID to deallocate: ");
        String pid = scanner.next();
        boolean found = false;
        for (MemoryBlock block : memory) {
            if (block.isAllocated && block.processID.equals(pid)) {
                block.isAllocated = false;
                block.processID = "Null";
                block.internalFragmentation = 0;
                found = true;
                System.out.println("Process " + pid + " deallocated.");
                break;
            }
        }
        if (!found) System.out.println("Process not found.");
    }
    
    
    // ===================================================================
    // Maha - Initial Report Display
    // This method prints all memory blocks with their size, address range,
    // and whether they are allocated or free (at the start of simulation)
    // ===================================================================
    static void printInitialReport() {
        System.out.println("\nMemory blocks:");
        System.out.println("============================================");
        System.out.printf("%-10s%-10s%-15s%-15s\n", "Block#", "Size", "Start-End", "Status");
        System.out.println("============================================");
        for (int i = 0; i < memory.size(); i++) {
            MemoryBlock b = memory.get(i);
            String range = b.startAddress + "-" + b.endAddress;
            String status = b.isAllocated ? "allocated" : "free";
            System.out.printf("%-10d%-10d%-15s%-15s\n", i, b.size, range, status);
        }
        System.out.println("============================================");
        initialReportPrinted = true;
    }
    
    
    // ===================================================================
    // Maha - Detailed Report Display
    // This method prints detailed status of each block, including process ID
    // and internal fragmentation
    // ===================================================================
    static void printReport() {
        System.out.println("\nMemory blocks:");
        System.out.println("================================================================================");
        System.out.printf("%-10s%-10s%-15s%-15s%-15s%-10s\n", "Block#", "Size", "Start-End", "Status", "ProcessID", "Fragment");
        System.out.println("================================================================================");
        for (int i = 0; i < memory.size(); i++) {
            MemoryBlock b = memory.get(i);
            String range = b.startAddress + "-" + b.endAddress;
            String status = b.isAllocated ? "allocated" : "free";
            System.out.printf("%-10d%-10d%-15s%-15s%-15s%-10d\n", i, b.size, range, status, b.processID, b.internalFragmentation);
        }
        System.out.println("================================================================================");
    }
}
