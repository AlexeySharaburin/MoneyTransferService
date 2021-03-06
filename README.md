# Сервис перевода денег MoneyTransferService

## Описание
Приложение *MoneyTransferService* представляет собой REST-сервис и предназначен для быстрого и удобного перевода денежных средств между держателями банковских карт, 
которые подключены к системе *MoneyTransferService*; 
при этом данные всех операций, произведенные в сервисе *MoneyTransferService* (с указанием времени операции) сохраняются в файле fileOperatiosLogs.log. 

## Порядок использования
### Запуск приложения *MoneyTransferService*
Для запуска приложения необходимо в корневом каталоге запустить файл transfer_service-0.0.1-SNAPSHOT.jar 
с помощью команды java -jar /transfer_service-0.0.1-SNAPSHOT.jar.
Также существует возможность запуска сервиса с помощью приложения *Docker* - для этого необходимо в корневом каталоге
запустить команду создания контейнера из образа transfer_service_docker:latest на порту 5500.
( Dockerfile)
При первом запуске приложения MoneyTransferService создастся файл 
fileOperatiosLogs.log (в который впоследствии будут записываться все операции,
выполняемые приложением).
### Запуск клиентского WEB-приложения *FRONT*
Для перевода денежных средств необходимо запустить приложение *FRONT* на порту 5500.
### Запуск приложений *MoneyTransferService* и *FRONT* с использованием средств *docker-compose*
Для автоматического запуска обоих приложений необходимо в корневом каталоге выполнить команду docker-compose up:
приложение *MoneyTransferService* запустится автоматически(образ для создания контейнера back_transfer:5.0), а страница приложения *FRONT*
(образ для создания контейнера front_transfer:3.0) будет доступна в браузере по адресу http://localhost:3000/card-transfer;
для остановки выполнения приложений - docker-compose down (контейнеры приложений описаны в файле docker-compose.yml).

## Перевод денежных средств
Процесс перевода осуществляется в два этапа:
1. На первом этапе клиент в приложении *FRONT* вводит следующие данные:
   - номер карты списания, состоящий из 16-и символов (например, 1111 1111 1111 1111) - обязательный параметр;
   - номер карты зачисления, состоящий из 16-и символов (например, 2222 2222 2222 2222) - обязательный параметр, номер карты списания не может совпадать с номером карты зачисления;
   - дата окончания действия карты списания в формате ММ/ГГ (например 11/22) - обязательный параметр, дата не может быть ниже текущей, месяц не может быть ниже 1 и выше 12;
   - код проверки подлинности карты CVV, состоящий из 3-х символов (например, 111) - обязательный параметр; 
   - сумма перевода в валюте перевода (RUR) - обязательный параметр, сумма перевода должна быть целым положительным числом;
     
Данные отправляются приложению *MoneyTransferService*, где происходит проверка валидности этих данных и, 
учитывая наличие необходимой для перевода суммы денежных средств (с учётом комиссии за перевод в размере 1%) 
на карте списания, устанавливается возможность перевода денежных средств. При положительном результате проверки входных данных,
на телефонный номер клиента отправляется СМС с кодом подтверждений операции перевода. Код состоит из 4-х цифр (например, 1111).

2. На втором этапе перевода клиент подтверждает выполнение операции, вводя код, полученный в СМС в приложение *FRONT*, код отправляется приложению *MoneyTransferService*, где происходит проверка этого кода,
и в случае успеха, происходит перевод денежных средств и внесение данных об операции в файл fileOperatiosLogs.log с указанием даты и времени перевода.
    
При возникновении ошибок на стороне сервера или при наличии ошибочных входных данных клиент получает через приложение *FRONT* информацию о проблемах, возникших в процессе операции с указанием причин,
которые явились причиной этих ошибок.

## Настройки приложения *MoneyTransferService*
В настройках приложения вы можете изменить расположение и имя файла с данными о произведенных операциях
        > nameLog = "fileOperatiosLogs.log",
    а также формат вывода даты и времени  
        > "HH:mm:ss dd.MM.yyyy".

В файле *application.properties* (/transfer_service/src/main/resources) вы можете изменить номер порта, 
на котором будет работать приложение *MoneyTransferService* - в настройках по умолчанию установлен номер порта 5500.
        
## Спасибо
Выражаю благодарность Максиму Воронцову за общее руководство проектом, полезные советы и комментарии в процессы реализации проекта,
а также Ивану Бочарову и Василию Дорохину за прекрасное изложение темы Spring на семинарах и подробные и полезные разъяснения в процессе выполнения домашних заданий.

## Контактная информация
Ждём ваши отзывы и комментарии о приложении *MoneyTransferService* - alexey.sharaburin@gmail.com.
