I considered an app where tickets for different auditoriums would be sold.

Since the number of seats in an auditorium is fixed we a shared variable called capacity.
I have the unsafe code in UnsafeAuditorium class.

buyTicket() and cancelTicket() could be accessed by different threads at same time and return a wrong capactiy value making the app not thread-safe.

I just added Reentrant lock to both methods to make it thread-safe.