Необходимо написать программу, импортирующую данные из xml-файла в БД, с учетом следующих ограничений:
* использовать чистую java (без spring и других фреймворков)
* для импорта использовать jdbc. Учесть быструю вставку. Количество insert = количество записей - будет считаться ошибкой
* игнорировать дубли. Они могут быть в исходном xml или уже присутствовать в БД. Реализация должна пропускать дублирующие данные
  Опционально можете усложнить задание на случай импорта большого xml (несколько Гб) - реализация в таком случае будет кардинально отличаться.
Ожидание:
* java код, выполняющий указанные выше требования
* код, собирающийся на любой машине, с использованием maven или gradle.
* инструкцию по настройке и запуску приложения (в формате .md)
* логически разделенные блоки, с корректным именованием, комментариями и обработкой ошибок 