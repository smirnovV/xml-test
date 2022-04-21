package ru.smirnovv;

/**
 * Приложение для сохранения XML-файла в базу данных.
 */
public class Application {

    /**
     * Слой работы с базой данных
     */
    private final XmlTableDAO xmlTableDAO;

    /**
     * Конструктор
     *
     * @param xmlTableDAO слой работы с базой данных
     */
    public Application(XmlTableDAO xmlTableDAO) {
        this.xmlTableDAO = xmlTableDAO;
    }

    /**
     * Точка входа
     *
     * @param args аргументы программы: url БД, пользователь БД, пароль БД, путь к XML - файлу
     */
    public static void main(String[] args) {
        new Application(XmlTableDAO.getInstance(args[0], args[1], args[2]))
                .saveXml(args[3]);
    }

    /**
     * Сохранить XML - файла
     *
     * @param path путь до XML - файла
     */
    private void saveXml(String path) {
        try {
            XmlReader reader = XmlReader.newInstance(path, 10000);
            xmlTableDAO.initDataBase();

            int prev = xmlTableDAO.retrieveRowCount();

            while (reader.hasNext()) {
                xmlTableDAO.insertData(reader.readChunk());
            }

            int after = xmlTableDAO.retrieveRowCount();

            System.out.println("Вставлено записей: " + (after - prev));
        } catch (ApplicationException exception) {
            exception.printStackTrace();
        }
    }

}
