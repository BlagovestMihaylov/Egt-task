const amqp = require('amqplib');

async function consumeMessages() {
    try {
        const connection = await amqp.connect('amqp://localhost');
        const channel = await connection.createChannel();

        const exchangeName = 'currency-gateway-exchange';
        const queueName = 'currency-gateway-queue';
        const routingKey = 'currency-gateway-routing-key';


        await channel.assertExchange(exchangeName, 'topic', { durable: true });
        const assertQueue = await channel.assertQueue(queueName, { durable: true });
        await channel.bindQueue(assertQueue.queue, exchangeName, routingKey);

        console.log('Waiting for messages. To exit press CTRL+C');

        await channel.consume(assertQueue.queue, (msg) => {
            const message = JSON.parse(msg.content.toString());
            console.log('Received message:', message);


            channel.ack(msg);
        }, {noAck: false});

    } catch (error) {
        console.error('Error:', error.message);
    }
}

consumeMessages();