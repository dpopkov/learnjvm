jmmtc - Java Memory Management Training Course
----------------------------------------------

### Example 2: GcAndFinalizeExample ([link](src/main/java/learn/jvm/jmmtc/ex02gc))  

### Example 3: Soft Leaks ([link](src/main/java/learn/jvm/jmmtc/ex03softleaks))

Soft leaks - an object is referenced on the stack even though it will never be used again. 

Run example with java -Xmx10m option, to get java.lang.OutOfMemoryError

### The "Mark and Sweep" process

Garbage collection - removing from the heap those objects which ae no longer reachable.
Modern garbage collectors use a clever mechanism for achieving it. 
Rather than searching for all the objects to remove, instead, the garbage collector looks
for all the objects that need to be retained, and it rescues them.
It's a two stage process. The first stage is the marking, and the second stage is sweeping.
In the marking stage, the program's execution is first paused. This is sometimes called
"A stop the world event". Marking cannot work properly if there are any threads still executing.
So all the threads in the application are paused.
The garbage collector then checks every single live reference. It simply looks at every variable
on the stack and follows its reference. The objects that it finds at the end of the reference
is marked as being alive. And it then follows any other references, that that object has, 
and marks those as being alive, also.
Once all the objects that are referenced are marked for keeping, a full scan of the heap takes place,
and the stages of all the objects is checked.
The memory occupied by those objects not marked of in used, can then be freed up.
And finally, the objects that are being kept, are moved into a single contiguous block of memory.
This stops the heap from becoming fragmented over time and makes it easier and quicker 
for the virtual machine to find memory to allocate to future objects.
So the garbage collector doesn't really collect any garbage. It actually collects objects which
are not eligible for garbage collection.


### Tuning the Virtual Machine

`-Xms`    set the starting heap size  
`-Xmx`    set the maximum heap size  
e.g. `-Xmx512m -Xms150m`  
`-XX:MaxPermSize`     set the size of the permgen (on Java7 and older)  
e.g. `-XX:MaxPermSize=256M`  
`-verbose:gc`     print to the console when a garbage collection takes place  
`-Xmm`     set the size of the young generation  
`-XX:+HeapDumpOnOutOfMemoryError`  creates a heap dump file (Oracle Java 8)  
`-XX:HeapDumpPath=path`     sets path to a heap dump file (Oracle Java 8)  

#### Types of collectors on Oracle JVM

* Serial    `-XX:+UseSerialGC`
* Parallel  `-XX:+UseParallelGC`
* Mostly Concurrent Mark-Sweep `-XX:+UseConcMarkSweepGC`
* Mostly Concurrent G1  `-XX:+UseG1GC`
