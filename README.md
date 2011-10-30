EMF-Prevayler
=============

This is an experimental project to implement transparent persistence
for EMF using Prevayler as a back end.


End goal
========

If all this works out as well as I expect, the result will be that
you'll be able to tell the library: "Connect this EMF resource and all
it's contained objects to this directory over here", and then use your
EMF objects just like usual.  And every change you make will
automatically and transparently be persisted.

Yes, You Won't Have To Think About Getting Your Stuff Stored/Retrieved
Anymore.

And since Prevayler journals every change, the worst case in a system
crash is that you lose the last few changes if the disk wasn't flushed
yet.

There will be a performance hit--not sure how much yet.  But I expect
that for interactive systems, the bottleneck will be the speed of the
human in front of the computer...

There's also (already) a configurable snapshot thread that will take a
new snapshot as frequently (or not) as you desire and then clean up
the stale journal files.  Or if you prefer you can turn this behavior
off and handle this yourself.


Here's how this could work
--------------------------

Prevayler's basic idea is that you store system state by taking a
snapshot, plus journalling all object mutations that happen after the
snapshot.

All EMF objects are implemented using interfaces.  So we can proxy all
object accesses pretty easily either by using dynamic proxies (for
EObjects) or with the decorator pattern.

Second, all EMF objects contained in a resource are easily addressable
via a URI.  i.e.: Given an EMF Resource, and a URI to a contained
object, it is an average O(log n) operation to find that contained
resource.  This is a reasonable performance target and lets us simply
and automatically avoid the [Prevalence
Baptism problem](http://prevayler.org/apidocs/org/prevayler/Transaction.html).

Since we can proxy object accesses, and we can easily find any random
object in the EMF object graph starting at the root, we can
automatically and transparently turn any object mutation operation
into a Prevayler transaction that will be journaled.

For EList and other containers, this is straightforward if a bit
annoying to implement.

For regular EObjects, we have to rely on EMF naming conventions plus
maybe a few of our own.  All setXXX operations get converted into
Transactions.  All getXXX operations get converted into Queries.
That's it.  For custom methods, the simplest thing is to turn all of
them into Transactions (assume that they mutate state even if they
don't), or possibly one could create a new naming convention to
specify methods are should or should not be journalled.

Since we're snapshotting by serializing the EMF to XMI, we can use
EMF's native tools for schema evolution.

Sound cool?

If you think so, please fork the repo and send merge requests as you
extend / fix this. :-)


License
=======

This work is distributed under the terms of the Eclipse Public License
1.0.
