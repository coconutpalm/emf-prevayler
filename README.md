EMF-Prevayler
=============

This is an experimental project to implement transparent persistence
for EMF using Prevayler as a back end.

Here's the basic idea:
----------------------

All EMF objects are implemented using interfaces.  So we can proxy all
object accesses pretty easily.

Second, all EMF objects contained in a resource are easily addressable
via a URI.  i.e.: Given an EMF Resource, and a URI to a contained
object, it is an average O(log n) operation to find that contained
resource.  This is reasonable and lets us simply and automatically
avoid the "Prevalence Baptism" problem.

Prevayler's basic idea is that you store system state by taking a
snapshot, plus journalling all object mutations that happen after the
snapshot.

Since we can proxy object accesses, we can automatically and
transparently turn any object mutation operation into a Prevayler
transaction that will be journaled.

For EList and other containers, this is straightforward if a bit
annoying to implement.

For regular EObjects, we have to rely on EMF naming conventions plus
maybe a few of our own.  All setXXX operations get converted into
Transactions.  All getXXX operations get converted into Queries.
That's it.  For custom methods, the simplest thing is to turn all of
them into Transactions (assume that they mutate state even if they
don't), or possibly one could create a new naming convention to
specify methods are should or should not be journalled.

That's the idea anyway.  Anyone else interested in working on this,
feel free to fork the repo and send merge requests. :-)

License
=======

This work is distributed under the terms of the Eclipse Public License
1.0.
