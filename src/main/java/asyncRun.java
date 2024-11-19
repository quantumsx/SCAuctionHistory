import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class asyncRun {

    public static void executeApiCalls(String itemToFind, int threadCount, int maxOffset) {
        // Создаем пул из 10 потоков
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // Список задач для выполнения
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < maxOffset; i += 200) {

            int offset = i;

            String token = TokenArray.getNewToken();

            tasks.add(() -> {
                ApiCall.ApiDescription(itemToFind, offset, token);
                return null;// Отправляем запрос
            });

        }

        try {
            // Выполняем все задачи
            List<Future<Void>> results = executorService.invokeAll(tasks);

            // Ждем завершения всех задач
            for (Future<Void> future : results) {
                try {
                    future.get(); // Проверяем успешность выполнения задачи
                } catch (Exception e) {
                    System.err.println("Error processing task: " + e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            System.err.println("Execution interrupted: " + e.getMessage());
        } finally {
            // Завершаем работу пула потоков
            executorService.shutdown();
        }


    }
}
