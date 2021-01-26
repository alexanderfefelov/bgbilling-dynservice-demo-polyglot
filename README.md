# bgbilling-dynservice-demo-polyglot

## Что это?

bgbilling-dynservice-demo-polyglot — это демонстрационная реализация dynservice на основе [GraalVM](https://www.graalvm.org/) для использования совместно с сервером [BGBilling](https://bgbilling.ru/).

## Как это установить?

Склонируйте этот репозиторий:

```
git clone https://github.com/alexanderfefelov/bgbilling-dynservice-demo-polyglot
```

Скопируйте содержимое каталога `dyn` в каталог `dyn` вашего экземпляра BGBilling.

## Привет, мир!

- [HelloWorldPolyglot.java](dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/HelloWorldPolyglot.java)
- [HelloWorldPolyglotImpl.java](dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/HelloWorldPolyglotImpl.java)
- [Hello.js](dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/Hello.js)
- [Hello.rb](dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/Hello.rb)
- [Hello.py](dyn/com/github/alexanderfefelov/bgbilling/dynservice/demo/Hello.py)

В конфигурацию ядра добавьте:

```properties
# Dynservice: Привет, мир!
#
#         Модуль      Сервис                                     Класс сервиса
#           │           │                                              │
#          ┌┴─┐ ┌───────┴────────┐ ┌───────────────────────────────────┴──────────────────────────────────────┐
#          │  │ │                │ │                                                                          │
dynservice:demo.HelloWorldPolyglot=com.github.alexanderfefelov.bgbilling.dynservice.demo.HelloWorldPolyglotImpl
```

Теперь выполните:

```bash
#                                                                       Модуль       Сервис                                                                        
#                                                                         │            │
#                                                                        ┌┴─┐ ┌────────┴───────┐
#                                                                        │  │ │                │
http --verbose --check-status \
  POST http://bgbilling-server.backpack.test:63081/billing/executer/json/demo/HelloWorldPolyglot \
      method=hello \
      params:='{}' \
      user:='{"user": "admin", "pswd": "admin"}'
```

В результате на запрос:

```
POST /billing/executer/json/demo/HelloWorldPolyglot HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 77
Content-Type: application/json
Host: bgbilling-server.backpack.test:63081
User-Agent: HTTPie/1.0.3

{
    "method": "hello",
    "params": {},
    "user": {
        "pswd": "admin",
        "user": "admin"
    }
}
```

будет получен ответ:

```
HTTP/1.1 200 OK
Content-Length: 121
Date: Tue, 26 Jan 2021 06:50:46 GMT

{"status":"ok","exception":null,"message":"","tag":null,"data":{"return":["Hello, js!","Hello, ruby!","Hello, python!"]}}
```
