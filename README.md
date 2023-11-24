# Egt-task

### Short Overview
#### 1. Данните за обменните валути се взимат от fixer.io
#### 2. Валутите се съхраняват в база данни и локален кеш
#### 3. Последниятните курсове на валутите се взимат се пазят в Redis (база данни като back-up) -> Cache -> DB -> fixer -> throw
#### 4. Заявките се пазят по Id && Service (в случая разделени на json и xml)
#### 5. За не се дублират заявките се ползва само базата данни


### How to run
#### ```docker compose up``` -> вдига всичко без JS клиента
#### ```node js-web-socket/RabbitListener.js``` пуска JS клиента. Клиента просто принтира в конзолата си всички съобщения, които получава от RabbitMQ

### How to test
```localhost:8080/current```
```json
{
    "requestId": "6215229217211",
    "clientId": "1232112",
    "currency": "EUR",
    "timestamp": 1700808396082,
    "period": 24
}
```

```localhost:8080/command```
```xml
<command id="1212434" >
<get consumer="13617162" >
<currency>EUR</currency>
</get>
</command>

```