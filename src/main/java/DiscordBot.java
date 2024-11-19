import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class DiscordBot extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Guild guild = event.getJDA().getGuildById(641315311383085089L);
        if (guild != null) {
            guild.upsertCommand("history", "Получить историю продаж артефакта")
                    .addOptions(new OptionData(OptionType.STRING, "artifact", "Название артефакта для поиска", true))
                    .queue();
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals("history")) return;

        CommandRateLimiter rateLimiter = new CommandRateLimiter();

        // Проверяем, можно ли выполнить команду
        if (!rateLimiter.canExecuteCommand()) {
            long secondsLeft = rateLimiter.getRemainingTimeInSeconds(); // Получаем оставшееся время
            event.reply("Команда может быть выполнена только через **" + secondsLeft + " секунд**.").queue();
            return;
        }

        // Если ограничение прошло, выполняем команду
        rateLimiter.updateLastCommandTime(); // Обновляем время последнего выполнения команды


        OptionMapping artifactName = event.getOption("artifact");
        String artName = artifactName.getAsString();

        // Проверка наличия артефакта в списке
        boolean artifactExists = ItemDB.items.stream()
                .anyMatch(item -> item.getOldName().equalsIgnoreCase(artName) ||
                        item.getNewName().equalsIgnoreCase(artName)); // Проверка как по старому, так и по новому названию

        if (!artifactExists) {
            // Если артефакт не найден, отправляем сообщение
            event.reply("Артефакт с названием ***" + artName + "*** не найден. Пожалуйста, проверьте правильность ввода.").queue();
            return; // Прерываем выполнение команды
        }

        event.reply("Выполняю поиск артефакта " + "***" + artName + "***" + "...").queue();

        asyncRun.executeApiCalls(ItemDB.getItemIdByName(artName), Main.numberOfThreads, Main.maxOffset);

        try {
            // Формируем файл с сортировкой
            String fileContent = formatItemListWithCustomOrder(Main.itemList, artName);

            // Создаём временный файл
            Path tempFile = createTempFile(fileContent, artName + "_history.txt");

            Main.itemList.clear();

            // Отправляем файл
            event.getHook().sendFiles(FileUpload.fromData(tempFile.toFile(), artName + "_history.txt")).queue();
        } catch (IOException e) {
            event.getHook().sendMessage("Произошла ошибка при создании файла: " + e.getMessage()).queue();
        }
    }


    private String formatItemListWithCustomOrder(List<Item> itemList, String itemName) {
        // Задаём пользовательский порядок качества
        List<String> qualityOrder = Arrays.asList("Лег", "Искл", "Редкий", "Особый", "Необычный", "Обычный");


        itemList.sort(Comparator
                .comparing((Item item) -> qualityOrder.indexOf(item.getQlt())) // По качеству
                .thenComparing((Item item) -> -item.getPtn())
                 .thenComparing((Item item) -> item.getFormattedTime(), Comparator.reverseOrder()));



        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');  // Использование точки в качестве разделителя тысяч

        DecimalFormat priceFormat = new DecimalFormat("#,###", symbols);

        StringBuilder itemListString = new StringBuilder("История продаж артефакта: " + itemName + "\n\n");
        itemListString.append(String.format("%-12s | %-6s | %-3s | %s%n", "Цена", "Кач-во", "Тир", "Дата/Время"));
        itemListString.append("-----------------------------------------------------\n");

        String currentQuality = null;

        for (Item item : itemList) {

            if (currentQuality == null || !currentQuality.equals(item.getQlt())) {
                if (currentQuality != null) {
                    itemListString.append("-----------------------------------------------------\n"); // Разделитель
                }
                currentQuality = item.getQlt();
            }

            String formattedPrice = priceFormat.format(item.getPrice());

            itemListString.append(String.format(
                    "%-12s | %-6s | %-3d | %s%n",
                    formattedPrice,
                    item.getQlt(),
                    item.getPtn(),
                    item.getFormattedTime()
            ));
        }

        return itemListString.toString();
    }


    private Path createTempFile(String content, String fileName) throws IOException {
        Path tempFile = Files.createTempFile(fileName, ".txt");
        Files.writeString(tempFile, content, StandardOpenOption.WRITE);
        return tempFile;
    }
}
