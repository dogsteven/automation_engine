This is my attempt to model an automation engine inspired by GoHighLevel.

We have two aspects of a visual automation engine: builder and execution. I have only implemented the barebone for execution.

In execution, we have 3 main subproblems to solve:
- fetching instructions
- executing the automation
- logging the outcome

Because I mainly deal with transaction processing system, I used aggregate pattern to model these things:
- `ExecutionTemplate` is where we get the definition of an automation, and of course, fetch instructions.
- `ExecutionInstance` is responsible for managing execution state, it's a pure state machine.
- `ExecutionLogEntry` is just a data class for... logging.

There are some instruction types:
- `Action`: synchronous invocation.
- `Activity`: dispatch something, suspend, wait for its completion, then resume.
- `Conditional`, `GoTo`: flow control.
- `Split`: randomly pick a route.
- `Signal`: suspend, wait for an event with timeout.
- `Drip`: throttle the rate of execution.

After executing an instruction, the `ExecutionInstance` may produce some domain events,
which will be either handle synchronously via `@EventListener`, or asynchronously via `Quartz` scheduling,
both approach guarantee strong consistency.

For drip instruction, I use an `ExecutionDripper`, which manages dripping processes.
We don't want to schedule a lot of triggers or inserting a lot of rows in a single transaction.
Instead, we split the process into batches, and the `ExecutionDripper` manages that.

I also provide a `Quartz` utility class named `TopicBasedSchedulerProvider`, which maintains a keyed scheduler pool,
and provides two ways to pick a scheduler:
- randomly pick a scheduler via Round-Robin mechanism.
- pick a scheduler based on an input `UUID`, which is guaranteed to be deterministic across machines.