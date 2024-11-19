import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

public class ItemDB {

    private String oldName;
    private String newName;
    private String itemId;


    public ItemDB(String oldName, String newName, String itemId) {
        this.oldName = oldName;
        this.newName = newName;
        this.itemId = itemId;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }

    public String getItemId() {
        return itemId;
    }

    static List<ItemDB> items = new ArrayList<>();

    // Функция для добавления элементов в список
    public static void addItemsToList() {



        items.add(new ItemDB("Частотный кристалл", "Призрачный кристалл", "9nd0"));
        items.add(new ItemDB("Бенгальский огонь", "Трещотка", "1k96"));
        items.add(new ItemDB("Вспышка", "Комета", "gyn0"));
        items.add(new ItemDB("Батарейка", "Батарейка", "zy32"));
        items.add(new ItemDB("Снежинка", "Ледяной ежик", "5r5g"));
        items.add(new ItemDB("Пустышка", "Гиря", "y54z"));
        items.add(new ItemDB("Дезинтегратор", "Дезинтегратор", "wg3p"));
        items.add(new ItemDB("Лунный свет", "Лампочка Ильича", "4lkp"));
        items.add(new ItemDB("Гелий", "Гелий", "kqr0"));
        items.add(new ItemDB("Спираль", "Спираль", "gyq5"));
        items.add(new ItemDB("Зеркало", "Зеркало", "zyrm"));
        items.add(new ItemDB("Осколок", "Осколок", "5rwq"));
        items.add(new ItemDB("Призма", "Призма", "y5w3"));
        items.add(new ItemDB("Трансформатор", "Трансформатор", "qo06"));
        items.add(new ItemDB("Атом", "Атом", "wg53"));
        items.add(new ItemDB("Кристалл", "Красный кристалл", "gy10"));
        items.add(new ItemDB("Огненный шар", "Огонек", "zyw2"));
        items.add(new ItemDB("Капля", "Пиявка", "5rog"));
        items.add(new ItemDB("Волчьи слезы", "Волчьи слезы", "y5nz"));
        items.add(new ItemDB("Мамины бусы", "Ветка Калины", "wglp"));
        items.add(new ItemDB("Глаз", "Гребешок", "4l7p"));
        items.add(new ItemDB("Пламя", "Жар-птица", "kqp0"));
        items.add(new ItemDB("Солнце", "Солнце", "qoq6"));
        items.add(new ItemDB("Иней", "Иней", "gyln"));
        items.add(new ItemDB("Хрусталь", "Хрусталь", "zyqk"));
        items.add(new ItemDB("Корка", "Корка", "5rzo"));
        items.add(new ItemDB("Вихрь", "Вихрь", "y5k0"));
        items.add(new ItemDB("Фаренгейт", "Фаренгейт", "wgwz"));
        items.add(new ItemDB("Бритва", "Бритва", "4lgj"));
        items.add(new ItemDB("Каблук", "Каблук", "kqoj"));
        items.add(new ItemDB("Куриный бог", "Куриный бог", "qo94"));
        items.add(new ItemDB("Радиатор", "Радиатор", "ljrj"));
        items.add(new ItemDB("Глаз бури", "Глаз бури", "jkml"));
        items.add(new ItemDB("Кровь камня", "Ягодка", "9n1w"));
        items.add(new ItemDB("Ломоть мяса", "Сало", "1k4q"));
        items.add(new ItemDB("Желчь камня", "Плод", "gypg"));
        items.add(new ItemDB("Болотный гнилец", "Болотный гнилец", "zyo9"));
        items.add(new ItemDB("Душа", "Сердце", "5r34"));
        items.add(new ItemDB("Пружина", "Гантель", "y5yw"));
        items.add(new ItemDB("Остов", "Остов", "wgzd"));
        items.add(new ItemDB("Каменный цветок", "Роза", "gyv6"));
        items.add(new ItemDB("Медуза", "Цибуля", "zyly"));
        items.add(new ItemDB("Темная медуза", "Проклятая роза", "5rg1"));
        items.add(new ItemDB("Жильник", "Жильник", "y5jk"));
        items.add(new ItemDB("Ночная звезда", "Белая роза", "wg62"));
        items.add(new ItemDB("Протомедуза", "Протоцибуля", "4l1r"));
        items.add(new ItemDB("Браслет", "Браслет", "kqgy"));
        items.add(new ItemDB("Выверт", "Вехотка", "9nvy"));
        items.add(new ItemDB("Грави", "Прима", "1kv2"));
        items.add(new ItemDB("Золотая рыбка", "Креветка", "gyg5"));
        items.add(new ItemDB("Золотистый грави", "Золотистая Прима", "zypm"));
        items.add(new ItemDB("Янтарник", "Янтарник", "5rpq"));
        items.add(new ItemDB("Криоген", "Криоген", "y5m3"));
        items.add(new ItemDB("Черный кристалл", "Темный кристалл", "wgr3"));
        items.add(new ItemDB("Кислый кристалл", "Кислотный кристалл", "gyjg"));
        items.add(new ItemDB("Кисель", "Кисель", "zyv9"));
        items.add(new ItemDB("Слизь", "Флегма", "5r04"));
        items.add(new ItemDB("Слизняк", "Скорлупа", "y5vw"));
        items.add(new ItemDB("Слюда", "Чернильница", "wgvd"));
        items.add(new ItemDB("Светляк", "Змеиный глаз", "4lml"));
        items.add(new ItemDB("Улитка", "Улитка", "kqj3"));
        items.add(new ItemDB("Пузырь", "Жвачка", "qo59"));
        items.add(new ItemDB("Пленка", "Ряска", "jkj4"));
        items.add(new ItemDB("Многогранник", "Многогранник", "ljpq"));
        items.add(new ItemDB("Колючка", "Репях", "9nml"));
        items.add(new ItemDB("Кристальная колючка", "Липкий репях", "1kdg"));
        items.add(new ItemDB("Морской еж", "Ершик", "gy06"));
        items.add(new ItemDB("Колобок", "Ежик", "zy7y"));
        items.add(new ItemDB("Стальной колобок", "Стальной Ежик", "5rd1"));

    }

    public static String getItemIdByName(String Name) {
        for (ItemDB item : items) {
            if (item.getOldName().equalsIgnoreCase(Name)) {
                return item.getItemId();
            }
            if (item.getNewName().equalsIgnoreCase(Name)) {
                return item.getItemId();
            }
        }
        return "Предмет не найден";
    }
}
