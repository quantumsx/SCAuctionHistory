import java.util.concurrent.atomic.AtomicLong;

public class CommandRateLimiter {
    private static final AtomicLong lastCommandTime = new AtomicLong(0L);
    private static final long RATE_LIMIT_MS = 20_000; // Ограничение: 1 минута в миллисекундах

    // Метод для проверки, можно ли выполнить команду
    public boolean canExecuteCommand() {
        long currentTime = System.currentTimeMillis();
        long lastExecution = lastCommandTime.get();

        return currentTime - lastExecution >= RATE_LIMIT_MS;
    }

    // Метод для обновления времени последнего выполнения команды
    public void updateLastCommandTime() {
        lastCommandTime.set(System.currentTimeMillis());
    }

    // Метод для получения оставшегося времени ожидания в секундах
    public long getRemainingTimeInSeconds() {
        long currentTime = System.currentTimeMillis();
        long lastExecution = lastCommandTime.get();

        // Если можно выполнить команду, возвращаем 0
        if (currentTime - lastExecution >= RATE_LIMIT_MS) {
            return 0;
        }

        // Вычисляем оставшееся время
        return (RATE_LIMIT_MS - (currentTime - lastExecution)) / 1000;
    }
}