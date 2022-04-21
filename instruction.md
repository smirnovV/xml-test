Запуск jar-файла:
java -jar xml-test-1.0-SNAPSHOT.jar <url> <user> <password> <file>
Пример:
java -jar xml-test-1.0-SNAPSHOT.jar jdbc:postgresql://localhost:9001/staff staff password ex.xml

Пример XML-файла:
    
    <Titles>
        <Title>t1</Title>
        <Title>t2</Title>
        <Title>t3</Title>
    </Titles>