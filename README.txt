1.What is the major difference between an abstract class and an interface?

Answer:
       We can only extend one abstract class but we can implement multiple interfaces 

2. Why is Java 7’s class inheritance flawed?

Answer: 
       Because it doesn't allow multiple inheritance which is introduced using default methods in java 8

Example: 

interface A {
    void m() default { ... }        
}
interface B extends A {}
interface C extends A {}
class D implements B, C {}

3. What are the major differences between Activities and Fragments?

Answer: 

* An Activity is an application component that provides a screen, with which users can interact in order to do something.

* Whereas a Fragment represents a behavior or a portion of user interface in an Activity. 

4. When using Fragments, how do you communicate back to their hosting Activity?

Answer:
       Using Interface, for example please see AddUserFragment in code communication with hosting Activity.


5. Can you make an entire app without ever using Fragments? Why or why not?

Are there any special cases when you absolutely have to use or should use

Fragments?

Answer: 
        Yes we can make entire app without using Fragments, We use fragments because they make it easier for developers to write applications that can scale across a variety of screen sizes Hence increase the reusability. No unless we use such View which requires fragment itself like viewpager.
 

6. What makes an AsyncTask such an annoyance to Android developers? Detail

some of the issues with AsyncTask, and how to potentially solve them.

Answer: 

* when rotating Activity the AsyncTask have old Activity refrence which was destriyed due to rotation 
and hence does not update new activity and causes memory leak by holding refrence to Activity which is destroyed.

* AsyncTask lifecycle does not ends with Activity you have to cancel the Task to end it 
(For example see BaseActivity class in code demonstrating such handling of AsyncTasks)

* AsyncTask Cancel does not actually halts the operations being performed in AsyncTask, 
It calls onCancelled(Object) instead of onPostExecute(Object) after completing doInBackground(Object[]), 
to stop the operation in doInBackground(Object[]) we need to periodically check if task canceled and stop 
operations (BaseAsyncTask class in code demonstrates that).

* The number of AsyncTasks that you can start is limited. For AsyncTask the limit is 128 concurrent tasks 
with an additional queue of 10 tasks. (if supporting Android 1.5, it’s a limit of ten tasks at a time, 
with a maximum queue of 10 tasks).