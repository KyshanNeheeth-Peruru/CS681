I considered an app where tickets for different auditoriums would be sold and we could move some people (i.e., tickets) from one auditorium to another.

In UnsafeAuditorium we have 2 locks in moveSeats(), due to which we could get a deadlock if one thread is waiting for 1st lock after acquiring the 2nd lock and if another thread is waiting for 2nd lock after acquiring 1st one.

To make it deadlock safe (i.e., thread safe) i used synchronised on lock object, which ensurs hat only one thread can enter the body at a time. This removed the possibility of deadlock caused by acquiring multiple locks in different orders. Also made the lock static so its same over all instances.


