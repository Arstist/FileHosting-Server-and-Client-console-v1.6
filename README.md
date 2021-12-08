# FileHosting-Server-and-Client-console-v1.6


#RUS:
Достаточно запустить Server.jar для работы сервера.
Открытие порта на сервере совершается в классе Server в этой строке (Channel channel = serverBootstrap.bind(8080).sync().channel();)
Файлы хранящиеся на сервере лежат по указаному пути "src\main\java\Server\Files", можно класть файлы в ручную. 
Настройка доступных команд на сервере находится в классе Command

После настроки под ваш хостинг, можно пользоваться Client.jar, файлы будут качаться в корневую папку где находится Client.jar
Можно скачивать/загружать файлы на сервер с клиента вводя команду /dd, далее полный путь к папке. 
Подробнее о всех командах можно узнать написать команду /help, сервер вернёт список доступных команд.

#Minus
-Не реализована инициализация новых файлов без перезагрузки сервера



#ENG:
It is enough to run Server.jar for the server to work.
Opening a port on the server is done in the Server class in this line (Channel channel = serverBootstrap.bind (8080) .sync (). Channel ();)
Files stored on the server are located at the specified path "src \ main \ java \ Server \ Files", you can put the files manually.
The configuration of the available commands on the server is in the Command class

After setting up for your hosting, you can use Client.jar, the files will be downloaded to the root folder where Client.jar is located
You can download / upload files to the server from the client by entering the / dd command, followed by the full path to the folder.
For more information about all commands, you can write the command / help, the server will return a list of available commands.


#Minus
-Not implemented initialization of new files without restarting the server
