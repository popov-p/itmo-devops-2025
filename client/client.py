import argparse
import pika
import sys
from PySide6.QtCore import QObject, Signal


class RabbitMQClient(QObject):
    message_received = Signal(str)

    def __init__(self, host='127.0.0.1', port=30572):
        super().__init__()
        self.connection = None
        self.channel = None
        self.queueName = "LogEntryQueue"
        self.host = host
        self.port = port

    def connectToRabbitMq(self):
        try:
            credentials = pika.PlainCredentials('pavel','popov')
            parameters = pika.ConnectionParameters(host=self.host, port=self.port, credentials=credentials)
            self.connection = pika.BlockingConnection(parameters)
            self.channel = self.connection.channel()
            self.channel.queue_declare(queue=self.queueName, durable=True)

            self.channel.basic_consume(queue=self.queueName, on_message_callback=self.onMessageReceived, auto_ack=True)

            print(f"Waiting for messages in {self.queueName}. To exit press CTRL+C")
            self.channel.start_consuming()

        except Exception as e:
            print(f"Error connecting to RabbitMQ: {str(e)}", file=sys.stderr)

    def onMessageReceived(self, ch, method, properties, body):
        message = body.decode()
        print(f"Received message: {message}")
        self.message_received.emit(message)

    def close_connection(self):
        if self.connection:
            self.connection.close()

def main():
    parser = argparse.ArgumentParser(description="RabbitMQ client application")
    parser.add_argument('--host', type=str, default='127.0.0.1', help="RabbitMQ server hostname")
    parser.add_argument('--port', type=int, default=30572, help="RabbitMQ server port")
    args = parser.parse_args()

    rabbit_client = RabbitMQClient(args.host, args.port)
    rabbit_client.connectToRabbitMq()


if __name__ == "__main__":
    main()
