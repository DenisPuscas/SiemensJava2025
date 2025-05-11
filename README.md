The original implementation had several concurrency issues:
- shared mutable state was accessed from multiple threads without synchronization;
- the method returned immediately without waiting for background tasks to finish;
- it did not use CompletableFuture composition to coordinate task completion.
