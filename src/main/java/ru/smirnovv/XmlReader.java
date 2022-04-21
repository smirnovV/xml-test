package ru.smirnovv;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Читатель XML-файла
 */
public class XmlReader {

    /**
     * Название читаемого тега
     */
    private static final String tagName = "Title";

    /**
     * Потоковый читатель XML-файла
     */
    private final XMLStreamReader reader;

    /**
     * Размер пачек для чтения
     */
    private final int chunkSize;

    /**
     * Поток закрыт
     */
    private boolean isClose = false;

    /**
     * @param reader    читатель XML-файла
     * @param chunkSize размер пачек для чтения
     */
    public XmlReader(XMLStreamReader reader, int chunkSize) {
        this.reader = reader;
        this.chunkSize = chunkSize;
    }

    /**
     * @param path      путь до XML-файла
     * @param chunkSize размер пачек для чтения
     * @return экземпляр класса
     * @throws ApplicationException возникшая ошибка
     */
    public static XmlReader newInstance(String path, int chunkSize) throws ApplicationException {
        try {
            return new XmlReader(XMLInputFactory.newInstance()
                                                .createXMLStreamReader(new FileInputStream(path)),
                                 chunkSize);
        } catch (Exception exception) {
            throw new ApplicationException("Ошибка открытия XML-файла", exception);
        }
    }

    /**
     * Прочитать очередную пачку данных
     *
     * @return множество строк для сохранения
     * @throws ApplicationException возникшая ошибка
     */
    public Set<String> readChunk() throws ApplicationException {
        if (isClose) {
            throw new ApplicationException("Попытка чтения закрытого потока", null);
        }

        try {
            Set<String> set = new HashSet<>();
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT && tagName.equals(reader.getLocalName())) {
                    set.add(reader.getElementText());
                }

                if (set.size() == chunkSize) {
                    return set;
                }
            }
            reader.close();
            isClose = true;
            return set;
        } catch (Exception exception) {
            try {
                reader.close();
            } catch (Exception ignored) {
            }

            throw new ApplicationException("", exception);
        }

    }

    /**
     * Проверить наличие данных для сохранения
     *
     * @return false, если данных для записи нет, true - иначе
     */
    public boolean hasNext() {
        return !isClose;
    }

}
