jmmtc - Java Memory Management Training Course
----------------------------------------------

### Example 3: Soft Leaks ([link](src/main/java/learn/jvm/jmmtc/ex03softleaks))

Soft leaks - an object is referenced on the stack even though it will never be used again. 

Run example with java -Xmx10m option, to get java.lang.OutOfMemoryError
