package org.example.src.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class OrderHistory {
    private final Deque<OrderCommand> commands = new ArrayDeque<>();

    public void execute(OrderCommand command) {
        command.execute();
        commands.push(command);
    }

    public boolean undo() {
        if (commands.isEmpty()) {
            return false;
        }

        commands.peek().undo();
        commands.pop();
        return true;
    }
}
