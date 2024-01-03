//package com.alok.grpc.grpcdemo
//
//class HelloWorldServer(private val port: Int) {
//
//    val server = io.grpc.ServerBuilder
//        .forPort(port)
//        .addService(HelloWorldService())
//        .build()
//
//
//    fun start() {
//        server.start()
//        println("Starting gRPC server on port $port")
//        Runtime.getRuntime().addShutdownHook(
//            Thread {
//                println("*** shutting down gRPC server since JVM is shutting down")
//                this@HelloWorldServer.stop()
//                println("*** server shut down")
//            }
//        )
//    }
//
//    fun stop() {
//        println("Stopping gRPC server on port $port")
//        server.shutdown()
//    }
//
//    fun blockUntilShutdown() {
//        server.awaitTermination()
//    }
//
//    private class HelloWorldService : GreeterGrpcKt.GreeterCoroutineImplBase() {
//        override suspend fun sayHello(request: HelloRequest) = HelloReply
//            .newBuilder()
//            .setMessage("Hello ${request.name}")
//            .build()
//    }
//}
//
//fun main() {
//    val port = System.getenv("PORT")?.toInt() ?: 50051
//    val server = HelloWorldServer(port)
//    server.start()
//    server.blockUntilShutdown()
//}