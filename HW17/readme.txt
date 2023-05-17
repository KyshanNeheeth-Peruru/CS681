I considered an app where tickets for different auditoriums would be sold and we could move some people (i.e., tickets) from one auditorium to another.

In UnsafeAuditorium in moveSeats(), if one thread acquires the lock of the current Auditorium object and another thread acquires the lock of the second Auditorium object, and then both threads try to acquire the lock of the other object, a deadlock can occur.

To make it deadlock safe, i did open call of cancelticket() in moveseats.


