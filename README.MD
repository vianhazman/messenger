# Messenger

Real-time chat engine with Apache Kafka message queue and websocket connectivity.
Based on tutorials:
- Kafka and websocket: https://dev.to/subhransu/realtime-chat-app-using-kafka-springboot-reactjs-and-websockets-lc
- MongoDB persisting: https://medium.com/javarevisited/building-persistable-one-to-one-chat-application-using-spring-boot-and-websockets-303ba5d30bb0

## Running Server
Latest updates are pushed into Docker registry.
```aidl
docker-compose -f deployment/docker-compose.prod.yml up
```

## Send Message

Sends message from sender to recepient

**URL** : `/api/send/`

**Method** : `POST`

**Payload:**
```json
{
    "sender": "[sender uid]",
    "recipient": "[recipient uid]",
    "content": "[content in plain text]"
}
```

## Count New Messages

Sends message from sender to recepient

**URL** : `/api/messages/{senderId}/{recipientId}/count`

**Method** : `GET`

**Response:**
```json
[count in integer]
```

## Get Chat Messages

Sends message from sender to recepient

**URL** : `/api/messages/{senderId}/{recipientId}`

**Method** : `GET`

**Response:**
```json
[
    {
        "sender": "[sender uid]",
        "recipient": "[recipient uid]",
        "id": "[chat id]",
        "chatId": "[content]"
    }, ...
]
```

## Get Chat Messages

Sends message from sender to recepient

**URL** : `/api/messages/{senderId}/{recipientId}`

**Method** : `GET`

**Response:**
```json
[
    {
        "sender": "[sender uid]",
        "recipient": "[recipient uid]",
        "id": "[chat id]",
        "content": "[content]",
        "status": "[DELIVERED/RECEIVED]",
        "timestamp": "[timestamp]"
    }, ...
]
```

## Get Chat Rooms

Get available chat rooms

**URL** : `/api/rooms/{senderId}`

**Method** : `GET`

**Response:**
```json
[
    {
        "sender": "[sender uid]",
        "recipient": "[recipient uid]",
        "id": "[id]",
        "chatId": "[chatId]",
    }, ...
]
```

## Get Specific Message

Get one message with message id

**URL** : `/api/messages/{id}`

**Method** : `GET`

**Response:**
```json
  {
        "sender": "[sender uid]",
        "recipient": "[recipient uid]",
        "id": "[chat id]",
        "content": "[content]",
        "status": "[DELIVERED/RECEIVED]",
        "timestamp": "[timestamp]"
    }
```

## Websocket to receive message

Get one message with message id, needs frontend logic to route messages from WS.

**URL** : `/ws-chat/`

**Method** : `GET`

**Response:**
```json
  {
        "sender": "[sender uid]",
        "recipient": "[recipient uid]",
        "id": "[chat id]",
        "content": "[content]",
        "status": "[DELIVERED/RECEIVED]",
        "timestamp": "[timestamp]"
    }
```